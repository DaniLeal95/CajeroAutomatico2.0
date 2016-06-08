package cajero;

import java.util.Scanner;

import datos.ClienteImp;
import gestionyutilidades.GestionFicherosClientes;

public class Principal {
	private static Scanner sc= new Scanner(System.in);
	
	/*
	 * 
	 * PSEUDOCODIGO GENERALIZADO:
	 * 		Inicio PP
	 * 			
	 * 			MostrarMenuPrincipal y Validar OpcionMenu
	 * 			Mientras no quiera Salir
	 * 				
	 * 				MostrarMenu y Validar OpcionMenu
	 * 			fin mientras
	 * 			
	 * 		FIN PP
	 * 
	 * */
	public static void main(String[] args) {
		GestionFicherosClientes gfclientes=new GestionFicherosClientes();
		String nombreFicheroMaestroClientes ="ClientesMaestro.dat";
		String nombreFicheroMovimientoClientes ="ClientesMovimiento.dat";
		
		ClienteImp cliente=null;
		int opcionmenu;
		String contraseña;
		int contadorcontraseña=0;
		long idCliente;
		
		
		
		
		System.out.println("Bienvenido al banco VVBA");
		System.out.println("-------------------------");
		//Mostrar menu y validar OpcionMenu
		do{
		menuPrincipal();
		opcionmenu=sc.nextInt();
		}while(opcionmenu<0 || opcionmenu>3);
		
		//MientrasNoquieraSalir
		while(opcionmenu!=0 && contadorcontraseña<3){
			
			switch (opcionmenu) {
			case 1:
				
				do{
					if(contadorcontraseña>0)System.out.println("La contraseña es incorrecta, te quendan "+(3-contadorcontraseña)+" intentos");
					System.out.println("Introduzca la contraseña de Administrador");
					contraseña=sc.next();
					//Si la contraseña introducida por el usuario se añadira uno al contador de contraseña
					if(!(contraseña.equals(gfclientes.obtenerCliente(1, nombreFicheroMaestroClientes,nombreFicheroMovimientoClientes ).getContrasena()))){
						contadorcontraseña++;
					}
				}while(!(contraseña.equals(gfclientes.obtenerCliente(1, nombreFicheroMaestroClientes,nombreFicheroMovimientoClientes ).getContrasena()))
						&& contadorcontraseña<3);
				
				//LLAMADA A SIGUIENTE MENU
				break;

			case 2:
				do{	
					gfclientes.mostrarClientes(nombreFicheroMaestroClientes);
					gfclientes.mostrarClientes(nombreFicheroMovimientoClientes);
					System.out.println("Introduce la idCliente");
					idCliente=sc.nextLong();
					cliente=gfclientes.obtenerCliente(idCliente, nombreFicheroMaestroClientes, nombreFicheroMovimientoClientes);
					//	Si el id que ha introducido no corresponde a ningun cliente
					if(cliente!=null){
						do{
							if(contadorcontraseña>0)System.out.println("La contraseña es incorrecta, te quendan "+(3-contadorcontraseña)+" intentos");
							System.out.println("Introduce la contraseña de "+cliente.getNombre());
							contraseña=sc.next();
							if(!(contraseña.equals(gfclientes.obtenerCliente(cliente.getIdCliente(), nombreFicheroMaestroClientes,nombreFicheroMovimientoClientes ).getContrasena()))){
								contadorcontraseña++;
							}	
						
						}while(!(contraseña.equals(gfclientes.obtenerCliente(cliente.getIdCliente(), nombreFicheroMaestroClientes,nombreFicheroMovimientoClientes ).getContrasena()))
								&& contadorcontraseña<3);
					}
				}while(cliente==null);
				//LAMADA AL SIGUIENTE PASO
				
						
				
				break;
			}
			//Si no se a equivocado 3 veces 
			if(contadorcontraseña<3){
			//Mostrar menu y validar OpcionMenu
				do{
				menuPrincipal();
				opcionmenu=sc.nextInt();
				}while(opcionmenu<0 || opcionmenu>2);
			}
		}
	}
	
	public static void menuPrincipal( ){
			System.out.println("----Menu Principal ----");
			System.out.println("1.- Modo Administrador");
			System.out.println("2.- Modo Usuario");
			System.out.println("0.- Salir");
			System.out.println("------------------------");
			System.out.println("Que desea hacer?");
	}

}
