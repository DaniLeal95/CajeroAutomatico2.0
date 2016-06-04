package test;

import datos.TarjetaImp;

public class TestTarjeta {
	public static void main(String args[]){
		TarjetaImp t0=new TarjetaImp();
		TarjetaImp t1=new TarjetaImp('C',"1234");
		TarjetaImp t2=new TarjetaImp(t1);
		TarjetaImp tnovalidaporpasarlongitud=new TarjetaImp('d',"7777777");
		TarjetaImp tnovalidapornoserdigitos=new TarjetaImp('c',"HOLA");
		
			
		//To string (validando metodo utilidades ValidarPIN) 
		//System.out.println(t0.toString());
		//System.out.println(t1.toString());
		//System.out.println(tnovalidaporpasarlongitud.toString());
		//System.out.println(tnovalidapornoserdigitos.toString());
		
		//tarjetatoCadena
		//System.out.println(t1.tarjetatoCadena());
		
		//hashcode
		//System.out.println(t1.hashCode());
		
		//equals
		//System.out.println(t1.equals(t2));
		//System.out.println(t1.equals(t0));
		
		//compareTo
		//System.out.println("debe salir 0, "+t1.compareTo(t2));
		//System.out.println("debe salir 1, "+t1.compareTo(t0));
		//System.out.println("debe salir -1, "+t1.compareTo(tnovalidapornoserdigitos));
		
		//clone
		//System.out.println(t1.clone());
		//System.out.println((tnovalidapornoserdigitos=t1.clone()).toString());
		
			
		
	}
		
}
