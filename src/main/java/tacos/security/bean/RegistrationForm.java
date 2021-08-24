package tacos.security.bean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.password.PasswordEncoder;

import tacos.model.bean.User;

/**
 * @author Dejan Smiljić <dej4n.s@gmail.com>
 *
 */
public class RegistrationForm {

	@NotBlank(message = "Username is required")
	@Size(min = 3, message = "Username must be at least 3 characters long")
	private String username;
	@NotBlank(message = " * Password is required")
	private String password;
	@NotBlank(message = " * Passwords do not match")
	private String confirm;
	@NotBlank(message = " * Full name is required")
	private String fullname;
	@NotBlank(message = " * Street is required")
	private String street;
	@NotBlank(message = " * City is required")
	private String city;
	@NotBlank(message = " * State is required")
	private String state;
	@NotBlank(message = " * Zip code is required")
	private String zip;
	@NotBlank(message = " * Phone number is required")
	private String phoneNumber;

	// constructors
	public RegistrationForm() {
	}

	public RegistrationForm(String username, String password, String fullname, String street, String city, String state,
			String zip, String phoneNumber) {
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNumber = phoneNumber;
	}

	// getters & setters | accessors & mutators
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		matchPassword();
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
		matchPassword();
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}// getters & setters END

	// custom | helper methods
	/**
	 * Creates a new <code>User</code> object.
	 * 
	 * @param passwordEncoder
	 * @return <b>User</b> object
	 */
	public User toUser(PasswordEncoder passwordEncoder) {
		return new User(username, passwordEncoder.encode(password), fullname, street, city, state, zip, phoneNumber);
	}

	// triggers validation if passwords do not match
	private void matchPassword() {
		if (this.password == null || this.confirm == null) {
			return;
		} else if (!this.password.equals(confirm)) {
			this.confirm = null;
		}
	}

	@Override
	public String toString() {
		return String.format(
				"RegistrationForm [username=%s, password=%s, confirm=%s, fullname=%s, street=%s, city=%s, state=%s, zip=%s, phoneNumber=%s]",
				username, password, confirm, fullname, street, city, state, zip, phoneNumber);
	}

}
