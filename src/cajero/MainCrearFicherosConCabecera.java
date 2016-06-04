package cajero;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.GregorianCalendar;

import datos.ClienteImp;
import datos.CuentaImp;
import datos.TarjetaImp;
import gestionyutilidades.GestionFicheros;

public class MainCrearFicherosConCabecera {

	public static void main(String[] args) {
		File f = null;
		File fmov = null;
		FileOutputStream fos = null;
		FileOutputStream fosmov=null;
		ObjectOutputStream oos = null;
		ObjectOutputStream oosmov=null;
		
		GestionFicheros gf=new GestionFicheros();
		

		try {
			
			// Abrir fichero para escribir
			f = new File("ClientesMaestro.dat");
			fos = new FileOutputStream(f);
			oos = new ObjectOutputStream(fos);
			fmov=new File("ClientesMovimiento.dat");
			fosmov= new FileOutputStream(fmov);
			oosmov= new ObjectOutputStream(fosmov);
			/* *
			 * Meto Dos cientes: uno usuario y otro Administrador. 
			 * */
			TarjetaImp t1=new TarjetaImp('D',"1111");
			TarjetaImp t3=new TarjetaImp('C',"1234");
			CuentaImp c2=new CuentaImp(900);
			
			c2.añadirTarjeta(t1);
			c2.añadirTarjeta(t3);
			
			GregorianCalendar fnacimiento=new GregorianCalendar(1995, 12, 10);
			GregorianCalendar fActual=new GregorianCalendar();
			
			
			ClienteImp clAdministrador= new ClienteImp("ADMIN","ADMIN",fActual,"00000000A",'H',"ADMINISTRADOR DEL SISTEMA", "111111");
			ClienteImp cl1 = new ClienteImp("Daniel", "Leal Reyes",fnacimiento,"53284930W",'H',"Este tio es el amo","123456");
			
			
			
			
			
			cl1.addCuenta(c2);
			System.out.println(cl1.toString());
			System.out.println(clAdministrador.toString());
			/*
			 * Y ahora una cuenta en movimiento
			 * */
			CuentaImp c1=gf.obtenerCuenta(1, 1);
			gf.movimientocon1cuenta(c1, 700);
			
			oos.writeObject(clAdministrador);
			oos.writeObject(cl1);
			oosmov.writeObject(c1);
			
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
		finally {
				try {
					// Cerrar fichero
					if(oos!=null){
						oos.close();
						fos.close();
					}
					if(oosmov!=null){
						oosmov.close();
						fosmov.close();
					}
				} catch (IOException e) {

					System.out.println(e);
				}
			}

		}

}


