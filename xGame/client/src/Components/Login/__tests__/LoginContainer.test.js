import "../../../enzyme.config.js";
import React from "react";
import { shallow } from "enzyme";

import LoginContainer from "../LoginContainer";

function testInitialState() {
  const app = shallow(<LoginContainer />);
  expect(app.html()).toMatchSnapshot();
}

test("Testing login initial state", testInitialState);
