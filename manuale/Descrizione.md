***INTRODUZIONE:***

Il NonSenseGenerator è un'applicazione che genera frasi senza senso partendo da un input dell'utente. 
Combina parole estratte da una frase fornita dall'utente con template predefiniti o casuali per creare frasi creative e senza senso. 
L'applicazione include anche funzionalità per analizzare la sintassi, validare la tossicità delle frasi generate e salvarle in un "bucket" che è possibile salvare come file .txt.


Utilizzo:


1. Inserimento della Frase

All'avvio, ti verrà chiesto di inserire una frase.  

La frase deve contenere almeno un sostantivo (noun) e un verbo (verb), altrimenti verrà richiesto di reinserirla.
  
	Esempio: 
	Write a sentence: “The quick brown fox jumps over the lazy dog”


2. Analisi della Frase

L'applicazione estrae sostantivi, verbi e aggettivi dalla frase inserita e li mostra nella console.  
  

  	Esempio: 
  
	Extracted words:
	Nouns: [fox, dog]
	Verbs: [jumps]
	Adjectives: [quick, brown, lazy]

  
3. Albero sintattico

Dopo che l’utente ha inserito la frase gli verrà chiesto se vuole visualizzarne l’albero sintattico

	Esempio: 
	Do you want to see the syntax tree of your sentence?
	Yes
	
	{sentence=The quick brown fox jumps over the lazy dog, tokens=[{word=The, tag=DET, dependency=DET}, {word=quick, tag=ADJ, dependency=AMOD}, {word=brown, tag=ADJ, dependency=AMOD}, {word=fox, tag=NOUN, dependency=NSUBJ}, {word=jumps, tag=VERB, dependency=ROOT}, {word=over, tag=ADP, dependency=PREP}, {word=the, tag=DET, dependency=DET}, {word=lazy, tag=ADJ, dependency=AMOD}, {word=dog, tag=NOUN, dependency=POBJ}]}





4. Scelta del Template

L'applicazione mostra una lista di template disponibili. Se il template ha ad esempio più aggettivi di quelli presenti nella frase di input ne verranno aggiunti da un dizionario. 

Puoi scegliere tra:

Template predefiniti: Selezionabili tramite numero.  

  	Esempio: 
 	Available templates:
  	[1] The [adjective] [noun] [verb] the [adjective] [noun].
 	[2] The [adjective] [noun] [verb] the [adjective] [noun] while the [noun] [verb] the [adjective] [noun].
 
  	Choose a template by number: 1

Oppure puoi scegliere di generare un template casuale


5. Generazione della Frase Nonsense

Dopo aver scelto il template, l'applicazione genera una frase nonsense combinando le parole estratte con il template selezionato.  
  	
   	Esempio:  
        Generated Sentence: The quick fox jumps the lazy dog.`


6. Validazione della Tossicità

La frase generata viene analizzata per valutarne la tossicità. Se il punteggio di tossicità è elevato, verrà visualizzato un avviso.  
 	
  	Esempio: 
  	Toxicity Score: 0.10
  	This sentence appears to be safe.

7. Inserimento della frase nel Bucket

Dopo che la frase è stata generata verrà chiesto all’utente se desidera salvarla all’interno di un bucket. 

	Esempio:
	Do you want to add the sentence to the bucket? Yes


8. Visualizzazione del bucket

Verrà chiesto all’utente se desidera visualizzare il buket con le frasi salvate fino a quel momento.

	Esempio:
	Bucket:
	- The quick fox jumps the lazy dog


9. Salvare il bucket 

Dopo aver deciso se visualizzare il bucket l’utente può decidere di svuotare il bucket.

	Esempio:
	Do you want to save the bucket ?Yes

Il bucket delle frasi nonsense generate fino a quel momento verranno salvate in un file txt che si troverà nella cartella del progetto.


10. Svuotare il bucket

Dopo aver deciso se salvare il bucket l'utente può decidere di svuotarlo

	Esempio:
	Do you want to clear the bucket? Yes


La volta successiva il bucket sarà vuoto.


