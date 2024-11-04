// src/main/resources/static/js/auth.js

async function login(event) {
    event.preventDefault(); // Prevenir que el formulario se envíe normalmente

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("/auth/token", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ username, password }),
        });

        if (!response.ok) {
            throw new Error("Error en el inicio de sesión");
        }

        const token = await response.text();
        localStorage.setItem("token", token); // Guardar el token en localStorage
        window.location.href = "/home"; // Redirigir al home después de iniciar sesión
    } catch (error) {
        console.error("Error:", error);
        alert("Usuario o contraseña incorrectos");
    }
}
