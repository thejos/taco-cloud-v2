package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.model.bean.Ingredient;

/**
 * The CrudRepository interface provides sophisticated CRUD functionality for
 * the entity class that is being managed. Extending CrudRepository exposes a
 * complete set of methods to manipulate your entities.<br>
 * <br>
 * There’s no need to write method implementation. When the application starts,
 * Spring Data will automatically generate an implementation. This means the
 * repositories are ready to use from the get-go. <br>
 * <br>
 * Interface that extends CrudRepositry can be customized by adding method
 * declarations.
 * 
 * @author Dejan Smiljić <dej4n.s@gmail.com>
 *
 */
public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
