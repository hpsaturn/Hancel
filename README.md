hancel-factual
==============

Hancel es una aplicación móvil para protección personal. Permite enviar notificaciones a redes de contactos (anillos de seguridad) en caso de peligro, realizar llamadas utilizando VoIP y enviar SMS cifrados ente clientes Hancel. 

# Características de Hancel

Puede utilizarse por profesionales o activistas como herramienta de protección personal al realizar trabajos de campo en zonas de peligro. En el caso de los periodistas, puede utilizarse para proteger la libertad de expresión al presentarse un caso de ataque contra su integridad personal. 

Hancel cuenta con las siguientes carácterísticas y tipos de alerta:

* **Anillos de seguridad:** Permite configurar grupos de contactos(anillos de seguridad) para enviar notificaciones y alertas de peligro. 
* **SMS cifrados:** Personas con alta probabilidad de ataque y bajo vigilancia, pueden enviar mensajes SMS cifrados a otros clientes Hancel. 
* **VoIp:** Realizar llamadas a otros clientes Hancel utilizando la red de datos.
* **Rastreo:** Permite programar el posicionamiento GPS por intervalo de tiempo y notificarlas al anillo de seguridad.
* **¿Todo bien?:** Notificación automática generada en un intervalo de tiempo (configurable) y debe ser atendida por el usuario o se envía una alerta al anillo de seguridad en caso de que el usuario no reaccione ante estas notificaciones después de un tiempo (configurable).
* **Pánico:** Cuenta con una alerta de emergencia (botón de pánico) que notifica al anillo de seguridad que algo esta definitivamente mal (situación de peligro extrema).


#Screenshots

![Login](http://i.imgur.com/Zt1PDfNl.png =250x) ' ![Imgur](http://i.imgur.com/3CXg2axl.png =250x)

# ¿Cómo construir?

El proyecto se encuentra en la carpeta **hancel-project** el cual depende de las siguientes bibliotecas:

* HoloEverywhere: Version 1.6.8 de https://github.com/Prototik/HoloEverywhere
* ActionBarSherlock: http://actionbarsherlock.com/
* Google Play Services
* PageView 4.2

Google Play Services y PageView se encuentran dentro de la carpeta **hancel-project**.  Las demás bibliotecas las agregamos por conveniencia para construir la aplicación de forma más sencilla.

### Importar el proyecto en eclipse

* Haciendo clic en `Archivo`-> `Importar`->`Importar desde Git` La URL del proyecto la puedes encontrar al lado derecho (HTTPS clone URL).   

<import.jpg>

* Seleccionar `Master`

<master.jpg>

* Descargue el proyecto en la ruta `/home/<user>/git/Hancel`
* Hacer clic derecho en la sección de `package explorer` y seleccionar `Import`->`Git`->`Projects from Git`.
* Seleccione `Local`.
* Seleccione el repositorio local (carpeda en la que realizó la descarga).
* Seleccione la opción `Import Existing Projects`.
* Seleccione todos los proyectos excepto `slider` y `demoActivity`.
* Haga clic en `finalizar`
* Los proyectos deben visualizarse de la siguiente forma:

<proyectos.jpg>

Hancel utiliza ACRA como sistema de log. Como actualmente estamos importando el JAR en lugar de compilar como biblioteca, debes verificar que el proyecto **Holo Everywhere Library** incluya la ruta correcta al archivo `acra-4.5.0.jar` localizado en la carpeta `libs` de Holo Everywhere Library

![Imgur](http://i.imgur.com/Xeh2JSc.png)

