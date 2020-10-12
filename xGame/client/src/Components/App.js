import React, { useState } from "react";

import LoginContainer from "./Login/LoginContainer";
import Profile from "./Pages/Profile";
import Match from "./Pages/Match";

import Header from "./Margins/Header";
import Footer from "./Margins/Footer";
import "./App.css";

const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [showLogin, setShowLogin] = useState(true);
  const [showProfile, setShowProfile] = useState(true);
  const [showMatch, setShowMatch] = useState(true);

  return (
    <div className="App">
      <Header />
      {isLoggedIn ? (
        <div className="pages">
          <Profile isOpen={showProfile} />
          <Match isOpen={showMatch} />
        </div>
      ) : (
        <LoginContainer />
      )}
      <Footer />
    </div>
  );
};
export default App;
