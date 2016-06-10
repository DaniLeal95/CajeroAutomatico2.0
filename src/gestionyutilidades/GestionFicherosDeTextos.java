package gestionyutilidades;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;

import datos.CuentaImp;

public class GestionFicherosDeTextos {

	/*Esta Clase de gestion esta exclusivamente para crear ficheros simulando un recibo*/
	
	
	/*
	 * Metodo ReciboMovimiento
	 * 	Breve Comentario:
	 * 		El metodo recibira dos Cuentas, una sera la tu cuenta y la otra sera el movimiento
	 * 
	 * 	Cabecera:
	 * 		public void ReciboMovimiento(CuentaImp cuenta,CuentaImp movimiento)
	 * 	Precondiciones:
	 * 		las cuentas , no deben ser nulas
	 * 	Entradas:
	 * 		dos CuentaImp
	 * 	Salida:
	 * 		Nada
	 *  Postcondiciones:
	 *   	Se creara un fichero.	
	 * 	
	 * */
	//Resguardo
//	public void ReciboMovimiento(CuentaImp cuenta,CuentaImp movimiento){
//		System.out.println("Metodo en resguardo");
//	}
	
	public void ReciboMovimiento(CuentaImp cuenta,CuentaImp movimiento){
		File f=new File("Recibo.txt");
		FileWriter fw=null;
		GregorianCalendar fActual=new GregorianCalendar();
		int dia,mes,ano,hora,min,seg;
		dia=fActual.get(GregorianCalendar.DAY_OF_MONTH);
		mes=fActual.get(GregorianCalendar.MONTH)+1;
		ano=fActual.get(GregorianCalendar.YEAR);
		hora=fActual.get(GregorianCalendar.HOUR_OF_DAY);
		min=fActual.get(GregorianCalendar.MINUTE);
		seg=fActual.get(GregorianCalendar.SECOND);
		
		try{
			fw=new FileWriter(f);
			
			fw.write("RECIBO DEL BANCO VVBA");
			fw.write("\r\n ------------------");
			fw.write("\r\n El numCuenta: "+cuenta.getNumCuenta());
			fw.write("\r\nHizo un movimiento: "+movimiento.getSaldo()+" Euros");
			fw.write("\r\nEn la fecha: "+dia+"/"+mes+"/"+ano+" "+hora+":"+min+":"+seg);
			fw.write("\r\n ------------------");
			fw.write("\r\n Su saldo ahora mismo: "+(cuenta.getSaldo()+movimiento.getSaldo())+ "Euros");
			fw.write("\r\n Gracias por confiar en nosotros.");
			
			
		}catch(IOException ioe){
			System.out.println(ioe);
		}finally{
			try{
				if(fw!=null){
					fw.close();
				}
				
			}catch(IOException ioe){
				System.out.println(ioe);
			}
		}
	
	}
	
	
	/*
	 * Metodo ReciboMovimiento
	 * 	Breve Comentario:
	 * 		El metodo recibira tres Cuentas, una sera la tu cuenta ,otra sera la cuenta que recibira el
	 * 		importe y la otra sera el movimiento.
	 * 
	 * 	Cabecera:
	 * 		public void ReciboTransferencia(CuentaImp cuenta,CuentaImp cuentaAingresar,CuentaImp movimiento)
	 * 	Precondiciones:
	 * 		las cuentas , no deben ser nulas
	 * 	Entradas:
	 * 		dos CuentaImp
	 * 	Salida:
	 * 		Nada
	 *  Postcondiciones:
	 *   	Se creara un fichero de texto.	
	 * 	
	 * */
	//Resguardo
//	public void ReciboTransferencia(CuentaImp cuenta,CuentaImp cuentaAingresar,CuentaImp movimiento){
//		System.out.println("Metodo en resguardo");
//	}
	
	public void ReciboTransferencia(CuentaImp cuenta,CuentaImp cuentaAingresar,CuentaImp movimiento){
		File f=new File("Recibo.txt");
		FileWriter fw=null;
		GregorianCalendar fActual=new GregorianCalendar();
		int dia,mes,ano,hora,min,seg;
		dia=fActual.get(GregorianCalendar.DAY_OF_MONTH);
		mes=fActual.get(GregorianCalendar.MONTH)+1;
		ano=fActual.get(GregorianCalendar.YEAR);
		hora=fActual.get(GregorianCalendar.HOUR_OF_DAY);
		min=fActual.get(GregorianCalendar.MINUTE);
		seg=fActual.get(GregorianCalendar.SECOND);
		
		try{
			fw=new FileWriter(f);
			
			fw.write("RECIBO DEL BANCO VVBA");
			fw.write("\r\n ------------------");
			fw.write("\r\n El numCuenta: "+cuenta.getNumCuenta());
			fw.write("\r\nHizo una transferencia de : "+movimiento.getSaldo()+" Euros");
			fw.write("\r\n Hacia El numCuenta: "+cuentaAingresar.getNumCuenta());
			fw.write("\r\nEn la fecha: "+dia+"/"+mes+"/"+ano+" "+hora+":"+min+":"+seg);
			fw.write("\r\n ------------------");
			fw.write("\r\n Su saldo ahora mismo: "+(cuenta.getSaldo()-movimiento.getSaldo())+ "Euros");
			fw.write("\r\n Gracias por confiar en nosotros.");
			
			
		}catch(IOException ioe){
			System.out.println(ioe);
		}finally{
			try{
				if(fw!=null){
					fw.close();
				}
				
			}catch(IOException ioe){
				System.out.println(ioe);
			}
		}
	
	}
}
