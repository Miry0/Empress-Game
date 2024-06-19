package model;
// classe bean che gestisce i giochi del catalogo; 

import java.io.Serializable;

//import java.java.serializable; 

public class Game_bean implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id_gioco;
	private String nome;
	private String piattaforma;
	private String genere;
	private float prezzo; 
	private int g_uscita;
	private int m_uscita;
	private int a_uscita;
	private int quantita;

	//creiamo un bean per ogni tabella del database. Da qui andiamo a creare il nostro DAO che ci servirà per connetterci al DataSource e, poi, al DB;  
	public Game_bean() {
		id_gioco = -1;
		nome = "";
		piattaforma = "";
		genere = "";
		g_uscita=-1; 
		m_uscita=-1;
		a_uscita=-1;
		quantita = 0;
	}
//get e set per l'id; 
	public int get_id_gioco() {
		return id_gioco;
	}

	public void set_id_gioco(int id_gioco) {
		this.id_gioco = id_gioco;
	}
	//get e set per nome; 
	public String get_nome() {
		return nome;
	}

	public void set_nome(String nome) {
		this.nome = nome;
	}
	
//get e set per piattaforma; 
	public String get_piattaforma() {
		return piattaforma;
	}

	public void set_piattaforma(String piattaforma) {
		this.piattaforma = piattaforma;
	}
	
//get e set per genere; 
	public String get_genere() {
		return genere;
	}

	public void set_genere(String genere) {
		this.genere = genere;
	}

//get e set per prezzo; 
	public float get_prezzo() {
		return prezzo;
	}

	public void set_prezzo(float prezzo) {
		this.prezzo = prezzo;
	}

//get e set per giorno di uscita del gioco; 
	public int get_g_uscita() {
		return g_uscita;
	}

	public void set_g_uscita(int g_uscita) {
		this.g_uscita = g_uscita;
	}

//get e set per mese di uscita del gioco; 
	public int get_m_uscita() {
		return m_uscita;
	}

	public void set_m_uscita(int m_uscita) {
		this.m_uscita = m_uscita;
	}

//get e set per anno di uscita del gioco; 

	public int get_a_uscita() {
		return a_uscita;
	}

	public void set_a_uscita(int a_uscita) {
		this.a_uscita = a_uscita;
	}
	
//get e set per quantità ; 
	
	public int get_quantita() {
		return g_uscita;
	}

	public void set_quantita(int quantita) {
		this.quantita = quantita;
	}

	@Override  //metodo per la stampa di una tupla della tabella "GIOCHI" del DB; 
	public String toString() {
		return id_gioco + " " + nome + " " + piattaforma + " " + genere + " " + g_uscita+"/"+m_uscita+"/"+"a_uscita"+" "+quantita;
	}

}
