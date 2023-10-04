# Título del Proyecto

_SuperHeroes API Services_

## Comenzando 🚀

_Mantenimiento CRUD de súper héroes._

### Instalación 🔧

Arrancar la aplicación:

    mvn clean spring-boot:run

Crear un fichero JAR del proyecto

    mvn clean package

Arracar aplicación desde el fichero jar, default profile

    java -jar target/superheroAPI-1.0.0.jar

Petición signUp

    curl --location 'http://localhost:8080/api/v1/auth/signup' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "firstName": "ivan",
        "lastName": "vilaro",
        "email": "vilaro.ivan@gmail.com",
        "password": "12345"
    }'

Petición signIn y generar token autenticación

    curl --location 'http://localhost:8080/api/v1/auth/signin' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "email":"vilaro.ivan@gmail.com",
        "password":"12345"
    }'

Petición para consultar todos los Súper héroesc

    curl --location 'http://localhost:8080/api/v1/superHeroes' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aWxhcm8uaXZhbkBnbWFpbC5jb20iLCJpYXQiOjE2OTY0MjYxNDMsImV4cCI6MTY5NjQyNzU4M30.3U93NBf2I1Smx-EETEfcufdL6WWzIa3h_KJq8kxVUDk'

Petición para consultar un único súper héroe por id

    curl --location 'http://localhost:8080/api/v1/superHeroes/1' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aWxhcm8uaXZhbkBnbWFpbC5jb20iLCJpYXQiOjE2OTYzNTgxMDYsImV4cCI6MTY5NjM1OTU0Nn0.iN1AtWVkLXqljaViLMwzc9PyiveTZjUa31a7JXUa8dk'

Petición para consultar todos los súper héroes que contienen, en su nombre, el valor de un parámetro enviado en la petición

    curl --location 'http://localhost:8080/api/v1/superHeroes/name/sup' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aWxhcm8uaXZhbkBnbWFpbC5jb20iLCJpYXQiOjE2OTYzNTgxMDYsImV4cCI6MTY5NjM1OTU0Nn0.iN1AtWVkLXqljaViLMwzc9PyiveTZjUa31a7JXUa8dk'

Petición para crear un súper héroes

    curl --location 'http://localhost:8080/api/v1/superHeroes' \
    --header 'Content-Type: application/json' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aWxhcm8uaXZhbkBnbWFpbC5jb20iLCJpYXQiOjE2OTYzNTgxMDYsImV4cCI6MTY5NjM1OTU0Nn0.iN1AtWVkLXqljaViLMwzc9PyiveTZjUa31a7JXUa8dk' \
    --data '{
        "name":"Ironman"
    }'

Petición para modificar un súper héroe

    curl --location --request PUT 'http://localhost:8080/api/v1/superHeroes/1' \
    --header 'Content-Type: application/json' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aWxhcm8uaXZhbkBnbWFpbC5jb20iLCJpYXQiOjE2OTYzNTgxMDYsImV4cCI6MTY5NjM1OTU0Nn0.iN1AtWVkLXqljaViLMwzc9PyiveTZjUa31a7JXUa8dk' \
    --data '{
        "id":"1",
        "name":"Wonder Woman"
    }'

Petición para borrar un súper héroe

    curl --location --request DELETE 'http://localhost:8080/api/v1/superHeroes/1' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aWxhcm8uaXZhbkBnbWFpbC5jb20iLCJpYXQiOjE2OTYzNTgxMDYsImV4cCI6MTY5NjM1OTU0Nn0.iN1AtWVkLXqljaViLMwzc9PyiveTZjUa31a7JXUa8dk'

## Ejecutando las pruebas ⚙️

    mvn clean test

### Analice las pruebas end-to-end 🔩

Las pruebas se encargan de validar las siguientes casuisticas:

- Consultar todos los súper héroes.
- Consultar un único súper héroe por id.
- Consultar todos los súper héroes que contienen, en su nombre, el valor de un parámetro enviado en la petición. Por ejemplo, si enviamos “man” devolverá “Spiderman”, “Superman”, “Manolito el fuerte”, etc.
- Crear un súper héroe.
- Modificar un súper héroe.
- Eliminar un súper héroe.

## Construido con 🛠️

* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [Spring Security](https://spring.io/projects/spring-security/) - Para automatizar el control de acceso (autenticación y autorización) y sesión de una aplicación
* [H2 Database] (https://www.h2database.com/html/main.html) - Para administrar la bases de datos
* [Liquibase] (https://www.liquibase.org/) - Para el mantenimiento de los scripts DDL de base de datos
* [Junit] (https://junit.org/junit5/) - Para pruebas unitarias y de integración de la aplicacion
* [Swagger] (https://swagger.io/) Para documentar y utilizar servicios web RESTful
* [Docker](https://maven.apache.org/](https://www.docker.com/) -  Despliegue automático
* [Redis](https://rometools.github.io/rome/](https://redis.io/) - Usado para caché


## Autores ✒️

* **Ivan Vilaró** - *Freelance Senior Java* - [ivilarop](https://github.com/ivilarop)

---
