package model;
// classe bean che gestisce carrello; 

import java.io.Serializable;

//import java.java.serializable; 

public class Legge_bean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id_utente; //chiave esterna; 
	private int n_ordine;  //chiave esterna. La poniamo come intera, invece che di tipo Utente_bean, in modo ch ci si possa lavorare in maniera meno faticosa; 
	
	

	//creiamo un bean per ogni tabella del database. Da qui andiamo a creare il nostro DAO che ci servirà per connetterci al DataSource e, poi, al DB;  
	    
	    // Costruttore
	    public Legge_bean() {
	    	id_utente=-1; 
	        n_ordine = -1;
	        
	    }

	    // Getter e setter
	    public int get_id_utente() {
			return id_utente;
		}

		public void set_id_utente(int id_utente) {
			this.id_utente = id_utente;
		}
		
	    public int get_n_ordine() {
	        return n_ordine;
	    }

	    public void set_n_ordine(int n_ordine) {
	        this.n_ordine = n_ordine;
	    }

	    public String toString() {
	    	return id_utente + " " + n_ordine;	    }

}
