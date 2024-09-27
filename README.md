# API REST con Spring Boot

Proyecto de creación de una API REST usando Spring Boot y Spring Boot Security y autentificación con JSON Web Token (JWT).

Para ello se están siguiendo los siguientes videotutoriales de Youtube:

1. [Dominando API REST con Spring Boot: taller completo](https://www.youtube.com/watch?v=J9OiXcTuzak) :heavy_check_mark:
2. [Spring Security 6: la guía completa para principiantes](https://www.youtube.com/watch?v=IPWBQDMIYkc) :heavy_check_mark:
3. [Spring Security: El universo de los Tokens](https://www.youtube.com/watch?v=wzGCBZCknUs) :point_left: _en curso_

## Dependencias

- Spring Web
- Spring Data JPA
- Spring Security
- Spring Boot DevTools
- MariaDB Driver
- Lombok
- Validation
- [dotenv-java](https://mvnrepository.com/artifact/io.github.cdimascio/dotenv-java) 

## Al clonar el proyecto

1. Cambia la versión del JDK a la que estés usando (aquí se está usando la 17).
2. Renombra o crea un archivo nuevo de `.env.template` a `.env`.
3. El proyecto está creado usando MariaDB como SGBD, si quieres usar otro, tendrás que cambiar las dependencias del archivo `pom.xml`. Y añadir el driver tu SGBD.
4. Crea una base de datos previamente y configura el archivo `.env`.