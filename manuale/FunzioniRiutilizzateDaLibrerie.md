
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


3. Maven Surefire Plugin (maven-surefire-plugin)
   
   Utilizzato per eseguire i test con Maven in modo automatico


   Non richiede chiamate dirette nel codice: viene configurato in pom.xml ed esegue i test durante mvn test


4. Maven Surefire Report Plugin
   
   Utilizzato per generare un report HTML dei risultati dei test


   Il file surefire-report.html viene generato automaticamente nella cartella target/site/










