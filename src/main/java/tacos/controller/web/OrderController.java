package tacos.controller.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import tacos.data.OrderRepository;
import tacos.model.bean.TacoOrder;
import tacos.model.bean.User;

/**
 * The class-level <tt>@RequestMapping</tt> specifies that any request-handling
 * methods in this controller will handle requests whose path begins with
 * <tt>/orders</tt>. When combined with the method-level <tt>@GetMapping</tt>,
 * it specifies that the <code>orderForm</code> method will handle HTTP GET
 * requests for <tt>/orders/current</tt>.
 * 
 * @see #orderForm(User, TacoOrder)
 * @author Dejan Smiljić <dej4n.s@gmail.com>
 */
@Controller
@RequestMapping(path = "/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;

	private Logger logger = LoggerFactory.getLogger(OrderController.class);

	/**
	 * Pre-populates the <code>TacoOrder</code> with the user’s name and address, so
	 * they don’t have to reenter it for each order. <br>
	 * The <code>orderForm</code> view is provided by a Thymeleaf template:
	 * <tt>/src/main/resources/templates/orderForm.html</tt><br>
	 * <strong>Most request-handling methods conclude by returning the logical name
	 * of a view, to which the request (along with any model data) is
	 * forwarded.</strong>
	 * 
	 * @param
	 * @return <b>String</b> - the value returned indicates a view that will be
	 *         shown to the user. <br>
	 *         <br>
	 */
	@GetMapping(path = "/current")
	public String orderForm(@AuthenticationPrincipal User user, @ModelAttribute TacoOrder tacoOrder) {

		if (tacoOrder.getDeliveryName() == null) {
			tacoOrder.setDeliveryName(user.getFullName());
		}

		if (tacoOrder.getDeliveryStreet() == null) {
			tacoOrder.setDeliveryStreet(user.getStreet());
		}

		if (tacoOrder.getDeliveryCity() == null) {
			tacoOrder.setDeliveryCity(user.getCity());
		}

		if (tacoOrder.getDeliveryState() == null) {
			tacoOrder.setDeliveryState(user.getState());
		}

		if (tacoOrder.getDeliveryZip() == null) {
			tacoOrder.setDeliveryZip(user.getZip());
		}

		return "orderForm";
	}

	/*-
	 * // Uncomment, fix imports...
	 * // if user types URL to navigate to '/orders/current' page, this method
	 * // creates session attribute 'tacoOrder' and prevents error:
	 * // '500: Expected session attribute 'tacoOrder''
	 * @ModelAttribute(name = "tacoOrder")
	 * public TacoOrder tacoOrder(Model model) {
	 *	 if (!model.containsAttribute("tacoOrder")) {
	 *		 logger.info("\n Object \"tacoOrder\" exposed to web view");
	 *		 return new TacoOrder();
	 *	 } else {
	 *		 logger.info("\nModel already contains attribute named \"tacoOrder\". returned null.");
	 * 		 return null;
	 *	 }
	 *}
	 */

	// prevents '405: Request method 'GET' not supported' error if
	// user types URL to navigate to '/orders' page
	@GetMapping
	public String processOrder() {
		logger.info("\n Attempt GET request for \"/orders\"... Redirect to \"/design\"");
		return "redirect:/design";
	}

	/**
	 * 
	 * @param tacoOrder
	 * @param errors
	 * @param sessionStatus
	 * @return <b>String</b> - the value returned indicates a view that will be
	 *         shown to the user. <br>
	 *         <br>
	 *         This method saves the <code>TacoOrder</code> object via the
	 *         <code>save</code> method on the injected
	 *         <code>OrderRepository</code>. The <code>TacoOrder</code> object is
	 *         submitted in the form (<tt>orderForm</tt>). It is the same
	 *         <code>TacoOrder</code> object maintained in <tt>session</tt>.<br>
	 *         Before the TacoOrder is saved, authenticated <tt>User</tt> is
	 *         determined. It is done via User object annotated as
	 *         <tt>@AuthenticationPrincipal</tt>. This annotation limits the
	 *         security-specific code to the annotation itself. By the time the
	 *         <tt>User</tt> object gets in <code>processOrder</code>, it is ready
	 *         to be used and assigned to <code>TacoOrder</code>.<br>
	 *         Once the <code>TacoOrder</code> is saved (persisted), it is not
	 *         needed in session anymore. The <code>processOrder</code> method asks
	 *         for a <code>SessionStatus</code> parameter and calls its
	 *         <code>setComplete</code> method to reset the session.<br>
	 *         If it's not cleaned out, the <code>TacoOrder</code> object remains in
	 *         session, including its associated <tt>Taco</tt> objects, so the next
	 *         order will start with whatever tacos the old order contained.
	 */
	@PostMapping
	public String processOrder(@Valid TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus,
			@AuthenticationPrincipal User user) {
		if (errors.hasErrors()) {
			return "orderForm";
		}

		/*-
		 * Remove the method parameter User, fix imports! 
		 * // obtains an Authentication object from the security context...
		 * Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 * // ...and then requests its principal
		 * User user = (User) authentication.getPrincipal();	
		 */

		logger.info("\n Authenticated user determined");
		// assigns user to TacoOrder object
		tacoOrder.setUser(user);
		logger.info("\n User assigned to order of tacos");

		TacoOrder persistedOrder = orderRepository.save(tacoOrder);
		logger.info("\n Order processed: " + persistedOrder);

		sessionStatus.setComplete();
		return "redirect:/";
	}

}
