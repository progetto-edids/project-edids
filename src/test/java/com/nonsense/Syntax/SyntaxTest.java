package com.nonsense.Syntax;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.cloud.language.v1.AnalyzeSyntaxResponse;
import com.google.cloud.language.v1.LanguageServiceClient; 
import com.nonsense.Dictionary.Dictionary;
import com.nonsense.NaturalLanguageApiClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class SyntaxTest {

    private Dictionary dictionary;
    private Syntax syntax;
    private LanguageServiceClient realLanguageServiceClient;

    @BeforeEach
    void setUp() throws IOException {
        // Questo metodo si occupa di caricare le credenziali dal file .env.
        realLanguageServiceClient = NaturalLanguageApiClient.getServiceClient();

        // Se c'è un problema con le credenziali o la connessione, realLanguageServiceClient sarà null.
        if (realLanguageServiceClient == null) {
            fail("Impossibile inizializzare LanguageServiceClient per i test. Assicurati che il file .env e le credenziali Google Cloud siano configurati correttamente e che le API necessarie siano abilitate.");
        }

        // Inizializza il dizionario con alcune parole di esempio
        dictionary = new Dictionary();
        dictionary.addNoun("dog");
        dictionary.addVerb("run");
        dictionary.addAdjective("happy");

        // Inizializza l'analizzatore sintattico passando il Dictionary e il client reale
        syntax = new Syntax(dictionary, realLanguageServiceClient);
    }

    @AfterEach
    void tearDown() {
        if (realLanguageServiceClient != null) {
            NaturalLanguageApiClient.closeServiceClient();
            realLanguageServiceClient = null; // Imposta a null per pulizia
        }
    }

    // Test per verificare il corretto funzionamento di responseAnalycies verificando che le liste vengano aggiornate
    @Test
    void testResponseAnalyticsWithSimpleSentence() throws Exception {
        String sentence = "The quick brown cat jumps over the lazy dog";

        // Questo metodo farà una VERA chiamata all'API
        AnalyzeSyntaxResponse response = syntax.responseAnalycies(sentence);

        assertNotNull(response, "La risposta non dovrebbe essere null");
        assertFalse(response.getTokensList().isEmpty(), "Dovrebbero esserci dei token");

        // Verifica che le liste siano state popolate correttamente
        List<String> nouns = syntax.getNouns();
        List<String> verbs = syntax.getVerbs();
        List<String> adjectives = syntax.getAdjectives();

        // Le asserzioni si basano su risposte REALI dell'API
        assertTrue(nouns.contains("cat"), "Dovrebbe contenere 'cat' come sostantivo");
        assertTrue(nouns.contains("dog"), "Dovrebbe contenere 'dog' come sostantivo");
        assertTrue(verbs.contains("jumps"), "Dovrebbe contenere 'jumps' come verbo");
        assertTrue(adjectives.contains("quick"), "Dovrebbe contenere 'quick' come aggettivo");
        assertTrue(adjectives.contains("brown"), "Dovrebbe contenere 'brown' come aggettivo");
        assertTrue(adjectives.contains("lazy"), "Dovrebbe contenere 'lazy' come aggettivo");
    }

    // Test per verificare che la validazione della sintassi venga fatta correttamente
    @Test
    void testValidateSyntax() throws Exception {
        // Frase valida con sostantivo e verbo (farà una vera chiamata API)
        assertTrue(syntax.validateSyntax("Cats sleep"), "La frase 'Cats sleep' dovrebbe essere valida");

        // Frase non valida (manca il verbo) (farà una vera chiamata API)
        assertFalse(syntax.validateSyntax("Happy cats"), "La frase 'Happy cats' dovrebbe essere non valida (manca verbo)");

        // Frase non valida (manca il sostantivo) (farà una vera chiamata API)
        assertFalse(syntax.validateSyntax("Run quickly"), "La frase 'Run quickly' dovrebbe essere non valida (manca sostantivo)");
    }

    // Test per verificare che il metodo updateListForTemplate aggiunga o tolga parole dalle liste a seconda dei casi
    @Test
    void testUpdateListForTemplate() throws Exception {
        // Testa l'espansione della lista quando non ci sono abbastanza elementi
        syntax.responseAnalycies("Cat runs");
        syntax.updateListForTemplate(5, "noun");

        List<String> expandedNouns = syntax.getNouns();
        assertEquals(5, expandedNouns.size(), "Dovrebbe avere 5 sostantivi dopo l'espansione");
        assertTrue(expandedNouns.contains("Cat"), "Dovrebbe mantenere i sostantivi originali");
        

        // Testa la riduzione della lista quando ci sono troppi elementi
        syntax.responseAnalycies("The big black dog barks loudly at the small white cat");
        syntax.updateListForTemplate(2, "adjective");

        List<String> reducedAdjectives = syntax.getAdjectives();
        assertEquals(2, reducedAdjectives.size(), "Dovrebbe avere solo 2 aggettivi dopo la riduzione");
    }

    // Test che verifica la creazione dell'albero sintattico
    @Test
    void testGetSyntaxTree() throws IOException {
        String sentence = "The cat sits on the table";

        // Questo metodo farà una VERA chiamata all'API
        Map<String, Object> tree = syntax.getSyntaxTree(sentence);

        assertNotNull(tree, "L'albero sintattico non dovrebbe essere null");
        assertEquals(sentence, tree.get("sentence"), "Dovrebbe contenere la frase originale");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> tokens = (List<Map<String, Object>>) tree.get("tokens");
        assertFalse(tokens.isEmpty(), "Dovrebbe contenere i token analizzati");

        // Verifica che ogni token abbia le proprietà attese
        for (Map<String, Object> token : tokens) {
            assertTrue(token.containsKey("word"), "Ogni token dovrebbe avere una parola");
            assertTrue(token.containsKey("tag"), "Ogni token dovrebbe avere un POS tag");
            assertTrue(token.containsKey("dependency"), "Ogni token dovrebbe avere una dependency label");
        }
    }

    // Test che verifica che se viene inserito input vuoto le liste rimangano vuote
    @Test
    void testEmptyInput() throws Exception {
        // Test con input vuoto
        String sentence = "";

        // Questo farà una chiamata API reale con un input vuoto
        AnalyzeSyntaxResponse response = syntax.responseAnalycies(sentence);

        assertNotNull(response, "La risposta non dovrebbe essere null anche con input vuoto");
        assertTrue(response.getTokensList().isEmpty(), "Non dovrebbero esserci token con input vuoto");

        // Verifica che le liste siano vuote
        assertTrue(syntax.getNouns().isEmpty(), "Nessun sostantivo con input vuoto");
        assertTrue(syntax.getVerbs().isEmpty(), "Nessun verbo con input vuoto");
        assertTrue(syntax.getAdjectives().isEmpty(), "Nessun aggettivo con input vuoto");
    }

    // Test che verifica che sono riconosciuti i sostantivi nonostante la punteggiatura
    @Test
    void testSpecialCharacters() throws Exception {
        String sentence = "Hello, world! How's it going?";

        AnalyzeSyntaxResponse response = syntax.responseAnalycies(sentence);

        assertNotNull(response, "La risposta non dovrebbe essere null");
        assertFalse(response.getTokensList().isEmpty(), "Dovrebbero esserci dei token");

        // Verifica che la punteggiatura sia gestita correttamente e che i sostantivi siano riconosciuti
        List<String> nouns = syntax.getNouns();
        assertTrue(nouns.contains("world"), "Dovrebbe riconoscere 'world' come sostantivo nonostante la punteggiatura");
    }
}
