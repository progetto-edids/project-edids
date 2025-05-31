
***FUNZIONI UTILIZZATE DA LIBRERIE:***
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



3. Libreria java-dotenv

   viene utilizzata per leggere le variabili definite nel file .env e renderle disponibili al programma.

   Le funzioni chiave utilizzate sono:

         Dotenv.configure(): Inizia il processo di configurazione per java-dotenv.
   
         .directory(String path): Specifica la directory in cui la libreria cercherà il file .env.
         È fondamentale che il programma venga avviato dalla directory che contiene il file .env.
   
         .load(): Carica il file .env e rende le variabili in esso contenute accessibili.
   
         dotenv.get(String key): Una volta che il file .env è stato caricato,
         questo metodo permette di recuperare il valore associato a una specifica key (nome della variabile d'ambiente).








