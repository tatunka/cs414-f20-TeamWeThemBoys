import "../../../enzyme.config.js";
import React from "react";
import { shallow } from "enzyme";

import MatchBoard from "../MatchBoard";

const boardState = [
  { pieceName: "\u2659", location: "d1" },
  { pieceName: "\u2659", location: "e2" },
  { pieceName: "\u265F", location: "a4" },
  { pieceName: "\u265F", location: "b5" },
];

function testInitialState() {
  const app = shallow(
    <MatchBoard boardState={boardState} activeColor={"white"} />
  );
  expect(app.find(".whiteBoardSquare").length).toEqual(4 * 8);
  expect(app.find(".blackBoardSquare").length).toEqual(4 * 8);
  expect(app.find(".white").length).toEqual(2);
  expect(app.find(".transparent").length).toEqual(60);
  expect(app.find(".chessPiece").length).toEqual(64);
}

test("Testing MatchBoard initial state", testInitialState);
