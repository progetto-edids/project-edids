package com.nonsense.BucketManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BucketManagerTest {

    private BucketManager bucketManager;

    @BeforeEach
    void setUp() {
        bucketManager = new BucketManager();
    }

    // Test per aggiungere una frase al bucket
    @Test
    void testAddPhrase() {
        String phrase = "The quick brown fox jumps over the lazy dog.";
        bucketManager.add(phrase);
        List<String> allPhrases = bucketManager.getAll();
        assertEquals(1, allPhrases.size());
        assertTrue(allPhrases.contains(phrase), "The phrase should be in the bucket.");
    }

    // Test per aggiungere una frase vuota, che non deve essere aggiunta
    @Test
    void testAddEmptyPhrase() {
        bucketManager.add("");
        List<String> allPhrases = bucketManager.getAll();
        assertEquals(0, allPhrases.size(), "An empty phrase should not be added.");
    }

    // Test per svuotare il bucket
    @Test
    void testClearBucket() {
        bucketManager.add("Test phrase 1");
        bucketManager.add("Test phrase 2");
        bucketManager.clear();
        assertTrue(bucketManager.getAll().isEmpty(), "The bucket should be empty after clear.");
    }

    // Test per salvare il bucket su un file
    @Test
    void testSaveToFile() throws IOException {
        String filePath = "test_bucket_output.txt";
        bucketManager.add("Test phrase 1");
        bucketManager.add("Test phrase 2");

        // Salva il bucket nel file
        bucketManager.saveToFile(filePath);

        // Verifica che il file sia stato creato
        File file = new File(filePath);
        assertTrue(file.exists(), "The file should be created.");

        // Verifica che il contenuto del file corrisponda alle frasi nel bucket
        List<String> fileContent = Files.readAllLines(file.toPath());
        assertEquals(2, fileContent.size(), "The file should contain 2 lines.");
        assertTrue(fileContent.contains("Test phrase 1"), "The file should contain 'Test phrase 1'.");
        assertTrue(fileContent.contains("Test phrase 2"), "The file should contain 'Test phrase 2'.");

        // Pulisci il file dopo il test
        Files.delete(file.toPath());
    }

    // Test per stampare il contenuto del bucket (verifica solo che il metodo non causi errori)
    @Test
    void testPrintBucket() {
        bucketManager.add("Test phrase 1");
        bucketManager.add("Test phrase 2");

        // Non possiamo "assert" sullo stdout, quindi lo testiamo solo per verificarne l'esecuzione.
        bucketManager.printBucket();
    }
}
