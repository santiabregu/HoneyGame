package es.us.dp1.lx_xy_24_25.your_game_name.player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {
     
    Optional<Player> findById(Integer id);

    @Query("SELECT p FROM Player p WHERE p.username = :username")
    Optional<Player> findByUsername(@Param("username") String username);
    
    @Query("SELECT p.friends FROM Player p WHERE p.username = :username")
    Optional<Set<Player>> findFriendsByUsername(@Param("username") String username);

    @Query("SELECT f FROM Player p JOIN p.friends f WHERE p.username = :username AND f.isOnline = true")
    Optional<Set<Player>> findOnlineFriendsByUsername(@Param("username") String username);

    
}