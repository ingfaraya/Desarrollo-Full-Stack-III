// src/main/resources/static/js/main.js

document.addEventListener("DOMContentLoaded", () => {
    // Código para ejecutar después de que el DOM haya cargado completamente
});

async function buscarRecetas() {
    try {
        const response = await fetchWithAuth("/api/recetas");
        if (!response.ok) {
            throw new Error("Error al obtener recetas");
        }
        const recetas = await response.json();
        mostrarRecetas(recetas);
    } catch (error) {
        console.error("Error:", error);
    }
}

function mostrarRecetas(recetas) {
    const recetasContainer = document.getElementById("recetasContainer");
    recetasContainer.innerHTML = ""; // Limpiar el contenedor

    recetas.forEach((receta) => {
        const recetaDiv = document.createElement("div");
        recetaDiv.classList.add("receta");
        recetaDiv.innerHTML = `<h3>${receta.titulo}</h3><p>${receta.descripcion}</p>`;
        recetasContainer.appendChild(recetaDiv);
    });
}
