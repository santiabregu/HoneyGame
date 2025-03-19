package es.us.dp1.lx_xy_24_25.your_game_name.cell;

import es.us.dp1.lx_xy_24_25.your_game_name.board.Board;
import es.us.dp1.lx_xy_24_25.your_game_name.model.BaseEntity;
import es.us.dp1.lx_xy_24_25.your_game_name.tile.Tile;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cell extends BaseEntity{
    
    @OneToOne(optional=true)
    Tile tile;

    Integer position;
    
    // true = llena, false = vacía
    Boolean status; 

    @ManyToOne
    Board tablero;
    
}
