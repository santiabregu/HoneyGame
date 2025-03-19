package es.us.dp1.lx_xy_24_25.your_game_name.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.us.dp1.lx_xy_24_25.your_game_name.bag.Bag;
import es.us.dp1.lx_xy_24_25.your_game_name.bag.BagRepository;
import es.us.dp1.lx_xy_24_25.your_game_name.tile.Tile;
import es.us.dp1.lx_xy_24_25.your_game_name.tile.TileRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {

    @Autowired
    BoardRepository br;

    @Autowired
    BagRepository bagR;

    @Autowired
    TileRepository tr;

    @Autowired 
    public BoardService(BoardRepository br, BagRepository bagR, TileRepository tr) {
        this.br = br;
        this.bagR = bagR;
        this.tr = tr;
    }

    @Transactional()
    public Board createBoard(Board board) {
        Board savedBoard = br.save(board);
        Bag bolsa = new Bag();
        bolsa.setTablero(savedBoard); 
        bolsa.setNumFichas(tr.findAll().size());
        List<Tile> allTiles = new ArrayList<>(tr.findAll());
        allTiles.forEach(tile -> {
            tile.setBag(bolsa);
        });
        bolsa.setFichasRestantes(allTiles);

        Bag savedBolsa = bagR.save(bolsa);
        savedBoard.setBolsa(savedBolsa);
        return br.save(savedBoard);
    }

    @Transactional()
    public Board save(Board board) {
        return br.save(board);
    }

}
