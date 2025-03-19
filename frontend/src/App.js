import React from "react";
import { Route, Routes } from "react-router-dom";
import jwt_decode from "jwt-decode";
import { ErrorBoundary } from "react-error-boundary";
import AppNavbar from "./AppNavbar";
import Home from "./home";
import PrivateRoute from "./privateRoute";
import Register from "./auth/register";
import Login from "./auth/login";
import Logout from "./auth/logout";
import PlanList from "./public/plan";
import tokenService from "./services/token.service";
import UserListAdmin from "./admin/users/UserListAdmin";
import UserEditAdmin from "./admin/users/UserEditAdmin";
import SwaggerDocs from "./public/swagger";
import Dashboard from "../src/dashboard/Dashboard"; // Import the Dashboard component
import Profile from "../src/profile/Profile.js"; // Import the Profile component
import Rules from "../src/rules/Rules.js"; // Import the Rules component
import Friends from "../src/friends/friends"; // Ensure the case matches the file name
import GameBoard from "../src/match/GameBoard"; // Import the Game component
import CrearUnir from "./dashboard/CrearUnir.js";
import GeneralSettings from "../src/settings/GeneralSettings"; // Import the GeneralSettings component
import '../src/static/css/home/home.css'; // Import the CSS file
import PendingRequests from "./friends/PendingRequests"; // Correct the import path
import AchievementListAdmin from "./achievements/achievementListAdmin.js";
import AchievementEdit from "./achievements/achievementEdit";
import AchievementListPlayer from "./achievements/achievementListPlayer.js";
import GamesPlayed from "./games/GamesPlayed.js";
import GamesInProgress from "./games/GamesInProgress.js";
import WaitingRoom from "./dashboard/WaitingRoom.js";
import MultiplayerGameBoard from "../src/match/MultiplayerBoard.js";
import Ranking from './ranking/Ranking'; // Import the new Ranking component
import FriendProfile from './friends/friendProfile';
import ViewProfile  from "./admin/users/UserViewAdmin";


function ErrorFallback({ error, resetErrorBoundary }) {
  return (
    <div role="alert">
      <p>Something went wrong:</p>
      <pre>{error.message}</pre>
      <button onClick={resetErrorBoundary}>Try again</button>
    </div>
  )
}

function App() {
  const jwt = tokenService.getLocalAccessToken();
  let roles = []
  if (jwt) {
    roles = getRolesFromJWT(jwt);
  }

  function getRolesFromJWT(jwt) {
    return jwt_decode(jwt).authorities;
  }

  let adminRoutes = <></>;
  let ownerRoutes = <></>;
  let userRoutes = <></>;
  let vetRoutes = <></>;
  let publicRoutes = <></>;

  roles.forEach((role) => {
    if (role === "ADMIN") {
      adminRoutes = (
        <>
          <Route path="/players/new" exact={true} element={<PrivateRoute><UserEditAdmin /></PrivateRoute>} />
          <Route path="/players" exact={true} element={<PrivateRoute><UserListAdmin /></PrivateRoute>} />
          <Route path="/players/:username" exact={true} element={<PrivateRoute><UserEditAdmin /></PrivateRoute>} />
          <Route path="/players/:username/view" element={<ViewProfile />} />
          <Route path="/achievements/" exact={true} element={<PrivateRoute>
            <AchievementListAdmin/></PrivateRoute>}  />    
            <Route path="/achievements/:achievementId" exact={true} element={<PrivateRoute><AchievementEdit
              /></PrivateRoute>} />
      
        </>)
    }
    if (role === "PLAYER") {
      ownerRoutes = (
        <>
          <Route path="/friends" exact={true} element={<PrivateRoute><Friends/></PrivateRoute>} /> 
          <Route path="/tablero/:id" element={<GameBoard />} />
          <Route path="/tableroMultiplayer/:id" element={<MultiplayerGameBoard />} />
          <Route path="/crearUnir" element={<CrearUnir />} />
          <Route path="/waitingRoom/:tableroId" element={<WaitingRoom />} />
          <Route path="/achievements/" exact={true} element={<PrivateRoute>
            <AchievementListPlayer/></PrivateRoute>}  />
        </>)
    }    
  })
  if (!jwt) {
    publicRoutes = (
      <>        
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
      </>
    )
  } else {
    userRoutes = (
      <>
        {<Route path="/dashboard" element={<PrivateRoute><Dashboard /></PrivateRoute>} /> }        
        <Route path="/logout" element={<Logout />} />
        <Route path="/login" element={<Login />} />
        <Route path="/profile" element={<PrivateRoute><Profile /></PrivateRoute>} /> {/* Add the profile route */}
        <Route path="/rules" element={<PrivateRoute><Rules /></PrivateRoute>} /> 
        <Route path="/settings" element={<PrivateRoute><GeneralSettings /></PrivateRoute>} /> {/* Add the general settings route */}
        <Route path="/pending-requests" element={<PrivateRoute><PendingRequests /></PrivateRoute>} />
        <Route path="/games/played" element={<PrivateRoute><GamesPlayed /></PrivateRoute>} />
        <Route path="/games/inprogress" element={<PrivateRoute><GamesInProgress /></PrivateRoute>} />
        <Route path="/ranking" element={<PrivateRoute><Ranking /></PrivateRoute>} /> {/* Add the ranking route */}
        <Route path="/friends/:username/profile" element={<FriendProfile />} />

      </>
    )
  }

  return (
    <div className="home-page-background">
      <ErrorBoundary FallbackComponent={ErrorFallback} >
        <AppNavbar />
        <Routes>
          <Route path="/" exact={true} element={<Home />} />
          <Route path="/plans" element={<PlanList />} />
          <Route path="/docs" element={<SwaggerDocs />} />
          {publicRoutes}
          {userRoutes}
          {adminRoutes}
          {ownerRoutes}
          {vetRoutes}
        </Routes>
      </ErrorBoundary>
    </div>
  );
}

export default App;