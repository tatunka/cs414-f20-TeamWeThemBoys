import React, {useState} from "react";
import {Collapse} from "reactstrap";

import Login from "./Pages/Login";
import Profile from "./Pages/Profile";
import Match from "./Pages/Match";

import Header from "./Margins/Header";
import Footer from "./Margins/Footer";
import './App.css';


const App = () => {
    const [showLogin, setShowLogin] = useState(true);
    const [showProfile, setShowProfile] = useState(true);
    const [showMatch, setShowMatch] = useState(true);

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
                <Profile/>
                <Match/>
            </div>
            <Footer/>
        </div>
    );
}
export default App;