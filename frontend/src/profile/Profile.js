import React, { useState, useEffect } from 'react';
import jwt_decode from "jwt-decode";
import tokenService from '../services/token.service';
import { Button } from 'reactstrap';
import { useNavigate } from 'react-router-dom'; // Import useNavigate from react-router-dom

import { FaPencilAlt } from 'react-icons/fa'; // Import the pencil icon from react-icons
import '../static/css/profile/profile.css'; // Import the CSS file
import PhotoSelector from './PhotoSelector'; // Import the PhotoSelector component



const jwt = tokenService.getLocalAccessToken();
const profilePhotos = [
  { name: 'image1.png', src: '/images/profile/image1.png' },
  { name: 'image2.png', src: '/images/profile/image2.png' },
  { name: 'image3.png', src: '/images/profile/image3.png' },
  { name: 'image4.png', src: '/images/profile/image4.png' },
];

export default function Profile() {
  const [user, setUser] = useState({});
  const [editing, setEditing] = useState(false);
  const [selectingPhoto, setSelectingPhoto] = useState(false); // State for photo selection screen
  const [stats, setStats] = useState(null);
  const navigate = useNavigate(); // Initialize useNavigate

  useEffect(() => {
    if (jwt) {
      const decodedToken = jwt_decode(jwt);
      const username = decodedToken.sub; // Suponiendo que el username está en `sub`
      
      // Obtener los datos del usuario
      fetch(`/api/v1/users/username/${username}`, {
        headers: { 'Authorization': `Bearer ${jwt}` }
      })
        .then(response => response.json())
        .then(data => { 
          setUser(data);

          // Obtener las estadísticas usando el username
          fetch(`/api/v1/players/stats/${username}`, {
            headers: { 'Authorization': `Bearer ${jwt}` }
          })
          .then(response => response.json())
          .then(statsData => setStats(statsData))
          .catch(error => console.error("Error fetching stats", error));
        })
        .catch(error => console.error("Error fetching user data", error));
    }
  }, [jwt]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUser({
      ...user,
      [name]: value
    });
  };

  const handlePhotoChange = (photoSrc) => {
    setUser({
      ...user,
      profilePhoto: photoSrc
    });
    setSelectingPhoto(false); // Close the photo selection screen
  };

  const handleEditClick = () => {
    setEditing(true);
  };

  const handleSaveClick = () => {
    fetch(`/api/v1/users/${user.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${jwt}`
      },
      body: JSON.stringify(user)
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Error updating profile');
        }
        return response.json();
      })
      .then(data => {
        setUser(data);
        setEditing(false);
      })
      .catch(error => {
        console.error("Error updating profile", error);
      });
  };

  const handleRankingClick = () => {
    navigate('/ranking'); // Navigate to the ranking screen
  };

  return (
    <div className="profile-container">
     <h1 className="profile-title">Profile</h1>  
      {selectingPhoto ? (
        <PhotoSelector onSelectPhoto={handlePhotoChange} />
      ) : editing ? (
        
        <div className="edit-profile-form">
          <label>
            Username:
            <input
              type="text"
              name="username"
              value={user.username || ''}
              onChange={handleInputChange}
            />
          </label>
          <label>
            {selectingPhoto ? (
              <div className="photo-options">
                {profilePhotos.map(photo => (
                  <img
                    key={photo.name}
                    src={photo.src}
                    alt={photo.name}
                    className={`photo-option ${user.profilePhoto === photo.name ? 'selected' : ''}`}
                    onClick={() => handlePhotoChange(photo.name)}
                  />
                ))}
              </div>
            ) : (
              <button
                type="button"
                className="select-photo-button"
                onClick={() => setSelectingPhoto(true)}
              >
                Select Photo
              </button>
            )}
          </label>
          <button onClick={handleSaveClick}>Save</button>
        </div>
      ) : (
        <div className="profile-content">
          <div className="profile-info">
            {user.profilePhoto && (
              <img 
                src={user.profilePhoto} 
                alt="Profile" 
                className="profile-picture"
              />
            )}
            <p className="username">{user.username}</p>
            <Button className="edit-button" onClick={handleEditClick}>
              <span className="text">Edit profile </span>
              <FaPencilAlt className="text" />
            </Button>
          </div>
          
          <div className="profile-stats">
            <h3>Player stats:</h3>
            <p>Total points: <span className="stat-value">{stats ? stats.totalPoints : 0}</span></p>
            <p>Played Games: <span className="stat-value">{stats ? stats.playedGames : 0}</span></p>
            <p>Games won: <span className="stat-value">{stats ? stats.wonGames : 0}</span></p>
            <Button className="ranking-button" onClick={handleRankingClick}>Ranking</Button>
          </div>
        </div>
      )}
    </div>
  );
}