package es.us.dp1.lx_xy_24_25.your_game_name.tile;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TileRepository extends CrudRepository<Tile, Integer>{
    
    List<Tile> findAll();
    
   /* @Query("SELECT t FROM Tile t WHERE t.bolsa.id = ?1")
    List<Tile> findAllByBolsaId(Integer bolsaId);*/
}
