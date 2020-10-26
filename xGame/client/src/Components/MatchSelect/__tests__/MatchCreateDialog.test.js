import "../../../enzyme.config.js";
import React from "react";
import { shallow } from "enzyme";

import MatchCreateDialog from "../MatchCreateDialog";

function testInitialState() {
  const app = shallow(<MatchCreateDialog />);
  expect(app.html()).toMatchSnapshot();
}

test("Testing match creation dialog initial state", testInitialState);
