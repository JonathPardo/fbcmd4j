package fbcmd4j;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;
import java.util.function.BiConsumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;




public class Utils {
	static final Logger logger = LogManager.getLogger(Utils.class);

	
	public static Properties loadPropertiesFromFile(String foldername, String filename) throws IOException{
		Properties props = new Properties();
		
		Path configFile = Paths.get(foldername, filename);
		
		
		if (Files.exists(configFile)){
			props.load(Files.newInputStream(configFile));
			
			
			BiConsumer<Object, Object> emptyProperty = (k, v) -> {
				if(((String)v).isEmpty())
					logger.info("La propiedad '" + k + "esta vacia de acuerdo a todo");
			};
		

			props.forEach(emptyProperty);
			
			// }
		} else {
			logger.info("Creando nuevo archivo de configuración.");
			Files.copy(Paths.get("fbcmd4j","facebook.properties"), configFile);
		}
		return props;
	}
	
	static void ConfigurarToken(String foldername, String filename, Properties props, Scanner scanner) throws FacebookException {
		Facebook facebook = new FacebookFactory().getInstance();
		facebook.setOAuthAppId(props.getProperty("oauth.appId"), props.getProperty("oauth.appSecret"));
		facebook.setOAuthPermissions(props.getProperty("oauth.permissions"));
		facebook.setOAuthAccessToken(new AccessToken(props.getProperty("oauth.accessToken"), null));
		
		System.out.println(props.getProperty("oauth.appId"));
		System.out.println(props.getProperty("oauth.appSecret"));
		System.out.println(props.getProperty("oauth.permissions"));
		System.out.println(props.getProperty("oauth.accessToken"));
		
		facebook.postStatusMessage("Gaara del Desierto creo que ya logre hacer algo ");

	}
	
}
