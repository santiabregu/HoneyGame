package es.us.dp1.lx_xy_24_25.your_game_name.stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;


public interface StatsRepository extends CrudRepository<Stats, Integer>{
 
   public List<Stats> findAll();

   @Query("SELECT s FROM Stats s WHERE s.player.username = :username")
    Optional<Stats> findStatsByUsername(@Param("username") String username);
   
    
    @Query("SELECT s FROM Stats s ORDER BY s.totalPoints DESC")
    List<Stats> findAllOrderedByTotalPoints();


}
