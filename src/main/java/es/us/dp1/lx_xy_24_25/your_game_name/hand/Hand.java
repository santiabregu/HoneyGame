package es.us.dp1.lx_xy_24_25.your_game_name.hand;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import es.us.dp1.lx_xy_24_25.your_game_name.board.Board;
import es.us.dp1.lx_xy_24_25.your_game_name.model.BaseEntity;
import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;
import es.us.dp1.lx_xy_24_25.your_game_name.tile.Tile;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Hand extends BaseEntity{
    Integer numFichas;

    Boolean status;

    @OneToMany 
    List<Tile> fichas; 

    @OneToOne 
    @JsonBackReference 
    Player player; 

    @ManyToOne 
    @JsonIgnore
    Board tablero;

}
