package henryloadtesting.henryloadtesting.userserviceexecutor;

public class UserEntity {
	
	private String userName;
    private String email;
    private String password;
    private boolean active;
    private String roles;
    
 // Constructor
    
    public UserEntity() {
        // Default constructor is required for Jackson deserialization
    }
    
    
	public UserEntity(String userName, String email, String password, boolean active, String roles) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.active = active;
		this.roles = roles;
	}

	
	// getters, and setters
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	

    
	
    

}
