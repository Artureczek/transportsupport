public class AddCarMethods {

	public String marka;
	public String model;
	public String silnik;
	public String pojemnoscBaku;
	public String pojemnoscLadowni;
	public String spalanie;
	
	public AddCarMethods(String _marka, String _model, String _silnik, String _pojemnoscBaku, String _pojemnoscLadowni, String _spalanie)
	{
		this.marka = _marka;
		this.model = _model;
		this.silnik = _silnik;
		this.pojemnoscBaku = _pojemnoscBaku;
		this.pojemnoscLadowni = _pojemnoscLadowni;
		this.spalanie = _spalanie;
	}
	
	public boolean Validate(){
		return true;
	}
	
	
}
