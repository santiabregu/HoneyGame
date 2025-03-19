import React from 'react';
import '../static/css/profile/PhotoSelector.css';

const profilePhotos = [
  { name: 'image1.png', src: '/images/profile/image1.png' },
  { name: 'image2.png', src: '/images/profile/image2.png' },
  { name: 'image3.png', src: '/images/profile/image3.png' },
  { name: 'image4.png', src: '/images/profile/image4.png' },
];

export default function PhotoSelector({ onSelectPhoto }) {
  return (
    <div className="photo-selector-container">
      <h1>Select Profile Photo</h1>
      <div className="photo-grid">
        {profilePhotos.map(photo => (
          <div key={photo.name} className="photo-item">
            <img src={photo.src} alt={photo.name} className="photo-thumbnail" />
            <button onClick={() => onSelectPhoto(photo.src)}>Select</button>
          </div>
        ))}
      </div>
      <button onClick={() => onSelectPhoto(null)}>Cancel</button>
    </div>
  );
}