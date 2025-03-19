import React, { useEffect, useState } from 'react';

import { useParams, useNavigate } from 'react-router-dom';
import tokenService from '../services/token.service';
import axios from 'axios';
import '../static/css/dashboard/WaitingRoom.css';
import hourglassImage from '../static/images/reloj.png';

const WaitingRoom = () => {
    const [codigoPartida, setCodigoPartida] = useState('');
    const [gameId, setGameId] = useState(null);
    const [players, setPlayers] = useState([]);
    const { tableroId } = useParams();
    const jwt = tokenService.getLocalAccessToken();
    const user = tokenService.getUser();
    const username = user ? user.username : '';
    const [creadorUserName, setCreadorUserName] = useState('');

    // Obtenemos el creador de la partida
    useEffect(() => {
        const fetchCreador = async () => {
            try {
                const response = await axios.get(`/api/v1/game/${gameId}`);
                setCreadorUserName(response.data.creador.username);
                console.log("El creador de la partida es:", creadorUserName);
            } catch (error) {
                console.error("Error fetching creador from database", error);
            }
        };

        if (gameId) {
            fetchCreador();
        }
    }, [gameId, creadorUserName]);

    const navigate = useNavigate();
    useEffect(() => {
        const fetchGameId = async () => {
            try {
                const response = await fetch(`/api/v1/game/getGameId/${tableroId}`, {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                    },
                });
                if (!response.ok) {
                    throw new Error("Failed to fetch gameId from tableroId");
                }
                const gameId = await response.json();
                console.log("Game ID recibido desde el backend:", gameId);
                setGameId(gameId);
            } catch (error) {
                console.error("Error fetching gameId from tableroId: ", error);
            }
        };

        fetchGameId();
    }, [tableroId, jwt]);

    useEffect(() => {
        const fetchGames = async () => {
            try {
                const response = await fetch(`/api/v1/game/${gameId}`, {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                    },
                });
                if (!response.ok) {
                    throw new Error("Failed to fetch game from gameId");
                }
                const data = await response.json();
                console.log("Codigo partida:", data.codigoDePartida);
                setCodigoPartida(data.codigoDePartida);
                setPlayers(data.jugadores);
            } catch (error) {
                console.error("Error fetching game from gameId: ", error);
            }
        };

        if (gameId) {
            fetchGames();
        }
    }, [gameId, jwt]);

    useEffect(() => {
        const addPlayerToGame = async () => {
            try {
                const response = await axios.put(`/api/v1/game/code/${codigoPartida}/join/${username}`, null, {
                    headers: {
                        'Authorization': `Bearer ${jwt}`
                    }
                });
                const updatedGame = response.data;
                setPlayers(updatedGame.jugadores);
            } catch (error) {
                console.error("Error adding player to game: ", error);
            }
        };

        if (username && codigoPartida) {
            addPlayerToGame();
            const interval = setInterval(addPlayerToGame, 2000);
            return () => clearInterval(interval);
        }
    }, [codigoPartida, username, jwt]);

    console.log("codigoPartida", codigoPartida);
    console.log("Players", players);

    const handleStartGame = () => {
        axios.patch(`/api/v1/game/${gameId}/startGame`, null, {
            headers: {
                'Authorization': `Bearer ${jwt}`
            }
        })
        .then(() => {
            navigate(`/tableroMultiplayer/${tableroId}`);
        })
        .catch(error => {
            console.error("Error starting game: ", error);
        });
    };

    useEffect(() => {
        const checkGameStatus = async () => {
            try {
                const response = await axios.get(`/api/v1/game/${gameId}/checkPlaying`, {
                    headers: {
                        'Authorization': `Bearer ${jwt}`
                    }
                });

                const isPlaying = response.data;
                if (isPlaying) {
                    navigate(`/tableroMultiplayer/${tableroId}`);
                }
            } catch (error) {
                console.error("Error checking game status: ", error);
            }
        };

        if (gameId) {
            const interval = setInterval(checkGameStatus, 2000);
            return () => clearInterval(interval);
        }
    }, [gameId, jwt, navigate, tableroId]);


    return (
        <div className="waiting-room-container">
            <h1 className="waiting-room-title">Waiting Room</h1>
            {/*<p>Game ID: <p style={{color: 'GrayText'}}>{tableroId}</p></p>*/}
            <p>Game Code: <p style={{color: 'red'}}>{codigoPartida}</p></p>
            <p>Share this code with your friends to join the game!</p>
            <p style={{color: 'red'}}>Players waiting:
            {players.map((player, index) => (
                <p style={{color: 'orange'}}key={index}>{player.username}</p>
            ))}
            </p>
            <p>Waiting for other players to join...</p>
            <img src= {hourglassImage} alt="hourglass" className="hourglass-image" />
            {players.length >=2 && creadorUserName === username && (
                <button className="start-game-button" onClick={handleStartGame}>
                    Start Game
                </button>
            )}
        </div>
    );
};

export default WaitingRoom;