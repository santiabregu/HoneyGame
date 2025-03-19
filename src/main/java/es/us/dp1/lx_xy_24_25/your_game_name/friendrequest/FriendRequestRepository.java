package es.us.dp1.lx_xy_24_25.your_game_name.friendrequest;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;

@Repository
public interface FriendRequestRepository extends CrudRepository<FriendRequest, Integer> {

    List<FriendRequest> findAll();

    @Query("SELECT fr FROM FriendRequest fr WHERE fr.receiver.username = :receiverUsername AND fr.status = 'PENDING'")
    List<FriendRequest> findPendingRequestsByReceiverUsername(@Param("receiverUsername") String receiverUsername);

    @Query("SELECT fr FROM FriendRequest fr WHERE fr.sender = :sender AND fr.receiver = :receiver")
    Optional<FriendRequest> findBySenderAndReceiver(@Param("sender") Player sender, @Param("receiver") Player receiver);
}