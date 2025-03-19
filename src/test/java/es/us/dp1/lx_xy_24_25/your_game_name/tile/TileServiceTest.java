package es.us.dp1.lx_xy_24_25.your_game_name.tile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TileServiceTest {

    @Autowired
    private TileService tileService;

    @MockBean
    private TileRepository tileRepository;

    private Tile tile;

    @BeforeEach
    void setup() {
        tile = new Tile();
        tile.setId(1);
        tile.setColor(Color.RED);
        tile.setBackColor(Color.BLUE);
    }

    @Test
    void shouldGetAllTiles() {
        Tile tile2 = new Tile();
        tile2.setId(2);
        tile2.setColor(Color.GREEN);
        tile2.setBackColor(Color.YELLOW);

        when(this.tileRepository.findAll()).thenReturn(List.of(tile, tile2));

        List<Tile> tiles = this.tileService.getAllTiles();
        assertEquals(2, tiles.size());
    }

    @Test
    void shouldCreateTile() {
        when(this.tileRepository.save(any(Tile.class))).thenReturn(tile);

        Tile savedTile = this.tileService.crearFicha(tile);
        assertEquals(tile.getColor(), savedTile.getColor());
    }

    @Test
    void shouldReturnListOfColors() {
        List<Color> colors = this.tileService.listaColores();
        assertEquals(6, colors.size());
    }

    @Test
    void shouldCreateBolsa() {
        when(this.tileRepository.save(any(Tile.class))).thenReturn(tile);

        List<Tile> bolsa = this.tileService.crearBolsa(tile);
        assertEquals(72, bolsa.size()); // 6 colors * 6 back colors * 2 (each combination added twice)
    }

    @Test
    void shouldInitializeTablero() {
        List<Tile> tablero = this.tileService.inicializaTablero(tile);
        assertEquals(19, tablero.size());
        assertEquals(null, tablero.get(0));
    }

    @Test
    void shouldPlaceInitialTiles() {
        when(this.tileRepository.save(any(Tile.class))).thenReturn(tile);

        List<Tile> initialTiles = this.tileService.colocarFichasInciales(tile);
        assertEquals(19, initialTiles.size());
    }
}