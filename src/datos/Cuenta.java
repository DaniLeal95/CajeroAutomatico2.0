package datos;


/*
 * Propiedades:
 * 
 * 	Numero De Cuenta- int , consultable
 * 	Saldo- int, consultable y modificable
 * 	
 * 	Compartida
 * 		CONTADOR- long(6 cifras) - consultable y modificable
 * 	
 * Metodos:
 * 		long getNumCuenta()
 * 		double getSaldo()
 * 		Vector<TarjetaImp> getTarjetas()
 * 		
 * 		void setSaldo(double saldo)
 * 		void SetTarjeta(TarjetaImp t)
 * 
 * 
 * */
public interface Cuenta{
	//Consultores
	public long getNumCuenta();
	public double getSaldo();
	//modificadores
	public void setSaldo(double saldo);
	
	
	
	
}
