package datos;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Vector;

import gestionyutilidades.Utilidades;


/*
 * Restricciones:
 * 		Nada
 * Metodos aniadidos:
 ********************
 *		String getPrestigio()
 *		String cuentatoCadena()
 * 		void addCuenta(CuentaImp c)
 * 		void deleteCuenta(long numCuenta)
 *
 * Metodos heredados:
 ********************
 * 		public String toString()
 * 		public int compareTo(ClienteImp c)
 * 		public ClienteImp clone()
 * 		public boolean equals(Object o)
 * 		public int hashCode()
 * 
 * 
 * 
 * */

public class ClienteImp extends PersonaImp implements Cliente,Cloneable,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2995685764709883913L;
	/*-----------------------*/
	/*Atributos bï¿½sicos*/
	private long idCliente;
	private String observaciones;
	private String contraseña;

	
	/*Atributos Compartidos*/
	public static long contadorclientes=0;
	/*----------------------*/
	
	/*Constructores ordinarios*/
	public ClienteImp(){
		super();
		Utilidades u=new Utilidades();
		/*Estas 4 lineas son para obtener el idCliente*/
		
		if(contadorclientes!=0){idCliente=contadorclientes+1;}
		else{idCliente=u.cogerUltimaId("idclientes.dat")+1;}
		contadorclientes=idCliente;
		u.escribirUltimaId(idCliente,"idclientes.dat");
		
		/*-------------------------------------------*/
		this.observaciones=" ";
		this.contraseña=" ";
	}
	
	public ClienteImp(String nombre,String apellido,GregorianCalendar fnacimiento,String dni,char genero){
		super(nombre,apellido,fnacimiento,dni,genero);
		Utilidades u=new Utilidades();
		/*Estas 4 lineas son para obtener el idCliente*/
		if(contadorclientes!=0){idCliente=contadorclientes+1;}
		else{idCliente=u.cogerUltimaId("idclientes.dat")+1;}
		contadorclientes=idCliente;
		u.escribirUltimaId(idCliente,"idclientes.dat");
		/*-------------------------------------------*/
		this.observaciones=" ";
		this.contraseña=" ";
	}
	
	public ClienteImp(String nombre,String apellido,GregorianCalendar fnacimiento,String dni,char genero,String observaciones,String contraseña){
		super(nombre,apellido,fnacimiento,dni,genero);
		Utilidades u=new Utilidades();
		/*Estas 4 lineas son para obtener el idCliente*/
		if(contadorclientes!=0){
			idCliente=contadorclientes+1;
			}
		else{
			idCliente=u.cogerUltimaId("idclientes.dat")+1;
			}
		contadorclientes=idCliente;
		u.escribirUltimaId(idCliente,"idclientes.dat");
		/*-------------------------------------------*/
		this.observaciones=observaciones;
		this.contraseña=contraseña;
	}
	
	//constructor de copia
	public ClienteImp(ClienteImp c){
		super(c.getNombre(),c.getApellido(),c.getFNacimiento(),c.getDni(),c.getGenero());
		this.idCliente=c.idCliente;
		this.observaciones=c.observaciones;
		this.contraseña=c.contraseña;
	}
	
	/*----------------*/
	
	
	@Override
	public long getIdCliente(){
		return this.idCliente;
	}
	
	
	@Override
	public String getObservaciones(){
		return this.observaciones;
	}

	@Override
	public String getContraseña(){
		return this.contraseña;
	}

	/*METODOS CONSULTORES REDEFINIDOS*/
	@Override
	public String getNombre(){
		return (super.getNombre());
	}
	
	@Override
	public String getApellido(){
		return(super.getApellido());
	}
	@Override
	public String getDni(){
		return(super.getDni());
	}
	@Override
	public GregorianCalendar getFNacimiento(){
		return (super.getFNacimiento());
	}
	@Override
	public char getGenero(){
		return(super.getGenero());
	}
	/**---------------
	 * Modificadores-
	 * --------------
	 ***/
	

	@Override
	public void setObservaciones(String observaciones){
		this.observaciones=observaciones;
	}
	@Override
	public void setContraseña(String contraseña){
		this.contraseña=contraseña;
	}

	/*METODOS MODIFICADORES REDEFINIDOS*/
	@Override
	public void setNombre(String nombre){
		super.setNombre(nombre);
	}
	@Override
	public void setApellido(String apellido){
		super.setApellido(apellido);
	}
	@Override
	public void setDni(String dni)throws PersonaNoValida{
		super.setDni(dni);
	}
	@Override
	public void setGenero(char genero) throws PersonaNoValida{
		super.setGenero(genero);
	}
	@Override
	public void setFNacimiento(GregorianCalendar fNacimiento) throws PersonaNoValida{
		super.setFNacimiento(fNacimiento);
	}
	/*Metodos aï¿½adidos*/

	



	
	
	/*getPrestigio
	 * 
	 * Breve comentario:
	 * 		-Este metodo harÃ¡ la media de todos los saldos de sus cuentas
	 * 		y retornara un prestigio segun la media:
	 * 				-Media mayor 20.000â‚¬ -> Buena
	 * 				-Media entre 0â‚¬ y 19.999â‚¬ -> Normal
	 * 				-Media menor a 0â‚¬ -> Mala
	 * 				-Si no tiene Cuentas -> NULL
	 * 	Cabecera: 
	 * 		String getPrestigio()
	 * 	Precondiciones:
	 * 		Nada 	
	 * 	Entradas:
	 * 		Nada
	 * 	Salidas:
	 * 		Un  Prestigio (String)
	 * 	Postcondiciones:
	 * 		El prestigio retornara asociado al nombre -> Funcion
	 * */
	
	/*public String getPrestigio(){
		String prestigio=null;
		double totalCuenta=0;
			for(int i=0;i<this.cuentas.size();i++){
				totalCuenta=totalCuenta+this.cuentas.elementAt(i).getSaldo();
			}
			double media=totalCuenta/this.cuentas.size();
			
			
			if(media<0.0){
				prestigio="Mala";
			}
			else if(media>=20000.0){
				prestigio="Buena";
			}
			else if(media>0.0 && media<20000.0){
				prestigio="Normal";
			}
		
		return prestigio;
	}*/

	
	/*ClientetoCadena
	 * Breve comentario: 
	 * 		el mÃ©todo devuelve un String con los valores de todos los atributos del cliente.
	 * Cabecera:
	 * 		String clientetoCadena()
	 * precondiciones:
	 * 		 nada
	 * entrada:
	 * 		 nada
	 * salida:
	 * 		 un String
	 * postcondiciones:
	 * 		 el String retornara asociado  al nombre -> Funcion
	 */
	public String clientetoCadena(){
		
		
		return getIdCliente()+", "+getNombre()+", "+getApellido()+", "+getDni()+", "+getGenero()+", "+ getObservaciones();
	}

	/*-------FIN METODOS AÃ‘ADIDOS---------*/
	
	
	/*--------METODOS HEREDADOS-----------*/

	@Override
	public String toString() {
		return "Nombre cliente: "+getNombre()+", IdCliente: " + idCliente + "\n observaciones: " + observaciones + "\n\n--------------------------------";
	}
	@Override
	public int hashCode(){
		return (int)(idCliente+1/2*Integer.parseInt(contraseña));
	}
	
	/*
	 * CompareTo
	 * 	Criterio de comparacion: 
	 * 		El Cliente es comparado por su id de cliente, retornarÃ¡ 1 si es mayor,
	 * 															   -1 si es menor,
	 * 																0 si son iguales.
	 * */
	public int compareTo(ClienteImp c) {
		int comparado=0;
		if(this.idCliente>c.getIdCliente()){
			comparado=1;
		}
		else if (this.idCliente<c.getIdCliente()){
			comparado=-1;
		}
		return comparado;
	}
	
	@Override
	public ClienteImp clone(){
		ClienteImp copia=null;
		
		copia = (ClienteImp) super.clone();
		copia.setContraseña(this.getContraseña());
		copia.setObservaciones(this.getObservaciones());
		copia.idCliente=this.idCliente;
		
		return copia;
	}
	
	/* equals
	 * Criterio de igualdad: Dos clientes son iguales si su idCliente son iguales.
 	 * */
	@Override
	public boolean equals(Object o){
		boolean igual=false;
		if(o!=null && o  instanceof ClienteImp) {
			ClienteImp c = (ClienteImp)  o;
				if(c.idCliente == this.idCliente){
					igual = true;
				}
			
		}
			
		
		
		return igual;
	}
	
	
	
/*-------FIN METODOS HEREDADOS---------*/
	
	
}

