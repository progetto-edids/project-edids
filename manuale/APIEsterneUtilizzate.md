

**INDICAZIONI DI API ESTERNE:**

- Google Cloud Natural Language API

    Il progetto utilizza la Google Cloud Natural Language API tramite il client ufficiale Java (google-cloud-language). 

    Questa API consente di effettuare analisi semantiche sui testi, come l’analisi sintattica, il riconoscimento delle entità e la validazione della tossicità della frase generata. È indispensabile per le funzionalità di elaborazione del linguaggio naturale presenti nel software. 

    L’accesso al servizio richiede la configurazione di un account Google Cloud e la definizione della variabile d’ambiente GOOGLE_APPLICATION_CREDENTIALS.

- JUnit 5

    JUnit 5 è il framework utilizzato per la scrittura e l’esecuzione dei test unitari. Permette di automatizzare la verifica del corretto funzionamento del codice attraverso annotazioni e asserzioni, migliorando la qualità e l’affidabilità del software.


- Maven Surefire Plugin

    Il Maven Surefire Plugin viene impiegato per eseguire i test durante la fase di build e per la generazione di report sui risultati dei test. Questo plugin facilita l’integrazione dei test nel processo di sviluppo automatizzato tramite Maven.


- java-dotenv
Questo progetto utilizza la libreria java-dotenv per gestire le variabili d'ambiente e le configurazioni sensibili (come chiavi API, credenziali) in modo sicuro e flessibile, separandole dal codice sorgente.
Le variabili d'ambiente vengono caricate da un file .env locale, garantendo che le credenziali non vengano commesse nel sistema di controllo versione.


