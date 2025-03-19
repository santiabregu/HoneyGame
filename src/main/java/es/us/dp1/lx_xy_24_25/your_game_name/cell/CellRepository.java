package es.us.dp1.lx_xy_24_25.your_game_name.cell;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CellRepository extends CrudRepository<Cell, Integer>{
    
}
