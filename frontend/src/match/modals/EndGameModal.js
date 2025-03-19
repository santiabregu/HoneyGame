import React from "react";
import '../../static/css/match/endgame.css';

const EndGameModal = ({ show, handleClose, win}) => {
    if(!show) {
        return null;
    }
    return(
        <div className="endgame-modal-overlay">
            <div className="endgame-modal-content">
                <h1> End of the game! </h1>
                <p> {win ? "Congratulations, you have won! :D" : "Bad luck, you have lost :c"} </p> 
                <button onClick={handleClose}> Close </button>
            </div>
        </div>
    )
}

export default EndGameModal; 