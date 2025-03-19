import { Button, Table } from "reactstrap";
import axios from "axios";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import tokenService from "../services/token.service";
import useFetchState from "../util/useFetchState";
import deleteFromList from "../util/deleteFromList";
import getErrorModal from "../util/getErrorModal";
import '../static/css/achievement/achievement.css'; // Import the CSS file

const imgNotFound = "https://cdn-icons-png.flaticon.com/512/5778/5778223.png";

export default function AchievementList() {
  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);
  const [alerts, setAlerts] = useState([]);
  const jwt = tokenService.getLocalAccessToken();
  const [userData, setData] = useState([]);
  const [achievementsData, setAchievementsData] = useState([]);

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
        setMessage(error.message);
      }
    };
    fetchCurrentUser();
  }, [jwt]);

  useEffect(() => {
    if (message) {
      const fetchAchievements = async () => {
        try {
          const response = await fetch(`/api/v1/players/achievements/${userData.username}`, {
            headers: {
              'Authorization': `Bearer ${jwt}`
            }
          });
          if (!response.ok) {
            throw new Error('Error fetching friends');
          }
          const achievementsData = await response.json();
          console.log(achievementsData); // Log the fetched friends data

          console.log('Fetched friends data:', achievementsData);
          setAchievementsData(achievementsData);
        } catch (err) {
          console.error(`Error fetching ach: ${err.message}`);
        }
      };
      fetchAchievements();
    }
  }, [message, userData.username, jwt]);

  const handleClaim = async (achievementId) => {
    try {
      await axios.put(`/api/v1/players/achievements/${userData.username}/claim/${achievementId}`, {}, {
        headers: {
          Authorization: `Bearer ${jwt}`
        }
      });
      setAchievementsData((prevAchievements) =>
        prevAchievements.map((achievement) =>
          achievement.id === achievementId
            ? { ...achievement, claimed: true }
            : achievement
        )
      );
    } catch (error) {
      setMessage("Error claiming achievement");
      setVisible(true);
    }
  };

  const achievementList = achievementsData.map((achievement) => (
    <tr key={achievement.id}>
      <td className="text-center">{achievement.name}</td>
      <td className="text-center">{achievement.description}</td>
      <td className="text-center">
        <img
          src={achievement.badgeImage || imgNotFound}
          alt={achievement.name}
          width="50px"
        />
      </td>
      <td className="text-center">
        <Button
          outline
          color="warning"
          className={`me-2 ${!achievement.unlocked ? 'disabled-button' : ''}`}
          onClick={() => handleClaim(achievement.id)}
          disabled={!achievement.unlocked || achievement.claimed}
        >
          Claim
        </Button>
      </td>
    </tr>
  ));

  const errorModal = getErrorModal(setVisible, visible, message);

  return (
    <div className="admin-page-container">
      <h1 className="text-center">Achievements</h1>
      {alerts.map((alert, index) => (
        <div key={index}>{alert.alert}</div>
      ))}
      {errorModal}
      <div>
        <Table aria-label="achievements" className="mt-4">
          <thead>
            <tr>
              <th className="text-center">Name</th>
              <th className="text-center">Description</th>
              <th className="text-center">Image</th>
              <th className="text-center">Actions</th>
            </tr>
          </thead>
          <tbody>{achievementList}</tbody>
        </Table>
      </div>
    </div>
  );
}