import React from 'react';
import axios from 'axios';
import posicion1 from '../../static/images/posicion1.png';
import posicion2 from '../../static/images/posicion2.png';
import posicion3 from '../../static/images/posicion3.png';
import perder from '../../static/images/perder.png';

const trophyPhotos = [
  { name: 'posicion1.jpg', src: posicion1 },
  { name: 'posicion2.jpg', src: posicion2 },
  { name: 'posicion3.jpg', src: posicion3 },
];

const MultiplayerModal = ({ show, handleClose, pointsByPlayer, jwt}) => {
    if (!show) {
        return null;
    }

    // Ordenar los jugadores por puntos de mayor a menor
    const sortedPlayers = [...pointsByPlayer].sort((a, b) => b.puntos - a.puntos);

    // Asignar win al jugador con más puntos
    const winner = sortedPlayers[0];

    // Actualizar las estadísticas del jugador con más puntos
    const updatePlayerStats = async (player) => {
        try {
            await axios.put(`/api/v1/players/stats/${player.nombre}/true`, {}, {
                params: {
                    puntosPartida: player.puntos
                },
                headers: {
                    Authorization: `Bearer ${jwt}`
                }
            });
            console.log("Player stats updated in database");
        } catch (error) {
            console.error("Error updating player stats in database", error);
        }
    };

    // Llamar a la función para actualizar las estadísticas del ganador
    updatePlayerStats(winner);

    return (
        <div className="result-modal-overlay">
            <div className="result-modal-content">
                <h1>Result</h1>
                <div className="horizontal-results">
                    {sortedPlayers.map((player, index) => {
                        return (
                            <div key={index} className="player-result">
                                {index < 3 ? (
                                    <img src={trophyPhotos[index].src} alt="Medal" className="imagen" />
                                ) : (
                                    <img src={perder} alt="Player lost" className="imagen" />
                                )}
                                <div className="number">{index + 1 + " "}</div>
                                <div style={{ fontSize: '25px' }}>{player.nombre + " "}</div>
                                <div style={{ fontSize: '20px' }}>{player.puntos + " points"}</div>
                            </div>
                        );
                    })}
                </div>
                <button onClick={handleClose}>Next</button>
            </div>
        </div>
    );
};

export default MultiplayerModal;