package tacos.controller.security;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import tacos.data.UserRepository;
import tacos.model.bean.User;
import tacos.security.bean.RegistrationForm;

/**
 * <code>RegistrationController</code>, like any typical Spring MVC controller
 * is annotated with <tt>@Controller</tt> to designate it as a controller and to
 * mark it for component scanning.<br>
 * Annotation <tt>@RequestMapping</tt> indicates
 * <code>RegistrationController</code> will handle requests whose path is
 * <tt>/register</tt>.
 * 
 * @see #registerForm()
 * 
 * @author Dejan Smiljić <dej4n.s@gmail.com>
 *
 */
@Controller
@RequestMapping("/register")
@SessionAttributes("registrationForm")
public class RegistrationController {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	private Logger logger = LoggerFactory.getLogger(RegistrationForm.class);

	public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * Handles a GET request whose path is <tt>/register</tt>.
	 * 
	 * @return <b>String</b> - the value returned indicates a view that will be
	 *         shown to the user.
	 */
	@GetMapping
	public String registerForm(Model model) {
		model.addAttribute("registrationForm", new RegistrationForm());
		return "registration";
	}

	/**
	 * While processing a form submission, <code>processRegistration</code> method
	 * passes <code>PasswordEncoder</code> to the <code>toUser</code> method which
	 * uses it to encode the password before saving it to the database. In this way,
	 * the submitted password is written in an encoded form, and the
	 * {@link tacos.security.SecurityConfig#userDetailsService(UserRepository)
	 * userDetailsService} will be able to authenticate against that encoded
	 * password.<br>
	 * The parameter <code>RegistrationForm</code> object is bound to the request
	 * data and it is defined in class: tacos.security.bean.RegistrationForm.
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService
	 * @param registrationForm
	 * @return <b>String</b> - the value returned indicates a view that will be
	 *         shown to the user.
	 */
	@PostMapping
	public String processRegistration(@Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm,
			Errors errors, SessionStatus sessionStatus) {
		if (errors.hasErrors()) {
			return "registration";
		}

		User persistedUser = userRepository.save(registrationForm.toUser(passwordEncoder));
		logger.info("\n User registration processed: " + persistedUser);
		sessionStatus.setComplete();
		return "redirect:/login";

	}

}
