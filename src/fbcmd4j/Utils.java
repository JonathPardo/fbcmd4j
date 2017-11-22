package fbcmd4j;


import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;






public class Utils {
	static final Logger logger = LogManager.getLogger(Utils.class);

	
	public static Properties loadPropertiesFromFile(String foldername, String filename) throws IOException{
		
	
		//Rebuild
		Properties props = new Properties();
		Path configFile = Paths.get(foldername, filename);
		Path configFolder = Paths.get(foldername);
		
		if (!Files.exists(configFile)) {
			if (!Files.exists(configFolder)){
				Files.createDirectory(configFolder);
			logger.info("Creando nuevo archivo de configuracion.");
			Files.copy(Utils.class.getResourceAsStream("facebook.properties"), configFile);
			}
		}

		props.load(Files.newInputStream(configFile));
		BiConsumer<Object, Object> emptyProperty = (k, v) -> {
			if(((String)v).isEmpty())
				logger.info("La propiedad '" + k + "' esta vacia");
		};
		props.forEach(emptyProperty);

		return props;	
	}
	
	static void ConfigurarToken(String foldername, String filename, Properties props, Scanner scanner) throws IOException, URISyntaxException, JSONException {
		
		String redirectUrl = "http://localhost:8080/";
		

			System.out.println("Ingrese su oauth.appID o Identificador de la aplicación:");
			props.setProperty("oauth.appId", scanner.nextLine());
			System.out.println("Ingrese su oauth.appSecret:");
			props.setProperty("oauth.appSecret", scanner.nextLine());
			String permis = "https://developers.facebook.com/apps/"+props.getProperty("oauth.appId")+"/fb-login/";
		System.out.println("Para utilizar el codigo con su aplicación es necesario ir a la siguente url");
		System.out.println(permis);
		System.out.println("Agrege en URI de redireccionamiento de OAuth válidos la siguente direccion:");
		System.out.println(redirectUrl);
		
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Desktop.getDesktop().browse(new URI(permis));
		System.out.println("Presione enter cuando haya configurado");
		scanner.nextLine();
		
				
				String returnValue = "https://www.facebook.com/dialog/oauth?client_id="
						+ props.getProperty("oauth.appId") + "&redirect_uri=" + redirectUrl
						+ "&scope="+props.getProperty("oauth.permissions");
				System.out.println("Despues de haber aceptado los permisos lo redirigira a la siguente url \nhttp://localhost:8080/?code=");
				System.out.println("no cierre la pagina");
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Desktop.getDesktop().browse(new URI(returnValue));
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Si no se abrio el navegador, por favor ingrese a " + returnValue);
				System.out.println("Por favor ingrese el codigo que esta despues de http://localhost:8080/?code=");
				String code = scanner.nextLine();
		String quedope="https://graph.facebook.com/oauth/access_token?client_id="
		+ props.getProperty("oauth.appId")+ "&redirect_uri=" + redirectUrl + "&client_secret="
		+ props.getProperty("oauth.appSecret") + "&code="+code;
		 JSONObject json = readJsonFromUrl(quedope);
		 //System.out.println(json.toString());
		 //System.out.println(json.get("access_token"));
		 String key = (String) json.get("access_token");
		 System.out.println("Se ha cargado correctamente el access token de usuario");
		 props.setProperty("oauth.accessToken", key);
		 
		 saveProperties(foldername, filename, props);
			logger.info("Configuraión guardada exitosamente.");
	}
	
	  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		    InputStream is = new URL(url).openStream();
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONObject json = new JSONObject(jsonText);
		      return json;
		    } finally {
		      is.close();
		    }
		  }
	

	private static String readAll(BufferedReader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	}

	public static void saveProperties(String foldername, String filename, Properties props) throws IOException{
		Path configFile = Paths.get(foldername, filename);
		props.store(Files.newOutputStream(configFile), "Generado por ObtenerAccessToken");
	}
	
	public static Facebook CargarConfiguracionFb(Properties props)
	{
		Facebook facebook = new FacebookFactory().getInstance();
		facebook.setOAuthAppId(props.getProperty("oauth.appId"), props.getProperty("oauth.appSecret"));
		facebook.setOAuthPermissions(props.getProperty("oauth.permissions"));
		facebook.setOAuthAccessToken(new AccessToken(props.getProperty("oauth.accessToken"), null));
		logger.info("Se configuro correctamente facebook");
		return facebook;
	}
	
	
	public static void NewsFeed(ResponseList<Post> statuses){
		
		 for (Post status : statuses) {
			 System.out.println(status.getStory());
         }
		 logger.info("Se obtuvo las NewsFeed");
	}
	
	public static void Wall(ResponseList<Post> statuses){
		
		 for (Post status : statuses) {
			 System.out.println(status.getMessage());
        }
		 logger.info("Se obtuvo el Wall (muro)");
	}
	
	public static void crearPost(Facebook facebook, String post) throws FacebookException{
			facebook.postStatusMessage(post);
			System.out.println("Se creo el post con el contenido: "+post);
			logger.info("Se creo un post en facebook");
	}
	public static void crearPostLink(Facebook facebook, String post) throws FacebookException, MalformedURLException{
		facebook.postLink(new URL(post));
		System.out.println("Se creo un nuevo post con el link: "+post);
		logger.info("Se creo un post con link en facebook");
}
	
}
