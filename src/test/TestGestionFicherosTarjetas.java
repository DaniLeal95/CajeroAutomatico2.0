package test;

import datos.TarjetaImp;
import gestionyutilidades.GestionFicherosTarjetas;

public class TestGestionFicherosTarjetas {

	public static void main(String[] args) {
		GestionFicherosTarjetas gf=new GestionFicherosTarjetas();
		TarjetaImp t=new TarjetaImp('D', "1234", 1);
		TarjetaImp t2=new TarjetaImp('C', "1234", 1);
		
		//MOSTRAMOS FICHERO MAESTRO
		System.out.println("MOSTRAR FICHERO MAESTRO");
		gf.mostrarTarjetas("TarjetasMaestro.dat");
		
		//ESCRIBIR MOVIMIENTO
		System.out.println("ESCRIBIMOS 2 TARJETAS MOVIMIENTO");
		gf.escribirMovimiento("TarjetasMovimiento.dat", t2);
		gf.escribirMovimiento("TarjetasMovimiento.dat", t2);
		
		//MOSTRAMOS MOVIMIENTO
		System.out.println("MOSTRAMOS MOVIMIENTO");
		gf.mostrarTarjetas("TarjetasMovimiento.dat");
		
		//ACTUALIZAMOS
		System.out.println("ACTUALIZAMOS");
		gf.actualizaTarjetas("TarjetasMaestro.dat", "TarjetasMovimiento.dat");
		gf.mostrarTarjetas("TarjetasMaestro.dat");
		
		//RECOGEMOS CUENTA
		System.out.println("OBTENEMOS LA CUENTA CON ID 5, Y LA MOSTRAMOS");
		TarjetaImp t3=gf.obtenerTarjeta(5, "TarjetasMaestro.dat","TarjetasMovimiento.dat");
		System.out.println(t3.toString());
	}

}
