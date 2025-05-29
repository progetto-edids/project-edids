package com.nonsense;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.nonsense.Dictionary.Dictionary;
import com.nonsense.NonSenseGenerator.NonSenseGenerator;
import com.nonsense.UI.UI;
import com.nonsense.Syntax.Syntax;
import com.nonsense.ToxicityValidator.ToxicityValidator;


public class Main {

    public static void main(String[] args) {
        // Inizializza il client Natural Language API usando la classe di utilità
        LanguageServiceClient languageServiceClient = NaturalLanguageApiClient.getServiceClient();

        // Verifica se il client è stato inizializzato correttamente
        if (languageServiceClient == null) {
            System.err.println("Impossibile inizializzare il client Natural Language API. Controlla i messaggi di errore precedenti.");
            return; 
        }

        System.out.println("Client Natural Language API inizializzato e pronto per l'uso.");

        try {
            ToxicityValidator toxicityValidator = new ToxicityValidator(languageServiceClient);
            Syntax syntax = new Syntax(new Dictionary(), languageServiceClient);
             UI ui = new UI(new NonSenseGenerator(), syntax, toxicityValidator);
                ui.start();

        } catch (Exception e) {
            // Gestione di errori generici durante l'interazione con l'API
            System.err.println("Si è verificato un errore durante l'interazione con la Natural Language API: " + e.getMessage());
            e.printStackTrace();
        } finally {
            
            // La classe NaturalLanguageApiClient gestisce la chiusura per una singola istanza.
            NaturalLanguageApiClient.closeServiceClient();
            System.out.println("\nClient Natural Language API chiuso.");
        } 
       
    }
}

       

