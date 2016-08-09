public class AddEmployeeMethods {

	
	public String name;
	public String surname;
	public String salary;
	public String pesel;
	public String birthDate;
	
	
	public AddEmployeeMethods(String _name, String _surname, String _salary, String _pesel, String _birthDate)
	{
		this.name = _name;
		this.surname = _surname;
		this.salary = _salary;
		this.pesel = _pesel;
		this.birthDate = _birthDate;
	}
	
	
	public boolean Validate(){
		return true;
	}
	
	
	
	
}
