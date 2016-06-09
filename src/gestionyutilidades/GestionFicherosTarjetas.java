package gestionyutilidades;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import datos.CuentaImp;
import datos.TarjetaImp;

public class GestionFicherosTarjetas  {



	/*
	 * Metodo mostrarTarjetas
	 * 	Breve Comentario:
	 * 		Este metodo mostrara por pantalla todos los clientes que tenemos registrados en el fichero
	 * 		que el metodo recibira por parametros
	 * 	Cabecera:
	 * 		void mostrarClientes(String nombreFichero)
	 * 	Precondiciones:
	 * 		El fichero debera contener objetos TarjetaImp de no ser asi,
	 * 			saltara una excepcion
	 * 	Entradas:
	 * 		El nombre del fichero
	 * 	Salidas:
	 * 		Nada
	 * 	Postcondiciones:
	 * 		Nada
	 * */
	/*
	 * Resguardo
	 * 	public void mostrarTarjetas(String nombreFichero){
	 * 		System.out.Println("Metodo en resguardo");
	 * 	}
	 * */
	public void mostrarTarjetas(String nombreFichero){
		Utilidades u=new Utilidades();
		File fmaestro=null;
		FileInputStream fismaestro=null;
		ObjectInputStream oismaestro=null;
		
		try{
			fmaestro=new File(nombreFichero);
			fismaestro=new FileInputStream(fmaestro);
			oismaestro=new ObjectInputStream(fismaestro);
			
				for(int i=0;i<u.contarRegistros(nombreFichero);i++){
					TarjetaImp tarjeta=(TarjetaImp)oismaestro.readObject();
					System.out.println(tarjeta.toString());
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
	/*
	 * MostrarTarjeta por Cuenta
	 * 	Breve Comentario:
	 * 		El metodo recibira un numCuenta, y mostrara por pantalla todas sus tarjetas actualizadas 
	 * 		registradas en el fichero recibido por parametros,
	 * 		, para ello deberemos de actualizar primero.
	 * 	Cabecera:
	 * 		void mostrarTarjetasporCuenta(long numCuenta,String nombreFicheroCuentaMaestro)
	 * 	Precondiciones:
	 * 		nada,si el numCuenta no corresponde a ninguna Cuenta no mostrara nada
	 * 	Entradas:
	 * 		un long(numCuenta) y una cadena(nombreFicheroCuentaMaestro)
	 * 	Salidas:
	 * 		Nada
	 * 	Postcondiciones:
	 * 		Nada	
	 * 
	 * */	
	public void mostrarTarjetasporCuenta(long numCuenta,String nombreFicheroCuentaMaestro){
		
		Utilidades u=new Utilidades();
		File f=null;
		FileInputStream fis=null;
		ObjectInputStream ois=null;
		boolean encontrado=false;
		
		try{
			actualizaTarjetas(nombreFicheroCuentaMaestro, "TarjetasMovimiento.dat");
			
			f=new File(nombreFicheroCuentaMaestro);
			fis=new FileInputStream(f);
			ois=new ObjectInputStream(fis);
			
			
			for(int i=0;i<u.contarRegistros(nombreFicheroCuentaMaestro) && !encontrado;i++){
				TarjetaImp tarjeta=(TarjetaImp)ois.readObject();
				if(tarjeta.getnumCuenta()==numCuenta){
					System.out.println(tarjeta.toString());
					encontrado=true;
				}
			}
			if(!encontrado)
				System.out.println("No posees ninguna tarjeta.");
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
				if(this.comprobarMovimiento(t.getNumtarjeta(), nombreFichero)){
					moos.writeObject(t);
				}else{
					System.out.println("En esa tarjeta ya ha echo una modificacion");
					System.out.println("Tiene que esperar a la proxima actualizacion");
				}
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
	/*
	 * ComprobarMovimientos
	 * 
	 * BreveComentario:
	 * 	El metodo leera el fichero movimiento que recibe por parametros,
	 * 	 y comprobara que la numTarjeta introducido no este en dicho fichero
	 * 	si no esta retornará True, de no estarlo retornara false.
	 * Cabecera:
	 * 	 boolean comprobarMovimiento(long numTarjeta,String nombreFicheroMovimiento)
	 * Precondiciones:
	 * 	Nada
	 * Entradas:
	 * 	un long (numTarjeta) y una cadena(nombreFicheroMovimiento)
	 * Salidas:
	 * 	boolean
	 * Postcondiciones:
	 * 	boolean retornara asociado al nombre-> Funcion 
	 * 
	 * */
	//RESGUARDO
//	public boolean comprobarMovimiento(long numTarjeta,String nombreFicheroMovimiento){
//		boolean posible=true;
//		System.out.println("En RESGUARDO");
//		return posible;
//	}
	public boolean comprobarMovimiento(long numTarjeta,String nombreFicheroMovimiento){
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
					TarjetaImp c=(TarjetaImp)ois.readObject();
					if(c.getNumtarjeta()==numTarjeta){
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
 *	ObtenerTarjeta
 *	Breve Comentario: 
 *		El metodo buscara en los ficheros recibidos por parametros,
 *		y retornara una TarjetaImp actualizada, segun un numTarjeta recibido en los parametros.
 *	Cabecera:
 *		TarjetaImp obtenerTarjeta(long numTarjeta,String nombreFicheroMaestro,String nombreFicheroMovimiento)
 *	
 *	Precondiciones:
 *		Almenos el fichero Maestro Debera EstarCreado. Si no saltara una excepcion de fichero no encontrado	
*  		,si el numTarjeta no corresponde a ninguna Tarjeta o esa Tarjeta Esta dada de baja, retornara null.
*  
 *	Entradas:
 *		un enterolargo, y dos cadenas
 *	
 *	Salidas:
 *		una TarjetaImp
 *
 *	Postcondiciones:
 *		TarjetaImp retornara asociada al nombre -> FUNCION.
 * *
 */
 //Resguardo	
//	public TarjetaImp obtenerTarjeta(long numTarjeta){
//		TarjetaImp tarjeta=null;
//		System.out.println("En Construccion.");
//		return tarjeta;
//	}
	
	public TarjetaImp obtenerTarjeta(long numTarjeta,String nombreFicheroMaestro,String nombreFicheroMovimiento){
		TarjetaImp tarjeta=null;
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
					TarjetaImp aux = (TarjetaImp) oismaestro.readObject();
					// Si lo encuentra deja de buscar en el fichero maestro
					// y asignale la tarjeta encontrada.
					if (aux.getNumtarjeta() == numTarjeta) {
						tarjeta = aux;
						encontrado = true;
					}
				}
			}
			else{
				//Si existe fichero de movimientos miraremos primero en el.
				fismovimiento = new FileInputStream(fmovimiento);
				oismovimiento = new ObjectInputStream(fismovimiento);
				for (int i = 0; i < u.contarRegistros(nombreFicheroMovimiento) && !encontrado; i++) {
					TarjetaImp aux = (TarjetaImp) oismovimiento.readObject();
					// Si lo encuentra deja de buscar en el fichero maestro
					// y asignale la tarjeta encontrada.
					if (aux.getNumtarjeta() == numTarjeta) {
						tarjeta = aux;
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
						TarjetaImp aux = (TarjetaImp) oismaestro.readObject();
						// Si lo encuentra deja de buscar en el fichero maestro
						// y asignale la tarjeta encontrada.
						if (aux.getNumtarjeta() == numTarjeta) {
							tarjeta = aux;
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
		
		
		return tarjeta;
	}

	/*
	 * Breve comentario:
	 * 	El metodo valida el numCuenta, consultando los ficheros de Cuentas que le pasamos por parametros 
	 * 	simulando como seria un Foreign Key en una base de datos.
	 * 	y retorna un boolean , true en caso de que sea posible utilizar ese numCuenta y false cuando no
	 * Cabecera:
	 * 	 boolean validarnumCuenta(long numCuenta, String ficheromaestro,String ficheromovimiento){
	 * Precondiciones:
	 * 	Almenos el fichero maestro debe existir, de no se asi saltara una excepcion
	 * 	
	 * Entradas:
	 * 		long numCuenta, y dos cadenas con los nombres de los ficheros
	 * Salidas:
	 * 		boolean
	 * Postcondiciones:
	 * 		boolean retornara asociado al nombre, Funcion.
	 * 
	 * */
	//RESGUARDO
//	public boolean validarnumCuenta(long numCuenta, String ficheromaestro,String ficheromovimiento){
//		boolean valida=false;
//		return valida;
//	}
	
	public boolean validarnumCuenta(long numCuenta, String ficheromaestro,String ficheromovimiento){
		boolean valida=false;
		Utilidades u=new Utilidades();
		
		File fmae=new File(ficheromaestro);
		File fmov=new File(ficheromovimiento);
		
		FileInputStream fismae=null;
		FileInputStream fismov=null;
		
		ObjectInputStream oismae=null;
		ObjectInputStream oismov=null;
		
		
		try{
			//si el fichero de movimiento no existe solo tenenmos que mirar en el fichero maestro
			if(!fmov.exists()){	
				fismae=new FileInputStream(fmae);
				oismae= new ObjectInputStream(fismae);
				
				CuentaImp aux=(CuentaImp)oismae.readObject();
				while(aux!=null && !valida){
					if(aux.getNumCuenta()== numCuenta){
						valida=true;
					}
					aux=(CuentaImp)oismae.readObject();
				}
			}
			else{
				//Si el fichero de movimiento existe primero miraremos en el de movimiento
				fismov=new FileInputStream(fmov);
				oismov=new ObjectInputStream(fismov);
				
				
				for(int i=0;i<u.contarRegistros("CuentasMovimiento.dat") && !valida;i++){
					CuentaImp aux=(CuentaImp) oismov.readObject();
					if(aux.getNumCuenta()== numCuenta){
						valida=true;
					}
				}
				//si no se a encontrado en el fichero de movimiento , lo miraremos en el maestro
				if(!valida){
					fismae= new FileInputStream(fmae);
					oismae=new ObjectInputStream(fismae);
					CuentaImp aux=(CuentaImp) oismae.readObject();
					
					while(aux!=null && !valida){
						if(aux.getNumCuenta()== numCuenta){
							valida=true;
						}
						aux=(CuentaImp) oismae.readObject();
					}
					
				}
				
			}
		}catch(FileNotFoundException fnfe){
		}catch(EOFException eofe){
			
		}catch (ClassNotFoundException cnfe){
			System.out.println(cnfe);
		}catch (IOException ioe) {
			System.out.println(ioe);
		}
		//cerramos ficheros.
		finally{
			try{
				if(oismov!=null){
					oismov.close();
					fismov.close();
				}
				if(oismae!=null){
					oismae.close();
					fismae.close();
				}
			}catch(IOException ioe){
				System.out.println(ioe);
			}
		}
		
		
		return valida;
	}
	
	
	/*
	 * Breve comentario:
	 * 	El metodo valida el numCuenta, consultando los ficheros de Tarjetas que le pasamos por parametros 
	 * 	y retorna un boolean , true en caso de que haya alguna tarjeta con ese numCuenta y false cuando no
	 * Cabecera:
	 * 	 boolean validartarjetaspornumCuenta(long numCuenta, String ficheromaestro,String ficheromovimiento){
	 * Precondiciones:
	 * 	Almenos el fichero maestro debe existir, de no se asi saltara una excepcion
	 * 	
	 * Entradas:
	 * 		long numCuenta, y dos cadenas con los nombres de los ficheros
	 * Salidas:
	 * 		boolean
	 * Postcondiciones:
	 * 		boolean retornara asociado al nombre, Funcion.
	 * 
	 * */
	//RESGUARDO
//	public boolean validarnumCuenta(long numCuenta, String ficheromaestro,String ficheromovimiento){
//		boolean valida=false;
//		return valida;
//	}
	
	public boolean validartarjetaspornumCuenta(long numCuenta, String ficheromaestro,String ficheromovimiento){
		boolean valida=false;
		Utilidades u=new Utilidades();
		
		File fmae=new File(ficheromaestro);
		File fmov=new File(ficheromovimiento);
		
		FileInputStream fismae=null;
		FileInputStream fismov=null;
		
		ObjectInputStream oismae=null;
		ObjectInputStream oismov=null;
		
		
		try{
			//si el fichero de movimiento no existe solo tenenmos que mirar en el fichero maestro
			if(!fmov.exists()){	
				fismae=new FileInputStream(fmae);
				oismae= new ObjectInputStream(fismae);
				
				TarjetaImp aux=(TarjetaImp)oismae.readObject();
				while(aux!=null && !valida){
					if(aux.getnumCuenta()== numCuenta){
						valida=true;
					}
					aux=(TarjetaImp)oismae.readObject();
				}
			}
			else{
				//Si el fichero de movimiento existe primero miraremos en el de movimiento
				fismov=new FileInputStream(fmov);
				oismov=new ObjectInputStream(fismov);
				
				
				for(int i=0;i<u.contarRegistros(ficheromovimiento) && !valida;i++){
					TarjetaImp aux=(TarjetaImp) oismov.readObject();
					if(aux.getnumCuenta()== numCuenta){
						valida=true;
					}
				}
				//si no se a encontrado en el fichero de movimiento , lo miraremos en el maestro
				if(!valida){
					fismae= new FileInputStream(fmae);
					oismae=new ObjectInputStream(fismae);
					TarjetaImp aux=(TarjetaImp) oismae.readObject();
					
					while(aux!=null && !valida){
						if(aux.getnumCuenta()== numCuenta){
							valida=true;
						}
						aux=(TarjetaImp) oismae.readObject();
					}
					
				}
				
			}
		}catch(FileNotFoundException fnfe){
			
		}catch(EOFException eofe){
			
		}catch (ClassNotFoundException cnfe){
			System.out.println(cnfe);
		}catch (IOException ioe) {
			System.out.println(ioe);
		}
		//cerramos ficheros.
		finally{
			try{
				if(oismov!=null){
					oismov.close();
					fismov.close();
				}
				if(oismae!=null){
					oismae.close();
					fismae.close();
				}
			}catch(IOException ioe){
				System.out.println(ioe);
			}
		}
		
		
		return valida;
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
		Utilidades u=new Utilidades();
		ordenacionExternaMezcla(nombreFicheroMaestro);
		ordenacionExternaMezcla(nombreFicheroMovimiento);
		File fmaestro = new File(nombreFicheroMaestro);
		File fmovimiento = new File(nombreFicheroMovimiento);
		File fmaestronuevo = new File("TarjetasMaestroNuevo.dat");
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
		
		String fAux1 = ("TarjetasAux1.dat");
		String fAux2 = ("TarjetasAux2.dat");
		
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
	 * Cabecera: void partirFichero(String nombrefichero, String nombrefAux1, String nombrefAux2, int secuencia)
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
		
		TarjetaImp registro = null;
		
		try {
			ficheroFIS = new FileInputStream(fichero);
			ficheroOIS = new ObjectInputStream(ficheroFIS);
			
			fAux1FOS = new FileOutputStream(fAux1);
			fAux1OOS = new ObjectOutputStream(fAux1FOS);
			
			fAux2FOS = new FileOutputStream(fAux2);
			fAux2OOS = new ObjectOutputStream(fAux2FOS);
			
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
		
		TarjetaImp registro1 = null;
		TarjetaImp registro2 = null;
		
		int contador1 = 0;
		int contador2 = 0;
		
		try {
			ficheroFOS = new FileOutputStream(fichero,true);
			ficheroOOS = new ObjectOutputStream(ficheroFOS);
			
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
		}catch(FileNotFoundException fnfe){
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