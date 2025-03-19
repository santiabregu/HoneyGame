package es.us.dp1.lx_xy_24_25.your_game_name.game;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer>{

    List<Game> findAll(); 

    @Query("SELECT g FROM Game g WHERE g.id = ?1")
    Optional<Game> findById(Integer id);

    @Query("SELECT g FROM Game g JOIN g.jugadores p WHERE p.id = ?1 AND g.gameStatus = FINISHED")
    List<Game> findGamesByPlayerId(Integer playerId);

    @Query("SELECT g FROM Game g WHERE g.gameStatus = WAITING")
    List<Game> findWaitingGames();

    @Query("SELECT g FROM Game g WHERE g.gameStatus = PLAYING")
    List<Game> findPlayingGames();

    @Query("SELECT g FROM Game g WHERE g.gameStatus = FINISHED")
    List<Game> findFinishedGames();

    @Query("SELECT g FROM Game g WHERE g.gamemode = SOLO")
    List<Game> findSoloGames();

    @Query("SELECT g FROM Game g WHERE g.gamemode = SURVIVAL")
    List<Game> findSurvivalGames();

    @Query("SELECT g FROM Game g WHERE g.gamemode = COMPETITIVE")
    List<Game> findMultiplayerGames();

    @Query("SELECT g FROM Game g WHERE g.tablero.id = ?1")
    Game findByTableroId(Integer tableroId);

    @Query("SELECT g FROM Game g WHERE g.gameStatus = ?1")
    List<Game> findByGameStatus(GameStatus gameStatus);

    @Query("SELECT g FROM Game g WHERE g.codigoDePartida = ?1 AND g.gameStatus = WAITING")
    Game findGameByCodigoDePartida(Integer codigoDePartida);

    @Query("SELECT g FROM Game g JOIN g.jugadores p WHERE p.username = ?1 AND g.gameStatus = ?2")
    List<Game> findGamesByUsernameAndStatus(String username, GameStatus status);

    @Query("SELECT g FROM Game g JOIN g.jugadores p WHERE g.codigoDePartida = ?1")
    Game findGameByCodigoDePartida(String codigoDePartida);
}
