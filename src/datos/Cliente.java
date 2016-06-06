package datos;

public interface Cliente {
/*
 * Propiedades :
 * 		Basicas:
 * 		-------
 * 			Idcuenta - entero largo , consultable
 * 			Cuentas - vector<CuentaImp>  , consultable y modificable
 * 			Observaciones - cadena , consultable y modificable
 * 			Contraseña - cadena , consultable y modificable
 * 		
 * 		Derivadas:
 * 		---------
 * 
 * 			Compartida:
 * 			-----------
 * 				contadorCliente - entero largo
 * */
	long getIdCliente();
	String getContrasena();
	String getObservaciones();

	void setObservaciones(String observaciones);
	void setContrasena(String contrasena);
}
