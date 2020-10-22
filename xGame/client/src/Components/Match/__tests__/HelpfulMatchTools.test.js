import "../../../enzyme.config.js";
import React from "react";
import Grid from "@material-ui/core/Grid";
import { shallow } from "enzyme";

import {
  findLocation,
  showTable,
  getPieceList,
  createBoardPiece,
} from "../HelpfulMatchTools";

function testFindLocation() {
  let locations = ["a1", "a8", "h1", "h8", "d5", "i5", "f9"];
  let expectedResults = [
    [7, 0],
    [0, 0],
    [7, 7],
    [3, 4],
    [8, 4],
    [5, 8],
  ];
  for (let i = 0; i < location.length; i++) {
    expect(findLocation(locations[i])).toEqual(expectedResults[i]);
  }
}

function testShowTable() {
  let list = ["something", "something Else"];
  let expected = [
    <Grid item key={"row " + 0} style={{ flex: "1" }}>
      something
    </Grid>,
    <Grid item key={"row " + 1} style={{ flex: "1" }}>
      something Else
    </Grid>,
  ];
  expect(showTable(list)).toEqual(expected);
}

function testGetPiecesList() {
  let blackPiecesList = [
    "\u265F",
    "\u265F",
    "\u265F",
    "\u265F",
    "\u265F",
    "\u265F",
    "\u265F",
    "\u265F",
    "\u265C",
    "\u265C",
    "\u265D",
    "\u265D",
    "\u265E",
    "\u265E",
    "\u265B",
    "\u265A",
  ];
  let whitePiecesList = [
    "\u2659",
    "\u2659",
    "\u2659",
    "\u2659",
    "\u2659",
    "\u2659",
    "\u2659",
    "\u2659",
    "\u2656",
    "\u2656",
    "\u2658",
    "\u2658",
    "\u2657",
    "\u2657",
    "\u2655",
    "\u2654",
  ];
  expect(getPieceList("white").sort()).toEqual(whitePiecesList.sort());
  expect(getPieceList("black").sort()).toEqual(blackPiecesList.sort());
}

test("Testing findLocation", testFindLocation);
test("Testing showTable", testShowTable);
test("Testing getPiecesList", testGetPiecesList);
