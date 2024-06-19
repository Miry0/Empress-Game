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
	id_utente int AUTO_INCREMENT, 
    nome varchar(20) NOT NULL, 
	cognome varchar(20) NOT NULL, 
    _password_ varchar(8) NOT NULL, 
    tipo varchar(20) NOT NULL, 
	g_nascita int NOT NULL, 
	m_nascita int NOT NULL, 
	a_nascita int NOT NULL, 
    
   PRIMARY KEY (id_utente) 
); 

CREATE TABLE LISTA_DESIDERI
(

	id_lista int NOT NULL, 
    id_utente int NOT NULL, 
    
    PRIMARY KEY(id_lista),
    FOREIGN KEY(id_utente) REFERENCES UTENTI(id_utente)
); 

CREATE TABLE sta_nella_lista
(
	id_lista int AUTO_INCREMENT, 
	id_utente int NOT NULL, 
    id_gioco int NOT NULL, 
    
    PRIMARY KEY(id_lista),
    FOREIGN KEY(id_utente) REFERENCES LISTA_DESIDERI(id_utente), 
    FOREIGN KEY(id_gioco) REFERENCES GIOCHI(id_gioco)
); 

CREATE TABLE CARRELLO
(
	n_ordine int AUTO_INCREMENT, 
    id_utente varchar(20) NOT NULL, 
    metodo_pagamento varchar(20) NOT NULL, 
    totale float NOT NULL, 
    g_ordine int NOT NULL, 
	m_ordine int NOT NULL, 
	a_ordine int NOT NULL, 
    
    PRIMARY KEY(n_ordine), 
    foreign key(id_utente) REFERENCES UTENTI(id_utente)
); 

CREATE TABLE sta_nel_carrello
(
	n_ordine int NOT NULL, 
    id_gioco int NOT NULL,
    
    FOREIGN KEY(id_gioco) REFERENCES GIOCHI(id_gioco),
    FOREIGN KEY(n_ordine) REFERENCES CARRELLO(id_gioco)
); 

CREATE TABLE STORICO
(
	n_ordine int NOT NULL, 
    
    FOREIGN KEY (n_ordine) REFERENCES CARRELLO(n_ordine)
);

CREATE TABLE legge
(
	id_utente int NOT NULL, 
    n_ordine int NOT NULL, 
   
	FOREIGN KEY (n_ordine) REFERENCES CARRELLO(n_ordine),
    FOREIGN KEY(id_utente) REFERENCES UTENTI(id_utente)
); 

