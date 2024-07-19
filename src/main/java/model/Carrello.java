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
			listaGiochi.replace(idGioco, quant++);
		} else
			listaGiochi.put(idGioco, 1);
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
        while (c < getCarrelloLenght()) {
           
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
