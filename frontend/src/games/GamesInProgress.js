import React, { useEffect, useState } from 'react';
import tokenService from '../services/token.service';
import getErrorModal from '../util/getErrorModal';
import '../static/css/games/games.css';


export default function GamesInProgress() {
  const [games, setGames] = useState([]);
  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);

  const jwt = tokenService.getLocalAccessToken();
  // Función para obtener todas las partidas en curso.
  const fetchGames = async () => {
    try {
      const response = await fetch(`/api/v1/game/status/PLAYING`, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });

      if (!response.ok) {
        throw new Error("Failed to fetch games");
      }

      const data = await response.json();
      console.log("Datos recibidos desde el backend:", data); // Debug
      setGames(data); // Guardar todas las partidas en el estado
    } catch (error) {
      setMessage(error.message);
      setVisible(true);
    }
  };


  
  // Cargar partidas al montar el componente
  useEffect(() => {
    fetchGames();
  }, []);

  const modal = getErrorModal(setVisible, visible, message);
  const participantes = games.map((game)=> game.jugadores.map((j) => j.username));
  const fotosPerfil = games.map((game)=> game.jugadores.map((j) => j.user.profilePhoto));

  console.log("foyos de perfil", fotosPerfil)

  const profilePhotos = {
    '/images/profile/image1.png': '/images/profile/image1.png',
    '/images/profile/image2.png': '/images/profile/image2.png',
    '/images/profile/image3.png': '/images/profile/image3.png',
    '/images/profile/image4.png': '/images/profile/image4.png',
  };

  const getFoto = (nombreFoto) => {
    return profilePhotos[nombreFoto];
  }
  return (
    <div className="games-played-container">
      <h1 className="text-center">Games in Progress</h1>
      {modal}
      {games.length > 0 ? (
        <div className="games-list">
          {games.map((game) => (
            <div key={game.id} className="game-card">
              <div>
                <div className="game-header">
                  <h2>Partida {game.id}</h2>
                </div>
                <p>Created by: {game.creador.username}</p>
              </div>
              <div className='game-body-container'>
                <div className="game-body">
                  <div className='nombreImagen'>
                    <p> <strong>Participants: </strong> {participantes} </p>
                <div style={{display: 'flex', flexDirection: 'row', gap: '10px'}}>
                  <img src={getFoto(fotosPerfil)} alt={"foto de perfil"} />
                </div>
                </div>
                <p>
                  <strong> Date:</strong> {new Date(game.start).toLocaleDateString()}
                </p>
              </div>
              <div className="buttons-container">
                <span className="game-mode">{game.gamemode}</span>
                <span className='time-match'>
                  <strong> Time:</strong> {new Date(game.start).toLocaleTimeString()} - {'In Progress'}
                </span>
              </div>
            </div>
          </div>
          ))}
        </div>
      ) : (
        <p className="text-center">No games found.</p>
      )}
    </div>
  );
}
