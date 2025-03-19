package es.us.dp1.lx_xy_24_25.your_game_name.stats;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Integer>{
    
    List<Achievement> findAll();
    
    public Achievement findByName(String name);

    @Query("SELECT a FROM Achievement a WHERE a.player.username = :username")
    Optional<List<Achievement>> findAchievementsByUsername(@Param("username") String username);

    @Query("SELECT a FROM Achievement a WHERE a.player.username = :username AND a.id = :id")
    Optional<Achievement> findSpecificAchievementByUsername(@Param("username") String username, @Param("id") int id);
}

