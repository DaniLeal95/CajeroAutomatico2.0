package cajero;

import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.Vector;

import datos.ClienteImp;
import datos.CuentaImp;
import datos.TarjetaImp;
import gestionyutilidades.GestionFicherosClientes;
import gestionyutilidades.GestionFicherosCuentas;
import gestionyutilidades.GestionFicherosDeTextos;
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
	 * Analisis:
	 * 	Este programa permite gestionar Un banco
	 * 	
	 * 	El objetivo es poder gestionar con ficheros el mantenimiento
	 * 		de clientes, con sus cuentas y tarjetas.
	 * 	
	 * Precondiciones:
	 * 	Los ficheros maestros deberan estar creados	
	 * 
	 * Entradas:
	 * 		se introducen por teclado, 
	 * 		- un entero con opcion de menu
	 * 			-Segun la opcion se deberan introducir:
	 * 				Modo cliente:
	 * 					-entero largo, idCliente
	 * 					-String contrasena
	 * 				Modo Administrador:
	 * 					-String contrasena
	 * 
	 * 	Salidas:
	 * 		muestra por pantalla :
	 * 			los menus cuando sean necesarios.
	 * 			
	 * 			mensajes para pedir alguna entrada.
	 * 			mensajes de error.
	 * 
	 * 			Si es Modo Cliente:
	 * 				un objeto ClienteImp
	 * 
	 * PSEUDOCODIGO GENERALIZADO:
	 * 		Inicio PP
	 * 			
	 * 			MostrarMenuPrincipal y Validar OpcionMenu
	 * 			Mientras opcionMenu!=0
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
			contadorcontrasena=0;
			menuPrincipal();
			opcionmenu=Integer.parseInt(sc.nextLine());
		}while(opcionmenu<0 || opcionmenu>3);
		
		//MientrasNoquieraSalir
		while(opcionmenu!=0 && contadorcontrasena<3){
			
			switch (opcionmenu) {
			case 1:
				//MODO ADMINISTRADOR
				do{
					if(contadorcontrasena>0)System.out.println("La contraseña es incorrecta, te quendan "+(3-contadorcontrasena)+" intentos");
					System.out.println("Introduzca la contraseña de Administrador");
					
					contrasena=sc.nextLine();
					//Si la contraseña introducida por el usuario se añadira uno al contador de contraseña
					if(!(contrasena.equals(gfclientes.obtenerCliente(1, nombreFicheroMaestroClientes,nombreFicheroMovimientoClientes ).getContrasena()))){
						contadorcontrasena++;
					}
				}while(!(contrasena.equals(gfclientes.obtenerCliente(1, nombreFicheroMaestroClientes,nombreFicheroMovimientoClientes ).getContrasena()))
						&& contadorcontrasena<3);
				
				//por razon de legibilidad translado el modo administrador hacia otro subprograma
				if(contadorcontrasena<3)
					ModoAdministrador();
				else
					System.out.println("TU NO ERES EL ADMINISTRADOR, MALDITO");
				break;

			case 2:
				//MODO USUARIO
				do{	
					
					do{
						//Mostramos los clientes
						gfclientes.mostrarClientes(nombreFicheroMaestroClientes);
						gfclientes.mostrarClientes(nombreFicheroMovimientoClientes);
						
						//Pedimos idCliente
						System.out.println("Introduce el id del Cliente");
						
						idCliente=Long.parseLong(sc.nextLine());
						
						if(idCliente<=1)
							System.out.println("Introduzca una id valida (debe ser mayor a 2)\n");
						//si el id Es invalido.
					
					}while(idCliente<=1);
					
					
					cliente=gfclientes.obtenerCliente(idCliente, nombreFicheroMaestroClientes, nombreFicheroMovimientoClientes);
					
					if(cliente==null)
						System.out.println("Introduzca una id de Cliente valida\n");
					//	Si el id que ha introducido no corresponde a ningun cliente
					if(cliente!=null){
						do{
							//Pedimos la contraseña
							if(contadorcontrasena>0)System.out.println("La contraseña es incorrecta, te quendan "+(3-contadorcontrasena)+" intentos");
							System.out.println("Introduce la contraseña de "+cliente.getNombre());
							
							contrasena=sc.nextLine();
							
							if(!(contrasena.equals(cliente.getContrasena()))){
								contadorcontrasena++;
							}	
						
						}while(!(contrasena.equals(cliente.getContrasena()))
								&& contadorcontrasena<3);
					}
				}while(cliente==null);
				//LAMADA AL SIGUIENTE PASO
				if(contadorcontrasena<3){
					System.out.println("Bienvenido "+cliente.getNombre());
					ModoUsuario(cliente);
					
				}
				else{
					System.out.println("Tu no eres "+cliente.getNombre());
				}
						
				
				break;
			}
			//Si no se a equivocado 3 veces 
			if(contadorcontrasena<3){
			//Mostrar menu y validar OpcionMenu
				do{
				menuPrincipal();
				opcionmenu=Integer.parseInt(sc.nextLine());
				}while(opcionmenu<0 || opcionmenu>2);
			}
		}
	}
	
	/*
	 *  * Analisis:
	 * 	Este programa permite gestionar Un banco
	 * 	
	 * 	El objetivo es poder gestionar con ficheros el mantenimiento
	 * 		de clientes, con sus cuentas y tarjetas.
	 * 
	 * 
	 * Entradas:
	 * 		se introducen por teclado, 
	 * 		- un entero con opcion de menu
	 * 			-Segun la opcion se deberan introducir:
	 *				-los atributos de un cliente
	 *					
	 *					Cadenas: nombre, apellidos,dni,observaciones,contraseña
	 *					entero: año,mes,dia
	 *					caracter: genero
	 *
	 *				-los atributos de una cuenta
	 *
	 *					double: saldo
	 *					enterolargo: idCliente
	 *				
	 *				-los atributos de una tarjeta
	 *					
	 *					caracter tipo
	 *					cadena pin
	 *					entero largo numCuenta
	 *				
	 * 
	 * 	Salidas:
	 * 		muestra por pantalla :
	 * 			los menus cuando sean necesarios.
	 * 			
	 * 			mensajes para pedir alguna entrada.
	 * 			mensajes de error.
	 * 
	 * 			Los ficheros maestros y/o movimiento
	 * 				 quedaran modificados.
	 * 
	 * 
	 * 
	 * PG:
	 * 		Inicio ModoAdministrador
	 * 			Mostrar Menu y validar opcion de Menu
	 * 			
	 * 			Mientras opcion de Menu !=0
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
		GregorianCalendar fNacimiento=new GregorianCalendar();
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
		boolean pinvalido;
		//----------------------
		
		
		
		//Mostrar menu y validar opcion de menu
		do{
			MenuModoAdministrador();
			opcionmenu=Integer.parseInt(sc.nextLine());
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
				nombre=sc.nextLine();
				System.out.println("Introduce sus Apellidos");
				apellidos=sc.nextLine();
				
				do{
					System.out.println("fecha de nacimiento");
					System.out.println("Introduce el anio");
					ano=Integer.parseInt(sc.nextLine());
						
					do{
						System.out.println("Introduce el mes");
						mes=Integer.parseInt(sc.nextLine());
					}while(mes<1 && mes>12);
					
					do{	
						System.out.println("Introduce el dia");
						dia=Integer.parseInt(sc.nextLine());
					}while (dia<1 && dia>31);
					
					try{
						fNacimiento.setLenient(false);
						fNacimiento=new GregorianCalendar(ano, mes, dia);
					}catch(Exception e){
						System.out.println("Fecha no valida");
						fNacimiento=new GregorianCalendar();
						//Le añado un dia a la fecha actual para que no supere la condicion while siguiente
						fNacimiento.set(GregorianCalendar.DAY_OF_WEEK_IN_MONTH, (fNacimiento.get(GregorianCalendar.DAY_OF_WEEK_IN_MONTH)+1));
					}

				}while(fNacimiento.compareTo(fActual)>0);
				
				do{
					System.out.println("Introduce dni");
					dni=sc.nextLine();
				}while(dni.length()!=9);
				
				do{
					System.out.println("Introduce su genero H o M");
					genero=Character.toUpperCase(sc.nextLine().charAt(0));
				}while(genero!='H' && genero!='M');
				
				System.out.println("Introduce alguna observacion");
				observaciones=sc.nextLine();
				
				System.out.println("Introduzca una contrasenia");
				contrasena=sc.nextLine();
				
				
				//FIN PEDIR DATOS DEL CLIENTE
				cliente=new ClienteImp(nombre,apellidos,fNacimiento,dni,genero,observaciones,contrasena);
				//REGISTRAMOS AL CLIENTE EN LOS FICHEROS
				gfclientes.escribirClienteMovimiento(nombreFicheroMovimientoClientes, cliente);
				
				break;
			
			/*-------------------FIN CREAR NUEVO CLIENTE---------------------*/
			case 2:
				//Crear Nueva Cuenta
				System.out.println("OPCION CREAR NUEVA CUENTA");
				//PEDIMOS DATOS DE LA CUENTA
				System.out.println("Introduce el saldo");
				saldo=Double.parseDouble(sc.nextLine());
				//Mostramos los ficheros de clientes
				gfclientes.mostrarClientes(nombreFicheroMaestroClientes);
				gfclientes.mostrarClientes(nombreFicheroMovimientoClientes);
				
				do{	
					System.out.println("Introduce el idCliente al que pertenece esta cuenta");
					
					idCliente=Long.parseLong(sc.nextLine());

					//VALIDAMOS QUE EL ID CLIENTE SEA VALIDO 
					if(!gfcuentas.validaridCliente(idCliente, nombreFicheroMaestroClientes, nombreFicheroMovimientoClientes))
						System.out.println("La cuenta no hace referencia a ningun cliente");
					//MIENTRAS NO HAYA NINGUN CLIENTE CON ESA ID O EL ID SEA ADMINISTRADOR
				}while (!(gfcuentas.validaridCliente(idCliente, nombreFicheroMaestroClientes, nombreFicheroMovimientoClientes)) 
						|| idCliente==1);
				//FIN PEDIR DATOS
				
				//SI TODOS LOS DATOS SON VALIDOS CREAMOS LA CUENTA
				cuenta=new CuentaImp(saldo,idCliente);
				//Y LA REGISTRAMOS
				gfcuentas.escribirMovimiento(nombreFicheroMovimientoCuentas, cuenta);
				
				break;
			/*-------------------------FIN CREAR NUEVA CUENTA----------------------*/
			
			case 3:
				//Crear Nueva Tarjeta
				System.out.println("OPCION CREAR NUEVA TARJETA");
				//Pedir tipo
				do{
					System.out.println("Introduzca el tipo de la tarjeta D(DEBITO) o C(CREDITO)");
					tipo=Character.toUpperCase(sc.nextLine().charAt(0));
				}while(tipo!='D' && tipo!='C');
				//Pedir pin
				do{	
					pinvalido=true;
					System.out.println("Introduce pin");
					pin=sc.nextLine();
					//Comprobacion de pin valido
					if (pin.length() == 4) {

						for (int i = 0; i < pin.length() && pinvalido; i++) {
							
							if (!Character.isDigit(pin.charAt(i))) {
								pinvalido = false;
							}
							
						}
					}
				}while(!pinvalido);
				
				//Pedir numCuenta
				do{
					gfcuentas.mostrarCuentas(nombreFicheroMaestroCuentas);
					gfcuentas.mostrarCuentas(nombreFicheroMovimientoCuentas);
					System.out.println("Introduce el numCuenta al que pertenece esta tarjeta");
					numCuenta=Long.parseLong(sc.nextLine()
							);
					if(!(gftarjetas.validarnumCuenta(numCuenta, nombreFicheroMaestroCuentas, nombreFicheroMovimientoCuentas)))
						System.out.println("El numero de cuenta no corresponde a ninguna cuenta");
				}while(!(gftarjetas.validarnumCuenta(numCuenta, nombreFicheroMaestroCuentas, nombreFicheroMovimientoCuentas)));
				
				
				tarjeta=new TarjetaImp(tipo, pin, numCuenta);
				//Escribimos la tarjeta en movimiento
				gftarjetas.escribirMovimiento(nombreFicheroMovimientoTarjetas, tarjeta);
				
				break;
				
				/*--------------------------FIN CREAR NUEVA TARJETA--------------------*/
			case 4:
				//Eliminar Cliente
				System.out.println("OPCION ELIMINAR CLIENTE");
				gfclientes.mostrarClientes(nombreFicheroMaestroClientes);
				gfclientes.mostrarClientes(nombreFicheroMovimientoClientes);
				//pedir id del cliente
				do{
					System.out.println("Introduzca el cliente que desea eliminar");
					idCliente=Long.parseLong(sc.nextLine());
					cliente=gfclientes.obtenerCliente(idCliente, nombreFicheroMaestroClientes, nombreFicheroMovimientoClientes);
				}while(idCliente==1 || cliente==null );
				//le añadimos un asterisco al nombre porque es la manera de darla de baja
				cliente.setNombre(cliente.getNombre().concat("*"));
				//escribimos el cliente en movimiento
				gfclientes.escribirClienteMovimiento(nombreFicheroMovimientoClientes, cliente);
				
				break;
				
				/*-------------------------FIN ELIMINAR CLIENTE--------------------*/
				
			case 5:
				//Eliminar Cuenta
				System.out.println("OPCION ELIMINAR CUENTA");
				gfcuentas.mostrarCuentas(nombreFicheroMaestroCuentas);
				gfcuentas.mostrarCuentas(nombreFicheroMovimientoCuentas);
				//pedir el numero de cuenta 
				do{
					System.out.println("Introduzca el numCuenta que desea eliminar");
					numCuenta=Long.parseLong(sc.nextLine());
					cuenta=gfcuentas.obtenerCuenta(numCuenta, nombreFicheroMaestroCuentas, nombreFicheroMovimientoCuentas);
				}while(cuenta==null);
				//le cambiamos la idCliente a  -1 porque es la manera de dar de baja a la cuenta
				cuenta.setidCliente(-1);
				//escribimos la cuenta en movimiento
				gfcuentas.escribirMovimiento(nombreFicheroMovimientoCuentas, cuenta);
				break;
				
				/*--------------------------FIN ELIMINAR CUENTA--------------------*/
				
			case 6:
				//Eliminar Tarjeta
				System.out.println("OPCION ELIMINAR TARJETA");
				gftarjetas.mostrarTarjetas(nombreFicheroMaestroTarjetas);
				gftarjetas.mostrarTarjetas(nombreFicheroMovimientoTarjetas);
				//pedir el numero de tarjeta
				do{
					System.out.println("Introduzca el numTarjeta que desea eliminar");
					numTarjeta=Long.parseLong(sc.nextLine());
					tarjeta=gftarjetas.obtenerTarjeta(numTarjeta, nombreFicheroMaestroTarjetas, nombreFicheroMovimientoTarjetas);
				}while(tarjeta==null);
				//le cambiamos la numCuenta a -1 porque es la maera de dar de baja a la tarjeta
				tarjeta.setnumCuenta(-1);
				//escribimos la tarjeta en movimiento
				gftarjetas.escribirMovimiento(nombreFicheroMovimientoTarjetas, tarjeta);
				
				break;
				
				/*------------------------FIN ELIMINAR TARJETA---------------------*/
				
			case 7:
				//Modificar Cliente
				System.out.println("OPCION MODIFICAR CLIENTE");
				gfclientes.mostrarClientes(nombreFicheroMaestroClientes);
				gfclientes.mostrarClientes(nombreFicheroMovimientoClientes);
				//Pedimos en idCliente que deseamos eliminar
				do{
					System.out.println("Introduzca el cliente que desea modificar");
					idCliente=Long.parseLong(sc.nextLine());
					cliente=gfclientes.obtenerCliente(idCliente, nombreFicheroMaestroClientes, nombreFicheroMovimientoClientes);
					//comprobacion de si ese cliente puede anadir un nuevo movimiento
					if(!(gfclientes.comprobarMovimiento(idCliente, nombreFicheroMovimientoClientes)))
							System.out.println("Ese cliente ya tiene un movimiento");
				}while((idCliente==1 || cliente==null) && !(gfclientes.comprobarMovimiento(idCliente, nombreFicheroMovimientoClientes)) );
				
				
				/*Pedir atributos*/
				
				
				//Insertamos nombre
				System.out.println("Introduce su Nombre");
				nombre=sc.nextLine();
				cliente.setNombre(nombre);
				
				//Insertamos apellidos
				System.out.println("Introduce sus Apellidos");
				apellidos=sc.nextLine();
				cliente.setApellido(apellidos);
				
				//Insertamos fecha de Nacimiento
				do{
					System.out.println("fecha de nacimiento");
					System.out.println("Introduce el anio");
					ano=Integer.parseInt(sc.nextLine());
						
					do{
						System.out.println("Introduce el mes");
						mes=Integer.parseInt(sc.nextLine());
					}while(mes<1 && mes>12);
					
					do{	
						System.out.println("Introduce el dia");
						dia=Integer.parseInt(sc.nextLine());
					}while (dia<1 && dia>31);
					
					try{
						fNacimiento.setLenient(false);
						fNacimiento=new GregorianCalendar(ano, mes, dia);
					}catch(Exception e){
						System.out.println("Fecha no valida");
						fNacimiento=new GregorianCalendar();
						//Le añado un dia a la fecha actual para que no supere la condicion while siguiente
						fNacimiento.set(GregorianCalendar.DAY_OF_WEEK_IN_MONTH, (fNacimiento.get(GregorianCalendar.DAY_OF_WEEK_IN_MONTH)+1));
					}
				}while(fNacimiento.compareTo(fActual)>0);
				
				
					cliente.setFNacimiento(fNacimiento);
				
				
				//Insertamos dni
				do{
					System.out.println("Introduce dni");
					dni=sc.nextLine();
				}while(dni.length()!=9);
					cliente.setDni(dni);
				
				
				//Insertamos genero
				do{
					System.out.println("Introduce su genero H o M");
					genero=Character.toUpperCase(sc.nextLine().charAt(0));
				}while(genero!='H' && genero!='M');
				cliente.setGenero(genero);
				
				
				//Insertamos observacion
				System.out.println("Introduce alguna observacion");
				observaciones=sc.nextLine();
				cliente.setObservaciones(observaciones);
				
				//Insertamos contrasenia
				System.out.println("Introduzca una contrasenia");
				contrasena=sc.nextLine();
				cliente.setContrasena(contrasena);
				
				//Lo insertamos en el fichero de movimiento.
				gfclientes.escribirClienteMovimiento(nombreFicheroMovimientoClientes, cliente);
				break;
			case 8:
				sc.nextLine();
				//Modificar Tarjeta
				System.out.println("OPCION MODIFICAR TARJETA");
				gftarjetas.mostrarTarjetas(nombreFicheroMaestroTarjetas);
				gftarjetas.mostrarTarjetas(nombreFicheroMovimientoTarjetas);
				//Pedimos el numTarjeta que deseamos modificar
				do{
					System.out.println("Introduzca el numTarjeta que desea modificar");
					numTarjeta=Long.parseLong(sc.nextLine());
					tarjeta=gftarjetas.obtenerTarjeta(numTarjeta, nombreFicheroMaestroTarjetas, nombreFicheroMovimientoTarjetas);
					//Comprobacion de que ese movimiento puede ser efectivo.
					if(!(gftarjetas.comprobarMovimiento(numTarjeta, nombreFicheroMovimientoTarjetas)))
						System.out.println("Esa Tarjeta ya tiene un movimiento.");
				}while(tarjeta==null && !(gftarjetas.comprobarMovimiento(numTarjeta, nombreFicheroMovimientoTarjetas)));
				
				//PedirAtributos
				
				//Insertamos tipo
				do{
					System.out.println("Introduce el tipo");
					tipo=Character.toUpperCase(sc.nextLine().charAt(0));
				}while(tipo!='C' && tipo!='D');
				tarjeta.setTipo(tipo);
				
				//Insertamos pin
				do{
					System.out.println("Introduce el pin");
					pin=sc.nextLine();
				}while(!(tarjeta.validarPin(pin)));
				tarjeta.setPin(pin);
				
				//Lo insertamos en el fichero de movimiento..
				gftarjetas.escribirMovimiento(nombreFicheroMovimientoTarjetas, tarjeta);
				
				break;
			case 9:
				//Actualizar
				System.out.println("ACTUALIZANDO....");
				gfclientes.actualizaClientes(nombreFicheroMaestroClientes, nombreFicheroMovimientoClientes);
				gfcuentas.actualizaCuentas(nombreFicheroMaestroCuentas, nombreFicheroMovimientoCuentas);
				gftarjetas.actualizaTarjetas(nombreFicheroMaestroTarjetas, nombreFicheroMovimientoTarjetas);
				
				System.out.println();
				System.out.println("Mostramos los archivos actualizados");
				System.out.println();
				System.out.println("Clientes");
				System.out.println();
				gfclientes.mostrarClientes(nombreFicheroMaestroClientes);
				System.out.println("-----------------------");
				System.out.println();
				System.out.println("Cuentas");
				gfcuentas.mostrarCuentas(nombreFicheroMaestroCuentas);
				System.out.println("-----------------------");
				System.out.println();
				System.out.println("Tarjetas");
				gftarjetas.mostrarTarjetas(nombreFicheroMaestroTarjetas);
				System.out.println("-----------------------");
				System.out.println();
			
				
				break;
			
			}//fin segun
			
			//Mostrar menu y validar Opcion Menu
			do{
				MenuModoAdministrador();
				opcionmenu=Integer.parseInt(sc.nextLine());
			}while(opcionmenu<0 || opcionmenu>9);	
		}//fin mientras
	}//fin Modo Administrador
	
	/*
	 *  * Analisis:
	 * 	Este subprograma permite hacer operaciones con un cliente
	 * 	
	 * Precondiciones:
	 * 	Los ficheros maestros deberan estar creados	
	 * 
	 * Entradas:
	 * 		se introducen por teclado, 
	 * 		- un entero con opcion de menu
	 * 			-Segun la opcion se deberan introducir:
	 * 				un entero para opcion de menu
	 * 				
	 * 				entero largo para numCuenta
	 * 				entero largo para numTarjeta
	 * 				entero largo para idCliente
	 * 
	 * 				double para el saldo
	 * 				
	 * 				String para el pin de la tarjeta
	 * 				
	 * 				Caracter para imprimir recibo o no.
	 * 	Salidas:
	 * 		muestra por pantalla :
	 * 			los menus cuando sean necesarios.
	 * 			
	 * 			mensajes para pedir alguna entrada.
	 * 			mensajes de error.
	 * 
	 * 			Los ficheros maestros y/o movimiento
	 * 				 quedaran modificados.
	 * 		
	 * 			El fichero Recibo quedara creado si el cliente
	 * 				desea obtener recibo,
	 * 				con los datos del movimiento.
	 * 
	 * 
	 * PG:MODO USUARIO
	 * 
	 * INICIO MODOUSUARIO
	 * 	
	 * 	Mostrar Menu y Validar Opcion de Menu
	 * 
	 * 	Mientras opciondeMenu!=0
	 * 		
	 * 		Segun Opcion
	 * 			caso 1:
	 * 				Mostrar Cuentas y Tarjetas
	 * 			
	 * 			caso 2:
	 * 				Sacar Dinero
	 * 
	 * 			case 3:
	 * 				Ingresar Dinero
	 * 
	 * 			case 4:
	 * 				Transferencia
	 * 		fin Segun
	 * 
	 * 		Mostrar Menu y Validar Opcion de Menu
	 * 
	 * 	Fin mientras
	 * 
	 * FIN MODOUSUARIO
	 * 
	 * Breve Comentario:
	 * 	Este subprograma tiene como funcion dividir para hacer mas legible el codigo, y hacer operaciones
	 * 	con un cliente que recibe por parametros.
	 * 
	 * Precondiciones:
	 * 	El cliente no debe ser nulo, sino saltaran excepciones en las siguientes operaciones.
	 * 
	 * Entradas:
	 * 	Un ClienteImp
	 * 
	 * Salidas:
	 * 	nada
	 * Postcondiciones:
	 * 	El fichero de Cuentasmovimiento se vera modificado.
	 * 	 
	 * 
	 * */
	
	public static void ModoUsuario(ClienteImp cliente){
		int opcionmenu,contadorpin=0;
		char recibo;
		long numCuenta=0,numCuenta2,numTarjeta,idCliente;
		double saldo;
		boolean valido=true;
		
		ClienteImp cliente2=null;
		String pin;
		GestionFicherosCuentas gfcuentas=new GestionFicherosCuentas();
		GestionFicherosTarjetas gftarjetas=new GestionFicherosTarjetas();
		GestionFicherosClientes gfclientes=new GestionFicherosClientes();
		GestionFicherosDeTextos gfTextos=new GestionFicherosDeTextos();
		CuentaImp cuenta=null,cuenta2=null;
		
		Vector<Long> cuentas;
		boolean encontrado=false;
		
		Vector<Long> tarjetas;
		
		
		TarjetaImp tarjeta;
		
		
		//mostrar menu y validar opcion de menu
		do{
			contadorpin=0;
			MenuModoUsuario();
			opcionmenu=Integer.parseInt(sc.nextLine());
		}while(opcionmenu<0 || opcionmenu>4);
		
		//mientras no sea salir
		while(opcionmenu!=0){
			//segun opcion
			switch(opcionmenu){
			
			case 1:
				// Mostrar Cuentas y Tarjetas

				// mostramos las cuentas de dicho cliente
				do {
					
					
					if(gfcuentas.tieneCuentassegunCliente(cliente.getIdCliente(), nombreFicheroMaestroCuentas)){
						do{
							
						
						System.out.println("Tus Cuentas\n");
						gfcuentas.mostrarCuentasporCliente(cliente.getIdCliente(), nombreFicheroMaestroCuentas);
						System.out.println("-----------");
						
						cuentas=gfcuentas.obtenerCuentasporCliente(cliente.getIdCliente(), nombreFicheroMaestroCuentas, nombreFicheroMovimientoCuentas);
						
						// le pedimos el numero de cuenta del que quiera conocer sus
						// tarjetas
						// mientras el numero de cuenta introducido sea invalido.
						System.out.println("Introduce el numero de cuenta de la cual quieras saber las tarjetas");
						numCuenta = Long.parseLong(sc.nextLine());
						encontrado=false;
						for(int i=0;i<cuentas.size() && !encontrado;i++){
							if(cuentas.elementAt(i)==numCuenta){
								encontrado=true;
							}
						}
						if(!encontrado){
							System.out.println("Introduce el num de cuenta que haga referencia a una cuenta suya ");
						}
						}while(!encontrado);
						cuenta = gfcuentas.obtenerCuenta(numCuenta, nombreFicheroMaestroCuentas,
							nombreFicheroMovimientoCuentas);

						
						}else{
							System.out.println("No tienes cuentas, habla con el administrador para obtener una");
							valido=false;
						}
				
					} while (cuenta == null && valido);
						if(gftarjetas.validartarjetaspornumCuenta(numCuenta, nombreFicheroMaestroTarjetas, nombreFicheroMovimientoTarjetas)){
							System.out.println("Tus Tarjetas");
							gftarjetas.mostrarTarjetasporCuenta(numCuenta, nombreFicheroMaestroTarjetas);
							
						
							System.out.println();
						}

				break;
			
			
			/*------------------------------------------------------------------------------------------------------------------------------------*/
			case 2:
				// Sacar Dinero
				// mostramos las cuentas de dicho cliente
				do {
					
					if(gfcuentas.tieneCuentassegunCliente(cliente.getIdCliente(), nombreFicheroMaestroCuentas)){
						do{
						System.out.println("Tus Cuentas\n");
						gfcuentas.mostrarCuentasporCliente(cliente.getIdCliente(), nombreFicheroMaestroCuentas);
						System.out.println("-----------");
						cuentas=gfcuentas.obtenerCuentasporCliente(cliente.getIdCliente(), nombreFicheroMaestroCuentas, nombreFicheroMovimientoCuentas);
						
							
						// le pedimos el numero de cuenta del que quiera conocer sus
						// tarjetas
						// mientras el numero de cuenta introducido sea invalido.
						System.out.println("Introduce el numero de cuenta de la cual quieras sacar dinero");
						numCuenta = Long.parseLong(sc.nextLine());
						//comprobamos que el numCuenta que introduce sea una cuenta suya
						encontrado=false;
						for(int i=0;i<cuentas.size() && !encontrado;i++){
							if(cuentas.elementAt(i)==numCuenta)
								encontrado=true;
						}
						if(!encontrado)
							System.out.println("Introduce el num de cuenta que haga referencia a una cuenta suya ");
						}while(!encontrado);
						cuenta = gfcuentas.obtenerCuenta(numCuenta, nombreFicheroMaestroCuentas,
							nombreFicheroMovimientoCuentas);

						if (cuenta == null)
							System.out.println("El numero de cuenta introducido es invalido");
						}
						else{
							System.out.println("No tienes Cuentas habla con el administrador para obtener una.");
							valido=false;
						}	
					} while (cuenta == null && valido);
				// Si tienes tarjetas, podras hacer dicha operacion
				if (gftarjetas.validartarjetaspornumCuenta(numCuenta, nombreFicheroMaestroTarjetas,
						nombreFicheroMovimientoTarjetas)) {
					
					do {
						System.out.println("Tus Tarjetas");
						gftarjetas.mostrarTarjetasporCuenta(numCuenta, nombreFicheroMaestroTarjetas);

						tarjetas=gftarjetas.obtenerTarjetasporCuenta(numCuenta, nombreFicheroMaestroTarjetas, nombreFicheroMovimientoTarjetas);
						// Le pedimos la tarjeta con la que desea sacar el
						// dinero.
						System.out.println("\nIntroduce el numero de Tarjeta con el que deseas sacar dinero");
						numTarjeta = Long.parseLong(sc.nextLine());
						
						encontrado=false;
						for(int i=0;i<tarjetas.size() && !encontrado; i++){
							if(tarjetas.elementAt(i)==numTarjeta){
								encontrado=true;
							}
						}
						if(!encontrado){
							System.out.println("Introduce un numero de Tarjeta que corresponda a la cuenta seleccionada");
						}
					} while (!encontrado);
						tarjeta = gftarjetas.obtenerTarjeta(numTarjeta, nombreFicheroMaestroTarjetas,
								nombreFicheroMaestroTarjetas);

					
					// Le pedimos que nos diga el pin
					do {
						System.out.println("Introduzca su pin");
						pin = sc.nextLine();
						//Si el pin es incorrecto saltara mensaje de error y sumara uno al contador de errores.
						if (!tarjeta.getPin().equals(pin)) {
							System.out.println("Pin Incorrecto");
							contadorpin++;
							System.out.println("Te quedan " + (3 - contadorpin) + " intentos");
						}
						//mientras el pin sea incorrecto y el contador menor a 3 se repetira el proceso
					} while (!tarjeta.getPin().equals(pin) && contadorpin < 3);
					
					if (contadorpin < 3) {
						// le pedimos el saldo a sacar
						System.out.println("Introduce el saldo que desea sacar");
						saldo = Double.parseDouble(sc.nextLine());

						// Si el saldo que desea sacar es mayor al que tiene en
						// su cuenta
						// solo lo podra hacer si su tarjeta es de Credito
						if (cuenta.getSaldo() < saldo) {

							// Si la cuenta es de credito se lo permitiremos
							if (tarjeta.getTipo() == 'C') {
								CuentaImp Cuentaanterior=cuenta.clone();
								// le damos el saldo que desee cambiar
								cuenta.setSaldo(-saldo);
								System.out.println("Recoja su dinero");
								// y escribimos el movimiento en el fichero de
								// movimiento
								gfcuentas.escribirMovimiento(nombreFicheroMovimientoCuentas, cuenta);
								do {
									System.out.println("Desea recibo? (S o N)");
									recibo = Character.toUpperCase(sc.nextLine().charAt(0));
								} while (recibo != 'S' && recibo != 'N');
								if (recibo == 'S') {
									gfTextos.ReciboMovimiento(Cuentaanterior, cuenta);
								}
							}

							// Si la tarjeta es de debito, no le permitiremos
							// hacerlo
							else {
								System.out.println("ERROR!");
								System.out.println("Tu tarjeta es de debito y estas intentando sacar mas dinero,");
								System.out.println("del que posees en la cuenta.");
							}
						}

						// Si el saldo es igual o mayor a el dinero que intenta
						// sacar
						// se lo permitiremos con los dos tipos de tarjeta
						else {
							CuentaImp Cuentaanterior=cuenta.clone();
							// le damos el saldo que desee cambiar
							cuenta.setSaldo(-saldo);
							System.out.println("Recoja su dinero");
							// y escribimos el movimiento en el fichero de
							// movimiento
							gfcuentas.escribirMovimiento(nombreFicheroMovimientoCuentas, cuenta);
							do {
								System.out.println("Desea recibo? (S o N)");
								recibo = Character.toUpperCase(sc.nextLine().charAt(0));
							} while (recibo != 'S' && recibo != 'N');
							if (recibo == 'S') {
								gfTextos.ReciboMovimiento(Cuentaanterior, cuenta);
							}
						}

					}
				}
				// Si no tienes tarjetas, no puedes sacar dinero
				else {
					System.out.println("No tienes tarjetas, Ve al banco y pide una!");
				}

				break;
				
				
				/*-----------------------------------------------------------------------------------------------------------------------*/
			case 3:
				//Ingresar Dinero
				
				// mostramos las cuentas de dicho cliente
				do {

					if (gfcuentas.tieneCuentassegunCliente(cliente.getIdCliente(), nombreFicheroMaestroCuentas)) {
						
						do {
							System.out.println("Tus Cuentas\n");

							gfcuentas.mostrarCuentasporCliente(cliente.getIdCliente(), nombreFicheroMaestroCuentas);
							System.out.println("-----------");
							cuentas = gfcuentas.obtenerCuentasporCliente(cliente.getIdCliente(),
									nombreFicheroMaestroCuentas, nombreFicheroMovimientoCuentas);

							// le pedimos el numero de cuenta del que quiera sacar dinero
							// mientras el numero de cuenta introducido sea
							// invalido.
							System.out.println("Introduce el numero de cuenta de la cual quieras ingresar dinero");
							numCuenta = Long.parseLong(sc.nextLine());
							encontrado = false;
							for (int i = 0; i < cuentas.size() && !encontrado; i++) {
								if (cuentas.elementAt(i) == numCuenta)
									valido = true;
							}
						} while (!encontrado);
						cuenta = gfcuentas.obtenerCuenta(numCuenta, nombreFicheroMaestroCuentas,
								nombreFicheroMovimientoCuentas);
						
						if (cuenta == null)
							System.out.println("El numero de cuenta introducido es invalido");
					} else {
						System.out.println("No tienes ninguna cuenta, habla con el administrador para obtener una");
						valido = false;
					}
				} while (cuenta == null && valido);
				// Si tienes tarjetas, podras hacer dicha operacion
				if (gftarjetas.validartarjetaspornumCuenta(numCuenta, nombreFicheroMaestroTarjetas,
						nombreFicheroMovimientoTarjetas)) {

					do {
						System.out.println("Tus Tarjetas");
						gftarjetas.mostrarTarjetasporCuenta(numCuenta, nombreFicheroMaestroTarjetas);
						
						tarjetas=gftarjetas.obtenerTarjetasporCuenta(numCuenta, nombreFicheroMaestroTarjetas, nombreFicheroMovimientoTarjetas);

						// Le pedimos la tarjeta con la que desea sacar el
						// dinero.
						System.out.println("\nIntroduce el numero de Tarjeta con el que deseas ingresar dinero");
						numTarjeta = Long.parseLong(sc.nextLine());

						encontrado=false;
						for(int i=0;i<tarjetas.size() && !encontrado; i++){
							if(tarjetas.elementAt(i)==numTarjeta){
								encontrado=true;
							}
						}
						if(!encontrado){
							System.out.println("Introduce un numero de Tarjeta que corresponda a la cuenta seleccionada");
						}
					} while (!encontrado);
					
						tarjeta = gftarjetas.obtenerTarjeta(numTarjeta, nombreFicheroMaestroTarjetas,
								nombreFicheroMaestroTarjetas);

						

					// Le pedimos que nos diga el pin
					do {
						System.out.println("Introduzca su pin");
						pin = sc.nextLine();
						// Si el pin es incorrecto saltara mensaje de error y
						// sumara uno al contador de errores.
						if (!tarjeta.getPin().equals(pin)) {
							System.out.println("Pin Incorrecto");
							contadorpin++;
							System.out.println("Te quedan " + (3 - contadorpin) + " intentos");
						}
						// mientras el pin sea incorrecto y el contador menor a
						// 3 se repetira el proceso
					} while (!tarjeta.getPin().equals(pin) && contadorpin < 3);

					if (contadorpin < 3) {
						// le pedimos el saldo a sacar
						System.out.println("Introduce el saldo que desea ingresar");
						saldo = Double.parseDouble(sc.nextLine());
						CuentaImp Cuentaanterior=cuenta.clone();
						// Si el saldo que desea sacar es mayor al que tiene en
						// su cuenta
						// solo lo podra hacer si su tarjeta es de Credito

						// le damos el saldo que desee cambiar
						cuenta.setSaldo(saldo);
						// y escribimos el movimiento en el fichero de
						// movimiento
						gfcuentas.escribirMovimiento(nombreFicheroMovimientoCuentas, cuenta);
						do {
							System.out.println("Desea recibo? (S o N)");
							recibo = Character.toUpperCase(sc.nextLine().charAt(0));
						} while (recibo != 'S' && recibo != 'N');
						if (recibo == 'S') {
							gfTextos.ReciboMovimiento(Cuentaanterior, cuenta);
						}
					}
				}

				break;
				
				
				/*----------------------------------------------------------------------------------------------------------*/
			case 4:
				//Transferencia
				// mostramos las cuentas de dicho cliente
				do {
					
					if(gfcuentas.tieneCuentassegunCliente(cliente.getIdCliente(), nombreFicheroMaestroCuentas)){
						do {
							System.out.println("Tus Cuentas\n");
							gfcuentas.mostrarCuentasporCliente(cliente.getIdCliente(), nombreFicheroMaestroCuentas);
							System.out.println("-----------");
							//recogo los numCuentas que tiene el cliente
							cuentas=gfcuentas.obtenerCuentasporCliente(cliente.getIdCliente(), nombreFicheroMaestroCuentas, nombreFicheroMovimientoCuentas);
							// le pedimos el numero de cuenta del que quiera
							// conocer sus
							// mientras el numero de cuenta introducido sea
							// invalido.
							System.out.println("Introduce el numero de cuenta de la cual quieras sacar dinero");
							numCuenta = Long.parseLong(sc.nextLine());
							
							//Validacion de la cuenta que ha introducido
							encontrado=false;
							for(int i=0;i<cuentas.size() && !encontrado; i++){
								if(cuentas.elementAt(i)==numCuenta){
									encontrado=true;
								}
							}
							if(!encontrado)
								System.out.println("Introduzca un num de Cuenta que corresponda a tu persona");
						} while (!encontrado);
					cuenta = gfcuentas.obtenerCuenta(numCuenta, nombreFicheroMaestroCuentas,
							nombreFicheroMovimientoCuentas);

					
					}else{
						System.out.println("No tienes cuentas habla con el administrador para obtener una");
						valido=false;
					}
				} while (cuenta == null && valido);
				if (valido) {
					do {

						gfclientes.mostrarClientes(nombreFicheroMaestroClientes);
						gfclientes.mostrarClientes(nombreFicheroMovimientoClientes);
						// mostrar clientes y validar el id del cliente
						System.out.println("\nIntroduzca al cliente que deseas hacerle la transferencia");
						idCliente = Long.parseLong(sc.nextLine());
						if (idCliente == cliente.getIdCliente())
							System.out.println("No te puedes hacer la transferencia ati mismo.");

						else
							cliente2 = gfclientes.obtenerCliente(idCliente, nombreFicheroMaestroClientes,
									nombreFicheroMovimientoClientes);
						if (cliente2 == null)
							System.out.println("Introduce una id valida");
					} while (idCliente == cliente.getIdCliente() || cliente == null);

					do {
						gfcuentas.mostrarCuentasporCliente(cliente2.getIdCliente(), nombreFicheroMaestroCuentas);
						gfcuentas.mostrarCuentasporCliente(cliente2.getIdCliente(), nombreFicheroMovimientoCuentas);
						
						cuentas=gfcuentas.obtenerCuentasporCliente(cliente2.getIdCliente(), nombreFicheroMaestroCuentas, nombreFicheroMovimientoCuentas);
						// le pedimos el numero de cuenta del que quiera conocer
						// sus
						// mientras el numero de cuenta introducido sea
						// invalido.
						System.out.println("Introduce el numero de cuenta de la cual quieras sacar dinero");
						numCuenta2 = Long.parseLong(sc.nextLine());
						encontrado=false;
						for(int i=0;i<cuentas.size() && !encontrado;i++){
							if(cuentas.elementAt(i)==numCuenta2){
								encontrado=true;
							}
						}
						if(!encontrado){
							System.out.println("Introduce un numCuenta que corresponda a tu persona.");
						}
					} while (!encontrado);
						cuenta2 = gfcuentas.obtenerCuenta(numCuenta2, nombreFicheroMaestroCuentas,
								nombreFicheroMovimientoCuentas);

					System.out.println("Cuanto dinero desea transferible?");
					saldo = Double.parseDouble(sc.nextLine());
					CuentaImp Cuentaanterior = cuenta.clone();

					cuenta.setSaldo(-saldo);
					gfcuentas.escribirMovimiento(nombreFicheroMovimientoCuentas, cuenta);
					cuenta2.setSaldo(saldo);
					gfcuentas.escribirMovimiento(nombreFicheroMovimientoCuentas, cuenta2);

					do {
						System.out.println("Desea recibo? (S o N)");
						recibo = Character.toUpperCase(sc.nextLine().charAt(0));
					} while (recibo != 'S' && recibo != 'N');
					if (recibo == 'S') {
						gfTextos.ReciboTransferencia(Cuentaanterior, cuenta2, cuenta2);
					}
				}
				
				break;
			}//fin segun
			
			//mostrar menu y validar opcion de menu
			do{
				MenuModoUsuario();
				opcionmenu=Integer.parseInt(sc.nextLine());
			}while(opcionmenu<0 || opcionmenu>4);
		}//fin mientras
	}
	
	/*
	 *	PINTAR MENU MODO USUARIO 
	 * 	Breve Comentario:
	 * 		El subprograma pinta en pantalla el menu de modo usuario
	 * 	Cabecera:
	 * 		 static void MenuModoUsuario()
	 * 	Precondiciones:
	 * 	Entradas:
	 * 	Salidas:
	 * 	Postcondiciones:
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
	  * Breve Comentario:
	 * 		El subprograma pinta en pantalla el menu de modo administrador
	 * 	Cabecera:
	 * 		static void MenuModoAdministrador()
	 * 	Precondiciones:
	 * 	Entradas:
	 * 	Salidas:
	 * 	Postcondiciones:
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
	 *  * 	Breve Comentario:
	 * 		El subprograma pinta en pantalla el menu principal
	 * 	Cabecera:
	 * 		 static void MenuPrincipal()
	 * 	Precondiciones:
	 * 	Entradas:
	 * 	Salidas:
	 * 	Postcondiciones:
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
