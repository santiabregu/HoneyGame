package es.us.dp1.lx_xy_24_25.your_game_name.bag;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.us.dp1.lx_xy_24_25.your_game_name.board.Board;
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
public class Bag extends BaseEntity{

    Integer numFichas;

    @OneToOne
    @JoinColumn(name = "tablero_id")
    @JsonIgnore
    Board tablero;

    @OneToMany(mappedBy = "bag", cascade = CascadeType.ALL)
    List<Tile> fichasRestantes;
    
}
