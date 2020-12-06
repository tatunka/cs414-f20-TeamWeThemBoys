import React, { useState } from "react";

import LoginContainer from "./Login/LoginContainer";
import Match from "./Match/Match";
import MatchSelect from "./MatchSelect/MatchSelect";
import Header from "./Margins/Header";
import Footer from "./Margins/Footer";

import "./App.css";
import * as messageService from "../service/messageService";
import * as matchService from "../service/matchService";

const App = () => {
  const [activeUser, setActiveUser] = useState({ isLoggedIn: false });
  const [activeMatch, setActiveMatch] = useState(null);

  const logInUser = async (user) => {
    const notifications = await messageService.getMessages(user?.id);
    setActiveUser({ ...user, isLoggedIn: true, notifications: notifications });
  };

  const logOutUser = () => {
    setActiveUser({ isLoggedIn: false });
  };

  const fetchNotifications = async () => {
    const notifications = await messageService.getMessages(activeUser?.id);
    setActiveUser({ ...activeUser, notifications: notifications });
  };

  const refreshActiveMatch = async () => {
    const newMatch = await matchService.getMatch(activeMatch?.id);
    setActiveMatch(newMatch);
  };

  return (
    <div className="App">
      <Header
        activeUser={activeUser}
        logOutUser={logOutUser}
        setActiveMatch={setActiveMatch}
        fetchNotifications={fetchNotifications}
      />
      {activeUser.isLoggedIn ? (
        <div className="pages">
          <MatchSelect
            activeUser={activeUser}
            setActiveMatch={setActiveMatch}
          />
          <Match
            activeUser={activeUser}
            activeMatch={activeMatch}
            setActiveMatch={setActiveMatch}
            refreshActiveMatch={refreshActiveMatch}
          />
        </div>
      ) : (
        <LoginContainer logInUser={logInUser} />
      )}
      <Footer />
    </div>
  );
};
export default App;
