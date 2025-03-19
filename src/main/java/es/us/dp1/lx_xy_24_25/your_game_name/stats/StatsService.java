package es.us.dp1.lx_xy_24_25.your_game_name.stats;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.lx_xy_24_25.your_game_name.exceptions.ResourceNotFoundException;
import es.us.dp1.lx_xy_24_25.your_game_name.player.PlayerRepository;
import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;

@Service
public class StatsService {

    StatsRepository sr;
    PlayerRepository pr;
    @Autowired
    public StatsService(StatsRepository sr, PlayerRepository pr){
        this.sr=sr;
        this.pr = pr;
    }

    @Transactional(readOnly = true) 
    public List<Stats> findAll(){
        return sr.findAll();
    }

    @Transactional(readOnly = true)
    public Stats getStatsByUsername(String username){
        return sr.findStatsByUsername(username).orElse(null);
    }

    @Transactional
    public void saveStats(Stats stats) {
         sr.save(stats);
    }

    @Transactional
    public Stats updateStats(String username, Boolean estadoPartida, Integer puntosPartida) throws ResourceNotFoundException {
        Stats stats = sr.findStatsByUsername(username).orElse(null);
        Player p = pr.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Player", "username", username));

            stats.setPlayedGames(stats.getPlayedGames() + 1);
            stats.setTotalPoints(stats.getTotalPoints() + puntosPartida);
            if (estadoPartida) {
                stats.setWonGames(stats.getWonGames() + 1);
            }
            if (puntosPartida <= 0) {
                stats.setBronzeMedals(stats.getBronzeMedals() + 1);
            } else if (puntosPartida <= 10) {
                stats.setSilverMedals(stats.getSilverMedals() + 1);
            } else if (puntosPartida <= 20) {
                stats.setGoldMedals(stats.getSilverMedals() + 1);
            }else {
                stats.setPlatinumMedals(stats.getGoldMedals() + 1);
            }
        
        return sr.save(stats);
    }

    @Transactional(readOnly = true)
    public List<PlayerRankingDTO> getPlayersOrderedByTotalPoints() {
        List<Stats> stats = sr.findAllOrderedByTotalPoints();
    
        return stats.stream()
                    .map(stat -> new PlayerRankingDTO(
                        stat.getPlayer().getUsername(),
                        stat.getPlayer().getUser().getProfilePhoto(), // Foto del jugador
                        stat.getTotalPoints()
                    ))
                    .toList();
    }
    
}
