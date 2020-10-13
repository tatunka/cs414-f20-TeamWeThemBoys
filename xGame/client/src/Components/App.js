import React, { useState } from "react";

import LoginContainer from "./Login/LoginContainer";
import Profile from "./Profile/Profile";
import Match from "./Match/Match";
import MatchSelect from "./MatchSelect/MatchSelect";
import Header from "./Margins/Header";
import Footer from "./Margins/Footer";

import "./App.css";

const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  return (
    <div className="App">
      <Header />
      {isLoggedIn ? (
        <div className="pages">
          <Profile />
          <MatchSelect />
          <Match />
        </div>
      ) : (
        <LoginContainer setIsLoggedIn={setIsLoggedIn} />
      )}
      <Footer />
    </div>
  );
};
export default App;
