/** @module utilsCommon */

export const getConsoleStyle = (color) => {
  return `color: ${color}; font-weight: bold; font-size: 0.9rem;`;
};

/**
 * @function logApiCall
 * @param {Object} funcProps - function props
 * @param {string} funcProps.action - The action name
 * @param {string} funcProps.method - The HTTP method used
 * (i.e. "GET", "POST", etc.)
 * @param {string} funcProps.url - URL
 * @param {Object} funcProps.token
 * @param {Object} funcProps.token.name
 * @param {Object} funcProps.token.token_type
 * @param {Object} funcProps.token.access_token
 * @param {Object} funcProps.tableObj - An object to be displayed as a table
 * @param {Array} funcProps.otherLogs - array of other logs
 */
export const logApiCall = (funcProps) => {
  const { action, method, url, bodyObj } = funcProps;
  let color;
  switch (method) {
    case "GET":
      color = "forestgreen";
      break;
    case "POST":
      color = "gold";
      break;
    case "PUT":
      color = "blue";
      break;
    case "DEL":
      color = "red";
      break;
    case "PATCH":
      color = "gray";
      break;
    default:
      color = "teal";
  }
  /* eslint-disable no-console */
  console.groupCollapsed(
    `%cAPI CALL > ${action} ${method ? `[${method}]` : ""}${
      url ? `: ${url}` : ""
    }`,
    getConsoleStyle(color)
  );
  if (url) {
    console.trace(`%cURL: ${url}`, "font-weight: bold");
  }
  if (bodyObj) {
    console.table(bodyObj);
  }

  console.groupEnd();
  /* eslint-enable no-console */
};

export const basicAPI = (url, description, fetchOptions = {}) => {
  logApiCall({
    action: description,
    method: fetchOptions.method || "GET",
    url,
    bodyObj: fetchOptions.body || null
  });
  return fetch(url, {
    ...fetchOptions,
    headers: {
      "Content-Type":
        fetchOptions && fetchOptions.body ? "application/json" : undefined
    },
    credentials: "same-origin"
  }).then((response) => {
    return response.json();
  });
};
