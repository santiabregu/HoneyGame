package es.us.dp1.lx_xy_24_25.your_game_name.cell;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/cell")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Cell", description = "API for the management of Cell")
public class CellRestController {

    @Autowired
    CellService cs;

    @GetMapping("/all")
    public List<Cell> getAllCells() {
        return cs.getAllCells();
    }

    @GetMapping("/{id}")
    public Cell getCellById(@PathVariable int id) {
        return cs.getCellById(id);
    }

    @PutMapping("/{id}")
    public Cell updateCellStatus(@PathVariable int id, @RequestBody boolean status) {
        return cs.updateCellStatus(id, status);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Cell> updateCell(@PathVariable Integer id, @RequestBody Cell cell) {
        Cell updatedCell = cs.updateCell(id, cell);
        return ResponseEntity.ok(updatedCell);
    }
}
    

