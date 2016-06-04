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

import datos.ClienteImp;
//import datos.CuentaImp;

public class Utilidades {





	/*
	   * Estudio de la interfaz de escribirUltimaID
	   * 
	   * Comentario: 
	   * 	El metodo sobreescribir� la Id escrita en el fichero
	   * Cabecera: 
	   * 	void escribirUltimaID(long id)
	   * Precondiciones:Nada
	   * 	
	   * Entradas:Nada
	   * Salidas:sobreescribira el fichero
	   * Postcondiciones:
	   * 	nada
	   * */	
	
	public void escribirUltimaId(long id,String nombreFichero){
		File f=null;
		FileOutputStream fos= null;
		ObjectOutputStream oos=null;
		try{
			f=new File(nombreFichero);
			fos=new FileOutputStream(f);
			oos=new ObjectOutputStream(fos);
			
			oos.writeLong(id);
		}catch(IOException ioe){
			System.out.println(ioe);
		}finally{
			try{
				oos.close();
			}catch(IOException ioe){
				System.out.println(ioe);
			}
		}
		
	}
	
	/*
	   * Estudio de la interfaz de CogerUltimaID
	   * 
	   * Comentario: 
	   * 	El metodo recogera la Id escrita en ese fichero(long) y la devolvera asociada al nombre
	   * Cabecera: 
	   * 	long CogerUltimaID()
	   * Precondiciones:Nada
	   * 	
	   * Entradas:Nada
	   * Salidas:un long (ID)
	   * Postcondiciones:
	   * 	El long retornara asociado al nombre -> Funcion
	   * */
	public long cogerUltimaId(String nombreFichero){
		long id=0;
		File f=new File(nombreFichero);

		
		if(f.exists()){						//Si el archivo existe pues leemos de el la id
			FileInputStream fis=null;
			ObjectInputStream ois=null;
			try{
				fis=new FileInputStream(f);
				ois=new ObjectInputStream(fis);	
				
				long aux=ois.readLong();
				while(aux!=-1){
					id=aux;
					aux=ois.readLong();
				}
			}catch(EOFException eofe){
				
			}catch(IOException ioe){
				System.out.println(ioe);
			}
			finally{
				try{
					ois.close();
				}catch(IOException ioe){
					System.out.println(ioe);
				}
			}
		}// fin if

		
		return id;
	}
	
	/*
	 * validarPin
	 * 	
	 * Breve comentario:
	 * 		El metodo validara el pin de la tarjeta
	 * 			si el pin es correcto retornara true y false si no es correcto
	 * cabecera:
	 * 		boolean validarPin()
	 * Precondiciones:
	 * 		nada
	 * Entradas:
	 * 		nada
	 * Salida:
	 * 		boolean (validacion)
	 * Postcondiciones:
	 * 		el boolean retornara asociado al nombre -> Funcion
	 * 	
	 * */
	public boolean validarPin(String pin) {
		// Comprobacion del pin
		boolean valido = true;
		if (pin.length() == 4) {

			for (int i = 0; i < pin.length() && valido; i++) {
				
				if (!Character.isDigit(pin.charAt(i))) {
					valido = false;
				}
				
			}
		}
		else{
			valido=false;
		}
		return valido;
	}
	
	
	
	/*
	 * validar Tarjeta Registrada
	 * 
	 * Breve comentario:
	 * 		El metodo leera el fichero movimiento,
	 * 			si en el fichero de movimiento no esta dicha tarjeta o
	 * 			si el fichero de movimientos no existe mirara en el fichero maestro 
	 * 			 y comprobara que el numTarjeta
	 * 			a validar, no este asignado a ninguna cuenta.
	 * Cabecera:
	 * 		boolean validarTarjetaRegistrada(long numtarjeta)
	 * Precondiciones:
	 * 		El fichero maestro debe existir, de no existir saltara una excepcion
	 * Entradas:
	 * 		un long numtarjeta
	 * Salidas:
	 *      un boolean
	 * Postcondiciones:
	 * 		boolean retornara asociado al nombre -> Funcion
	 */
	
	 //Resguardo validarTarjetaRegistrada
	/*public boolean validarTarjetaRegistrada(long numtarjeta){
		boolean valida=true;
		System.out.println("validarTarjetaRegistrada en construccion");
		return valida;
	}*/
	
	public boolean validarTarjetaRegistrada(long numtarjeta){
		boolean valida=true;
		//GestionFicheros gf=new GestionFicheros();
		File fmae= new File("ClientesMaestro.dat");
		//File fmov= new File("ClientesMovimientos.dat");
		FileInputStream fismae= null;
		//FileInputStream fismov= null;
		ObjectInputStream oismae= null;
		//ObjectInputStream oismov= null;
		
		try{
			
			
			
			
			//si el fichero de movimientos no existe, solo tenemos que mirar
			//en el maestro.
			//if(!fmov.exists()){
				fismae= new FileInputStream(fmae);
				oismae= new ObjectInputStream(fismae);
				
				
				ClienteImp aux=(ClienteImp)oismae.readObject();
				while(aux!=null){
					//recorremos las cuentas de los clientes
					for(int i=0;i<aux.getCuentas().size() && valida ;i++){
						//recogemos las tarjetas de las cuentas de los clientes
						for(int j=0; j< aux.getCuentas().elementAt(i).getTarjetas().size() && valida ; j++){
							
							//si alguna tarjeta tiene el mismo numtarjeta, no sera valido asignarla a ninguna cuenta
							if(aux.getCuentas().elementAt(i).getTarjetas().elementAt(j).getNumtarjeta() == numtarjeta){
								valida = false;
							}
							
						}
						
					}
					aux=(ClienteImp)oismae.readObject();
				}
			//}
			
			//si el fichero de movimiento si existe, debemos mirar primero en el de movimientos
			/*else{
				
				
				fismov= new FileInputStream(fmov);
				oismov= new ObjectInputStream(fismov);
				//utilizamos el metodo de contar registros, porque en caso de que en el fichero de movimiento
				//no este, no nos salte la excepcion de findeficheros.
				for(int i=0; i<gf.contarRegistros("ClientesMovimiento.dat") && valida;i++){
					CuentaImp aux=(CuentaImp)oismov.readObject();
					
					//recorremos las tarjetas de la cuenta
					for(int j=0;j<aux.getTarjetas().size() && valida ; j++){
						//si el numtarjeta de alguna cuenta registrada es igual a la que queremos validar
						//no se podra incluir.
						if(aux.getTarjetas().elementAt(j).getNumtarjeta() == numtarjeta){
							valida=false;
						}
					}
					
				}
				//si en el fichero de movimientos no se a encontrado dicha tarjeta
				//miraremos en el maestro
				if(valida){
					fismae= new FileInputStream(fmae);
					oismae= new ObjectInputStream(fismae);
					
					ClienteImp aux=(ClienteImp) oismae.readObject();
					while(aux!=null){
						//recorremos las cuentas de los clientes
						for(int i=0;i<aux.getCuentas().size() && valida ;i++){
							//recogemos las tarjetas de las cuentas de los clientes
							for(int j=0; j< aux.getCuentas().elementAt(i).getTarjetas().size() && valida ; j++){
								
								//si alguna tarjeta tiene el mismo numtarjeta, no sera valido asignarla a ninguna cuenta
								if(aux.getCuentas().elementAt(i).getTarjetas().elementAt(j).getNumtarjeta() == numtarjeta){
									valida = false;
								}
								
							}
							
						}
						aux=(ClienteImp) oismae.readObject();
					}
					
					
				}
				
			}*/
			
			
		}catch(EOFException eof){
			
		}catch(ClassNotFoundException cnfe){
			System.out.println(cnfe);
		}catch(FileNotFoundException fnfe){
			System.out.println(fnfe);
		}catch(IOException ioe){
			System.out.println(ioe);

		}finally{
			try{
				/*if(oismov!=null){
					oismov.close();
					fismov.close();
				}*/
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
	

}

