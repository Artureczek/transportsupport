package main;

public class CreateUserMethods {

	private String name;
	private String surname;
	private String email;
	private String username;
	private String password;
	private String repeatedPassword;
	
	
	public CreateUserMethods(String _name, String _surname, String _email, String _username, String _password, String _repeatedPassword)
	{
		this.name = _name;
		this.surname = _surname;
		this.email = _email;
		this.username = _username;
		this.password = _password;
		this.repeatedPassword = _repeatedPassword;
	}
	
	public boolean Validate(){

		return true;
		
	}
	
	
	
	
	
}
