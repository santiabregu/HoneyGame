package es.us.dp1.lx_xy_24_25.your_game_name.tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TileService {

    @Autowired
    TileRepository tr;

    public TileService(TileRepository tr){
        this.tr=tr;
    }

    @Transactional(readOnly = true)
    public List<Tile> getAllTiles(){
        return tr.findAll();
    }

    @Transactional(readOnly = true)
    public Tile getTileById(Integer id){
        return tr.findById(id).orElse(null);
    }

    //Crear ficha en la db
    public Tile crearFicha(Tile tile) {
        return tr.save(tile);
    }

    public List<Color> listaColores() {

        List<Color> colores = new ArrayList<>();
        colores.add(Color.RED);
        colores.add(Color.BLUE);
        colores.add(Color.PURPLE);
        colores.add(Color.YELLOW);
        colores.add(Color.GREEN);
        colores.add(Color.ORANGE);

        return colores;
    }

//--------------------Crear bolsa------------------------
    public List<Tile> crearBolsa(Tile tile) {

        List<Tile> listaFichas = new ArrayList<Tile>();
        // Creo una lista de colores para poder crear todas las fichas, la bolsa
        List<Color> colores = listaColores();

        //Para añadir tantas fichas como combinaciones posibles de colores FRONT y BACK
        //ASK si ya estan creadas las fichas en la db, ¿por qué se crean de nuevo?
        for (Color c : colores) { 
            tile.setColor(c);
            for (Color cb : colores) {
                tile.setBackColor(cb);
                listaFichas.add(crearFicha(tile));
                listaFichas.add(crearFicha(tile));//ASK ¿Por qué se añade dos veces?
                // FIX
            }
        }
        
        // Mezcla aleatoriamente la lista antes de devolverla
        Collections.shuffle(listaFichas);

        return listaFichas;
    }

     
//------------Inicializar el tablero----------------
    public List<Tile> inicializaTablero(Tile tile) {
        List<Tile> tableroVacio = new ArrayList<>();
        for (int i = 0; i <= 18; i++) {
            tableroVacio.add(null);  // Agrega `null` (sin ficha)
        }
        return tableroVacio;
    }
    

//---------------Asigna las primeras fichas de los extremos------------------------
        public   List<Tile> colocarFichasInciales(Tile tile) {
            //Iterar sobre la bolsa y poner en el tablero (el tablero tiene 19)
            List<Tile> listaFichasIniciales = inicializaTablero(tile);
            List<Color> colores = listaColores();
            List<Tile> bolsa = crearBolsa(tile);
            
            //Las celdas de las esquinas
            //(más o menos salen esos números al enumerarlas en el orden 
                //del juego (primero arriba y rodeando...))
            List<Integer> posicionesEsquinas = new ArrayList<>();
            posicionesEsquinas.add(0);
            posicionesEsquinas.add(3);
            posicionesEsquinas.add(5);
            posicionesEsquinas.add(13);
            posicionesEsquinas.add(15);
            posicionesEsquinas.add(18);
    
            for (Integer i : posicionesEsquinas) {
                for (Tile t : bolsa) {
                    if (colores.contains(t.color)) {
                        //Si no lo contiene es que ya está puesta una esquina con ese color
                        //A la esquina "i", le asignamos el color de la ficha "t"
                        listaFichasIniciales.set(i, t);
                        //Eliminamos el color y la ficha de la bolsa
                        colores.remove(t.color);
                        bolsa.remove(t);
                        //Siguiente posición de esquina
                        break;
                    }
                }
            }
            return listaFichasIniciales;
        }

        /*@Transactional(readOnly = true)
        public List<Tile> getAllTilesFromBag(Integer bolsaId) {
            return tr.findAllByBolsaId(bolsaId);
        }*/
    
}
