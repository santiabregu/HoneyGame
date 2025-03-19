import React from 'react';
import '../App.css';
import '../static/css/home/home.css'; 
//import logo from '../static/images/logotipo.jpg';

export default function Home(){
    
    return(
        <div className="home-page-container">

            <div className="hero-div">
                <h1>Honey</h1>
                <div className="bee"></div>
               
                <h3>Let's collect some points today!</h3>                
            </div>
        </div>
    );
}