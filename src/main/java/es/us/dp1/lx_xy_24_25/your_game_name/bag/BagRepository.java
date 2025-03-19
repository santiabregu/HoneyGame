package es.us.dp1.lx_xy_24_25.your_game_name.bag;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BagRepository extends CrudRepository<Bag, Integer>{
    @Query("SELECT b FROM Bag b WHERE b.tablero.id = ?1")
    Bag findBolsaByTableroId(Integer tableroId);
    
}
