package com.nonsense.Dictionary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryTest {

    private Dictionary dictionary;

    @BeforeEach
    void setUp() {
        dictionary = new Dictionary();
    }

    //Test che verifica che un sostantivo nuovo venga aggiunta al dizionario
    @Test
    void testAddNoun_NewWord() {
        boolean added = dictionary.addNoun("orange");
        assertTrue(added);
    }

    //Test per verificare che se un sostantivo è già nel dizionario non lo aggiunge
    @Test
    void testAddNoun_Duplicate() {
        dictionary.addNoun("apple"); // already exists
        boolean added = dictionary.addNoun("apple");
        assertFalse(added);
    }

    //Test che verifica che un verbo nuovo venga aggiunto al dizionario
    @Test
    void testAddVerb_NewWord() {
        boolean added = dictionary.addVerb("teleport");
        assertTrue(added);
    }

    //Test per verificare che se un verbo è già nel dizionario non lo aggiunge
    @Test
    void testAddVerb_Duplicate() {
        dictionary.addVerb("run"); // already exists
        boolean added = dictionary.addVerb("run");
        assertFalse(added);
    }

     //Test che verifica che un aggettivo nuovo venga aggiunto al dizionario
    @Test
    void testAddAdjective_NewWord() {
        boolean added = dictionary.addAdjective("mysterious");
        assertTrue(added);
    }

    //Test per verificare che se un aggettivo è già nel dizionario non lo aggiunge
    @Test
    void testAddAdjective_Duplicate() {
        dictionary.addAdjective("happy"); // already exists
        boolean added = dictionary.addAdjective("happy");
        assertFalse(added);
    }

    //test che verifica che vengano presi sostantivi, verbi e aggettvi casuali e che i dizionari non siano vuoti 
    @Test
    void testGetRandomNoun_NotEmpty() {
        String noun = dictionary.getRandomNoun();
        assertNotNull(noun);
        assertFalse(noun.isBlank());
    }

    @Test
    void testGetRandomVerb_NotEmpty() {
        String verb = dictionary.getRandomVerb();
        assertNotNull(verb);
        assertFalse(verb.isBlank());
    }

    @Test
    void testGetRandomAdjective_NotEmpty() {
        String adjective = dictionary.getRandomAdjective();
        assertNotNull(adjective);
        assertFalse(adjective.isBlank());
    }

    //test per verificare che, se i dizionari sono vuoti, il metodo getRandom... restituisca una stringa di default, invece di lanciare una eccezione
    @Test
    void testGetRandomNoun_WhenEmpty() {
        Dictionary emptyDict = new Dictionary();
        emptyDict.clearNouns();  // For testing only
        String result = emptyDict.getRandomNoun();
        assertEquals("placeholder", result);
    }

    @Test
    void testGetRandomVerb_WhenEmpty() {
        Dictionary emptyDict = new Dictionary();
        emptyDict.clearVerbs();  // For testing only
        String result = emptyDict.getRandomVerb();
        assertEquals("placeholder", result);
    }

    @Test
    void testGetRandomAdjective_WhenEmpty() {
        Dictionary emptyDict = new Dictionary();
        emptyDict.clearAdjectives();  // For testing only
        String result = emptyDict.getRandomAdjective();
        assertEquals("placeholder", result);
    }

}
