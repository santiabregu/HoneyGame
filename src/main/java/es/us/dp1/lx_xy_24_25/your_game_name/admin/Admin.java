package es.us.dp1.lx_xy_24_25.your_game_name.admin;

import es.us.dp1.lx_xy_24_25.your_game_name.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
 
@Getter
@Setter
@Entity
@Table(name = "admin")
public class Admin extends User{
    
}
