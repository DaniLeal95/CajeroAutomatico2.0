package datos;

import java.io.Serializable;
import java.util.Vector;

import gestionyutilidades.Utilidades;


/*
 *Restricciones :
 **************
 * 	Ninguna
 *
 * Funcionalidades:
 * ***************
 * 	
 * 	Aniadidas:
 *	********
 *		void añadirTarjeta(TarjetaImp t)
 *		String cuentatoCadena()
 *		
 *
 *	Heredadadas:
 *	************
 *		int hashCode(),
 *		void toString(),
 *		CuentaImp clone(),
 *		int compareTo(CuentaImp c),
 *		boolean equals(Object o).
 * 
 * 
 */

public class CuentaImp implements  Cuenta, Serializable, Cloneable, Comparable<CuentaImp> {
	
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1851317503457735857L;
	//ATRIBUTOS
	private long numCuenta;
	private double saldo;
	private Vector<TarjetaImp> tarjetas;
	

	
	//PropiedadesCompartidas
	private transient static long contadorCuentas=0;
	
	
	//constructores ordinarios
	public CuentaImp(){
		Utilidades u=new Utilidades();
		saldo=0;
		
		if(contadorCuentas!=0){numCuenta=contadorCuentas+1; } 
		else{ 
			numCuenta=u.cogerUltimaId("idcuenta.dat")+1;
		}
		contadorCuentas=numCuenta;
		u.escribirUltimaId(numCuenta,"idcuenta.dat");
		tarjetas=new Vector<TarjetaImp>(0,1);
		
	}
	
	public CuentaImp(long saldo){
		this();
		this.saldo=saldo;
		tarjetas=new Vector<TarjetaImp>(0,1);
	}
	
	public CuentaImp(long saldo,Vector<TarjetaImp> tarjetas){
		this();
		this.saldo=saldo;
		this.tarjetas=tarjetas;
	}
	//Constructor de copia
	public CuentaImp(CuentaImp c){
		this.numCuenta=c.numCuenta;
		this.saldo=c.saldo;
		this.tarjetas=c.tarjetas;
	}
	
	//Consultores

	public long getNumCuenta(){
		return(this.numCuenta);
	}
	public double getSaldo(){
		return(this.saldo);
	}
	public Vector<TarjetaImp> getTarjetas(){
		return (this.tarjetas);
	}
	//modificadores
	
	public void setSaldo(double saldo){
		this.saldo=saldo; 
		
	}
	
	
	
	
	/*-------------------------
	 * FUNCIONALIDADES A�ADIDAS
	 * ------------------------
	 * */
	
	/*
	 * setTarjeta
	 * 	Breve Comentario:
	 * -----------------
	 * 		Este metodo recibe por parametros una TarjetaImp y si no esta registrada en otra cuenta
	 * 			lo aniade al vector de tarjetas
	 * 
	 * 	Cabecera:
	 * ----------
	 * 		void setTarjeta(TarjetaImp t)
	 * 		
	 * Precondiciones:
	 * --------------
	 * 		Nada, si la tarjeta ya esta registrada con otra tarjeta o en esa misma, no la insertara
	 * 
	 * Entradas:
	 * ----------
	 * 		Una tarjetaImp
	 * 
	 * Salidas:
	 * ---------
	 * 		Nada
	 * 
	 * Postcondiciones:
	 * ----------------
	 * 		Nada
	 * 
	 * */
	
	public void a�adirTarjeta(TarjetaImp t){
		Utilidades u=new Utilidades();
		
		//boolean valida=u.validarTarjetaRegistrada(t.getNumtarjeta());
		//if(valida){
			this.tarjetas.add(t);
		/*}
		else{
			System.out.println("ERROR:Tarjeta No Añadida");
		}*/
	}
	
	
	//eliminartarjeta
	/*
	 * Breve comentario:
	 * 	-Este metodo eliminara una Tarjeta del vector tarjetas
	 * Cabecera:
	 * 	void eliminarTarjeta(long numtarjeta)
	 * Precondiciones:
	 * 	Nada, si el numtarjeta no existe no eliminara nada
	 * Entradas:
	 * 	Un long id
	 * Salidas:
	 * 	Nada
	 * Postcondiciones:	
	 * 	Nada
	 * 
	 * */
	/*	RESGUARDO
	 * 
	 * public void eliminarTarjeta(long numtarjeta){
	 * 	System.out.println("eliminarTarjeta esta construccion");
	 * }
	 * */
	public void eliminarTarjeta(TarjetaImp tarjeta){
		for(int i=0;i<this.tarjetas.size();i++){	
			if(this.tarjetas.elementAt(i).getNumtarjeta()==tarjeta.getNumtarjeta()){
				this.tarjetas.removeElementAt(i);
			}
		}
	}
	
		/*CuentatoCadena
		 * Breve comentario: 
		 * 		el método devuelve un String con los valores de todos los atributos de la Cuenta.
		 * Cabecera:
		 * 		String cuentatoCadena()
		 * precondiciones:
		 * 		 nada
		 * entrada:
		 * 		 nada
		 * salida:
		 * 		 un String
		 * postcondiciones:
		 * 		 el String retornara asociado  al nombre -> Funcion
		 */
		public String cuentatoCadena(){
			String tarjetas="";
			for(int i=0;i<getTarjetas().size();i++){
				tarjetas=tarjetas.concat(getTarjetas().elementAt(i).tarjetatoCadena());
			}
			
			return getNumCuenta()+", "+getSaldo()+", "+tarjetas;
		}
	
	
	
	
	//HEREDADAS
	@Override
	public int hashCode() {
		int result = 1;
		result= (int)(this.numCuenta*this.saldo/100*1.6);
		return result;
	}

	/*
	 *Equals
	 *	
	 *	Criterio de igualdad: 
	 *			dos Cuentas son iguales si tienen el mismo numCuenta y el mismo saldo.
	 * */
	@Override
	public boolean equals(Object o) {
		boolean iguales=false;
		
		if(o!=null && o instanceof CuentaImp)
			{
				CuentaImp c=(CuentaImp)o;
				if(this.numCuenta==c.numCuenta && this.saldo==c.saldo)
					{
						iguales=true;
				}//fin if
		}//fin if
		return(iguales);
	}
	

	@Override
	public CuentaImp clone(){
		CuentaImp copia=null;
		try{
			
			copia=(CuentaImp) super.clone();
			copia.numCuenta=this.numCuenta;
			
		}catch(CloneNotSupportedException cnse){
			
			System.out.println(cnse);
			
		}
		
		return copia;
	}
	/*	CompareTo
	 * 	*********
	 * 	Criterio de comparacion;
	 * 		Compara por numCuenta, retorna 1 si es mayor, -1 si es menor y 0 si son iguales.
	 * 
	 * */
	@Override
	public int compareTo(CuentaImp c){
		int comparada= 0;
		
		if(this.getNumCuenta()>c.getNumCuenta()){
			comparada=1;
		}
		else if(this.getNumCuenta()<c.getNumCuenta()){
			comparada=-1;
		}
		
		return comparada;
	}
	
	

	@Override
	public String toString() {
		String tarjeta="";
		for(int i=0;i<tarjetas.size();i++){
			tarjeta=tarjeta.concat("\n\t\t"+tarjetas.elementAt(i).toString());
		}
		return "\n\tNumCuenta: " + numCuenta + ", saldo: " + saldo + "euros, \n\t\ttarjetas: " + tarjeta + "";
	}
	
	
	








	
}
