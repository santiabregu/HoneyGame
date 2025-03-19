package es.us.dp1.lx_xy_24_25.your_game_name.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import es.us.dp1.lx_xy_24_25.your_game_name.exceptions.ResourceNotFoundException;
import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;
import es.us.dp1.lx_xy_24_25.your_game_name.player.PlayerRepository;

@SpringBootTest
public class GameServiceTest {

    @Autowired
    private GameService gameService;

    @MockBean
    private GameRepository gameRepository;

    @MockBean
    private PlayerRepository playerRepository;

    private Game game;
    private Player player;

    @BeforeEach
    void setup() {
        player = new Player();
        player.setId(1);
        player.setUsername("player1");

        game = new Game();
        game.setId(1);
        game.setCodigoDePartida(12345);
        game.setGameStatus(GameStatus.PLAYING);
        game.setJugadores(new ArrayList<>()); // Initialize the jugadores list

    }

    @Test
    void shouldFindAllGames() {
        Game game2 = new Game();
        game2.setId(2);
        game2.setCodigoDePartida(67890);
        game2.setGameStatus(GameStatus.PLAYING);

        when(this.gameRepository.findAll()).thenReturn(List.of(game, game2));

        List<Game> games = this.gameService.getAllGames();
        assertEquals(2, games.size());
    }

    @Test
    void shouldFindGameById() {
        when(this.gameRepository.findById(1)).thenReturn(Optional.of(game));

        Optional<Game> foundGame = this.gameService.getGameById(1);
        assertEquals(game.getCodigoDePartida(), foundGame.get().getCodigoDePartida());
    }

    @Test
    void shouldSaveGame() {
        when(this.gameRepository.save(any(Game.class))).thenReturn(game);

        Game savedGame = this.gameService.save(game);
        assertEquals(game.getCodigoDePartida(), savedGame.getCodigoDePartida());
    }

    @Test
    void shouldDeleteGameById() {
        doNothing().when(this.gameRepository).deleteById(1);

        this.gameService.delete(1);
        Mockito.verify(this.gameRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    void shouldFindGamesByStatus() {
        when(this.gameRepository.findByGameStatus(GameStatus.PLAYING)).thenReturn(List.of(game));

        List<Game> games = this.gameService.getGamesByStatus(GameStatus.PLAYING);
        assertEquals(1, games.size());
    }

    @Test
    void shouldAddPlayerToGame() {
        when(this.gameRepository.findById(1)).thenReturn(Optional.of(game));
        when(this.playerRepository.findById(1)).thenReturn(Optional.of(player));
        when(this.gameRepository.save(any(Game.class))).thenReturn(game);
        when(this.playerRepository.save(any(Player.class))).thenReturn(player);
    
        Game updatedGame = this.gameService.addPlayerToGame(1, 1);
        assertEquals(1, updatedGame.getJugadores().size());
    }

    @Test
    void shouldFindGameIdByTableroId() {
        when(this.gameRepository.findByTableroId(1)).thenReturn(game);

        Integer gameId = this.gameService.findGameIdByTableroId(1);
        assertEquals(game.getId(), gameId);
    }

    @Test
    void shouldFindGameByCodigoDePartida() {
        when(this.gameRepository.findGameByCodigoDePartida(12345)).thenReturn(game);

        Game foundGame = this.gameService.findGameByCodigoDePartida(12345);
        assertEquals(game.getCodigoDePartida(), foundGame.getCodigoDePartida());
    }

    @Test
    void shouldThrowExceptionWhenGameNotFoundByCodigoDePartida() {
        when(this.gameRepository.findGameByCodigoDePartida(12345)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            this.gameService.findGameByCodigoDePartida(12345);
        });
    }

    @Test
    void shouldJoinToGame() {
        when(this.gameRepository.findGameByCodigoDePartida(12345)).thenReturn(game);
        when(this.playerRepository.findByUsername("player1")).thenReturn(Optional.of(player));
        when(this.gameRepository.save(any(Game.class))).thenReturn(game);
        when(this.playerRepository.save(any(Player.class))).thenReturn(player);

        Game updatedGame = this.gameService.joinToGame(12345, "player1");
        assertEquals(1, updatedGame.getJugadores().size());
    }

    @Test
    void shouldFindGamesByPlayerAndStatus() {
        when(this.gameRepository.findGamesByUsernameAndStatus("player1", GameStatus.PLAYING)).thenReturn(List.of(game));

        List<Game> games = this.gameService.getGamesByPlayerAndStatus("player1", GameStatus.PLAYING);
        assertEquals(1, games.size());
    }

    @Test
    void shouldJoinGame() throws Exception {
        when(this.gameRepository.findGameByCodigoDePartida("12345")).thenReturn(game);
        when(this.playerRepository.findByUsername("player1")).thenReturn(Optional.of(player));
        when(this.gameRepository.save(any(Game.class))).thenReturn(game);

        Game updatedGame = this.gameService.joinGame("12345", "player1");
        assertEquals(1, updatedGame.getJugadores().size());
    }

    @Test
    void shouldThrowExceptionWhenPlayerAlreadyInGame() {
        game.getJugadores().add(player);
        when(this.gameRepository.findGameByCodigoDePartida("12345")).thenReturn(game);
        when(this.playerRepository.findByUsername("player1")).thenReturn(Optional.of(player));

        assertThrows(Exception.class, () -> {
            this.gameService.joinGame("12345", "player1");
        });
    }
}