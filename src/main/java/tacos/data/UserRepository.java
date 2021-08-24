package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.model.bean.User;

/**
 * @author Dejan Smiljić <dej4n.s@gmail.com>
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);

}
