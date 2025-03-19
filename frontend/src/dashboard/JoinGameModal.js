import React from "react";
import '../static/css/dashboard/CrearUnir.css';

const JoinGameModal = ({ show, handleClose, gameCode, setGameCode, handleJoin }) => {
    console.log("Modal show prop:", show); // Verifica si la prop show está llegando correctamente
    if (!show) {
        return null;
    }
    console.log(gameCode)
    return (
        <div className="endgame-modal-overlay">
            <div className="endgame-modal-content">
                <button style= {{background: 'white', color: 'red'}}onClick={handleClose}> x </button>
                <h2>Enter the game code</h2>
                <input
                    type="text"
                    value={gameCode}
                    onChange={(e) => setGameCode(e.target.value)}
                    placeholder="For example: 1234"
                    style={{ margin: '10px', padding: '20px', height: '45px', borderRadius: '5px' }}
                />
                <button style={{alignSelf: 'flex-end', margin: '10px', background: 'orange', height: '47px'}} 
                    onClick={handleJoin}>
                    Join
                </button>
            </div>
        </div>
    );
};

export default JoinGameModal;