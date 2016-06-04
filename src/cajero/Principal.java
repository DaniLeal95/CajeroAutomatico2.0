package cajero;

import java.util.Scanner;

public class Principal {

	
	/*
	 * 
	 * PSEUDOCODIGO GENERALIZADO:
	 * 		Inicio PP
	 * 			MostrarClientes
	 * 			MostrarMenu y Validar OpcionMenu
	 * 			Mientras no quiera Salir
	 * 				
	 * 				MostrarMenu y Validar OpcionMenu
	 * 			fin mientras
	 * 			
	 * 		FIN PP
	 * 
	 * */
	public static void main(String[] args) {
		//Declaracion de variables
		int opcionmenu;
		Scanner sc= new Scanner(System.in);
		
		
		System.out.println("Bienvenido al banco VVBA");
		System.out.println("-------------------------");
		//Mostrar menu y validar OpcionMenu
		do{
		menuPrincipal();
		opcionmenu=sc.nextInt();
		}while(opcionmenu<0 || opcionmenu>3);
		
		//MientrasNoquieraSalir
		while(opcionmenu!=0){
			System.out.println("AQUI DEBERIA DE IR ALGO NO!? ESTOY CONFUSO");
			
			//Mostrar menu y validar OpcionMenu
			do{
			menuPrincipal();
			opcionmenu=sc.nextInt();
			}while(opcionmenu<0 || opcionmenu>3);
		}
	}
	
	public static void menuPrincipal( ){

			System.out.println("----Menu Principal Cajero----");
			System.out.println("1.- Anadir Cliente");
			System.out.println("2.- Movimientos");
			System.out.println("3.- Actualizar Ficheros");
			System.out.println("0.- Salir");
			System.out.println("------------------------");
			System.out.println("Que desea hacer?");
		
	}

}
