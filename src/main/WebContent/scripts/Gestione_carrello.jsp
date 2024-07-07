<!-- jsp usata per gestire l'aggiunta di prodotti al carrello -->


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Index</title>
</head>
<body>
    <h2>Aggiungi elemento al carrello</h2>
    <form action="carrello" method="post">
        <input type="hidden" name="action" value="add">
        <label for="id_utente">ID Utente:</label>
        <input type="number" id="id_utente" name="id_utente" required><br>
        <label for="metodo_pagamento">Metodo di pagamento:</label>
        <input type="text" id="metodo_pagamento" name="metodo_pagamento" required><br>
        <label for="totale">Totale:</label>
        <input type="number" step="0.01" id="totale" name="totale" required><br>
        <label for="g_ordine">Giorno Ordine:</label>
        <input type="number" id="g_ordine" name="g_ordine" required><br>
        <label for="m_ordine">Mese Ordine:</label>
        <input type="number" id="m_ordine" name="m_ordine" required><br>
        <label for="a_ordine">Anno Ordine:</label>
        <input type="number" id="a_ordine" name="a_ordine" required><br>
        <label for="immagine">Immagine:</label>
        <input type="text" id="immagine" name="immagine" required><br> <!-- Supponendo che l'immagine sia una stringa base64 -->
        <button type="submit">Aggiungi al carrello</button>
    </form>
</body>
</html>
