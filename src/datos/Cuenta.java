package datos;


/*
 * Propiedades:
 * 
 * 	NumCuenta- long , consultable
 * 	Saldo- double, consultable y modificable
 * 	
 * 	Compartida
 * 	
 * Metodos:
 * 		//Consultores
 * 
 * 		long getNumCuenta()
 * 		double getSaldo()
 * 		
 * 		//Modificadores
 * 		void setSaldo(double saldo)
 * 
 * 
 * */
public interface Cuenta{
	//Consultores
	public long getNumCuenta();
	public double getSaldo();
	public long getidCliente();
	//modificadores
	public void setSaldo(double saldo);
	public void setidCliente(long idCliente);
	
	
	
	
}
