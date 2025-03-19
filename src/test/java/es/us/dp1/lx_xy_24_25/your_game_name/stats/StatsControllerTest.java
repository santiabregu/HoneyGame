package es.us.dp1.lx_xy_24_25.your_game_name.stats;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.us.dp1.lx_xy_24_25.your_game_name.configuration.SecurityConfiguration;
import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;
import es.us.dp1.lx_xy_24_25.your_game_name.stats.Stats;
import es.us.dp1.lx_xy_24_25.your_game_name.stats.StatsController;
import es.us.dp1.lx_xy_24_25.your_game_name.stats.StatsService;
import es.us.dp1.lx_xy_24_25.your_game_name.stats.AchievementService;

@WebMvcTest(controllers = StatsController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class StatsControllerTest {

    private static final String BASE_URL = "/api/v1/players/stats";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatsService statsService;

    @MockBean
    private AchievementService achievementService;

    @Autowired
    private ObjectMapper objectMapper;

    private Stats stats;
    private Player player;

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
    }

    @Test
    @WithMockUser("admin")
    void shouldFindAllStats() throws Exception {
        Player player2 = new Player();
        player2.setId(2);
        player2.setUsername("player2");

        Stats stats2 = new Stats();
        stats2.setPlayer(player2);
        stats2.setTotalPoints(200);
        stats2.setPlayedGames(20);
        stats2.setWonGames(10);
        stats2.setBronzeMedals(2);
        stats2.setSilverMedals(3);
        stats2.setGoldMedals(4);
        stats2.setPlatinumMedals(5);

        when(this.statsService.findAll()).thenReturn(List.of(stats, stats2));

        mockMvc.perform(get(BASE_URL)).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].player.username").value("player1"))
                .andExpect(jsonPath("$[1].player.username").value("player2"));
    }

    @Test
    @WithMockUser("admin")
    void shouldFindStatsByUsername() throws Exception {
        when(this.statsService.getStatsByUsername("player1")).thenReturn(stats);

        mockMvc.perform(get(BASE_URL + "/{username}", "player1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.player.username").value("player1"))
                .andExpect(jsonPath("$.totalPoints").value(100))
                .andExpect(jsonPath("$.playedGames").value(10))
                .andExpect(jsonPath("$.wonGames").value(5))
                .andExpect(jsonPath("$.bronzeMedals").value(1))
                .andExpect(jsonPath("$.silverMedals").value(2))
                .andExpect(jsonPath("$.goldMedals").value(3))
                .andExpect(jsonPath("$.platinumMedals").value(4));
    }

    @Test
    @WithMockUser("admin")
    void shouldUpdateStats() throws Exception {
        stats.setTotalPoints(150);
        stats.setPlayedGames(15);
        stats.setWonGames(7);

        when(this.statsService.updateStats(any(String.class), any(Boolean.class), any(Integer.class))).thenReturn(stats);
        doNothing().when(this.achievementService).checkAndUnlockAchievements(any(String.class));

        mockMvc.perform(put(BASE_URL + "/{username}/{estadoPartida}", "player1", true)
                .with(csrf())
                .param("puntosPartida", "50")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stats)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player.username").value("player1"))
                .andExpect(jsonPath("$.totalPoints").value(150))
                .andExpect(jsonPath("$.playedGames").value(15))
                .andExpect(jsonPath("$.wonGames").value(7));
    }

    @Test
    @WithMockUser("admin")
    void shouldGetRanking() throws Exception {
        PlayerRankingDTO playerRankingDTO = new PlayerRankingDTO("player1", "image.png",100);
        PlayerRankingDTO playerRankingDTO2 = new PlayerRankingDTO("player2","image.png", 200);

        when(this.statsService.getPlayersOrderedByTotalPoints()).thenReturn(List.of(playerRankingDTO, playerRankingDTO2));

        mockMvc.perform(get(BASE_URL + "/ranking"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].username").value("player1"))
                .andExpect(jsonPath("$[0].totalPoints").value(100))
                .andExpect(jsonPath("$[1].username").value("player2"))
                .andExpect(jsonPath("$[1].totalPoints").value(200));
    }
}