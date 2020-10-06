import React, { useState } from "react";

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

    return (
        <div className="App">
            <Header />
            <div className="pages">
                <Login isOpen={showLogin} />
                <Profile isOpen={showProfile} />
                <Match isOpen={showMatch} />
            </div>
            <Footer />
        </div>
    );
}
export default App;