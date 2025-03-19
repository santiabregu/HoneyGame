package es.us.dp1.lx_xy_24_25.your_game_name.stats;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

import es.us.dp1.lx_xy_24_25.your_game_name.model.NamedEntity;
import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Achievement extends NamedEntity {
    @NotBlank
    private String description;

    private String badgeImage;

    @Min(0)
    private double threshold;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    Metric metric;

    Boolean claimed;

    Boolean unlocked;

    public Achievement() {
        this.claimed = false; // Set default value
        this.unlocked = false; // Set default value
    }

    public String getActualDescription(){
        return description.replace("<THRESHOLD>",String.valueOf(threshold));
    }

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

}
