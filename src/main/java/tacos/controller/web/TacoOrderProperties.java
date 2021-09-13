package tacos.controller.web;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Purpose of <tt>TacoOrderProperties</tt> is to be holder of configuration data
 * (Configuration property holder bean). It makes it easy to share common
 * configuration properties among several beans that may make use of that
 * information.<br>
 * Prefix attribute of the <tt>@ConfigurationProperties</tt> annotation is set
 * to <tt>taco.orders</tt>, so that when setting the <tt>pageSize</tt> external
 * property (from a .properties file), a configuration property named
 * <tt>taco.orders.pageSize</tt> has to be used.<br>
 * Validation constraints are applied to <tt>pageSize</tt> property to limit its
 * minimum and maximum values.
 * 
 * @author Dejan Smiljić <dej4n.s@gmail.com>
 *
 */
@Component
@ConfigurationProperties(prefix = "taco.orders")
@Validated
public class TacoOrderProperties {

	// pageSize property defaults to 20. It can be changed to arbitrary value by
	// setting a 'taco.orders.pageSize' property in 'application.properties' file.
	@Min(value = 5, message = "must be between 5 and 50")
	@Max(value = 50, message = "must be between 5 and 50")
	private int pageSize = 20;

	// constructors
	public TacoOrderProperties() {
	}

	public TacoOrderProperties(int pageSize) {
		this.pageSize = pageSize;
	}

	// getters & setters | accessors & mutators
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "TacoOrderProperties [pageSize=" + pageSize + "]";
	}

}
