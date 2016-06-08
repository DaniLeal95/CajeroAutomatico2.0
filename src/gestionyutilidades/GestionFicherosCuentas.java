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
import datos.CuentaImp;
import datos.TarjetaImp;

public class GestionFicherosCuentas  {



	/*
	 * Metodo mostrarcuentas
	 * 	Breve Comentario:
	 * 		Este metodo mostrara por pantalla todos las cuentas que tenemos registrados en el fichero
	 * 		
	 * 	Cabecera:
	 * 		void mostrarCuentas(String nombreFichero)
	 * 	Precondiciones:
	 * 		el fichero debera estar creado, de no estarlo saltara una excepcion de fichero no encontrado
	 * 	Entradas:
	 * 		El nombre del fichero
	 * 	Salidas:
	 * 		Nada
	 * 	Postcondiciones:
	 * 		Nada
	 * */
	public void mostrarCuentas(String nombreFichero){
		File fmaestro=null;
		FileInputStream fismaestro=null;
		ObjectInputStream oismaestro=null;
		Utilidades u=new Utilidades();
		try{
			fmaestro=new File(nombreFichero);
			fismaestro=new FileInputStream(fmaestro);
			oismaestro=new ObjectInputStream(fismaestro);
			
				for(int i=0;i<u.contarRegistros(nombreFichero);i++){
					CuentaImp cuenta=(CuentaImp)oismaestro.readObject();
					System.out.println(cuenta.toString());
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


	/*Escribir Movimiento 
	 * 	Breve comentario:
	 * 		El metodo escribirá en el fichero un objeto CuentaImp que recibirá por parametros 
	 * 	Cabecera:
	 * 		void escribirCuenta(CuentaImp c)
	 * 	Precondiciones:
	 * 		Nada
	 * 	Entradas:
	 * 		un objeto CuentaImp
	 * 	Salidas:
	 * 		Nada 
	 * 	Postcondiciones:
	 * 		Escribirá el objeto en el fichero.
	 * */
	public void escribirMovimiento(String nombreFichero,CuentaImp c){
		
		File f=null;
		FileOutputStream fos=null;
		MiObjectOutputStream moos=null;
		ObjectOutputStream oos=null;
		
		try{
			f=new File(nombreFichero);
			//SI EL FICHERO DE MOVIMIENTOS EXISTE QUIERE DECIR QUE TENEMOS QUE ESCRIBIR SIN CABECERA
			if(f.exists()){
				fos=new FileOutputStream(f,true);
				moos=new MiObjectOutputStream(fos);
				moos.writeObject(c);
			}else{
				//SI NO, TENEMOS QUE ESCRIBIR CON CABECERA
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
	 *	ObtenerTarjeta
	 *	Breve Comentario: 
	 *		El metodo buscara en los ficheros recibidos por parametros,
	 *		y retornara una CuentaImp actualizada, segun un numCuenta recibido en los parametros.
	 *	Cabecera:
	 *		CuentaImp obtenerCuenta(long numCuenta,String nombreFicheroMaestro,String nombreFicheroMovimiento)
	 *	Precondiciones:
	 *	Almenos el fichero Maestro Debera EstarCreado. Si no saltara una excepcion de fichero no encontrado	
	 *  ,si el numCuenta no corresponde a ninguna cuenta o esa Cuenta Esta dada de baja, retornara null.
	 *	Entradas:
	 *		un enterolargo, y dos cadenas
	 *	Salidas:
	 *		una CuentaImp
	 *	Postcondiciones:
	 *		CuentaImp retornara asociada al nombre -> FUNCION.
	 * */
	
	//RESGUARDO
//	public CuentaImp obtenerCuenta(long numCuenta,String nombreFicheroMaestro,String nombreFicheroMovimiento){
//		CuentaImp cuenta=null;
//		System.out.println("EN RESGUARDO");
//		return cuenta;
//	}
	
	public CuentaImp obtenerCuenta(long numCuenta,String nombreFicheroMaestro,String nombreFicheroMovimiento){
		CuentaImp cuenta=null;
		Utilidades u=new Utilidades();
		
		File fmaestro=new File(nombreFicheroMaestro);
		File fmovimiento=new File(nombreFicheroMovimiento);
		
		FileInputStream fismaestro=null;
		FileInputStream fismovimiento=null;
		
		ObjectInputStream oismaestro=null;
		ObjectInputStream oismovimiento=null;
		
		try{
			
			//Si el fichero de movimiento no existe solo tenemos que mirar en el maestro
			if(!fmovimiento.exists()){
				boolean encontrado=false;
				fismaestro=new FileInputStream(fmaestro);
				oismaestro=new ObjectInputStream(fismaestro);
				
				for(int i=0;i<u.contarRegistros(nombreFicheroMaestro) && !encontrado;i++){
					CuentaImp c=(CuentaImp) oismaestro.readObject();
					if(c.getNumCuenta()==numCuenta){
						cuenta=c;
						encontrado=true;
					}
				}
			}
			//Si el fichero de movimiento si existe miraremos primero en el maestro y despues en el de movimiento
			else{
				boolean encontrado=false;
				boolean baja=false;
				fismaestro=new FileInputStream(fmaestro);
				oismaestro=new ObjectInputStream(fismaestro);
				
				fismovimiento=new FileInputStream(fmovimiento);
				oismovimiento=new ObjectInputStream(fismovimiento);
				
				for(int i=0;i<u.contarRegistros(nombreFicheroMaestro) && !encontrado;i++){
					CuentaImp c=(CuentaImp) oismaestro.readObject();
					if(c.getNumCuenta()==numCuenta){
						cuenta=c;
						encontrado=true;
					}
				}
				
				//Aqui no me sirve el encontrado porque tengo que mirar 
				for(int i=0;i<u.contarRegistros(nombreFicheroMovimiento) && !baja;i++){
					CuentaImp c=(CuentaImp) oismaestro.readObject();
					if(c.getNumCuenta()==numCuenta && encontrado){
						//SI LA CUENTA ESTA DADA DE BAJA TIENE QUE RETORNAR NULL
						if(c.getidCliente()!=-1){
							cuenta.setSaldo(cuenta.getSaldo()+c.getSaldo());
						}else{
							cuenta=null;
							baja=true;
						}
						
					}
					else if(c.getNumCuenta()==numCuenta && !encontrado){
						//SI LA CUENTA ESTA DADA DE BAJA TIENE QUE RETORNAR NULL
						if(c.getidCliente()!=-1){
							cuenta=c;
						}else{
							cuenta=null;
							baja=true;
						}
					}
				}
				
			}
			
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
		
		
		return cuenta;
	}
	
/*DarDeBajaTarjetas
 * Breve Comentario:
 * 		El metodo escribirá en el fichero de movimientos de tarjetas, un registro por cada tarjeta con el numeroDeCuenta
 * 		igual al que recibimos por parametros, y la cambiaremos por -1 (Que significa dada de baja)
 * 
 * Cabecera:
 * 		void dardeBajaTarjetas(long numCuenta,String nombreFicheroMaestro,String nombreFicheroMovimiento)
 * 
 * Precondiciones:
 * 		Almenos el fichero maestro debera estar creado, si no saltara una excepcion de fichero no encontrado
 * Entradas:
 * 		un long (numCuenta) y dos cadenas(nombreFicheros)
 * Salidas:
 * 		nada
 * Postcondiciones:
 * 		El archivo de TarjetaMovimientos, quedara modificado.
 * */
	
	//public void dardeBajaTarjetas(long numCuenta,String nombreFicheroMaestro,String nombreFicheroMovimiento){
	//	System.out.println("EN RESGUARDO");
	//}
	public void dardeBajaTarjetas(long numCuenta,String nombreFicheroMaestro,String nombreFicheroMovimiento){
		Utilidades u=new Utilidades();
		GestionFicherosTarjetas gft=new GestionFicherosTarjetas();
		File fmaestro=new File(nombreFicheroMaestro);
		File fmovimiento=new File(nombreFicheroMovimiento);
		
		FileInputStream fismaestro=null;
		ObjectInputStream oismaestro=null;
		
		FileOutputStream fosmovimiento=null;
		ObjectOutputStream oosmovimiento=null;
		
		try{
				//Actualizamos Tarjetas.
				gft.actualizaTarjetas(nombreFicheroMaestro, nombreFicheroMovimiento);
				//Y luego hacemos las operaciones.
				fismaestro=new FileInputStream(fmaestro);
				oismaestro=new ObjectInputStream(fismaestro);
				
				fosmovimiento=new FileOutputStream(fmovimiento,true);
				oosmovimiento=new ObjectOutputStream(fosmovimiento);
				
				for(int i=0;i<u.contarRegistros(nombreFicheroMaestro);i++){
					TarjetaImp t=(TarjetaImp)oismaestro.readObject();
					//Si el numero de Cuenta de la tarjeta es igual al numCuenta que se va a dar de baja
					//le asignamos un -1 al numCuenta de la tarjeta (que significa tarjeta dada de baja)
					//Y la escribimos en el fichero de movimiento
					if(t.getnumCuenta()==numCuenta){
						t.setnumCuenta(-1);
						oosmovimiento.writeObject(t);
					}
				}
			/**NO VOLVER A INTENTARLO, LOCURA MAXIMO*/
//			//Si el fichero de movimiento existe mirare en el maestro y luego en el de movimiento
//			else{
//				fismaestro=new FileInputStream(fmaestro);
//				oismaestro=new ObjectInputStream(fismaestro);
//				
//				fosmovimiento=new FileOutputStream(fmovimiento,true);
//				moosmovimiento=new MiObjectOutputStream(fosmovimiento);
//			
//				File faux=new File("ficheromovaux.dat");
//				FileOutputStream fosaux=new FileOutputStream(faux,true);
//				ObjectOutputStream oosaux=new ObjectOutputStream(fosaux);
//				
//				for(int i=0;i<u.contarRegistros(nombreFicheroMaestro);i++){
//					TarjetaImp t=(TarjetaImp)oismaestro.readObject();
//					//Si el numero de Cuenta de la tarjeta es igual al numCuenta que se va a dar de baja
//					//le asignamos un -1 al numCuenta de la tarjeta (que significa tarjeta dada de baja)
//					//Y la escribimos en el fichero de movimiento
//					if(t.getnumCuenta()==numCuenta){
//						t.setnumCuenta(-1);
//						oosaux.writeObject(t);
//					}
//				}
//				for(int i=0;i<u.contarRegistros(nombreFicheroMovimiento);i++){
//					TarjetaImp t=(TarjetaImp)oismaestro.readObject();
//					//Si el numero de Cuenta de la tarjeta es igual al numCuenta que se va a dar de baja
//					//le asignamos un -1 al numCuenta de la tarjeta (que significa tarjeta dada de baja)
//					//Y la escribimos en el fichero de movimiento
//					if(t.getnumCuenta()==numCuenta){
//						t.setnumCuenta(-1);
//						oosmovimiento.writeObject(t);
//					}
//				}
//			}
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
	
	
	
/*
 * actualiza Cuentas
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

	public void actualizaCuentas(String nombreFicheroMaestro,String nombreFicheroMovimiento) {
		Utilidades u=new Utilidades();
		ordenacionExternaMezcla(nombreFicheroMaestro);
		ordenacionExternaMezcla(nombreFicheroMovimiento);
		File fmaestro = new File(nombreFicheroMaestro);
		File fmovimiento = new File(nombreFicheroMovimiento);
		File fmaestronuevo = new File("CuentasMaestroNuevo.dat");
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
				
				CuentaImp cuentaaux = (CuentaImp) oism.readObject();
				CuentaImp cuentamovaux = (CuentaImp) oismo.readObject();
				for (int i = 0; i < numregistrosmaestro && i < numregistrosmovimiento; i++) {
						boolean activo=true;
					
					
					if (cuentaaux.compareTo(cuentamovaux) == 0) {
						// Si la cuenta tiene un -1
						// quiere
						// decir que ya no esta activa.
						// BAJA Y MODIFICACION
						while(activo==true && cuentaaux.compareTo(cuentamovaux) == 0 ){
							if (!(cuentamovaux.getidCliente()==-1)) {
								cuentaaux.setSaldo(cuentaaux.getSaldo()+cuentamovaux.getSaldo());
								cuentaaux.setidCliente(cuentamovaux.getidCliente());
							}else{
								dardeBajaTarjetas(cuentamovaux.getNumCuenta(), "TarjetasMaestro.dat", "TarjetasMovimiento.dat");
								activo=false;
							}
					
							try{
								cuentamovaux = (CuentaImp) oismo.readObject();
							}catch(IOException ioe){
								cuentamovaux = null;
							}
						}
						//escribimos fuera del bucle para guardar la cuenta con todas las actualizaciones
						if(activo)oos.writeObject(cuentamovaux);
							
							//Y leemos los dos siguientes registros.
						try{
							cuentaaux = (CuentaImp) oism.readObject();
						}catch(IOException ioe){
							cuentaaux = null;
						}
						
					
					} else if (cuentamovaux.compareTo(cuentaaux) > 0) {
						// SI no hay ninguna actualizacion
						oos.writeObject(cuentaaux);
						
						//Y como no hay ninnguna actualizacion
						//solo tenemos que volver a leer del fichero maestro
						try{
							cuentaaux = (CuentaImp) oism.readObject();
						}catch(IOException e){
							cuentaaux=null;
						}
					} else {
							CuentaImp cuentamovaux2=(CuentaImp) oismo.readObject();
						//alta
								while(cuentamovaux.compareTo(cuentamovaux2)==0){
								
								if(!(cuentamovaux.getidCliente()==-1) && !(cuentamovaux2.getidCliente()==-1)){
									cuentamovaux.setSaldo(cuentamovaux.getSaldo()+cuentamovaux2.getSaldo());
									cuentamovaux.setidCliente(cuentamovaux2.getidCliente());
								}
								cuentamovaux2=(CuentaImp) oismo.readObject();
							}
							if(!(cuentamovaux.getidCliente()==-1))
								oos.writeObject(cuentamovaux);
							else
							dardeBajaTarjetas(cuentamovaux.getNumCuenta(), "TarjetasMaestro.dat", "TarjetasMovimiento.dat");
							cuentamovaux = cuentamovaux2;
					}
						

				} // fin Para
				
				//se ha acabado el fichero movimiento pero no el maestro
				//ClienteImp aux2=(ClienteImp) oism.readObject();
				while (cuentaaux != null) {
					oos.writeObject(cuentaaux);
					try {
						cuentaaux = (CuentaImp) oism.readObject();
					} catch (EOFException eof){
						cuentaaux = null;
					} catch (IOException e) {
						cuentaaux = null;
					}
				}
				//Se ha acabado el fichero maestro pero no el de movimientos
				boolean valido=true;
				while (cuentamovaux != null) {
				
					CuentaImp cuentamovaux2=(CuentaImp) oismo.readObject();
					//alta
							while(cuentamovaux.compareTo(cuentamovaux2)==0 && valido){
							
							if(!(cuentamovaux.getidCliente()==-1) && !(cuentamovaux2.getidCliente()==-1)){
								
								cuentamovaux.setSaldo(cuentamovaux.getSaldo()+cuentamovaux2.getSaldo());
								cuentamovaux.setidCliente(cuentamovaux2.getidCliente());
							}
							else{
								valido=false;
							}
							try{
							cuentamovaux2=(CuentaImp) oismo.readObject();
							//Si la siguiente linea es fin de fichero, escribeme la cuenta
							}catch(EOFException eof){
								if(valido)oos.writeObject(cuentamovaux);
								valido=false;
							}
							
						}
						
							
						cuentamovaux = cuentamovaux2;
						if(cuentamovaux.getidCliente()!=-1)
							oos.writeObject(cuentamovaux);
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

		
		String fAux1 = ("CuentasAux1.dat");
		String fAux2 = ("CuentasAux2.dat");
		
		File ficheroAux1 = new File(fAux1);
		File ficheroAux2 = new File(fAux2);
		File f=new File(fichero);
		
		registros = u.contarRegistros(fichero);		
		while(secuencia < registros) {
			//partimos
			partirFichero(fichero, fAux1, fAux2, secuencia);
			//vaciarmaestro.
			f.delete();
			f=new File(fichero);
			
			//mezclamos
			mezclarFichero(fichero, fAux1, fAux2, secuencia);	
			
			//vaciar auxiliares.
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
		
		CuentaImp registro = null;
		
		try {
			ficheroFIS = new FileInputStream(fichero);
			ficheroOIS = new ObjectInputStream(ficheroFIS);
			
			fAux1FOS = new FileOutputStream(fAux1);
			fAux1OOS = new ObjectOutputStream(fAux1FOS);
			
			fAux2FOS = new FileOutputStream(fAux2);
			fAux2OOS = new ObjectOutputStream(fAux2FOS);
			
			registro = (CuentaImp) ficheroOIS.readObject();
			
			while(registro != null) {			
				for (int i = 0; i < secuencia && registro != null; i++) {			
					fAux1OOS.writeObject(registro);
					registro = (CuentaImp) ficheroOIS.readObject();
				}			
				for (int i = 0; i < secuencia && registro != null; i++) {
					fAux2OOS.writeObject(registro);
					registro = (CuentaImp) ficheroOIS.readObject();
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
		
		CuentaImp registro1 = null;
		CuentaImp registro2 = null;
		
		int contador1 = 0;
		int contador2 = 0;
		
		try {
			ficheroFOS = new FileOutputStream(fichero,true);
			ficheroOOS = new ObjectOutputStream(ficheroFOS);
			
			fAux1FIS = new FileInputStream(fAux1);
			fAux1OIS = new ObjectInputStream(fAux1FIS);
			
			fAux2FIS = new FileInputStream(fAux2);
			fAux2OIS = new ObjectInputStream(fAux2FIS);
			
			registro1 = (CuentaImp) fAux1OIS.readObject();
			registro2 = (CuentaImp) fAux2OIS.readObject();
			
			while (registro1 != null && registro2 != null) {			
				for(contador1 = 0, contador2 = 0; contador1 < secuencia && contador2 < secuencia && registro1 != null && registro2 != null;) {				
					if(registro1.compareTo(registro2) < 0) {
						try {
							ficheroOOS.writeObject(registro1);
							registro1 = (CuentaImp) fAux1OIS.readObject();
							contador1++;
						} catch (IOException e) {
							registro1 = null;
						}						
					}
					else {
						try {
							ficheroOOS.writeObject(registro2);
							registro2 = (CuentaImp) fAux2OIS.readObject();
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
							registro1 = (CuentaImp) fAux1OIS.readObject();
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
							registro2 = (CuentaImp) fAux2OIS.readObject();
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
							registro2 = (CuentaImp) fAux2OIS.readObject();
						}
					} catch (IOException e) {
						registro2 = null;
					}						
				}
				if(registro2 == null) {
					try {
						while (registro1 != null) {						
							ficheroOOS.writeObject(registro1);
							registro1 = (CuentaImp) fAux1OIS.readObject();
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