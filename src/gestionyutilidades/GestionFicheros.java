package gestionyutilidades;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import cajero.MiObjectOutputStream;
import datos.ClienteImp;
import datos.CuentaImp;

public class GestionFicheros  {



	/*
	 * Metodo mostrarFicheromaestro
	 * 	Breve Comentario:
	 * 		Este metodo mostrara por pantalla todos los clientes que tenemos registrados en el fichero
	 * 		
	 * 	Cabecera:
	 * 		void mostrarFicheromaestro()
	 * 	Precondiciones:
	 * 		el fichero debera estar creado, de no estarlo saltara una excepcion de fichero no encontrado
	 * 	Entradas:
	 * 		El nombre del fichero
	 * 	Salidas:
	 * 		Nada
	 * 	Postcondiciones:
	 * 		Nada
	 * */
	public void mostrarFicheromaestro(){
		File f=null;
		FileInputStream fis=null;
		ObjectInputStream ois=null;
		
		try{
			f=new File("ClientesMaestro.dat");
			fis=new FileInputStream(f);
			ois=new ObjectInputStream(fis);
				ClienteImp cliente=(ClienteImp)ois.readObject();
				while(cliente!=null){
					System.out.println(cliente.toString());
					cliente=(ClienteImp)ois.readObject();
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
				if(ois!=null){
					ois.close();
					fis.close();
				}
				
			}catch(IOException ioe){
				System.out.println(ioe);
			}
		}
		
	}
	
	/*
	 * Obtener cuenta.
	 * 
	 * 	Breve comentario:
	 * 		Este metodo retornara una cuenta , dadas una id de cliente 
	 * 		y una id de cuenta
	 * 	Cabecera:
	 * 		CuentaImp obtenerCuenta(long idCliente,long idCuenta)
	 * 	Precondiciones:
	 * 		Nada, si no encuentra ninguna cuenta con esas id retornara null.
	 * 			
	 * 	Entradas:
	 * 		dos long con las ids de cliente y cuenta
	 * 	Salidas:
	 * 		Un objeto CuentaImp
	 * 	Postcondiciones:
	 * 		El objeto CuentaImp retornara asociado al nombre, funcion
	 * 
	 * 
	 * */
	public CuentaImp obtenerCuenta(long idCliente,long idCuenta){
		CuentaImp cuenta=null;
		
		File fmae=null;
		File fmov=null;
		
		FileInputStream fismae=null;
		FileInputStream fismov=null;
		
		ObjectInputStream oismae=null;
		ObjectInputStream oismov=null;
		boolean encontrado=false;
		ClienteImp aux=null;
		CuentaImp auxc=null;
		try{
			//fichero maestro
			fmae= new File("ClientesMaestro.dat");
			//fichero movimiento
			fmov=new File("ClientesMovimiento.dat");
			fismae=new FileInputStream(fmae);
			oismae=new ObjectInputStream(fismae);

				
			
			/*Si el fichero de movimiento no existe, no necesitamos mirar en el.
			 * Solo tendremos que buscar en el maestro,
			 * */
			if(!fmov.exists()){
			aux=(ClienteImp)oismae.readObject();
				while(aux!=null && encontrado==false){
					if(aux.getIdCliente()==idCliente){
						
						for(int i=0;i<aux.getCuentas().size();i++){
							
							if(aux.getCuentas().elementAt(i).getNumCuenta()==idCuenta){
								cuenta=aux.getCuentas().elementAt(i);
								encontrado=true;
							}
						}
					}
					aux=(ClienteImp)oismae.readObject();
				}
			}
			/*Si el fichero de movimientos si existe tenemos que mirar en los dos
			 * ficheros ,primero en el de movimiento y luego si en el de movimiento
			 *  no lo encuentra mirara en el maestro.
			 *  Si no el objeto quedara null;
			 * */
			else{
				
				fismov=new FileInputStream(fmov);
				oismov=new ObjectInputStream(fismov);
				
				auxc=(CuentaImp)oismov.readObject();
				/*Recorro el fichero entero porque puede haber mas de un movimiento
				 * entonces me interesa mirar todos los clientes ingresados 
				 * */
				while(auxc!=null){
					if(auxc.getNumCuenta()==idCuenta){
								cuenta=auxc;
								encontrado=true;
					}	
					auxc=(CuentaImp)oismov.readObject();
				}
				/*Si no se a encontrado el cliente en el de movimientos
				 * 	miraremos en el maestro
				 * */
				
				if(!encontrado){
					aux=(ClienteImp)oismae.readObject();
					while(aux!=null && !encontrado){
						if(aux.getIdCliente()==idCliente){
							
							for(int i=0;i<aux.getCuentas().size();i++){
								
								if(aux.getCuentas().elementAt(i).getNumCuenta()==idCuenta){
									cuenta=aux.getCuentas().elementAt(i);
									encontrado=true;
								}
							}
							
						}	
						aux=(ClienteImp)oismae.readObject();
					}
				}
			}
		}catch(EOFException eof){
		}catch(IOException ioe){
			System.out.println(ioe);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}finally{
			try{
				if(oismae!=null){
					oismae.close();	
					fismae.close();
				}
				
				if(oismov!=null){
					oismov.close();
					fismov.close();
				}
				
				
			}catch(IOException ioe){
				System.out.println(ioe);
			}
		}
		
		return cuenta;
	}
	
	
	/*Escribir Fichero ClienteMaestro
	 * 	Breve comentario:
	 * 		El metodo escribirá en el fichero ClienteMaestro un objeto clienteImp que recibirá por parametros 
	 * 	Cabecera:
	 * 		void escribirCliente(ClienteImp cliente)
	 * 	Precondiciones:
	 * 		Nada
	 * 	Entradas:
	 * 		un objeto ClienteImp
	 * 	Salidas:
	 * 		Nada 
	 * 	Postcondiciones:
	 * 		Escribirá el objeto en el fichero.
	 * */
	public void escribirCliente(ClienteImp cliente){
		boolean valida=true;
		Utilidades u= new Utilidades();
		File f=null;
		FileOutputStream fos=null;
		MiObjectOutputStream oos=null;
		try{
			f=new File("ClientesMaestro.dat");
			fos=new FileOutputStream(f,true);
			oos=new MiObjectOutputStream(fos);
			
			/*for(int i=0;i<cliente.getCuentas().size() && valida; i++){
				
				for(int j=0;j<cliente.getCuentas().elementAt(i).getTarjetas().size() && valida;j++){
					
					//si la llamada a validar Tarjeta es falso, no se podra registrar ese cliente.
					if(!u.validarTarjetaRegistrada
						(cliente.getCuentas().elementAt(i).
								getTarjetas().elementAt(j).getNumtarjeta())){
						
						valida=false;
					}
				}
			}
			
			if(valida)*/
				oos.writeObject(cliente);
			
		}catch(IOException ioe){
			System.out.println(ioe);
		}finally{
			
				try{
					if(oos!=null){
					oos.close();
					fos.close();
					}
				}catch(IOException ioe){
					System.out.println(ioe);
				}
			
		}
	}
	
	/*Escribir Fichero ClienteMaestro
	 * 	Breve comentario:
	 * 		El metodo escribirá en el fichero ("ClientesMovimiento.dat") un objeto Cuenta que recibirá por parametros 
	 * 	Cabecera:
	 * 		void escribirCliente(ClienteImp cliente)
	 * 	Precondiciones:
	 * 		Nada
	 * 	Entradas:
	 * 		un objeto CuentaImp
	 * 	Salidas:
	 * 		Nada 
	 * 	Postcondiciones:
	 * 		Escribirá el objeto en el fichero.
	 * */
	public void escribirMovimiento(CuentaImp c){
		
		File f=null;
		FileOutputStream fos=null;
		MiObjectOutputStream moos=null;
		
		try{
			f=new File("ClientesMovimiento.dat");
			fos=new FileOutputStream(f,true);
			moos=new MiObjectOutputStream(fos);
			
			moos.writeObject(c);
		}catch(IOException ioe){
			System.out.println(ioe);
		}finally{
			try{
				fos.close();
				moos.close();
			}catch(IOException ioe){
				System.out.println(ioe);
			}
		}
		
	}
	/*
	 * Mostrar fichero movimientos
	 * 	Breve Comentario:
	 * 		Este metodo mostrara por pantalla todos los movimientos que tenemos registrados en el fichero de movimientos
	 * 		
	 * 	Cabecera:
	 * 		 void mostrarFicheromovimiento()
	 * 	Precondiciones:
	 * 		el fichero debera estar creado, de no estarlo saltara una excepcion de fichero no encontrado
	 * 	Entradas:
	 * 		Nada
	 * 	Salidas:
	 * 		Nada
	 * 	Postcondiciones:
	 * 		Nada
	 * */
	public void mostrarFicheromovimiento(){
		File f=null;
		FileInputStream fis=null;
		ObjectInputStream ois=null;
		try{
			f=new File("ClientesMovimiento.dat");
			fis=new FileInputStream(f);
			ois=new ObjectInputStream(fis);
			
			CuentaImp cliente=(CuentaImp)ois.readObject();
			while(cliente!=null){
			System.out.println(cliente.toString());
			cliente=(CuentaImp)ois.readObject();
			
			}
		
		}catch(FileNotFoundException fnfe){
			System.out.println("Fichero no encontrado");
		}catch(EOFException eof){
			System.out.println("");
		}	catch (IOException ioe) {
			System.out.println(ioe);
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe);
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

	public void actualizaClientes() {
		
		File fmaestro = new File("ClientesMaestro.dat");
		File fmovimiento = new File("ClientesMovimiento.dat");
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

				
					

				for (int i = 0; i < numregistrosmaestro; i++) {
					// leemos el primero cliente del fichero maestro
					ClienteImp cmaestro = (ClienteImp) oism.readObject();
					if (cmaestro != null) {
						numregistrosmovimiento = this.contarRegistros("ClientesMovimiento.dat");
						for (int y = 0; y < numregistrosmovimiento; y++) {
							// Leemos el primer movimiento
							CuentaImp cmovimiento = (CuentaImp) oismo.readObject();
							if (cmovimiento != null) {

								for (int z = 0; z < cmaestro.getCuentas().size(); z++) {
									// Si el numero de cuenta es el mismo
									if (cmaestro.getCuentas().elementAt(z).getNumCuenta() == cmovimiento
											.getNumCuenta()) {

										// le damos al saldo Del Maestro el
										// saldo
										// que
										// hay en el de movimiento.
										cmaestro.getCuentas().elementAt(z).setSaldo(cmovimiento.getSaldo());
										oos.writeObject(cmaestro);
									}
								}
							}
						}
					}

				} // fin Para
					
				
			
			}catch(EOFException eofe){
				System.out.println("");
			} catch (IOException ioe) {
				System.out.println(ioe);
			} catch (ClassNotFoundException e) {
				System.out.println(e);
			} finally {
				try {
					if(oismo!=null){
						oismo.close();
						oism.close();
					}
					if(fismo!=null){
						fismo.close();
						fism.close();
					}
					if(fos!=null){
						oos.close();
						fos.close();
					}
					
					
						//Ahora eliminamos el antiguo archivo de Maestro
						if(fmaestro.delete()){
						}
					
						//y renombramos el archivo maestro nuevo con el nombre del antiguo
						fmaestronuevo.renameTo(fmaestro);
					

					
				} catch (IOException ioe) {
					System.out.println(ioe);
				}
			}

		}

	}
	

	/*
	 * Movimientos con 1 cuenta
	 * 	Breve comentario:
	 * 		Este metodo recibe una cuentaImp y  modifica el saldo de dicha cuenta, insertando en el fichero de movimientos la cuenta modificada.
	 * 	Cabecera:
	 * 		 void movimientocon1cuenta(CuentaImp c, int saldo)
	 * 	Precondiciones:
	 * 		nada
	 * 	Entradas:
	 * 		Un objeto CuentaImp a modificar y un double saldo.
	 * 	Salidas: 
	 * 		Escribira en el fichero
	 * 
	 * 
	 * */
	public void movimientocon1cuenta(CuentaImp c, double saldo){
		
		File fmaestro=new File("ClientesMaestro.dat");
		File fmovimiento=new File("ClientesMovimiento.dat");
		//PARA LEER
		FileInputStream fismae=null;
		FileInputStream fismov=null;
		ObjectInputStream oismov=null;
		ObjectInputStream oismae=null;
		//PARA ESCRIBIR
		FileOutputStream fos=null;
		MiObjectOutputStream moos=null; //Declaro los dos porque en el primer caso if si no hay archivo de movimiento lo tengo que crear con cabecera.
		ObjectOutputStream oos=null;
		
		boolean encontrado=false;//este indicador lo utilizo por si encuentra la cuenta en cuestion deje de buscar, solo sirve
								 //para el fichero maestro.
		
		//CUENTA AUXILIAR
		CuentaImp aux=null;
		
		if(!fmovimiento.exists()){
			
			try{
				fismae=new FileInputStream(fmaestro);		//Si el archivo de movimientos no existe, solo tenemos que mirar en el maestro
				oismae=new ObjectInputStream(fismae);
				
				fos=new FileOutputStream(fmovimiento);
				oos=new ObjectOutputStream(fos);
				
				aux=(CuentaImp)oismae.readObject();
				
				while(aux!=null && encontrado==false){
					if(aux.getNumCuenta()==c.getNumCuenta()){
						c.setSaldo(aux.getSaldo()+saldo);
						oos.writeObject(c);
						encontrado=true;
					}
					aux=(CuentaImp)oismae.readObject();
				}
			}catch(EOFException eofe){
				System.out.println();
			}catch(IOException ioe){
				System.out.println(ioe);
			} catch (ClassNotFoundException e) {
				System.out.println(e);
			}finally{
				try{
					oismae.close();
					fismae.close();
					oos.close();
					fos.close();
				}catch(IOException ioe){
					System.out.println(ioe);
				}
			}
			
		}//Fin if
		else{
			//ahora primero miramos en el de movimientos
			try{
				
				fismov=new FileInputStream(fmovimiento);
				oismov=new ObjectInputStream(fismov);
				
				fos=new FileOutputStream(fmovimiento,true);
				moos= new MiObjectOutputStream(fos);
				
				
				int numregistros=contarRegistros("ClientesMovimiento.dat");
																//Contamos los registros y leemos el archivo de movimientos 
				for(int i=0;i<numregistros;i++){
					aux=(CuentaImp)oismov.readObject();
					if(c.getNumCuenta()==aux.getNumCuenta()){
						encontrado=true;				
					}
				}													//si hemos encontrado alguna cuenta con ese numero de cuenta , le asignamos	el valor del ultimo movimiento
				if(aux!=null)c.setSaldo(aux.getSaldo()+saldo);		// mas el saldo que el usuario nos pasa por parametros.
			
				if (!encontrado) {

					fismae = new FileInputStream(fmaestro); // Si en el archivo de
															// movimientos no se encuentra esa cuenta, 
															// tenemos que mirar
															// en el maestro
					oismae = new ObjectInputStream(fismae);

				

					aux = (CuentaImp) oismae.readObject();
					while (aux != null && encontrado == false) {
						if (aux.getNumCuenta() == c.getNumCuenta()) {
							c.setSaldo(aux.getSaldo() + saldo);
							
							encontrado = true;
						}
						aux = (CuentaImp) oismae.readObject();
					}

				}
				if(encontrado)moos.writeObject(c);//Y aqui escribimos en el fichero de movimiento, la cuenta con el saldo modificado, si la hemos encotrado!
				
			}catch(EOFException eofe){
				System.out.println();
			}catch(ClassNotFoundException cnfe){
				System.out.println(cnfe);
			}catch(IOException ioe){
				System.out.println(ioe);
			}finally{
				try{
					if(oismae!=null){
						oismae.close();
						fismae.close();
					}
					if(oismov!=null){
						oismov.close();
						fismov.close();
					}
					if(moos!=null){
						moos.close();
						fos.close();
					}
				}catch(IOException ioe){
					System.out.println(ioe);
				}
			}
			
			
		}
	}
}