package test;

import datos.CuentaImp;
import gestionyutilidades.GestionFicherosCuentas;

public class TestGestionFicherosCuentas {

	public static void main(String[] args) {
		
		GestionFicherosCuentas gfc=new GestionFicherosCuentas();
		
		//CuentaImp c=new CuentaImp(15000,2);
		CuentaImp c2=new CuentaImp(-500,2);
		//CuentaImp c3=new CuentaImp(50,2);
		CuentaImp c4=new CuentaImp(c2);
		c4.setSaldo(1000);
		//MostrarCuentas
		System.out.println("MUESTRO EL ARCHIVO MAESTRO");
		gfc.mostrarCuentas("CuentasMaestro.dat");
		
		//EscribirMovimiento
		System.out.println("ESCRIBO DOS CUENTAS EN MOVIMIENTO");
		gfc.escribirMovimiento("CuentasMovimiento.dat", c2);
		//gfc.escribirMovimiento("CuentasMovimiento.dat", c3);
		//gfc.escribirMovimiento("CuentasMovimiento.dat", c);
		gfc.escribirMovimiento("CuentasMovimiento.dat", c4);
		
		
		//MostrarCuentas
		System.out.println("MOSTRAMOS EL ARCHIVO MOVIMIENTO");
		gfc.mostrarCuentas("CuentasMovimiento.dat");
		
		//OrdenamosMovimiento
		//System.out.println("ORDENAMOS");
		//gfc.ordenacionExternaMezcla("CuentasMovimiento.dat");
		//gfc.mostrarCuentas("CuentasMovimiento.dat");
		
		
		//ActualizarCuentas
		System.out.println("ACTUALIZAMOS");
		gfc.actualizaCuentas("CuentasMaestro.dat", "CuentasMovimiento.dat");
		
		//MostrarCuentas
		System.out.println("MOSTRAMOS EL ARCHIVO MAESTRO ACTUALIZADO");
		gfc.mostrarCuentas("CuentasMaestro.dat");
	}

}