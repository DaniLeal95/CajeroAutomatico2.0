package datos;

public interface Tarjeta {

	/*
	 * Estudio
	 * 	Propiedades
	 * 	Propiedades basicas
	 * 		caracter tipo -> consultable y modificable
	 * 		long numTarjeta -> consultable
	 * 	Propiedades Compartidas

	 * */
	//consultores
	public char getTipo();
	public String getPin();
	public long getNumtarjeta();
	//modificadores
	public void setTipo(char tipo) ;
	public void setPin(String pin) ;
	

	


}