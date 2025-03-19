package es.us.dp1.lx_xy_24_25.your_game_name.friendrequest;

import es.us.dp1.lx_xy_24_25.your_game_name.model.BaseEntity;
import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "friend_requests")
public class FriendRequest extends BaseEntity {

    @ManyToOne 
    @JoinColumn(name = "sender")
    private Player sender;

    @ManyToOne
    @JoinColumn(name = "receiver")
    private Player receiver;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    public enum RequestStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}