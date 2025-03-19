import React, { useState } from "react";
import rojaDelantera from '../static/tiles/roja_delantera.png';
import azulDelantera from '../static/tiles/azul_delantera.png';
import verdeDelantera from '../static/tiles/verde_delantera.png';
import moradaDelantera from '../static/tiles/morada_delantera.png';
import amarillaDelantera from '../static/tiles/amarilla_delantera.png';
import naranjaDelantera from '../static/tiles/naranja_delantera.png';
import rojaTrasera from '../static/tiles/roja_trasera.png';
import azulTrasera from '../static/tiles/azul_trasera.png';
import verdeTrasera from '../static/tiles/verde_trasera.png';
import moradaTrasera from '../static/tiles/morada_trasera.png';
import amarillaTrasera from '../static/tiles/amarilla_trasera.png';
import naranjaTrasera from '../static/tiles/naranja_trasera.png';



const cardImages = {
    'RED': rojaDelantera,
    'BLUE': azulDelantera,
    'GREEN': verdeDelantera,
    'PURPLE': moradaDelantera,
    'YELLOW': amarillaDelantera,
    'ORANGE': naranjaDelantera,
    'RED_BACK': rojaTrasera,
    'BLUE_BACK': azulTrasera,
    'GREEN_BACK': verdeTrasera,
    'PURPLE_BACK': moradaTrasera,
    'YELLOW_BACK': amarillaTrasera,
    'ORANGE_BACK': naranjaTrasera
};





function getImageUrl(color, backColor, isReversed) {
    const key = isReversed ? backColor : color;
    return cardImages[key];
}




export function TileComponent({tile, onClick, isInHand}){
    const [isReversed, setIsReversed] = useState(true); 
    //El valor inicial de una ficha en mano será con su lado vacío (isReversed = true)

    if (!tile) {
        return null; 
        //Devuelve null si tile es undefined o null
    }

    const handleTileClick = () => {
        setIsReversed(!isReversed);
        //le da la vuelta a la ficha
        if (onClick) onClick(); 
        //Ejecuta la función onClick pasada como prop
        
    };

//Selecciona la imagen según "isReversed"
const imageUrl = getImageUrl(tile.color,tile.backColor, tile.isReversed);

const tileStyle = {
    position: isInHand ? 'relative' : 'absolute',
    top: '0',
    left: '0',
    width: '100%',
    paddingTop: '100%',
    marginBottom: '10px', // Espacio entre hexágonos
    cursor: 'pointer',
    clipPath: 'polygon(25% 0%, 75% 0%, 100% 50%, 75% 100%, 25% 100%, 0% 50%)',
    backgroundImage: `url(${imageUrl})`,
    backgroundSize: 'cover'
   
};

    
    return (
        <div style={tileStyle} onClick={handleTileClick}>
            {}
        </div>
    );
}








