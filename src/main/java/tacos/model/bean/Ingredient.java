package tacos.model.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * @author Dejan Smiljić <dej4n.s@gmail.com>
 *
 */
@Entity
public class Ingredient implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String name;
	@Enumerated(EnumType.STRING)
	private IngredientType ingredientType;

	// no-args constructor
	public Ingredient() {
	}

	// constructor with fields
	public Ingredient(String id, String name, IngredientType ingredientType) {
		this.id = id;
		this.name = name;
		this.ingredientType = ingredientType;
	}

	// getters and setter || accessors and mutators
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IngredientType getIngredientType() {
		return ingredientType;
	}

	public void setIngredientType(IngredientType ingredientType) {
		this.ingredientType = ingredientType;
	}

	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", name=" + name + ", ingredientType=" + ingredientType + "]";
	}

}
