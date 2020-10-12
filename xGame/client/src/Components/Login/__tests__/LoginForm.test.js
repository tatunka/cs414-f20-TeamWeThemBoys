import "../../../enzyme.config.js";
import React from "react";
import { shallow } from "enzyme";

import LoginForm from "../LoginForm";

function testInitialState() {
  const app = shallow(<LoginForm />);
  expect(app.html()).toMatchSnapshot();
}

test("Testing login initial state", testInitialState);
