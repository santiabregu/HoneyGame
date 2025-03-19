package es.us.dp1.lx_xy_24_25.your_game_name.board;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends CrudRepository<Board, Integer>{
    
}
