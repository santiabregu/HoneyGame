package es.us.dp1.lx_xy_24_25.your_game_name.tile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/tile")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Tile", description = "API for the management of Tiles")
public class TileRestController {
    
    @Autowired
    TileService ts;
 
    @GetMapping("/all")
    public List<Tile> getAllTiles() {
        return ts.getAllTiles();
    }
    
    /*@GetMapping("/{bolsaId}")
    public List<Tile> getAllTilesFromBag(@PathVariable("bolsaId") Integer bolsaId) {
        return ts.getAllTilesFromBag(bolsaId);
    }*/

}
