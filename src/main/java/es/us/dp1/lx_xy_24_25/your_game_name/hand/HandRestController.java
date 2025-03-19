package es.us.dp1.lx_xy_24_25.your_game_name.hand;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.us.dp1.lx_xy_24_25.your_game_name.tile.Tile;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/hand")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Hand", description = "API for the management of Hand")
public class HandRestController {

    @Autowired
    HandService handService;

    @GetMapping
    public ResponseEntity<List<Hand>> getAllHands() {
        List<Hand> hands = handService.findAll();
        return new ResponseEntity<>(hands, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Hand> getHandByPlayerUsername(@PathVariable String username){
        Hand hand = handService.findByPlayerUsername(username);
        if(hand != null){
            return ResponseEntity.ok(hand);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/numFichas")
    public ResponseEntity<Hand> updateNumTiles(@PathVariable Integer id){
        Hand hand = handService.findById(id).orElse(null);
        if(hand==null){
            return ResponseEntity.notFound().build();
        }
        hand.setNumFichas(hand.getFichas().size());
        handService.save(hand);

        return ResponseEntity.ok(hand);
    }

    @PostMapping
    public ResponseEntity<Hand> createHand(@RequestBody Hand hand) {
        Hand createdHand = handService.save(hand);
        return new ResponseEntity<>(createdHand, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hand> updateHand(@PathVariable int id, @RequestBody Hand handDetails) {
        Optional<Hand> hand = handService.findById(id);

        if (hand.isPresent()) {
            Hand updatedHand = hand.get();
            updatedHand.setNumFichas(handDetails.getNumFichas());
            updatedHand.setStatus(handDetails.getStatus());
            updatedHand.setFichas(handDetails.getFichas());
            updatedHand.setPlayer(handDetails.getPlayer());
            updatedHand.setTablero(handDetails.getTablero());
            handService.save(updatedHand);
            return new ResponseEntity<>(updatedHand, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHand(@PathVariable int id) {
        Optional<Hand> hand = handService.findById(id);

        if (hand.isPresent()) {
            handService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/addTile")
    public ResponseEntity<Hand> addTileToHand(@PathVariable int id, @RequestBody Tile tile) {
        Optional<Hand> hand = handService.findById(id);

        if (hand.isPresent()) {
            Hand existingHand = hand.get();
            handService.addTileToHand(existingHand, tile);
            return new ResponseEntity<>(existingHand, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/removeTile")
    public ResponseEntity<Hand> removeTileFromHand(@PathVariable int id, @RequestBody Tile tile) {
        Optional<Hand> hand = handService.findById(id);

        if (hand.isPresent()) {
            Hand existingHand = hand.get();
            handService.removeTileFromHand(existingHand, tile);
            return new ResponseEntity<>(existingHand, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
