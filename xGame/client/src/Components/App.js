import React, { useState } from "react";

import LoginContainer from "./Login/LoginContainer";
import Match from "./Match/Match";
import MatchSelect from "./MatchSelect/MatchSelect";
import Header from "./Margins/Header";
import Footer from "./Margins/Footer";

import "./App.css";
import * as messageService from "../service/messageService";

const App = () => {
  const [activeUser, setActiveUser] = useState({ isLoggedIn: false });

  const logInUser = async (user) => {
    const notifications = await messageService.getMessages(user?.id);
    setActiveUser({ ...user, isLoggedIn: true, notifications: notifications });
  };

  const logOutUser = () => {
    setActiveUser({ isLoggedIn: false });
  };

  return (
    <div className="App">
      <Header activeUser={activeUser} logOutUser={logOutUser} />
      {activeUser.isLoggedIn ? (
        <div className="pages">
          <MatchSelect />
          <Match />
        </div>
      ) : (
        <LoginContainer logInUser={logInUser} />
      )}
      <Footer />
    </div>
  );
};
export default App;
