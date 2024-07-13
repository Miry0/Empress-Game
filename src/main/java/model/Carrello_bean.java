package model;

import java.io.Serializable;
import java.io.Serializable;
import java.sql.Date;

public class Carrello_bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int n_ordine;
    private String nome_utente;
    private String metodo_pagamento;
    private Float totale;
    private Date data_ordine; // Campo per la data dell'ordine
    private byte[] immagine; // Aggiungi questa variabile per l'immagine

    // Costruttore
    public Carrello_bean() {
        n_ordine = -1;
        nome_utente = null;
        metodo_pagamento = null;
        totale = -1f;
        data_ordine=new Date(System.currentTimeMillis()); // Inizializzazione con la data corrente
        immagine = null; // Inizializzazione dell'immagine
    }

    // Getter e setter per l'immagine
    public byte[] getImmagine() {
        return immagine;
    }

    public void setImmagine(byte[] immagine) {
        this.immagine = immagine;
    }

    // Altri getter e setter
    public int get_n_ordine() {
        return n_ordine;
    }

    public void set_n_ordine(int n_ordine) {
        this.n_ordine = n_ordine;
    }

    public String get_nome_utente() {
        return nome_utente;
    }

    public void set_nome_utente(String nome_utente) {
        this.nome_utente = nome_utente;
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


    public Date get_data_ordine() {
        return data_ordine;
    }

    public void set_data_ordine(Date data_ordine) {
        this.data_ordine = data_ordine;
    }

    @Override
    public String toString() {
        return "n_ordine=" + "nome_utente" + " metodo_pagamento" + " totale" + "data_ordine";
    }
}
