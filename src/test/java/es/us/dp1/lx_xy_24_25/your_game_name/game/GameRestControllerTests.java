package es.us.dp1.lx_xy_24_25.your_game_name.game;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.us.dp1.lx_xy_24_25.your_game_name.board.Board;
import es.us.dp1.lx_xy_24_25.your_game_name.board.BoardService;
import es.us.dp1.lx_xy_24_25.your_game_name.exceptions.ResourceNotFoundException;
import es.us.dp1.lx_xy_24_25.your_game_name.hand.HandService;
import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;
import es.us.dp1.lx_xy_24_25.your_game_name.player.PlayerRepository;
import es.us.dp1.lx_xy_24_25.your_game_name.player.PlayerService;
import es.us.dp1.lx_xy_24_25.your_game_name.user.User;
import es.us.dp1.lx_xy_24_25.your_game_name.tile.TileService;
import es.us.dp1.lx_xy_24_25.your_game_name.user.UserService;

@WebMvcTest(GameRestController.class)
public class GameRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gs;

    @MockBean
    private PlayerRepository pr;

    @MockBean
    private UserService userService;

    @MockBean
    private PlayerService ps;

    @MockBean
    private BoardService bs;

    @MockBean
    private HandService hs;

    @MockBean
    private TileService ts;

    @InjectMocks
    private GameRestController gameController;

    private Game game;
    private Player player;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();

        player = new Player();
        player.setUsername("guilinbor");

        game = new Game();
        game.setId(1);
        game.setCreador(player);
        game.setGamemode(GameModes.SURVIVAL);
        game.setGameStatus(GameStatus.PLAYING);
        game.setStart(LocalDateTime.now());
        game.setJugadores(Collections.singletonList(player));
    }

    @Test
    void shouldGetGameById() throws Exception {
        when(gs.getGameById(1)).thenReturn(Optional.of(game));

        mockMvc.perform(get("/api/v1/game/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturnNotFoundWhenGameDoesNotExist() throws Exception {
        when(gs.getGameById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/game/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetPlayersByGameId() throws Exception {
        when(gs.getGameById(1)).thenReturn(Optional.of(game));

        mockMvc.perform(get("/api/v1/game/1/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("guilinbor"));
    }

    @Test
    void shouldGetAllGames() throws Exception {
        when(gs.getAllGames()).thenReturn(Collections.singletonList(game));

        mockMvc.perform(get("/api/v1/game"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldJoinGame() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        when(userService.findCurrentUser()).thenReturn(user);
        when(gs.joinGame("1234", "testuser")).thenReturn(game);

        mockMvc.perform(patch("/api/v1/game/1234/join"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldCreateNewGame() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("gameMode", "SURVIVAL");

        User user = new User();
        user.setUsername("guilinbor");
        when(userService.findCurrentUser()).thenReturn(user);
        when(ps.findPlayer("guilinbor")).thenReturn(player);
        when(gs.save(any(Game.class))).thenReturn(game);
        when(bs.createBoard(any(Board.class))).thenReturn(new Board());

        mockMvc.perform(post("/api/v1/game/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.gameId").value(1));
    }

    @Test
    void shouldEndGame() throws Exception {
        when(gs.getGameById(1)).thenReturn(Optional.of(game));
        when(gs.save(any(Game.class))).thenReturn(game);

        mockMvc.perform(patch("/api/v1/game/1/endGame"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteGame() throws Exception {
        when(gs.getGameById(1)).thenReturn(Optional.of(game));

        mockMvc.perform(delete("/api/v1/game/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldAddPlayerToGame() throws Exception {
        when(gs.addPlayerToGame(1, 1)).thenReturn(game);

        mockMvc.perform(put("/api/v1/game/1/addPlayer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldGetGameIdByTableroId() throws Exception {
        when(gs.findGameIdByTableroId(1)).thenReturn(1);

        mockMvc.perform(get("/api/v1/game/getGameId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
    }

    @Test
    void shouldUpdateGameStatus() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("gameStatus", "FINISHED");

        when(gs.getGameById(1)).thenReturn(Optional.of(game));
        when(gs.save(any(Game.class))).thenReturn(game);

        mockMvc.perform(put("/api/v1/game/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameStatus").value("FINISHED"));
    }

    @Test
    void shouldGetGameByCodigoDePartida() throws Exception {
        when(gs.findGameByCodigoDePartida(1234)).thenReturn(game);

        mockMvc.perform(get("/api/v1/game/code/1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldJoinGameByCodigoDePartida() throws Exception {
        when(gs.joinToGame(1234, "testuser")).thenReturn(game);

        mockMvc.perform(put("/api/v1/game/code/1234/join/testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldGetGamesByPlayerAndStatus() throws Exception {
        when(gs.getGamesByPlayerAndStatus("testuser", GameStatus.PLAYING)).thenReturn(Collections.singletonList(game));

        mockMvc.perform(get("/api/v1/game/player/testuser/PLAYING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldGetAllGamesByStatus() throws Exception {
        when(gs.getGamesByStatus(GameStatus.PLAYING)).thenReturn(Collections.singletonList(game));

        mockMvc.perform(get("/api/v1/game/status/PLAYING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldStartGame() throws Exception {
        when(gs.getGameById(1)).thenReturn(Optional.of(game));
        when(gs.save(any(Game.class))).thenReturn(game);

        mockMvc.perform(patch("/api/v1/game/1/startGame"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldCheckIfGameIsPlaying() throws Exception {
        when(gs.getGameById(1)).thenReturn(Optional.of(game));

        mockMvc.perform(get("/api/v1/game/1/checkPlaying"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void shouldNotAddPlayerToGameWhenGameNotFound() throws Exception {
        when(gs.addPlayerToGame(1, 1)).thenThrow(new ResourceNotFoundException("Game", "id", 1));

        mockMvc.perform(put("/api/v1/game/1/addPlayer/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldNotGetGameIdByTableroIdWhenNotFound() throws Exception {
        when(gs.findGameIdByTableroId(1)).thenReturn(null);

        mockMvc.perform(get("/api/v1/game/getGameId/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldNotUpdateGameStatusWhenGameNotFound() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("gameStatus", "FINISHED");

        when(gs.getGameById(1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/game/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldNotCreateNewGameWhenInvalidGameMode() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("gameMode", "INVALID_MODE");

        mockMvc.perform(post("/api/v1/game/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldNotCreateNewGameWhenPlayerNotFound() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("gameMode", "SURVIVAL");

        User user = new User();
        user.setUsername("guilinbor");
        when(userService.findCurrentUser()).thenReturn(user);
        when(ps.findPlayer("guilinbor")).thenThrow(new ResourceNotFoundException("Player", "username", "guilinbor"));

        mockMvc.perform(post("/api/v1/game/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(status().isNotFound());
    }


}