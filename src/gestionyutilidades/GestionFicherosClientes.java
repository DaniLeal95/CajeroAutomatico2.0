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
import datos.ClienteImp;
import datos.CuentaImp;

public class GestionFicherosClientes  {



	/*
	 * Metodo mostrarclientes
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
	public void mostrarClientes(String nombreFichero){
		File fmaestro=null;
		FileInputStream fismaestro=null;
		ObjectInputStream oismaestro=null;
		Utilidades u=new Utilidades();
		
		try{
			fmaestro=new File(nombreFichero);
			fismaestro=new FileInputStream(fmaestro);
			oismaestro=new ObjectInputStream(fismaestro);
			
				for(int i=0;i<u.contarRegistros(nombreFichero);i++){
					ClienteImp cliente=(ClienteImp)oismaestro.readObject();
					System.out.println(cliente.toString());
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

	
	/*EscribirClienteMovimiento
	 * 	Breve comentario:
	 * 		El metodo escribirá en el fichero un objeto Cliente que recibirá por parametros 
	 * 	Cabecera:
	 * 		void escribirClienteMovimiento(ClienteImp cliente)
	 * 	Precondiciones:
	 * 		Nada
	 * 	Entradas:
	 * 		un objeto ClienteImp y String nombreFichero
	 * 	Salidas:
	 * 		Nada 
	 * 	Postcondiciones:
	 * 		Escribirá el objeto en el fichero.
	 * */
	public void escribirClienteMovimiento(String nombreFichero,ClienteImp c){
		
		File f=null;
		FileOutputStream fos=null;
		MiObjectOutputStream moos=null;
		ObjectOutputStream oos=null;
		
		try{
			f=new File(nombreFichero);
			if(f.exists()){
				fos=new FileOutputStream(f,true);
				moos=new MiObjectOutputStream(fos);
				moos.writeObject(c);
			}else{
				fos=new FileOutputStream(f,true);
				oos=new ObjectOutputStream(fos);
				oos.writeObject(c);
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

	
	
/*
 * actualiza Clientes
 * 	Breve comentario: 
 * 		Este metodo mezcla dos archivos desordenados ( ClientesMaestro.dat y ClientesMovimientos.dat )
 * 			y los incluye en un archivo auxiliar mezclados
 * 	Cabecera:
 * 		void actulizaClientes()
 * 	Precondiciones:
 * 		nada
 * 	Entradas:
 * 		nada
 * 	Salidas:
 * 		El metodo escribirá en un archivo auxiliar
 * 	Postcondiciones:
 * 		El metodo escribira en un archivo auxiliar los datos de los dos archivos (maestro y movimientos) y los volcará 
 * 		sobre el auxiliar.
 * 
 * */

	public void actualizaClientes(String nombreFicheroMaestro,String nombreFicheroMovimiento) {
		Utilidades u=new Utilidades();
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
			numregistrosmaestro = u.contarRegistros("ClientesMaestro.dat");
			numregistrosmovimiento = u.contarRegistros("ClientesMovimiento.dat");
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
				
				ClienteImp clienteaux = (ClienteImp) oism.readObject();
				ClienteImp clientemovaux = (ClienteImp) oismo.readObject();
				for (int i = 0; i < numregistrosmaestro && i < numregistrosmovimiento; i++) {
						
					
					
					if (clienteaux.compareTo(clientemovaux) == 0) {
						// Si el cliente contiene un asterisco en el nombre
						// quiere
						// decir que ya no esta activa.
						// BAJA Y MODIFICACION
						if (!clientemovaux.getNombre().contains("*")) {
							oos.writeObject(clientemovaux);
						}
						//Y leemos los dos siguientes registros.
						try{
							clienteaux = (ClienteImp) oism.readObject();
						}catch(IOException ioe){
							clienteaux = null;
						}
						try{
							clientemovaux = (ClienteImp) oismo.readObject();
						}catch(IOException ioe){
							clientemovaux = null;
						}
					
					} else if (clientemovaux.compareTo(clienteaux) > 0) {
						// SI no hay ninguna actualizacion
						oos.writeObject(clienteaux);
						
						//Y como no hay ninnguna actualizacion
						//solo tenemos que volver a leer del fichero maestro
						try{
							clienteaux = (ClienteImp) oism.readObject();
						}catch(IOException e){
							clienteaux=null;
						}
					} else {
						//alta
						oos.writeObject(clientemovaux);
						try{
							clientemovaux = (ClienteImp) oismo.readObject();
						}catch(IOException ioe){
							clientemovaux = null;
						}
					}

				} // fin Para
				
				//se ha acabado el fichero movimiento pero no el maestro
				//ClienteImp aux2=(ClienteImp) oism.readObject();
				while (clienteaux != null) {
					oos.writeObject(clienteaux);
					try {
						clienteaux = (ClienteImp) oism.readObject();
					} catch (EOFException eof){
						clienteaux = null;
					} catch (IOException e) {
						clienteaux = null;
					}
				}
				//Se ha acabado el fichero maestro pero no el de movimientos
				
				while (clientemovaux != null) {
					oos.writeObject(clientemovaux);
					try {
						clientemovaux = (ClienteImp) oismo.readObject();
					} catch(EOFException eof){
						clientemovaux = null;
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
		Utilidades u=new Utilidades();
		
		String fAux1 = ("ClientesAux1.dat");
		String fAux2 = ("ClientesAux2.dat");
		
		File ficheroAux1 = new File(fAux1);
		File ficheroAux2 = new File(fAux2);
		File f=new File(fichero);
		
		registros = u.contarRegistros(fichero);		
		while(secuencia < registros) {
			//partimos.
			partirFichero(fichero, fAux1, fAux2, secuencia);
			//vaciarmaestro.
			f.delete();
			f=new File(fichero);
			//mezclamos
			mezclarFichero(fichero, fAux1, fAux2, secuencia);
			//vaciarauxiliares
			ficheroAux1.delete();
			ficheroAux2.delete();
			ficheroAux1=new File(fAux1);
			ficheroAux2=new File(fAux2);
			
			secuencia = secuencia*2;
		}
		ficheroAux1.delete();
		ficheroAux2.delete();
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
	public  void partirFichero(String nombrefichero, String nombrefAux1, String nombrefAux2, int secuencia) {
		File fichero=new File(nombrefichero);
		FileInputStream ficheroFIS = null;
		ObjectInputStream ficheroOIS = null;
		
		File fAux1=new File(nombrefAux1);
		FileOutputStream fAux1FOS = null;
		ObjectOutputStream fAux1OOS = null;
		
		File fAux2=new File(nombrefAux2);
		FileOutputStream fAux2FOS = null;
		ObjectOutputStream fAux2OOS = null;
		
		ClienteImp registro = null;
		
		try {
			ficheroFIS = new FileInputStream(fichero);
			ficheroOIS = new ObjectInputStream(ficheroFIS);
			
			fAux1FOS = new FileOutputStream(fAux1);
			fAux1OOS = new ObjectOutputStream(fAux1FOS);
			
			fAux2FOS = new FileOutputStream(fAux2);
			fAux2OOS = new ObjectOutputStream(fAux2FOS);
			
			registro = (ClienteImp) ficheroOIS.readObject();
			
			while(registro != null) {			
				for (int i = 0; i < secuencia && registro != null; i++) {			
					fAux1OOS.writeObject(registro);
					registro = (ClienteImp) ficheroOIS.readObject();
				}			
				for (int i = 0; i < secuencia && registro != null; i++) {
					fAux2OOS.writeObject(registro);
					registro = (ClienteImp) ficheroOIS.readObject();
				}
			}
		} catch(EOFException eof){
			
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
	public  void mezclarFichero(String fichero, String fAux1, String fAux2, int secuencia) {
		FileOutputStream ficheroFOS = null;
		ObjectOutputStream ficheroOOS = null;
		
		FileInputStream fAux1FIS = null;
		ObjectInputStream fAux1OIS = null;
		
		FileInputStream fAux2FIS = null;
		ObjectInputStream fAux2OIS = null;
		
		ClienteImp registro1 = null;
		ClienteImp registro2 = null;
		
		int contador1 = 0;
		int contador2 = 0;
		
		try {
			ficheroFOS = new FileOutputStream(fichero,true);
			ficheroOOS = new ObjectOutputStream(ficheroFOS);
			
			fAux1FIS = new FileInputStream(fAux1);
			fAux1OIS = new ObjectInputStream(fAux1FIS);
			
			fAux2FIS = new FileInputStream(fAux2);
			fAux2OIS = new ObjectInputStream(fAux2FIS);
			
			registro1 = (ClienteImp) fAux1OIS.readObject();
			registro2 = (ClienteImp) fAux2OIS.readObject();
			
			while (registro1 != null && registro2 != null) {			
				for(contador1 = 0, contador2 = 0; contador1 < secuencia && contador2 < secuencia && registro1 != null && registro2 != null;) {				
					if(registro1.compareTo(registro2) < 0) {
						try {
							ficheroOOS.writeObject(registro1);
							registro1 = (ClienteImp) fAux1OIS.readObject();
							contador1++;
						} catch (IOException e) {
							registro1 = null;
						}						
					}
					else {
						try {
							ficheroOOS.writeObject(registro2);
							registro2 = (ClienteImp) fAux2OIS.readObject();
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
							registro1 = (ClienteImp) fAux1OIS.readObject();
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
							registro2 = (ClienteImp) fAux2OIS.readObject();
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
							registro2 = (ClienteImp) fAux2OIS.readObject();
						}
					} catch (IOException e) {
						registro2 = null;
					}						
				}
				if(registro2 == null) {
					try {
						while (registro1 != null) {						
							ficheroOOS.writeObject(registro1);
							registro1 = (ClienteImp) fAux1OIS.readObject();
						}
					} catch (IOException e) {
						registro1 = null;
					}					
				}
			}
		} catch(EOFException eof){
			
		} catch (IOException e) {
			System.out.println(e);
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