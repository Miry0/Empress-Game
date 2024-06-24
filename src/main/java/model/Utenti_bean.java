package model;
// classe bean che gestisce gli utenti, sia di tipo base che amministratori; 

import java.io.Serializable;

//import java.java.serializable; 

public class Utenti_bean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome_utente;
	private String nome;
	private String cognome;
	private String _password;
	private String tipo; 
	private int g_nascita;
	private int m_nascita;
	private int a_nascita;

	//creiamo un bean per ogni tabella del database. Da qui andiamo a creare il nostro DAO che ci servirà per connetterci al DataSource e, poi, al DB;  
	public Utenti_bean() {
		nome_utente = "";
		nome = "";
		cognome = "";
		_password = "";
		g_nascita=-1; 
		m_nascita=-1;
		a_nascita=-1;
	}
//get e set per l'id; 
	public String get_nome_utente() {
		return nome_utente;
	}

	public void set_nome_utente(String nome_utente) {
		this.nome_utente = nome_utente;
	}
	//get e set per nome; 
	public String get_nome() {
		return nome;
	}

	public void set_nome(String nome) {
		this.nome = nome;
	}
	
	//get e set per cognome; 
		public String get_cognome() {
			return cognome;
		}

		public void set_cognome(String cognome) {
			this.cognome = cognome;
		}
		
	//get e set per password; 
		public String get_password() {
			return _password;
		}
	
		public void set_password(String _password) {
			this._password = _password;
		}
	
	//get e set per tipo; 
		public String get_tipo() {
			return tipo;
		}
	
		public void set_tipo(String tipo) {
			this.tipo =tipo;
		}
	
	//get e set della data di nascita dell'utenteo; 
		public int get_g_nascita() {
			return g_nascita;
		}
	
		public void set_g_nascita(int g_nascita) {
			this.g_nascita = g_nascita;
		}
	
	//get e set per mese di uscita del gioco; 
		public int get_m_nascita() {
			return m_nascita;
		}
	
		public void set_m_nascita(int m_nascita) {
			this.m_nascita = m_nascita;
		}
	
	//get e set per anno di uscita del gioco; 
	
		public int get_a_nascita() {
			return a_nascita;
		}
	
		public void set_a_nascita(int a_nascita) {
			this.a_nascita = a_nascita;
		}
		

		@Override //metodo per la stampa di una tupla della tabella "UTENTI" del DB; 
		public String toString() {
			return nome_utente + " " + nome + " " + cognome + " " + tipo + " " + g_nascita+"/"+m_nascita+"/"+"a_nascita";
		}

}
