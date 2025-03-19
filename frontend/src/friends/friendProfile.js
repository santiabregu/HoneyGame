import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom'; // Hook para obtener parámetros de la URL
import tokenService from '../services/token.service'; // Servicio para manejar el token

import '../static/css/friends/FriendProfile.css'; // Agrega un archivo CSS específico para estilos

export default function FriendProfile() {
    const { username } = useParams(); // Obtén el nombre del amigo desde la URL
    const [friendData, setFriendData] = useState(null);
    const [error, setError] = useState(null);
    const jwt = tokenService.getLocalAccessToken();

    // Obtener los datos del amigo desde el backend
    useEffect(() => {
        const fetchFriendData = async () => {
            try {
                const response = await fetch(`/api/v1/players/${username}`, {
                    headers: {
                        'Authorization': `Bearer ${jwt}`
                    }
                });
                if (!response.ok) {
                    throw new Error(`Error fetching profile for ${username}`);
                }
                const data = await response.json();
                setFriendData(data);
            } catch (err) {
                console.error(err.message);
                setError(err.message);
            }
        };
        fetchFriendData();
    }, [username, jwt]);

    if (error) {
        return <p className="error-message">Error: {error}</p>;
    }

    if (!friendData) {
        return <p>Loading...</p>;
    }

    return (
        <div className="friend-profile-container">
            <h1>{friendData.username}'s profile</h1>
            <img 
                src={friendData.user.profilePhoto} 
                alt={`${friendData.username}'s profile`} 
                className="friend-profile-photo"
            />
            <p><strong>First Name:</strong> {friendData.firstname}</p>
            <p><strong>Last Name:</strong> {friendData.lastname}</p>
        </div>
    );
}
