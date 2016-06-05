package datos;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Vector;

import gestionyutilidades.GestionFicheros;
import gestionyutilidades.Utilidades;


/*
 *Restricciones :
 **************
 *	El idCliente debe existir en los ficheros maestros.
 * Funcionalidades:
 * ***************
 * 	
 * 	Aniadidas:
 *	********
 *		String cuentatoCadena()
 *		boolean validarnumCuenta(long numCuenta, String ficheromaestro,String ficheromovimiento){
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
		this.saldo=saldo;
		if(validaridCliente(idCliente, "ClientesMaestro.dat", "ClientesMovimiento.dat"))
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
		if(validaridCliente(idCliente, "ClientesMaestro.dat", "ClientesMovimiento.dat"))
			this.idCliente=idCliente;
	}
	
	
	
	/*-------------------------
	 * FUNCIONALIDADES A�ADIDAS
	 * ------------------------
	 * */
	
	/*
	 * Breve comentario:
	 * 	El metodo valida el idCliente, consultando los ficheros de Clientes que le pasamos por parametros 
	 * 	simulando como seria un Foreign Key en una base de datos.
	 * 	y retorna un boolean , true en caso de que sea posible utilizar ese idCliente y false cuando no
	 * Cabecera:
	 *  boolean validaridCliente(long idCliente, String ficheromaestro,String ficheromovimiento){
	 * Precondiciones:
	 * 	Almenos el fichero maestro debe existir, de no se asi saltara una excepcion
	 * 	
	 * Entradas:
	 * 		long idCliente, y dos cadenas con los nombres de los ficheros
	 * Salidas:
	 * 		boolean
	 * Postcondiciones:
	 * 		boolean retornara asociado al nombre, Funcion.
	 * 
	 * */
	//RESGUARDO
	//	public boolean validaridCliente(long idCliente, String ficheromaestro,String ficheromovimiento){
	//		boolean valida=false;
	//		return valida;
	//	}
	
	public boolean validaridCliente(long idCliente, String ficheromaestro,String ficheromovimiento){
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
				
				ClienteImp aux=(ClienteImp)oismae.readObject();
				while(aux!=null && !valida){
					if(aux.getIdCliente()== idCliente){
						valida=true;
					}
					aux=(ClienteImp)oismae.readObject();
				}
			}
			else{
				//Si el fichero de movimiento existe primero miraremos en el de movimiento
				fismov=new FileInputStream(fmov);
				oismov=new ObjectInputStream(fismov);
				
				
				for(int i=0;i<gf.contarRegistros(ficheromovimiento) && !valida;i++){
					Cliente aux=(ClienteImp) oismov.readObject();
					if(aux.getIdCliente()== idCliente){
						valida=true;
					}
				}
				//si no se a encontrado en el fichero de movimiento , lo miraremos en el maestro
				if(!valida){
					fismae= new FileInputStream(fmae);
					oismae=new ObjectInputStream(fismae);
					ClienteImp aux=(ClienteImp) oismae.readObject();
					
					while(aux!=null && !valida){
						if(aux.getIdCliente()== idCliente){
							valida=true;
						}
						aux=(ClienteImp) oismae.readObject();
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
