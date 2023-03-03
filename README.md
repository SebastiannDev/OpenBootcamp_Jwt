# **Spring boot api rest basico**

## **Objetivos**

* Aprender a desarrollar una api con todos los principios CRUD
* Concoer el ecosistema, que es un Bean, la Inyeccion de dependencias
* Comprender la implementación de JPA Repository
* Generar Logs dentro de spring boot para concer los estados de la aplcación
* Documentar la api con swagger y la libreria Open Api - springDoc
* Comprender como funciona JUnit en casos de test en Api Rest
* Ecosistema de artefactos, empaquetar un programa java, accediendo a variables de application.properties

# **Spring security**

Spring security hace parte de el ecosistema spring, este permite agregar seguridad a nuestra aplicación gestionando las autorizaciones, y la atenticación.

Para ello requiere de conocer fundamentalmente el flujo en el que spring security trabaja: 

1. **Filters**: el filtro permite interceptar todas las request, lanza excepciones si se desea y podemos agregar código que por ejemplo autentique un Jwt
   1. Se debe tener en cuenta que este filtro requiere implementar:  `OncePrequRequest`
2. **Configuracion**: se encarga de configurar todo el contexto de spring security como:
   1. Permitir rutas
   2. Denegar rutas a menos que tenga un: rol, autenticacion etc..
   3. Agregar filtros, y manejo de excepciones, definir el tipo de autenticación.
3. **Autenticacion**: este se encarga de crear una sessión en nuestra aplicación por medio de el `Authenticator Manager` basado en la el tipo de autenticación establecida asumira el `Authentication Provider` que usara:
   1. UserDetailsService
   2. UserDetails
4. **Autorizacion**: Se encarga `Authorization Manager` el cual se encarga de otorgar autorizaciones, conocer el estado actual de la autorización.
   > puedes revisar el link en la [Documentacion.](https://docs.spring.io/spring-security/reference/servlet/authorization/architecture.html)
  