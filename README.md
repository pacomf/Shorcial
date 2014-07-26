Shorcial
=========

<a href="http://shorcial.wordpress.com/">
  <p align="center">
    <img alt="Shorcial" src="http://shorcial.files.wordpress.com/2014/07/screenshot_2014-07-26-11-19-551.png?w=310&h=550" />
  </p>
</a>

**Red Social de las Playas** de Canarias, con escalabilidad a todas las **playas del Mundo**, para el [**II Concurso Open Data Canarias 2014**](http://www.opendatacanarias.es/ii-concurso-open-data).

<a href="http://opendatacanarias.es/">
  <p align="center">
    <img alt="Open Data Canarias" src="http://www.opendatacanarias.es/datos/uploads/group/20140619-043808.941708open-data-canarias.png" />
  </p>
</a>

## La idea... ##

Partiendo de la base que consideramos al Turismo como el mayor motor económico de las Islas Canarias, queríamos aportar nuestro **granito de arena** y poder ofrecer a nuestros turistas una herramienta sencilla, bonita y útil para que pudieran encontrar y compartir las Playas de Nuestras Islas (Pero hemos elaborado una herramienta totalmente extrapolable y con una capacidad de escalabilidad tremenda).

La idea de Shorcial surge con el fin de presentar una herramienta útil para el concurso de Open Data Canarias 2014. Para ello hemos quedado tres amigos y hemos decidido realizar una **aplicación móvil** (con un **backend en servidor**), al ser los tres desarrolladores **Android** hemos decidido empezar con esta plataforma, para más adelante continuar con el resto de plataformas.

Con esta aplicación lo que queremos es ofrecer una herramienta útil que nos ayude a encontrar nuestra playa ideal en cada momento.

El nombre de Shorcial, es un juego de las palabras Inglesas [**Sho**]re + So[**cial**] ("Costa + Social"), queriendo dejar claro el aspecto **Social** de conocer las Costas de todo el mundo.


## Funcionalidades ##

0. **Multilenguaje**
  - **Inglés**
  - **Español**
  - Alemán (En desarrollo...)
  - Ruso (Próximamente)
  - Francés (Próximamente)
  - Italiano (Próximamente)
  - Chino (Próximamente)
  - Japonés (Próximamante)
1. Encontrar Playas Cercanas a ti.
2. **Buscar Playas** por Nombre o Cercanas a un sitio determinado.
3. Ver mucha información interesante acerca de las playas.
  - Nombre de La Playa
  - **Temperatura Actual** en la Playa
  - Localización (Con un ¿Cómo llegar?)
  - **Ver en Directo** (si dispone de Web Cam)
  - Si tiene o no Bandera Azul
  - Grado de Dificultad de Acceso
  - Tipo de Arena
  - Grado de Limpieza
  - Si tiene o no Hamacas
  - Si tiene o no Sombrillas
  - Si tiene o no Rompeolas
  - Si tiene o no Chiringuitos
  - Si tiene o no Duchas
  - Si tiene o no Socorrista
4. Poder **Comentar y Valorar las Playas**.
5. Poder **Lanzar Mensajes en Botellas** desde las Playas (Hace falta estar presencialmente en la misma playa para lanzar Mensajes y para ver los Mensajes de otros usuarios que han llegado a esa Playa).
6. **Hacer CheckIn** en las Playas
7. **Añadir** nuevas playas.
8. Editar algunos parámetyros de las Playas ya existentes.
9. Informar de Playas incorrectas.

## Conjuntos de Datos ##

<a href="http://opendatacanarias.es/">
  <p align="center">
    <img alt="Open Data Canarias" src="http://www.opendatacanarias.es/datos/uploads/group/20140619-043808.941708open-data-canarias.png" />
  </p>
</a>

La Aplicación usa **diferentes conjuntos de datos** del portal [**Open Data Canarias**](http://opendatacanarias.es/). Entre ellos se encuentran:

1. [**Información acerca de las Playas**](http://www.opendatacanarias.es/datos/dataset/tdt-playas-de-tenerife)
2. [**Información de WebCams**](http://www.opendatacanarias.es/datos/dataset/cic-camaras-en-carreteras)

Como el segundo conjunto de Datos era sólo de Carreteras, lo hemos complementado con otras WebCams abiertas que hemos encontrado en otras fuentes de datos, con el fin de otorgar mucho mayor potencial de datos a la Aplicación.

## ¿Cuántas Playas hay y de Dónde? ##

En la aplicación inicial, en la fase de lanzamiento, teníamos **107 Playas de toda Canarias**. Exactamente:

- 50 Playas de Tenerife
- 20 Playas de Gran Canaria
- 10 Playas de Fuerteventura
- 10 Playas de Lanzarote
- 5 Playas de La Gomera
- 5 Playas de El Hierro
- 5 Playas de La Palma
- 1 Playa de La Graciosa
- 1 Playa de La Isla de Lobos

Algunas de estas playas cuentan con web cam... tenemos censadas unas **50 webcams a lo largo de las Playas Canarias**, para poder ver en tiempo real, o casi, la situación de la playa.

**Estos datos pueden haber aumentado de manera significativa**, y aumentarán con el tiempo, ya que dotamos a los usuarios de poder **añadir nuevas playas de una manera sencilla y efectiva**. La comunidad juega un papel muy importante en esto, y entre todos podremos crear la mayor base de datos sobre playas que se haya visto nunca. ¡Cualquier parte del mundo tiene cabida!

## Tecnologías usadas ##

Para el desarrollo del proyecto se han realizado dos aplicaciones:

1. [**Aplicación Android Nativa**](https://github.com/pacomf/playasODC_app)
2. [**Backend en Node.js**](https://github.com/alelit4/playasODC_server)

Se ha usado en todo momento, Software Libre:

* Android
  - [**Facebook SDK**](https://github.com/facebook/facebook-android-sdk): Permite Login y Recabar información con Facebook.
  - [**ADA Framework**](https://github.com/mobandme/ADA-Framework): ORM de SQL Lite, para el manejo de base de datos.
  - [**GSON**](https://code.google.com/p/google-gson/): Utilidades para el manejo del formato JSON.
  - [**Crouton**](https://github.com/keyboardsurfer/Crouton): Alternativa mejorada de los típicos Toasts.
  - [**ImageLoader**](https://github.com/nostra13/Android-Universal-Image-Loader): Librería optimizada para el manejo de imágenes.
  - [**Rebound**](https://github.com/facebook/rebound): Librería para dar Efecto de Rebote a los Elementos al pulsarlos.

* Servidor (BackEnd)
  - [**Node.js**] (http://www.nodejs.org/): Tecnología usada en el servidor como BackEnd.
  - [**Mongoose**](https://github.com/LearnBoost/mongoose): Módulo para Node.js, ORM de MongoDB, para el manejo de la base de datos.
  - [**Express.js**](https://github.com/visionmedia/express): Módulo para Node.js, para crear WebServices de tipo REST.
  - [**Node-CSV**](https://github.com/wdavidw/node-csv): Modulo de Node.js, para manejar CSVs.

## Cómo participar ##

Estamos deseando que la comunidad se una al proyecto, y puedan participar en él. Si quieres desarrollar alguna nueva funcionalidad, sólo debes tener en cuenta que se usan:

* Android Studio 0.8.2 o Superior. Con la API 14 o Superior de Android (4.0+) y las Google APIs.
* MongoDB
* Node.js

Con esos tres componentes, te puedes descargar nuestros proyectos, ver su código fuente, compilarlos y ejecutarlos a tus anchas. Puedes implementar nuevas funcionalidades o arreglar bugs.

¡Haznos Pull Requests!

## Autores ##

Este proyecto ha sido desarrollado, en conjunto, por:

<!-- Tabla -->
<table cellspacing="0">
  <tr  style="background-color: #E3E3E3;">
    <td> <b>Avatar</b> </td>
    <td> <b>Nombre</b> </td>
	<td> <b>Perfil de LinkedIn</b> </td>
  </tr>
  <tr style="background-color: #FFFFFF;">
    <td> <img width="64"src="http://shorcial.files.wordpress.com/2014/07/paco.jpg?w=139&h=197"/> </td>
    <td> Paco Martín Fernández </td>
	<td> <a href="es.linkedin.com/in/fmartinfdez/">Ver Pérfil</a> </td>
  </tr>
  <tr style="background-color: #FFFFFF;">
    <td> <img width="64"src="http://shorcial.files.wordpress.com/2014/07/dsc07622.jpg?w=199&h=212"/> </td>
    <td> Iván Santos González </td>
	<td> <a href="es.linkedin.com/pub/iván-santos-gonzález/3b/924/69">Ver Pérfil</a> </td>
  </tr>
  <tr style="background-color: #FFFFFF;">
    <td> <img width="64"src="http://shorcial.files.wordpress.com/2014/07/ale.png?w=187&h=196"/> </td>
    <td> Alexandra Rivero García </td>
	<td> <a href="es.linkedin.com/pub/alexandra-rivero/3b/a05/839">Ver Pérfil</a> </td>
  </tr>
</table>
<!-- Fin tabla -->

