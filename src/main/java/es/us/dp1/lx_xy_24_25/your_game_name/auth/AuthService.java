package es.us.dp1.lx_xy_24_25.your_game_name.auth;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.us.dp1.lx_xy_24_25.your_game_name.auth.payload.request.SignupRequest;
import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;
import es.us.dp1.lx_xy_24_25.your_game_name.player.PlayerService;
import es.us.dp1.lx_xy_24_25.your_game_name.stats.Stats;
import es.us.dp1.lx_xy_24_25.your_game_name.stats.StatsService;
import es.us.dp1.lx_xy_24_25.your_game_name.user.Authorities;
import es.us.dp1.lx_xy_24_25.your_game_name.user.AuthoritiesService;
import es.us.dp1.lx_xy_24_25.your_game_name.user.User;
import es.us.dp1.lx_xy_24_25.your_game_name.user.UserService;

@Service
public class AuthService {
    private final PasswordEncoder encoder;
    private final AuthoritiesService authoritiesService;
    private final UserService userService;
    private final PlayerService playerService;
    private final StatsService statsService;


    @Autowired
    public AuthService(PasswordEncoder encoder, AuthoritiesService authoritiesService, UserService userService,
                       PlayerService playerService, StatsService statsService) {
        this.encoder = encoder;
        this.authoritiesService = authoritiesService;
        this.userService = userService;
        this.playerService = playerService;
        this.statsService = statsService;
    }

    @Transactional
    public void createUser(@Valid SignupRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        Authorities role;
        role = authoritiesService.findByAuthority("PLAYER");
        user.setAuthority(role);
        //userService.saveUser(user);

        Player player = new Player();
        player.setUsername(request.getUsername());
        player.setFirstname(request.getFirstName());
        player.setLastname(request.getLastName());
        player.setId(user.getId());
        player.setUser(user);
        playerService.savePlayer(player);

        // Create and save Stats
        Stats stats = new Stats();
        stats.setPlayer(player);
        stats.setTotalPoints(0); // Set totalPoints to 0
        stats.setPlayedGames(0);
        stats.setWonGames(0);
        stats.setBronzeMedals(0);
        stats.setSilverMedals(0);
        stats.setGoldMedals(0);
        stats.setPlatinumMedals(0);
        statsService.saveStats(stats);
	}
}