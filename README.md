# Microblogging API

## Descripción
La aplicación de Microblogging es una versión simplificada de una plataforma de microblogging similar a Twitter. Permite a los usuarios publicar, seguir a otros usuarios y ver el timeline de tweets.

## Tecnologías Utilizadas
- **Java Spring Boot**: El backend de la aplicación está desarrollado en Java utilizando el framework Spring Boot.
- **MongoDB**: La base de datos utilizada es MongoDB, con dos colecciones, users y tweets.
- **Docker**: La aplicación está contenerizada utilizando Docker.

## Arquitectura de tres capas
La arquitectura de la aplicación posee tres capas: Controller, Service, Repository, cada una con una responsabilidad específica.
![Arquitectura](https://github.com/llullmariasol/microblogging/assets/50931383/acf4a650-357a-420b-b7d2-ff185cf803a7)

## Pasos para levantar el proyecto localmente

Siga estos pasos para ejecutar la aplicación localmente:

1. Clonar el repositorio
2. Navegar al directorio del proyecto: `cd microblogging`
3. Construir la imagen de Docker: `docker build -t microblogging .`
4. Ejecutar la aplicación en un contenedor: `docker run -p 8080:8080 microblogging`
VER
La aplicación estará disponible en [http://localhost:8080](http://localhost:8080).

## Contribuciones
