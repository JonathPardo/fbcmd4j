# fbcmd4j

## Instalación
Para descargar el archivo es necesario el comando git clone https://github.com/JonathPardo/fbcmd4j.git

## Uso
Importar archivo en eclipse
File> Import> Existing Projects into Workspace.

#Exportar 
File> Export> Runnable JAR file.

#Acciones
- (0) Configurar cliente
Para utilizar la aplicación es necesario tener el:
#oauth.appId
#oauth.appSecret

Despues de ingresarlo, sera redireccionado a la dirección  "https://developers.facebook.com/apps/"+oauth.appID+"/fb-login/"; (es automatico)
Se pide la configuración de un redireccionamiento.
Despues de ello se valida los permisos del usuario y se el url http://localhost:8080/?code=
Es necesario copiar el codigo que arroja e introducirlo en la aplicación
La aplicación hara la configuración del usuario y esta lista para utilizarse

- (1) Mostrar NewsFeed
Muestra las notificaciónes del usuario
- (2) Mostrar Wall
Muestra el muro del usuario

- (3) Escribir un estado
Crea un post en el muro del usuario

- (4) Publicar un link
Crea un post con un link en el muro del usuario

- (5) Salir
Salir de la aplicación


## Créditos

Basado en el codigo de: 
Jose Manuel Lopez Lujan, MIT
https://github.com/jm66/

Creador de la aplicación:
Jonathan Guadalupe Arellano Pardo
al02778355

## Licencia
El código está disponible bajo la licencia MIT. Consulte el archivo LICENSE en la raíz del proyecto para más información.
