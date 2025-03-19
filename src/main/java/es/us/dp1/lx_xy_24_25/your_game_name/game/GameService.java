package es.us.dp1.lx_xy_24_25.your_game_name.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.lx_xy_24_25.your_game_name.exceptions.ResourceNotFoundException;
import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;
import es.us.dp1.lx_xy_24_25.your_game_name.player.PlayerRepository;

@Service
public class GameService {
    

    private GameRepository gr;
    private PlayerRepository pr;


    Map<Integer, List<Integer>> vecinos = new HashMap<Integer, List<Integer>>() {{
        put(1, Arrays.asList(2, 3, 5));
        put(2, Arrays.asList(4, 7, 5, 1));
        put(3, Arrays.asList(1, 5, 6, 8));
        put(4, Arrays.asList(2, 7, 9));
        put(5, Arrays.asList(1, 2, 3, 7, 8, 10));
        put(6, Arrays.asList(3, 8 , 11));
        put(7, Arrays.asList(2, 4, 5, 9, 10, 12));
        put(8, Arrays.asList(3, 5, 6, 10, 11, 13));
        put(9, Arrays.asList(4, 7, 12, 14));
        put(10, Arrays.asList(5, 7, 8, 12, 13, 15));
        put(11, Arrays.asList(6, 8, 13, 16));
        put(12, Arrays.asList(7, 9, 10, 14, 15, 17));
        put(13, Arrays.asList(8, 10, 11, 15, 16, 18));
        put(14, Arrays.asList(9, 12, 17));
        put(15, Arrays.asList(10, 12, 13, 17, 18, 19));
        put(16, Arrays.asList(11, 13, 18));
        put(17, Arrays.asList(12, 14, 15, 19));
        put(18, Arrays.asList(13, 15, 16, 19));
        put(19, Arrays.asList(15, 17, 18));
    }};

    @Autowired
    public GameService(GameRepository gr, PlayerRepository pr){
        this.gr=gr;
        this.pr=pr;
    }

    @Transactional(readOnly=true)
    public List<Game> getAllGames(){
        return gr.findAll();
    }

    @Transactional
    public Game save(Game g) {
        gr.save(g);
        return g;
    }
    @Transactional(readOnly=true)
    public Optional<Game> getGameById(Integer id) {        
        return Optional.of(gr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game not found")));
    }

    @Transactional(readOnly = true)
    public List<Game> getGamesByStatus(GameStatus status){
        return gr.findByGameStatus(status);
    }
    
    @Transactional()
    public void delete(Integer id) {
        gr.deleteById(id);
    }

    public Game addPlayerToGame(Integer gameId, Integer playerId) {
        Game game = gr.findById(gameId).orElseThrow(() -> new ResourceNotFoundException("Game not found"));
        Player player = pr.findById(playerId).orElseThrow(() -> new ResourceNotFoundException("Player not found"));
        
        game.getJugadores().add(player);  
        player.setPartida(game);  

        gr.save(game);  
        pr.save(player);  
        return gr.save(game);
    }

    public Integer findGameIdByTableroId(Integer tableroId) {
        Game game = gr.findByTableroId(tableroId);
        return game != null ? game.getId() : null;
    }

  
    @Transactional(readOnly = true)
    public Game findGameByCodigoDePartida(Integer codigoDePartida) throws ResourceNotFoundException {
        Game g = gr.findGameByCodigoDePartida(codigoDePartida);
        if(g == null){
            throw new ResourceNotFoundException("Game with this code: " + codigoDePartida+" not found");
        }
        return g;
    }
    

    @Transactional()
    public Game joinToGame(Integer codigoDePartida, String username) {
        Game game = findGameByCodigoDePartida(codigoDePartida);
        Player player = pr.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Player not found"));
        game.getJugadores().add(player);
        player.setPartida(game);
        gr.save(game);
        pr.save(player);
        return gr.save(game);
    }
    
    @Transactional(readOnly = true)
    public List<Game> getGamesByPlayerAndStatus(String username, GameStatus status) {
        return gr.findGamesByUsernameAndStatus(username, status);
    }

    @Transactional()
    public Game joinGame(String codigoPartida, String username) throws Exception{
        Game game = gr.findGameByCodigoDePartida(codigoPartida);
        Player playerToAdd = pr.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Player not found")); 
        List<Player> players = game.getJugadores();
        if(players.contains(playerToAdd)){
            throw new Exception("Player already in game");
        }
        players.add(playerToAdd);
        game.setJugadores(players);
        return gr.save(game);
    }
}
