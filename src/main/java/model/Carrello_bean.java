package model;
// classe bean che gestisce carrello; 

import java.io.Serializable;

//import java.java.serializable; 

public class Carrello_bean implements Serializable {

	private static final long serialVersionUID = 1L;

	private int n_ordine;
	private int id_utente;  //chiave esterna. La poniamo come intera, invece che di tipo Utente_bean, in modo ch ci si possa lavorare in maniera meno faticosa; 
	private String metodo_pagamento;
	private Float totale;
	private int g_ordine; 
	private int m_ordine; 
	private int a_ordine; 
	

	//creiamo un bean per ogni tabella del database. Da qui andiamo a creare il nostro DAO che ci servirà per connetterci al DataSource e, poi, al DB;  
	    
	    // Costruttore
	    public Carrello_bean() {
	        n_ordine = -1;
	        id_utente = -1; // inizializziamo l'attributo id_utente nel costruttore per rvitare che si possa essere uguale a null; 
	        metodo_pagamento = "";
	        totale = -1f;
	        g_ordine = -1; 
	        m_ordine = -1;
	        a_ordine = -1;
	    }

	    // Getter e setter
	    public int get_n_ordine() {
	        return n_ordine;
	    }

	    public void set_n_ordine(int n_ordine) {
	        this.n_ordine = n_ordine;
	    }

	    public int get_id_utente() {
	        return id_utente;
	    }

	    public void set_id_utente(int id_utente) {
	        this.id_utente =id_utente;
	    }

	    public String get_metodo_pagamento() {
	        return metodo_pagamento;
	    }

	    public void set_metodo_pagamento(String metodo_pagamento) {
	        this.metodo_pagamento = metodo_pagamento;
	    }

	    public Float get_totale() {
	        return totale;
	    }

	    public void set_totale(Float totale) {
	        this.totale = totale;
	    }

	    public int get_g_ordine() {
	        return g_ordine;
	    }

	    public void set_g_ordine(int g_ordine) {
	        this.g_ordine = g_ordine;
	    }

	    public int get_m_ordine() {
	        return m_ordine;
	    }

	    public void set_m_ordine(int m_ordine) {
	        this.m_ordine = m_ordine;
	    }

	    public int get_a_ordine() {
	        return a_ordine;
	    }

	    public void set_a_ordine(int a_ordine) {
	        this.a_ordine = a_ordine;
	    }


		@Override //metodo per la stampa di una tupla della tabella "CARRELLO" del DB; 
		public String toString() {
			return n_ordine+" "+id_utente + " " + totale + " " + metodo_pagamento + " " + g_ordine+"/"+m_ordine+"/"+"a_ordine";
		}

}
