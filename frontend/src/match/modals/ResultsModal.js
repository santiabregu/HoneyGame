import React from "react";
import '../../static/css/match/results.css';

import posicion1 from '../../static/images/posicion1.png';
import posicion2 from '../../static/images/posicion2.png';
import posicion3 from '../../static/images/posicion3.png';
import perder from '../../static/images/perder.png';

const trophyPhoto = [
  { name: 'posicion1.jpg', src: posicion1 },
  { name: 'posicion2.jpg', src: posicion2 },
  { name: 'posicion3.jpg', src: posicion3 },
];

const derrota = [
    { name: 'perder.jpg', src: perder },
]
const ResultsModal = ({ show, handleClose, pointsByPlayer, win}) => {
    if(!show) {
        return null;
    }
    return(
        <div className="result-modal-overlay">
            <div className="result-modal-content">
                <h1> Result</h1>
                <div className="horizontal-results">
                {pointsByPlayer.map((player, index) => {
                    return(
                        <div key={index} className="player-result">
                            {win ? (
                                <img src={posicion1} alt="Medal" className="imagen"/>
                            ):
                                <img src={perder} alt="Player lost" className="imagen"/>
                            }
                            <div className="number">{index+1 + " "}</div>
                            <div style={{fontSize:'25px'}}>{player.nombre + " "}</div>
                            <div style={{fontSize:'20px'}}>{player.puntos + " points"}</div>
                        </div>
                    )
                })}
                </div>
                <button onClick={handleClose}> Next </button>
            </div>
        </div>
    )
}

export default ResultsModal; 