// src/settings/GeneralSettings.js
import React, { useState } from 'react';
import audioFile from '../static/audio/audio.mp3'; // Import the audio file
import '../static/css/settings/GeneralSettings.css'; // Correct the import path
function GeneralSettings() {
    const [audio] = useState(new Audio(audioFile));
    const [isPlaying, setIsPlaying] = useState(false);
    const [volume, setVolume] = useState(1); // Volume ranges from 0.0 to 1.0

    const toggleAudio = () => {
        if (isPlaying) {
            audio.pause();
        } else {
            audio.play();
        }
        setIsPlaying(!isPlaying);
    };

    const handleVolumeChange = (event) => {
        const newVolume = parseFloat(event.target.value);
        audio.volume = newVolume;
        setVolume(newVolume);
    };

    return (
        <div className="settings-container">
            <h1>General Settings</h1>
            <button className="play-button" onClick={toggleAudio}>{isPlaying ? 'Pause Audio' : 'Play Audio'}</button>
            <div className="volume-control">
                <p className="volume-label">Volume</p>
                <input
                    type="range"
                    min="0"
                    max="1"
                    step="0.1"
                    value={volume}
                    onChange={handleVolumeChange}
                    className="volume-slider"
                />
                <p className="volume-percentage">{Math.round(volume * 100)}%</p>
            </div>
        </div>
    );
}

export default GeneralSettings;