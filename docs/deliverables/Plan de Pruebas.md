# Plan de Pruebas

## 1. Introducción

Este documento describe el plan de pruebas para el proyecto **Honey** desarrollado en el marco de la asignatura **Diseño y Pruebas 1** por el grupo **L1-03**. El objetivo del plan de pruebas es garantizar que el software desarrollado cumple con los requisitos especificados en las historias de usuario y que se han realizado las pruebas necesarias para validar su funcionamiento.

## 2. Alcance

El alcance de este plan de pruebas incluye:

- Pruebas unitarias.
  - Pruebas unitarias de backend incluyendo pruebas servicios o repositorios
  - Pruebas unitarias de frontend: pruebas de las funciones javascript creadas en frontend.
  - Pruebas unitarias de interfaz de usuario. Usan la interfaz de  usuario de nuestros componentes frontend.
- Pruebas de integración.  En nuestro caso principalmente son pruebas de controladores.

## 3. Estrategia de Pruebas

### 3.1 Tipos de Pruebas

#### 3.1.1 Pruebas Unitarias
Las pruebas unitarias se realizarán para verificar el correcto funcionamiento de los componentes individuales del software. Se utilizarán herramientas de automatización de pruebas como **JUnit** en background y .

#### 3.1.2 Pruebas de Integración
Las pruebas de integración se enfocarán en evaluar la interacción entre los distintos módulos o componentes del sistema, nosotros las realizaremos a nivel de API, probando nuestros controladores Spring.

## 4. Herramientas y Entorno de Pruebas

### 4.1 Herramientas
- **Maven**: Gestión de dependencias y ejecución de las pruebas.
- **JUnit**: Framework de pruebas unitarias.
- **Jacoco**: Generación de informes de cobertura de código.
- **Jest**: Framework para pruebas unitarias en javascript.
- **React-test**: Liberaría para la creación de pruebas unitarias de componentes React.

### 4.2 Entorno de Pruebas
Las pruebas se ejecutarán en el entorno de desarrollo y, eventualmente, en el entorno de pruebas del servidor de integración continua.

## 5. Planificación de Pruebas

### 5.1 Cobertura de Pruebas

El informe de cobertura de pruebas se puede consultar en "DP1-2024-2025--l1-03\target\site\jacoco\index.html".

### 5.2 Matriz de Trazabilidad

# Matriz de Trazabilidad
|                         |                                                            |                                                                                                                  |              |                        |
| :---------------------: | :--------------------------------------------------------: | :--------------------------------------------------------------------------------------------------------------: | :----------: | :--------------------: |
| **Historia de Usuario** |                         **Prueba**                         |                                                  **Descripción**                                                 |  **Estado**  |        **Tipo**        |
|           HU-1          |                  `shouldAuthenticateUser`                  |                Verifica el proceso de autenticación de un usuario y la generación de un token JWT.               | Implementada |    Prueba funcional    |
|           HU-1          |                    `shouldValidateToken`                   |                                      Verifica la validación de un token JWT.                                     | Implementada | Prueba de integración. |
|           HU-1          |                   shouldNotValidateToken                   |                         Verifica que un token JWT inválido no sea validado correctamente.                        | Implementada |  Prueba de integración |
|           HU-2          |                     shouldRegisterUser                     |                    Verifica que un usuario se registre correctamente si no existe previamente.                   | Implementada |  Prueba de integración |
|           HU-2          |         `shouldNotRegisterUserWithExistingUsername`        |               Verifica que no se pueda registrar un usuario con un nombre de usuario ya existente.               | Implementada |  Prueba de integración |
|     No especificada     |                   `shouldCreateAdminUser`                  |         Verifica que un usuario admin sea creado correctamente y que el número total de usuarios aumente.        | Implementada |  Prueba de integración |
|           HU-2          |                  `shouldCreatePlayerUser`                  |        Verifica que un usuario jugador sea creado correctamente y que el número total de usuarios aumente.       | Implementada |  Prueba de integración |
|           HU-6          |                    shouldReturnAllCells                    |                           Verifica que todas las celdas sean retornadas correctamente.                           | Implementada |  Prueba de integración |
|           HU-6          |                    shouldReturnCellById                    |                     Verifica que una celda específica sea retornada correctamente por su ID.                     | Implementada |  Prueba de integración |
|           HU-6          |                   shouldUpdateCellStatus                   |                        Verifica que el estado de una celda sea actualizado correctamente.                        | Implementada |  Prueba de integración |
|           HU-6          |                      shouldNotGetCell                      |                   Verifica que se lance una excepción cuando se busca una celda que no existe.                   | Implementada |  Prueba de integración |
|           HU-6          |                    shouldReturnAllCells                    |                         Verifica que el servicio retorne la cantidad correcta de celdas.                         | Implementada |    Prueba unitaria.    |
|        HU-21HU-22       |                     shouldFindAllGames                     |                         Verifica que el servicio retorne todos los juegos correctamente.                         | Implementada |     Prueba unitaria    |
|     No especificada     |                     shouldFindGameById                     |                          Verifica que el servicio retorne un juego específico por su ID.                         | Implementada |     Prueba unitaria    |
|        HU-11HU-12       |                       shouldSaveGame                       |                              Verifica que el servicio guarde correctamente un juego.                             | Implementada |     Prueba unitaria    |
|     No especificada     |                    shouldDeleteGameById                    |                        Verifica que el servicio elimine correctamente un juego por su ID.                        | Implementada |     Prueba unitaria    |
|     HU-21HU-22HU-12     |                   shouldFindGamesByStatus                  |                             Verifica que el servicio retorne los juegos por su estado.                           | Implementada |     Prueba unitaria    |
|        HU-28HU-29       |                    shouldAddPlayerToGame                   |                       Verifica que el servicio agregue un jugador a un juego correctamente.                      | Implementada |     Prueba unitaria    |
|     No especificada     |                 shouldFindGameIdByTableroId                |                      Verifica que el servicio encuentre un ID de juego por el ID de tablero.                     | Implementada |     Prueba unitaria    |
|          HU-29          |               shouldFindGameByCodigoDePartida              |                       Verifica que el servicio encuentre un juego por su código de partida.                      | Implementada |     Prueba unitaria    |
|          HU-29          |    shouldThrowExceptionWhenGameNotFoundByCodigoDePartida   |            Verifica que se lance una excepción cuando no se encuentre un juego por código de partida.            | Implementada |     Prueba unitaria    |
|          HU-29          |                      shouldJoinToGame                      |                  Verifica que el servicio permita a un jugador unirse a un juego correctamente.                  | Implementada |     Prueba unitaria    |
|     HU-21HU-22HU-12     |              shouldFindGamesByPlayerAndStatus              |                     Verifica que el servicio retorne los juegos de un jugador por su estado.                     | Implementada |     Prueba unitaria    |
|          HU-29          |         shouldThrowExceptionWhenPlayerAlreadyInGame        |                      Verifica que se lance una excepción si un jugador ya está en un juego.                      | Implementada |     Prueba unitaria    |
|     No especificada     |                      shouldGetAllHands                     |                               Verifica que se obtienen todas las manos disponibles.                              | Implementada |  Prueba de integración |
|           HU-5          |                shouldGetHandByPlayerUsername               |                 Verifica que se obtiene una mano específica por el nombre de usuario del jugador.                | Implementada |  Prueba de integración |
|           HU-5          |  shouldReturnNotFoundWhenHandByPlayerUsernameDoesNotExist  |             Verifica que se devuelve un error 404 si la mano buscada por nombre de usuario no existe.            | Implementada |  Prueba de integración |
|         HU-6HU-8        |                    shouldUpdateNumTiles                    |                     Verifica que se actualiza correctamente el número de fichas de una mano.                     | Implementada |  Prueba de integración |
|         HU-6HU-8        | shouldReturnNotFoundWhenUpdatingNumTilesForNonExistentHand |                Verifica que se devuelve un error 404 al intentar actualizar una mano inexistente.                | Implementada |  Prueba de integración |
|           HU-4          |                      shouldCreateHand                      |                                Verifica que se crea correctamente una nueva mano.                                | Implementada |  Prueba de integración |
|           HU-6          |                      shouldUpdateHand                      |                  Verifica que se actualiza correctamente una mano existente con nuevos detalles.                 | Implementada |  Prueba de integración |
|           HU-6          |       shouldReturnNotFoundWhenUpdatingNonExistentHand      |                Verifica que se devuelve un error 404 al intentar actualizar una mano inexistente.                | Implementada |  Prueba de integración |
|     No especificado     |                      shouldDeleteHand                      |                             Verifica que se elimina correctamente una mano existente.                            | Implementada |  Prueba de integración |
|     No especificada     |       shouldReturnNotFoundWhenDeletingNonExistentHand      |                 Verifica que se devuelve un error 404 al intentar eliminar una mano inexistente.                 | Implementada |  Prueba de integración |
|           HU-6          |                     shouldAddTileToHand                    |                    Verifica que se puede agregar una ficha a una mano existente correctamente.                   | Implementada |  Prueba de integración |
|           HU-6          |     shouldReturnNotFoundWhenAddingTileToNonExistentHand    |            Verifica que se devuelve un error 404 al intentar agregar una ficha a una mano inexistente            | Implementada |  Prueba de integración |
|           HU-6          |                  shouldRemoveTileFromHand                  |                   Verifica que se puede eliminar una ficha de una mano existente correctamente.                  | Implementada |  Prueba de integración |
|           HU-6          |   shouldReturnNotFoundWhenRemovingTileFromNonExistentHand  |           Verifica que se devuelve un error 404 al intentar eliminar una ficha de una mano inexistente.          | Implementada |  Prueba de integración |
|     No especificada     |                        shouldFindAll                       |            Verifica que se recuperan todas las manos con `findAll` y el repositorio se llama una vez.            | Implementada |     Prueba unitaria    |
|     No especificada     |                       shouldFindById                       |                Verifica que se recupera una mano por ID con `findById` y coincide con la esperada.               | Implementada |     Prueba unitaria    |
|           HU-9          |                       shouldSaveHand                       |                 Comprueba que se guarda correctamente una mano con `save` y se retorna la misma.                 | Implementada |     Prueba unitaria    |
|     No especificada     |                    shouldDeleteHandById                    |            Asegura que se elimina una mano por ID con `deleteById` y el repositorio se llama una vez.            | Implementada |     Prueba unitaria    |
|     No especificada     |                 shouldFindByPlayerUsername                 |                Verifica que se obtiene una mano por nombre de usuario con `findByPlayerUsername`.                | Implementada |     Prueba unitaria    |
|           HU-2          |             shouldNotValidateWhenFirstNameEmpty            |                       Verifica que no se permita un `firstName` vacío en un objeto `Person`                      | Implementada |  Prueba de validación. |
|     No especificada     |                 shouldGetCurrentPlayerTest                 |                       Verifica que se obtenga el jugador actual autenticado correctamente.                       | Implementada |  Prueba de integración |
|          HU-16          |                  shouldGetPlayerByUsername                 |                        Comprueba que se pueda obtener un jugador por su nombre de usuario.                       | Implementada |  Prueba de integración |
|          HU-16          |                   shouldSendFriendRequest                  |                           Verifica que se envíe correctamente una solicitud de amistad.                          | Implementada |  Prueba de integración |
|          HU-27          |                shouldRespondToFriendRequest                |             Valida que se responda correctamente a una solicitud de amistad con un estado específico.            | Implementada |  Prueba de integración |
|          HU-17          |                      shouldGetFriends                      |                      Confirma que se obtenga correctamente la lista de amigos de un jugador.                     | Implementada |  Prueba de integración |
|          HU-27          |                  shouldGetPendingRequests                  |              Comprueba que se obtengan todas las solicitudes de amistad pendientes para un jugador.              | Implementada |  Prueba de integración |
|     No especificada     |                     shouldGetPlayerById                    |                            Verifica que se pueda obtener un jugador utilizando su ID.                            | Implementada |  Prueba de integración |
|           HU-2          |                     shouldCreatePlayer                     |                            Verifica que se pueda crear un nuevo jugador correctamente.                           | Implementada |  Prueba de integración |
|          HU-13          |                     shouldUpdatePlayer                     |                      Confirma que se pueda actualizar la información de un jugador existente.                    | Implementada |  Prueba de integración |
|     No especificada     |                     shouldDeletePlayer                     |                                Valida que se pueda eliminar un jugador por su ID.                                | Implementada |  Prueba de integración |
|          HU-25          |                   shouldGetOnlineFriends                   |                       Verifica que se obtengan correctamente los amigos que están en línea.                      | Implementada |  Prueba de integración |
|          HU-23          |                  shouldGetPaginatedPlayers                 |          Confirma que se puedan obtener jugadores en formato paginado con el total de páginas incluido.          | Implementada |  Prueba de integración |
|          HU-16          |                 shouldFindPlayerByUsername                 |                       Verifica que se pueda encontrar un jugador por su nombre de usuario.                       | Implementada |     Prueba unitaria    |
|          HU-16          |           shouldNotFindPlayerByIncorrectUsername           |                     Asegura que se lance una excepción cuando el nombre de usuario no existe.                    | Implementada |     Prueba unitaria    |
|          HU-17          |                      shouldGetFriends                      |                             Confirma que se puedan obtener los amigos de un jugador.                             | Implementada |     Prueba unitaria    |
|          HU-16          |            shouldThrowExceptionIfPlayerNotFound            |                         Asegura que se lance una excepción si no se encuentra un jugador.                        | Implementada |     Prueba unitaria    |
|          HU-16          |   shouldThrowExceptionWhenSendingRequestToNonExistentUser  |          Verifica que se lance una excepción al intentar enviar una solicitud a un usuario inexistente.          | Implementada |     Prueba unitaria    |
|          HU-27          |   shouldThrowExceptionWhenRespondingToNonExistentRequest   |                Verifica que se lance una excepción cuando se responde a una solicitud inexistente.               | Implementada |     Prueba unitaria    |
|          HU-27          | shouldThrowExceptionWhenRespondingToAlreadyAcceptedRequest |               Asegura que se lance una excepción al intentar responder a una solicitud ya aceptada.              | Implementada |     Prueba unitaria    |
|          HU-27          |        shouldThrowExceptionForDuplicateFriendRequest       |             Asegura que se lance una excepción al intentar enviar una solicitud de amistad duplicada.            | Implementada |     Prueba unitaria    |
|     No especificada     |             shouldReturnPlayerDTOListOfFriends             |             Verifica que se devuelvan correctamente los amigos de un jugador como una lista de DTOs.             | Implementada |     Prueba unitaria    |
|     No especificada     |                  shouldGetCurrentUsername                  |                      Verifica que se pueda obtener el nombre de usuario del jugador actual.                      | Implementada |     Prueba unitaria    |
|          HU-24          |                    shouldFindAllPlayers                    |                            Asegura que se devuelvan todos los jugadores correctamente.                           | Implementada |     Prueba unitaria    |
|           HU-2          |                      shouldSavePlayer                      |                              Verifica que un jugador se pueda guardar correctamente.                             | Implementada |  Prueba de integración |
|      No especifcada     |                     shouldUpdatePlayer                     |                              Valida que se pueda actualizar un jugador correctamente.                            | Implementada |  Prueba de integración |
|          HU-23          |                 shouldFindPlayersPaginated                 |                       Verifica que se devuelvan jugadores de manera paginada correctamente.                      | Implementada |     Prueba unitaria    |
|          Hu-23          |                     shouldGetTotalPages                    |                         Asegura que se obtenga el número total de páginas correctamente.                         | Implementada |     Prueba unitaria    |
|     No especificada     |                shouldFindAllPendingRequests                |                    Valida que se obtengan correctamente las solicitudes de amistad pendientes.                   | Implementada |  Prueba de integración |
|     No especificada     |                      shouldFindPlayer                      |                       Verifica que se pueda encontrar un jugador por su nombre de usuario.                       | Implementada |     Prueba unitaria    |
|          HU-31          |                  shouldFindAllAchievements                 |                                    Verifica que se obtengan todos los logros.                                    | Implementada | Prueba de integración. |
|     No especificada     |                  shouldFindAchievementById                 |                                 Verifica que se pueda obtener un logro por su ID.                                | Implementada | Prueba de integración. |
|     No especificada     |              shouldFindAchievementsByUsername              |                    Verifica que se obtengan los logros de un jugador por su nombre de usuario.                   | Implementada | Prueba de integración. |
|          HU-30          |                   shouldClaimAchievement                   |                                 Verifica que un jugador pueda reclamar un logro.                                 | Implementada | Prueba de integración. |
|          HU-31          |                   shouldCreateAchievement                  |                                    Verifica que se pueda crear un nuevo logro.                                   | Implementada | Prueba de integración. |
|          HU-31          |                 shouldDeleteAchievementById                |                                             Elimina un logro por ID.                                             | Implementada |  Prueba de integración |
|          HU-31          |     shouldReturnNotFoundWhenAchievementByIdDoesNotExist    |                             Retorna "Not Found" cuando el logro no existe por su ID.                             | Implementada |  Prueba de integración |
|          HU-30          |   shouldReturnNotFoundWhenClaimingNonExistentAchievement   |                         Retorna "Not Found" al intentar reclamar un logro que no existe.                         | Implementada |  Prueba de integración |
|          HU-31          |                    shouldSaveAchievement                   |                                                 Guarda un logro.                                                 | Implementada |     Prueba unitaria    |
|          HU-11          |              shouldCheckAndUnlockAchievements              |                         Verifica y desbloquea logros según las estadísticas del jugador.                         | Implementada |     Prueba unitaria    |
|     No especificada     |                 shouldFindAchievementByName                |                                         Encuentra un logro por su nombre.                                        | Implementada |     Prueba unitaria    |
|     No especificada     |              shouldFindAchievementsByUsername              |                                    Encuentra logros por el nombre de usuario.                                    | Implementada |     Prueba unitaria    |
|     No especificada     |           shouldFindSpecificAchievementByUsername          |                              Encuentra un logro específico por el nombre de usuario.                             | Implementada |     Prueba unitaria    |
|          HU-26          |                     shouldFindAllStats                     |                                         Encuentra todas las estadísticas.                                        | Implementada |     Prueba unitaria    |
|          HU-13          |                  shouldFindStatsByUsername                 |                                 Encuentra las estadísticas por nombre de usuario.                                | Implementada |     Prueba unitaria    |
|     No especificada     |           shouldThrowExceptionWhenPlayerNotFound           |                              Lanza una excepción cuando no se encuentra el jugador.                              | Implementada |     Prueba unitaria    |
|          HU-11          |                       shouldSaveStats                      |                                             Guarda las estadísticas.                                             | Implementada |     Prueba unitaria    |
|        HU-11HU-13       |                      shouldUpdateStats                     |                                     Actualiza las estadísticas de un jugador.                                    | Implementada |     Prueba unitaria    |
|          HU-11          |            shouldGetPlayersOrderedByTotalPoints            |                                Obtiene los jugadores ordenados por puntos totales.                               | Implementada |     Prueba unitaria    |
|     No especificada     |                    shouldReturnAllTiles                    |                                             Devuelve todos los tiles.                                            | Implementada |  Prueba de integración |
|     No especificada     |            shouldReturnEmptyListWhenNoTilesExist           |                                   Devuelve una lista vacía cuando no hay tiles.                                  | Implementada |  Prueba de integración |
|     No especificada     |                      shouldGetAllTiles                     |                                   Devuelve todos los tiles desde el repositorio.                                 | Implementada |     Prueba unitaria    |
|     No especificada     |                      shouldCreateTile                      |                                    Crea un tile y lo guarda en el repositorio.                                   | Implementada |     Prueba unitaria    |
|     No especificada     |                  shouldReturnListOfColors                  |                                          Devuelve una lista de colores.                                          | Implementada |     Prueba unitaria    |
|           HU-4          |                      shouldCreateBolsa                     |                                             Crea una bolsa de tiles.                                             | Implementada |     Prueba unitaria    |
|           HU-4          |                   shouldInitializeTablero                  |                                              shouldInitializeTablero                                             | Implementada |     Prueba unitaria    |
|           HU-6          |                   shouldPlaceInitialTiles                  |                                    Coloca las fichas iniciales en el tablero.                                    | Implementada |     Prueba unitaria    |
|     No especificada     |                  shouldFindAllAuthorities                  |                        Verifica que se recuperen todas las autoridades desde el servicio.                        | Implementada |     Prueba unitaria    |
|     No especificada     |              shouldFindAuthoritiesByAuthority              |                       Verifica que se recupere una autoridad por su nombre (como "ADMIN").                       | Implementada |     Prueba unitaria    |
|     No especificada     |        shouldNotFindAuthoritiesByIncorrectAuthority        |                Verifica que se lance una excepción cuando no se encuentre la autoridad solicitada.               | Implementada |     Prueba unitaria    |
|     No especificada     |                   shouldInsertAuthorities                  |       Verifica que se inserte correctamente una nueva autoridad y que se actualice la lista de autoridades.      | Implementada |  Prueba de integración |
|     No especificada     |                 shouldFindAllWithAuthority                 |                        Verifica que se obtienen los usuarios con una autoridad específica.                       | Implementada |  Prueba de integración |
|     No especificada     |                     shouldFindAllAuths                     |                                  Verifica que se obtienen todas las autoridades.                                 | Implementada |  Prueba de integración |
|     No especificada     |                      shouldReturnUser                      |                                    Verifica que se obtiene un usuario por ID.                                    | Implementada |  Prueba de integración |
|     No especificada     |                  shouldReturnNotFoundUser                  |                   Verifica que se maneja correctamente el error si no se encuentra un usuario.                   | Implementada |  Prueba de integración |
|           HU-2          |                      shouldCreateUser                      |                                      Verifica que se puede crear un usuario.                                     | Implementada |  Prueba de integración |
|          HU-13          |                      shouldUpdateUser                      |                                    Verifica que se puede actualizar un usuario.                                  | Implementada |  Prueba de integración |
|     No especificada     |               shouldReturnNotFoundUpdateUser               |              Verifica que se retorna un error 404 cuando no se encuentra el usuario para actualizar.             | Implementada |  Prueba de integración |
|     No especificada     |                    shouldDeleteOtherUser                   |                          Verifica que se puede eliminar un usuario distinto al logueado.                         | Implementada |  Prueba de integración |
|     No especificada     |                  shouldNotDeleteLoggedUser                 |                        Verifica que no se puede eliminar el usuario actualmente logueado.                        | Implementada |  Prueba de integración |
|     No especificada     |                    shouldFindCurrentUser                   |                        Verifica que se pueda encontrar al usuario actualmente autenticado.                       | Implementada |     Prueba unitaria    |
|     No especificada     |               shouldNotFindCorrectCurrentUser              |             Verifica que se lance un error cuando el usuario autenticado no coincide con el esperado.            | Implementada |     Prueba unitaria    |
|     No especificada     |                 shouldNotFindAuthenticated                 |                           Verifica que se lance un error si no hay usuario autenticado.                          | Implementada |     Prueba unitaria    |
|          HU-23          |                     shouldFindAllUsers                     |                                Verifica que se puedan obtener todos los usuarios.                                | Implementada |     Prueba unitaria    |
|          HU-17          |                  shouldFindUsersByUsername                 |                       Verifica que se pueda encontrar un usuario por su nombre de usuario.                       | Implementada |     Prueba unitaria    |
|     No especificada     |                 shouldFindUsersByAuthority                 |                        Verifica que se puedan obtener usuarios filtrados por su autoridad.                       | Implementada |     Prueba unitaria    |
|          HU-17          |            shouldNotFindUserByIncorrectUsername            |             Verifica que se lance una excepción cuando se busca un usuario con un nombre incorrecto.             | Implementada |     Prueba unitaria    |
|     No especificada     |                    shouldFindSingleUser                    |                               Verifica que se pueda encontrar un usuario por su ID.                              | Implementada |     Prueba unitaria    |
|     No especificada     |              shouldNotFindSingleUserWithBadID              |               Verifica que se lance una excepción cuando se busca un usuario con un ID incorrecto.               | Implementada |     Prueba unitaria    |
|     No especificada     |                       shouldExistUser                      |                                   Verifica que un usuario exista en el sistema.                                  | Implementada |     Prueba unitaria    |
|     No especificada     |                     shouldNotExistUser                     |                                 Verifica que un usuario no exista en el sistema.                                 | Implementada |     Prueba unitaria    |
|           HU-2          |                      shouldInsertUser                      |                          Verifica que se pueda insertar un nuevo usuario correctamente.                          | Implementada |     Prueba unitaria    |
|     No especificada     |                      shouldGetGameById                     |                                 Verifica que se pueda obtener un juego por su ID.                                | Implementada |  Prueba de integración |
|     No especificada     |          shouldReturnNotFoundWhenGameDoesNotExist          |                         Verifica que se devuelva un error 404 cuando el juego no existe.                         | Implementada |  Prueba de integración |
|          HU-12          |                  shouldGetPlayersByGameId                  |                        Verifica que se puedan obtener los jugadores de un juego por su ID.                       | Implementada |  Prueba de integración |
|        HU-21HU-22       |                      shouldGetAllGames                     |                              Verifica que se devuelvan todos los juegos disponibles.                             | Implementada |  Prueba de integración |
|          HU-29          |                       shouldJoinGame                       |                                 Verifica que un jugador se pueda unir a un juego.                                | Implementada |  Prueba de integración |
|          HU-28          |                     shouldCreateNewGame                    |                             Verifica que se pueda crear un nuevo juego correctamente.                            | Implementada |  Prueba de integración |
|          HU-19          |                        shouldEndGame                       |                                   Verifica que un juego termine correctamente.                                   | Implementada |  Prueba de integración |
|     No especificada     |                      shouldDeleteGame                      |                                Verifica que un juego sea eliminado correctamente.                                | Implementada |  Prueba de integración |
|     No especificada     |                    shouldAddPlayerToGame                   |                          Verifica que un jugador sea agregado correctamente a un juego.                          | Implementada |  Prueba de integración |
|     No especificada     |                 shouldGetGameIdByTableroId                 |                  Verifica que se pueda obtener el ID de un juego a partir del ID de su tablero.                  | Implementada |  Prueba de integración |
|          HU-19          |                   shouldUpdateGameStatus                   |                       Verifica que se pueda actualizar correctamente el estado de un juego.                      | Implementada |  Prueba de integración |
|          HU-29          |               shouldGetGameByCodigoDePartida               |                        Verifica que se pueda obtener un juego usando su código de partida.                       | Implementada |  Prueba de integración |
|          HU-29          |               shouldJoinGameByCodigoDePartida              |                   Verifica que un jugador se pueda unir a un juego usando su código de partida.                  | Implementada |  Prueba de integración |
|     No especificada     |               shouldGetGamesByPlayerAndStatus              |                   Verifica que se devuelvan los juegos de un jugador con un estado específico.                   | Implementada |  Prueba de integración |
|     HU-22HU-21HU-12     |                  shouldGetAllGamesByStatus                 |               Verifica que se devuelvan todos los juegos con un estado específico, como "PLAYING".               | Implementada |  Prueba de integración |
|        HU-28HU-4        |                       shouldStartGame                      |                                Verifica que un juego pueda comenzar correctamente.                               | Implementada |  Prueba de integración |
|     No especificada     |                 shouldCheckIfGameIsPlaying                 |                            Verifica que se pueda comprobar si un juego está en curso.                            | Implementada |  Prueba de integración |
|     No especificada     |          shouldNotAddPlayerToGameWhenGameNotFound          |      Verifica que se maneje correctamente el caso cuando un juego no existe al intentar agregar un jugador.      | Implementada |  Prueba de integración |
|     No especificada     |          shouldNotGetGameIdByTableroIdWhenNotFound         | Verifica que se maneje correctamente el caso cuando no se encuentra un juego con el ID de tablero proporcionado. | Implementada |  Prueba de integración |
|     No especificada     |          shouldNotUpdateGameStatusWhenGameNotFound         |  Verifica que se maneje correctamente el caso cuando no se encuentra un juego al intentar actualizar su estado.  | Implementada |  Prueba de integración |
|     No especificada     |          shouldNotCreateNewGameWhenInvalidGameMode         |                      Verifica que no se pueda crear un juego con un modo de juego inválido.                      | Implementada |  Prueba de integración |
|     No especificada     |          shouldNotCreateNewGameWhenPlayerNotFound          |                    Verifica que no se pueda crear un juego cuando el jugador no se encuentra.                    | Implementada |  Prueba de integración |





### 5.3 Matriz de Trazabilidad entre Pruebas e Historias de Usuario

| Prueba                                    | HU-1 | HU-11 | HU-12 | HU-13 | HU-16 | HU-17 | HU-19 | HU-2 | HU-21 | HU-22 | HU-23 | HU-24 | HU-25 | HU-26 | HU-27 | HU-28 | HU-29 | HU-30 | HU-31 | HU-4 | HU-5 | HU-6 | HU-8 | HU-9 |
|-------------------------------------------|------|-------|-------|-------|-------|-------|-------|------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|------|------|------|------|------|
| shouldAuthenticateUser                   |   X  |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldValidateToken                      |   X  |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldNotValidateToken                   |   X  |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldRegisterUser                       |      |       |       |       |       |       |       |  X   |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldNotRegisterUserWithExistingUsername|      |       |       |       |       |       |       |  X   |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldCreatePlayerUser                   |      |       |       |       |       |       |       |  X   |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldReturnAllCells                     |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |  X   |      |      |
| shouldReturnCellById                     |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |  X   |      |      |
| shouldUpdateCellStatus                   |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |  X   |      |      |
| shouldNotGetCell                         |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |  X   |      |      |
| shouldFindAllGames                       |      |       |       |       |       |       |       |      |   X   |   X   |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldSaveGame                           |      |   X   |   X   |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldFindGamesByStatus                  |      |   X   |   X   |       |       |       |       |      |   X   |   X   |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldAddPlayerToGame                    |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |   X   |   X   |       |       |      |      |      |      |      |
| shouldFindGameByCodigoDePartida          |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |   X   |       |       |      |      |      |      |      |
| shouldThrowExceptionWhenGameNotFoundByCodigoDePartida|      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |   X   |       |       |      |      |      |      |      |
| shouldJoinToGame                         |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |   X   |       |       |      |      |      |      |      |
| shouldFindGamesByPlayerAndStatus         |      |   X   |   X   |       |       |       |       |      |   X   |   X   |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldThrowExceptionWhenPlayerAlreadyInGame|    |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |   X   |       |       |      |      |      |      |      |
| shouldGetHandByPlayerUsername            |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |   X  |      |      |      |
| shouldReturnNotFoundWhenHandByPlayerUsernameDoesNotExist| |     |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |   X  |      |      |      |
| shouldUpdateNumTiles                     |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |  X   |  X   |      |
| shouldReturnNotFoundWhenUpdatingNumTilesForNonExistentHand| |   |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |  X   |  X   |      |
| shouldCreateHand                         |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |  X   |      |      |      |      |
| shouldUpdateHand                         |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |  X   |      |      |
| shouldReturnNotFoundWhenUpdatingNonExistentHand|       |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |  X   |      |      |
| shouldAddTileToHand                      |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |  X   |      |      |
| shouldReturnNotFoundWhenAddingTileToNonExistentHand|   |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |  X   |      |      |
| shouldRemoveTileFromHand                 |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |  X   |      |      |
| shouldReturnNotFoundWhenRemovingTileFromNonExistentHand| |     |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |  X   |      |      |
| shouldSaveHand                           |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |  X   |
| shouldNotValidateWhenFirstNameEmpty       |      |       |       |       |       |       |       |  X   |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldGetPlayerByUsername                 |      |       |       |       |   X   |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldSendFriendRequest                   |      |       |       |       |   X   |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldRespondToFriendRequest              |      |       |       |       |       |       |       |      |       |       |       |       |       |       |   X   |       |       |       |       |      |      |      |      |      |
| shouldGetFriends                          |      |       |       |       |       |   X   |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldGetPendingRequests                  |      |       |       |       |       |       |       |      |       |       |       |       |       |       |   X   |       |       |       |       |      |      |      |      |      |
| shouldCreatePlayer                        |      |       |       |       |       |       |       |  X   |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldUpdatePlayer                        |      |       |       |   X   |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldGetOnlineFriends                    |      |       |       |       |       |       |       |      |       |       |       |       |   X   |       |       |       |       |       |       |      |      |      |      |      |
| shouldGetPaginatedPlayers                 |      |       |       |       |       |       |       |      |       |       |   X   |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldFindPlayerByUsername                |      |       |       |       |   X   |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldNotFindPlayerByIncorrectUsername    |      |       |       |       |   X   |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldThrowExceptionIfPlayerNotFound      |      |       |       |       |   X   |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldThrowExceptionWhenSendingRequestToNonExistentUser| |       |       |       |   X   |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |
| shouldThrowExceptionWhenRespondingToNonExistentRequest| |       |       |       |       |       |       |      |       |       |       |       |       |       |   X   |       |       |       |       |      |      |      |      |
| shouldThrowExceptionWhenRespondingToAlreadyAcceptedRequest| |   |       |       |       |       |       |      |       |       |       |       |       |       |   X   |       |       |       |       |      |      |      |      |
| shouldThrowExceptionForDuplicateFriendRequest| |       |       |       |       |       |       |      |       |       |       |       |       |       |   X   |       |       |       |       |      |      |      |      |
| shouldFindAllPlayers                      |      |       |       |       |       |       |       |      |       |       |       |   X   |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldSavePlayer                          |      |       |       |       |       |       |       |  X   |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldFindPlayersPaginated                |      |       |       |       |       |       |       |      |       |       |   X   |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldGetTotalPages                       |      |       |       |       |       |       |       |      |       |       |   X   |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldFindAllAchievements                 |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |   X   |      |      |      |      |      |
| shouldClaimAchievement                    |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |   X   |       |      |      |      |      |      |
| shouldCreateAchievement                   |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |   X   |      |      |      |      |      |
| shouldDeleteAchievementById               |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |   X   |      |      |      |      |      |
| shouldReturnNotFoundWhenAchievementByIdDoesNotExist| |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |   X   |      |      |      |      |
| shouldReturnNotFoundWhenClaimingNonExistentAchievement| |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |   X   |       |      |      |      |      |
| shouldSaveAchievement                     |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |   X   |      |      |      |      |      |
| shouldCheckAndUnlockAchievements          |      |   X   |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldFindAllStats                        |      |       |       |       |       |       |       |      |       |       |       |       |       |   X   |       |       |       |       |       |      |      |      |      |      |
| shouldFindStatsByUsername                 |      |       |       |   X   |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldSaveStats                           |      |   X   |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldUpdateStats                         |      |   X   |       |   X   |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldGetPlayersOrderedByTotalPoints      |      |   X   |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldCreateBolsa                         |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |   X  |      |      |      |      |
| shouldInitializeTablero                   |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |   X  |      |      |      |      |
| shouldPlaceInitialTiles                   |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |  X   |      |      |
| shouldCreateUser                          |      |       |       |       |       |       |       |  X   |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldUpdateUser                          |      |       |       |   X   |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldFindAllUsers                        |      |       |       |       |       |       |       |      |       |       |   X   |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldFindUsersByUsername                 |      |       |       |       |       |   X   |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldNotFindUserByIncorrectUsername      |      |       |       |       |       |   X   |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldInsertUser                          |      |       |       |       |       |       |       |  X   |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldGetPlayersByGameId                  |      |       |   X   |       |       |       |       |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldGetAllGames                         |      |       |       |       |       |       |       |      |   X   |   X   |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldJoinGame                            |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |   X   |       |       |      |      |      |      |      |
| shouldCreateNewGame                       |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |   X   |       |       |       |      |      |      |      |      |
| shouldEndGame                             |      |       |       |       |       |       |   X   |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldUpdateGameStatus                    |      |       |       |       |       |       |   X   |      |       |       |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldGetGameByCodigoDePartida            |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |   X   |       |       |      |      |      |      |      |
| shouldJoinGameByCodigoDePartida           |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |       |   X   |       |       |      |      |      |      |      |
| shouldGetAllGamesByStatus                 |      |       |   X   |       |       |       |       |      |   X   |   X   |       |       |       |       |       |       |       |       |       |      |      |      |      |      |
| shouldStartGame                           |      |       |       |       |       |       |       |      |       |       |       |       |       |       |       |   X   |       |       |       |   X  |      |      |      |      |

## 6. Criterios de Aceptación

- Todas las pruebas unitarias deben pasar con éxito antes de la entrega final del proyecto.
- La cobertura de código debe ser al menos del 70%.
- No debe haber fallos críticos en las pruebas de integración y en la funcionalidad.

## 7. Conclusión

Este plan de pruebas establece la estructura y los criterios para asegurar la calidad del software desarrollado. Es responsabilidad del equipo de desarrollo y pruebas seguir este plan para garantizar la entrega de un producto funcional y libre de errores.
