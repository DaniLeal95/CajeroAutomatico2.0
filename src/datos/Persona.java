package datos;

import java.util.GregorianCalendar;
/*
* Propiedades:
* 	nombre: cadena, consultable y modificable
* 	apellido: cadena, consultable y modificable
* 	FNacimiento: Fecha, consultable y modificable
* 	dni: cadena, consultable y modificable
* 	telefono: cadena, consultable y modificable 
*	direccion: cadena, consultable y modificable
* 	genero: caracter, consultable y modificable
*/

public interface Persona {

	//consultores
	String getNombre();

	String getApellido();

	GregorianCalendar getFNacimiento();

	char getGenero();

	String getDni();

	String getTelefono();

	String getDireccion();


	
	
	//modificadores
	void setNombre(String nombre);
	
	void setApellido(String apellido);
	
	void setGenero(char genero) throws PersonaNoValida;
	
	void setDni(String dni) throws PersonaNoValida;
	
	void setTelefono(String telefono) throws PersonaNoValida;
	
	void setDireccion(String direccion);
	
	void setFNacimiento(GregorianCalendar fNacimiento) throws PersonaNoValida;
	

}