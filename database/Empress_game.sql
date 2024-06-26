CREATE SCHEMA Empress_DB; 
USE Empress_DB; 

CREATE TABLE GIOCHI
(
	id_gioco int AUTO_INCREMENT, 
    nome varchar(20) NOT NULL, 
    piattaforma varchar(20) NOT NULL, 
    genere varchar(20) NOT NULL, 
	prezzo float NULL NULL, 
	g_ucita int NOT NULL, 
	m_ucita int NOT NULL, 
	a_ucita int NOT NULL, 
    quantita int,
    
    PRIMARY KEY(id_gioco)
); 

CREATE TABLE UTENTI
(
	nome_utente varchar(20) , 
    nome varchar(20) NOT NULL, 
	cognome varchar(20) NOT NULL, 
    _password varchar(8) NOT NULL, 
    email varchar(50) NOT NULL, 
    tipo varchar(20) NOT NULL, 
	g_nascita int NOT NULL, 
	m_nascita int NOT NULL, 
	a_nascita int NOT NULL, 
    
   PRIMARY KEY (nome_utente) 
); 

CREATE TABLE LISTA_DESIDERI
(

	id_lista int NOT NULL, 
    nome_utente varchar(20) NOT NULL, 
    
    PRIMARY KEY(id_lista),
    FOREIGN KEY(nome_utente) REFERENCES UTENTI(nome_utente)
); 

/*
CREATE TABLE sta_nella_lista
(
	id_lista int AUTO_INCREMENT, 
	id_utente int NOT NULL, 
    id_gioco int NOT NULL, 
    
    PRIMARY KEY(id_lista),
    FOREIGN KEY(id_utente) REFERENCES LISTA_DESIDERI(id_utente), 
    FOREIGN KEY(id_gioco) REFERENCES GIOCHI(id_gioco)
); 
*/
CREATE TABLE CARRELLO
(
	n_ordine int AUTO_INCREMENT, 
    nome_utente varchar(20) NOT NULL, 
    metodo_pagamento varchar(20) NOT NULL, 
    totale float NOT NULL, 
    g_ordine int NOT NULL, 
	m_ordine int NOT NULL, 
	a_ordine int NOT NULL, 
    
    PRIMARY KEY(n_ordine), 
    foreign key(nome_utente) REFERENCES UTENTI(nome_utente)
); 

/*
CREATE TABLE sta_nel_carrello
(
	n_ordine int NOT NULL, 
    id_gioco int NOT NULL,
    
    FOREIGN KEY(id_gioco) REFERENCES GIOCHI(id_gioco),
    FOREIGN KEY(n_ordine) REFERENCES CARRELLO(id_gioco)
); 
*/

CREATE TABLE STORICO
(
	n_ordine int NOT NULL, 
    
    FOREIGN KEY (n_ordine) REFERENCES CARRELLO(n_ordine)
);

/*
CREATE TABLE legge
(
	id_utente int NOT NULL, 
    n_ordine int NOT NULL, 
   
	FOREIGN KEY (n_ordine) REFERENCES CARRELLO(n_ordine),
    FOREIGN KEY(id_utente) REFERENCES UTENTI(id_utente)
); 
*/
-- Inserimenti nella tabella UTENTI con nomi utente univoci
INSERT INTO UTENTI (nome_utente, nome, cognome, _password, email, tipo, g_nascita, m_nascita, a_nascita) 
VALUES 
("Alex@123!", "Alessandro", "D'Africa", "password", "alessandro.dafrica@example.com", "admin", 12, 12, 2012),
("JKook@789!", "Jungkook", "Jeon", "password", "jungkook.jeon@example.com", "base", 1, 8, 1997),  
("TaeHyung!23", "Taehyung", "Kim", "password", "taehyung.kim@example.com", "base", 30, 12, 1995),  
("Jimin@!456", "Jimin", "Park", "password", "jimin.park@example.com", "base", 13, 1, 1995);
("RM@345!abc", "Namjoon", "Kim", "password", "namjoon.kim@example.com", "base", 12, 9, 1994),
("Suga@123$", "Yoongi", "Min", "password", "yoongi.min@example.com", "base", 9, 3, 1993),  
("Hobi@789@", "Hoseok", "Jung", "password", "hoseok.jung@example.com", "base", 18, 2, 1994),  
("Jin@!567@", "Seokjin", "Kim", "password", "seokjin.kim@example.com", "base", 4, 12, 1992);

-- Inserimenti nella tabella GIOCHI
INSERT INTO GIOCHI (nome, piattaforma, genere, prezzo, g_uscita, m_uscita, a_uscita, quantita) 
VALUES 
('Zelda', 'Nintendo', 'Azione', 59.99, 3, 3, 2017, 10),
('FIFA 21', 'PlayStation', 'Sport', 49.99, 6, 10, 2020, 25),
('Minecraft', 'PC', 'Avventura', 26.95, 18, 11, 2011, 100),
('Cyberpunk 2077', 'PC', 'RPG', 59.99, 10, 12, 2020, 50);

-- Inserimenti nella tabella LISTA_DESIDERI
INSERT INTO LISTA_DESIDERI (nome_utente) 
VALUES 
("Alex@123!"), 
("JKook@789!"), 
("TaeHyung!23"), 
("Jimin@!456"),
("RM@345!abc"),
("Suga@123$"),
("Hobi@789@"),
("Jin@!567@");

-- Inserimenti nella tabella CARRELLO
INSERT INTO CARRELLO (nome_utente, metodo_pagamento, totale, g_ordine, m_ordine, a_ordine) 
VALUES 
("Alex@123!", 'Carta di credito', 59.99, 12, 6, 2023),
("JKook@789!", 'PayPal', 49.99, 15, 6, 2023),
("TaeHyung!23", 'Carta di credito', 26.95, 18, 6, 2023),
("Jimin@!456", 'Bonifico', 59.99, 21, 6, 2023),
("RM@345!abc", 'PayPal', 39.99, 24, 6, 2023),
("Suga@123$", 'Carta di credito', 19.99, 27, 6, 2023),
("Hobi@789@", 'Bonifico', 49.99, 30, 6, 2023),
("Jin@!567@", 'Carta di credito', 29.99, 3, 7, 2023);

-- Inserimenti nella tabella STORICO
INSERT INTO STORICO (n_ordine) 
VALUES 
(1), 
(2), 
(3), 
(4),
(5),
(6),
(7),
(8);


