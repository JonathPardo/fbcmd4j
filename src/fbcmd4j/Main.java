package fbcmd4j;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

import javax.xml.parsers.FactoryConfigurationError;

import facebook4j.*;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class Main {
	private static final Logger logger = LogManager.getLogger(Main.class);
	private static final String CONFIG_DIR = "config";
	private static final String CONFIG_FILE = "facebook.properties";
	private static final String APP_VERSION = "v1.0";

	public static void main(String[] args) {
		logger.info("inicializando app");
		Facebook facebook = null;
		Properties props = null;
		// Carga propiedades 
		try {
			props = Utils.loadPropertiesFromFile(CONFIG_DIR, CONFIG_FILE);
		} catch (IOException ex) {	
			System.out.println(ex);
			logger.error(ex);
		}

		int seleccion;
		try (Scanner scanner = new Scanner(System.in)){
			while(true){
				
				
				// Inicio Menu
				System.out.format("Aplicación de Facebook %s\n\n", APP_VERSION);
				System.out.println("Opciones: ");
				System.out.println("(0) Configurar cliente");
				System.out.println("(1) Mostrar NewsFeed");
				System.out.println("(2) Mostrar Wall");
				System.out.println("(3) Escribir un estado");
				System.out.println("(4) Publicar un link");
				System.out.println("(5) Salir");
				System.out.println("\nPor favor ingrese una opción: ");
				// Fin de Menu
				try {
					seleccion = scanner.nextInt();
					scanner.nextLine();

					switch(seleccion){
						case 0:
							System.out.println("Selecciono Configurando cliente");
							Utils.ConfigurarToken(CONFIG_DIR, CONFIG_FILE, props, scanner);
							props = Utils.loadPropertiesFromFile(CONFIG_DIR, CONFIG_FILE);
							break;
						case 1: 
							System.out.println("Selecciono Mostrar NewsFeed");
							
							break;
						case 2:
							System.out.println("Selecciono Mostrar Wall");
							
							break;
						case 3:
							System.out.println("Selecciono Crear un nuevo estado");
							
							
							break;
						case 4:
							System.out.println("Selecciono Crear un link");
							
							
							break;
						case 5:
							System.out.println("Selecciono Salir");
							
							System.exit(0);
						default:
							logger.error("Opción inválida");
							break;
					}
				} catch (InputMismatchException ex){
					System.out.println("Ocurrió un errror, favor de revisar log.");
					logger.error("Opción inválida. %s. \n", ex.getClass());
					scanner.next();
				} catch (FactoryConfigurationError ex){
					System.out.println("Ocurrió un errror, favor de revisar log.");
					logger.error(ex.getMessage());
					scanner.next();
				} catch (Exception ex){
					System.out.println("Ocurrió un errror, favor de revisar log.");
					logger.error(ex);
					scanner.next();
				} 
			}
		} catch (Exception ex){
			logger.error(ex);
		}
	}
}
