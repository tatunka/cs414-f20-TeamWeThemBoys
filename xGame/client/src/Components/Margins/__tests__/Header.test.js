import "../../../enzyme.config.js";
import React from "react";
import { shallow } from "enzyme";

import Header from "../Header";

function testInitialState() {
  const app = shallow(<Header />);
  expect(app.html()).toMatchSnapshot();
}

test("Testing header initial state", testInitialState);
