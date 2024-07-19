package model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public class Carrello {
	private LinkedHashMap<Integer, Integer> listaGiochi;
	
	//costruttore
	public Carrello() {
		this.listaGiochi = new LinkedHashMap<>();
	}
	
	public void aggiungiGioco(int idGioco){
		if(listaGiochi.containsKey(idGioco)) {
			int quant = listaGiochi.get(idGioco);
			listaGiochi.replace(idGioco, quant+1);
		} else
			listaGiochi.put(idGioco, 1);
	}
	
	
	public boolean isCarrelloEmpty(){
		return listaGiochi.isEmpty();
	}
	
	public int getGiocoByIndex(int index){
		if(isCarrelloEmpty())
			return 0;
		
		return (int) listaGiochi.keySet().toArray()[index];
	}
	
	public int getCarrelloLenght() {
		return listaGiochi.size();
	}
	
	public int get_idGioco(int c) {
		return listaGiochi.get(c);
	}
	
	public int getQuant(int idGioco) {
		return listaGiochi.get(idGioco);
	}
	
	// Metodo per rimuovere un gioco per ID
    public boolean removeGiocoByKey(int idGioco) {
       
    	if (isCarrelloEmpty()) {
            return false;
    	}
    	
    	if(listaGiochi.containsKey(idGioco))
    		listaGiochi.remove(idGioco);
    	else
    		return false;
    	
    	return true;
    }
    
	
	//controlla se un gioco sta nel carrello
	 public boolean contieneGioco(int idGioco) {
		 Set<Integer> keys = listaGiochi.keySet();
		 
		 for(int gioco : keys)
			 if(gioco == idGioco)
				 return true;
		 
		 return false;
		 	/*
	        for (int gioco : listaGiochi) {
	            if (gioco == idGioco) {
	                return true;
	            }
	        }
	        return false;
	       	*/
	    }
}
