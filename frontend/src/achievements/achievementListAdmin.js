import { Button, Table } from "reactstrap";
import { useState } from "react";
import { Link } from "react-router-dom";
import tokenService from "../services/token.service";
import useFetchState from "../util/useFetchState";
import deleteFromList from "../util/deleteFromList";
import getErrorModal from "../util/getErrorModal";

const imgNotFound = "https://cdn-icons-png.flaticon.com/512/5778/5778223.png";

export default function AchievementList() {
  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);
  const [alerts, setAlerts] = useState([]);
  const jwt = tokenService.getLocalAccessToken();
  const [userData, setData] = useState([]);

  const [achievements, setAchievements] = useFetchState(
    [],
    `/api/v1/players/achievements`,
    jwt
  );

  const handleDelete = (id) => {
    deleteFromList(
      `/api/v1/achievements/${id}`,
      id,
      [achievements, setAchievements],
      [alerts, setAlerts],
      setMessage,
      setVisible
    );
  };

  const achievementList = achievements.map((achievement) => (
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
      <td className="text-center">{achievement.threshold}</td>
      <td className="text-center">{achievement.metric}</td>
      <td className="text-center">
        <Button outline color="warning" className="me-2">
          <Link
            to={`/achievements/${achievement.id}`}
            className="btn sm"
            style={{ textDecoration: "none" }}
          >
            Edit
          </Link>
        </Button>
        <Button
          outline
          color="danger"
          onClick={() => handleDelete(achievement.id)}
        >
          Delete
        </Button>
      </td>
    </tr>
  ));

  const errorModal = getErrorModal(setVisible, visible, message);

  return (
    console.log(achievements),
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
              <th className="text-center">Threshold</th>
              <th className="text-center">Metric</th>
              <th className="text-center">Actions</th>
            </tr>
          </thead>
          <tbody>{achievementList}</tbody>
        </Table>
        <Button outline color="success" className="mt-3">
          <Link
            to="/achievements/new"
            className="btn sm"
            style={{ textDecoration: "none" }}
          >
            Create Achievement
          </Link>
        </Button>
      </div>
    </div>
  );
}
