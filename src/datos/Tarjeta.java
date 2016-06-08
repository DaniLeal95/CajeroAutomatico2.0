package datos;

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
	public long getnumCuenta();
	//modificadores
	public void setTipo(char tipo) throws TarjetaExcepcion;
	public void setPin(String pin) throws TarjetaExcepcion;
	public void setnumCuenta(long idCuenta)throws TarjetaExcepcion;

	


}