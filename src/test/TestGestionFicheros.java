package test;

import java.util.GregorianCalendar;

import cajero.TarjetaExcepcion;
import datos.ClienteImp;
import datos.CuentaImp;
import datos.TarjetaImp;
import gestionyutilidades.GestionFicherosClientes;
import gestionyutilidades.Utilidades;

public class TestGestionFicheros {

	public static void main(String[] args) {
		GestionFicherosClientes gf=new GestionFicherosClientes();
		Utilidades u=new Utilidades();
		
		//mostrarFicheroMaestro
		
		gf.mostrarClientes("ClientesMaestro.dat");
		
		ClienteImp c=new ClienteImp("ClienteNuevo","PEPOTE",new GregorianCalendar(2002,2,18),"11111111F",'M',"PRUEBA","123456");
		System.out.println("AÑADO UN CLIENTE A MOVIMIENTOS");
		gf.escribirMovimiento("ClientesMovimiento.dat", c);
		
		System.out.println("MUESTRO CLIENTES");
		gf.mostrarClientes("ClientesMovimiento.dat");
		
		
		//System.out.println("ACTUALIZO");
		//ordenacionExternaMezcla("ClientesMaestro.dat");
		
		gf.actualizaClientes("ClientesMaestro.dat", "ClientesMovimiento.dat");
		
		System.out.println("MUESTRO CLIENTES MAESTRO");
		gf.mostrarClientes("ClientesMaestro.dat");
		
		//ObtenerCuenta
		//CuentaImp obtenida=gf.obtenerCuenta(2, 1);
		//System.out.println(obtenida.toString());
		
		//escribir cliente
		/*TarjetaImp t1=new TarjetaImp('D',"1111");
		TarjetaImp t3=new TarjetaImp('C',"1234");
		CuentaImp c2=new CuentaImp(900);
		
		GregorianCalendar fnacimiento=new GregorianCalendar(1995, 12, 10);
		ClienteImp cl1 = new ClienteImp("EAESA", "Leal Reyes",fnacimiento,"53284930W",'H',"Este tio es el amo","123456");
		
		
		
		
		
		cl1.addCuenta(c2);
		
		
		gf.escribirCliente(cl1);
		gf.mostrarFicheromaestro();
		
		
		//TarjetaImp t4=new TarjetaImp('D');
		//CuentaImp c1=new CuentaImp(500);
		//c1.setTarjeta(t4);
		//System.out.println(c1.toString());
		
		//CuentaImp ccopiado=new CuentaImp(c1,8000);
		//	gf.escribirMovimiento(ccopiado);
		//	GregorianCalendar fnacimiento=new GregorianCalendar(12, 10, 1995);
		//ClienteImp c2=new ClienteImp("Eduardo","Mano","Tijeras",fnacimiento,"53284930W",'H');
		//c2.addCuenta(c1);
		//gf.escribirCliente(c2);
		//gf.escribirCliente(cliente);
		
		//gf.escribirMovimiento(ccopiado);
		//gf.mostrarFicheromaestro();
		//gf.mostrarFicheromovimiento();
		//gf.actualizaClientes();
		//gf.mostrarFicheromaestro();
		
		//CuentaImp c=gf.obtenerCuenta(1, 1);
		//System.out.println(c.toString());
		
		//gf.movimientocon1cuenta(c, -500);
		//gf.movimientocon1cuenta(c, 500);
		//c=gf.obtenerCuenta(1, 1);
		//System.out.println(c.toString());
		
		
		*/
		
		
	}

}
