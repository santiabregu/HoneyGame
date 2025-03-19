import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom'; // Hook para obtener parámetros de la URL
import tokenService from "../../services/token.service";

import "frontend/src/static/css/friends/FriendProfile.css";

export default function ViewProfile() {
    const { username } = useParams(); 
    const [data, setData] = useState(null);
    const [error, setError] = useState(null);
    const jwt = tokenService.getLocalAccessToken();

    // Obtener los datos del amigo desde el backend
    useEffect(() => {
        const fetchData = async () => {
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
                setData(data);
            } catch (err) {
                console.error(err.message);
                setError(err.message);
            }
        };
        fetchData();
    }, [username, jwt]);

    if (error) {
        return <p className="error-message">Error: {error}</p>;
    }

    if (!data) {
        return <p>Loading...</p>;
    }

    return (
        <div className="friend-profile-container">
            <h1>{data.username}'s profile</h1>
            <img 
                src={data.user.profilePhoto} 
                alt={`${data.username}'s profile`} 
                className="friend-profile-photo"
            />
            <p><strong>First Name:</strong> {data.firstname}</p>
            <p><strong>Last Name:</strong> {data.lastname}</p>
        </div>
    );
}
