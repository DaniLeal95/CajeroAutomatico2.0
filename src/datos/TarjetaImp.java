package datos;

import java.io.Serializable;

import gestionyutilidades.GestionFicherosTarjetas;
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
	 * 	El tipo solo puede ser 'C' O 'D'
	 * 
	 * 	Metodos añadidos
	 * 		String tarjetatoCadena()
	 * 		boolean validarPin(String pin)
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
		GestionFicherosTarjetas gft=new GestionFicherosTarjetas();
		if(Character.toUpperCase(tipo)=='C' || Character.toUpperCase(tipo)=='D'){
			this.tipo=tipo;
		}
		if(validarPin(pin)){
			this.pin=pin;
		}
		if(gft.validarnumCuenta(numCuenta, "CuentasMaestro.dat", "CuentasMovimiento.dat") || numCuenta==-1){
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
	public long getnumCuenta(){
		return this.numCuenta;
	}

	
	//modificadores
	@Override
	public void setTipo(char tipo) {
		
		if(Character.toUpperCase(tipo)=='C' || Character.toUpperCase(tipo)=='D'){
			this.tipo = tipo;
		}
		else {
			System.out.println("Solo puede ser de C(Credito) o de D(Debito)");
		}
	}
	@Override
	public void setPin(String pin){
		if(!validarPin(pin))
			System.out.println("Pin invalido");
		else
			this.pin=pin;
	}
	
	public void setnumCuenta(long numCuenta){
		GestionFicherosTarjetas gft=new GestionFicherosTarjetas();
		if(gft.validarnumCuenta(numCuenta, "CuentasMaestro.dat", "CuentasMovimiento.dat") || numCuenta==-1)
			this.numCuenta=numCuenta;
		
		
	}
	
	/*
	*	Métodos añadidos
	*/
	
	
	/*
	 * validarPin
	 * 	
	 * Breve comentario:
	 * 		El metodo validara el pin de la tarjeta
	 * 			si el pin es correcto retornara true y false si no es correcto
	 * cabecera:
	 * 		boolean validarPin()
	 * Precondiciones:
	 * 		nada
	 * Entradas:
	 * 		nada
	 * Salida:
	 * 		boolean (validacion)
	 * Postcondiciones:
	 * 		el boolean retornara asociado al nombre -> Funcion
	 * 	
	 * */
	public boolean validarPin(String pin) {
		// Comprobacion del pin
		boolean valido = true;
		if (pin.length() == 4) {

			for (int i = 0; i < pin.length() && valido; i++) {
				
				if (!Character.isDigit(pin.charAt(i))) {
					valido = false;
				}
				
			}
		}
		else{
			valido=false;
		}
		return valido;
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
			if(this.numtarjeta>t.numtarjeta)
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
		
		return "Numtarjeta: " + (numtarjeta) +", tipo: " + tip +", Pin: "+this.pin+", NumCuenta: "+this.numCuenta;
	}

	



}
