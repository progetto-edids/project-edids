package com.nonsense.ToxicityValidator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToxicityValidatorTest {

    //test che verifica che con un afrase non tossica la validazione della tossicità dia un rate tra 0 e 1
    @Test
    void testGetToxicityScore_withNonToxicText() throws Exception {
        String text = "My mus cooks dinner really well.";
        double score = ToxicityValidator.getToxicityScore(text);

        assertTrue(score >= 0.0 && score <= 1.0, "Score should be between 0 and 1");
        System.out.println("Toxicity score (non-toxic): " + score);
    }

    //test che verifica che inserendo una frase tossica la validizazione dia un rate tra 0 e 1
    @Test
    void testGetToxicityScore_withToxicText() throws Exception {
        String text = "You're a horrible, stupid person!";
        double score = ToxicityValidator.getToxicityScore(text);

        assertTrue(score >= 0.0 && score <= 1.0, "Score should be between 0 and 1");
        System.out.println("Toxicity score (toxic): " + score);
    }

    //test che verifica che un testo vuoto ha tossicità nulla
    @Test
    void testGetToxicityScore_withEmptyText() throws Exception {
        String text = "";
        double score = ToxicityValidator.getToxicityScore(text);

        assertEquals(0.0, score, "Empty text should result in 0 toxicity");
    }
}
