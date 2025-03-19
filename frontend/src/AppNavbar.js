import React, { useState, useEffect } from 'react';
import { Navbar, NavbarBrand, NavLink, NavItem, Nav, NavbarText, NavbarToggler, Collapse, Dropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap';
import { Link } from 'react-router-dom';
import tokenService from './services/token.service';
import jwt_decode from "jwt-decode";
import 'frontend/src/static/css/navbar/navbar.css';

function AppNavbar() {
    const [roles, setRoles] = useState([]);
    const [username, setUsername] = useState("");
    const jwt = tokenService.getLocalAccessToken();
    const [collapsed, setCollapsed] = useState(true);
    const [dropdownOpen, setDropdownOpen] = useState(false);
    const [gamesDropdownOpen, setGamesDropdownOpen] = useState(false); // Estado para el desplegable de "Games"


    const toggleNavbar = () => setCollapsed(!collapsed);
    const toggleDropdown = () => setDropdownOpen(!dropdownOpen);
    const toggleGamesDropdown = () => setGamesDropdownOpen(!gamesDropdownOpen); // Función para alternar el desplegable de "Games"


    useEffect(() => {
        if (jwt) {
            setRoles(jwt_decode(jwt).authorities);
            setUsername(jwt_decode(jwt).sub);
        }
    }, [jwt])

    let adminLinks = <></>;
    let ownerLinks = <></>;
    let userLinks = <></>;
    let playerLinks = <></>;
    let publicLinks = <></>;

    roles.forEach((role) => {
        if (role === "ADMIN") {
            adminLinks = (
                <>                    
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/players">Players</NavLink>
                        </NavItem>
                    <NavItem>
                        <Dropdown nav isOpen={gamesDropdownOpen} toggle={toggleGamesDropdown}>
                            <DropdownToggle nav caret style={{ color: "white" }}>
                                Games
                            </DropdownToggle>
                            <DropdownMenu>
                                <DropdownItem tag={Link} to="/games/inprogress">Games In Progress</DropdownItem>
                                <DropdownItem tag={Link} to="/games/played">Games Played</DropdownItem>
                            </DropdownMenu>
                        </Dropdown>
                    </NavItem>
                </>
            )
        }        
        if (role === "PLAYER") {
            playerLinks = (
                <>       
                    <NavItem>
                    <NavLink style={{ color: "white" }} tag={Link} to="/dashboard">Play</NavLink>
                    </NavItem>             
                    <NavItem>
                    <NavLink style={{ color: "white" }} tag={Link} to="/profile">Profile</NavLink>
                    </NavItem>
                    <NavItem>
                    <NavLink style={{ color: "white" }} tag={Link} to="/friends">Friends</NavLink>
                    </NavItem>
                    <NavItem>
                    <NavLink style={{ color: "white" }} tag={Link}  to="/games/played">Games Played</NavLink>
                    </NavItem>
                    
                    
                </>
            )
        }     
    })

    if (!jwt) {
        publicLinks = (
            <>
                <NavItem>
                    <NavLink style={{ color: "white" }} id="docs" tag={Link} to="/docs">Docs</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink style={{ color: "white" }} id="plans" tag={Link} to="/plans">Pricing Plans</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink style={{ color: "white" }} id="register" tag={Link} to="/register">Register</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink style={{ color: "white" }} id="login" tag={Link} to="/login">Login</NavLink>
                </NavItem>
            </>
        )
    } else {
        userLinks = (
            <>
                
                <NavItem>
                    <NavLink style={{ color: "white" }} tag={Link} to="/achievements">Achievements</NavLink>
                </NavItem>
                
                
                <Dropdown nav isOpen={dropdownOpen} toggle={toggleDropdown}>
                    <DropdownToggle nav caret style={{ color: "white" }}>
                        Settings
                    </DropdownToggle>
                    <DropdownMenu>
                        <DropdownItem tag={Link} to="/rules">Game Rules</DropdownItem>
                        <DropdownItem tag={Link} to="/settings">General Settings</DropdownItem>
                        <DropdownItem id="logout" tag={Link} to="/logout">Logout</DropdownItem>
                    </DropdownMenu>
                </Dropdown>
            </>
        )

    }

    return (
        <div>
            <Navbar expand="md" dark className="custom-navbar">
                <NavbarBrand href="/">
                <img alt="logo" src="/logo2-recortado.png" className="logo-img" style={{ height: 40, width: 40 }} />
                Honey
                </NavbarBrand>
                <NavbarToggler onClick={toggleNavbar} className="ms-2" />
                <Collapse isOpen={!collapsed} navbar>
                    <Nav className="me-auto mb-2 mb-lg-0" navbar>
                        
                        {adminLinks}
                        {ownerLinks}
                    </Nav>
                    <Nav className="ms-auto mb-2 mb-lg-0" navbar>
                        {publicLinks}
                        {playerLinks}
                        {userLinks}
                    </Nav>
                </Collapse>
            </Navbar>
        </div>
    );
}

export default AppNavbar;