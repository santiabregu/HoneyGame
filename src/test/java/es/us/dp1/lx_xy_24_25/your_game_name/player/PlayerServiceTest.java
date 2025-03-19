package es.us.dp1.lx_xy_24_25.your_game_name.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.lx_xy_24_25.your_game_name.exceptions.ResourceNotFoundException;
import es.us.dp1.lx_xy_24_25.your_game_name.friendrequest.FriendRequest;
import es.us.dp1.lx_xy_24_25.your_game_name.user.AuthoritiesService;
import es.us.dp1.lx_xy_24_25.your_game_name.user.User;
import es.us.dp1.lx_xy_24_25.your_game_name.user.UserService;

@SpringBootTest
@AutoConfigureTestDatabase
public class PlayerServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private AuthoritiesService authService;

    private User user;
    private Player player;

    // Existing test cases
    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    void shouldFindPlayerByUsername() {
        Player foundPlayer = playerService.findPlayer("sanbre");
        assertEquals("sanbre", foundPlayer.getUsername());
    }

    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    void shouldNotFindPlayerByIncorrectUsername() {
        assertThrows(ResourceNotFoundException.class, () -> playerService.findPlayer("nonexistent"));
    }

    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    @Transactional
    void shouldSendFriendRequest() {
        Player receiver = playerService.findPlayer("javpalgon");
        playerService.sendFriendRequest("sanbre", "javpalgon");

        List<FriendRequest> pendingRequests = playerService.findAllPendingRequests("javpalgon");
        assertEquals(1, pendingRequests.size());
        assertEquals(FriendRequest.RequestStatus.PENDING, pendingRequests.get(0).getStatus());
    }

    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    @Transactional
    void shouldRespondToFriendRequest() {
        Player sender = playerService.findPlayer("sanbre");
        Player receiver = playerService.findPlayer("javpalgon");
        playerService.sendFriendRequest("sanbre", "javpalgon");

        FriendRequest friendRequest = playerService.findAllPendingRequests("javpalgon").get(0);
        playerService.respondToFriendRequest(friendRequest.getId(), FriendRequest.RequestStatus.ACCEPTED);

        assertEquals(FriendRequest.RequestStatus.ACCEPTED, friendRequest.getStatus());
        assertEquals(2, receiver.getFriends().size());
        assertEquals(2, sender.getFriends().size());
    }

    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    @Transactional
    void shouldGetFriends() {
        Set<Player> friends = playerService.findFriendsByUsername("sanbre").orElseThrow();
        assertEquals(1, friends.size());
    }

    // New test cases

    // Positive tests
    /*@Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    @Transactional
    void shouldRejectFriendRequest() {
        playerService.sendFriendRequest("sanbre", "javpalgon");
        FriendRequest friendRequest = playerService.findAllPendingRequests("javpalgon").get(0);

        playerService.respondToFriendRequest(friendRequest.getId(), FriendRequest.RequestStatus.REJECTED);

        assertEquals(FriendRequest.RequestStatus.REJECTED, friendRequest.getStatus());
    }*/

    // Negative tests for findPlayer
    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    void shouldThrowExceptionIfPlayerNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> playerService.findPlayer("unknownUser"));
    }

    // Negative tests for sendFriendRequest
    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    @Transactional
    void shouldThrowExceptionWhenSendingRequestToNonExistentUser() {
        assertThrows(ResourceNotFoundException.class, () -> playerService.sendFriendRequest("sanbre", "unknownUser"));
    }

    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    @Transactional
    void shouldThrowExceptionForDuplicateFriendRequest() {
        playerService.sendFriendRequest("sanbre", "javpalgon");

        assertThrows(Exception.class, () -> playerService.sendFriendRequest("sanbre", "javpalgon"));
    }

    // Negative tests for respondToFriendRequest
    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    @Transactional
    void shouldThrowExceptionWhenRespondingToNonExistentRequest() {
        assertThrows(ResourceNotFoundException.class, () -> playerService.respondToFriendRequest(999, FriendRequest.RequestStatus.ACCEPTED));
    }

    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    @Transactional
    void shouldThrowExceptionWhenRespondingToAlreadyAcceptedRequest() {
        playerService.sendFriendRequest("sanbre", "javpalgon");
        FriendRequest friendRequest = playerService.findAllPendingRequests("javpalgon").get(0);

        playerService.respondToFriendRequest(friendRequest.getId(), FriendRequest.RequestStatus.ACCEPTED);

        assertThrows(Exception.class, () -> playerService.respondToFriendRequest(friendRequest.getId(), FriendRequest.RequestStatus.REJECTED));
    }


    // Positive tests for getFriends
    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    @Transactional
    void shouldReturnPlayerDTOListOfFriends() {
        List<PlayerDTO> friends = playerService.getFriends("sanbre");
        assertEquals(1, friends.size());
    }

    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    void shouldGetCurrentPlayer() {
        Player currentPlayer = playerService.getCurrentPlayer();
        assertEquals("sanbre", currentPlayer.getUsername());
    }

    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    void shouldGetCurrentUsername() {
        String currentUsername = playerService.getCurrentUsername();
        assertEquals("sanbre", currentUsername);
    }

    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    void shouldFindPlayerById() {
        Player player = playerService.findById(1);
        assertEquals(1, player.getId());
    }

    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    void shouldFindAllPlayers() {
        List<Player> players = playerService.findAll();
        assertEquals(4, players.size()); 
    }

    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    @Transactional
    void shouldSavePlayer() {
        Player newPlayer = new Player();
        newPlayer.setUsername("newplayer");
        newPlayer.setFirstname("New");
        newPlayer.setLastname("Player");
        Player savedPlayer = playerService.savePlayer(newPlayer);
        assertEquals("newplayer", savedPlayer.getUsername());
    }

    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    @Transactional
    void shouldUpdatePlayer() {
        Player playerToUpdate = playerService.findById(1);
        playerToUpdate.setFirstname("UpdatedName");
        Player updatedPlayer = playerService.updatePlayer(playerToUpdate, 1);
        assertEquals("UpdatedName", updatedPlayer.getFirstname());
    }


    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    void shouldFindPlayersPaginated() {
        List<playerDTOPaginated> playersPage = playerService.findPlayersPaginated(0, 2);
        assertEquals(2, playersPage.size()); //al menos 2
    }

    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    void shouldGetTotalPages() {
        int totalPages = playerService.getTotalPages(2);
        assertEquals(2, totalPages); // 4 jugadores, 2 paginas
    }

    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    @Transactional
    void shouldFindAllPendingRequests() {
        playerService.sendFriendRequest("sanbre", "javpalgon");
        List<FriendRequest> pendingRequests = playerService.findAllPendingRequests("javpalgon");
        assertEquals(1, pendingRequests.size());
        assertEquals(FriendRequest.RequestStatus.PENDING, pendingRequests.get(0).getStatus());
    }

    @Test
    @WithMockUser(username = "sanbre", password = "0wn3r")
    void shouldFindPlayer() {
        Player player = playerService.findPlayer("sanbre");
        assertEquals("sanbre", player.getUsername());
    }

}
