package com.nonsense.TemplateManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TemplateManagerTest {

    private TemplateManager manager;

    @BeforeEach
    void setUp() {
        manager = new TemplateManager();
    }

    @Test
    void testDefaultTemplatesLoaded() {
        List<String> templates = manager.getTemplates();
        assertNotNull(templates, "Templates list should not be null");
        assertEquals(5, templates.size(), "There should be 5 default templates loaded");
    }

   /*  @Test
    void testGetRandomTemplateNotEmpty() {
        String template = manager.getRandomTemplate();
        assertNotNull(template, "Random template should not be null");
        assertFalse(template.isEmpty(), "Random template should not be empty");
    }*/

    @Test
    void testGetTemplateByIndex_ValidIndex() {
        String template = manager.getTemplateByIndex(0);
        assertNotNull(template, "Template at index 0 should not be null");
    }

    @Test
    void testGetTemplateByIndex_InvalidNegativeIndex() {
        String template = manager.getTemplateByIndex(-1);
        assertNull(template, "Template with negative index should return null");
    }

    @Test
    void testGetTemplateByIndex_IndexOutOfBounds() {
        String template = manager.getTemplateByIndex(10); // index > 4
        assertNull(template, "Template with out-of-bounds index should return null");
    }

    @Test
    public void testGenerateRandomTemplate_NotNullOrEmpty() {
        TemplateManager manager = new TemplateManager();
        String template = manager.generateRandomTemplate();

        assertNotNull(template, "Il template generato non dovrebbe essere null");
        assertFalse(template.isEmpty(), "Il template generato non dovrebbe essere vuoto");
    }

    @Test
    public void testGenerateRandomTemplate_ContainsNounAndVerb() {
        TemplateManager manager = new TemplateManager();
        String template = manager.generateRandomTemplate();

        assertTrue(template.contains("[noun]"), "Il template dovrebbe contenere almeno un [noun]");
        assertTrue(template.contains("[verb]"), "Il template dovrebbe contenere almeno un [verb]");
    }

    @Test
    public void testGenerateRandomTemplate_EndsWithPeriod() {
        TemplateManager manager = new TemplateManager();
        String template = manager.generateRandomTemplate();

        assertTrue(template.endsWith("."), "Il template dovrebbe finire con un punto.");
    }

    @Test
    public void testOptionalAdjective_ReturnsValidValues() {
        TemplateManager manager = new TemplateManager();
        // Ripetiamo il test più volte per coprire entrambe le casistiche (con o senza aggettivo)
        boolean foundAdjective = false;
        boolean foundEmpty = false;

        for (int i = 0; i < 100; i++) {
            String result = invokeOptionalAdjective(manager);
            if (result.equals("")) {
                foundEmpty = true;
            } else if (result.equals("[adjective] ")) {
                foundAdjective = true;
            }
            if (foundAdjective && foundEmpty) break;
        }

        assertTrue(foundAdjective, "Dovrebbe esserci almeno una volta '[adjective] '");
        assertTrue(foundEmpty, "Dovrebbe esserci almeno una volta una stringa vuota");
    }

    @Test
    public void testCapitalize() {
        TemplateManager manager = new TemplateManager();

        assertEquals("Hello", invokeCapitalize(manager, "hello"));
        assertEquals("Hello world", invokeCapitalize(manager, "hello world"));
        assertEquals("", invokeCapitalize(manager, ""));
        assertNull(invokeCapitalize(manager, null));
    }

    // 🔒 I metodi optionalAdjective() e capitalize() sono privati, quindi usiamo la riflessione:
    private String invokeOptionalAdjective(TemplateManager manager) {
        try {
            var method = TemplateManager.class.getDeclaredMethod("optionalAdjective");
            method.setAccessible(true);
            return (String) method.invoke(manager);
        } catch (Exception e) {
            throw new RuntimeException("Errore nell'invocazione di optionalAdjective", e);
        }
    }

    private String invokeCapitalize(TemplateManager manager, String input) {
        try {
            var method = TemplateManager.class.getDeclaredMethod("capitalize", String.class);
            method.setAccessible(true);
            return (String) method.invoke(manager, input);
        } catch (Exception e) {
            throw new RuntimeException("Errore nell'invocazione di capitalize", e);
        }
    }
}
