// src/dashboard/Dashboard.js
import React, {useState, useEffect,  setMessage} from 'react';
import '../static/css/dashboard/dashboard.css'; // Import the CSS file
//import { Link } from 'react-router-dom';
import solitaryImg from '../static/images/solitary.png'; // Import the images
import survivalImg from '../static/images/survival.png';
import multiplayerImg from '../static/images/multiplayer.png';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import tokenService from "../services/token.service";
import FriendsModal from 'frontend/src/dashboard/FriendsModal.js';
import beeHive from 'frontend/public/beehive.png';


const jwt = tokenService.getLocalAccessToken();


function Dashboard() {
    const navigate = useNavigate();
    const [friends, setFriends] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
        const [userData, setData] = useState([]);
    
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
            }
        };
        fetchCurrentUser();
}, [jwt]);


    const createGame = (gameMode) => {
        console.log(`Creando juego con modo: ${gameMode}`);
        axios.post('/api/v1/game/new', { gameMode }, {
            headers: {
                'Authorization': `Bearer ${jwt}`
            }
        })
        .then((response) => {
            const tableroId = response.data.tableroId;
            console.log("Tablero creado con ID:", tableroId);
            if (gameMode === 'MULTIPLAYER') {
                navigate(`/tableroMultiplayer/${tableroId}`);   
            } else {
                navigate(`/tablero/${tableroId}?mode=${gameMode}`);
            }

        })
        .catch((error) => {
            console.error("Error creando el juego: ", error);
        });
    };

    const fetchOnlineFriends = async () => {
        try {
            const response = await fetch(`/api/v1/players/${userData.username}/onlineFriends`, {
                headers: {
                    'Authorization': `Bearer ${jwt}`
                }
            });
            if (!response.ok) {
                throw new Error('Error fetching online friends');
            }
            const friendsData = await response.json();
            setFriends(friendsData);
        } catch (error) {
            console.error('Error fetching online friends:', error);
        }
    };

    const handleOpenModal = () => {
        fetchOnlineFriends();
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
    };

    return (
        <div className="dashboard-container">
            <div className="dashboard-content">
                <div className="dashboard-box">
                    <img src={solitaryImg} alt="Solitary Mode" />
                    <button className="myButton" onClick={() => createGame('SOLO')}>
                        Solitary
                    </button>
                </div>
                <div className="dashboard-box">
                    <img src={survivalImg} alt="Survival Mode" />
                    <button className="myButton" onClick={() => createGame('SURVIVAL')}>
                        Survival
                    </button>
                </div>
                <div className="dashboard-box">
                    <img src={multiplayerImg} alt="Multiplayer Mode" />
                    <button className="myButton" onClick={() => navigate('/crearUnir')}>
                        Multiplayer
                    </button>
                </div>
            </div>
            <div className="friends-online-container" onClick={handleOpenModal}>
                <div className="friends-online-text">Online</div>
                <img src={beeHive} alt="Bee Hive" className="friends-online-image" />
            </div>
            <FriendsModal isOpen={isModalOpen} onClose={handleCloseModal} friends={friends} />
        </div>
        
    );
}

export default Dashboard;