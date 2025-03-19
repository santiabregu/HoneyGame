package es.us.dp1.lx_xy_24_25.your_game_name.hand;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HandRepository extends CrudRepository<Hand, Integer>{

    @Query("SELECT h FROM Hand h WHERE h.player.username = ?1")
    Hand findByPlayerUsername(String username);

}
