package datos;

public interface Cliente {
/*
 * Propiedades :
 * 		Basicas:
 * 		-------
 * 			Idcuenta - entero largo , consultable
 * 			Observaciones - cadena , consultable y modificable
 * 			Contraseña - cadena , consultable y modificable
 * 		
 * 		Derivadas:
 * 		---------
 * 
 * 
 * */
	long getIdCliente();
	String getContrasena();
	String getObservaciones();

	void setObservaciones(String observaciones);
	void setContrasena(String contrasena);
}
