package es.us.dp1.lx_xy_24_25.your_game_name.hand;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.us.dp1.lx_xy_24_25.your_game_name.tile.Tile;

public class HandServiceTest {

    @Mock
    private HandRepository handRepository;

    @InjectMocks
    private HandService handService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldFindAll() {
        Hand hand1 = new Hand();
        Hand hand2 = new Hand();
        when(handRepository.findAll()).thenReturn(Arrays.asList(hand1, hand2));

        List<Hand> result = handService.findAll();

        assertEquals(2, result.size());
        verify(handRepository, times(1)).findAll();
    }

    @Test
    public void shouldFindById() {
        Hand hand = new Hand();
        when(handRepository.findById(1)).thenReturn(Optional.of(hand));

        Optional<Hand> result = handService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(hand, result.get());
        verify(handRepository, times(1)).findById(1);
    }

    @Test
    public void shouldSaveHand() {
        Hand hand = new Hand();
        when(handRepository.save(hand)).thenReturn(hand);

        Hand result = handService.save(hand);

        assertEquals(hand, result);
        verify(handRepository, times(1)).save(hand);
    }

    @Test
    public void shouldDeleteHandById() {
        doNothing().when(handRepository).deleteById(1);

        handService.deleteById(1);

        verify(handRepository, times(1)).deleteById(1);
    }




    @Test
    public void shouldFindByPlayerUsername() {
        Hand hand = new Hand();
        when(handRepository.findByPlayerUsername("player1")).thenReturn(hand);

        Hand result = handService.findByPlayerUsername("player1");

        assertEquals(hand, result);
        verify(handRepository, times(1)).findByPlayerUsername("player1");
    }
}
