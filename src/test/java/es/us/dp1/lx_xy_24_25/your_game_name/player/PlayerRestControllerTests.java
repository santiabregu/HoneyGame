package es.us.dp1.lx_xy_24_25.your_game_name.player;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.us.dp1.lx_xy_24_25.your_game_name.configuration.SecurityConfiguration;
import es.us.dp1.lx_xy_24_25.your_game_name.exceptions.ResourceNotFoundException;
import es.us.dp1.lx_xy_24_25.your_game_name.friendrequest.FriendRequest;
import es.us.dp1.lx_xy_24_25.your_game_name.user.Authorities;
import es.us.dp1.lx_xy_24_25.your_game_name.user.AuthoritiesService;
import es.us.dp1.lx_xy_24_25.your_game_name.user.User;
import es.us.dp1.lx_xy_24_25.your_game_name.user.UserService;

@WebMvcTest(controllers = PlayerRestController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class PlayerRestControllerTests {

    private static final String BASE_URL = "/api/v1/players";
    private static final String TEST_USERNAME = "testuser"; //el nombre del usuario autenticado en el test
    private static final Integer TEST_REQUEST_ID = 1;
    private static final int TEST_USER_ID = 1;
	private static final int TEST_AUTH_ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @Autowired
    private PlayerRestController playerRestController;

	@MockBean
	private AuthoritiesService authService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private Player player;
    private Authorities auth;

    @BeforeEach
    void setup() {
        auth = new Authorities();
		auth.setId(TEST_AUTH_ID);

        user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setAuthority(auth);

        player = new Player();
        player.setUsername("testuser");
        player.setUser(user); // Set the User object itself

        when(this.userService.findCurrentUser()).thenReturn(getUserFromDetails(
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }

    private User getUserFromDetails(UserDetails details) {
        User logged = new User();
        logged.setUsername(details.getUsername());
        logged.setPassword(details.getPassword());
        Authorities aux = new Authorities();
        for (GrantedAuthority auth : details.getAuthorities()) {
            aux.setAuthority(auth.getAuthority());
        }
        logged.setAuthority(aux);
        return logged;
    }

    @Test
    @WithMockUser("testuser")
    void shouldGetCurrentPlayerTest() throws Exception {
        when(this.userService.findCurrentUser()).thenReturn(user);

        mockMvc.perform(get(BASE_URL + "/currentPlayer").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(TEST_USERNAME));
    }
    
    @Test
    @WithMockUser("testuser")
    void shouldGetPlayerByUsername() throws Exception {
        when(this.playerService.findPlayer(TEST_USERNAME)).thenReturn(player);

        mockMvc.perform(get(BASE_URL + "/{username}", TEST_USERNAME).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(TEST_USERNAME));
    }

    @Test
    @WithMockUser("testuser")
    void shouldSendFriendRequest() throws Exception {
        doNothing().when(this.playerService).sendFriendRequest(TEST_USERNAME, "frienduser");

        mockMvc.perform(post(BASE_URL + "/{username}/send-friend-request/{receiverUsername}", TEST_USERNAME, "frienduser").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("testuser")
    void shouldRespondToFriendRequest() throws Exception {
        doNothing().when(this.playerService).respondToFriendRequest(TEST_REQUEST_ID, FriendRequest.RequestStatus.ACCEPTED);

        mockMvc.perform(post(BASE_URL + "/{username}/respond-friend-request/{requestId}", TEST_USERNAME, TEST_REQUEST_ID)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Collections.singletonMap("status", "ACCEPTED"))))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("testuser")
    void shouldGetFriends() throws Exception {
        Set<Player> friends = Set.of(new Player(), new Player());
        when(this.playerService.findFriendsByUsername(TEST_USERNAME)).thenReturn(Optional.of(friends));

        mockMvc.perform(get(BASE_URL + "/{username}/friends", TEST_USERNAME).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(friends.size()));
    }

    @Test
    @WithMockUser("testuser")
    void shouldGetPendingRequests() throws Exception {
        List<FriendRequest> pendingRequests = List.of(new FriendRequest(), new FriendRequest());
        when(this.playerService.findAllPendingRequests(TEST_USERNAME)).thenReturn(pendingRequests);

        mockMvc.perform(get(BASE_URL + "/{username}/pending-requests", TEST_USERNAME).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(pendingRequests.size()));
    }

    @Test
    @WithMockUser("testuser")
    void shouldGetPlayerById() throws Exception {
        when(this.playerService.findById(TEST_USER_ID)).thenReturn(player);

        mockMvc.perform(get(BASE_URL + "/id/{playerId}", TEST_USER_ID).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(TEST_USERNAME));
    }

    @Test
    @WithMockUser("testuser")
    void shouldCreatePlayer() throws Exception {
        when(this.playerService.savePlayer(any(Player.class))).thenReturn(player);

        mockMvc.perform(post(BASE_URL)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(TEST_USERNAME));
    }

    @Test
    @WithMockUser("testuser")
    void shouldUpdatePlayer() throws Exception {
    when(this.playerService.findById(TEST_USER_ID)).thenReturn(new Player());
    when(this.playerService.updatePlayer(any(Player.class), eq(TEST_USER_ID))).thenReturn(player);

    mockMvc.perform(put(BASE_URL + "/{playerId}", TEST_USER_ID)
            .with(csrf()) 
            .contentType(MediaType.APPLICATION_JSON) 
            .content(objectMapper.writeValueAsString(player))) 
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value(TEST_USERNAME)); 
}

    @Test
    @WithMockUser("testuser")
    void shouldDeletePlayer() throws Exception {
        when(playerService.findById(TEST_USER_ID)).thenReturn(new Player());
        doNothing().when(playerService).deletePlayer(TEST_USER_ID);
        mockMvc.perform(delete(BASE_URL + "/{id}", TEST_USER_ID).with(csrf()))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("testuser")
    void shouldGetOnlineFriends() throws Exception {
        Set<Player> onlineFriends = Set.of(new Player(), new Player());
        when(this.playerService.findOnlineFriendsByUsername(TEST_USERNAME)).thenReturn(Optional.of(onlineFriends));

        mockMvc.perform(get(BASE_URL + "/{username}/onlineFriends", TEST_USERNAME).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(onlineFriends.size()));
    }

    @Test
    @WithMockUser("testuser")
    void shouldGetPaginatedPlayers() throws Exception {
        List<playerDTOPaginated> players = List.of(new playerDTOPaginated(), new playerDTOPaginated());
        int totalPages = 2;
        when(this.playerService.findPlayersPaginated(0, 10)).thenReturn(players);
        when(this.playerService.getTotalPages(10)).thenReturn(totalPages);

        mockMvc.perform(get(BASE_URL + "/paginated").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.players.length()").value(players.size()))
                .andExpect(jsonPath("$.totalPages").value(totalPages));
    }
}