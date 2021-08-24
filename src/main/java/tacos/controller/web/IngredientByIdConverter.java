package tacos.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.data.IngredientRepository;
import tacos.model.bean.Ingredient;

/**
 * @author Dejan Smiljić <dej4n.s@gmail.com> <br>
 *         <br>
 *         Class <code>IngredientByIdConverter</code> converts a <b>String to an
 *         Ingredient&nbsp;</b><tt>[tacos.model.bean.Ingredient]</tt>.<br>
 *         <br>
 *         A converter is any class that implements Spring’s <b>Converter</b>
 *         interface and implements its <code>convert</code> method to take one
 *         value and convert it to another.<br>
 *         The overridden <code>convert</code> method takes a <b>String</b>
 *         which is the ingredient ID and uses it to lookup the
 *         <strong>Ingredient</strong>.<br>
 *         Method <code>findById</code> fetches <code>Ingredient</code> object
 *         from database. This method is a member of injected
 *         <code>IngredientRepository</code>.<br>
 *         The <code>IngredientByIdConverter</code> is annotated with
 *         <tt>@Component</tt> to make it recognizable as a bean in the Spring
 *         application context.
 * 
 */
@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

	@Autowired
	private IngredientRepository ingredientRepository;

	// constructor
	public IngredientByIdConverter() {
	}

	@Override
	public Ingredient convert(String id) {
		return ingredientRepository.findById(id).orElse(null);
	}

}
