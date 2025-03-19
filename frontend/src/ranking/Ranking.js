import React, { useEffect, useState } from 'react';
import '../static/css/ranking/ranking.css';

// Mapeo de fotos locales con sus rutas
const profilePhotos = {
  '/images/profile/image1.png': '/images/profile/image1.png',
  '/images/profile/image2.png': '/images/profile/image2.png',
  '/images/profile/image3.png': '/images/profile/image3.png',
  '/images/profile/image4.png': '/images/profile/image4.png',
};

export default function Ranking() {
  const [ranking, setRanking] = useState([]);

  // Función para obtener el ranking desde el backend
  const fetchRanking = async () => {
    try {
      const response = await fetch('/api/v1/players/stats/ranking');
      if (!response.ok) {
        throw new Error('Failed to fetch ranking data');
      }
      const data = await response.json();
      console.log('Ranking data fetched:', data); // Debug log
      setRanking(data);
    } catch (error) {
      console.error('Error fetching ranking data:', error);
    }
  };

  useEffect(() => {
    fetchRanking(); // Llama a la función al montar el componente
  }, []);

  return (
    <div className="ranking-container">
      <h1>Global Ranking</h1>
      <div className="ranking-list">
        {ranking.length > 0 ? (
          ranking.map((player, index) => (
            <div key={player.username} className="ranking-item">
              <div className="ranking-position">{index + 1}</div>
              {profilePhotos[player.profilePhoto] && (
                <img
                  src={profilePhotos[player.profilePhoto]}
                  alt={`${player.username}'s avatar`}
                  className="ranking-avatar"
                />
              )}
              <div className="ranking-info">
                <span className="ranking-username">{player.username}</span>
                 <div className="ranking-points">
                 <span>{player.totalPoints}</span>
                 <span> Points</span>
                </div>
                </div>
            </div>
          ))
        ) : (
          <p>No ranking data available.</p>
        )}
      </div>
    </div>
  );
}
