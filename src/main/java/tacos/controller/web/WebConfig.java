package tacos.controller.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <strong>View controllers can be used to handle HTTP GET requests for which no
 * model data or processing is required.</strong><br>
 * <br>
 * When a controller is simple enough that it doesn’t populate a model or
 * process input, controller can be defined in an alternative way. This class
 * implements the <code>WebMvcConfigurer</code> interface.
 * <code>WebMvcConfigurer</code> defines several methods for configuring Spring
 * MVC. Even though it’s an interface, it provides default implementations of
 * all the methods, so you only need to override the methods you need. In this
 * case the <code>addViewControllers</code> method is overridden.<br>
 * 
 * @see #addViewControllers(ViewControllerRegistry)
 * @author Dejan Smiljić <dej4n.s@gmail.com>
 * 
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	/**
	 * <code>addViewControllers</code> method is given a
	 * <tt>ViewControllerRegistry</tt> that you can use to register one or more view
	 * controllers. Here, you call <code>addViewController</code> method on the
	 * registry, passing in “/”, which is the path for which your view controller
	 * will handle GET requests. That method returns a
	 * <tt>ViewControllerRegistration</tt> object, on which you immediately call
	 * <code>setViewName</code> method to specify <tt>"home"</tt> as the view that a
	 * request for “/” should be forwarded to.
	 *
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		// login page view controller - handles requests at /login path
		registry.addViewController("/login").setViewName("login");
	}

}
