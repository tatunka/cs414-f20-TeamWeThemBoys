import "../../../enzyme.config.js";
import React from "react";
import { shallow } from "enzyme";

import SignUpForm from "../SignUpForm";

function testInitialState() {
  const app = shallow(<SignUpForm />);
  expect(app.html()).toMatchSnapshot();
}

test("Testing login initial state", testInitialState);
