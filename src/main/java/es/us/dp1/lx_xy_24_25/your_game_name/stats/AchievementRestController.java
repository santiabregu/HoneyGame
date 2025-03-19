package es.us.dp1.lx_xy_24_25.your_game_name.stats;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import es.us.dp1.lx_xy_24_25.your_game_name.exceptions.ResourceNotFoundException;
import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;
import es.us.dp1.lx_xy_24_25.your_game_name.player.PlayerService;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/players/achievements")
@Tag(name = "Achievements", description = "The Achievements management API")
@SecurityRequirement(name = "bearerAuth")
public class AchievementRestController {
    
	@Autowired
    private AchievementService achievementService;

	@Autowired
    private PlayerService playerService;

	


    @Autowired
	public AchievementRestController(AchievementService achievementService) {
		this.achievementService = achievementService;
	}

    @GetMapping
	public ResponseEntity<List<Achievement>> findAll() {
		return new ResponseEntity<>((List<Achievement>) achievementService.getAchievements(), HttpStatus.OK);
	}

	@GetMapping("/achievement/{id}")
	public ResponseEntity<Achievement> findAchievement(@PathVariable("id") int id){
		Achievement achievementToGet=achievementService.getById(id);
		if(achievementToGet==null)
			throw new ResourceNotFoundException("Achievement with id "+id+" not found!");
		return new ResponseEntity<Achievement>(achievementToGet, HttpStatus.OK);
	}

	@GetMapping("/{username}")
	public ResponseEntity<List<Achievement>> findAchievementsByUsername(@PathVariable("username") String username){
		List<Achievement> achievementsToGet=achievementService.getAchievementsByUsername(username).orElse(null);
		if(achievementsToGet==null)
			throw new ResourceNotFoundException("Achievement with id "+username+" not found!");
		return new ResponseEntity<List<Achievement>>(achievementsToGet, HttpStatus.OK);
	}

	@PutMapping("/{username}/claim/{id}")
	public ResponseEntity<Achievement> claimAchievement(@PathVariable("id") int id, @PathVariable("username") String username){
		Achievement achievementToGet=achievementService.getSpecificAchievementByUsername(username, id).orElse(null);
		if(achievementToGet==null)
			throw new ResourceNotFoundException("Achievement with id "+id+" not found!");
		if(achievementToGet.getClaimed()==false)
			achievementToGet.setClaimed(true);
			achievementService.saveAchievement(achievementToGet); // Save the updated achievement
		return new ResponseEntity<Achievement>(achievementToGet, HttpStatus.OK);
	} 

    @PostMapping
    public ResponseEntity<Achievement> createAchievement(@RequestBody @Valid Achievement newAchievement, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Achievement savedAchievement = achievementService.saveAchievement(newAchievement);

        // Fetch all players
        List<Player> players = playerService.findAll();

        // Assign the new achievement to each player
        for (Player player : players) {
            Achievement playerAchievement = new Achievement();
			playerAchievement.setName(savedAchievement.getName());
            playerAchievement.setDescription(savedAchievement.getDescription());
            playerAchievement.setBadgeImage(savedAchievement.getBadgeImage());
            playerAchievement.setThreshold(savedAchievement.getThreshold());
            playerAchievement.setMetric(savedAchievement.getMetric());
            playerAchievement.setClaimed(false);
            playerAchievement.setUnlocked(false);
            playerAchievement.setPlayer(player);
            achievementService.saveAchievement(playerAchievement);
        }

        return new ResponseEntity<>(savedAchievement, HttpStatus.CREATED);
    }

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAchievement(@PathVariable("id") int id){
		findAchievement(id);
		achievementService.deleteAchievementById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
