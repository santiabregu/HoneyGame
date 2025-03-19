package es.us.dp1.lx_xy_24_25.your_game_name.player;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.lx_xy_24_25.your_game_name.exceptions.ResourceNotFoundException;
import es.us.dp1.lx_xy_24_25.your_game_name.friendrequest.FriendRequest;
import es.us.dp1.lx_xy_24_25.your_game_name.friendrequest.FriendRequestRepository;
import jakarta.validation.Valid;

import java.util.HashSet;
import java.util.List;
import java.util.Optional; // Add this import
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private PlayerRepository playerRepository;
    protected FriendRequestRepository friendRequestRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, FriendRequestRepository friendRequestRepository) {
        this.playerRepository= playerRepository;
        this.friendRequestRepository = friendRequestRepository;
    }

    @Transactional(readOnly = true)
    public Player getCurrentPlayer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new ResourceNotFoundException("Nobody authenticated!");
        }
        return playerRepository.findByUsername(auth.getName()).orElseThrow(()-> new ResourceNotFoundException("player no encontrado."));
    }

    @Transactional(readOnly = true)
    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new ResourceNotFoundException("Nobody authenticated!");
        }
        return auth.getName();
    }

    @Transactional(readOnly = true)
    public Player findById(Integer id){
        return playerRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Player> findAll() {
        return (List<Player>) playerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Player findPlayer(String username) {
        return playerRepository.findByUsername(username)
        .orElseThrow(() -> new ResourceNotFoundException("no se ha encontrado el player"));
 
    }


    @Transactional
    public Player savePlayer(Player player) throws DataAccessException {
        playerRepository.save(player);
        return player;
    }

    public Optional<Set<Player>> findFriendsByUsername(String username) {
        Player player = playerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Player not found"));
        return Optional.of(player.getFriends());
    }

    public Optional<Set<Player>> findOnlineFriendsByUsername(String username) {

        return playerRepository.findOnlineFriendsByUsername(username);
    }


    @Transactional
    public Player updatePlayer(@Valid Player player, Integer playerId){
        Player playerParaActualizar = playerRepository.findById(playerId).orElseThrow(()-> new ResourceNotFoundException("no se ha encontrado el player"));
        BeanUtils.copyProperties(player, playerParaActualizar, "id");
        return playerRepository.save(playerParaActualizar);
    }

    @Transactional
    public void deletePlayer(Integer id){
        Player paraBorrar = playerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("no se ha encontrado el player"));
        paraBorrar.getFriends().forEach(f-> f.getFriends().remove(paraBorrar));
        paraBorrar.setFriends(new HashSet<>());
        playerRepository.delete(paraBorrar); 
    }


    @Transactional
    public void sendFriendRequest(String senderUsername, String receiverUsername) {
        Player sender = playerRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Player", "username", senderUsername));
        Player receiver = playerRepository.findByUsername(receiverUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Player", "username", receiverUsername));
        // Check for existing friend request
        Optional<FriendRequest> existingRequest = friendRequestRepository.findBySenderAndReceiver(sender, receiver);
        if (existingRequest.isPresent()) {
            throw new IllegalArgumentException("Friend request already exists");
        }
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequest.setStatus(FriendRequest.RequestStatus.PENDING);
        friendRequestRepository.save(friendRequest);
    }

    @Transactional
    public void respondToFriendRequest(Integer requestId, FriendRequest.RequestStatus status) {
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("FriendRequest:", "id", requestId));
        if (request.getStatus() == FriendRequest.RequestStatus.ACCEPTED) {
            throw new IllegalArgumentException("Friend request has already been accepted");
        }
        if (status == FriendRequest.RequestStatus.ACCEPTED) {
            Player sender = request.getSender();
            Player receiver = request.getReceiver();
            sender.getFriends().add(receiver);
            receiver.getFriends().add(sender);
            playerRepository.save(sender);
            playerRepository.save(receiver);
        }
        request.setStatus(status);
        friendRequestRepository.save(request);
    }

    public List<PlayerDTO> getFriends(String username) {
        Player player = playerRepository.findByUsername(username).orElse(null);
        return player.getFriends().stream()
                .map(friend -> new PlayerDTO(
                        friend.getFirstname(),
                        friend.getLastname(),
                        friend.getUsername(),
                        friend.getUser().getProfilePhoto()))
                .collect(Collectors.toList());
    }

    // Pending friend requests
    @Transactional(readOnly = true)
    public List<FriendRequest> findAllPendingRequests(String username) {
        return friendRequestRepository.findPendingRequestsByReceiverUsername(username);
    }

    @Transactional
    public List<playerDTOPaginated> findPlayersPaginated(int page, int size) {
        List<Player> allPlayers = (List<Player>) playerRepository.findAll();

        // Realizar la paginación manual
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, allPlayers.size());

        if (fromIndex > allPlayers.size()) {
            return List.of(); // Si la página solicitada no tiene datos
        }

        return allPlayers.subList(fromIndex, toIndex).stream()
            .map(player -> new playerDTOPaginated(
                    player.getId(),
                    player.getFirstname(),
                    player.getLastname(),
                    player.getUsername(),
                    player.getUser().getProfilePhoto()
            ))
            .collect(Collectors.toList());
    }

    @Transactional
    public int getTotalPages(int size) {
        long totalPlayers = playerRepository.count();
        return (int) Math.ceil((double) totalPlayers / size);
    }

    @Transactional
    public void removeFriend(String username, String friendUsername) {
        Player player = playerRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Player", "username", username));

        Player friend = playerRepository.findByUsername(friendUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Player", "username", friendUsername));

        player.getFriends().remove(friend); // Elimina al amigo de la lista del usuario
        friend.getFriends().remove(player); // Elimina al usuario de la lista del amigo

        playerRepository.save(player); // Guarda los cambios
        playerRepository.save(friend); // Guarda los cambios    
    }

    @Transactional
    public Player updatePlayerPoints(String username, int newPoints) {
        Player player = playerRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Player", "username", username));
        player.setPoints(newPoints);
        return playerRepository.save(player);
    }

}