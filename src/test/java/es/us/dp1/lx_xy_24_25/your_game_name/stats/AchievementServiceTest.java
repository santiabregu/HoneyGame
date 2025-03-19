package es.us.dp1.lx_xy_24_25.your_game_name.stats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;

@SpringBootTest
public class AchievementServiceTest {

    @Autowired
    private AchievementService achievementService;

    @MockBean
    private AchievementRepository achievementRepository;

    @MockBean
    private StatsService statsService;

    private Achievement achievement;
    private Player player;
    private Stats stats;

    @BeforeEach
    void setup() {
        player = new Player();
        player.setId(1);
        player.setUsername("player1");

        stats = new Stats();
        stats.setPlayer(player);
        stats.setTotalPoints(100);
        stats.setPlayedGames(10);
        stats.setWonGames(5);
        stats.setBronzeMedals(1);
        stats.setSilverMedals(2);
        stats.setGoldMedals(3);
        stats.setPlatinumMedals(4);

        achievement = new Achievement();
        achievement.setId(1);
        achievement.setName("First Achievement");
        achievement.setDescription("Achieve <THRESHOLD> points");
        achievement.setThreshold(50);
        achievement.setMetric(Metric.TOTAL_POINTS);
        achievement.setPlayer(player);
        achievement.setUnlocked(false);
        achievement.setClaimed(false);
    }

    @Test
    void shouldFindAllAchievements() {
        Achievement achievement2 = new Achievement();
        achievement2.setId(2);
        achievement2.setName("Second Achievement");
        achievement2.setDescription("Achieve <THRESHOLD> games played");
        achievement2.setThreshold(10);
        achievement2.setMetric(Metric.GAMES_PLAYED);
        achievement2.setPlayer(player);
        achievement2.setUnlocked(false);
        achievement2.setClaimed(false);

        when(this.achievementRepository.findAll()).thenReturn(List.of(achievement, achievement2));

        List<Achievement> achievements = this.achievementService.getAchievements();
        assertEquals(2, achievements.size());
    }

    @Test
    void shouldFindAchievementById() {
        when(this.achievementRepository.findById(1)).thenReturn(Optional.of(achievement));

        Achievement foundAchievement = this.achievementService.getById(1);
        assertEquals(achievement.getName(), foundAchievement.getName());
    }

    @Test
    void shouldSaveAchievement() {
        when(this.achievementRepository.save(any(Achievement.class))).thenReturn(achievement);

        Achievement savedAchievement = this.achievementService.saveAchievement(achievement);
        assertEquals(achievement.getName(), savedAchievement.getName());
    }

    @Test
    void shouldDeleteAchievementById() {
        doNothing().when(this.achievementRepository).deleteById(1);

        this.achievementService.deleteAchievementById(1);
        Mockito.verify(this.achievementRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    void shouldCheckAndUnlockAchievements() {
        when(this.statsService.getStatsByUsername("player1")).thenReturn(stats);
        when(this.achievementRepository.findAchievementsByUsername("player1")).thenReturn(Optional.of(List.of(achievement)));

        this.achievementService.checkAndUnlockAchievements("player1");

        assertEquals(true, achievement.getUnlocked());
    }

    @Test
    void shouldFindAchievementByName() {
        when(this.achievementRepository.findByName("First Achievement")).thenReturn(achievement);

        Achievement foundAchievement = this.achievementService.getAchievementByName("First Achievement");
        assertEquals(achievement.getName(), foundAchievement.getName());
    }

    @Test
    void shouldFindAchievementsByUsername() {
        when(this.achievementRepository.findAchievementsByUsername("player1")).thenReturn(Optional.of(List.of(achievement)));

        Optional<List<Achievement>> achievements = this.achievementService.getAchievementsByUsername("player1");
        assertEquals(1, achievements.get().size());
    }

    @Test
    void shouldFindSpecificAchievementByUsername() {
        when(this.achievementRepository.findSpecificAchievementByUsername("player1", 1)).thenReturn(Optional.of(achievement));

        Optional<Achievement> foundAchievement = this.achievementService.getSpecificAchievementByUsername("player1", 1);
        assertEquals(achievement.getName(), foundAchievement.get().getName());
    }
}