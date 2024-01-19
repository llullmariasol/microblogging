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

## Funcionalidades
La API desarrollada proporciona endpoints con las siguientes funcionalidades:

**Usuarios**
- Creación de nuevos usuarios.
- Consulta de información de usuarios por ID.
- Actualización de información de usuarios.
- Eliminación de usuarios.
- Seguir y dejar de seguir a otros usuarios.
  
**Tweets**
- Creación de nuevos tweets.
- Consulta de información de tweets por ID.
- Eliminación de tweets.
- Obtención de una línea de tiempo basada en los usuarios seguidos.

## Cómo levantar el proyecto localmente usando Docker
1. Clonar el repositorio
2. Navegar al directorio del proyecto: `cd microblogging`
3. Ejecutar el siguiente comando: `docker compose up`
4. Se puede chequear los containers e imágenes levantados usando los siguientes comandos respectivamente: `docker ps` y `docker images`
5. La aplicación estará disponible en [http://localhost:6868](http://localhost:6868) y se puede probar utilizando Postman (en la raíz del repositorio se encuentra una colección "microblogging.json" con los endpoints)
