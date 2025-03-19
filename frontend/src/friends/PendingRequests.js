import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate
import { Button } from 'reactstrap';
import '../static/css/friends/pendingRequests.css'; // Import the CSS file
import tokenService from '../services/token.service';

const jwt = tokenService.getLocalAccessToken();

export default function PendingRequests() {
  const [pendingRequests, setPendingRequests] = useState([]);
const [userData, setData] = useState([]);
  const [message, setMessage] = useState(null);
  const [error, setError] = useState(null);
  const navigate = useNavigate(); // Initialize useNavigate



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
        console.error('Fetch current user error:', error);
        setError(error.message);
      }
    };
    fetchCurrentUser();
  }, [jwt]);

  

  const handleRespondToFriendRequest = async (requestId, status) => {
    try {
        const response = await fetch(`/api/v1/players/${userData.username}/respond-friend-request/${requestId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwt}`
            },
            body: JSON.stringify({ status })
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Error responding to friend request: ${errorText}`);
        }
        setPendingRequests(prevRequests => 
            prevRequests.map(request => 
                request.id === requestId ? { ...request, status } : request
            )
        ); // Update the status of the request in the local state
        // Handle successful response
        console.log(`Friend request ${status.toLowerCase()} successfully`);
    } catch (err) {
        setError(err.message);
        console.error(`Error responding to friend request: ${err.message}`);
    }
};



  useEffect(() => {
    const fetchPendingRequests = async () => {
      try {
        const username = userData.username; // Get the username from the data state
        const response = await fetch(`/api/v1/players/${username}/pending-requests`, {
          headers: {
            'Authorization': `Bearer ${jwt}`
          }
        });
        if (!response.ok) {
          throw new Error('Error fetching pending requests');
        }
        console.log(response)

        const requests = await response.json();
        console.log(`Fetched pending requests: ${(requests)}`);
        setPendingRequests(requests);
      } catch (err) {
        setError(err.message);
        console.error(`Error fetching pending requests: ${err.message}`);
      }
    };

    if (userData.username) {
      fetchPendingRequests();
    }
  }, [userData, jwt]);

  return (
    <div className="pending-requests-container">
        <h1>Pending Friend Requests</h1>
        {error ? (
            <p className="error">Error: {error}</p>
        ) : pendingRequests.length === 0 ? (
            <p>No pending requests</p>
        ) : (
            <ul>
                {pendingRequests.map((request, index) =>
                    request.status === 'PENDING' && (
                        <li key={index}>
                            <span>{request.sender.username} wants to be your friend.</span>
                            <div className="buttons">
                                <button onClick={() => handleRespondToFriendRequest(request.id, 'ACCEPTED')}>Accept</button>
                                <button onClick={() => handleRespondToFriendRequest(request.id, 'REJECTED')}>Decline</button>
                            </div>
                        </li>
                    )
                )}
            </ul>
        )}
    </div>
);

}