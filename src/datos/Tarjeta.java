package datos;

import cajero.TarjetaExcepcion;

public interface Tarjeta {

	/*
	 * Estudio
	 * 	Propiedades
	 * 	Propiedades basicas
	 * 		string tipo -> consultable y modificable
	 * 		long numTarjeta -> consultable
	 * 	Propiedades Compartidas
	 * 		long contadortarjeta -> consultable
	 * */
	//consultores
	public char getTipo();
	public String getPin();
	public long getNumtarjeta();
	//modificadores
	public void setTipo(char tipo) throws TarjetaExcepcion;
	public void setPin(String pin) throws TarjetaExcepcion;

	


}