import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../static/css/dashboard/CrearUnir.css';
import '../static/css/match/endgame.css'
import JoinGameModal from './JoinGameModal';
import tokenService from "../services/token.service";
import axios from 'axios';

const jwt = tokenService.getLocalAccessToken();
const user = tokenService.getUser(); 
const username = user ? user.username : '';


function CrearUnir() {
    const navigate = useNavigate();
    const [showModal, setShowModal] = useState(false); // Mostrar el modal al cargar la pantalla
    const [gameCode, setGameCode] = useState('');

    const handleCreateGame = () => {
        const gameMode = 'COMPETITIVE';
        console.log(`Creating game with mode: ${gameMode}`);
        axios.post('/api/v1/game/new', { gameMode },{
            headers: {
                'Authorization': `Bearer ${jwt}`
            }
        })
        .then((response) => {
            const tableroId = response.data.tableroId;
            console.log("Board game created with ID:", tableroId);
            navigate(`/waitingRoom/${tableroId}`);
            //Luego de waiting room al empezar si lleva a tablero/tableroId
        })
        .catch((error) => {
            console.error("Error creating game: ", error);
        });
    };

    const handleJoinGame = () => {
        setShowModal(true);
    };
    

    const handleCloseModal = () => {
        setShowModal(false);
        console.log("Modal should be closed now, showModal:", showModal);
    };

    const handleJoin = () => {
        // Aquí puedes manejar la lógica para unirse a la partida con el código ingresado
        console.log("Joining game with code:", gameCode);
        console.log("Username:", username);
        axios.put(`/api/v1/game/code/${gameCode}/join/${username}`, {}, {
            headers: {
                'Authorization': `Bearer ${jwt}`
            }
        })
        .then((response) => {
            console.log("Joined game successfully");
            const tableroId = response.data.tablero.id;
            console.log("TableroId:", tableroId);
            navigate(`/waitingRoom/${tableroId}`);
        })
        .catch((error) => {
            console.error("Error joining game: ", error);
        });

        //console.log(`Joining game with code: ${gameCode}`);
        setShowModal(false);
    };

    return (
        <div className="crear-unir-container">
            <h1>What do you want to do?</h1>
            <button className="myButton" onClick={handleCreateGame}>
                Create game
            </button>
            <button className="myButton" onClick={handleJoinGame}>
                Join game
            </button>

            <JoinGameModal
                show={showModal}
                handleClose={handleCloseModal}
                gameCode={gameCode}
                setGameCode={setGameCode}
                handleJoin={handleJoin}
            />
        </div>
    );
}

export default CrearUnir;