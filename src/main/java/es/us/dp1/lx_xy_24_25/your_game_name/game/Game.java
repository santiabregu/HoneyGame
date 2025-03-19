package es.us.dp1.lx_xy_24_25.your_game_name.game;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import es.us.dp1.lx_xy_24_25.your_game_name.board.Board;
import es.us.dp1.lx_xy_24_25.your_game_name.model.BaseEntity;
import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Game extends BaseEntity{

    @NotNull
    Integer turn;

    LocalDateTime start;
    LocalDateTime finish;

    @NotNull
    @Enumerated(EnumType.STRING)
    GameModes gamemode;

    @NotNull
    @Enumerated(EnumType.STRING)
    GameStatus gameStatus;

    @OneToMany(mappedBy = "partida", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<Player> jugadores;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tablero_id") 
    @JsonManagedReference
    Board tablero;

    Integer codigoDePartida;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creador_id", nullable = false)
    Player creador;
    
}
