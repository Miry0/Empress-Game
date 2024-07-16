package model;

public class Sta_nella_lista_bean {
	
	private int id; //chiave primaria
	private int id_lista;
    private String nome_utente;
    private int id_gioco;
    
    public Sta_nella_lista_bean() {
        // this.id_lista = id_lista;
    	id_lista=-1; 
        nome_utente = null;
        id_gioco=-1; 
          // Inizializziamo l'immagine a null nel costruttore
      }
    
    // Getters e setters dell'id della riga della table
    public int get_id() {
        return id;
    }

    public void set_id(int id) {
        this.id = id;
    }
    
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

    // Metodo toString per la stampa dei dettagli del bean
    @Override
    public String toString() {
        return id+ " "+ id_lista + " " + nome_utente+ " " + id_gioco;
    }
}
