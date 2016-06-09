package gestionyutilidades;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import datos.ClienteImp;
import datos.CuentaImp;

public class GestionFicherosClientes  {

	/**
	 * Clase de gestion De Ficheros de los Clientes
	 * 
	 * 
	 * */
	


	/*
	 * Metodo mostrarclientes
	 * 	Breve Comentario:
	 * 		Este metodo mostrara por pantalla todos los clientes que tenemos registrados en el fichero
	 * 		
	 * 	Cabecera:
	 * 		void mostrarClientes(String nombreFichero)
	 * 	Precondiciones:
	 * 		el fichero debera estar creado, de no estarlo no mostrara nada
	 * 		los archivos deben contener objetos ClientesImp de no ser as� saltara una excepcion
	 * 	Entradas:
	 * 		El nombre del fichero
	 * 	Salidas:
	 * 		Nada
	 * 	Postcondiciones:
	 * 		Nada
	 * */
	/*RESGUARDO
	 * public void mostrarClientes(String nombreFichero){
	 * System.out.println("Metodo en construccion");
	 * }
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
					if(cliente.getIdCliente()!=1)
						System.out.println(cliente.toString());
				}
			
				
			
		}catch(FileNotFoundException fnfe){
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
	 * 		Escribirá el objeto en el fichero,el fichero sera modificado
	 * */
	/*RESGUARDO
	 * public void escribirClienteMovimiento(String nombreFichero,ClienteImp c){
	 * 	System.out.println("Metodo en construccion");
	 * }
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
				if(this.comprobarMovimiento(c.getIdCliente(), nombreFichero)){
					moos.writeObject(c);
				}else{
					System.out.println("Ese cliente ya ha echo una modificacion");
					System.out.println("Tiene que esperar a la proxima actualizacion");
				}
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
	 * ComprobarMovimientos
	 * 
	 * BreveComentario:
	 * 	El metodo leera el fichero movimiento que recibe por parametros,
	 * 	 y comprobara que la idCliente introducido no este en dicho fichero
	 * 	si no esta retornará True, de no estarlo retornara false.
	 * Cabecera:
	 * 	 boolean comprobarMovimiento(long idCliente,String nombreFicheroMovimiento)
	 * Precondiciones:
	 * 	los archivos deben contener objetos ClientesImp de no ser as� saltara una excepcion
	 * Entradas:
	 * 	un long (idcliente) y una cadena(nombreFicheroMovimiento)
	 * Salidas:
	 * 	boolean
	 * Postcondiciones:
	 * 	boolean retornara asociado al nombre-> Funcion 
	 * 
	 * */
	//RESGUARDO
//	public boolean comprobarMovimiento(long idCliente,String nombreFicheroMovimiento){
//		boolean posible=true;
//		System.out.println("En RESGUARDO");
//		return posible;
//	}
	public boolean comprobarMovimiento(long idCliente,String nombreFicheroMovimiento){
		boolean posible=true;
		File f=new File(nombreFicheroMovimiento);
		FileInputStream fis=null;
		ObjectInputStream ois=null;
		Utilidades u=new Utilidades();
		try{
			fis=new FileInputStream(f);
			ois=new ObjectInputStream(fis);
			
			if(f.exists()){
				for(int i=0;i<u.contarRegistros(nombreFicheroMovimiento) && posible;i++){
					ClienteImp c=(ClienteImp)ois.readObject();
					if(c.getIdCliente()==idCliente){
						posible=false;
					}
				}
			}
		}catch(FileNotFoundException fnfe){
			
		}catch(ClassNotFoundException cnfe){
			System.out.println(cnfe);
		}catch(IOException ioe){
			System.out.println(ioe);
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
			
		
		return posible;
	}
	
	/*
	 *	ObtenerCliente
	 *	Breve Comentario: 
	 *		El metodo buscara en los ficheros recibidos por parametros,
	 *		y retornara un ClienteImp actualizado, segun un idCliente recibido en los parametros.
	 *	Cabecera:
	 *		 ClienteImp obtenerCliente(long idCliente,String nombreFicheroMaestro,String nombreFicheroMovimiento)
	 *	
	 *	Precondiciones:
	 *		los archivos deben contener objetos ClientesImp de no ser as� saltara una excepcion
	 *		Almenos el fichero Maestro Debera Estar Creado.	
	*  		,si el idCliente no corresponde a ningun cliente o ese Cliente Esta dado de baja, retornara null.
	*  
	 *	Entradas:
	 *		un enterolargo, y dos cadenas
	 *	
	 *	Salidas:
	 *		una CuentaImp
	 *
	 *	Postcondiciones:
	 *		CuentaImp retornara asociada al nombre -> FUNCION.
	 * *
	 */
	 //Resguardo	
//		public ClienteImp obtenerCliente(long idCliente,String nombreFicheroMaestro,String nombreFicheroMovimiento){
//			ClienteImp cliente=null;
//			System.out.println("En Construccion.");
//			return cliente;
//		}
		
		public ClienteImp obtenerCliente(long idCliente,String nombreFicheroMaestro,String nombreFicheroMovimiento){
			ClienteImp cliente=null;
			Utilidades u=new Utilidades();
			boolean encontrado=false;
			
			File fmaestro=new File(nombreFicheroMaestro);
			File fmovimiento=new File(nombreFicheroMovimiento);
			
			FileInputStream fismaestro=null;
			FileInputStream fismovimiento=null;
			
			ObjectInputStream oismaestro=null;
			ObjectInputStream oismovimiento=null;
			
			try{
				//Si no hay fichero de movimientos solo mira en el maestro
				if (!fmovimiento.exists()) {
					fismaestro = new FileInputStream(fmaestro);
					oismaestro = new ObjectInputStream(fismaestro);
					// mientras haya registros y no se haya encontrado
					for (int i = 0; i < u.contarRegistros(nombreFicheroMaestro) && !encontrado; i++) {
						ClienteImp aux = (ClienteImp) oismaestro.readObject();
						// Si lo encuentra deja de buscar en el fichero maestro
						// y asignale la tarjeta encontrada.
						if (aux.getIdCliente() == idCliente) {
							cliente = aux;
							encontrado = true;
						}
					}
				}
				else{
					//Si existe fichero de movimientos miraremos primero en el.
					fismovimiento = new FileInputStream(fmovimiento);
					oismovimiento = new ObjectInputStream(fismovimiento);
					for (int i = 0; i < u.contarRegistros(nombreFicheroMovimiento) && !encontrado; i++) {
						ClienteImp aux = (ClienteImp) oismovimiento.readObject();
						// Si lo encuentra deja de buscar en el fichero maestro
						// y asignale la tarjeta encontrada.
						if (aux.getIdCliente() == idCliente) {
							cliente = aux;
							encontrado = true;
						}
					}
					
					//Si en el fichero de movimientos no existe ninguna modificacion suya
					//Tendremos que mirar en el maestro
					if(!encontrado){
						fismaestro = new FileInputStream(fmaestro);
						oismaestro = new ObjectInputStream(fismaestro);
						// mientras haya registros y no se haya encontrado
						for (int i = 0; i < u.contarRegistros(nombreFicheroMaestro) && !encontrado; i++) {
							ClienteImp aux = (ClienteImp) oismaestro.readObject();
							// Si lo encuentra deja de buscar en el fichero maestro
							// y asignale la tarjeta encontrada.
							if (aux.getIdCliente() == idCliente) {
								cliente = aux;
								encontrado = true;
							}
						}
					}
				}
				
				
			}catch(FileNotFoundException fnfe){
			}catch(ClassNotFoundException cnfe){
				System.out.println(cnfe);
			}catch(IOException ioe){
				System.out.println(ioe);
			}finally{
				try{
					if(oismaestro!=null){
						oismaestro.close();
						fismaestro.close();
					}
					if(oismovimiento!=null){
						oismovimiento.close();
						fismovimiento.close();
					}
				}catch(IOException ioe){
					System.out.println(ioe);
				}
			}
			
			
			return cliente;
		}
	
	
/*
 * actualiza Clientes
 * 	Breve comentario: 
 * 		Este metodo mezcla dos archivos desordenados que recibe por parametros
 * 			y los incluye en un archivo auxiliar mezclados
 * 	Cabecera:
 * 		void actulizaClientes(String nombreFicheroMaestro,String nombreFicheroMovimiento)
 * 	Precondiciones:
 * 		los archivos deben contener objetos ClientesImp de no ser as� saltara una excepcion
 * 		, de no existir los ficheros no hara nada
 * 	Entradas:
 * 		dos cadenas
 * 	Salidas:
		Nada
 * 	Postcondiciones:
 * 		El fichero Maestro se vera modificado
 * 		y el fichero movimiento eliminado
 * */
		/*Resguardo
		 * public void actualizaClientes(String nombreFicheroMaestro,String nombreFicheroMovimiento) {
			System.out.println("Metodo en construccion");
		}*/
		
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
				numregistrosmaestro = u.contarRegistros(nombreFicheroMaestro);
				numregistrosmovimiento = u.contarRegistros(nombreFicheroMovimiento);
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
							if (!(clientemovaux.getNombre().contains("*"))) {
								oos.writeObject(clientemovaux);
							}
							else{
								dardeBajaCuentas(clientemovaux.getIdCliente(), "CuentasMaestro.dat", "CuentasMovimiento.dat");
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
							if(!(clientemovaux.getNombre().contains("*")))
								oos.writeObject(clientemovaux);
							else
								dardeBajaCuentas(clientemovaux.getIdCliente(), "CuentasMaestro.dat", "CuentasMovimiento.dat");
							try{
								clientemovaux = (ClienteImp) oismo.readObject();
							}catch(IOException ioe){
								clientemovaux = null;
							}
						}

					} // fin Para
					
					//se ha acabado el fichero movimiento pero no el maestro
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
						//SI EL NOMBRE NO CONTIENE UN ASTERISCO LO ESCRIBIMOS,
						if(!clientemovaux.getNombre().contains("*"))
							oos.writeObject(clientemovaux);
						else
							//SI NO DAMOS DE BAJA SUS CUENTAS
							dardeBajaCuentas(clientemovaux.getIdCliente(), "CuentasMaestro.dat", "CuentasMovimiento.dat");
						try {
							clientemovaux = (ClienteImp) oismo.readObject();
						} catch(EOFException eof){
							clientemovaux = null;
						} catch (IOException e) {
							System.out.println(e);
						}
					}
						
					
				}catch(FileNotFoundException fnfe){
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
	
	/*DarDeBajaCuentas
	 * Breve Comentario:
	 * 		El metodo escribirá en el fichero de movimientos de Cuentas, un registro por cada Cuenta con el idCliente
	 * 		igual al que recibimos por parametros, y la cambiaremos por -1 (Que significa dada de baja)
	 * 
	 * Cabecera:
	 * 		 void dardeBajaCuentas(long idCliente,String nombreFicheroMaestro,String nombreFicheroMovimiento)
	 * 
	 * Precondiciones:
	 * 		los archivos deben contener objetos CuentaImp de no ser as� saltara una excepcion
	 * Entradas:
	 * 		un long (idCliente) y dos cadenas(nombreFicheros)
	 * Salidas:
	 * 		nada
	 * Postcondiciones:
	 * 		El archivo de CuentaMovimientos, quedara modificado.
	 * */
		
		//public void dardeBajaCuentas(long idCliente,String nombreFicheroMaestro,String nombreFicheroMovimiento){
		//	System.out.println("EN RESGUARDO");
		//}
		public void dardeBajaCuentas(long idCliente,String nombreFicheroMaestro,String nombreFicheroMovimiento){
			Utilidades u=new Utilidades();
			GestionFicherosCuentas gfc=new GestionFicherosCuentas();
			File fmaestro=new File(nombreFicheroMaestro);
			File fmovimiento=new File(nombreFicheroMovimiento);
			
			FileInputStream fismaestro=null;
			ObjectInputStream oismaestro=null;
			
			FileOutputStream fosmovimiento=null;
			ObjectOutputStream oosmovimiento=null;
			
			try{
					//Actualizamos Cuentas.
					gfc.actualizaCuentas(nombreFicheroMaestro, nombreFicheroMovimiento);
					//Y luego hacemos las operaciones.
					fismaestro=new FileInputStream(fmaestro);
					oismaestro=new ObjectInputStream(fismaestro);
					
					fosmovimiento=new FileOutputStream(fmovimiento,true);
					oosmovimiento=new ObjectOutputStream(fosmovimiento);
					
					for(int i=0;i<u.contarRegistros(nombreFicheroMaestro);i++){
						CuentaImp c=(CuentaImp)oismaestro.readObject();
						//Si el numero de Cuenta de la tarjeta es igual al numCuenta que se va a dar de baja
						//le asignamos un -1 al numCuenta de la tarjeta (que significa tarjeta dada de baja)
						//Y la escribimos en el fichero de movimiento
						if((c.getidCliente())==idCliente){
							c.setidCliente(-1);
							oosmovimiento.writeObject(c);
						}
					}

			}catch(FileNotFoundException fnfe){
			}catch(ClassNotFoundException cnfe){
				System.out.println(cnfe);
			}catch(IOException ioe){
				System.out.println(ioe);
			}
			finally{
				try{
					if(oismaestro!=null){
						oismaestro.close();
						fismaestro.close();
					}
				
					if(oosmovimiento!=null){
						oosmovimiento.close();
						fosmovimiento.close();
					}
				
					
				}catch(IOException ioe){
					System.out.println(ioe);
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
		}catch(FileNotFoundException fnfe){
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
		}catch (FileNotFoundException fnfe){
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