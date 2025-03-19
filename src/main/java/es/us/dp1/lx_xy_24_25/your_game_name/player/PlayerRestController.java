package es.us.dp1.lx_xy_24_25.your_game_name.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.us.dp1.lx_xy_24_25.your_game_name.auth.payload.response.MessageResponse;
import es.us.dp1.lx_xy_24_25.your_game_name.exceptions.ResourceNotFoundException;
import es.us.dp1.lx_xy_24_25.your_game_name.friendrequest.FriendRequest;
import es.us.dp1.lx_xy_24_25.your_game_name.user.User;
import es.us.dp1.lx_xy_24_25.your_game_name.user.UserService;
import es.us.dp1.lx_xy_24_25.your_game_name.util.RestPreconditions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional; 
import java.util.Set;

@RestController
@RequestMapping("/api/v1/players")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Player", description = "API for the management of Player")
public class PlayerRestController {

    @Autowired
    private PlayerService playerService;

     @Autowired
    private UserService userService;



    @GetMapping
    public ResponseEntity<List<Player>> findAll(){
        List<Player> res;
        res = (List<Player>) playerService.findAll();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/currentPlayer")
    public ResponseEntity<?> getCurrentPlayer() {
    try {
        User currentUser = userService.findCurrentUser();
        System.out.println("Current user: " + currentUser); // Debugging statement
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    } catch (ResourceNotFoundException e) {
        System.err.println("ResourceNotFoundException: " + e.getMessage()); // Debugging statement
        return new ResponseEntity<>(Collections.singletonMap("message", "ha salio malamente"), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
        System.err.println("Exception: " + e.getMessage()); // Debugging statement
        return new ResponseEntity<>(Collections.singletonMap("message", "ha salio malamente"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    @GetMapping(value="{username}")
    public ResponseEntity<Player> findByUsername(@PathVariable("username") String username){
        return new ResponseEntity<>(playerService.findPlayer(username), HttpStatus.OK);
    }

    @GetMapping("/id/{playerId}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("playerId") Integer playerId){
        return new ResponseEntity<>(playerService.findById(playerId),HttpStatus.OK);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Player> createPlayer(@RequestBody @Valid Player p){
        p.getUser().getAuthority().setId(2);
        p.getUser().getAuthority().setAuthority("PLAYER");
        p.getUser().setUsername(p.getUsername());
        Player player = playerService.savePlayer(p);
        return new ResponseEntity<>(player,HttpStatus.CREATED);
    }

    @PutMapping("/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Player> updatePlayer(@PathVariable("playerId") Integer playerId, @RequestBody @Valid Player player){
        RestPreconditions.checkNotNull(playerService.findById(playerId), "Player", "ID", playerId);
        player.getUser().getAuthority().setId(2);
        player.getUser().getAuthority().setAuthority("PLAYER");
        return new ResponseEntity<>(playerService.updatePlayer(player, playerId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") Integer playerId){
        RestPreconditions.checkNotNull(playerService.findById(playerId), "Player", "ID", playerId);
        playerService.deletePlayer(playerId);
        return new ResponseEntity<>(new MessageResponse("Player borrado!"), HttpStatus.OK);
    }

    @PostMapping("{username}/send-friend-request/{receiverUsername}")
    public ResponseEntity<Void> sendFriendRequest(@PathVariable("username") String username, @PathVariable String receiverUsername) {
            playerService.sendFriendRequest(username, receiverUsername);
            return ResponseEntity.ok().build();
        }
    
    @PostMapping("{username}/respond-friend-request/{requestId}")
    public ResponseEntity<Void> respondToFriendRequest(@PathVariable String username, @PathVariable Integer requestId, @RequestBody Map<String, String> body) {
        String statusStr = body.get("status");
        FriendRequest.RequestStatus status = FriendRequest.RequestStatus.valueOf(statusStr);
        playerService.respondToFriendRequest(requestId, status);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{username}/friends")
    public ResponseEntity<Set<Player>> getFriends(@PathVariable("username") String username) {
        Optional<Set<Player>> friends = playerService.findFriendsByUsername(username);
        if (friends.isPresent()) {
            return new ResponseEntity<>(friends.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{username}/pending-requests")
    public ResponseEntity<Set<FriendRequest>> getPendingRequests(@PathVariable("username") String username) {
        List<FriendRequest> pendingRequests = playerService.findAllPendingRequests(username);
        if (!pendingRequests.isEmpty()) {
            return new ResponseEntity<>(new HashSet<>(pendingRequests), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.emptySet(), HttpStatus.OK); // Return 200 with empty set
        }
    }

    @GetMapping("/{username}/onlineFriends")
    public ResponseEntity<Set<Player>> getOnlineFriends(@PathVariable("username") String username) {
        Optional<Set<Player>> onlineFriends = playerService.findOnlineFriendsByUsername(username);
        if (onlineFriends.isPresent()) {
            return new ResponseEntity<>(onlineFriends.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

     /**
     * Endpoint para obtener jugadores con paginación
     * URL: /api/v1/players/paginated?page={page}&size={size}
     */
    @GetMapping("/paginated")
    public ResponseEntity<Map<String, Object>> getPaginatedPlayers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Obtener los jugadores paginados y el total de páginas
        List<playerDTOPaginated> players = playerService.findPlayersPaginated(page, size);
        int totalPages = playerService.getTotalPages(size);

        // Construir la respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("players", players);
        response.put("currentPage", page);
        response.put("totalPages", totalPages);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{username}/remove-friend/{friendUsername}")
        public ResponseEntity<Void> removeFriend(
            @PathVariable String username, 
            @PathVariable String friendUsername) {
            playerService.removeFriend(username, friendUsername);
        return ResponseEntity.noContent().build(); // Devuelve un 204 si todo va bien
    }

    @PatchMapping("/{username}/points")
    public ResponseEntity<Player> updatePlayerPoints(
            @PathVariable String username, 
            @RequestBody Map<String, Integer> pointsUpdate) {
        int newPoints = pointsUpdate.get("points");
        Player updatedPlayer = playerService.updatePlayerPoints(username, newPoints);
        return ResponseEntity.ok(updatedPlayer);
    }

}