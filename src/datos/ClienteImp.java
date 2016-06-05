package datos;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Vector;

import gestionyutilidades.GestionFicheros;
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
	 * 		-Este metodo recoge el total de todo el saldo de sus cuentas
	 * 		y retornara un prestigio segun el total:
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
	 * 		Una cadena  Prestigio 
	 * 	Postcondiciones:
	 * 		El prestigio retornara asociado al nombre -> Funcion
	 * */
	//Resguardo
	/*public String getPrestigio(){
		String prestigio=null;
		double totalCuenta=0;
	
		return prestigio;
	}*/
	public String getPrestigio(String ficheromaestro,String ficheromovimiento){
		String prestigio=null;
		double dineroTotal=0;
		GestionFicheros gf=new GestionFicheros();
		
		File fmae=new File(ficheromaestro);
		File fmov=new File(ficheromovimiento);
		
		FileInputStream fismae=null;
		FileInputStream fismov=null;
		
		ObjectInputStream oismae=null;
		ObjectInputStream oismov=null;
		
		
		try{
			//si el fichero de movimiento no existe solo tenenmos que mirar en el fichero maestro
			if(!fmov.exists()){	
				fismae=new FileInputStream(fmae);
				oismae= new ObjectInputStream(fismae);
				
				
				for(int i=0;i<gf.contarRegistros(ficheromaestro);i++){
					CuentaImp aux=(CuentaImp)oismae.readObject();
					if(aux.getidCliente()== this.idCliente){
						dineroTotal=dineroTotal+aux.getSaldo();
					}
				}
			}
			else{
				//Si el fichero de movimiento existe primero miraremos en el de movimiento
				fismov=new FileInputStream(fmov);
				oismov=new ObjectInputStream(fismov);
				
				
				for(int i=0;i<gf.contarRegistros("CuentasMovimiento.dat");i++){
					CuentaImp aux=(CuentaImp) oismov.readObject();
					if(aux.getidCliente()== this.idCliente){
					}
				}
					//Ahora leemos del fichero maestro
					fismae= new FileInputStream(fmae);
					oismae=new ObjectInputStream(fismae);
					
					
					for(int i=0;i<gf.contarRegistros(ficheromaestro);i++){
						
						CuentaImp aux=(CuentaImp) oismae.readObject();
						if(aux.getidCliente()== this.idCliente){
							dineroTotal=dineroTotal+aux.getSaldo();
						}
					}
					
				
			}
		}catch(EOFException eofe){
			
		}catch (ClassNotFoundException cnfe){
			System.out.println(cnfe);
		}catch (IOException ioe) {
			System.out.println(ioe);
		}
		//cerramos ficheros.
		finally{
			try{
				if(oismov!=null){
					oismov.close();
					fismov.close();
				}
				if(oismae!=null){
					oismae.close();
					fismae.close();
				}
			}catch(IOException ioe){
				System.out.println(ioe);
			}
		}
		
		if(dineroTotal >= 20000)
			prestigio="Buena";
		else 
			if(dineroTotal > 0 && dineroTotal<20000)
				prestigio="Normal";
			else
				prestigio="Mala";
			
		return prestigio;
		}

	
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
		return "Nombre cliente: "+getNombre()+", IdCliente: " + idCliente + "\n observaciones: " + observaciones;
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

