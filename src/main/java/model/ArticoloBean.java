package model;

import java.io.Serializable;

public class ArticoloBean implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private int id; //chiave primaria
    private int nOrdine;
    private int idGioco;
    private int quantita;
    
    public ArticoloBean() {
    	idGioco = -1;
    	nOrdine = -1;
    	quantita = -1;
    }
    
    // Getters e setters dell'id della riga della table
    public int get_id() {
        return id;
    }

    public void set_id(int id) {
        this.id = id;
    }
    
    public int get_quantita() {
        return quantita;
    }

    public void set_quantita(int quantita) {
        this.quantita = quantita;
    }
    
    public void set_id_gioco(int idGioco) {
        this.idGioco = idGioco;
    }

    public int get_id_gioco() {
        return idGioco;
    }

	public int getnOrdine() {
		return nOrdine;
	}

	public void setnOrdine(int nOrdine) {
		this.nOrdine = nOrdine;
	}

}