package model;

import java.util.ArrayList;

public class Carrello {
	private ArrayList<Integer> listaGiochi;
	private int idCarrello;
	
	//costruttore
	public Carrello(int idCarrello) {
		this.listaGiochi = new ArrayList<>();
		this.idCarrello = idCarrello;
	}
	
	public void aggiungiGioco(int idGioco){
		listaGiochi.add(idGioco);
	}
	
	public int getIdCarrello(){
		return idCarrello;
	}
	
	public boolean isCarrelloEmpty(){
		return listaGiochi.isEmpty();
	}
	
	public int getGiocoByIndex(int index){
		if(isCarrelloEmpty())
			return 0;
		
		return listaGiochi.get(index);
	}
	
	public int getCarrelloLenght() {
		return listaGiochi.size();
	}
}
