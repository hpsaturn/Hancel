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

# ¿Cómo construir?

El proyecto se encuentra en la carpeta **hancel-project** el cual depende de las siguientes bibliotecas:

* HoloEverywhere: Version 1.6.8 de https://github.com/Prototik/HoloEverywhere
* ActionBarSherlock: http://actionbarsherlock.com/
* Google Play Services
* PageView 4.2

Google Play Services y PageView se encuentran dentro de la carpeta **hancel-project**.  Las demás bibliotecas las agregamos por conveniencia para construir la aplicación de forma más sencilla.

### Importar el proyecto en eclipse

* Haciendo clic en `File`-> `Import`-> `Git`-> `Projects from Git` La URL del proyecto la puedes encontrar al lado derecho (HTTPS clone URL).

![import](https://github.com/Izel/Hancel/blob/master/doc/img/import.png)

* Ingresar los datos para clonar el proyecto

![clone](https://github.com/Izel/Hancel/blob/master/doc/img/clone.png)

* Seleccionar la rama `Master`

![master](https://github.com/Izel/Hancel/blob/master/doc/img/master.png)

El proyecto iniciará la descarga en la ruta especificada.  Una vez finalizada la descarga, eclipse inicia con la operación import y muestra la siguiente ventana.

![importProjects](https://github.com/Izel/Hancel/blob/master/doc/img/importProjects.png)

* Seleccione la opción `import existing projects` y haga clic en `next`
* Seleccione todos los proyectos excepto `slider` y `demoActivity` y haga clic en `finalizar`
* Los proyectos quedan importados y deberían visualizarse de la siguiente forma:

![proyectosOK](https://github.com/Izel/Hancel/blob/master/doc/img/proyectosOk.png)


### Errores de classpath

Es posible que después de realizar el import deba modificar las características de los proyectos puesto que el archivo .classpath no esta en el repositorio y no se descarga. Si los proyectos presentan error haga lo siguiente:

* Haga clic derecho en cada uno de los proyectos `Properties`-> `Build Path`, seleccione la pestaña `source` y haga clic en el boton `Add source`
* Seleccione `gen` y `src` y haga clic en `ok`

![gen](https://github.com/Izel/Hancel/blob/master/doc/img/gen.png)

* Aparecerá un cuadro de dialogo. Seleccione `yes`
* Repita la operación para cada uno de los proyectos.


