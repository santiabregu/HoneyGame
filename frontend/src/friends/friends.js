import React, { useState, useEffect } from 'react';
import tokenService from '../services/token.service';
import useFetchState from '../util/useFetchState';
import { useNavigate } from 'react-router-dom'; // Import useNavigate
import photo1 from '../static/images/profile/image1.png';
import photo2 from '../static/images/profile/image2.png';
import photo3 from '../static/images/profile/image3.png';
import photo4 from '../static/images/profile/image4.png';

import '../static/css/friends/Friends.css'; // Import the CSS file

const jwt = tokenService.getLocalAccessToken();

export default function Friends() {
    const [ensenyarTexto, SetEnsenyarTexto] = useState(true);
    const handleClick = () => {
        SetEnsenyarTexto(false);
    };
    const [amigos, setAmigos] = useState([]);
    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(true);
    const [error, setError] = useState(null);
    const [searchResults, setSearchResults] = useState(null);
    const [searchQuery, setSearchQuery] = useState('');
    const [pendingRequests, setPendingRequests] = useState([]);
    const [userData, setData] = useState([]);
    const navigate = useNavigate(); // Initialize useNavigate
    const navigateViewProfile = useNavigate();

    

    useEffect(() => {
        const fetchCurrentUser = async () => {
            try {
                const response = await fetch(`/api/v1/players/currentPlayer`, {
                    headers: {
                        'Authorization': `Bearer ${jwt}`
                    }
                });
                if (!response.ok) {
                    throw new Error('Error fetching current user data');
                }
                const userData = await response.json();
                console.log('Fetched current user data:', userData);
                setData(userData);
                setMessage(userData.username);
            } catch (error) {
                console.error(error);
                setError(error.message);
            }
        };
        fetchCurrentUser();
}, [jwt]);


    
useEffect(() => {
    if (message) {
        const fetchFriends = async () => {
            try {
                const response = await fetch(`/api/v1/players/${userData.username}/friends`, {
                    headers: {
                        'Authorization': `Bearer ${jwt}`
                    }
                });
                if (!response.ok) {
                    throw new Error('Error fetching friends');
                }
                const friendsData = await response.json();
                console.log(friendsData); // Log the fetched friends data

                console.log('Fetched friends data:', friendsData);
                setAmigos(friendsData);
            } catch (err) {
                setError(err.message);
                console.error(`Error fetching friends: ${err.message}`);
            }
        };
        fetchFriends();
    }
}, [message, userData.username, jwt]);

    const handleViewProfile = (username) => {
        navigateViewProfile(`/friends/${username}/profile`); // Redirige a una nueva pantalla donde mostrarás el perfil
    };

    const handleDeleteFriend = async (friendUsername) => {
        try {
            const currentUser = userData.username; // Nombre de usuario actual
            const response = await fetch(`/api/v1/players/${currentUser}/remove-friend/${friendUsername}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${jwt}`
                }
            });
            if (!response.ok) {
                throw new Error('Error removing friend');
            }
            alert('Friend removed successfully!');
            setAmigos(amigos.filter((amigo) => amigo.username !== friendUsername)); // Actualiza la lista
        } catch (error) {
            console.error(`Error removing friend: ${error.message}`);
        }
    };
    


    const handleViewPendingRequests = () => {
        navigate('/pending-requests'); // Navigate to the pending requests screen
    };

    const handleSendFriendRequest = async (receiverUsername) => {
        try {
            const currentUser = userData.username; // Assuming `data` contains the current user's information
            const response = await fetch(`/api/v1/players/${currentUser}/send-friend-request/${receiverUsername}`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${jwt}`
                }
            });
            if (!response.ok) {
                throw new Error('Error sending friend request');
            }
            alert('Friend request sent');
            setSearchResults(null); // Clear search results
             // Refresh friends list
        } catch (err) {
            console.error(`Error sending friend request: ${err.message}`);
        }
    };


    const handleSearch = async () => {
        try {
            const response = await fetch(`/api/v1/players/${searchQuery}`, {
                headers: {
                    'Authorization': `Bearer ${jwt}`
                }
            });
            if (!response.ok) {
                throw new Error('Error searching for friends');
            }

            const searchResults = await response.json();
            setSearchResults(searchResults);
           console.log(searchResults);
        } catch (err) {
            setError(err.message);
            console.error(`Error searching for friends: ${err.message}`);
        }
    };


    return (
        <div className="friends-container">
            <button onClick={handleViewPendingRequests} className="friends-pending-button">Pending friend requests</button>
            <label className="friends-label">
                Add friends:
            </label>
            <div className="friends-search">
                <img
                    src="https://png.pngtree.com/png-clipart/20230401/original/pngtree-magnifying-glass-line-icon-png-image_9015864.png"
                    alt="Buscar"
                    className="friends-search-icon"
                />
                <input
                    type="text"
                    placeholder="Write friend's username"
                    onClick={() => setMessage(null)}
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                    className="friends-search-input"
                />
                <button onClick={handleSearch} className="friends-search-button">Search</button>
            </div>
            {!searchResults && (
            <>
            <h1>Friends:</h1>
            {error && <p className="error">{error}</p>}
            <ul className="friends-list">
                {amigos.length > 0 ? (
                    amigos.map((amigo, pos) => (
                        <li key={pos} className="friend-item">
                            <img 
                                src={amigo.user.profilePhoto} 
                                alt="Profile" 
                                className="friend-photo"
                            />
                            <div className="friend-info">
                                <span className="friend-name">{amigo.username}</span>
                                <span className={`status-indicator ${amigo.isOnline ? 'online' : 'offline'}`}>
                                    {amigo.isOnline ? '● Online' : '● Offline'}
                                </span>
                            </div>
                            <div className="friend-actions" style={{ marginLeft: 'auto', display: 'flex', flexDirection: 'column' }}>
                                <button 
                                    className="view-profile-button"
                                    onClick={() => handleViewProfile(amigo.username)}
                                >
                                    See Profile
                                </button>
                                <button 
                                    className="delete-friend-button"
                                    onClick={() => handleDeleteFriend(amigo.username)}
                                >
                                    Delete Friend
                                </button>
                            </div>
                        </li>
                    ))
                ) : (
                    <p>No friends found</p>
                )}
            </ul>
            </>
            )}
            {searchResults && (
                <div className="search-results">
                    <h2>Search results</h2>
                    <ul className='friends-list' style={{backgroundColor:"#fff2cc"}}>
                        <li className='friend-item' style={{ display: "flex", alignItems: "center", justifyContent: "space-between"}}>
                        <div style={{display:"flex", alignItems:"center"}}>    
                        <img src={searchResults.user.profilePhoto} 
                            alt="Profile" 
                            className="friend-photo"
                            />
                            {searchResults.firstname} {searchResults.lastname} ({searchResults.username})
                            </div>
                            <button style={{ backgroundColor: "#ffab40", justifySelf: "flex-end"}} onClick={() => handleSendFriendRequest(searchResults.username)}>Enviar solicitud de amistad</button>
                        </li>
                    </ul>
                </div>
            )}
        </div>
    );
}