package gestionyutilidades;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cajero.MiObjectOutputStream;
import datos.TarjetaImp;

public class GestionFicherosTarjetas  {



	/*
	 * Metodo mostrarTarjetas
	 * 	Breve Comentario:
	 * 		Este metodo mostrara por pantalla todos los clientes que tenemos registrados en el fichero
	 * 		
	 * 	Cabecera:
	 * 		void mostrarClientes(String nombreFichero)
	 * 	Precondiciones:
	 * 		el fichero debera estar creado, de no estarlo saltara una excepcion de fichero no encontrado
	 * 	Entradas:
	 * 		El nombre del fichero
	 * 	Salidas:
	 * 		Nada
	 * 	Postcondiciones:
	 * 		Nada
	 * */
	public void mostrarTarjetas(String nombreFichero){
		File fmaestro=null;
		FileInputStream fismaestro=null;
		ObjectInputStream oismaestro=null;
		
		try{
			fmaestro=new File(nombreFichero);
			fismaestro=new FileInputStream(fmaestro);
			oismaestro=new ObjectInputStream(fismaestro);
			
				for(int i=0;i<contarRegistros(nombreFichero);i++){
					TarjetaImp tarjeta=(TarjetaImp)oismaestro.readObject();
					System.out.println(tarjeta.toString());
				}
			
				
			
		}catch(FileNotFoundException fnfe){
			System.out.println("Fichero no encontrado");
		}catch(EOFException eof){
		
		}	catch (IOException ioe) {
			System.out.println(ioe);
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe);
		}finally{
			try{
				if(oismaestro!=null){
					oismaestro.close();
					fismaestro.close();
				}
				
			}catch(IOException ioe){
				System.out.println(ioe);
			}
		}
		
	}

	
	/*Escribir Fichero 
	 * 	Breve comentario:
	 * 		El metodo escribirá en el fichero un objeto Tarjeta que recibirá por parametros 
	 * 	Cabecera:
	 * 		void escribirTarjeta(TarjetaImp t)
	 * 	Precondiciones:
	 * 		Nada
	 * 	Entradas:
	 * 		un objeto CuentaImp
	 * 	Salidas:
	 * 		Nada 
	 * 	Postcondiciones:
	 * 		Escribirá el objeto en el fichero.
	 * */
	public void escribirMovimiento(String nombreFichero,TarjetaImp t){
		
		File f=null;
		FileOutputStream fos=null;
		MiObjectOutputStream moos=null;
		ObjectOutputStream oos=null;
		
		try{
			f=new File(nombreFichero);
			if(f.exists()){
				fos=new FileOutputStream(f,true);
				moos=new MiObjectOutputStream(fos);
				moos.writeObject(t);
			}else{
				fos=new FileOutputStream(f,true);
				oos=new ObjectOutputStream(fos);
				oos.writeObject(t);
			}
		}catch(IOException ioe){
			System.out.println(ioe);
		}finally{
			try{
				if(moos!=null){
					fos.close();
					moos.close();
				}
				if(oos!=null){
					oos.close();
					fos.close();
				}
			}catch(IOException ioe){
				System.out.println(ioe);
			}
		}
		
	}

	
	/*contarRegistros
	 * 
	 * Breve comentario:
	 * 		El metodo lee todo el fichero y retorna el numero de registros que hayan en el fichero cuyo nombre 
	 *  		se le  pasa por parametros
	 * 	Cabecera:
	 * 		int contarRegistros(String nombreFichero)
	 * 	Precondiciones:
	 * 		El fichero debera existir, en el caso de que no este creado saltara una excepcion(FILENOTFOUNDEXCEPTION)
	 * 	Entradas:
	 * 		el nombre del fichero(String)
	 * 	Salida:
	 * 		un entero numregistros
	 * 	Postcondiciones:
	 * 		el numregistros retornara asociado al nombre -> Funcion.
	 * 
	 * */
	
	public int contarRegistros(String nombreFichero){
		int registro=0;
		File f=null;
		FileInputStream fis=null;
		ObjectInputStream ois=null;
		try{
			f=new File(nombreFichero);
			fis=new FileInputStream(f);
			ois=new ObjectInputStream(fis);
			
			Object c=ois.readObject();
		
			while(c!=null){
				registro++;
				c=ois.readObject();
			}
		}catch(EOFException eof){
			System.out.println("");
		}catch(FileNotFoundException fnfe){
			System.out.println("El fichero "+nombreFichero+" no existe");
		}catch(IOException ioe){
			System.out.println(ioe);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}finally{
			
				try{
					if(ois!=null){
						ois.close();
						fis.close();
					}
				}catch(IOException ioe){
					System.out.println(ioe);
				}
			
		}
	
		return registro;
	}


/*
 * actualiza Tarjetas
 * 	Breve comentario: 
 * 		Este metodo Actualiza el archivo Maestro comparandolo con el de movimientos 
 * 	Cabecera:
 * 		void actulizaTarjetas(String nombreFicheroMaestro,String nombreFicheroMovimiento)
 * 	Precondiciones:
 * 		los ficheros deben existir
 * 	Entradas:
 * 		dos cadenas (nombre de los ficheros)
 * 	Salidas:
 * 		nada
 * 	Postcondiciones:
 * 		nada
 * 
 * */

	public void actualizaTarjetas(String nombreFicheroMaestro,String nombreFicheroMovimiento) {
		
		ordenacionExternaMezcla(nombreFicheroMaestro);
		ordenacionExternaMezcla(nombreFicheroMovimiento);
		File fmaestro = new File(nombreFicheroMaestro);
		File fmovimiento = new File(nombreFicheroMovimiento);
		File fmaestronuevo = new File("ClientesMaestroNuevo.dat");
		// Para leer maestro
		FileInputStream fism = null;
		ObjectInputStream oism = null;
		// Para leer movimientos
		FileInputStream fismo = null;
		ObjectInputStream oismo = null;

		// Para escribir
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		
		int numregistrosmaestro;
		int numregistrosmovimiento;

		if (fmaestro.exists() && fmovimiento.exists()) {
			numregistrosmaestro = this.contarRegistros("ClientesMaestro.dat");
			numregistrosmovimiento = this.contarRegistros("ClientesMovimiento.dat");
			try {

				// Abrimos para leer el archivo maestro
				fism = new FileInputStream(fmaestro);
				oism = new ObjectInputStream(fism);
				// Abrimos para leer el archivo movimiento
				fismo = new FileInputStream(fmovimiento);
				oismo = new ObjectInputStream(fismo);
				// Abrimos para escribir en el nuevo archivo maestro
				fos = new FileOutputStream(fmaestronuevo);
				oos = new ObjectOutputStream(fos);
				
					//Como en el fichero de movimientos solo puede haber un movimiento
					//, cada vez que encontremos un cliente con su misma id, la escribiremos
				
				TarjetaImp tarjetaaux = (TarjetaImp) oism.readObject();
				TarjetaImp tarjetamovaux = (TarjetaImp) oismo.readObject();
				for (int i = 0; i < numregistrosmaestro && i < numregistrosmovimiento; i++) {
						
					
					
					if (tarjetaaux.compareTo(tarjetamovaux) == 0) {
						// Si el cliente contiene un asterisco en el nombre
						// quiere
						// decir que ya no esta activa.
						// BAJA Y MODIFICACION
						if (!(tarjetamovaux.getnumCuenta()==-1)) {
							oos.writeObject(tarjetamovaux);
						}
						//Y leemos los dos siguientes registros.
						try{
							tarjetaaux = (TarjetaImp) oism.readObject();
						}catch(IOException ioe){
							tarjetaaux = null;
						}
						try{
							tarjetamovaux = (TarjetaImp) oismo.readObject();
						}catch(IOException ioe){
							tarjetamovaux = null;
						}
					
					} else if (tarjetamovaux.compareTo(tarjetaaux) > 0) {
						// SI no hay ninguna actualizacion
						oos.writeObject(tarjetaaux);
						
						//Y como no hay ninnguna actualizacion
						//solo tenemos que volver a leer del fichero maestro
						try{
							tarjetaaux = (TarjetaImp) oism.readObject();
						}catch(IOException e){
							tarjetaaux=null;
						}
					} else {
						//alta
						oos.writeObject(tarjetamovaux);
						try{
							tarjetamovaux = (TarjetaImp) oismo.readObject();
						}catch(IOException ioe){
							tarjetamovaux = null;
						}
					}

				} // fin Para
				
				//se ha acabado el fichero movimiento pero no el maestro
				while (tarjetaaux != null) {
					oos.writeObject(tarjetaaux);
					try {
						tarjetaaux = (TarjetaImp) oism.readObject();
					} catch (EOFException eof){
						tarjetaaux = null;
					} catch (IOException e) {
						tarjetaaux = null;
					}
				}
				//Se ha acabado el fichero maestro pero no el de movimientos
				
				while (tarjetamovaux != null) {
					oos.writeObject(tarjetamovaux);
					try {
						tarjetamovaux = (TarjetaImp) oismo.readObject();
					} catch(EOFException eof){
						tarjetamovaux = null;
					} catch (IOException e) {
						System.out.println(e);
					}
				}
					
				
			
			}catch(EOFException eofe){

			} catch (IOException ioe) {
				System.out.println(ioe);
			} catch (ClassNotFoundException e) {
				System.out.println(e);
			} finally {
				try {
					if(oismo!=null){
						oismo.close();
						fismo.close();
					}
					if(oism!=null){
						oism.close();
						fism.close();
					}
					if(oos!=null){
						oos.close();
						fos.close();
					}
					
					
						//Ahora eliminamos el antiguo archivo de Maestro
						fmaestro.delete();
						fmovimiento.delete();
					
						//y renombramos el archivo maestro nuevo con el nombre del antiguo
						fmaestronuevo.renameTo(fmaestro);
					

					
				} catch (IOException ioe) {
					System.out.println(ioe);
				}
			}

		}

	}
	

	
	
	
	
	
	/*ORDENACION*/
	
	/* ordenacionExternaMezcla
	 * 
	 * Cabecera: void ordenacionExternaMezcla(String fichero, String fAux1, String fAux2)
	 * Comentario: Haciendo uso de dos ficheros auxiliares se ordenará un fichero
	 * Precondición: Nada
	 * Entrada: Tres cadenas (ficheros)
	 * Salida: Un fichero ordenado
	 * Postcondición: El fichero original queda ordenado
	 */
	public  void ordenacionExternaMezcla(String fichero) {
		int secuencia = 1;
		int registros = 0;
		GestionFicherosTarjetas gf=new GestionFicherosTarjetas();
		
		String fAux1 = ("TarjetasAux1.dat");
		String fAux2 = ("TarjetasAux2.dat");
		
		File ficheroAux1 = new File(fAux1);
		File ficheroAux2 = new File(fAux2);
		
		registros = gf.contarRegistros(fichero);		
		while(secuencia <= registros) {
			partirFichero(fichero, fAux1, fAux2, secuencia);
			mezclarFichero(fichero, fAux1, fAux2, secuencia);			
			secuencia = secuencia*2;
		}
		ficheroAux1.delete();
		ficheroAux2.delete();//ESTE NO SE BORRA
	}
	
	/* partirFichero
	 * 
	 * Cabecera: void partirFichero(String fichero, String fAux1, String fAux2, int secuencia)
	 * Comentario: Dado un fichero y una secuencia, se partirá en dos ficheros
	 * Precondición: Nada
	 * Entrada: Tres cadenas (representando los ficheros) y un entero (secuencia)
	 * Salida: Los dos ficheros auxiliares (fAux1 y fAux2) se repartirán los datos del fichero original
	 * Postcondición: Los dos ficheros auxiliares tendrán el contenido del fichero original distribuido entre ellos
	 */
	public  void partirFichero(String fichero, String fAux1, String fAux2, int secuencia) {
		FileInputStream ficheroFIS = null;
		ObjectInputStream ficheroOIS = null;
		
		FileOutputStream fAux1FOS = null;
		ObjectOutputStream fAux1OOS = null;
		
		FileOutputStream fAux2FOS = null;
		MiObjectOutputStream fAux2OOS = null;
		
		TarjetaImp registro = null;
		
		try {
			ficheroFIS = new FileInputStream(fichero);
			ficheroOIS = new ObjectInputStream(ficheroFIS);
			
			fAux1FOS = new FileOutputStream(fAux1);
			fAux1OOS = new ObjectOutputStream(fAux1FOS);
			
			fAux2FOS = new FileOutputStream(fAux2,true);
			fAux2OOS = new MiObjectOutputStream(fAux2FOS);
			
			registro = (TarjetaImp) ficheroOIS.readObject();
			
			while(registro != null) {			
				for (int i = 0; i < secuencia && registro != null; i++) {			
					fAux1OOS.writeObject(registro);
					registro = (TarjetaImp) ficheroOIS.readObject();
				}			
				for (int i = 0; i < secuencia && registro != null; i++) {
					fAux2OOS.writeObject(registro);
					registro = (TarjetaImp) ficheroOIS.readObject();
				}
			}
		} catch (IOException e) {
			registro = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fAux1OOS != null) {
					fAux1OOS.close();
					fAux1FOS.close();
				}
				if (fAux2OOS != null) {
					fAux2OOS.close();
					fAux2FOS.close();
				}		
				if (ficheroOIS != null) {
					ficheroOIS.close();
					ficheroFIS.close();
				}		
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/* mezclarFichero
	 * 
	 * Cabecera: void mezclarFichero(String fichero, String fAux1, String fAux2, int secuencia)
	 * Comentario: Dados dos ficheros se mezclarán de forma ordenada en un solo fichero (tambien provisto)
	 * Precondición: Nada
	 * Entrada: Tres cadenas (ficheros) y un entero
	 * Salida: Un fichero que contendrá toda la información de los auxiliares
	 * Postcondición: Los dos ficheros auxiliares tendrán el contenido del fichero original distribuido entre ellos
	 */	
	public static void mezclarFichero(String fichero, String fAux1, String fAux2, int secuencia) {
		FileOutputStream ficheroFOS = null;
		MiObjectOutputStream ficheroOOS = null;
		
		FileInputStream fAux1FIS = null;
		ObjectInputStream fAux1OIS = null;
		
		FileInputStream fAux2FIS = null;
		ObjectInputStream fAux2OIS = null;
		
		TarjetaImp registro1 = null;
		TarjetaImp registro2 = null;
		
		int contador1 = 0;
		int contador2 = 0;
		
		try {
			ficheroFOS = new FileOutputStream(fichero,true);
			ficheroOOS = new MiObjectOutputStream(ficheroFOS);
			
			fAux1FIS = new FileInputStream(fAux1);
			fAux1OIS = new ObjectInputStream(fAux1FIS);
			
			fAux2FIS = new FileInputStream(fAux2);
			fAux2OIS = new ObjectInputStream(fAux2FIS);
			
			registro1 = (TarjetaImp) fAux1OIS.readObject();
			registro2 = (TarjetaImp) fAux2OIS.readObject();
			
			while (registro1 != null && registro2 != null) {			
				for(contador1 = 0, contador2 = 0; contador1 < secuencia && contador2 < secuencia && registro1 != null && registro2 != null;) {				
					if(registro1.compareTo(registro2) < 0) {
						try {
							ficheroOOS.writeObject(registro1);
							registro1 = (TarjetaImp) fAux1OIS.readObject();
							contador1++;
						} catch (IOException e) {
							registro1 = null;
						}						
					}
					else {
						try {
							ficheroOOS.writeObject(registro2);
							registro2 = (TarjetaImp) fAux2OIS.readObject();
							contador2++;
						} catch (IOException e) {
							registro2 = null;
						}						
					}
				}
				
				if(contador1 < secuencia) {
					try {
						while (registro1 != null && contador1 < secuencia) {						
							ficheroOOS.writeObject(registro1);
							registro1 = (TarjetaImp) fAux1OIS.readObject();
							contador1++;
						}
					} catch (IOException e) {
						registro1 = null;												
					}
				}
				else {
					try {
						while (registro2 != null && contador2 < secuencia) {						
							ficheroOOS.writeObject(registro2);
							registro2 = (TarjetaImp) fAux2OIS.readObject();
							contador2++;
						}
					} catch (IOException e) {
						registro2 = null;
					}
				}
				
				if(registro1 == null) {
					try {
						while (registro2 != null) {						
							ficheroOOS.writeObject(registro2);
							registro2 = (TarjetaImp) fAux2OIS.readObject();
						}
					} catch (IOException e) {
						registro2 = null;
					}						
				}
				if(registro2 == null) {
					try {
						while (registro1 != null) {						
							ficheroOOS.writeObject(registro1);
							registro1 = (TarjetaImp) fAux1OIS.readObject();
						}
					} catch (IOException e) {
						registro1 = null;
					}					
				}
			}
		} catch (IOException e) {
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ficheroOOS != null) {
					ficheroOOS.close();
					ficheroFOS.close();
				}
				if (fAux1OIS != null) {
					fAux1OIS.close();
					fAux1FIS.close();
				}		
				if (fAux2OIS != null) {
					fAux2OIS.close();
					fAux2FIS.close();
				}		
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}