package datos;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import cajero.TarjetaExcepcion;
import gestionyutilidades.GestionFicheros;
import gestionyutilidades.Utilidades;

public class TarjetaImp implements Tarjeta,Serializable,Comparable<TarjetaImp>,Cloneable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2552394970522188468L;
	/* 
	 * Clase Implementada de Tarjeta 
	 * 
	 * Restricciones;
	 * 	El pin debe tener una longitud de 4 digitos.
	 * 
	 * 	Metodos añadidos
	 * 		String tarjetatoCadena()
	 * 		validarnumCuenta(long numCuenta,String ficheromaestro,String ficheromovimiento)
	 * 	
	 * 	Metodos heredados:
	 * 		int hashCode()
	 * 		boolean equals(Object o)
	 * 		int compareTo(TarjetaImp t)
	 * 		TarjetaImp clone()
	 * 		String toString()
	 * 
	 * */
	//basicas
	private char tipo;
	private long numtarjeta;
	private String pin;
	private long numCuenta;
	//compartidas
	private static long contadortarjeta=0;
	
	//constructores
	public TarjetaImp(){
		Utilidades u=new Utilidades();
		if(contadortarjeta!=0){
			numtarjeta=contadortarjeta+1;	
		}
		else{ 
			numtarjeta=u.cogerUltimaId("idtarjeta.dat")+1;
		}
		contadortarjeta=numtarjeta;
		u.escribirUltimaId(numtarjeta,"idtarjeta.dat");
		tipo=' ';
		pin="0000";
		numCuenta=1;
	}
	
	
	public TarjetaImp(char tipo,String pin, long numCuenta) {
		this();
		Utilidades u=new Utilidades();
		if(Character.toUpperCase(tipo)=='C' || Character.toUpperCase(tipo)=='D'){
			this.tipo=tipo;
		}
		if(u.validarPin(pin)){
			this.pin=pin;
		}
		if(validarnumCuenta(numCuenta, "CuentasMaestro.dat", "CuentasMovimiento.dat")){
			this.numCuenta=numCuenta;	
		}
		
		
	}
	/*Constructor de copia*/
	public TarjetaImp(TarjetaImp tp){
		this.tipo=tp.getTipo();
		this.numtarjeta=tp.numtarjeta;
		this.pin=tp.pin;
		this.numCuenta=tp.numCuenta;
	}
	/**------------------------------------------------------**/
	/*Consultores*/
	@Override
	public char getTipo() {
		return tipo;
	}
	@Override
	public String getPin(){
		return pin;
	}
	@Override
	public long getNumtarjeta() {
		return numtarjeta;
	}
	
	public long getNumTarjetas(){
		return contadortarjeta;
	}
	@Override
	public long getnumCuenta(){
		return this.numCuenta;
	}

	
	//modificadores
	@Override
	public void setTipo(char tipo) throws TarjetaExcepcion {
		
		if(Character.toUpperCase(tipo)=='C' || Character.toUpperCase(tipo)=='D'){
			this.tipo = tipo;
		}
		else {
			throw new TarjetaExcepcion("Solo puede ser de Credito o de Debito");
		}
	}
	@Override
	public void setPin(String pin) throws TarjetaExcepcion {
		Utilidades u=new Utilidades();
		if(!u.validarPin(pin))
			throw new TarjetaExcepcion("El pin introducido es incorrecto");
		else
			this.pin=pin;
	}
	
	@Override
	public void setnumCuenta(long numCuenta) throws TarjetaExcepcion{
		if(validarnumCuenta(numCuenta, "CuentasMaestro.dat", "CuentasMovimiento.dat"))
			this.numCuenta=numCuenta;
		
		else 
			throw new TarjetaExcepcion("El numCuenta Introducido no aparece registrado");
	}
	
	/*
	*	Métodos añadidos
	*/
	
	/*
	 * Breve comentario:
	 * 	El metodo valida el numCuenta, consultando los ficheros de Cuentas que le pasamos por parametros 
	 * 	simulando como seria un Foreign Key en una base de datos.
	 * 	y retorna un boolean , true en caso de que sea posible utilizar ese numCuenta y false cuando no
	 * Cabecera:
	 * 	 boolean validarnumCuenta(long numCuenta, String ficheromaestro,String ficheromovimiento){
	 * Precondiciones:
	 * 	Almenos el fichero maestro debe existir, de no se asi saltara una excepcion
	 * 	
	 * Entradas:
	 * 		long numCuenta, y dos cadenas con los nombres de los ficheros
	 * Salidas:
	 * 		boolean
	 * Postcondiciones:
	 * 		boolean retornara asociado al nombre, Funcion.
	 * 
	 * */
	//RESGUARDO
//	public boolean validarnumCuenta(long numCuenta, String ficheromaestro,String ficheromovimiento){
//		boolean valida=false;
//		return valida;
//	}
	
	public boolean validarnumCuenta(long numCuenta, String ficheromaestro,String ficheromovimiento){
		boolean valida=false;
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
				
				CuentaImp aux=(CuentaImp)oismae.readObject();
				while(aux!=null && !valida){
					if(aux.getNumCuenta()== numCuenta){
						valida=true;
					}
					aux=(CuentaImp)oismae.readObject();
				}
			}
			else{
				//Si el fichero de movimiento existe primero miraremos en el de movimiento
				fismov=new FileInputStream(fmov);
				oismov=new ObjectInputStream(fismov);
				
				
				for(int i=0;i<gf.contarRegistros("CuentasMovimiento.dat") && !valida;i++){
					CuentaImp aux=(CuentaImp) oismov.readObject();
					if(aux.getNumCuenta()== numCuenta){
						valida=true;
					}
				}
				//si no se a encontrado en el fichero de movimiento , lo miraremos en el maestro
				if(!valida){
					fismae= new FileInputStream(fmae);
					oismae=new ObjectInputStream(fismae);
					CuentaImp aux=(CuentaImp) oismae.readObject();
					
					while(aux!=null && !valida){
						if(aux.getNumCuenta()== numCuenta){
							valida=true;
						}
						aux=(CuentaImp) oismae.readObject();
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
		
		
		return valida;
	}
	
	
	
	/*
	 *  comentario: 
	 *  	el método devuelve una cadena con los valores de todos 
	 *  		los atributos de la cuenta.
	 * cabecera: 
	 * 		String tarjetatoCadena()
	 *
	 * precondiciones:
	 * 		 nada
	 * entrada:
	 * 		 nada
	 * salida: 
	 * 		una cadena
	 * postcondiciones:
	 * 		 cadena asociada al nombre -> Funcion
	 */
	public String tarjetatoCadena()
	{
		return getNumtarjeta()+","+getTipo()+","+getPin();
	}
	
	/*-------FIN METODOS AÑADIDOS---------*/
	
	
	/*
	 * METODOS HEREDADOS
	 * 
	 * */
	
	//Metodos sobrescritos
	@Override
	public int hashCode() {
		return (int)(this.numtarjeta*1.2+1/2*this.numtarjeta);
	}
	
	/*
	 * Metodo equals
	 * 	Criterio de igualdad:Dos tarjetas son iguales si su numero de tarjeta y su tipo son iguales.
	 * */
	@Override
	public boolean equals(Object o){
		boolean igual=false;
		
		if(o!=null && o instanceof TarjetaImp) {
			TarjetaImp t = (TarjetaImp) o;
			if(this.numtarjeta==t.numtarjeta && this.tipo==t.tipo){
				igual=true;
			}
			
		}
		
		return igual;
		
	}
	
	/*CompareTo
	 * ********
	 *  Criterio de comparacion: Compara por numero de tarjeta, retornara 1 si es mayor, -1 si es menor y 0 cuando sean iguales
	 * */
	@Override
	public int compareTo(TarjetaImp t)
	{
		int compara = 0;
		if(this.numtarjeta<t.numtarjeta)
			compara = -1;
		else
			if(this.tipo>t.tipo)
				compara = 1;
		return compara;
	}
	
	/*Clone*/
	
	@Override
	public TarjetaImp clone()
	{
		TarjetaImp copia = null;
		try{
			copia = (TarjetaImp) super.clone();
		}
		catch(CloneNotSupportedException e){
			System.out.println(e);
		}
		return copia;
	}
	
	
	@Override
	public String toString() {
		String tip="Debito";
		if(tipo=='C')tip="Credito";	//esto simplemente es estetico, 
					//para que en vez que en el toString muestre un char nos muestre una cadena
		
		return "Numtarjeta: " + (numtarjeta) +", tipo: " + tip +", Pin: "+this.pin;
	}

	



}
