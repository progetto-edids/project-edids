package com.nonsense.ToxicityValidator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.google.cloud.language.v1.LanguageServiceClient; // Importa LanguageServiceClient
import com.nonsense.NaturalLanguageApiClient; // Importa NaturalLanguageApiClient

import java.io.IOException;

class ToxicityValidatorTest {

    private ToxicityValidator validator; // Istanza non statica del validator
    private LanguageServiceClient realLanguageServiceClient; // Il client API reale

    @BeforeEach
    void setUp() throws IOException { // Aggiungi throws IOException per getServiceClient()
        // 1. Ottieni l'istanza reale del LanguageServiceClient.
        // Questo richiederà che il file .env e le credenziali siano configurate correttamente.
        realLanguageServiceClient = NaturalLanguageApiClient.getServiceClient();

        // Se il client non può essere inizializzato (es. credenziali mancanti/errate), il test fallisce.
        if (realLanguageServiceClient == null) {
            fail("Impossibile inizializzare LanguageServiceClient per i test. Controlla il file .env e le credenziali.");
        }

        // 2. Inizializza ToxicityValidator, passandogli il LanguageServiceClient reale.
        validator = new ToxicityValidator(realLanguageServiceClient);
    }

    @AfterEach
    void tearDown() {
        // 3. Chiudi il LanguageServiceClient reale dopo ogni test per rilasciare le risorse.
        if (realLanguageServiceClient != null) {
            NaturalLanguageApiClient.closeServiceClient();
            realLanguageServiceClient = null; // Imposta a null per pulizia
        }
        // Non c'è un metodo .close() su ToxicityValidator da chiamare qui.
    }


    //test che verifica che con una frase non tossica la validazione della tossicità dia un rate tra 0 e 1
    @Test
    void testGetToxicityScore_withNonToxicText() throws Exception { // Aggiungi throws Exception
        String text = "My mum cooks dinner really well.";
        // Chiama il metodo non statico sull'istanza del validator
        double score = validator.getToxicityScore(text); 

        assertTrue(score >= 0.0 && score <= 1.0, "Score should be between 0 and 1");
        System.out.println("Toxicity score (non-toxic): " + score);
        // Potresti voler asserire che il punteggio sia basso per un testo non tossico, es:
        // assertTrue(score < 0.2, "Score for non-toxic text should be low"); 
    }

    //test che verifica che inserendo una frase tossica la validizazione dia un rate tra 0 e 1
    @Test
    void testGetToxicityScore_withToxicText() throws Exception { // Aggiungi throws Exception
        String text = "You're a horrible, stupid person!";
        // Chiama il metodo non statico sull'istanza del validator
        double score = validator.getToxicityScore(text);

        assertTrue(score >= 0.0 && score <= 1.0, "Score should be between 0 and 1");
        System.out.println("Toxicity score (toxic): " + score);
        // Potresti voler asserire che il punteggio sia alto per un testo tossico, es:
        // assertTrue(score > 0.8, "Score for toxic text should be high");
    }

    //test che verifica che un testo vuoto ha tossicità nulla
    @Test
    void testGetToxicityScore_withEmptyText() throws Exception { // Aggiungi throws Exception
        String text = "";
        // Chiama il metodo non statico sull'istanza del validator
        double score = validator.getToxicityScore(text);

        assertEquals(0.0, score, "Empty text should result in 0 toxicity");
    }
}