package model;

import java.util.ArrayList;

public class Carrello {
	private ArrayList<Integer> listaGiochi;
	
	//costruttore
	public Carrello() {
		this.listaGiochi = new ArrayList<>();
	}
	
	public void aggiungiGioco(int idGioco){
		listaGiochi.add(idGioco);
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
	
	public boolean removeGiocoByKey(int idGioco){
		if(isCarrelloEmpty())
			return false;
		
		listaGiochi.remove(idGioco);
		return true;
	}
	
	//controlla se un gioco sta nel carrello
	 public boolean contieneGioco(int idGioco) {
	        for (int gioco : listaGiochi) {
	            if (gioco == idGioco) {
	                return true;
	            }
	        }
	        return false;
	    }
}
