import React, { useState, useEffect, useRef, useCallback } from 'react';
import { TileComponent } from "./TileComponent.js";
import tokenService from '../services/token.service.js';
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import jwt_decode from "jwt-decode";
import { ToastContainer, toast} from 'react-toastify';
import  BagComponent from "./BagComponent.js";
import 'react-toastify/dist/ReactToastify.css';
import '../static/css/match/Modal.css';
import '../static/css/match/board.css';
import '../static/css/match/ButtonEndGame.css';
import { apiFetch } from "./Utils.js";
import { tileModalStyle, columnStyle, notificationStyle, baseHexStyle } from './style/Styles.js';
import { PointsDisplay } from './PointsDisplay.js';
import { colorMap, cellsInicio, vecinos } from './ExtraFunctions.js';
import '../static/css/match/Multiplayer.css';
import MultiplayerModal from './modals/MultiplayerModal.js';

//------------------------HEXAGONO------------------------------------
export function Hexagon({coord, tile, onClick, color}) {
    const hex = {...baseHexStyle, backgroundColor: tile ? (tile.isReversed ? colorMap[tile.backColor] : colorMap[tile.color]) : color, top: `${coord[0]}%`, left: `${coord[1]}%`, opacity: tile ? '1': '0.5'};
    return (
        <div onClick={onClick} style={hex}> 
            {tile && <TileComponent tile={tile} />} {/* Mostrar la ficha en la celda  si tile no es null*/}
        </div>
    );
}

//------------------------------TABLERO------------------------------------
export default function MultiplayerGameBoard() {
    const [gameId, setGameId] = useState();
    const [points, SetPoints] = useState(0); 
    const [username, setUsername] = useState('');
    const [allTiles, setAllTiles] = useState([]);
    const [tableroId, setTableroId] = useState();
    const [cells, setCells] = useState([]);
    const [selectedTile, setSelectedTile] = useState();
    const [hand, setHand] = useState([]);
    const [bolsa, setBolsa] = useState([]);
    const [notificacionId, setNotificacionId] = useState();
    const [notificacionId2, setNotificacionId2] = useState();
    const[prueba, setPrueba] = useState();
    const [startGame, setStartGame] = useState(false);
    const[notificacionInicio, setNotificacionInicio] = useState(false);
    const[hayMatch, setHayMatch] = useState(false)
    const[matchedTiles, setMatchedTiles] = useState([]);
    const [eliminados, setEliminados] = useState([]);
    const [showResModal, setShowResModal] = useState(false);
    const [pointsByPlayer, setPointsByPlayer] = useState([]);
    const [players, setPlayers] = useState([]);
    const [turno, setTurno] = useState(0);
    const [creadorUserName, setCreadorUserName] = useState('');
    const navigate = useNavigate();
    const jwt = tokenService.getLocalAccessToken();

//-------------Fichas de las esquinas-------------------
    const allCellsInicioFilled = cellsInicio.every(id => {
        const cell = cells.find(c => c.id === id);
        return cell && cell.status === true;
    }, [gameId, jwt, username]);

    // Obtenemos el creador de la partida
    useEffect(() => {
        const fetchCreador = async () => {
            try {
                const response = await axios.get(`/api/v1/game/${gameId}`);
                setCreadorUserName(response.data.creador.username);
            } catch (error) {
                console.error("Error fetching creador from database", error);
            }
        };
        if (gameId) {
            fetchCreador();
        }
    }, [gameId, creadorUserName]);

    //Saca el tbalero id de la url cuando entras a esta
    useEffect(() => {
        const url = window.location.href;
        const matches = url.match(/tableroMultiplayer\/(\d+)/);
        if (matches) {
          setTableroId(matches[1]); 
        }
    }, [gameId]);

    //Actualizo turno en DATABASE
    const updateTurnInDatabase = useCallback(async (newTurn) => {
        try {
            await axios.patch(`/api/v1/game/${gameId}/turn`, null, {
                params: { newTurn },
                headers: {
                    'Authorization': `Bearer ${jwt}` // Envía el token en los encabezados
                }
            }, [gameId, jwt, username]);
        } catch (error) {
            console.error("Error updating turn in database", error);
        }
    }, [gameId, jwt, username]);

//---------------Función para obtener el valor de TURN de la base de datos-------
    useEffect(() => {
        const fetchTurnFromDatabase = async () => {
            try {
                const response = await axios.get(`/api/v1/game/${gameId}`);
                setTurno(response.data.turn);
            } catch (error) {
                console.error("Error fetching turn from database", error);
            }
        };
        const intervalId = setInterval(fetchTurnFromDatabase, 1000);
        return () => clearInterval(intervalId);
    }, [turno, gameId, jwt, updateTurnInDatabase]);

//-------------Obtengo "id" de la partida a partir de la "id" del tablero----------------------
    useEffect(() => {
        if (tableroId) {
            apiFetch(`/api/v1/game/getGameId/${tableroId}`)
                .then((data) => {
                    if (data) setGameId(data);
                })
                .catch((error) => {
                    console.error("Error retrieving the gameId:", error);
                });
        }
    }, [tableroId]);
    
//---------------Obtengo las fichas (tiles) de la base de datos y las meto en "allTiles"----------------
    useEffect(() => {
        const jwt = tokenService.getLocalAccessToken();
        if (jwt) {
            setUsername(jwt_decode(jwt).sub);
            apiFetch('/api/v1/tile/all')
                .then((data) => {
                    if (data) setAllTiles(data);
                })
                .catch((error) => {
                    console.error("Error retrieving tiles:", error);
                });
        }
    }, [gameId]);

//---------------Obtengo los nombres de los jugadores de la partida y los meto en "players"----------------
    useEffect(() =>{
        apiFetch(`/api/v1/game/${gameId}/players`)
            .then(data => {
                if(data) setPlayers(data)
            })
            .catch(error => {
                console.error("Error retrieving players giving a gameId:", error)
            })
    }, [gameId]);

//---------------Obtengo las casillas (cells) de la base de datos y las meto en "cells"----------------
    useEffect(() => {
        const jwt = tokenService.getLocalAccessToken();
        if (jwt) {
            setUsername(jwt_decode(jwt).sub);
            apiFetch('/api/v1/cell/all')
                .then((data) => {
                    if (data)  setCells(data);
                })
                .catch((error) => {
                    console.error("Error retrieving cells:", error);
                });
        }
    }, []);

//-------------------Obtengo la bolsa de la partida--------------------------    
    useEffect(() => {
        const jwt = tokenService.getLocalAccessToken();
        if (tableroId && jwt) {
            apiFetch(`/api/v1/bag/${tableroId}`)
                .then(response => {
                    setBolsa(response);
                })
                .catch(error => {
                    console.error("Error retrieving the board's bag:", error);
                });
        }
    }, [tableroId]);

    useEffect(() => {
        if (allTiles.length > 0) {
            setBolsa(prevBolsa => ({
                ...prevBolsa,
                fichasRestantes: allTiles, 
                numFichas: allTiles.length
            }));

        } else {
            setBolsa(prevBolsa => ({
                ...prevBolsa,
                fichasRestantes: [],
                numFichas: 0
            }));
        }
    }, [allTiles, jwt, bolsa.id]); 

//----------Obtengo la mano  de cada jugador, saco la mano de cada jugador 
    useEffect(() => {
        if (creadorUserName && jwt) {
            apiFetch(`/api/v1/hand/${creadorUserName}`)
                .then(response => {
                    setHand(response);
                })
                .catch(error => {
                    console.error("Error retrieving the player's hand:", error);
                });
        }
    }, [creadorUserName, jwt]);

//-----Auxiliar: llenar una casilla o vaiarla-----------
    const updateCellStatus = (id, status) => {
        apiFetch(`/api/v1/cell/${id}`, 'PUT', status)
            .then(response => {
                setCells(prevCells =>
                    prevCells.map(cell =>
                        cell.id === id ? response : cell
                    )
                );
            })
            .catch(error => {
                console.error("There was an error updating the cell status!", error);
            });
    };

    //-----Auxiliar: asignar una ficha aleatoria a la celda----------
    const assignRandomTileToCell = (cell, index) => {
        const randomIndex = Math.floor(Math.random() * hand.length); 
        const randomTile = { ...hand[randomIndex], isReversed: true }; //para que la Tile se coloque con el lado de inicio (lado reverso de las fichas)
        setHand(prevHand => prevHand.filter((_, idx) => idx !== randomIndex)); // Quitamos la ficha de la mano
            if (index === 0) {
            toast.dismiss(); 
        }
        return { ...cell, tile: randomTile, status: true }; // Asignamos la ficha a la celda
    };

//---------------------Rellenamos casillas con fichas--------------------
const handleCellClick = (index) => {
    if (players[turno].username !== username) {
        toast("It's not your turn!", {position: "bottom-center", autoClose: 4000, style: { color: 'red' }});
        return;
    } else {
        setCells(prevCells => 
            prevCells.map((casilla, i) => {
                if (i === index) {
                    if (hand.length > 0) { 
                        if (startGame) { 
                            if (selectedTile) { 
                                if (selectedTile.isReversed) { 
                                    const updatedHand = hand.filter(tile => tile.id !== selectedTile.id);
                                    setHand(updatedHand);
                                    setSelectedTile();
                                    const newCasilla = { ...casilla, tile: selectedTile };
                    
                                    // Actualizar la base de datos usando axios
                                    axios.put(`/api/v1/cell/${casilla.id}`, newCasilla, {
                                        headers: {
                                            'Content-Type': 'application/json',
                                            'Authorization': `Bearer ${jwt}`
                                        }
                                    })
                                    .then((response) => {
                                        setCells(prevCells =>
                                            prevCells.map(cell =>
                                                cell.id === casilla.id ? response.data : cell
                                            )
                                        );
                                    })
                                    .catch((error) => {
                                        console.error("Error updating the cell:", error);
                                    });
    
                                    return newCasilla; // Devuelve la casilla con la ficha colocada
                                }
                            } else {
                                return casilla;
                            }
                        } else {
                            // Verificar si la celda actual es la siguiente en el orden de cellsInicio
                            const cellId = casilla.id;
                            const cellIndexInInicio = cellsInicio.indexOf(cellId);
                            if (cellIndexInInicio === -1 || (cellIndexInInicio > 0 && !cells.find(c => c.id === cellsInicio[cellIndexInInicio - 1]).status)) {
                                toast("You must place the tile in the correct order!", {position: "bottom-center", autoClose: 4000, style: { color: 'red' }});
                                return casilla;
                            }
                            // Si el juego no ha comenzado, asigna una ficha aleatoria a la casilla
                            const newCasilla = assignRandomTileToCell(casilla, i);
    
                            // Actualizar la base de datos usando axios
                            axios.put(`/api/v1/cell/${casilla.id}`, newCasilla, {
                                headers: {
                                    'Content-Type': 'application/json',
                                    'Authorization': `Bearer ${jwt}`
                                }
                            })
                            .then((response) => {
                                setCells(prevCells =>
                                    prevCells.map(cell =>
                                        cell.id === casilla.id ? response.data : cell
                                    )
                                );
                            })
                            .catch((error) => {
                                console.error("Error updating the cell:", error);
                            });
                            
                            return newCasilla;
                        }
                    }
                }
                return casilla; // Si no se cumple ninguna condición, se devuelve la casilla sin cambios
            })
        );
    }
};
    
//--------------Función para clickar una FICHA---------------------
    const handleTileClick = (index) => {
        if (players[turno].username !== username) {
            toast("It's not your turn!", {position: "bottom-center", autoClose: 4000, style: { color: 'red' }});
            return;
        } else {
            if(!startGame){ return; }
            setHand(prevTiles =>
                prevTiles.map((tile, i) =>
                    i === index ? { ...tile, isReversed: !tile.isReversed } : tile
                )
            );
            setSelectedTile(hand[index]); 
        }
    };

    useEffect(() => {
        if (selectedTile) {
            toast("Tile selected!", {...notificationStyle, position: "bottom-center"});
        }
    }, [selectedTile]);
    
//--------------Función para clickar la BOLSA---------------------
    const handleBolsaClick = async() => {
        if (players[turno].username !== username) {
            toast("It's not your turn!", {position: "bottom-center", autoClose: 4000, style: { color: 'red' }});
            return;
        } else {
            try {
                const newTurn = turno + 1 < players.length ? turno + 1 : 0;
                updateTurnInDatabase(newTurn);
                if (bolsa.fichasRestantes.length === 0) {
                    toast("There are no tiles in the bag!", {...notificationStyle, style: {color: 'red'} ,position: "bottom-center"});
                    return; 
                } else if (hand.length === 1 && !startGame) {
                  toast("You must place the piece clockwise starting from the top corner before taking another one!", {...notificationStyle, style: {color: 'red'} ,position: "bottom-center"});
                    return;
                }
    
                const randomIndex = Math.floor(Math.random() * bolsa.fichasRestantes.length); 
                const selectedTile = bolsa.fichasRestantes[randomIndex];
    
                const tile = {...selectedTile, isReversed: true };
    
                var lista = [];
                lista.push(tile);
    
                setHand(prevHand => {
                    const currentHand = Array.isArray(prevHand) ? prevHand : [];
                    return [...currentHand, tile];  // Añade la ficha a la mano
                });
                const fichaAEliminar = bolsa.fichasRestantes[randomIndex];
                const updatedFichasRestantes = bolsa.fichasRestantes.filter(ficha => ficha.id !== fichaAEliminar.id);

                await fetch(`/api/v1/bag/${tableroId}/${fichaAEliminar.id}`,
                   {
                       method: 'PATCH',
                       headers: {
                            'Content-Type': 'application/json',
                            Authorization: `Bearer ${jwt}`
                        },
                    })
                    .then(response => {
                        if (!response.ok){
                            throw new Error("Error updating the bag");
                        }
                        return response.json();
                    })
                    .catch((error) => {
                        console.error("Error updating the bag:", error);
                   })
                setBolsa(prevBolsa => ({
                    ...prevBolsa,
                    fichasRestantes: updatedFichasRestantes,   // Actualiza las fichas restantes en la bolsa
                   numFichas: updatedFichasRestantes.length   // Actualiza el número de fichas
                }));
        } catch (error) {
            console.error("Error updating the bag:", error);
        }
        }
    };
    
//------------------------COORDENADAS------------------------------

    const lista = [1,2,3,2,3,2,3,2,1];
    let listaCord = [];

    function getCords(list) {
        for (let i = 0; i < list.length; i++) { 
            if (i===0) {
                listaCord.push([8,45]);
            } else {
                if (list[i] === 1) { 
                    listaCord.push([8*(i+1),45]);
                } else if (list[i] === 2) { 
                    listaCord.push([8*(i+1),45-6]);
                    listaCord.push([8*(i+1),45+6]);
                } else {
                    listaCord.push([8*(i+1),45-12]);
                    listaCord.push([8*(i+1),45]);
                    listaCord.push([8*(i+1),45+12]);
                }        
            }
        }
        return listaCord;
    }

    let laLista = getCords(lista);

    function showBoard(list) {
        return list.map((coords, i) => {  
            const cell = cells.find(c=> c.id === i+1);
            return (
                <Hexagon
                    key={i}
                    coord={coords}
                    onClick={() => handleCellClick(i)}
                    tile={cells[i] && cells[i].tile} 
                    color={cellsInicio.includes(i+1) ? 'black' : 'white'}
                    onUpdateCellStatus={updateCellStatus}
                    status =  {cell?.status}
                />
            );
        });
    }
    
//-----------Estas son las POSICIONES de los HEXÁGONOS---------------
    let hexagonos = showBoard(laLista);

// Notificación cuando la bolsa esté llena (al principio del juego)
        useEffect(() => {
            if (bolsa && bolsa.numFichas === 72) {
                const id = toast("Press the bag and draw a tile!",{...notificationStyle, position: "bottom-center"});

            setNotificacionId(id);
            }
        }, [bolsa]); // Se ejecuta cada vez que cambia la bolsa

// Notificación cuando la mano tiene una ficha (cuando el jugador saca una ficha de la bolsa)
        useEffect(() =>{
            if(hand && hand.length === 1 && notificacionId){
                toast.dismiss(notificacionId);
                setNotificacionId(null);
                toast("Press the first highlighted cell and place your tile",{...notificationStyle, position: "bottom-center"})
                toast("You must place the pieces clockwise starting from the top corner!", {position: "bottom-center", autoClose: 5500, style: { color: 'red' }}) 
            }
        },[hand,notificacionId])

    useEffect(() => {
        const cellInicio = cells.find(c => c.id === 1);

        if(cellInicio && cellInicio.status === true && !notificacionInicio){
            const id = toast("Take another tile from the bag", {...notificationStyle, position: "bottom-center"})
            setNotificacionId2(id);  // Guarda el ID de la notificación
            setNotificacionInicio(true); // Marca que la primera casilla ya está ocupada
        }

        // Limpiar la notificación cuando el componente se desmonte
        return () => {
            if(notificacionId2){
                toast.dismiss(notificacionId2); // Cierra la notificación
                setNotificacionId2(null); // Resetea el ID de la notificación
            }
        };
    }, [cells, notificacionId2, notificacionInicio]); // Se ejecuta cada vez que cambian las celdas, el ID de la notificación o el estado de inicio

    const fichasTablero = cells.filter(cell => cell.tile !== null).map(cell => cell.tile); 
    // Fichas en la mano
    const fichasMano = hand;

    useEffect(() => {
        function volverAMeterEnLaBolsa(ficha) {
            const fichaYaEnBolsa = bolsa.fichasRestantes.some(f => f.id === ficha.id);
            
            if (!fichaYaEnBolsa) {
                const updatedFichasRestantes = [...bolsa.fichasRestantes, ficha];
                setBolsa(prevBolsa => ({
                    ...prevBolsa,
                    fichasRestantes: updatedFichasRestantes,
                    numFichas: updatedFichasRestantes.length
                }));
            }
        }

        function eliminarDeLaMano(ficha) {
            const updatedFichasMano = hand.filter(f => f.id !== ficha.id);
            setHand(prevHand => ({
                ...prevHand,
                fichas: updatedFichasMano, // Actualiza las fichas en la mano
                numFichas: updatedFichasMano.length // Actualiza el número de fichas en la mano
            }));
        }

        if (Array.isArray(fichasMano) && hand && fichasTablero.length > 0 && !startGame) {
            fichasMano.forEach(fichaMano => {
                const hayFichaIgual = fichasTablero.find(fichasTablero => fichaMano.backColor === fichasTablero.backColor);
                if (hayFichaIgual) {
                    setPrueba(false); // Marca que hubo coincidencia de color
                    volverAMeterEnLaBolsa(fichaMano); // Devuelve la ficha a la bolsa
                    eliminarDeLaMano(fichaMano); // Elimina la ficha de la mano
                } else {
                    setPrueba(true); // Si no hay coincidencia, la prueba es true
                }
            });
        }
    }, [fichasMano, fichasTablero, hand, bolsa, prueba, startGame]); // Se ejecuta cuando cambia alguna de las dependencias

    useEffect(() =>{
        if(prueba && notificacionInicio){
            toast("Put the tile in the board",{...notificationStyle, position: "bottom-center"})
        }else if(!prueba && notificacionInicio){
            toast("Keep drawing",{...notificationStyle, position: "bottom-center"})
        }
    },[prueba, notificacionInicio])

//--------------Función para finalizar la partida-----------
    const handleEndGame = useCallback(async () => {
        try {
            await axios.patch(`/api/v1/game/${gameId}/endGame`, {},{headers: {Authorization: `Bearer ${jwt}`}});
            // llamada para actualizar las estadísticas del jugador	(si se termina la partida manualmente, el jugador ha perdido)
            setShowResModal(true);
        } catch (error) {
            if (error.response) {
                console.error('Error status:', error.response.status); // Código HTTP
                console.error('Error data:', error.response.data); // Detalles del error
            } else {
                console.error('Error message:', error.message); // Mensaje general
            }
            alert('An error occurred while ending the game. Please try again.');
        }
    }, [gameId, jwt]);

//----------Para actualizar los PUNTOS en la base de datos cada vez que points cambie
    useEffect(() => {
        const updatePointsInDatabase = async (newPoints) => {
            try {
                await axios.patch(`/api/v1/players/${username}/points`, { points: newPoints }, {
                    headers: {
                        'Authorization': `Bearer ${jwt}` // Envía el token en los encabezados
                    }
                });

                // Después de actualizar los puntos, actualiza los jugadores
                apiFetch(`/api/v1/game/${gameId}/players`)
                    .then(data => {
                        if (data) setPlayers(data);
                    })
                    .catch(error => {
                        console.error("Error retrieving players giving a gameId:", error);
                    });
            } catch (error) {
                console.error("Error updating points in database", error);
            }
        };

        const interval = setInterval(() => {
            updatePointsInDatabase(points);

        }, 1000);
        return () => clearInterval(interval);
    }, [points, username, jwt, gameId]);

//----------------useEffect para finalizar la partida-------------------
    useEffect(() => {
        //const puntosPorJugador = [[username, points]]; 
        if ((startGame && fichasTablero.length === 0)){ 
            handleEndGame();
        } else if (startGame && ((hand.length === 0 && bolsa.numFichas === 0) || fichasTablero.length === cells.length)) {
            handleEndGame();
        }
    }, [startGame, hand, bolsa, fichasTablero, cells, allCellsInicioFilled, handleEndGame])

//---------------Notificación cuando estén rellenas las casillas de inicio-----------
    useEffect(() =>{
        if(allCellsInicioFilled) {
            setStartGame(true);
        }
        if(startGame) {
            toast("You can now start playing, click on a tile from your hand and place it on the board to make MATCHES",{ position: "bottom-center", autoClose: 4000,})
        }
    }, [allCellsInicioFilled,startGame]);

    useEffect(() => {
        const checkForMatches = (index) => {
            const visited = new Set(); 
            let matchingTiles = 0;
            let matchedTiles = [];
            
            const dfs = (currentIndex) => {
                visited.add(currentIndex);
                
                const listaAdyacentes = vecinos[currentIndex + 1];
                if (!listaAdyacentes) return;
    
                listaAdyacentes.forEach(adjIndex => {
                    const cell = cells[adjIndex - 1];
                    if (cell && cell.tile && !visited.has(adjIndex - 1)) {
                        const isTileReversed = cell.tile.isReversed; 
                        const { color, backColor } = cell.tile;
    
                        let isMatching = false;
                        if (isTileReversed) {
                            isMatching = colorMap[backColor] === ( cells[index].tile.isReversed ? colorMap[cells[index].tile.backColor] : colorMap[cells[index].tile.color]); 
                        } else if (!isTileReversed) {
                            isMatching = colorMap[color] === (cells[index].tile.isReversed ? colorMap[cells[index].tile.backColor] : colorMap[cells[index].tile.color]); 
                        }
                        
                        if (isMatching) {
                            matchingTiles++; // Contamos cada celda que coincida en color
                            matchedTiles.push(cell); // Almacenar la ficha que coincide
                            dfs(adjIndex - 1); // Llamamos recursivamente para buscar más adyacencias
                        }
                    }
                });
            };
            
            matchedTiles.push(cells[index]);
            matchingTiles++;
            dfs(index);
    
            if (matchingTiles >= 3) {
                setHayMatch(true);
                
                setMatchedTiles(matchedTiles);
                setSelectedTile(null);
    
                const eliminados = [];
                const updatedCells = cells.map(cell => {
                    if (matchedTiles.includes(cell)) {
                        if (!cell.tile.isReversed) {
                            eliminados.push(cell.tile); // Guardar la ficha eliminada
                            return { ...cell, tile: null }; // Eliminar la ficha del tablero
                        }
                        const { color, backColor } = cell.tile;
                        if (colorMap[color] === colorMap[backColor]) {
                            eliminados.push(cell.tile);
                            return { ...cell, tile: null };
                        }
                    }
                    return cell;
                });
    
                setEliminados(prev => [...prev, ...eliminados]);
    
                const finalUpdatedCells = updatedCells.map(cell => {
                    if (matchedTiles.includes(cell) && cell.tile) {
                        return { ...cell, tile: { ...cell.tile, isReversed: false } }; // Marcar la ficha como isReversed = false
                    }
                    return cell;
                });
    
                setCells(finalUpdatedCells);
                
            } else {
                setHayMatch(false);
            }
        };
    
        if (selectedTile) {
            const index = cells.findIndex(cell => cell.tile?.id === selectedTile.id);
            if (index !== -1 && cells[index].tile) {
                checkForMatches(index);
            }
        }

        const verificarCadenas = () => {
            cells.forEach((cell, index) => {
                if (cell.tile) { // Verifica si hay una ficha en la celda
                    checkForMatches(index); // Llamas a tu función que revisa cadenas
                }
            });
        };
        verificarCadenas();
    }, [cells, selectedTile, matchedTiles]);

//-----------------Points-----------------
    useEffect(() => {
        if (matchedTiles.length >= 3) {
            SetPoints((prevPoints) => prevPoints + 1 + (matchedTiles.length - 3));
            setMatchedTiles([]);
        }
    }, [matchedTiles, players.length]);

    // Lógica para actualizar el turno
    useEffect(() => {
        if (matchedTiles.length >= 3) {
            const newTurn = turno + 1 < players.length ? turno + 1 : 0;
            updateTurnInDatabase(newTurn);
        }
    }, [matchedTiles, players.length, turno, updateTurnInDatabase]);

    useEffect(() => {
        const puntosPorJugador = players.map(player => ({
            nombre: player.username,
            puntos: player.points
        }));
        setPointsByPlayer(puntosPorJugador);
    }, [players]);

    //Comprueba si la partida está FINISHED
    useEffect(() => {
        const checkGameStatus = async () => {
            try {
                const response = await axios.get(`/api/v1/game/${gameId}/checkFinished`, {
                    headers: {
                        'Authorization': `Bearer ${jwt}`
                    }
                });

                const isFinished = response.data;
                if (isFinished) {
                    setShowResModal(true);
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
        <div className="home-page-container">
            <div className="columnas" style={columnStyle}>
                <div>
                    {hexagonos}
                </div>
            <BagComponent 
                bolsa={{ positionBottom: '300px', positionLeft: '160px' }} // Ajusta la posición según necesites
                onClick={handleBolsaClick} 
            />
            <ToastContainer>
            {/*Para que salgan las notificaciones*/}
            </ToastContainer>
            <div className="container-parent">
            <h3 style={{ color: 'orange' }}>{username} hand:</h3>
                <div className="tiles-container" style={tileModalStyle}>
                {Array.isArray(hand) && hand.length>0 ? (
                    hand.map((tile, index) => (
                        <TileComponent
                            key={index} 
                            tile={tile}
                            isInHand={true}
                            onClick={() => handleTileClick(index)} />
                    ))
                ):(<p>There is not tiles in your hand</p>)}
                </div>
            </div>
            </div>
            <PointsDisplay points={points} />
            <div className="player-turn-container">
                {players && players[turno] && players[turno].username ? (
                    <h3 className="player-turn">{players[turno].username} turn</h3>
                ) : (
                    <h3 className="loading-turn">Loading player turn...</h3>
                )}
            </div>
            <div className='button-container-turn'>
                <button onClick={() => {
                        const newTurn = turno + 1 < players.length ? turno + 1 : 0;
                        updateTurnInDatabase(newTurn); 
                    }}> END TURN
                </button>
            </div>
            <MultiplayerModal show={showResModal}  handleClose={() => {
                setShowResModal(false)
                navigate('/dashboard')
                }}
                pointsByPlayer={players.map(player => ({
                    nombre: player.username,
                    puntos: player.points
                }))}/>
            {players.length > 0 && creadorUserName === username && 
            points === Math.min(...players.map(player => player.points)) &&(
                <div className="end-game-container">
                    <button className="end-game-button" onClick={() => {
                        handleEndGame()
                    }}> ABORT GAME & THE CREATOR LOSES
                    </button>
                </div>
            )}
        </div>
    ); 
}