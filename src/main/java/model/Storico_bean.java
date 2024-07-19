package model;
// classe bean che gestisce carrello; 

import java.io.Serializable;
import java.sql.Date;

//import java.java.serializable; 

public class Storico_bean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int n_ordine;  //chiave primaria; 
	private String nome_utente; 
	private Date data; 
	private float totale; 
	
	
	

	//creiamo un bean per ogni tabella del database. Da qui andiamo a creare il nostro DAO che ci servirà per connetterci al DataSource e, poi, al DB;  
	    
	    // Costruttore
	    public Storico_bean() {
	    	 nome_utente = null;
	    	 data=null; 
	    	 totale=-1; 
	    }

	    // Getter e setter n_ordine
	    public int get_n_ordine() {
	        return n_ordine;
	    }

	    public void set_n_ordine(int n_ordine) {
	        this.n_ordine = n_ordine;
	    }
	    
	 // Getter e setter nome_utente
	    public String get_nome_utente() {
	        return nome_utente;
	    }

	    public void set_nome_utente(String nome_utente) {
	        this.nome_utente = nome_utente;
	    }
	    
	    // Getter e setter data
	    public Date get_data() {
	        return data;
	    }

	    public void set_data(Date data) {
	        this.data = data;
	    }
	    
	 // Getter e setter totale
	    public float get_totale() {
	        return totale;
	    }

	    public void set_totale(float totale) {
	        this.totale = totale;
	    }
	    
	 // Metodo toString per la stampa dei dettagli del bean
	    @Override
	    public String toString() {
	        return n_ordine + " " + nome_utente+ " " + totale+ " " + data;
	    }
}
