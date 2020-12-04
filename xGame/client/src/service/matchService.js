/**
 * @module matchService
 * @description exports functions for interacting with the backend match service
 */
import * as utils from "../utils/utilsCommon";
import { matchServiceURL } from "../config/urlConfig";

export const acceptInvite = (matchId) => {
  const url = `${matchServiceURL}/accept?matchId=${matchId}`;

  return utils
    .basicAPI(url, "Accept Invite", {
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

export const createMatch = (creationObject) => {
  const { whiteID, blackID } = creationObject;
  const url = `${matchServiceURL}?whiteId=${whiteID}&blackId=${blackID}`;

  return utils
    .basicAPI(url, "Create Match", {
      method: "POST",
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

export const getMatch = (matchId) => {
  const url = `${matchServiceURL}?matchId=${matchId}`;

  return utils
    .basicAPI(url, "Get Match", {
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

export const getActiveMatches = (playerId) => {
  const url = `${matchServiceURL}es?playerId=${playerId}`;

  return utils
    .basicAPI(url, "Get Active Matches", {
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
