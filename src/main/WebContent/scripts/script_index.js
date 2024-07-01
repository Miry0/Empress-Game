// Funzione per mostrare/nascondere il menu
function toggleMenu() {
  var menu = document.getElementById("menu");
  var menuLeft = window.getComputedStyle(menu).getPropertyValue("left");

  if (menuLeft === "-220px") {
    menu.style.left = "0"; // Mostra il menu spostandolo verso destra
  } else {
    menu.style.left = "-220px"; // Nascondi il menu spostandolo completamente al di fuori dello schermo a sinistra
  }
}

// Funzione per controllare se l'utente è autenticato usando i cookie
function isUserAuthenticated() {
  return document.cookie.split(';').some((item) => item.trim().startsWith('session='));
}

// Aggiungi un listener al link del profilo
document.getElementById("profile-link").addEventListener("click", function(event) {
  if (!isUserAuthenticated()) {
    event.preventDefault(); // Impedisci il comportamento predefinito del link
    window.location.href = "login.html"; // Reindirizza alla pagina di login
  }
  // Se l'utente è autenticato, lascia che il link funzioni normalmente e reindirizzi a "profilo.html"
});
