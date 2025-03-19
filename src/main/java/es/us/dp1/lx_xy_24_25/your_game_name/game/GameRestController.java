package es.us.dp1.lx_xy_24_25.your_game_name.game;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.us.dp1.lx_xy_24_25.your_game_name.bag.Bag;
import es.us.dp1.lx_xy_24_25.your_game_name.board.Board;
import es.us.dp1.lx_xy_24_25.your_game_name.board.BoardService;
import es.us.dp1.lx_xy_24_25.your_game_name.exceptions.ResourceNotFoundException;
import es.us.dp1.lx_xy_24_25.your_game_name.hand.Hand;
import es.us.dp1.lx_xy_24_25.your_game_name.hand.HandService;
import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;
import es.us.dp1.lx_xy_24_25.your_game_name.player.PlayerRepository;
import es.us.dp1.lx_xy_24_25.your_game_name.player.PlayerService;
import es.us.dp1.lx_xy_24_25.your_game_name.tile.Tile;
import es.us.dp1.lx_xy_24_25.your_game_name.tile.TileService;
import es.us.dp1.lx_xy_24_25.your_game_name.user.UserService;
import io.micrometer.core.ipc.http.HttpSender.Response;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/game")
@Tag(name = "Games", description = "API for the  management of  Games.")
@SecurityRequirement(name = "bearerAuth")
public class GameRestController {
    
    GameService gs;
    PlayerRepository pr;
    UserService userService;
    PlayerService ps;
    BoardService bs;
    HandService hs;
    TileService ts;

    @Autowired
    public GameRestController(GameService gs, PlayerRepository pr, UserService userService, PlayerService ps, BoardService bs,HandService hs, TileService ts){
        this.gs=gs;
        this.pr = pr;
        this.userService = userService;
        this.ps= ps;
        this.bs = bs;
        this.hs = hs;
        this.ts = ts;
    }

    @GetMapping("/{id}")
    public Game getGameById(@PathVariable("id") Integer id){
        Optional<Game> g=gs.getGameById(id);
        if(!g.isPresent())
            throw new ResourceNotFoundException("Game", "id", id);
        return g.get();
    }

    @GetMapping("/{id}/players")
    public List<Player> getPlayersByGameId(@PathVariable("id") Integer id){
        Optional<Game> g=gs.getGameById(id);
        List<Player> players = g.get().getJugadores();
        if(!g.isPresent())
            throw new ResourceNotFoundException("Game", "id", id);
        return players;
    }

    @GetMapping()
    public List<Game> getAllGames(){
        return gs.getAllGames();
    }
    
    @PatchMapping("/{gameCode}/join")
    public ResponseEntity<Game> joinGame(@PathVariable("gameCode") String gameCode) throws Exception{
        String username = userService.findCurrentUser().getUsername();
        Game game = gs.joinGame(gameCode, username);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Game> createGame(@Valid @RequestBody Game g){
        g=gs.save(g);
        URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(g.getId())
                    .toUri();
        return ResponseEntity.created(location).body(g);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateGame(@Valid @RequestBody Game g,@PathVariable("id")Integer id){
        Game gToUpdate=getGameById(id);
        BeanUtils.copyProperties(g,gToUpdate, "id");
        gs.save(gToUpdate);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/endGame")
    public ResponseEntity<Void> updateGameStatus(@PathVariable("id") Integer id) {
        Game gToUpdate = getGameById(id);
        gToUpdate.setGameStatus(GameStatus.FINISHED);
        gToUpdate.setFinish(LocalDateTime.now());
        gs.save(gToUpdate);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable("id")Integer id){
        if(getGameById(id)!=null)
            gs.delete(id);
        return ResponseEntity.noContent().build();
    }

//------CREAR PARTIDA DE CADA TIPO----------
    @PostMapping("/new")
    public ResponseEntity<Map<String, Object>> createNewGame(@RequestBody Map<String, String> body) {
        String gameModeString = body.get("gameMode");
        GameModes gameMode;
        try {
            gameMode = GameModes.valueOf(gameModeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException("Invalid game mode: " + gameModeString);
        }
        String username = userService.findCurrentUser().getUsername();
        Player player = ps.findPlayer(username);

        // Create the game
        Game game = new Game();
        List<Player> jugadores = new ArrayList<>();
        game.setCreador(player);
        game.setGamemode(gameMode);
        game.setTurn(1);
        game.setStart(LocalDateTime.now());
        jugadores.add(player);
        game.setJugadores(jugadores);
        player.setPartida(game);
        if (game.getGamemode() == GameModes.COMPETITIVE) {
            game.setCodigoDePartida(1000 + new Random().nextInt(9000));
            game.setGameStatus(GameStatus.WAITING);
        } else {
            game.setGameStatus(GameStatus.PLAYING);
        }

        Game savedGame = gs.save(game);
        pr.save(player);

        // Create a new board associated with the game
        Board board = new Board();
        board.setPartida(savedGame);
        Board savedBoard = bs.createBoard(board);

        // Save the new board in the game
        savedGame.setTablero(savedBoard);
        gs.save(savedGame);

        // Create a hand for each player in the game
        for (Player p : savedGame.getJugadores()) {
            Hand existingHand = hs.findByPlayerUsername(p.getUsername());
            if (existingHand != null) {
                existingHand.setNumFichas(0);
                existingHand.setStatus(false);
                existingHand.setTablero(savedBoard);
                hs.save(existingHand);
            } else {
                Hand hand = new Hand();
                hand.setNumFichas(0);
                hand.setStatus(false);
                hand.setPlayer(p);
                hand.setTablero(savedBoard);
                Hand savedHand = hs.save(hand);
                p.setMano(savedHand);
                pr.save(p);
            }
        }

        // Return the URI of the created game
        Map<String, Object> response = new HashMap<>();
        response.put("gameId", savedGame.getId());
        response.put("tableroId", savedBoard.getId());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{gameId}/addPlayer/{playerId}")
    public ResponseEntity<Game> addPlayerToGame(@PathVariable Integer gameId, @PathVariable Integer playerId) {
        Game updatedGame = gs.addPlayerToGame(gameId, playerId);
        return ResponseEntity.ok(updatedGame);
    }

    // Método para obtener gameId a partir de tableroId
    @GetMapping("/getGameId/{tableroId}")
    public ResponseEntity<Integer> getGameIdByTableroId(@PathVariable Integer tableroId) {
        Integer gameId = gs.findGameIdByTableroId(tableroId);
        if (gameId != null) {
            return ResponseEntity.ok(gameId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("{gameId}/status")
    public ResponseEntity<Game> updateGameStatus(@PathVariable Integer gameId, @RequestBody Map<String, String> requestBody) {
        String newStatusStr = requestBody.get("gameStatus");
        GameStatus newStatus = GameStatus.valueOf(newStatusStr.toUpperCase());
    
        Optional<Game> game = gs.getGameById(gameId);
        if (game.isEmpty()) {
            throw new ResourceNotFoundException("Game", "id", gameId);
        }
        Game newGame = game.get();

        newGame.setGameStatus(newStatus);
        newGame.setFinish(LocalDateTime.now());
        gs.save(newGame);

        return ResponseEntity.ok(newGame);
    }

    @GetMapping("/code/{codigoDePartida}")
    public ResponseEntity<Game> getGameByCodigoDePartida(@PathVariable("codigoDePartida") Integer codigoDePartida) {
        return new ResponseEntity<>(gs.findGameByCodigoDePartida(codigoDePartida),HttpStatus.OK);
    }

    @PutMapping("/code/{codigoDePartida}/join/{username}")
    public ResponseEntity<Game> joinGame(@PathVariable("codigoDePartida") Integer codigoDePartida, @PathVariable("username") String username) {
        return new ResponseEntity<>(gs.joinToGame(codigoDePartida, username), HttpStatus.OK);
    }
    
    @GetMapping("/player/{username}/{status}")
    public List<Game> getGamesByPlayerAndStatus(@PathVariable("username") String username, @PathVariable("status") GameStatus status) {
        return gs.getGamesByPlayerAndStatus(username, status); 
    }

    @GetMapping("/status/{status}")
    public List<Game> getAllGamesByStatus(@PathVariable("status") GameStatus status){
        return gs.getGamesByStatus(status);
    }

    @PatchMapping("/{id}/startGame")
    public ResponseEntity<Void> startGame(@PathVariable("id") Integer id) {
        Game gToUpdate = getGameById(id);
        gToUpdate.setGameStatus(GameStatus.PLAYING);
        gs.save(gToUpdate);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/checkPlaying")
    public ResponseEntity<Boolean> checkIfGameIsPlaying(@PathVariable Integer id) {
        Optional<Game> optionalGame = gs.getGameById(id);
        if (!optionalGame.isPresent()) {
            throw new ResourceNotFoundException("Game", "id", id);
        }
        Game game = optionalGame.get();
        boolean isPlaying = game.getGameStatus() == GameStatus.PLAYING;
        return ResponseEntity.ok(isPlaying);
    }

    @PatchMapping("/{id}/turn")
    public ResponseEntity<Game> updateTurn(@PathVariable Integer id, @RequestParam Integer newTurn) {
        Optional<Game> optionalGame = gs.getGameById(id);
        if (!optionalGame.isPresent()) {
            throw new ResourceNotFoundException("Game", "id", id);
        }
        Game game = optionalGame.get();
        game.setTurn(newTurn);
        gs.save(game);
        return ResponseEntity.ok(game);
    }

    @GetMapping("/{id}/checkFinished")
    public ResponseEntity<Boolean> checkIfGameIsFinished(@PathVariable Integer id) {
        Optional<Game> optionalGame = gs.getGameById(id);
        if (!optionalGame.isPresent()) {
            throw new ResourceNotFoundException("Game", "id", id);
        }
        Game game = optionalGame.get();
        boolean isFinished = game.getGameStatus() == GameStatus.FINISHED;
        return ResponseEntity.ok(isFinished);
    }
}
