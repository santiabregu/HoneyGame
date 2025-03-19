
//----------Hexagon-------------
export const colorMap = {
    'RED': '#FF0000',
    'BLUE': '#0000FF',
    'GREEN': '#00FF00',
    'PURPLE': '#800080',
    'YELLOW': '#FFFF00',
    'ORANGE': '#FFA500',
    'RED_BACK': '#FF0000',
    'BLUE_BACK': '#0000FF',
    'GREEN_BACK': '#00FF00',
    'PURPLE_BACK': '#800080',
    'YELLOW_BACK': '#FFFF00',
    'ORANGE_BACK': '#FFA500',
};

//---------DICIONARIO con los VECINOS de las CELDAS del TABLERO----------------
export const claves = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19];
export const valores = [
    [2,3,5], // Vecinos para la casilla con id =1
    [1,4,5,7], // Vecinos para la casilla con id = 2
    [1,5,6,8], //...
    [2,7,9],
    [1,2,3,7,8,10],
    [3,8,11],
    [2,4,5,9,10,12],
    [3,5,6,10,11,13],
    [4,7,12,14],
    [5,7,8,12,13,15],
    [6,8,13,16],
    [7,9,10,14,15,17],
    [8,10,11,15,16,18],
    [9,12,17],
    [10,12,13,17,18,19],
    [11,13,18],
    [12,14,15,19],
    [13,15,16,19],
    [15,17,18]
];

//---------DICIONARIO con los VECINOS de las CELDAS del TABLERO----------------
export const cellsInicio = [1, 6, 16, 19, 14, 4];

export const vecinos = {};
for (let i = 0; i < claves.length; i++) {
    vecinos[claves[i]] = valores[i];
}

//-------Comprobar si son las 3 vecinas para SRUVIVAL
export const areAllTilesNeighbors = (tiles) => {
    // Implementa la lógica para verificar si todas las fichas en `tiles` son vecinas entre sí
    for (let i = 0; i < tiles.length; i++) {
        for (let j = i + 1; j < tiles.length; j++) {
            if (!areNeighbors(tiles[i], tiles[j])) {
                return false;
            }
        }
    }
    return true;
};

const areNeighbors = (tile1, tile2) => {
    const tile1Id = tile1.id;
    const tile2Id = tile2.id;
    return vecinos[tile1Id].includes(tile2Id);
};
