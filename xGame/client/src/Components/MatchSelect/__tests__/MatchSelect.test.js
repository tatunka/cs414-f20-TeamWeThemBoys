import "../../../enzyme.config.js";
import React from "react";
import { shallow } from "enzyme";

import MatchSelect from "../MatchSelect";

function testInitialState() {
  const app = shallow(<MatchSelect />);
  expect(app.html()).toMatchSnapshot();
}

test("Testing login initial state", testInitialState);
