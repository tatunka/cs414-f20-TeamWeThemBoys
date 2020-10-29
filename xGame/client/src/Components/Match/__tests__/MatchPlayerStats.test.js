import "../../../enzyme.config.js";
import React from "react";
import { shallow } from "enzyme";

import MatchPlayerStats from "../MatchPlayerStats";

const boardState = [
  { pieceName: "\u2659", location: "f2" },
  { pieceName: "\u2659", location: "f3" },
  { pieceName: "\u2659", location: "g4" },
  { pieceName: "\u2659", location: "g3" },
  { pieceName: "\u2659", location: "h5" },
  { pieceName: "\u2659", location: "e4" },
  { pieceName: "\u2656", location: "e1" },
  { pieceName: "\u2656", location: "h4" },
  { pieceName: "\u2657", location: "f1" },
  { pieceName: "\u2657", location: "h2" },
  { pieceName: "\u2658", location: "g1" },
  { pieceName: "\u2658", location: "h3" },
  { pieceName: "\u2655", location: "g2" },
  { pieceName: "\u2654", location: "h1" },
  { pieceName: "\u265F", location: "b6" },
  { pieceName: "\u265F", location: "c6" },
  { pieceName: "\u265F", location: "c7" },
  { pieceName: "\u265F", location: "d5" },
  { pieceName: "\u265F", location: "d7" },
  { pieceName: "\u265F", location: "e8" },
  { pieceName: "\u265C", location: "d8" },
  { pieceName: "\u265C", location: "a5" },
  { pieceName: "\u265D", location: "a7" },
  { pieceName: "\u265D", location: "c8" },
  { pieceName: "\u265E", location: "a6" },
  { pieceName: "\u265E", location: "b8" },
  { pieceName: "\u265B", location: "b7" },
  { pieceName: "\u265A", location: "a8" },
];

function testInitialState() {
  const app = shallow(
    <MatchPlayerStats
      playerName={"jim"}
      activeColor={"white"}
      turnCounter={0}
      boardState={boardState}
    />
  );
  expect(app.find('.chessPiece').length).toEqual(2);
}

test("Testing MatchPlayerStats initial state", testInitialState);
