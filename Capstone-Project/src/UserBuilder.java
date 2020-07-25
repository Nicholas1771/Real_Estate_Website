
public class UserBuilder {
	
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String password;
	private boolean verified;
	
	
	public UserBuilder setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	
	public UserBuilder setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	
	public UserBuilder setUsername(String username) {
		this.username = username;
		return this;
	}
	
	public UserBuilder setEmail(String email) {
		this.email = email;
		return this;
	}
	
	public UserBuilder setPassword(String password) {
		this.password = password;
		return this;
	}
	
	public UserBuilder setVerified(Boolean verified) {
		this.verified = verified;
		return this;
	}
	
	/*
	 * this method creates the User
	 */
	public User createUser() {
		return new User(firstName,lastName,username,email,password,verified);
	}

}
