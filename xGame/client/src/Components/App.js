import React, {Component} from "react";
import {Collapse} from "reactstrap";

import Login from "./Pages/Login";
import Profile from "./Pages/Profile";
import Match from "./Pages/Match";

import Header from "./Margins/Header";
import Footer from "./Margins/Footer";
import './App.css';


export default App = () => {
    const [showLogin, setShowLogin] = useState(false);
    const [showProfile, setShowProfile] = useState(false);
    const [showMatch, setShowMatch] = useState(false);

    const Login = () => {
        return (
            <Collapse isOpen={showLogin}>
                <Login/>
            </Collapse>
        );
    }

    const Profile = () => {
        return (
            <Collapse isOpen={showProfile}>
                <Profile/>
            </Collapse>
        );
    }

    const Match = () => {
        return (
            <Collapse isOpen={showMatch}>
                <Match/>
            </Collapse>
        );
    }

    return (
        <div className="App">
            <Header/>
            <div className="pages">
                <Login/>
                <Proile/>
                <Match/>
            </div>
            <Footer/>
        </div>
    );
}