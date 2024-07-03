package model;

public class Sta_nella_lista_bean {
    private int id_lista;
    private String nome_utente;
    private int id_gioco;
    private String nome_gioco;
    private byte[] immagine;

    // Getters e setters
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

    public String get_nome_gioco() {
        return nome_gioco;
    }

    public void set_nome_gioco(String nome_gioco) {
        this.nome_gioco = nome_gioco;
    }

    public byte[] get_immagine() {
        return immagine;
    }

    public void set_immagine(byte[] immagine) {
        this.immagine = immagine;
    }
}
