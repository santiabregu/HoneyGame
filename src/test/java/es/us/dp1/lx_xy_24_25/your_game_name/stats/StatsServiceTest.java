package es.us.dp1.lx_xy_24_25.your_game_name.stats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import es.us.dp1.lx_xy_24_25.your_game_name.exceptions.ResourceNotFoundException;
import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;
import es.us.dp1.lx_xy_24_25.your_game_name.player.PlayerRepository;
import es.us.dp1.lx_xy_24_25.your_game_name.user.User;

@SpringBootTest
public class StatsServiceTest {
    
	private Player player;
    private Stats stats;

    @Autowired
    private StatsService stastService;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private StatsRepository statsRepository;

	@BeforeEach
	void setUp() {
	
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


}

	@Test
	void shouldFindAllStats() {
    Stats stats2 = new Stats();
    Player player2 = new Player();
    player2.setId(2);
    player2.setUsername("player2");
    stats2.setPlayer(player2);
    stats2.setTotalPoints(50);
    stats2.setPlayedGames(5);
    stats2.setWonGames(2);
    stats2.setBronzeMedals(0);
    stats2.setSilverMedals(1);
    stats2.setGoldMedals(1);
    stats2.setPlatinumMedals(0);

    when(this.statsRepository.findAll()).thenReturn(List.of(stats, stats2)); 
    List<Stats> statsList = this.stastService.findAll();
    assertEquals(2, statsList.size());
}



	@Test
	void shouldFindStatsByUsername() {
		when(this.statsRepository.findStatsByUsername("player1")).thenReturn(java.util.Optional.of(stats));
	
		Stats retrievedStats = this.stastService.getStatsByUsername("player1");
	
		assertEquals("player1", retrievedStats.getPlayer().getUsername());
		assertEquals(100, retrievedStats.getTotalPoints());
		assertEquals(10, retrievedStats.getPlayedGames());
		assertEquals(5, retrievedStats.getWonGames());
		assertEquals(1, retrievedStats.getBronzeMedals());
		assertEquals(2, retrievedStats.getSilverMedals());
		assertEquals(3, retrievedStats.getGoldMedals());
		assertEquals(4, retrievedStats.getPlatinumMedals());
	}
	

    @Test
    void shouldThrowExceptionWhenPlayerNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            this.stastService.updateStats("unknownPlayer", true, 10);
        });
    }

    @Test
    void shouldSaveStats() {
        when(this.statsRepository.save(any(Stats.class))).thenReturn(stats);
        this.stastService.saveStats(stats);
        assertNotNull(stats);
    }

    @Test
    void shouldUpdateStats() throws ResourceNotFoundException {
        when(this.statsRepository.findStatsByUsername("player1")).thenReturn(java.util.Optional.of(stats));
        when(this.playerRepository.findByUsername("player1")).thenReturn(java.util.Optional.of(player));
        when(this.statsRepository.save(any(Stats.class))).thenReturn(stats);

        Stats updatedStats = this.stastService.updateStats("player1", true, 15);

        assertNotNull(updatedStats);
        assertEquals(11, updatedStats.getPlayedGames());
        assertEquals(115, updatedStats.getTotalPoints());
        assertEquals(6, updatedStats.getWonGames());
        assertEquals(1, updatedStats.getBronzeMedals());
        assertEquals(2, updatedStats.getSilverMedals());
        assertEquals(3, updatedStats.getGoldMedals());
        assertEquals(4, updatedStats.getPlatinumMedals());
    }

    @Test
    void shouldGetPlayersOrderedByTotalPoints() {
        Stats stats2 = new Stats();
        Player player2 = new Player();
        player2.setId(2);
        player2.setUsername("player2");
        stats2.setPlayer(player2);
        stats2.setTotalPoints(50);

        player.setUser(new User());
        player.getUser().setProfilePhoto("photo1.jpg");

        player2.setUser(new User());
        player2.getUser().setProfilePhoto("photo2.jpg");

        when(this.statsRepository.findAllOrderedByTotalPoints()).thenReturn(List.of(stats, stats2));

        List<PlayerRankingDTO> ranking = this.stastService.getPlayersOrderedByTotalPoints();

        PlayerRankingDTO playerRankingDTO1 = new PlayerRankingDTO(player.getUsername(), player.getUser().getProfilePhoto(), 100);
        PlayerRankingDTO playerRankingDTO2 = new PlayerRankingDTO(player2.getUsername(), player2.getUser().getProfilePhoto(), 50);

        assertEquals(2, ranking.size());
        assertEquals(playerRankingDTO1.getUsername(), ranking.get(0).getUsername());
        assertEquals(playerRankingDTO1.getTotalPoints(), ranking.get(0).getTotalPoints());
        assertEquals(playerRankingDTO1.getProfilePhoto(), ranking.get(0).getProfilePhoto());
        assertEquals(playerRankingDTO2.getUsername(), ranking.get(1).getUsername());
        assertEquals(playerRankingDTO2.getTotalPoints(), ranking.get(1).getTotalPoints());
        assertEquals(playerRankingDTO2.getProfilePhoto(), ranking.get(1).getProfilePhoto());
    }
	
}
