# project-edids

INTRODUZIONE: 

Il NonSenseGenerator è un'applicazione che genera frasi senza senso partendo da un input dell'utente. 
Combina parole estratte da una frase fornita dall'utente con template predefiniti o casuali per creare frasi creative e senza senso. 
L'applicazione include anche funzionalità per analizzare la sintassi, validare la tossicità delle frasi generate e salvarle in un "bucket" che è possibile salvare come file .txt.

Utilizzo:


Inserimento della Frase

All'avvio, ti verrà chiesto di inserire una frase.  

La frase deve contenere almeno un sostantivo (noun) e un verbo (verb), altrimenti verrà richiesto di reinserirla.

	Esempio: 
	Write a sentence: “The quick brown fox jumps over the lazy dog”


Analisi della Frase

L'applicazione estrae sostantivi, verbi e aggettivi dalla frase inserita e li mostra nella console.  
	
 	Esempio: 
  
	Extracted words:
	Nouns: [fox, dog]
	Verbs: [jumps]
	Adjectives: [quick, brown, lazy]

  
Albero sintattico

Dopo che l’utente ha inserito la frase gli verrà chiesto se vuole visualizzarne l’albero sintattico

	Esempio: 
	Do you want to see the syntax tree of your sentence?
	Yes
	
	{sentence=The quick brown fox jumps over the lazy dog, tokens=[{word=The, tag=DET, dependency=DET}, {word=quick, tag=ADJ, dependency=AMOD}, {word=brown, tag=ADJ, dependency=AMOD}, {word=fox, tag=NOUN, dependency=NSUBJ}, {word=jumps, tag=VERB, dependency=ROOT}, {word=over, tag=ADP, dependency=PREP}, {word=the, tag=DET, dependency=DET}, {word=lazy, tag=ADJ, dependency=AMOD}, {word=dog, tag=NOUN, dependency=POBJ}]}





Scelta del Template

L'applicazione mostra una lista di template disponibili. Se il template ha ad esempio più aggettivi di quelli presenti nella frase di input ne verranno aggiunti da un dizionario. 

Puoi scegliere tra:

Template predefiniti: Selezionabili tramite numero.  

  	Esempio: 
 	Available templates:
  	[1] The [adjective] [noun] [verb] the [adjective] [noun].
 	[2] The [adjective] [noun] [verb] the [adjective] [noun] while the [noun] [verb] the [adjective] [noun].
 
  	Choose a template by number: 1

Oppure puoi scegliere di generare un template casuale


Generazione della Frase Nonsense

Dopo aver scelto il template, l'applicazione genera una frase nonsense combinando le parole estratte con il template selezionato.  
  	
   	Esempio:  
        Generated Sentence: The quick fox jumps the lazy dog.`


Validazione della Tossicità

La frase generata viene analizzata per valutarne la tossicità. Se il punteggio di tossicità è elevato, verrà visualizzato un avviso.  
 	
  	Esempio: 
  	Toxicity Score: 0.10
  	This sentence appears to be safe.

Inserimento della frase nel Bucket

Dopo che la frase è stata generata verrà chiesto all’utente se desidera salvarla all’interno di un bucket. 

	Esempio:
	Do you want to add the sentence to the bucket ?Yes


Visualizzazione del bucket

Verrà chiesto all’utente se desidera visualizzare il buket con le frasi salvate fino a quel momento.

	Esempio:
	Bucket:
	- The quick fox jumps the lazy dog


Salvare il bucket 

Dopo aver deciso se visualizzare il bucket l’utente può decidere di svuotare il bucket.

	Esempio:
	Do you want to save the bucket ?Yes

Il bucket delle frasi nonsense generate fino a quel momento verranno salvate in un file txt che si troverà nella cartella del progetto.


Svuotare il bucket

Dopo aver deciso se salvare il bucket l'utente può decidere di svuotarlo

	Esempio:
	Do you want to clear the bucket? Yes


La volta successiva il bucket sarà vuoto.





ISTRUZIONI:

Come preparare l'ambiente, compilare e avviare l’applicazione.

Clonare il progetto con: 

	git clone https://github.com/sara-githu/project-edids cd nonsensegenerator 

Configurare Google Cloud:
Accedere alla console di Google Cloud.
Creare un Service Account con il ruolo di Cloud Natural Language API User.
Scaricare il file .json di credenziali.
Inserirlo in 

	credentials/google-credentials.json 
Che verrà ignorato da git.
Il progetto è già configurato epr usare questo path.

Per chi vuole poter modificare il progetto:

Prerequisito avere Maven

Compilare il progetto: 
Aprire il terminale nella cartella principale del progetto (dove si trova pom.xml) e digitare: 

	mvn clean install

Eseguire il progetto : 
Aprire il terminale nella cartella principale del progetto (dove si trova pom.xml) e digitare: 

	mvn exec:java


Eseguire i test
Aprire il terminale nella cartella principale del progetto (dove si trova pom.xml) e digitare: 

	mvn test

Per generare il report HTML dei test: 

	mvn surefire-report:report


Per utenti finali: 

3. Apri il progetto con un IDE 
 
4. esegui:

	java -jar Main.jar. 




AMBIENTI DI ESECUZIONE: 
Per eseguire correttamente il progetto Nonsense Generator è necessario disporre di: 

Java JDK 21. Ambiente di esecuzione e linguaggio di programmazione usato per la creazione del software.

JUnit 5.9.3. 
E’ il framework utilizzato per l’implementazione dei test automatici del progetto, consentendo la verifica del corretto funzionamento dei metodi principali attraverso test unitari.
Un framework è un insieme strutturato di:
Librerie già pronte
Regole e convenzioni
Annotazioni e metodi predefiniti che ti aiutano a scrivere codice in modo organizzato e coerente.


Apache Maven come build tool. 
Apache Maven è uno strumento che automatizza la compilazione, il testing, la gestione delle dipendenze e il packaging di progetti software, principalmente Java.

Connessione a internet per l’utilizzo dell'API esterna





FUNZIONI UTILIZZATE DA LIBRERIE
1. JUnit 5.9.3 (org.junit.jupiter)
Utilizzato per scrivere e lanciare test unitari


Funzionalità riutilizzate:
@Test – per definire metodi di test
assertEquals, assertTrue, assertFalse – per verificare i risultati attesi


2. Google Cloud Natural Language API (com.google.cloud.language)
Utilizzata per analizzare il contenuto semantico delle frasi


Funzionalità riutilizzate:
LanguageServiceClient – per accedere ai servizi linguistici di Google
Document – per rappresentare il testo da analizzare
AnalyzeSyntaxResponse – per ottenere risultati dall’analisi
ModerateTestRequest – per ottenere il punteggio di tossicità della frase


3. Maven Surefire Plugin (maven-surefire-plugin)
Utilizzato per eseguire i test con Maven in modo automatico


Non richiede chiamate dirette nel codice: viene configurato in pom.xml ed esegue i test durante mvn test


4. Maven Surefire Report Plugin
Utilizzato per generare un report HTML dei risultati dei test


Il file surefire-report.html viene generato automaticamente nella cartella target/site/






INDICAZIONI DI API ESTERNE:

Google Cloud Natural Language API
Il progetto utilizza la Google Cloud Natural Language API tramite il client ufficiale Java (google-cloud-language). Questa API consente di effettuare analisi semantiche sui testi, come l’analisi sintattica, il riconoscimento delle entità e la validazione della tossicità della frase generata. È indispensabile per le funzionalità di elaborazione del linguaggio naturale presenti nel software. L’accesso al servizio richiede la configurazione di un account Google Cloud e la definizione della variabile d’ambiente GOOGLE_APPLICATION_CREDENTIALS.

JUnit 5
JUnit 5 è il framework utilizzato per la scrittura e l’esecuzione dei test unitari. Permette di automatizzare la verifica del corretto funzionamento del codice attraverso annotazioni e asserzioni, migliorando la qualità e l’affidabilità del software.

Maven Surefire Plugin
Il Maven Surefire Plugin viene impiegato per eseguire i test durante la fase di build e per la generazione di report sui risultati dei test. Questo plugin facilita l’integrazione dei test nel processo di sviluppo automatizzato tramite Maven.




