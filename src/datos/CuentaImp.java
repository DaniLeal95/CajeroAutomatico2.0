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
	private long idCliente;

	
	//PropiedadesCompartidas
	private transient static long contadorCuentas=0;
	
	
	//constructores ordinarios
	public CuentaImp(){
		Utilidades u=new Utilidades();
		saldo=0;
		idCliente = 1;
		if(contadorCuentas!=0){numCuenta=contadorCuentas+1; } 
		else{ 
			numCuenta=u.cogerUltimaId("idcuenta.dat")+1;
		}
		contadorCuentas=numCuenta;
		u.escribirUltimaId(numCuenta,"idcuenta.dat");
		
	}
	
	public CuentaImp(long saldo,long idCliente){
		this();
		//aqui falta metodo comprobar cliente.
		this.saldo=saldo;
		this.idCliente=idCliente;
	}
	

	//Constructor de copia
	public CuentaImp(CuentaImp c){
		this.numCuenta=c.numCuenta;
		this.saldo=c.saldo;
		this.idCliente=c.idCliente;
	}
	
	//Consultores

	@Override
	public long getNumCuenta(){
		return(this.numCuenta);
	}
	@Override
	public double getSaldo(){
		return(this.saldo);
	}
	@Override
	public long getidCliente(){
		return(this.idCliente);
	}
	
	//modificadores
	@Override
	public void setSaldo(double saldo){
		this.saldo=saldo; 
	}
	@Override
	public void setidCliente(long idCliente){
		//faltametodocomprobarCliente
		this.idCliente=idCliente;
	}
	
	
	
	/*-------------------------
	 * FUNCIONALIDADES A�ADIDAS
	 * ------------------------
	 * */
	

	

	
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
			
			
			return getNumCuenta()+", "+getSaldo()+", "+getidCliente();
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
	 *			dos Cuentas son iguales si tienen el mismo numCuenta , el mismo saldo y el mismo idCliente.
	 * */
	@Override
	public boolean equals(Object o) {
		boolean iguales=false;
		
		if(o!=null && o instanceof CuentaImp)
			{
				CuentaImp c=(CuentaImp)o;
				if(this.numCuenta==c.numCuenta && this.saldo==c.saldo && this.idCliente==c.idCliente)
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
		
		return "\n\tNumCuenta: " + numCuenta + ", saldo: " + saldo + "euros, IdCliente: " + idCliente + "";
	}
	
	
	








	
}
