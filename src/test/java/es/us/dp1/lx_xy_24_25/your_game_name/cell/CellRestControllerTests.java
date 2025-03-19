package es.us.dp1.lx_xy_24_25.your_game_name.cell;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import es.us.dp1.lx_xy_24_25.your_game_name.exceptions.ResourceNotFoundException;

import org.springframework.http.MediaType;

@WebMvcTest(CellRestController.class)
class CellRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CellService cellService;

    private List<Cell> cellList;
    private Cell cell1;
    private Cell cell2;


    @Autowired
	private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        cell1 = new Cell();
        cell1.setId(1);
        cell1.setPosition(1);
        cell1.setStatus(true);

        cell2 = new Cell();
        cell2.setId(2);
        cell2.setPosition(2);
        cell2.setStatus(false);

        cellList = Arrays.asList(cell1, cell2);
    }

    @Test
    @WithMockUser
    void shouldReturnAllCells() throws Exception {
        when(cellService.getAllCells()).thenReturn(cellList);

        mockMvc.perform(get("/api/v1/cell/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].position").value(1))
                .andExpect(jsonPath("$[0].status").value(true))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].position").value(2))
                .andExpect(jsonPath("$[1].status").value(false));
    }

    @Test
    @WithMockUser
    void shouldReturnCellById() throws Exception {
        when(cellService.getCellById(1)).thenReturn(cell1);

        mockMvc.perform(get("/api/v1/cell/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.position").value(1))
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    @WithMockUser
    void shouldUpdateCellStatus() throws Exception{
        cell1.setStatus(false);
        when(this.cellService.getCellById(1)).thenReturn(cell1);
        when(this.cellService.updateCellStatus(1, cell1.getStatus())).thenReturn(cell1);
        mockMvc.perform(put("/api/v1/cell/{id}", 1).with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(cell1.getStatus())))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.position").value(1))
        .andExpect(jsonPath("$.status").value(false));
    }

    @Test
    @WithMockUser
    void shouldNotGetCell() throws Exception{
        when(cellService.getCellById(3)).thenThrow(new ResourceNotFoundException("No existe la celda buscada"));

        mockMvc.perform(get("/api/v1/cell/{id}", 3))
                .andExpect(status().isNotFound());
    }
}
