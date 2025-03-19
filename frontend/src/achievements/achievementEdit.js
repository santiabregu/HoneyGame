import { useState } from "react";
import { Form, Input, Label } from "reactstrap";
import { Link, useNavigate } from "react-router-dom";
import tokenService from "../services/token.service";
import getErrorModal from "../util/getErrorModal";
import getIdFromUrl from "../util/getIdFromUrl";
import useFetchState from "../util/useFetchState";

export default function AchievementEdit() {
  const jwt = tokenService.getLocalAccessToken();
  const id = getIdFromUrl(2);
  const isNewAchievement = id === "new";

  const emptyAchievement = {
    id: isNewAchievement ? null : id,
    name: "",
    description: "",
    badgeImage: "",
    threshold: 1,
    metric: "GAMES_PLAYED",
    actualDescription: "",
  };

  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);
  const [achievement, setAchievement] = useFetchState(
    emptyAchievement,
    isNewAchievement ? null : `/api/v1/players/achievements/achievement/${id}`,
    jwt,
    setMessage,
    setVisible,
    id
  );

  const modal = getErrorModal(setVisible, visible, message);
  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();

    const url = `/api/v1/players/achievements${achievement.id ? `/achievement/${achievement.id}` : ""}`;
    const method = achievement.id ? "PUT" : "POST";

    fetch(url, {
      method,
      headers: {
        Authorization: `Bearer ${jwt}`,
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(achievement),
    })
      .then((response) => response.text())
      .then((data) => {
        if (data === "") {
          navigate("/achievements");
        } else {
          const json = JSON.parse(data);
          if (json.message) {
            setMessage(json.message);
            setVisible(true);
          } else {
            navigate("/achievements");
          }
        }
      })
      .catch((error) => alert(error));
  };

  const handleChange = (event) => {
    const { name, value } = event.target;
    setAchievement((prev) => ({ ...prev, [name]: value }));
  };

  return (
    <div className="auth-page-container">
      <h2 className="text-center">
        {achievement.id ? "Edit Achievement" : "Add Achievement"}
      </h2>
      <div className="auth-form-container">
        {modal}
        <Form onSubmit={handleSubmit}>
          <div className="custom-form-input">
            <Label for="name" className="custom-form-input-label">
              Name
            </Label>
            <Input
              type="text"
              required
              name="name"
              id="name"
              value={achievement.name || ""}
              onChange={handleChange}
              className="custom-input"
            />
          </div>
          <div className="custom-form-input">
            <Label for="description" className="custom-form-input-label">
              Description
            </Label>
            <Input
              type="text"
              required
              name="description"
              id="description"
              value={achievement.description || ""}
              onChange={handleChange}
              className="custom-input"
            />
          </div>
          <div className="custom-form-input">
            <Label for="badgeImage" className="custom-form-input-label">
              Badge Image URL
            </Label>
            <Input
              type="text"
              required
              name="badgeImage"
              id="badgeImage"
              value={achievement.badgeImage || ""}
              onChange={handleChange}
              className="custom-input"
            />
          </div>
          <div className="custom-form-input">
            <Label for="metric" className="custom-form-input-label">
              Metric
            </Label>
            <Input
              type="select"
              required
              name="metric"
              id="metric"
              value={achievement.metric || ""}
              onChange={handleChange}
              className="custom-input"
            >
              <option value="">None</option>
              <option value="BRONZE_MEDALS">BRONZE_MEDALS</option>
              <option value="SILVER_MEDALS">SILVER_MEDALS</option>
              <option value="GOLD_MEDALS">GOLD_MEDALS</option>
              <option value="PLATINUM_MEDALS">PLATINUM_MEDALS</option>
              <option value="GAMES_PLAYED">GAMES_PLAYED</option>
              <option value="WON_GAMES">WON_GAMES</option>
              <option value="TOTAL_POINTS">TOTAL_POINTS</option>
            </Input>
          </div>
          <div className="custom-form-input">
            <Label for="threshold" className="custom-form-input-label">
              Threshold Value
            </Label>
            <Input
              type="number"
              required
              name="threshold"
              id="threshold"
              value={achievement.threshold || ""}
              onChange={handleChange}
              className="custom-input"
            />
          </div>
          <div className="custom-button-row">
            <button className="auth-button">Save</button>
            <Link
              to="/achievements"
              className="auth-button"
              style={{ textDecoration: "none" }}
            >
              Cancel
            </Link>
          </div>
        </Form>
      </div>
    </div>
  );
}