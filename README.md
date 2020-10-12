
# **Laboratorio N°6**
## **CVDS-1**
### **Ciclos de Vida del Desarrollo de Software**

![](https://github.com/DonSantiagoS/LAB2CVDS/blob/master/Imagenes/Logo.png)

## BADGES

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/2e47eccde11947ba9922585fae70782a)](https://app.codacy.com/manual/DonSantiagoS/LAB6CVDS/dashboard)
[![CircleCI](https://circleci.com/gh/MysticUnios1998/CVDS-Lab6_CI.svg?style=svg)](https://app.circleci.com/pipelines/github/DonSantiagoS)

[Laboratorio 6 Heroku][1]

## Parte I. Integración continua

Para este ejercicio haga uso de la versión funcional de su aplicación: la rama 'master' con la aplicación basada en un 'mock' de la lógica, o la versión completa (en caso de que ya la tenga) ya mezclada en la rama 'master'.

1. Cree (si no la tiene aún) una cuenta en el proveedor PAAS [Heroku][2]

![](https://github.com/DonSantiagoS/LAB6CVDS/blob/master/imagenes/imagen1.PNG)

2. Acceda a su cuenta en Heroku y cree una nueva aplicación

![](https://github.com/DonSantiagoS/LAB6CVDS/blob/master/imagenes/imagen2.PNG)

3. Después de crear su cuenta en Heroku y la respectiva aplicación, genere una llave de API: Opción Manage Account:

![](https://github.com/DonSantiagoS/LAB6CVDS/blob/master/imagenes/imagen3.PNG)

4. Ingrese a la plataforma de integración contínua Circle.CI (www.circleci.com). Para ingresar, basta que se autentique con su usuario de GitHUB.

![](https://github.com/DonSantiagoS/LAB6CVDS/blob/master/imagenes/imagen4.PNG)

despues de elegir el proyecto, Alli debera elegir usar la configuracion existente y elegir la opcion de construir

![](https://github.com/DonSantiagoS/LAB6CVDS/blob/master/imagenes/imagen7.PNG)

para posteriormente tener en la opcion de  **Pipelines**

![](https://github.com/DonSantiagoS/LAB6CVDS/blob/master/imagenes/imagen5.PNG)

5. Finalmente se deberan configurar la variable de entorno en circleci, donde debera crearla poniendo como nombre **HEROKU_API_KEY** para alli digitar la llave API generada por heroku de manera que estas puedan realizar la conexion

![](https://github.com/DonSantiagoS/LAB6CVDS/blob/master/imagenes/imagen6.PNG)

6. Si todo queda correctamente configurado, cada vez que hagan un PUSH al repositorio, CircleCI ejecutará la fase de construcción del proyecto. Para que cuando las pruebas pasen automáticamente se despliegue en Heroku, debe definir en el archivo circle.yml (ubicado en la raíz del proyecto):

+ La rama del repositorio de GitHUB que se desplegará en Heroku. o El nombre de la aplicación de Heroku en la que se hará el despliegue.
+ La ejecución de la fase ‘site’ de Maven, para generar la documentación de pruebas, cubrimiento de pruebas y análisis estático (cuando las mismas sean habilitadas).

Ahora bien en el directorio raiz se debera crear el archivo **circle.yml**
````
machine:
  java:
    version: oraclejdk8

deployment:
  staging:
    branch: master
    heroku: 
      appname: NOMBRE_APLICACION_EN_HEROKU
	
````

![](https://github.com/DonSantiagoS/LAB6CVDS/blob/master/imagenes/imagen9.PNG)

7. Rectifique que en el pom.xml, en la fase de construcción, se tenga el siguiente plugin (es decir, dentro de <build><plugins>):

````
<!-- Plugin configuration for Heroku compatibility. -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-dependency-plugin</artifactId>
            <version>2.1</version>
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>copy</goal>
                    </goals>
                    <configuration>
                        <artifactItems>
                            <artifactItem>
                                <groupId>com.github.jsimone</groupId>
                                <artifactId>webapp-runner</artifactId>
                                <version>8.0.30.2</version>
                                <destFileName>webapp-runner.jar</destFileName>
                            </artifactItem>
                        </artifactItems>

                    </configuration>
                </execution>

            </executions>
        </plugin>
		
````

![](https://github.com/DonSantiagoS/LAB6CVDS/blob/master/imagenes/imagen10.PNG)


**Nota: Si en el pom.xml ya hay otro plugin con el mismo y , reemplácelo por el anteriormente mostrado.**

8. Heroku requiere los siguientes archivos de configuración (con sus respectivos contenidos) en el directorio raíz del proyecto, de manera que sea qué versión de Java utilizar, y cómo iniciar la aplicación, respectivamente:

**system.properties**

````
java.runtime.version=1.8
````
**Procfile**

````
web:    java $JAVA_OPTS -jar target/dependency/webapp-runner.jar --port $PORT target/*.war
````
![](https://github.com/DonSantiagoS/LAB6CVDS/blob/master/imagenes/imagen8.PNG)

9. El ambiente de despliegue contínuo requiere también un archivo de configuración 'circle.yml' en la raíz del proyecto, en el cual se indica (entre otras cosas) en qué aplicación de Heroku se debe desplegar la aplicación que está en GitHUB. Puede basarse en el siguiente archivo, teniendo en cuenta que se debe ajustar el parámetro 'appname': https://github.com/PDSW-ECI/base-proyectos/blob/master/circle.yml

![](https://github.com/DonSantiagoS/LAB6CVDS/blob/master/imagenes/imagen9.PNG)

10. Haga commit y push de su repositorio local a GitHub. Abra la consola de CircleCI y verifique que el de descarga, compilación, y despliegue. Igualmente, verifique que la aplicación haya sido desplegada en Heroku.

![](https://github.com/DonSantiagoS/LAB6CVDS/blob/master/imagenes/imagen11.PNG)

11. Ahora, va a integrar un entorno de Análisis de Calidad de Código a su proyecto, el cual detecte contínuamente defectos asociados al mismo. Autentíquese en CODACY con su cuenta de GitHUB, y agregue el proyecto antes creado.

![](https://github.com/DonSantiagoS/LAB6CVDS/blob/master/imagenes/imagen12.PNG)

12. Cree un archivo README.md para su proyecto, y asocie al mismo dos 'badges', que permitan conocer el estado del proyecto en cualquier momento: uno para Circle.CI, y otro para CODACY. El proyecto usado como referencia, ya incluye dichos 'badges' en su archivo README: https://github.com/PDSW-ECI/base-proyectos

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/2e47eccde11947ba9922585fae70782a)](https://app.codacy.com/manual/DonSantiagoS/LAB6CVDS/dashboard)
[![CircleCI](https://circleci.com/gh/MysticUnios1998/CVDS-Lab6_CI.svg?style=svg)](https://app.circleci.com/pipelines/github/DonSantiagoS)

![](https://github.com/DonSantiagoS/LAB6CVDS/blob/master/imagenes/imagen13.PNG)


## Parte II. Interfaz Gráfica

##### Autores:
 * Santiago Buitrago
 * Andres Cubillos

[1]:https://labcvds6.herokuapp.com/
[2]:https://www.heroku.com
[3]:https://docs.oracle.com/javaee/7/api/javax/faces/webapp/FacesServlet.html
[4]:https://users.dcc.uchile.cl/~jbarrios/servlets/general.html
[5]:https://en.wikipedia.org/wiki/List_of_HTTP_status_codes

