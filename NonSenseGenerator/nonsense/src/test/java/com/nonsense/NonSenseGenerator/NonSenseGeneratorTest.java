package com.nonsense.NonSenseGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import com.nonsense.Dictionary.Dictionary;
import com.nonsense.Syntax.Syntax;

class NonSenseGeneratorTest {

    private Dictionary dictionary;
    private NonSenseGenerator generator;

    @BeforeEach  // Eseguito prima di ogni test
    public void setUp() {
        generator = new NonSenseGenerator();  

        dictionary = new Dictionary();
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
        Syntax syntax = new Syntax(dictionary);
        syntax.validateSyntax("The quick brown fox jumps over the lazy dog");

        String template = "The [adjective] [noun] [verb] the [plural_noun]";
        String result = generator.fillTemplate(template, syntax);

        assertFalse(result.contains("[noun]"));
        assertFalse(result.contains("[verb]"));
        assertFalse(result.contains("[adjective]"));
        assertFalse(result.contains("[plural_noun]"));

        System.out.println("Generated: " + result);
    }

    //test che verifica il corretto riempimento del template nel caso la frase di input abbia parole insufficienti a rimepire tutto il template 
    @Test
    void testFillTemplate_withInsufficientWords() throws Exception {
        Syntax syntax = new Syntax(dictionary);
        syntax.validateSyntax("Mum cooks dinner");  

        String template = "The [adjective] [noun] [verb] the [adjective] [noun] while the [noun] [verb] the [adjective] [noun].";
        generator.setSelectedTemplate(template, syntax);
        String result = generator.fillTemplate(template, syntax);

        System.out.println(result);
        //verifica che i placeholders siano stati sostituiti
        assertFalse(result.contains("[noun]"));
        assertFalse(result.contains("[verb]"));
        assertFalse(result.contains("[adjective]"));
    }

 

    

}
