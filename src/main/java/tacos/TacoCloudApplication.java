package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import tacos.data.IngredientRepository;
import tacos.model.bean.Ingredient;
import tacos.model.bean.IngredientType;

/**
 * This is the main class that will be executed when that JAR file is run. This
 * class has a a minimal amount of Spring configuration to bootstrap the
 * application.<br>
 * The <tt>@SpringBootApplication</tt> annotation clearly signifies that this is
 * a Spring Boot application. <tt>@SpringBootApplication</tt> is a composite
 * annotation that combines three other annotations:
 * <ul>
 * <li><tt>@SpringBootConfiguration</tt> — Designates this class as a
 * configuration class. If needed, Java-based Spring Framework configuration can
 * be added to this class. This annotation is, in fact, a specialized form of
 * the <tt>@Configuration</tt> annotation.</li><br>
 * <li><tt>@EnableAutoConfiguration</tt> - Enables Spring Boot automatic
 * configuration. This annotation tells SpringBoot to automatically configure
 * any components that it might need.</li><br>
 * <li><tt>@ComponentScan</tt> - Enables component scanning. This allows
 * declaring other classes with annotations like
 * <tt>@Component, @Controller, @Service</tt> and others, to have Spring
 * automatically discover them and register them as components in the Spring
 * application context.</li>
 * </ul>
 * The other important part of the <code>TacoCloudApllication</code> class is
 * the <code>main</code> method. This is the method that will be run when the
 * JAR file is executed. <br>
 * <br>
 * 
 * @author Dejan Smiljić <dej4n.s@gmail.com>
 *
 */
@SpringBootApplication
public class TacoCloudApplication {

	/**
	 * 
	 * @param args
	 * @implNote <code>main</code> method calls a static <code>run</code> method on
	 *           the SpringApplication class, which performs the actual
	 *           bootstrapping of the application, creating the Spring application
	 *           context. The two parameters passed to the <code>run</code> method
	 *           are a configuration class and the command-line arguments. Although
	 *           it’s not necessary that the configuration class passed to
	 *           <code>run</code> be the same as the bootstrap class, this is the
	 *           most convenient and typical choice.
	 */
	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}

	/*
	 * If there’s a file named schema.sql in the root of the application’s
	 * classpath, then the SQL in that file will be executed against the database
	 * when the application starts. Therefore, a file named schema.sql should be
	 * placed in the src/main/resources folder (src/main/resources/schema.sql).
	 * ---------------------------------------------------------------------------
	 * You also need to preload the database with some ingredient data. Application
	 * at its current state uses CommandLineRunner functional interface to preload
	 * data to database. What’s convenient about using CommandLineRunner (or
	 * ApllicationRunner) interface is that they are using the repositories to
	 * create the persisted objects instead of an SQL script. This means that
	 * they’ll work equally well for relational databases or non-relational
	 * databases.
	 * ---------------------------------------------------------------------------
	 * Spring Boot will also execute a file named data.sql from the root of the
	 * classpath when the application starts. Therefore, you can load the database
	 * with ingredient data using the data.sql file. If you want to use this file to
	 * preload the database, comment-out or delete CommandLineRunner dataLoader
	 * method and place data.sql file in /src/main/resources/ folder. File is in
	 * src/main/resources/lib folder. Set: spring.jpa.hibernate.ddl-auto=none in
	 * application.properties;
	 */

	@Bean
	public CommandLineRunner ingredientDataLoader(IngredientRepository repo) {
		return args -> {
			repo.save(new Ingredient("FLTO", "Flour Tortilla", IngredientType.WRAP));
			repo.save(new Ingredient("COTO", "Corn Tortilla", IngredientType.WRAP));
			repo.save(new Ingredient("GRBF", "Ground Beef", IngredientType.PROTEIN));
			repo.save(new Ingredient("CARN", "Carnitas", IngredientType.PROTEIN));
			repo.save(new Ingredient("TMTO", "Diced Tomatoes", IngredientType.VEGGIES));
			repo.save(new Ingredient("LETC", "Lettuce", IngredientType.VEGGIES));
			repo.save(new Ingredient("CHED", "Cheddar", IngredientType.CHEESE));
			repo.save(new Ingredient("JACK", "Monterrey Jack", IngredientType.CHEESE));
			repo.save(new Ingredient("SLSA", "Salsa", IngredientType.SAUCE));
			repo.save(new Ingredient("SRCR", "Sour Cream", IngredientType.SAUCE));
		};
	}

}
