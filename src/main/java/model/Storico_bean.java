package model;
// classe bean che gestisce carrello; 

import java.io.Serializable;

//import java.java.serializable; 

public class Storico_bean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int n_ordine;  //chiave esterna. La poniamo come intera, invece che di tipo Utente_bean, in modo ch ci si possa lavorare in maniera meno faticosa; 
	
	

	//creiamo un bean per ogni tabella del database. Da qui andiamo a creare il nostro DAO che ci servirà per connetterci al DataSource e, poi, al DB;  
	    
	    // Costruttore
	    public Storico_bean() {
	        n_ordine = -1;
	    }

	    // Getter e setter
	    public int get_n_ordine() {
	        return n_ordine;
	    }

	    public void set_n_ordine(int n_ordine) {
	        this.n_ordine = n_ordine;
	    }

	    public String toString() {
	        return String.valueOf(n_ordine); //accetta un argomento di tipo int e lo restituisce come stringa; 
	    }

}
