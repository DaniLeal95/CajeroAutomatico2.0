package cajero;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.GregorianCalendar;

import datos.ClienteImp;
import datos.CuentaImp;
import datos.TarjetaImp;

public class MainCrearFicherosConCabecera {

	public static void main(String[] args) {
		File fclientes = null;
		File fcuentas = null;
		File ftarjetas = null;
		
		FileOutputStream fosclientes = null;
		FileOutputStream foscuentas=null;
		FileOutputStream fostarjetas=null;
		
		ObjectOutputStream oosclientes = null;
		ObjectOutputStream ooscuentas=null;
		ObjectOutputStream oostarjetas= null;
		
		

		try {
			//FICHEROS MAESTROS
			// Abrir fichero clientes para escribir
			fclientes = new File("ClientesMaestro.dat");
			fosclientes = new FileOutputStream(fclientes);
			oosclientes = new ObjectOutputStream(fosclientes);
			
			//Abrimos ficheros para cuentas
			fcuentas = new File("CuentasMaestro.dat");
			foscuentas = new FileOutputStream(fcuentas);
			ooscuentas = new ObjectOutputStream(foscuentas);
			
			//Abrimos fichero para tarjetas
			ftarjetas=new File("TarjetasMaestro.dat");
			fostarjetas= new FileOutputStream(ftarjetas,true);
			oostarjetas= new ObjectOutputStream(fostarjetas);
			
			//Insertamos un cliente
			ClienteImp admin=new ClienteImp("Admin", "Admin", new GregorianCalendar(),"11111111W",'H', "ADMIN","111111");
			ClienteImp cliente=new ClienteImp("Dani", "Leal", new GregorianCalendar(1995,10,12),"53284930W",'H', "YO, PORQUE SI","123456");
			ClienteImp cliente2=new ClienteImp("Clara", "Martinez", new GregorianCalendar(1985,10,12),"65412873H",'M', "Una cualquiera","123456");
			oosclientes.writeObject(admin);
			oosclientes.writeObject(cliente);
			oosclientes.writeObject(cliente2);
			
			//Insertamos una Cuenta
			CuentaImp cuenta=new CuentaImp(1000,2);
			CuentaImp cuenta2=new CuentaImp(90000,3);
			ooscuentas.writeObject(cuenta);
			ooscuentas.writeObject(cuenta2);
			
			//Insertamos una tarjeta
			TarjetaImp tarjeta=new TarjetaImp('D', "1234", 1);
			TarjetaImp tarjeta2=new TarjetaImp('C',"5678",2);
			oostarjetas.writeObject(tarjeta);
			oostarjetas.writeObject(tarjeta2);
			
			
			
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
		finally {
				try {
					// Cerrar fichero
					if(oosclientes!=null){
						oosclientes.close();
						fosclientes.close();
					}
					if(ooscuentas!=null){
						ooscuentas.close();
						foscuentas.close();
					}
					if(oostarjetas!=null){
						oostarjetas.close();
						fostarjetas.close();
					}
				} catch (IOException e) {

					System.out.println(e);
				}
			}

		}

}


