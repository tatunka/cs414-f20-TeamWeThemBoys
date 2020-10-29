import React, { useState } from "react";

import LoginContainer from "./Login/LoginContainer";
import Profile from "./Profile/Profile";
import Match from "./Match/Match";
import MatchSelect from "./MatchSelect/MatchSelect";
import Header from "./Margins/Header";
import Footer from "./Margins/Footer";

import "./App.css";

const App = () => {
  const [activeUser, setActiveUser] = useState({ isLoggedIn: false });

  return (
    <div className="App">
      <Header activeUser={activeUser} />
      {activeUser.isLoggedIn ? (
        <div className="pages">
          <Profile />
          <MatchSelect />
          <Match />
        </div>
      ) : (
        <LoginContainer setActiveUser={setActiveUser} />
      )}
      <Footer />
    </div>
  );
};
export default App;
