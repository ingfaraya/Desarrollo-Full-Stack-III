document.addEventListener("DOMContentLoaded", async () => {
    const token = localStorage.getItem("jwtToken");
    
    if (!token) {
        showAlert("Debes iniciar sesión para acceder a las recetas.", "warning");
        window.location.href = "/login";
        return;
    }

    try {
        const response = await fetch("/api/recetas", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        });

        if (response.ok) {
            const recetas = await response.json();
            displayRecetas(recetas);
        } else if (response.status === 403 || response.status === 401) {
            showAlert("Sesión expirada. Por favor, inicia sesión nuevamente.", "warning");
            localStorage.removeItem("jwtToken");
            window.location.href = "/login";
        } else {
            showAlert("Error al obtener recetas. Por favor, intenta nuevamente más tarde.", "danger");
            console.error("Error al obtener recetas:", response.status);
        }
    } catch (error) {
        showAlert("Error al obtener recetas. Por favor, verifica tu conexión.", "danger");
        console.error("Error al obtener recetas:", error);
    }
});

function displayRecetas(recetas) {
    const recetasContainer = document.getElementById("recetasContainer");
    recetasContainer.innerHTML = "";

    recetas.forEach((receta) => {
        const recetaElement = document.createElement("div");
        recetaElement.classList.add("card", "mb-3", "shadow-sm");

        recetaElement.innerHTML = `
            <div class="card-body">
                <h5 class="card-title">${receta.nombre}</h5>
                <p class="card-text">${receta.descripcion}</p>
                <h6 class="card-subtitle mb-2 text-muted">Ingredientes:</h6>
                <p class="card-text">${receta.ingredientes}</p>
            </div>
        `;

        recetasContainer.appendChild(recetaElement);
    });
}

function showAlert(message, type) {
    const alertPlaceholder = document.getElementById("alertPlaceholder");
    const alertElement = document.createElement("div");
    alertElement.className = `alert alert-${type} alert-dismissible fade show`;
    alertElement.role = "alert";
    alertElement.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    `;

    alertPlaceholder.appendChild(alertElement);

    setTimeout(() => {
        alertElement.remove();
    }, 3000); // Alert disappears after 3 seconds
}
