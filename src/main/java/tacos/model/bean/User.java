package tacos.model.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Implementation of <code>UserDetails</code> will provide some essential user
 * information to the framework, such as what authorities are granted to the
 * user and whether the user’s account is enabled or not.
 * 
 * @author Dejan Smiljić <dej4n.s@gmail.com>
 *
 */
@Entity
public class User implements UserDetails, Serializable {

	private static final long serialVersionUID = 4L;

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "U_SEQ_GEN", sequenceName = "user_sequence_generator", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "U_SEQ_GEN")
	private Long id;
	private String username;
	private String password;
	private String fullname;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String phoneNumber;

	// constructors
	public User() {
	}

	public User(String username, String password, String fullName, String street, String city, String state, String zip,
			String phoneNumber) {
		this.username = username;
		this.password = password;
		this.fullname = fullName;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNumber = phoneNumber;
	}

	// getters & setters | accessors & mutators
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullname;
	}

	public void setFullName(String fullName) {
		this.fullname = fullName;
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
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}// getters, setters END

	// implement UserDetails interface methods
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * Returns a collection of granted authorities.<br>
	 * Returns a Collection indicating that all users will have been granted
	 * ROLE_USER authority.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	/*
	 * Following implemented methods return a boolean to indicate whether or not the
	 * user’s account is enabled or expired. Taco-Cloud has no need to disable
	 * users, so all methods return true to indicate that the users are active.
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}// implement UserDetails

	@Override
	public String toString() {
		return String.format(
				"User [id=%s, username=%s, password=%s, fullname=%s, street=%s, city=%s, state=%s, zip=%s, phoneNumber=%s]",
				id, username, password, fullname, street, city, state, zip, phoneNumber);
	}

}
