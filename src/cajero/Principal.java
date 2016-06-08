package cajero;

import java.util.GregorianCalendar;
import java.util.Scanner;

import datos.ClienteImp;
import datos.CuentaImp;
import datos.TarjetaImp;
import gestionyutilidades.GestionFicherosClientes;
import gestionyutilidades.GestionFicherosCuentas;
import gestionyutilidades.GestionFicherosTarjetas;

public class Principal {
	private static Scanner sc= new Scanner(System.in);
	private static String nombreFicheroMaestroClientes ="ClientesMaestro.dat";
	private static String nombreFicheroMovimientoClientes ="ClientesMovimiento.dat";
	private static String nombreFicheroMaestroCuentas="CuentasMaestro.dat";
	private static String nombreFicheroMovimientoCuentas="CuentasMovimiento.dat";
	private static String nombreFicheroMaestroTarjetas="TarjetasMaestro.dat";
	private static String nombreFicheroMovimientoTarjetas="TarjetasMovimiento.dat";
	/*
	 * 
	 * PSEUDOCODIGO GENERALIZADO:
	 * 		Inicio PP
	 * 			
	 * 			MostrarMenuPrincipal y Validar OpcionMenu
	 * 			Mientras no quiera Salir
	 * 					
	 * 				segun OpcionMenu
	 * 					case 1:
	 * 						Modo Administrador
	 * 					
	 * 					case 2:
	 * 						Modo Usuario
	 * 				
	 * 				fin segun
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
		String contrasena;
		int contadorcontrasena=0;
		long idCliente;
		
		
		
		
		System.out.println("Bienvenido al banco VVBA");
		System.out.println("-------------------------");
		//Mostrar menu y validar OpcionMenu
		do{
		menuPrincipal();
		opcionmenu=sc.nextInt();
		}while(opcionmenu<0 || opcionmenu>3);
		
		//MientrasNoquieraSalir
		while(opcionmenu!=0 && contadorcontrasena<3){
			
			switch (opcionmenu) {
			case 1:
				//MODO ADMINISTRADOR
				do{
					if(contadorcontrasena>0)System.out.println("La contraseña es incorrecta, te quendan "+(3-contadorcontrasena)+" intentos");
					System.out.println("Introduzca la contraseña de Administrador");
					contrasena=sc.next();
					//Si la contraseña introducida por el usuario se añadira uno al contador de contraseña
					if(!(contrasena.equals(gfclientes.obtenerCliente(1, nombreFicheroMaestroClientes,nombreFicheroMovimientoClientes ).getContrasena()))){
						contadorcontrasena++;
					}
				}while(!(contrasena.equals(gfclientes.obtenerCliente(1, nombreFicheroMaestroClientes,nombreFicheroMovimientoClientes ).getContrasena()))
						&& contadorcontrasena<3);
				
				//por razon de legibilidad translado el modo administrador hacia otro subprograma
				ModoAdministrador();
				
				break;

			case 2:
				//Modo Usuario
				do{	
					gfclientes.mostrarClientes(nombreFicheroMaestroClientes);
					gfclientes.mostrarClientes(nombreFicheroMovimientoClientes);
					System.out.println("Introduce la idCliente");
					idCliente=sc.nextLong();
					cliente=gfclientes.obtenerCliente(idCliente, nombreFicheroMaestroClientes, nombreFicheroMovimientoClientes);
					//	Si el id que ha introducido no corresponde a ningun cliente
					if(cliente!=null){
						do{
							if(contadorcontrasena>0)System.out.println("La contraseña es incorrecta, te quendan "+(3-contadorcontrasena)+" intentos");
							System.out.println("Introduce la contraseña de "+cliente.getNombre());
							contrasena=sc.next();
							if(!(contrasena.equals(gfclientes.obtenerCliente(cliente.getIdCliente(), nombreFicheroMaestroClientes,nombreFicheroMovimientoClientes ).getContrasena()))){
								contadorcontrasena++;
							}	
						
						}while(!(contrasena.equals(gfclientes.obtenerCliente(cliente.getIdCliente(), nombreFicheroMaestroClientes,nombreFicheroMovimientoClientes ).getContrasena()))
								&& contadorcontrasena<3);
					}
				}while(cliente==null);
				//LAMADA AL SIGUIENTE PASO
				
						
				
				break;
			}
			//Si no se a equivocado 3 veces 
			if(contadorcontrasena<3){
			//Mostrar menu y validar OpcionMenu
				do{
				menuPrincipal();
				opcionmenu=sc.nextInt();
				}while(opcionmenu<0 || opcionmenu>2);
			}
		}
	}
	
	/*
	 * PG:
	 * 		Inicio ModoAdministrador
	 * 			Mostrar Menu y validar opcion de Menu
	 * 			
	 * 			Mientras no sea salir
	 * 				
	 * 				segun opcion
	 * 					
	 * 					caso 1:
	 * 						Crear Nuevo Cliente
	 * 					
	 * 					caso 2:
	 * 						Crear Nueva Cuenta
	 * 					
	 * 					caso 3:
	 * 						Crear Nueva Tarjeta
	 * 
	 * 					caso 4:
	 * 						Eliminar Cliente
	 * 
	 * 					caso 5:
	 * 						Eliminar Cuenta
	 * 		
	 * 					caso 6:
	 * 						Eliminar Tarjeta
	 * 		
	 * 					caso 7:
	 * 						Modificar Cliente
	 * 
	 * 					caso 8:
	 * 						Modificar Tarjeta
	 * 
	 * 					caso 9:
	 * 						Actualizar fichero
	 * 					
	 * 				fin segun
	 * 				
	 * 				Mostrar Menu y Validar Opcion de menu
	 * 		
	 * 			fin Mientras
	 * 
	 * 		Fin ModoAdministrador
	 * */
	public static void ModoAdministrador(){
		int opcionmenu;
		GestionFicherosClientes gfclientes=new GestionFicherosClientes();
		GestionFicherosCuentas gfcuentas=new GestionFicherosCuentas();
		GestionFicherosTarjetas gftarjetas=new GestionFicherosTarjetas();
		//Variables para Clientes
		String nombre,apellidos,dni,observaciones,contrasena;
		GregorianCalendar fNacimiento;
		GregorianCalendar fActual=new GregorianCalendar();
		int ano,mes,dia;
		char genero;
		ClienteImp cliente=null;
		//------------------------
		
		//Variables para Cuentas
		double saldo;
		long idCliente;
		CuentaImp cuenta=null;
		//----------------------
		
		//Variables para Tarjeta
		char tipo;
		String pin;
		long numCuenta;
		TarjetaImp tarjeta=null;
		long numTarjeta;
		//----------------------
		
		
		
		//Mostrar menu y validar opcion de menu
		do{
			MenuModoAdministrador();
			opcionmenu=sc.nextInt();
		}while(opcionmenu<0 || opcionmenu>9);
		//Mientras no sea salir
		while(opcionmenu!=0){
			
			//segun opcion
			switch (opcionmenu) {
			case 1:
				//Crear Nuevo Cliente
				System.out.println("OPCION CREAR NUEVO CLIENTE");
				//PEDIMOS DATOS DEL CLIENTE
				System.out.println("Introduce su Nombre");
				nombre=sc.next();
				System.out.println("Introduce sus Apellidos");
				apellidos=sc.next();
				
				do{
					System.out.println("fecha de nacimiento");
					System.out.println("Introduce el anio");
					ano=sc.nextInt();
						
					do{
						System.out.println("Introduce el mes");
						mes=sc.nextInt();
					}while(mes<1 && mes>12);
					
					do{	
						System.out.println("Introduce el dia");
						dia=sc.nextInt();
					}while (dia<1 && dia>31);
					
					fNacimiento=new GregorianCalendar(ano, mes, dia);
				}while(fNacimiento.compareTo(fActual)>0);
				
				do{
					System.out.println("Introduce dni");
					dni=sc.next();
				}while(dni.length()!=9);
				
				do{
					System.out.println("Introduce su genero H o M");
					genero=Character.toUpperCase(sc.next().charAt(0));
				}while(genero!='H' && genero!='M');
				
				System.out.println("Introduce alguna observacion");
				observaciones=sc.next();
				
				sc.nextLine();
				System.out.println("Introduzca una contrasenia");
				contrasena=sc.nextLine();
				
				
				//FIN PEDIR DATOS DEL CLIENTE
				cliente=new ClienteImp(nombre,apellidos,fNacimiento,dni,genero,observaciones,contrasena);
				//REGISTRAMOS AL CLIENTE EN LOS FICHEROS
				gfclientes.escribirClienteMovimiento(nombreFicheroMovimientoClientes, cliente);
				
				break;
			
			case 2:
				//Crear Nueva Cuenta
					
				System.out.println("OPCION CREAR NUEVA CUENTA");
				//PEDIMOS DATOS DE LA CUENTA
				System.out.println("Introduce el saldo");
				saldo=sc.nextDouble();
				//Mostramos los ficheros de clientes
				gfclientes.mostrarClientes(nombreFicheroMaestroClientes);
				gfclientes.mostrarClientes(nombreFicheroMovimientoClientes);
				
				do{	
					System.out.println("Introduce el idCliente al que pertenece esta cuenta");
					idCliente=sc.nextLong();
					//VALIDAMOS QUE EL ID CLIENTE SEA VALIDO 
					if(cuenta.validaridCliente(idCliente, nombreFicheroMaestroClientes, nombreFicheroMovimientoClientes))
						System.out.println("La cuenta no hace referencia a ningun cliente");
					//MIENTRAS NO HAYA NINGUN CLIENTE CON ESA ID O EL ID SEA ADMINISTRADOR
				}while (!(cuenta.validaridCliente(idCliente, nombreFicheroMaestroClientes, nombreFicheroMovimientoClientes)) 
						|| idCliente==1);
				//FIN PEDIR DATOS
				
				//SI TODOS LOS DATOS SON VALIDOS CREAMOS LA CUENTA
				cuenta=new CuentaImp(saldo,idCliente);
				//Y LA REGISTRAMOS
				gfcuentas.escribirMovimiento(nombreFicheroMovimientoCuentas, cuenta);
				
				break;
			
			
			case 3:
				//Crear Nueva Tarjeta
				System.out.println("OPCION CREAR NUEVA TARJETA");
				do{
					System.out.println("Introduzca el tipo de la tarjeta D(DEBITO) o C(CREDITO)");
					tipo=Character.toUpperCase(sc.next().charAt(0));
				}while(tipo!='D' && tipo!='C');
				
				do{
					System.out.println("Introduce pin");
					pin=sc.next();
				}while(!(tarjeta.validarPin(pin)));
				
				do{
					gfcuentas.mostrarCuentas(nombreFicheroMaestroCuentas);
					gfcuentas.mostrarCuentas(nombreFicheroMovimientoCuentas);
					System.out.println("Introduce el numCuenta al que pertenece esta tarjeta");
					numCuenta=sc.nextLong();
					if(!(tarjeta.validarnumCuenta(numCuenta, nombreFicheroMaestroCuentas, nombreFicheroMovimientoCuentas)))
						System.out.println("El numero de cuenta no corresponde a ninguna cuenta");
				}while(!(tarjeta.validarnumCuenta(numCuenta, nombreFicheroMaestroCuentas, nombreFicheroMovimientoCuentas)));
				tarjeta=new TarjetaImp(tipo, pin, numCuenta);
				gftarjetas.escribirMovimiento(nombreFicheroMovimientoTarjetas, tarjeta);
				break;
			case 4:
				//Eliminar Cliente
				
				System.out.println("OPCION ELIMINAR CLIENTE");
				gfclientes.mostrarClientes(nombreFicheroMaestroClientes);
				gfclientes.mostrarClientes(nombreFicheroMovimientoClientes);
				do{
					System.out.println("Introduzca el cliente que desea eliminar");
					idCliente=Long.parseLong(sc.nextLine());
					cliente=gfclientes.obtenerCliente(idCliente, nombreFicheroMaestroClientes, nombreFicheroMovimientoClientes);
				}while(idCliente==1 || cliente==null );
				cliente.setNombre(cliente.getNombre().concat("*"));
				gfclientes.escribirClienteMovimiento(nombreFicheroMovimientoClientes, cliente);
				
				break;
			case 5:
				//Eliminar Cuenta
				
				System.out.println("OPCION ELIMINAR CUENTA");
				gfcuentas.mostrarCuentas(nombreFicheroMaestroCuentas);
				gfcuentas.mostrarCuentas(nombreFicheroMovimientoCuentas);
				
				do{
					System.out.println("Introduzca el numCuenta que desea eliminar");
					numCuenta=Long.parseLong(sc.nextLine());
					cuenta=gfcuentas.obtenerCuenta(numCuenta, nombreFicheroMaestroCuentas, nombreFicheroMovimientoCuentas);
				}while(cuenta==null);
				cuenta.setidCliente(-1);
				gfcuentas.escribirMovimiento(nombreFicheroMovimientoCuentas, cuenta);
				break;
			case 6:
				//Eliminar Tarjeta
				System.out.println("OPCION ELIMINAR TARJETA");
				gftarjetas.mostrarTarjetas(nombreFicheroMaestroTarjetas);
				gftarjetas.mostrarTarjetas(nombreFicheroMovimientoTarjetas);
				
				do{
					System.out.println("Introduzca el numTarjeta que desea eliminar");
					numTarjeta=Long.parseLong(sc.nextLine());
					tarjeta=gftarjetas.obtenerTarjeta(numTarjeta, nombreFicheroMaestroTarjetas, nombreFicheroMovimientoTarjetas);
				}while(tarjeta==null);
				
				tarjeta.setnumCuenta(-1);
				gftarjetas.escribirMovimiento(nombreFicheroMovimientoTarjetas, tarjeta);
				
				break;
			case 7:
				//Modificar Cliente
				
				break;
			case 8:
				//Modificar Tarjeta
				
				break;
			case 9:
				//Actualizar
				
				break;
			
			}//fin segun
			
			//Mostrar menu y validar Opcion Menu
			do{
				MenuModoAdministrador();
				opcionmenu=sc.nextInt();
			}while(opcionmenu<0 || opcionmenu>9);	
		}//fin mientras
	}//fin Modo Administrador
	
	
	
	
	
	/*
	 *	PINTAR MENU MODO USUARIO 
	 * 
	 * */
	public static void MenuModoUsuario(){
		System.out.println("----Menu Modo Usuario ----");
		System.out.println("1.- Mostrar Cuentas y Tarjetas");
		System.out.println("2.- Sacar Dinero");
		System.out.println("3.- Ingresar Dinero");
		System.out.println("4.- Transferencia");
		System.out.println("0.- Salir");
		System.out.println("--------------------------");
		System.out.println("Que desea hacer?");
	}
	
	/*
	 * PINTAR MENU MODO ADMINISTRADOR
	 * 
	 * */
	public static void MenuModoAdministrador(){
		System.out.println("----Menu Modo Administrador ----");
		System.out.println("1.- Crear nuevo cliente");
		System.out.println("2.- Crear nueva cuenta");
		System.out.println("3.- Crear nueva tarjeta");
		System.out.println("4.- Eliminar cliente");
		System.out.println("5.- Eliminar cuenta");
		System.out.println("6.- Eliminar tarjeta");
		System.out.println("7.- Modificar Cliente");
		System.out.println("8.- Modificar Tarjeta");
		System.out.println("9.- Actualizar Ficheros");
		System.out.println("0.- Salir");
		System.out.println("--------------------------------");
		System.out.println("Que desea hacer?");
	}
	
	/*
	 * PINTAR MENU PRINCIPAL
	 * 
	 * */
	public static void menuPrincipal( ){
			System.out.println("----Menu Principal ----");
			System.out.println("1.- Modo Administrador");
			System.out.println("2.- Modo Usuario");
			System.out.println("0.- Salir");
			System.out.println("------------------------");
			System.out.println("Que desea hacer?");
	}

}
