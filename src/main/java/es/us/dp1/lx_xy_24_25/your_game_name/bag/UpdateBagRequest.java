package es.us.dp1.lx_xy_24_25.your_game_name.bag;

import java.util.List;

import es.us.dp1.lx_xy_24_25.your_game_name.tile.Tile;

public class UpdateBagRequest {

    private List<Tile> fichasRestantes;  // Asumiendo que fichasRestantes es una lista de String (ajusta según tu estructura)
    private int numFichas;

    // Getters y Setters
    public List<Tile> getFichasRestantes() {
        return fichasRestantes;
    }

    public void setFichasRestantes(List<Tile> fichasRestantes) {
        this.fichasRestantes = fichasRestantes;
    }

    public int getNumFichas() {
        return numFichas;
    }

    public void setNumFichas(int numFichas) {
        this.numFichas = numFichas;
    }
    
}
