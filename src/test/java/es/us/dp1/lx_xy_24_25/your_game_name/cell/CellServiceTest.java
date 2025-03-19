package es.us.dp1.lx_xy_24_25.your_game_name.cell;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import es.us.dp1.lx_xy_24_25.your_game_name.user.UserService;

@SpringBootTest
public class CellServiceTest {

    @Autowired
    private CellService cellService;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser()
    public void shouldReturnAllCells() {
        List<Cell> cells = cellService.getAllCells();
        assertEquals(19, cells.size());
    }
}