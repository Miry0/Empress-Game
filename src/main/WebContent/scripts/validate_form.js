

// Funzione generica per validare un form
//prende in ingresso l'id del form. RequiredField rapprwsenta i campi del form che sono obbligatori da compilare. numericFields rappresentva i campi numeridi da validare
export function validateForm(formId, requiredFields=[], numericFields=[], floatFields=[]) { //gestiamo l'eventialità che possa essere passato un array vuoto per uni dei parametri
	
    var form = document.getElementById(formId); //recupera il form dal DOM tramite il suo id
    var isValid = true; //inizializza una variabile booleana

    requiredFields.forEach(function(fieldName) { //per ogni campo stringa obbligatorio, viene valutato se l'input è valido
        var input = form.elements[fieldName]; // viene recuperato l'elemento input corrispondente dal form
        
        if (!input || input.value.trim() === '') {
            isValid = false;
            // Personalizza il messaggio di errore o altre azioni
            console.log('Campo ' + fieldName + ' è obbligatorio.');
        }
    });

 
	numericFields.forEach(function(fieldName) { 
	        var input = form.elements[fieldName]; //recupera i campi numerici
	        
	        //biosgna valutare che l'input non sia null, perchè provare ad accedere al suo valore genererebbe un errore
	        if (!input && input.value.trim() !== '') { 
				
	            var value = parseInt(input.value.trim(), 10); // Converte il valore in intero
	            //se si trattasse di una stringa, allora la conversione in intero non andrebbe a buon fine e verrebbe restituito un messaggio di errore
	            
	            if (isNaN(value)) { //se isNAN restituisce true, allora l'input non è valido
	                isValid = false;
	                console.log('Campo ' + fieldName + ' deve essere un numero intero.');
	            }
	            
	            if(value<0){
					isValid = false;
	                console.log('Campo ' + fieldName + ' deve essere un valore intero maggiore di zero');
				}
	        }
	    });
	    
	     // Validazione campi numerici float
    	floatFields.forEach(function(fieldName) {
        var input = form.elements[fieldName];
        if (!input && input.value.trim() !== '') {
			
            var value = parseFloat(input.value.trim());
            if (isNaN(value) || value < 0) {
                isValid = false;
                console.log('Campo ' + fieldName + ' deve essere un numero decimale maggiore o uguale a zero.');
            }
        }
    });



return isValid;
	}
