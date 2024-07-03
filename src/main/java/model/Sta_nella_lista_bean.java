package model;

import java.io.Serializable;

public class Sta_nella_lista_bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_lista; // chiave primaria
    private String nome_utente; // chiave esterna. La poniamo come intera, invece che di tipo Utente_bean, in modo che ci si possa lavorare in maniera meno faticosa;
    private int id_gioco; // chiave esterna;
    private byte[] immagine; // immagine del prodotto

    // Costruttore
    public Sta_nella_lista_bean() {
        id_lista = -1;
        nome_utente = null;
        id_gioco = -1;
        immagine = null;
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

    public int get_id_gioco() {
        return id_gioco;
    }

    public void set_id_gioco(int id_gioco) {
        this.id_gioco = id_gioco;
    }

    public byte[] get_immagine() {
        return immagine;
    }

    public void set_immagine(byte[] immagine) {
        this.immagine = immagine;
    }

    @Override
    public String toString() {
        return id_lista + " " + nome_utente + " " + id_gioco;
    }
}
