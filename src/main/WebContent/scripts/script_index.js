window.onload = function() {
    var contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
    
    var styleLink = document.getElementById("style-css");
    if (styleLink) {
        styleLink.href = contextPath + "/Style/style.css";
    }
    
    var logoImg = document.getElementById("logo-img");
    if (logoImg) {
        logoImg.src = contextPath + "/images/logo.png";
    }
    
    var scriptElement = document.querySelector('script[src=""]');
    if (scriptElement) {
        scriptElement.src = contextPath + "/scripts/script_index.js";
    }
    
    var profileLink = document.getElementById("profile-link");
    if (profileLink) {
        profileLink.addEventListener("click", function(event) {
            if (!isUserAuthenticated()) {
                event.preventDefault(); // Impedisci il comportamento predefinito del link
                window.location.href = contextPath + "/scripts/Pagina_login.jsp"; // Reindirizza alla pagina di login se non sei loggato
            }
            // Se l'utente è autenticato, lascia che il link funzioni normalmente e reindirizzi a "profilo.html"
        });
    }
};

// Funzione per mostrare/nascondere il menu
function toggleMenu() {
    var menu = document.getElementById("menu");
    if (menu) {
        var menuLeft = window.getComputedStyle(menu).getPropertyValue("left");

        if (menuLeft === "-220px") {
            menu.style.left = "0"; // Mostra il menu spostandolo verso destra
        } else {
            menu.style.left = "-220px"; // Nascondi il menu spostandolo completamente al di fuori dello schermo a sinistra
        }
    }
}

// Funzione per controllare se l'utente è autenticato usando i cookie
function isUserAuthenticated() {
    return document.cookie.split(';').some((item) => item.trim().startsWith('session='));
}

function validateForm() {
    var isValid = true;

    var username = document.getElementById('username').value.trim();
    var password = document.getElementById('password').value.trim();

    var usernameError = document.getElementById('usernameError');
    var passwordError = document.getElementById('passwordError');

    usernameError.textContent = '';
    passwordError.textContent = '';

    if (username === '') {
        usernameError.textContent = 'Compilare il campo';
        isValid = false;
    }

    if (password === '') {
        passwordError.textContent = 'Compilare il campo';
        isValid = false;
    }

    return isValid;
}