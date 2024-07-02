package model;
// classe bean che gestisce la lista dei desideri; 

import java.io.Serializable;

//import java.java.serializable; 

public class Desideri_bean implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id_lista;	//chiave primaria
	private String nome_utente;  //chiave esterna. La poniamo come intera, invece che di tipo Utente_bean, in modo che ci si possa lavorare in maniera meno faticosa; 
	
	

	//creiamo un bean per ogni tabella del database. Da qui andiamo a creare il nostro DAO che ci servirà per connetterci al DataSource e, poi, al DB;  
	    
	    // Costruttore
	    public Desideri_bean() {
	        id_lista = -1;
	        nome_utente = null; // inizializziamo l'attributo id_utente nel costruttore per rvitare che si possa essere uguale a null; 
	       ;
	    }

	    // Getter e setter
	    public int get_id_lista() {
	        return id_lista;
	    }

	    public void set_id_lista(int id_lista) {
	        this.id_lista = id_lista;
	    }

	    public String get_nome_utente() {
			return nome_utente;
		}

		public void set_nome_utente(String nome_utente) {
			this.nome_utente = nome_utente;
		}
		//get e set per nome; 
		@Override //metodo per la stampa di una tupla della tabella "LISTA_DESIDERI" del DB; 
		public String toString() {
			return id_lista+" "+nome_utente;
		}

}
