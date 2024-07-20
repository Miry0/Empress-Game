package model;

import java.io.Serializable;

public class Game_bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_gioco;
    private String nome;
    private String piattaforma;
    private String genere;
    private float prezzo;
    private int g_uscita;
    private int m_uscita;
    private int a_uscita;
    private int quantita;
    private String immagine; // Aggiunta variabile per l'immagine del gioco

    // Costruttore
    public Game_bean() {
        //id_gioco = -1;
        nome = "";
        piattaforma =null;
        genere = null;
        prezzo = 0.0f;
        g_uscita = -1;
        m_uscita = -1;
        a_uscita = -1;
        quantita = 0;
        immagine = null; // Inizializzazione dell'immagine
    }

    // Getter e setter per l'immagine
    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    // Altri getter e setter
    public int get_id_gioco() {
        return id_gioco;
    }

    public void set_id_gioco(int id_gioco) {
        this.id_gioco = id_gioco;
    }

    public String get_nome() {
        return nome;
    }

    public void set_nome(String nome) {
        this.nome = nome;
    }

    public String get_piattaforma() {
        return piattaforma;
    }

    public void set_piattaforma(String piattaforma) {
        this.piattaforma = piattaforma;
    }

    public String get_genere() {
        return genere;
    }

    public void set_genere(String genere) {
        this.genere = genere;
    }

    public float get_prezzo() {
        return prezzo;
    }

    public void set_prezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public int get_g_uscita() {
        return g_uscita;
    }

    public void set_g_uscita(int g_uscita) {
        this.g_uscita = g_uscita;
    }

    public int get_m_uscita() {
        return m_uscita;
    }

    public void set_m_uscita(int m_uscita) {
        this.m_uscita = m_uscita;
    }

    public int get_a_uscita() {
        return a_uscita;
    }

    public void set_a_uscita(int a_uscita) {
        this.a_uscita = a_uscita;
    }

    public int get_quantita() {
        return quantita;
    }

    public void set_quantita(int quantita) {
        this.quantita = quantita;
    }
    
    public boolean isEmpty(){
    	if(id_gioco == -1)
    		return true;
    	
    	return false;
    }

    @Override
    public String toString() {
        return "Game_bean [id_gioco=" + id_gioco + ", nome=" + nome + ", piattaforma=" + piattaforma + ", genere="
                + genere + ", prezzo=" + prezzo + ", g_uscita=" + g_uscita + ", m_uscita=" + m_uscita + ", a_uscita="
                + a_uscita + ", quantita=" + quantita + "]";
    }
}