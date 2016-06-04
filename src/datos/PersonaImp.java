package datos;

import java.io.Serializable;
import java.util.GregorianCalendar;

/*
 * Clase Persona
 * **************
 * Restricciones:
 * 		-La Fnacimiento no puede ser posterior a la actual.
 *		-El dni debe tener 9 caracteres.
 *		-El telefono debe tener 9 caracteres.
 *		-El genero debe ser M o H
 * 
 * 
 * Metodos anadidos:
 * 	String personatoCadena()
 * 	int obtenerEdad()
 * 
 * 
 * 
 * Metodos heredados:
 * equals()
 * String toString()
 * int hashCode()
 * PersonaImp clone()
 */

public class PersonaImp implements Serializable, Persona,Cloneable, Comparable<PersonaImp> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1535886074536612368L;
	//Atributos compartidos
	
	//Atributos basicos
	private String nombre;
	private String apellido;
	private String dni;
	private char genero;
	private String telefono;
	private String direccion;
	private GregorianCalendar fNacimiento;

	//constructores ordinarios
	public PersonaImp(){
		this.nombre=" ";
		this.apellido=" ";
		this.dni=" " ;
		this.genero=' ';
		this.telefono=" ";
		this.direccion=" " ;
		this.fNacimiento=new GregorianCalendar();
	}
	
	public PersonaImp(String nombre, String apellido, GregorianCalendar fNacimiento, String dni, char genero){
		this();
		GregorianCalendar fActual=new GregorianCalendar();
		if(fNacimiento.compareTo(fActual)<0){
			if(dni.length()==9){
				if(Character.toUpperCase(genero)=='M' || Character.toUpperCase(genero)=='H'){
					this.nombre=nombre;
					this.apellido=apellido;
					this.fNacimiento=fNacimiento;
					this.dni=dni;
					this.genero=genero;
				}
			}
		}
	}
	public PersonaImp(String nombre,String apellido,GregorianCalendar fNacimiento,String dni, char genero, String direccion, String telefono){
		this();
		GregorianCalendar fActual=new GregorianCalendar();
		if(fNacimiento.compareTo(fActual)<0){
			if(dni.length()==9){
				if(Character.toUpperCase(genero)=='M' || Character.toUpperCase(genero)=='H'){
					if(telefono.length()==9){
						this.nombre=nombre;
						this.apellido=apellido;
						this.fNacimiento=fNacimiento;
						this.dni=dni;
						this.genero=genero;
						this.telefono=telefono;
						this.direccion=direccion;
					}
				}
			}
		}
	}
	//constructor de copia
	public PersonaImp(PersonaImp p){
		this.nombre=p.nombre;
		this.apellido=p.apellido;
		this.fNacimiento=p.fNacimiento;
		this.dni=p.dni;
		this.genero=p.genero;
		this.telefono=p.telefono;
		this.direccion=p.direccion;
	}


	
	//metodos consultores

	


	@Override
	public String getNombre()
	{
		return nombre;
	}
	
	
	@Override
	public String getApellido()
	{
		return apellido;
	}
	
	@Override
	public GregorianCalendar getFNacimiento(){
		return this.fNacimiento;
	}
	
	@Override
	public char getGenero(){
		return genero;
	}
	
	@Override
	public String getDni()
	{
		return dni;
	}
	
	@Override
	public String getTelefono()
	{
		return telefono;
	}

	@Override
	public String getDireccion()
	{
		return direccion;
	}
	


	
	//metodos modificadores
	
	@Override
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	@Override
	public void setApellido(String apellido){
		this.apellido=apellido;
	}
	@Override
	public void setDni(String dni) throws PersonaNoValida{
		if(dni.length()!=9){
			throw new PersonaNoValida("El dni debe tener 9 cifras");
		}else{
			this.dni=dni;
		}
	}
	@Override
	public void setGenero(char genero) throws PersonaNoValida{
		if(Character.toUpperCase(genero)!='H' && Character.toUpperCase(genero)!='M'){
			throw new PersonaNoValida("El genero debe ser H O M");
		}else{
			this.genero=genero;
		}
	}
	
	@Override
	public void setTelefono(String telefono) throws PersonaNoValida
	 {
		if (telefono.length()!=9)
		{
			throw new PersonaNoValida("el telefono debe tener 9 caracteres");
		}
		else
		{
			this.telefono= telefono;
		}
	 }
	 

	@Override
	public void setDireccion(String direccion)
	 {
		 this.direccion= direccion;
	 }
	 @Override
	 public void setFNacimiento(GregorianCalendar fNacimiento) throws PersonaNoValida{
		 GregorianCalendar fActual=new GregorianCalendar();
		 if(this.fNacimiento.compareTo(fActual)>0){
			 throw new PersonaNoValida("La fecha de nacimiento debe ser anterior a la actual");
		 }else{
			 this.fNacimiento=fNacimiento;
		 }
	 }
	 //Metodos añadidos
		/*personatoCadena()
		 * Breve comentario: 
		 * 		el método devuelve un String con los valores de todos los atributos del persona.
		 * Cabecera:
		 * 		String personatoCadena()
		 * precondiciones:
		 * 		 nada
		 * entrada:
		 * 		 nada
		 * salida:
		 * 		 un String
		 * postcondiciones:
		 * 		 el String retornara asociado  al nombre -> Funcion
		 */
		
		public String personatoCadena(){
			String s;
			String fecha=Integer.toString(this.fNacimiento.get(GregorianCalendar.DAY_OF_MONTH))+"/"+Integer.toString(this.fNacimiento.get(GregorianCalendar.MONTH))+"/"+Integer.toString(this.fNacimiento.get(GregorianCalendar.YEAR));
			s=(this.nombre+","+this.apellido+","+fecha+","+this.dni+","+this.genero+","+this.direccion+","+this.telefono);
			return (s);
		}
		/*	obtenerEdad
		 * -Breve Comentario:
		 * 	 Este metodo recibe un objeto GregorianCalendar y lo compara con la fecha actual
		 * 		y retornara su edad.
		 * 
		 * Cabecera:
		 * 	int obtenerEdad(GregorianCalendar fNacimiento)
		 * Precondiciones:
		 * 	nada, si la fechaNacimiento es posterior a la actual retornará -1
		 * Entradas:
		 * 	un objeto GregorianCalendar
		 * Salidas:
		 * 	un entero(edad)
		 * Postcondiciones:
		 * 	el entero retornara asociado al nombre -> FUNCION
		 * */
		public int obtenerEdad(){
			GregorianCalendar fActual=new GregorianCalendar();
			
			int edad=-1;
			//Si la fecha de nacimiento es posterior a la actual, retornará 0.
			if(fNacimiento.compareTo(fActual)<0){
				edad=fActual.get(GregorianCalendar.YEAR)-fNacimiento.get(GregorianCalendar.YEAR);
				//Si el mes de nuestro nacimiento es mayor o igual**(Siguiente Condicion)
				//quiere decir que tenemos un año menos porque aun no hemos llegado a nuestro cumpleaños
				if((fNacimiento.get(GregorianCalendar.MONTH))>=
						(fActual.get(GregorianCalendar.MONTH))){
					//Si el mes es el mismo , y el dia de nuestro nacimiento es posterior
					//al dia actual quiere decir que aun no hemos cumplido años
					//entonces le tenemos que restar uno a nuestra Edad
					//O el mes de nuestra fecha de nacimiento es mayor a el de la fecha actual
					//tambien tenemos que restarle 1 a la edad.
					if((fNacimiento.get(GregorianCalendar.MONTH))==
							(fActual.get(GregorianCalendar.MONTH)) 
							&& 
							((fNacimiento.get(GregorianCalendar.DAY_OF_MONTH))>
							(fActual.get(GregorianCalendar.DAY_OF_MONTH)))
							||(fNacimiento.get(GregorianCalendar.MONTH)> fActual.get(GregorianCalendar.MONTH))
							){
								edad--;
					}
				}
			}
			return edad;
		}
		/*----FIN METODOS AÑADIDOS----*/
	 //Metodos heredados

	 //Metodo toString
	 @Override
	 public String toString()
	 {
		 String fecha=Integer.toString(this.fNacimiento.get(GregorianCalendar.DAY_OF_MONTH))+"/"+Integer.toString(this.fNacimiento.get(GregorianCalendar.MONTH))+"/"+Integer.toString(this.fNacimiento.get(GregorianCalendar.YEAR));
		 String s= ("\nNombre: "+this.getNombre()+"\nApellido: "+this.getApellido()+"\nFecha de nacimiento: "+fecha+"\nGenero: "+this.getGenero()+"\nDni: "+this.getDni()+"\nTelefono: "+this.getTelefono()+"\nDireccion: "+this.getDireccion());
		 return s;
	 }
	 
	 //Metodo hashCode
	 @Override
	 public int hashCode()
	 {
		 return ((int)(Integer.parseInt(telefono)*1/2));
	 }
		/* equals
		 * Criterio de igualdad: Dos Personas son iguales si su dni son iguales.
	 	 * */
	@Override
	public boolean equals(Object o) {
		boolean igual = false;
		if (o != null && o instanceof PersonaImp) {
			PersonaImp p = (PersonaImp) o;
			if (p.dni == this.dni) {
				igual = true;
			}

		}

		return igual;
	}
	
	@Override
	public PersonaImp clone(){
	PersonaImp p=null;
		try{
			p=(PersonaImp) super.clone();
		}catch(CloneNotSupportedException cnse){
			System.out.println(cnse);
		}
		return p;
	}
	/*compareTo
	 * 
	 * Criterio de comparacion:
	 * 		La persona se comparará por la Fnacimiento.
	 * 			Si la fecha de nacimiento es mayor a 
	 * 			la fecha que nos pasan por parametros  retornara 1;
	 * 				si es menor -1, 0 si son iguales.
	 * 			 
	 * */
	@Override
	public int compareTo(PersonaImp p){
		int comparada=0;
		if(this.fNacimiento.compareTo(p.fNacimiento)<0){
			 comparada= -1;
		}
		else if (this.fNacimiento.compareTo(p.fNacimiento)>0){
			comparada = 1;
		}
			
		return comparada;
		
	}



	

}//fin clase alumno

