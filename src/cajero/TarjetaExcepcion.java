package cajero;

public class TarjetaExcepcion extends Exception {

	private static final long serialVersionUID = 6628290599280866053L;

	public TarjetaExcepcion(String texto){
		super(texto);
	}

}
