import React from 'react';
import '../static/css/dashboard/friendsModal.css'; // Import the CSS file


const FriendsModal = ({ isOpen, onClose, friends }) => {
  if (!isOpen) return null;

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <button className="close-button" onClick={onClose}>X</button>
        <h2>Friends Online</h2>
        <ul className="friends-list">
          {friends.map((friend, index) => (
            <li key={index} className="friend-item">
              <img src={friend.user.profilePhoto} alt="Profile" className="friend-photo" />
              <span className="friend-name">{friend.username}</span>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default FriendsModal;