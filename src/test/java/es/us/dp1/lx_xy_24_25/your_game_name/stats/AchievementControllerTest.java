package es.us.dp1.lx_xy_24_25.your_game_name.stats;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

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
import es.us.dp1.lx_xy_24_25.your_game_name.stats.Achievement;
import es.us.dp1.lx_xy_24_25.your_game_name.stats.AchievementRestController;
import es.us.dp1.lx_xy_24_25.your_game_name.stats.AchievementService;
import es.us.dp1.lx_xy_24_25.your_game_name.stats.Metric;
import es.us.dp1.lx_xy_24_25.your_game_name.player.PlayerService;

@WebMvcTest(controllers = AchievementRestController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class AchievementControllerTest {

    private static final String BASE_URL = "/api/v1/players/achievements";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AchievementService achievementService;

    @MockBean
    private PlayerService playerService;

    @Autowired
    private ObjectMapper objectMapper;

    private Achievement achievement;
    private Player player;

    @BeforeEach
    void setup() {
        player = new Player();
        player.setId(1);
        player.setUsername("player1");

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
    @WithMockUser("admin")
    void shouldFindAllAchievements() throws Exception {
        Achievement achievement2 = new Achievement();
        achievement2.setId(2);
        achievement2.setName("Second Achievement");
        achievement2.setDescription("Achieve <THRESHOLD> games played");
        achievement2.setThreshold(10);
        achievement2.setMetric(Metric.GAMES_PLAYED);
        achievement2.setPlayer(player);
        achievement2.setUnlocked(false);
        achievement2.setClaimed(false);

        when(this.achievementService.getAchievements()).thenReturn(List.of(achievement, achievement2));

        mockMvc.perform(get(BASE_URL)).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("First Achievement"))
                .andExpect(jsonPath("$[1].name").value("Second Achievement"));
    }

    @Test
    @WithMockUser("admin")
    void shouldFindAchievementById() throws Exception {
        when(this.achievementService.getById(1)).thenReturn(achievement);

        mockMvc.perform(get(BASE_URL + "/achievement/{id}", 1)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("First Achievement"))
                .andExpect(jsonPath("$.description").value("Achieve <THRESHOLD> points"))
                .andExpect(jsonPath("$.threshold").value(50))
                .andExpect(jsonPath("$.metric").value("TOTAL_POINTS"));
    }

    @Test
    @WithMockUser("admin")
    void shouldFindAchievementsByUsername() throws Exception {
        when(this.achievementService.getAchievementsByUsername("player1")).thenReturn(Optional.of(List.of(achievement)));

        mockMvc.perform(get(BASE_URL + "/{username}", "player1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("First Achievement"))
                .andExpect(jsonPath("$[0].description").value("Achieve <THRESHOLD> points"))
                .andExpect(jsonPath("$[0].threshold").value(50))
                .andExpect(jsonPath("$[0].metric").value("TOTAL_POINTS"));
    }

    @Test
    @WithMockUser("admin")
    void shouldClaimAchievement() throws Exception {
        achievement.setClaimed(true);

        when(this.achievementService.getSpecificAchievementByUsername("player1", 1)).thenReturn(Optional.of(achievement));
        when(this.achievementService.saveAchievement(any(Achievement.class))).thenReturn(achievement);

        mockMvc.perform(put(BASE_URL + "/{username}/claim/{id}", "player1", 1)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(achievement)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.claimed").value(true));
    }

    @Test
    @WithMockUser("admin")
    void shouldCreateAchievement() throws Exception {
        when(this.achievementService.saveAchievement(any(Achievement.class))).thenReturn(achievement);
        when(this.playerService.findAll()).thenReturn(List.of(player));

        mockMvc.perform(post(BASE_URL)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(achievement)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("First Achievement"))
                .andExpect(jsonPath("$.description").value("Achieve <THRESHOLD> points"))
                .andExpect(jsonPath("$.threshold").value(50))
                .andExpect(jsonPath("$.metric").value("TOTAL_POINTS"));
    }

    @Test
    @WithMockUser("admin")
    void shouldDeleteAchievementById() throws Exception {
        when(this.achievementService.getById(1)).thenReturn(achievement);
        doNothing().when(this.achievementService).deleteAchievementById(1);

        mockMvc.perform(delete(BASE_URL + "/{id}", 1)
                .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
@WithMockUser("admin")
void shouldReturnNotFoundWhenAchievementByIdDoesNotExist() throws Exception {
    when(this.achievementService.getById(1)).thenReturn(null);

    mockMvc.perform(get(BASE_URL + "/achievement/{id}", 1))
            .andExpect(status().isNotFound());
}

@Test
@WithMockUser("admin")
void shouldReturnNotFoundWhenAchievementsByUsernameDoesNotExist() throws Exception {
    when(this.achievementService.getAchievementsByUsername("player1")).thenReturn(Optional.empty());

    mockMvc.perform(get(BASE_URL + "/{username}", "player1"))
            .andExpect(status().isNotFound());
}

@Test
@WithMockUser("admin")
void shouldReturnNotFoundWhenClaimingNonExistentAchievement() throws Exception {
    when(this.achievementService.getSpecificAchievementByUsername("player1", 1)).thenReturn(Optional.empty());

    mockMvc.perform(put(BASE_URL + "/{username}/claim/{id}", "player1", 1).with(csrf()))
            .andExpect(status().isNotFound());
}


}