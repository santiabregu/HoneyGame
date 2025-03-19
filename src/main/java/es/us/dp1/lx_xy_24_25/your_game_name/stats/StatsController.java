package es.us.dp1.lx_xy_24_25.your_game_name.stats;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.ipc.http.HttpSender.Response;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;
import es.us.dp1.lx_xy_24_25.your_game_name.stats.*;;

@RestController
@RequestMapping("/api/v1/players/stats")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Stats", description = "API for the management of Stats")
public class StatsController {

    @Autowired
    private StatsService statsService;  

    @Autowired
    private AchievementService achievementService;

    @GetMapping
    public ResponseEntity<List<Stats>> findAll(){
        List<Stats> res;
        res = (List<Stats>) statsService.findAll();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(value="{username}")
    public ResponseEntity<Stats> findStatsByUsername(@PathVariable("username") String username){
        return new ResponseEntity<>(statsService.getStatsByUsername(username), HttpStatus.OK);
    }


    @PutMapping("/{username}/{estadoPartida}")
    public ResponseEntity<Stats> updateStats(@PathVariable("username") String username, @PathVariable("estadoPartida") Boolean estadoPartida, @RequestParam Integer puntosPartida) {
        Stats updatedStats = statsService.updateStats(username, estadoPartida, puntosPartida);
        achievementService.checkAndUnlockAchievements(username);
        return new ResponseEntity<>(updatedStats, HttpStatus.OK);
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<PlayerRankingDTO>> getRanking() {
        List<PlayerRankingDTO> ranking = statsService.getPlayersOrderedByTotalPoints();
        return new ResponseEntity<>(ranking, HttpStatus.OK);
    }
}
