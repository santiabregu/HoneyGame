package es.us.dp1.lx_xy_24_25.your_game_name.bag;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import es.us.dp1.lx_xy_24_25.your_game_name.tile.Tile;
import es.us.dp1.lx_xy_24_25.your_game_name.tile.TileService;

import es.us.dp1.lx_xy_24_25.your_game_name.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/bag")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Bag", description = "API for the management of Bag")
public class BagRestController {

    BagService bs;

    TileService ts;

    @Autowired
    public BagRestController(BagService bs, TileService ts) {
        this.bs = bs;
        this.ts = ts;
    }

    @GetMapping("/{tableroId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Bag getBolsaByTableroId(@PathVariable Integer tableroId) {
        Bag bolsa = bs.findByTableroId(tableroId);
        if (bolsa == null) {
            throw new ResourceNotFoundException("No se ha encontrado ese tablero.");
        }
        return bolsa;
    }

    /*@PatchMapping("/{id}")
    public ResponseEntity<Void> updateBag(@PathVariable("id") Integer id) {

        Bag bolsa = bs.getBolsaById(id);
        if (bolsa == null) {
            return ResponseEntity.notFound().build(); // Bolsa no encontrada
        }

        //bolsa.setNumFichas(updateRequest.getNumFichas());

        bs.save(bolsa);

        return ResponseEntity.noContent().build(); // Respuesta de éxito (sin contenido)
    }
    */

    @PatchMapping("/{tableroId}/{tileId}")
    public ResponseEntity<Bag> deleteTileFromBag (@PathVariable("tableroId") Integer tableroId, @PathVariable("tileId") Integer tileId) {
        System.out.println("buscando tablero con id" + tableroId);
        Bag bolsa = bs.findByTableroId(tableroId);
        if (bolsa == null) {
            throw new ResourceNotFoundException("No se ha encontrado ese tablero.");
        }
        System.out.println("tablero encontrado. Buscando ficha con id" + tileId);
        Tile ficha = ts.getTileById(tileId);
        if (ficha == null) {
            throw new ResourceNotFoundException("No se ha encontrado esa ficha.");
        }
        System.out.println("ficha encontrada. Eliminando ficha de la bolsa");
        List<Tile> fichasAct = bolsa.getFichasRestantes().stream().filter(f -> f.getId() != tileId).collect(Collectors.toList());
        bolsa.setFichasRestantes(fichasAct);
        ficha.setBag(null);
        bolsa.setNumFichas(bolsa.getFichasRestantes().size());
        System.out.println("ficha eliminada de la bolsa. Guardando cambios");
        bs.save(bolsa);
        return new ResponseEntity<>(bolsa, HttpStatus.OK);
    }

}
