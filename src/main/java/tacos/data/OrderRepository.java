package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.model.bean.TacoOrder;

/**
 * The CrudRepository interface provides sophisticated CRUD functionality for
 * the entity class that is being managed. Extending CrudRepository exposes a
 * complete set of methods to manipulate your entities.<br>
 * <br>
 * There’s no need to write method implementation. When the application starts,
 * Spring Data will automatically generate an implementation. This means the
 * repositories are ready to use from the get-go.<br>
 * <br>
 * Interface that extends CrudRepositry can be customized by adding method
 * declarations.
 * 
 * @author Dejan Smiljić <dej4n.s@gmail.com>
 *
 */
public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

	// Perform queries unique to your domain?
	// Method declarations can be added to this interface.

	/*
	 * // Example: fetch all the orders delivered to a given ZIP code...
	 * List<TacoOrder> findByDeliveryZip(String deliveryZip);
	 */

	// Spring Data examines any methods in the repository interface, parses the
	// method name, and attempts to understand the method’s purpose in the context
	// of the persisted object (a TacoOrder, in this case). In essence, Spring Data
	// definesTacoOrdera sort of miniature domain-specific language (DSL) where
	// persistence details are expressed in repository method signatures.

	// Spring Data knows that this method is intended to find Orders because
	// CrudRepository is parameterized with TacoOrer.
	// The method name, 'findByDeliveryZip()', indicates that this method should
	// find all TacoOrder entities by matching their 'deliveryZip' property with the
	// value passed in as a parameter to the method.

	// Repository methods are composed of a verb, an optional subject, the word
	// 'By', and a predicate. In the case of 'findByDeliveryZip()', the verb is
	// 'find' (Spring Data also understands 'read', 'get', 'find' as synonymous for
	// fetching one or more entities. Alternatively, word 'count' can be used as the
	// verb if you only want the method to return an 'int' with the count of
	// matching entities.);
	// Predicate is 'DeliveryZip';
	// The subject isn’t specified and is implied to be a 'TacoOrder'.
	// Spring Data ignores most words in a subject, it will find entities
	// of type that CrudRepository interface is parameterized with.

	// Although the naming convention can be useful for relatively simple queries,
	// it doesn’t take much imagination to see that method names could get out of
	// hand for more-complex queries. In that case, feel free to name the method
	// anything you want and annotate it with '@Query' to explicitly specify the
	// query to be performed when the method is called.
	// Custom query methods work with Spring Data JPA and also with Spring Data
	// JDBC, but with two key differences!

	// For more information on custom query methods consult:
	// Spring in Action, Sixth Edition by Craig Walls;
	// Chapter 3: Working with data;
	// page 89;
	// Book version: MEAP;

}
