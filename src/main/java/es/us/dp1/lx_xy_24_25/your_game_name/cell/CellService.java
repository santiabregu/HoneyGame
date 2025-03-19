package es.us.dp1.lx_xy_24_25.your_game_name.cell;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.lx_xy_24_25.your_game_name.exceptions.ResourceNotFoundException;
import jakarta.annotation.Resource;

@Service
public class CellService {
    
    @Autowired
    CellRepository cr;

    public CellService(CellRepository cr) {
        this.cr = cr;
    }

    @Transactional(readOnly = true)
    public List<Cell> getAllCells() {
        return (List<Cell>) cr.findAll();
        /*List<Cell> casillas = new ArrayList<Cell>();
        cr.findAll().forEach(casillas::add);
        return casillas;*/
    }

    @Transactional(readOnly = true)
    public Cell getCellById(Integer id) throws ResourceNotFoundException{
        return cr.findById(id).orElseThrow(()-> new ResourceNotFoundException("No existe la celda buscada"));
    }

    @Transactional
    public Cell updateCellStatus(Integer id, Boolean status) throws ResourceNotFoundException{
        Cell cell = cr.findById(id).orElse(null);
        if (cell != null) {
            cell.setStatus(status);
            return cr.save(cell);
        }
        throw new ResourceNotFoundException("No existe una celda con ese ID");
    }

    @Transactional
    public Cell updateCell(Integer id, Cell cell) {
        Cell existingCell = cr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cell not found"));
        existingCell.setTile(cell.getTile());
        existingCell.setStatus(cell.getStatus());
        return cr.save(existingCell);
    }
}
