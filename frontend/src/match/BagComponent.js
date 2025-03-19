import React from 'react';
import bag from "../static/images/bag.png";

export default function BagComponent({ bolsa, onClick }) {
    const bolsaStyle = {
        position: 'absolute',
        left: '60px',  // Ajusta este valor para mover la bolsa más a la derecha
        top: '50%',
        transform: 'translateY(-50%)',
        width: '150px',
        height: '180px',
        cursor: 'pointer'
    };

    return (
        <img
            src={bag}
            alt="Bolsa"
            style={bolsaStyle}
            onClick={onClick} // Use  the onClick prop passed to the component
        />
    );
}