// Crea l'oggetto 
var xhttp = new XMLHttpRequest();

// Definizione funzione 
xhttp.onreadystatechange = function loadRecensione(){ 
    if (this.readyState == 4 && this.status == 200) { 
// Update the web page with the retrieved data 
    document.getElementById("recensione").innerHTML = this.responseText;
    } 
   };

// Invia la richiesta al server
 xhttp.open("GET", "mydata.php", true);
 xhttp.send();