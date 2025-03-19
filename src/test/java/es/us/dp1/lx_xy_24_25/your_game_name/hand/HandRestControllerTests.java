package es.us.dp1.lx_xy_24_25.your_game_name.hand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.us.dp1.lx_xy_24_25.your_game_name.board.Board;
import es.us.dp1.lx_xy_24_25.your_game_name.player.Player;
import es.us.dp1.lx_xy_24_25.your_game_name.tile.Tile;

@WebMvcTest(HandRestController.class)
@ExtendWith(MockitoExtension.class)
public class HandRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HandService handService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAllHands() throws Exception {
        when(handService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/hand"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());

        verify(handService).findAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetHandByPlayerUsername() throws Exception {
        Hand hand = new Hand();
        hand.setId(1);
        hand.setNumFichas(5);
        when(handService.findByPlayerUsername("JohnDoe")).thenReturn(hand);

        mockMvc.perform(get("/api/v1/hand/JohnDoe"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.numFichas").value(5));

        verify(handService).findByPlayerUsername("JohnDoe");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnNotFoundWhenHandByPlayerUsernameDoesNotExist() throws Exception {
        when(handService.findByPlayerUsername("JohnDoe")).thenReturn(null);

        mockMvc.perform(get("/api/v1/hand/JohnDoe"))
            .andExpect(status().isNotFound());

        verify(handService).findByPlayerUsername("JohnDoe");
    }

    @Test
    @WithMockUser
    void shouldUpdateNumTiles() throws Exception {
        Hand hand = new Hand();
        hand.setId(1);
        hand.setFichas(Collections.emptyList());
        when(handService.findById(1)).thenReturn(Optional.of(hand));
        when(handService.save(any(Hand.class))).thenReturn(hand);

        mockMvc.perform(put("/api/v1/hand/1/numFichas").with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.numFichas").value(0));

        verify(handService).findById(1);
        verify(handService).save(any(Hand.class));
    }

    @Test
    @WithMockUser
    void shouldReturnNotFoundWhenUpdatingNumTilesForNonExistentHand() throws Exception {
        when(handService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/hand/1/numFichas").with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(handService).findById(1);
        verify(handService, never()).save(any(Hand.class));
    }

    @Test
    @WithMockUser
    void shouldCreateHand() throws Exception {
        Hand hand = new Hand();
        hand.setId(1);
        when(handService.save(any(Hand.class))).thenReturn(hand);

        mockMvc.perform(post("/api/v1/hand")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(hand))
                .with(csrf()))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1));

        verify(handService).save(any(Hand.class));
    }

    @Test
    @WithMockUser
    void shouldUpdateHand() throws Exception {
        Hand hand = new Hand();
        hand.setId(1);
        Hand handDetails = new Hand();
        handDetails.setNumFichas(5);
        handDetails.setStatus(true);
        handDetails.setFichas(Collections.emptyList());
        handDetails.setPlayer(new Player());
        handDetails.setTablero(new Board());

        when(handService.findById(1)).thenReturn(Optional.of(hand));
        when(handService.save(any(Hand.class))).thenReturn(hand);

        mockMvc.perform(put("/api/v1/hand/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(handDetails))
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));

        verify(handService).findById(1);
        verify(handService).save(any(Hand.class));
    }

    @Test
    @WithMockUser
    void shouldReturnNotFoundWhenUpdatingNonExistentHand() throws Exception {
        Hand handDetails = new Hand();
        handDetails.setNumFichas(5);
        handDetails.setStatus(true);
        handDetails.setFichas(Collections.emptyList());
        handDetails.setPlayer(new Player());
        handDetails.setTablero(new Board());

        when(handService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/hand/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(handDetails))
                .with(csrf()))
            .andExpect(status().isNotFound());

        verify(handService).findById(1);
        verify(handService, never()).save(any(Hand.class));
    }

    @Test
    @WithMockUser
    void shouldDeleteHand() throws Exception {
        Hand hand = new Hand();
        hand.setId(1);
        when(handService.findById(1)).thenReturn(Optional.of(hand));

        mockMvc.perform(delete("/api/v1/hand/1")
                .with(csrf()))
            .andExpect(status().isNoContent());

        verify(handService).findById(1);
        verify(handService).deleteById(1);
    }

    @Test
    @WithMockUser
    void shouldReturnNotFoundWhenDeletingNonExistentHand() throws Exception {
        when(handService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/v1/hand/1")
                .with(csrf()))
            .andExpect(status().isNotFound());

        verify(handService).findById(1);
        verify(handService, never()).deleteById(1);
    }

    @Test
    @WithMockUser
    void shouldAddTileToHand() throws Exception {
        Hand hand = new Hand();
        hand.setId(1);
        Tile tile = new Tile();
        tile.setId(1);

        when(handService.findById(1)).thenReturn(Optional.of(hand));
        doNothing().when(handService).addTileToHand(any(Hand.class), any(Tile.class));

        mockMvc.perform(post("/api/v1/hand/1/addTile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(tile))
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));

        verify(handService).findById(1);
        verify(handService).addTileToHand(any(Hand.class), any(Tile.class));
    }

    @Test
    @WithMockUser
    void shouldReturnNotFoundWhenAddingTileToNonExistentHand() throws Exception {
        Tile tile = new Tile();
        when(handService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/v1/hand/1/addTile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(tile))
                .with(csrf()))
            .andExpect(status().isNotFound());

        verify(handService).findById(1);
        verify(handService, never()).addTileToHand(any(Hand.class), any(Tile.class));
    }


    @Test
    @WithMockUser
    void shouldRemoveTileFromHand() throws Exception {
        Hand hand = new Hand();
        hand.setId(1);
        Tile tile = new Tile();
        tile.setId(1);

        // Añadir el tile a la mano para que pueda ser removido
        hand.setFichas(new ArrayList<>(Collections.singletonList(tile)));

        when(handService.findById(1)).thenReturn(Optional.of(hand));
        doNothing().when(handService).removeTileFromHand(any(Hand.class), any(Tile.class));

        mockMvc.perform(post("/api/v1/hand/1/removeTile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(tile))
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));

        verify(handService).findById(1);

        // Capturar los argumentos pasados a removeTileFromHand
        ArgumentCaptor<Hand> handCaptor = ArgumentCaptor.forClass(Hand.class);
        ArgumentCaptor<Tile> tileCaptor = ArgumentCaptor.forClass(Tile.class);
        verify(handService).removeTileFromHand(handCaptor.capture(), tileCaptor.capture());

        // Verificar que los argumentos capturados son los esperados
        Hand capturedHand = handCaptor.getValue();
        Tile capturedTile = tileCaptor.getValue();
        assertEquals(hand.getId(), capturedHand.getId());
        assertEquals(tile.getId(), capturedTile.getId());
    }

    @Test
    @WithMockUser
    void shouldReturnNotFoundWhenRemovingTileFromNonExistentHand() throws Exception {
        Tile tile = new Tile();
        when(handService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/v1/hand/1/removeTile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(tile))
                .with(csrf()))
            .andExpect(status().isNotFound());

        verify(handService).findById(1);
        verify(handService, never()).removeTileFromHand(any(Hand.class), any(Tile.class));
    }
}