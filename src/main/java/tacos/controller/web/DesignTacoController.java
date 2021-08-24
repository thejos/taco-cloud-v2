package tacos.controller.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import tacos.data.IngredientRepository;
import tacos.data.TacoRepositry;
import tacos.model.bean.Ingredient;
import tacos.model.bean.IngredientType;
import tacos.model.bean.Taco;
import tacos.model.bean.TacoOrder;

/**
 * <code>DesignTacoController</code> is a controller class that addresses the
 * following requirements:
 * <ul>
 * <li>Handle HTTP GET requests where the request path is
 * "<tt>/design</tt>"</li>
 * <li>Build a list of ingredients</li>
 * <li>Hand the request and the ingredient data off to a view template to be
 * rendered as HTML and sent to the requesting web browser</li>
 * </ul>
 * 
 * @see #addIngredientsToModel(Model)
 * @see #showDesignForm(Model)
 * @see #processTaco(Taco, Errors, TacoOrder)
 * 
 * 
 * @author Dejan Smiljić <dej4n.s@gmail.com>
 *
 */
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

	@Autowired
	private IngredientRepository ingredientRepository;
	@Autowired
	private TacoRepositry tacoRepositry;

	private Logger logger = LoggerFactory.getLogger(DesignTacoController.class);

	/**
	 * 
	 * @param model <br>
	 *              <br>
	 *              <code>addIngredientsToModel</code> method is invoked when a
	 *              request is handled and constructs a list of
	 *              <code>Ingredient</code> objects to be put into the model.<br>
	 *              The method uses the injected <code>IngredientRepository</code>'s
	 *              method <code>findAll</code> to fetch all ingredients from the
	 *              database. <br>
	 *              Once the list of ingredients is ready, the next few lines of
	 *              <code>addIngredientsToModel</code> method filters the list by
	 *              ingredient type (using a helper method named
	 *              <code>filterByType</code>). A list of ingredient types is then
	 *              added as an attribute to the <code>Model</code> object that will
	 *              be passed to <code>showDesignForm</code> method.<br>
	 *              <code><b>Model</b></code> is an object that ferries data between
	 *              a controller and whatever view is charged with rendering that
	 *              data. Ultimately, data that’s placed in <code>Model</code>
	 *              attributes is copied into the servlet request attributes, where
	 *              the view can find them and use them to render a page in the
	 *              user’s browser.
	 */
	@ModelAttribute
	public void addIngredientsToModel(Model model) {

		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepository.findAll().forEach(i -> ingredients.add(i));

		IngredientType[] types = IngredientType.values();
		for (IngredientType type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
	}// addIngredientsToModel() END

	// helper method; filters the list by ingredient type
	private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, IngredientType ingredientType) {
		return ingredients.stream().filter(x -> x.getIngredientType().equals(ingredientType))
				.collect(Collectors.toList());
	}// filterByType() END

	/**
	 * @param model
	 * @return <b>String</b> - the logical name of the view that will be used to
	 *         render the model to the browser.<br>
	 *         <br>
	 *         <tt>@GetMapping</tt> annotation paired with the class-level
	 *         <tt>@RequestMapping</tt> annotation specifies that when an HTTP GET
	 *         request is received for "<tt>/design</tt>",
	 *         <code>showDesignForm</code> method will be called to handle the
	 *         request.<br>
	 *         <code>showDesignForm</code> method also populates the given
	 *         <code>Model</code> with an empty <code>Taco</code> object under a key
	 *         whose name is "taco".
	 */
	@GetMapping
	public String showDesignForm(Model model) {
		model.addAttribute("taco", new Taco());
//		model.addAttribute("tacoOrder", new TacoOrder());
		return "design";
	}// showDesignForm() END

	/**
	 * <code>@ModelAttribute</code> annotation ensures that an TacoOrder object will
	 * be created in the model. <br>
	 * The class-level <code>@SessionAttributes</code> annotation specifies any
	 * model objects like the <tt>tacoOrder</tt> attribute that should be kept in
	 * session and available across multiple requests.
	 * 
	 * @return TacoOrder
	 */
	@ModelAttribute(name = "tacoOrder")
	public TacoOrder tacoOrder() {
		return new TacoOrder();
	}

	/**
	 * 
	 * @param taco
	 * @param errors
	 * @return <b>String</b> - the value returned indicates a view that will be
	 *         shown to the user. <br>
	 *         <br>
	 *         The <tt>@Valid</tt> annotation tells Spring MVC to perform validation
	 *         on the submitted <code>Taco</code> object after it’s bound to the
	 *         submitted form data and before the <code>processTaco</code> method is
	 *         called. If there are any validation errors, the details of those
	 *         errors will be captured in an <code>Errors</code> object that's
	 *         passed into <code>processTaco</code> method. The first few lines of
	 *         this method consult the <code>Errors</code> object asking its
	 *         <code>hasErrors</code> method if there are any validation errors. If
	 *         there are, the method concludes without processing the
	 *         <code>Taco</code> and returns the <tt>"design"</tt> view name so that
	 *         the form is redisplayed. If there are no errors the user’s browser
	 *         should be redirected to the relative path
	 *         "<tt>/orders/current</tt>".<br>
	 *         The <code>TacoOrder</code> parameter is annotated with
	 *         <tt>@ModelAttribute</tt> to indicate that its value should come from
	 *         the model and that Spring MVC shouldn’t attempt to bind request
	 *         parameters to it.<br>
	 *         After checking for validation errors, injected
	 *         <code>TacoRepository</code> is used to persist (save) the taco.
	 *         <code>Taco</code> object is then also added to the
	 *         <code>TacoOrder</code> that’s kept in the session.
	 */
	@PostMapping
	public String processTaco(@Valid @ModelAttribute("taco") Taco taco, Errors errors,
			@ModelAttribute TacoOrder tacoOrder) {
		if (errors.hasErrors()) {
			return "design";
		}

		// After checking for validation errors, processTaco() uses the injected
		// TacoRepository to save the taco.
		Taco persistedTaco = tacoRepositry.save(taco);
		// Adds the Taco object to the TacoOrder that’s kept in the session.
		tacoOrder.addTaco(persistedTaco);
		// tacoOrder.getTacos().add(persistedTaco);

		logger.info("\nProcessing taco... " + persistedTaco + "\n all tacos: " + tacoOrder.getTacos());
		return "redirect:/orders/current";
	}// processTaco() END

}
