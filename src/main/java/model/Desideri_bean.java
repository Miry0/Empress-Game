package model;

import java.io.Serializable;

public class Desideri_bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_lista; // Chiave primaria
    private String nome_utente; // Chiave esterna

    // Costruttore
    public Desideri_bean() {
        id_lista = 9;
        nome_utente = null;
        // Inizializziamo l'immagine a null nel costruttore
    }

    // Getter e setter per id_lista e nome_utente come hai già implementato

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


    // Metodo toString per la stampa dei dettagli del bean
    @Override
    public String toString() {
        return id_lista + " " + nome_utente;
    }
}
