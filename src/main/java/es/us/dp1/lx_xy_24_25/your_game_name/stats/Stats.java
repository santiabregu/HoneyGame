package es.us.dp1.lx_xy_24_25.your_game_name.stats;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.boot.context.properties.bind.DefaultValue;

import es.us.dp1.lx_xy_24_25.your_game_name.model.BaseEntity;
import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder.Default;

@Getter
@Setter
@Entity
public class Stats extends BaseEntity{

    @NotNull
    @Min(0)
    Integer totalPoints;

    @NotNull
    @Min(0)
    Integer playedGames;

    @NotNull    
    @Min(0)
    Integer wonGames;

    @OneToOne
    @JoinColumn(name = "player_id")
	@NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Player player;

    @Min(0)
    Integer BronzeMedals;

    @Min(0)
    Integer SilverMedals;

    @Min(0)
    Integer GoldMedals;

    @Min(0)
    Integer PlatinumMedals;
    
}
