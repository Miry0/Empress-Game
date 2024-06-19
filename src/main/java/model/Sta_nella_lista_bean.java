package model;
// classe bean che gestisce la lista dei desideri; 

import java.io.Serializable;

//import java.java.serializable; 

public class Sta_nella_lista_bean implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id_lista;	//chiave primaria
	private int id_utente;  //chiave esterna. La poniamo come intera, invece che di tipo Utente_bean, in modo che ci si possa lavorare in maniera meno faticosa;
	private int id_gioco; //chiave esterna; 
	
	

	//creiamo un bean per ogni tabella del database. Da qui andiamo a creare il nostro DAO che ci servirà per connetterci al DataSource e, poi, al DB;  
	    
	    // Costruttore
	    public Sta_nella_lista_bean() {
	        id_lista = -1;
	        id_utente = -1; // inizializziamo l'attributo id_utente nel costruttore per rvitare che si possa essere uguale a null; 
	        id_gioco=-1; 
	       ;
	    }

	    // Getter e setter
	    public int get_id_lista() {
	        return id_lista;
	    }

	    public void set_id_lista(int id_lista) {
	        this.id_lista = id_lista;
	    }

	    public int get_id_utente() {
	        return id_utente;
	    }

	    public void set_id_utente(int id_utente) {
	        this.id_utente =id_utente;
	    }   
	    
	    public int get_id_gioco() {
	        return id_gioco;
	    }

	    public void set_id_gioco(int id_gioco) {
	        this.id_gioco =id_gioco;
	    }   

		@Override //metodo per la stampa di una tupla della tabella "LISTA_DESIDERI" del DB; 
		public String toString() {
			return id_lista+" "+id_utente+" "+id_gioco;
		}

}
