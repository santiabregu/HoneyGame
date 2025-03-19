package es.us.dp1.lx_xy_24_25.your_game_name.player;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import es.us.dp1.lx_xy_24_25.your_game_name.game.Game;
import es.us.dp1.lx_xy_24_25.your_game_name.hand.Hand;

import es.us.dp1.lx_xy_24_25.your_game_name.model.BaseEntity;
import es.us.dp1.lx_xy_24_25.your_game_name.stats.Achievement;
import es.us.dp1.lx_xy_24_25.your_game_name.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="players")
public class Player extends BaseEntity {

    private String firstname;
    @Column(unique= true)
    private String username;
    private String lastname;

    private Integer points;

    @ManyToMany
    @JoinTable(
        name = "player_friends",
        joinColumns = @JoinColumn(name = "player_id"), 
        inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("friends") 
    private Set<Player> friends = new HashSet<>();

    @NotNull
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne 
    @JoinColumn(name = "game_id")
    @JsonBackReference
    Game partida;

    @OneToOne 
    @JsonIgnore
    Hand mano;

    Boolean isOnline;

    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "creador")
    List<Game> createdMatches;*/

    public void addFriend(Player friend) {
        this.friends.add(friend);
        friend.getFriends().add(this);
    }

    public void removeFriend(Player friend) {
        this.friends.remove(friend);
        friend.getFriends().remove(this);
    }

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Achievement> achievements = new HashSet<>();

    public Player() {
        this.isOnline = false;
    }
}