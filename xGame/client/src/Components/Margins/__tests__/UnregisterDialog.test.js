import "../../../enzyme.config.js";
import React from "react";
import { shallow } from "enzyme";

import MatchCreateDialog from "../UnregisterDialog";

function testInitialState() {
  const app = shallow(<UnregisterDialog />);
  expect(app.html()).toMatchSnapshot();
}

test("Testing deregistration dialog initial state", testInitialState);