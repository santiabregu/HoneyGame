import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Form, Input, Label } from "reactstrap";
import tokenService from "../../services/token.service";
import "../../static/css/admin/adminPage.css";
import getErrorModal from "../../util/getErrorModal";
import getIdFromUrl from "../../util/getIdFromUrl";
import useFetchData from "../../util/useFetchData";
import useFetchState from "../../util/useFetchState";

const jwt = tokenService.getLocalAccessToken();

export default function UserEditAdmin() {
  const emptyItem = {
    username: "",
    firstname: "",
    lastname: "",
    user: {
      password: "",
      authority: {
        id: 2,
        authority: "PLAYER"
      }
    }
  };
  const username = getIdFromUrl(2);
  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);
  const [player, setPlayer] = useFetchState(
    emptyItem,
    `/api/v1/players/${username}`,
    jwt,
    setMessage,
    setVisible,
    username
  );

  function handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    if(name=="password"){
      setPlayer({ ...player, user: { ...player.user, password: value } });
    } else{
      setPlayer({ ...player, [name]: value });
    } 
  }
  useEffect(() => {
    console.log(player)
  }, [player])

  function handleSubmit(event) {
    event.preventDefault();
    const body = Boolean(player.id) ? {...player
    } : {
      username: player.username,
      firstname: player.firstname,
      lastname: player.lastname,
      user: {
        password: player.user.password,
        authority: player.user.authority
      }
    }
    fetch("/api/v1/players" + (player.id ? "/" + player.id : ""), {
      method: player.id ? "PUT" : "POST",
      headers: {
        Authorization: `Bearer ${jwt}`,
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(body),
    })
      .then((response) => response.json())
      .then((json) => {
        if (json.message) {
          setMessage(json.message);
          setVisible(true);
        } else window.location.href = "/players";
      })
      .catch((message) => alert(message));
  }

  const modal = getErrorModal(setVisible, visible, message);


  return (
    <div className="auth-page-container">
      {<h2>{player.id ? "Edit Player" : "Add Player"}</h2>}
      {modal}
      <div className="auth-form-container">
        <Form onSubmit={handleSubmit}>
          <div className="custom-form-input">
            <Label for="username" className="custom-form-input-label">
              Username
            </Label>
            <Input
              type="text"
              required
              name="username"
              id="username"
              value={player.username || ""}
              onChange={handleChange}
              className="custom-input"
            />
          </div>
          <div className="custom-form-input">
            <Label for="password" className="custom-form-input-label">
              Password
            </Label>
            <Input
              type="password"
              required
              name="password"
              id="password"
              value={player.user?.password || ""}
              onChange={handleChange}
              className="custom-input"
            />
          </div>
          <div className="custom-form-input">
            <Label for="firstName" className="custom-form-input-label">
              First Name
            </Label>
            <Input
              type="text"
              required
              name="firstname"
              id="firstname"
              value={player.firstname || ""}
              onChange={handleChange}
              className="custom-input"
            />
          </div>
          <div className="custom-form-input">
            <Label for="lastName" className="custom-form-input-label">
              Last Name
            </Label>
            <Input
              type="text"
              required
              name="lastname"
              id="lastname"
              value={player.lastname || ""}
              onChange={handleChange}
              className="custom-input"
            />
          </div>
          <div className="custom-button-row">
            <button className="auth-button">Save</button>
            <Link
              to={`/players`}
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
