package system;

public class User {
	private String userID;
	private String name;
	private String email;
	private String password;
	private String phoneNumber;
	
	User(String name,String email, String password, String phoneNumber){
		this.name = name;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
	}
	

    private String generateUserID() {
        return "USER" + System.currentTimeMillis();
    }

  
    public String getUserID() {
        return userID;
    }
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	} 
	
}
