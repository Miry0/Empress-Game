package model;

import java.util.ArrayList;
import java.util.Iterator;

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
	
	public int get_idGioco(int c) {
		return listaGiochi.get(c);
	}
	
	 // Metodo per rimuovere un gioco per ID
    public boolean removeGiocoByKey(int idGioco) {
       
    	if (isCarrelloEmpty()) {
            return false;
    	}
        
       int c=0; 
        while (c< getCarrelloLenght()) {
           
            if (listaGiochi.get(c) == idGioco) {
            	listaGiochi.remove(c); 
                return true;
            }
            c++;
        }
       
        return false;
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
