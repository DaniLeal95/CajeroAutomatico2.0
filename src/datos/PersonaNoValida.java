package datos;


public class PersonaNoValida extends Exception{
	
	public PersonaNoValida(){
		super();
	}
	
	public PersonaNoValida(String e){
		super(e);
	}
	
}
