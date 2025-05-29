package com.nonsense.NonSenseGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nonsense.Dictionary.Dictionary;
import com.nonsense.Syntax.Syntax;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.nonsense.NaturalLanguageApiClient;

import java.io.IOException;

class NonSenseGeneratorTest {

    private Dictionary dictionary;
    private NonSenseGenerator generator;
    private Syntax syntax;
    private LanguageServiceClient realLanguageServiceClient; 

    @BeforeEach // Eseguito prima di ogni test
    public void setUp() throws IOException {
        generator = new NonSenseGenerator(); 
        dictionary = new Dictionary();

        //Inizializza il LanguageServiceClient reale.
        realLanguageServiceClient = NaturalLanguageApiClient.getServiceClient();
        
        // Se il client non può essere inizializzato (es. credenziali mancanti/errate), il test fallisce.
        if (realLanguageServiceClient == null) {
            fail("Impossibile inizializzare LanguageServiceClient per i test. Controlla il file .env e le credenziali.");
        }

        //Inizializza Syntax, passandogli il Dictionary e il LanguageServiceClient reale.
        syntax = new Syntax(dictionary, realLanguageServiceClient);
    }

    @AfterEach
    void tearDown() {
        //Chiudi il LanguageServiceClient reale dopo ogni test per rilasciare le risorse.
        if (realLanguageServiceClient != null) {
            NaturalLanguageApiClient.closeServiceClient();
            realLanguageServiceClient = null; // Imposta a null per pulizia
        }
    }

    @Test
    void testGetSafeWord_withValidWord() {
        assertEquals("hello", generator.getSafeWord("hello"));
    }

    @Test
    void testGetSafeWord_withNull() {
        assertEquals("placeholder", generator.getSafeWord(null));
    }

    @Test
    void testGetSafeWord_withBlank() {
        assertEquals("placeholder", generator.getSafeWord("   "));
    }

    //test che verifica che il conteggio dei placeholder per aggettivi, sostantivi e verbi sia corretto
    @Test
    void testCountPlaceholders() {
        String template = "The [adjective] [noun] [verb] the [adjective] [noun].";
        assertEquals(2, generator.countPlaceholders(template, "\\[noun\\]"));
        assertEquals(1, generator.countPlaceholders(template, "\\[verb\\]"));
        assertEquals(2, generator.countPlaceholders(template, "\\[adjective\\]"));
        assertEquals(0, generator.countPlaceholders(template, "\\[plural_noun\\]"));
    }

    //test che verifica che dopo aver riempito il template non siano più presenti i placeholders di aggettivi sostantivi e verbi
    @Test
    void testFillTemplate_withValidSyntax() throws Exception {
        syntax.validateSyntax("The quick brown fox jumps over the lazy dog");

        String template = "The [adjective] [noun] [verb] the [plural_noun]";
        String result = generator.fillTemplate(template, syntax);

        // Verifiche che non ci siano più placeholder
        assertFalse(result.contains("[noun]"));
        assertFalse(result.contains("[verb]"));
        assertFalse(result.contains("[adjective]"));
        assertFalse(result.contains("[plural_noun]"));

        System.out.println("Generated: " + result);
    }

    //test che verifica il corretto riempimento del template nel caso la frase di input abbia parole insufficienti a rimepire tutto il template 
    @Test
    void testFillTemplate_withInsufficientWords() throws Exception {
        // Usiamo l'istanza di syntax inizializzata in setUp, che usa il client API reale.
        syntax.validateSyntax("Mum cooks dinner");  

        String template = "The [adjective] [noun] [verb] the [adjective] [noun] while the [noun] [verb] the [adjective] [noun].";
        generator.setSelectedTemplate(template, syntax);
        String result = generator.fillTemplate(template, syntax);

        System.out.println(result);
        // Verifica che i placeholders siano stati sostituiti
        assertFalse(result.contains("[noun]"));
        assertFalse(result.contains("[verb]"));
        assertFalse(result.contains("[adjective]"));
    }
}
