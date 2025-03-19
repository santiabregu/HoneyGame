package es.us.dp1.lx_xy_24_25.your_game_name.tile;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TileRestController.class)
class TileRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TileService tileService;

    private List<Tile> tileList;

    @BeforeEach
    public void setup() {
        Tile tile1 = new Tile();
        tile1.setId(1);
        tile1.setIsReversed(false);
        tile1.setColor(Color.RED);
        tile1.setBackColor(Color.BLUE);

        Tile tile2 = new Tile();
        tile2.setId(2);
        tile2.setIsReversed(true);
        tile2.setColor(Color.YELLOW);
        tile2.setBackColor(Color.GREEN);

        tileList = Arrays.asList(tile1, tile2);
    }

    @Test
    @WithMockUser
    void shouldReturnAllTiles() throws Exception {
        when(tileService.getAllTiles()).thenReturn(tileList);

        mockMvc.perform(get("/api/v1/tile/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].isReversed").value(false))
                .andExpect(jsonPath("$[0].color").value("RED"))
                .andExpect(jsonPath("$[0].backColor").value("BLUE"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].isReversed").value(true))
                .andExpect(jsonPath("$[1].color").value("YELLOW"))
                .andExpect(jsonPath("$[1].backColor").value("GREEN"));
    }

    @Test
    @WithMockUser
    void shouldReturnEmptyListWhenNoTilesExist() throws Exception {
        when(tileService.getAllTiles()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/tile/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}

