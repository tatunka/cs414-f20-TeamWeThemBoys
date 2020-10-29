import "../../../enzyme.config.js";
import React from "react";
import { shallow } from "enzyme";

import Match from "../Match";

function testInitialState() {
  const app = shallow(<Match isOpen={true}/>);
  expect(app.html).toMatchSnapshot();
}

test("Testing login initial state", testInitialState);
