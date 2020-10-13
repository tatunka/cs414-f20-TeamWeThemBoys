import "../../../enzyme.config.js";
import React from "react";
import { shallow } from "enzyme";

import Footer from "../Footer";

function testInitialState() {
  const app = shallow(<Footer />);
  expect(app.html()).toMatchSnapshot();
}

test("Testing footer initial state", testInitialState);
