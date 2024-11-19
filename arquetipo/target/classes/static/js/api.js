// src/main/resources/static/js/api.js

function getAuthToken() {
    return localStorage.getItem("token");
}

async function fetchWithAuth(url, options = {}) {
    const token = getAuthToken();
    if (!options.headers) {
        options.headers = {};
    }
    if (token) {
        options.headers["Authorization"] = "Bearer " + token;
    }
    const response = await fetch(url, options);
    if (response.status === 401) {
        // Token inválido o expirado, redirigir al login
        window.location.href = "/login";
    }
    return response;
}
