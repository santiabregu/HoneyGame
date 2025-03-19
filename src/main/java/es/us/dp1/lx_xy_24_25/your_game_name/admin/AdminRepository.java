package es.us.dp1.lx_xy_24_25.your_game_name.admin;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin,Integer>{
    
    Optional<Admin> findById(Integer id);

    @Query("SELECT a FROM Admin a WHERE a.username = :username")
    Optional<Player> findByUsername(@Param("username") String username);
    
}
