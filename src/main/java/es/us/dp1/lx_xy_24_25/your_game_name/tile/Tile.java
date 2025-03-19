package es.us.dp1.lx_xy_24_25.your_game_name.tile;

import es.us.dp1.lx_xy_24_25.your_game_name.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.us.dp1.lx_xy_24_25.your_game_name.bag.Bag;
import es.us.dp1.lx_xy_24_25.your_game_name.board.Board;
import es.us.dp1.lx_xy_24_25.your_game_name.cell.Cell;
import es.us.dp1.lx_xy_24_25.your_game_name.hand.Hand;

@Getter
@Setter
@Entity
public class Tile extends BaseEntity {
    Boolean isReversed;

    @Enumerated(EnumType.STRING)
    @NotNull
    Color color;

    @Enumerated(EnumType.STRING)
    @NotNull
    Color backColor;

    @OneToOne
    Cell casilla;

    @ManyToOne
    @JoinColumn(name = "bag_id")
    @JsonIgnore
    Bag bag;

    @ManyToOne
    Board tablero;

    @ManyToOne
    Hand mano;
}
