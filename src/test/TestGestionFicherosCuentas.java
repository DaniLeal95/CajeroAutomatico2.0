package test;

import datos.CuentaImp;
import gestionyutilidades.GestionFicherosCuentas;

public class TestGestionFicherosCuentas {

	public static void main(String[] args) {
		
		GestionFicherosCuentas gfc=new GestionFicherosCuentas();
		
		CuentaImp c=new CuentaImp(15000,2);
		CuentaImp c2=new CuentaImp(-500,2);
		CuentaImp c3=new CuentaImp(50,2);
		CuentaImp c4=new CuentaImp(c2);
		CuentaImp c5=new CuentaImp(70000,-1);
		CuentaImp c6=new CuentaImp(70000,1);
		c4.setSaldo(1000);
		//MostrarCuentas
		System.out.println("MUESTRO EL ARCHIVO MAESTRO");
		gfc.mostrarCuentas("CuentasMaestro.dat");
		
		//ObtenerCuenta
		CuentaImp Cuentaobtenida=gfc.obtenerCuenta(1, "CuentasMaestro.dat", "CuentasMovimiento.dat");
		System.out.println(Cuentaobtenida.toString());
		
		CuentaImp Cuentaobtenida2=gfc.obtenerCuenta(2, "CuentasMaestro.dat", "CuentasMovimiento.dat");
		System.out.println(Cuentaobtenida2.toString());
		Cuentaobtenida.setSaldo(-500);
		Cuentaobtenida2.setSaldo(500);
		//EscribirMovimiento
		System.out.println("ESCRIBO DOS CUENTAS EN MOVIMIENTO");

		gfc.escribirMovimiento("CuentasMovimiento.dat", Cuentaobtenida2);
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
