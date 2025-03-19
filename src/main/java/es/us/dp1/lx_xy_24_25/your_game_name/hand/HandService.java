package es.us.dp1.lx_xy_24_25.your_game_name.hand;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.lx_xy_24_25.your_game_name.tile.Tile;

@Service
public class HandService {

    @Autowired
    HandRepository hr;

    @Transactional(readOnly = true)
    public List<Hand> findAll() {
        Iterable<Hand> handsIterable = hr.findAll();
        List<Hand> hands = StreamSupport.stream(handsIterable.spliterator(), false).collect(Collectors.toList());
        return hands;
    }    

    @Transactional(readOnly = true)
    public Optional<Hand> findById(Integer id) {
        return hr.findById(id);
    }

    @Transactional
    public Hand save(Hand hand) {
        return hr.save(hand);
    }

    @Transactional
    public void deleteById(Integer id) {
        hr.deleteById(id);
    }

    @Transactional
    public void addTileToHand(Hand hand, Tile tile) {
        hand.getFichas().add(tile);
        hand.setNumFichas(hand.getFichas().size());
        hand.setStatus(!hand.getFichas().isEmpty());
        hr.save(hand);
    }

    @Transactional
    public void removeTileFromHand(Hand hand, Tile tile) {
        hand.getFichas().remove(tile);
        hand.setNumFichas(hand.getFichas().size());
        hand.setStatus(!hand.getFichas().isEmpty());
        hr.save(hand);
    }

    @Transactional
    public Hand findByPlayerUsername(String username) {
        return hr.findByPlayerUsername(username);
    }

}
