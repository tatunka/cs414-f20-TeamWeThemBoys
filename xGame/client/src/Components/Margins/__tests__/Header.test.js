import "../../../enzyme.config.js";
import React from "react";
import { shallow } from "enzyme";
import { Button } from "@material-ui/core";

import Header from "../Header";

function testLogoutButton() {
  let activeUser = {
    id: 1,
    nickname: "greg",
    email: "gmail.com",
    isLoggedIn: true,
  };
  const app = shallow(
    <Header
      activeUser={activeUser}
      setActiveUser={(user) => {
        activeUser = user;
      }}
    />
  );
  app.find(Button).simulate("click");
  expect(activeUser).toEqual({ isLoggedIn: false });
}

test("Testing header logout button", testLogoutButton);
