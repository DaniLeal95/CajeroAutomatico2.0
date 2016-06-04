package datos;

public interface Cliente {
/*
 * Propiedades :
 * 		Basicas:
 * 		-------
 * 			Idcuenta - entero largo , consultable
 * 			Cuentas - vector<CuentaImp>  , consultable y modificable
 * 			Observaciones - cadena , consultable y modificable
 * 			ContraseÃ±a - cadena , consultable y modificable
 * 		
 * 		Derivadas:
 * 		---------
 * 
 * 			Compartida:
 * 			-----------
 * 				contadorCliente - entero largo
 * */
	long getIdCliente();
	String getContraseña();
	String getObservaciones();

	void setObservaciones(String observaciones);
	void setContraseña(String contraseña);
}
