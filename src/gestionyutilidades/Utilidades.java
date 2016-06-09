package gestionyutilidades;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Utilidades {


/*clase de utilidades*/


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
	   * Salidas:
	   * 	nada
	   * Postcondiciones:
	   * 	sobreescribira el fichero
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
	   * Precondiciones:
	   * 	Nada, si el fichero no existe devolvera 0
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

	/*contarRegistros
	 * 
	 * Breve comentario:
	 * 		El metodo lee todo el fichero y retorna el numero de registros que hayan en el fichero cuyo nombre 
	 *  		se le  pasa por parametros
	 * 	Cabecera:
	 * 		int contarRegistros(String nombreFichero)
	 * 	Precondiciones:
	 * 		nada, si el fichero no existe devolvera 0
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
		}catch(FileNotFoundException fnfe){
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

	

}

