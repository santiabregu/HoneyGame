package es.us.dp1.lx_xy_24_25.your_game_name.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/board")
public class BoardController {
    @Autowired
    BoardService bs;
    
}
