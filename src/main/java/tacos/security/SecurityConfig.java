package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import tacos.data.UserRepository;
import tacos.model.bean.User;

/**
 * @author Dejan Smiljić <dej4n.s@gmail.com>
 *
 */
@Configuration
public class SecurityConfig {

	/**
	 * Spring Security supports few encoding mechanisms. For complete list of
	 * supported encoders see <tt>PasswordEncoder</tt> implementation.<br>
	 * No matter which password encoder is used, the password in the database is
	 * never decoded. Instead, the password entered at login is encoded using the
	 * same algorithm, and it’s then compared with the encoded password in the
	 * database. That comparison is performed in the <tt>PasswordEncoder</tt>'s
	 * <tt>matches</tt> method.<br>
	 * <tt>BCrypt</tt> algorithm will internally generate a random salt.<br>
	 * <tt>BCrypt</tt> algorithm generates a String of length 60.<br>
	 * Some mechanisms, such as the <tt>MD5PasswordEncoder</tt> and
	 * <tt>ShaPasswordEncoder</tt> use weaker algorithms and are now deprecated.
	 * 
	 * @return Implementation of PasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * The <code>userDetailsService</code> method is given a
	 * <code>UserRepository</code> as a parameter. To create the bean, it returns a
	 * lambda that takes a <code>username</code> parameter and uses it to call
	 * <code>findByUsername</code> on the given <code>UserRepository</code>.<br>
	 * The <tt>loadByUsername</tt> method has one simple rule: it must never return
	 * <tt>null</tt>. Therefore, if the call to <code>findByUsername</code> returns
	 * <tt>null</tt>, the lambda will throw a
	 * <code>UsernameNotFoundException</code>. Otherwise, the <code>User</code> that
	 * was found will be returned.
	 * 
	 * @param userRepository
	 * @return <b>User</b> object
	 * @throws <b>UsernameNotFoundException</b> if User object is null
	 * @see org.springframework.security.core.userdetails.UserDetailsService
	 */
	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepository) {
		return username -> {
			User user = userRepository.findByUsername(username);
			if (user != null) {
				return user;
			}
			throw new UsernameNotFoundException("User \"" + username + "\" not found!");
		};
	}

	/**
	 * This method accepts an <code>HttpSecurity</code> object which acts as a
	 * builder that can be used to configure how security is handled at the web
	 * level. Once security configuration is setup via the <code>HttpSecurity</code>
	 * object a call to <code>build</code> method will create a
	 * <tt>SecurityFilterChain</tt> that is returned from the bean method.<br>
	 * <br>
	 * {@link org.springframework.security.config.annotation.web.builders.HttpSecurity
	 * <b>HttpSecurity</b>}<br>
	 * Among many things that can be configured with <code>HttpSecurity</code> are
	 * these:
	 * <ul>
	 * <li>Requiring that certain security conditions be met before allowing a
	 * request to be served</li>
	 * <li>Configuring a custom login page</li>
	 * <li>Enabling users to log out of the application</li>
	 * <li>Configuring cross-site request forgery protection</li>
	 * </ul>
	 * Intercepting requests to ensure that the user has proper authority is the
	 * most common thing to configure <code>HttpSecurity</code> to do.<br>
	 * <br>
	 * 
	 * @param httpSecurity
	 * @return SecurityFilterChain
	 * @throws Exception
	 * @see org.springframework.security.config.annotation.SecurityBuilder#build()
	 *      build
	 * @see org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl
	 *      AuthorizedUrl
	 * @see <a href=
	 *      "https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#expressions">SpEL</a>
	 *      - Spring Expression Language (external source)
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		// The call to authorizeRequests() method returns an
		// ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry on which
		// you can specify URL paths and patterns and the security requirements for
		// those paths object.

		// In this case, two security rules are specified:
		// 1. Requests for /design and /orders should be for users
		// with a granted authority of ROLE_USER. Don’t include the "ROLE_"
		// prefix on roles passed to hasRole() method, it will be assumed by hasRole().
		// 2. All requests should be permitted to all users.

		// The order of these rules is important. Security rules declared first take
		// precedence over those declared lower down. If you were to swap the order of
		// those two security rules, all requests would have permitAll() applied to
		// them; the rule for /design and /orders requests would have no effect.

		/*
		 * return httpSecurity.authorizeRequests().antMatchers("/design",
		 * "/orders").hasRole("USER") .antMatchers("/",
		 * "/**").permitAll().and().build();
		 */

		/*
		 * hasRole() and permitAll() are just a couple of the methods for declaring
		 * security requirements for request paths. See Javadoc: AuthorizedUrl class
		 * declaration for list of available methods. Most of these methods provide
		 * essential security rules for request handling, but they’re self-limiting,
		 * only enabling security rules as defined by those methods. Alternatively, you
		 * can use the access() method to provide a SpEL expression to declare richer
		 * security rules. Spring Security extends SpEL to include several
		 * security-specific values and functions. With SpEL-based security constraints,
		 * the possibilities are virtually endless. For more information on Spring
		 * Expression Language see Javadoc: SpEL.
		 */
		return httpSecurity
				// ensure that requests for /design and /orders are only available to
				// authenticated users
				.authorizeRequests().antMatchers("/design", "/orders", "/orders/**").access("hasRole('USER')")
				// all other requests should be permitted for all users
				.antMatchers("/", "/**").access("permitAll()")
				/*
				 * A call to and() method bridges the sections of configuration. This method can
				 * be called several times as new sections of configuration begin.
				 */
				.and()
				/*-
				 * A Call to formLogin() method begins configuring custom login form. 
				 * Call to loginPage() method designates the path where the custom login page
				 * will be provided.
				 * When Spring Security determines that the user is unauthenticated and needs to 
				 * log in, it will redirect them to this path.
				 */
				.formLogin().loginPage("/login").and()
				// enable logout
				.logout()
				// specify custom post-logout landing page
				.logoutSuccessUrl("/?logout_success")
				// session invalidation and list of cookies to be deleted when user logs out
				.invalidateHttpSession(true).deleteCookies("JSESSIONID")
				// bridge
				.and()
				// make h2-console non-secured; for testing purpose
				// H2 Console shouldn't be unsecured in production!
				.csrf().ignoringAntMatchers("/h2-console/**")
				// allow pages to be loaded in frames from the same origin; needed for
				// h2-console
				.and().headers().frameOptions().sameOrigin()
				// conclude filter configuration - build security filter
				.and().build();

	}// securityFilterChain() END

}
