/**
 * @module gameService
 * @description exports functions for interacting with the backend game service
 */
import * as utils from "../utils/utilsCommon";
import { gameServiceURL } from "../config/urlConfig";

export const legalMoves = (matchId, position) => {
  const url = `${gameServiceURL}/legalMoves?matchId=${matchId}&piecePosition=${position}`;

  return utils
    .basicAPI(url, "Get Legal Moves", {
      method: "GET",
    })
    .then((response) => {
      if (response) {
        return response;
      }
      return false;
    })
    .catch((error) => {
      console.error("Error:", error);
      return false;
    });
};

export const move = (matchId, fromPosition, toPosition) => {
  const url = `${gameServiceURL}/move?matchId=${matchId}&fromPosition=${fromPosition}&toPosition=${toPosition}`;

  return utils
    .basicAPI(url, "Make A Move", {
      method: "PATCH",
    })
    .then((response) => {
      if (response) {
        return response;
      }
      return false;
    })
    .catch((error) => {
      console.error("Error:", error);
      return false;
    });
};
