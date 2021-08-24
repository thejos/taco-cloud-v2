package tacos.model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Dejan Smiljić <dej4n.s@gmail.com>
 *
 */
@Entity
public class Taco implements Serializable {

	private static final long serialVersionUID = 2L;

	/**
	 * The sequence strategy consists of two parts - defining a named sequence and
	 * using the named sequence in one or more fields in one or more classes.
	 * The @SequenceGenerator annotation is used to define a sequence and accepts a
	 * name, an initial value (the default is 1) and an allocation size (the default
	 * is 50). A sequence is global to the application and can be used by one or
	 * more fields in one or more classes. The SEQUENCE strategy is used in
	 * the @GeneratedValue annotation to attach the given field to the previously
	 * defined named sequence. Unlike AUTO and IDENTITY, the SEQUENCE strategy
	 * generates an automatic value as soon as a new entity object is persisted
	 * (i.e. before commit). This may be useful when the primary key value is needed
	 * earlier. To minimize round trips to the database server, IDs are allocated in
	 * groups. The number of IDs in each allocation is specified by the
	 * allocationSize attribute. It is possible that some of the IDs in a given
	 * allocation will not be used. Therefore, this strategy does not guarantee
	 * there will be no gaps in sequence values.
	 */
	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "T_SEQ_GEN", sequenceName = "taco_sequence_generator", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_SEQ_GEN")
	private Long id;
	/**
	 * Spring supports Java’s Bean Validation API (also known as JSR-303; <a href=
	 * "https://jcp.org/en/jsr/detail?id=303">https://jcp.org/en/jsr/detail?id=303</a>
	 * ). This makes it easy to declare validation rules as opposed to explicitly
	 * writing declaration logic in your application code. The Validation API offers
	 * several annotations that can be placed on properties of domain objects to
	 * declare validation rules. Hibernate’s implementation of the Validation API
	 * adds even more validation annotations. Both can be added to a project by
	 * adding the Spring Validation starter to the build.<br>
	 * In earlier versions of Spring Boot, the Spring Validation starter was
	 * automatically included with the web starter. Starting with Spring Boot 2.3.0,
	 * you’ll need to explicitly add it to your build if you intend to apply
	 * validation.
	 */
	@NotNull
	@Size(min = 5, message = "Name must be at least 5 characters long")
	private String name;
	/**
	 * To declare the relationship between a Taco and its associated Ingredient
	 * list, annotation @ManyToMany is used. A Taco can have many Ingredient
	 * objects, and an Ingredient can be a part of many Tacos.
	 */
	@NotNull
	@Size(min = 1, message = "You must choose at least 1 ingredient")
	@ManyToMany(targetEntity = Ingredient.class)
	private List<Ingredient> ingredients;

	// private Date dateCreated = new Date();
	private Date dateCreated;

	// constructors
	// default constructor is mandatory; id parameter is not mandatory;
	public Taco() {
	}

	public Taco(String name, List<Ingredient> ingredients, Date dateCreated) {
		this.name = name;
		this.ingredients = ingredients;
		this.dateCreated = dateCreated;
	}

	// getters, setters || accessors, mutators
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	// custom, helper methods
	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
	}

	/**
	 * Sets the dateCreated property to the current date and time before Taco is
	 * persisted
	 */
	@PrePersist
	public void creationDate() {
		this.dateCreated = new Date();
	}

	@Override
	public String toString() {
		return "Taco [id=" + id + ", name=" + name + ", ingredients=" + ingredients + ", dateCreated=" + dateCreated
				+ "]";
	}

}
