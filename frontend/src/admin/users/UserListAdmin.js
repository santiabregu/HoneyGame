import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Button } from "reactstrap";
import tokenService from "../../services/token.service";
import "../../static/css/admin/adminPage.css";
import deleteFromList from "../../util/deleteFromList";
import getErrorModal from "../../util/getErrorModal";

const jwt = tokenService.getLocalAccessToken();

export default function UserListAdmin() {
  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);
  const [players, setPlayers] = useState([]); // Jugadores actuales
  const [currentPage, setCurrentPage] = useState(0); // Página actual
  const [totalPages, setTotalPages] = useState(0); // Total de páginas
  const [alerts, setAlerts] = useState([]);

  const profilePhotos = {
    '/images/profile/image1.png': '/images/profile/image1.png',
    '/images/profile/image2.png': '/images/profile/image2.png',
    '/images/profile/image3.png': '/images/profile/image3.png',
    '/images/profile/image4.png': '/images/profile/image4.png',
  };

  // Función para obtener jugadores desde el backend
  const fetchPlayers = async (page = 0, size = 2) => {
    try {
      const response = await fetch(`/api/v1/players/paginated?page=${page}&size=${size}`, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });
  
      if (!response.ok) {
        throw new Error("Failed to fetch players");
      }
  
      const data = await response.json();
  
      // Actualizar el estado con los datos de los jugadores
      setPlayers(data.players);
      setCurrentPage(data.currentPage);
      setTotalPages(data.totalPages);
    } catch (error) {
      setMessage(error.message);
      setVisible(true);
    }
  };
  

  // Manejador para cambiar de página
  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < totalPages) {
      setCurrentPage(newPage); // Actualizar la página actual
    }
  };

  

  // Llamar a fetchPlayers cuando se monta el componente
  useEffect(() => {
    fetchPlayers(currentPage);
  }, [currentPage]);

  const handleDelete = async (playerId) => {
  
    if (!window.confirm("Are you sure you want to delete this player?")) {
      console.log("Deletion cancelled by user");
      return;
    }
  
    try {
      const response = await fetch(`/api/v1/players/${playerId}`, {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });
  
      if (!response.ok) {
        const errorDetails = await response.text(); // Leer el cuerpo de la respuesta
      console.error("Backend response error:", errorDetails);
      throw new Error(`Failed to delete player: ${errorDetails}`);
      }
  
      alert("Player deleted successfully!");
  
      if (players.length === 1 && currentPage > 0) {
        setCurrentPage((prevPage) => prevPage - 1); // Retroceder a la página anterior
      } else {
        fetchPlayers(currentPage); // Refrescar la lista actual
      }
    } catch (error) {
      console.error("Error deleting player:", error);
      alert("Error deleting player: " + error.message);
    }
  };
  

  const modal = getErrorModal(setVisible, visible, message);

  return (
    <div className="admin-page-container">
  <h1 className="text-center">Players</h1>
  <Button
    style={{ width: "80%", marginTop: "8px", textAlign: "initial" }}
    color="success"
    tag={Link}
    to="/players/new"
  >
    <img
      src="https://png.pngtree.com/png-clipart/20230401/original/pngtree-magnifying-glass-line-icon-png-image_9015864.png"
      alt="icon"
      style={{
        width: "16px",
        height: "16px",
        marginRight: "8px",
        verticalAlign: "middle",
      }}
    />
    Add player
  </Button>

  <div className="users-list">
    {players.map((player) => (
      <div key={player.id} className="user-card">
        <img
          src={profilePhotos[player.profilePhoto]}
          alt="Profile"
          className="user-photo"
        />
        <div className="user-info">
          <p className="user-name">{player.username}</p>
          <p className="user-details">{player.firstname} {player.lastname}</p>
        </div>
        <div className="user-actions">
          <Button
            color="warning"
            size="sm"
            tag={Link}
            to={"/players/" + player.username}
          >
            Edit player
          </Button>
          <Button
            color="primary"
            size="sm"
            tag={Link}
            to={`/players/${player.username}/view`}
          >
            View player
          </Button>
          <Button
            size="sm"
            color="danger"
            aria-label={"delete"}
            onClick={() => handleDelete(player.id)}
          >
            Delete player
          </Button>
        </div>
      </div>
    ))}
  </div>

  <div style={{ width: "80%", textAlign: "center", marginTop: "16px" }}>
    <Button
      color="primary"
      size="sm"
      disabled={currentPage === 0}
      onClick={() => handlePageChange(currentPage - 1)}
    >
      ← Previous
    </Button>
    <span style={{ margin: "0 16px" }}>
      Page {currentPage + 1} of {totalPages}
    </span>
    <Button
      color="primary"
      size="sm"
      disabled={currentPage === totalPages - 1}
      onClick={() => handlePageChange(currentPage + 1)}
    >
      Next →
    </Button>
  </div>
</div>

  );
}
