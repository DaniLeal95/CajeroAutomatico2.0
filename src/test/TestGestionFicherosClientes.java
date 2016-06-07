package test;

import java.util.GregorianCalendar;

import cajero.TarjetaExcepcion;
import datos.ClienteImp;
import datos.CuentaImp;
import datos.TarjetaImp;
import gestionyutilidades.GestionFicherosClientes;
import gestionyutilidades.Utilidades;

public class TestGestionFicherosClientes {

	public static void main(String[] args) {
		GestionFicherosClientes gf=new GestionFicherosClientes();
		Utilidades u=new Utilidades();
		
		//mostrarFicheroMaestro
		
		gf.mostrarClientes("ClientesMaestro.dat");
		
		//Creo un cliente
		ClienteImp c=new ClienteImp("ClienteNuevo","PEPOTE",new GregorianCalendar(2002,2,18),"11111111F",'M',"PRUEBA","123456");
		ClienteImp c2=new ClienteImp("CLIENTE NUEVO 2","SURMANO",new GregorianCalendar(2003,3,25),"22222222A",'H',"PREBA2","123456");
		
		//Escribir cliente en movimiento
		System.out.println("AÑADO UN CLIENTE A MOVIMIENTOS");
		gf.escribirClienteMovimiento("ClientesMovimiento.dat", c2);
		gf.escribirClienteMovimiento("ClientesMovimiento.dat", c);
		
		//Mostramos fichero movimientos
		System.out.println("MUESTRO CLIENTES MOVIMIENTO");
		gf.mostrarClientes("ClientesMovimiento.dat");
		
		//ACTUALIZAR (Dentro llama a ordenar ficheros, no hace falta probarlo)
		System.out.println("ACTUALIZO");
		gf.actualizaClientes("ClientesMaestro.dat", "ClientesMovimiento.dat");
		
		//Vuelvo a mostrar Clientes
		System.out.println("MUESTRO CLIENTES MAESTRO");
		gf.mostrarClientes("ClientesMaestro.dat");
		

		
	}

}
