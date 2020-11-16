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
      method: "PATCH"
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
