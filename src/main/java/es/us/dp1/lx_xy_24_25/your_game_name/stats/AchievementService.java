package es.us.dp1.lx_xy_24_25.your_game_name.stats;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;
import jakarta.validation.Valid;
import es.us.dp1.lx_xy_24_25.your_game_name.stats.*;

@Service
public class AchievementService {
        
    AchievementRepository repo;
    StatsService sr;

    @Autowired
    public AchievementService(AchievementRepository repo, StatsService sr){
        this.repo=repo;
        this.sr=sr;
    }

    @Transactional(readOnly = true)    
    List<Achievement> getAchievements(){
        return repo.findAll();
    }
    
    @Transactional(readOnly = true)    
    public Achievement getById(int id){
        Optional<Achievement> result=repo.findById(id);
        return result.isPresent()?result.get():null;
    }

    @Transactional
    public Achievement saveAchievement(@Valid Achievement newAchievement) {
        return repo.save(newAchievement);
    }

    
    
    @Transactional
    public void deleteAchievementById(int id){
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Achievement getAchievementByName(String name){
        return repo.findByName(name);
    }

    @Transactional(readOnly = true)
    public Optional<List<Achievement>> getAchievementsByUsername(String username){
        return repo.findAchievementsByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<Achievement> getSpecificAchievementByUsername(String username, int id){
        return repo.findSpecificAchievementByUsername(username, id);
    }

    @Transactional
    public void checkAndUnlockAchievements(String playerName) {
        Stats stats = sr.getStatsByUsername(playerName);
        List<Achievement> achievements = repo.findAchievementsByUsername(playerName).orElse(null);

        for (Achievement achievement : achievements) {
            if (!achievement.getUnlocked()) {
                switch (achievement.getMetric()) {
                    case BRONZE_MEDALS:
                        if (stats.getBronzeMedals() >= achievement.getThreshold()) {
                            achievement.setUnlocked(true);
                            repo.save(achievement);
                        }
                        break;
                    case SILVER_MEDALS:
                        if (stats.getSilverMedals() >= achievement.getThreshold()) {
                            achievement.setUnlocked(true);
                            repo.save(achievement);
                        }
                        break;
                    case GOLD_MEDALS:
                        if (stats.getGoldMedals() >= achievement.getThreshold()) {
                            achievement.setUnlocked(true);
                            repo.save(achievement);
                        }
                        break;
                    case PLATINUM_MEDALS:
                        if (stats.getPlatinumMedals() >= achievement.getThreshold()) {
                            achievement.setUnlocked(true);
                            repo.save(achievement);
                        }
                        break;
                    case GAMES_PLAYED:
                        if (stats.getPlayedGames() >= achievement.getThreshold()) {
                            achievement.setUnlocked(true);
                            repo.save(achievement);
                        }
                        break;
                    case WON_GAMES:
                        if (stats.getWonGames() >= achievement.getThreshold()) {
                            achievement.setUnlocked(true);
                            repo.save(achievement);
                        }
                        break;
                    case TOTAL_POINTS:
                        if (stats.getTotalPoints() >= achievement.getThreshold()) {
                            achievement.setUnlocked(true);
                            repo.save(achievement);
                        }
                        break;
                }
            }
        }
    }
    

}
