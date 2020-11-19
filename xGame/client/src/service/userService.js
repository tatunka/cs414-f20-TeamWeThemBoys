/**
 * @module userService
 * @description exports functions for interacting with the backend user service
 */
import * as utils from "../utils/utilsCommon";
import { userServiceURL } from "../config/urlConfig";

export const register = (registerObject) => {
  const { nickname, email, password } = registerObject;
  const url = `${userServiceURL}/register`;

  return utils
    .basicAPI(url, "Register User", {
      method: "POST",
      body: JSON.stringify({
        nickname: nickname,
        email: email,
        password: password
      })
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

export const login = (loginObject) => {
  const { email, password } = loginObject;
  const url = `${userServiceURL}/login`;

  return utils
    .basicAPI(url, "Login User", {
      method: "POST",
      body: JSON.stringify({
        email: email,
        password: password
      })
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

export const search = (param) => {
  const url = `${userServiceURL}/search?param=${param}`;

  return utils
    .basicAPI(url, "Find User", {
      method: "GET"
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
