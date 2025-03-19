package es.us.dp1.lx_xy_24_25.your_game_name.board;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import es.us.dp1.lx_xy_24_25.your_game_name.bag.Bag;
import es.us.dp1.lx_xy_24_25.your_game_name.cell.Cell;
import es.us.dp1.lx_xy_24_25.your_game_name.game.Game;
import es.us.dp1.lx_xy_24_25.your_game_name.hand.Hand;
import es.us.dp1.lx_xy_24_25.your_game_name.model.BaseEntity;
import es.us.dp1.lx_xy_24_25.your_game_name.tile.Tile;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Board extends BaseEntity{
    
    @JsonBackReference
    @OneToOne(mappedBy = "tablero")  
    Game partida;
    
    @OneToMany
    List<Cell> cell;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bolsa_id")
    Bag bolsa;

    @OneToMany 
    List<Hand> manos;

    @OneToMany
    List<Tile> fichasColocadas; 
    
    @OneToMany
    List<Tile> fichasSinColocar; 
}
