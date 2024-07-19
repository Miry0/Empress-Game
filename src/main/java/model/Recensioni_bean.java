package model;
// classe bean che gestisce carrello; 

import java.io.Serializable;
import java.sql.Date;

//import java.java.serializable; 

public class Recensioni_bean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id_recensione;  //chiave primaria; 
	private String nome_utente; 
	private int id_gioco; 
	private String testo; 
	
	
	

	//creiamo un bean per ogni tabella del database. Da qui andiamo a creare il nostro DAO che ci servirà per connetterci al DataSource e, poi, al DB;  
	    
	    // Costruttore
	    public Recensioni_bean() {
	    	 nome_utente = null;
	    	 id_gioco=-1; 
	    	 testo=null; 
	    }
	    
	 // Getter e setter id_recennione
	    public int get_id_recensione() {
	        return id_recensione;
	    }

	    public void set_id_recensione(int id_recensione) {
	        this.id_recensione = id_recensione;
	    }
	    
	    // Getter e setter nome_utente
	    public String get_nome_utente() {
	        return nome_utente;
	    }

	    public void set_nome_utente(String nome_utente) {
	        this.nome_utente = nome_utente;
	    }
	    
	    /// Altri getter e setter
	    public int get_id_gioco() {
	        return id_gioco;
	    }

	    public void set_id_gioco(int id_gioco) {
	        this.id_gioco = id_gioco;
	    }
	    
	 // Getter e setter testo
	    public String get_testo() {
	        return testo;
	    }

	    public void set_testo(String testo) {
	        this.testo = testo;
	    }
	    
	 // Metodo toString per la stampa dei dettagli del bean
	    @Override
	    public String toString() {
	        return id_recensione + " " + nome_utente+ " " + id_gioco+ " " + testo;
	    }
}
