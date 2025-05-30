/*Classe TemplateManager: gestisce le operazioni relative ai template ad esempio il caricamento dei template predefiniti
e la creazione di un template casuale.*/
package com.nonsense.TemplateManager;

import java.util.ArrayList;
import java.util.List;

public class TemplateManager {
    private final List<String> templates;

    //costruttore
    public TemplateManager() {
        templates = new ArrayList<>();
        loadDefaultTemplates();
    }

    //carica i template di default
    private void loadDefaultTemplates() {
        templates.add("The [adjective] [noun] [verb] the [adjective] [noun].");
        templates.add("The [adjective] [noun] [verb] the [adjective] [noun] while the [noun] [verb] the [adjective] [noun].");
        templates.add("Every [adjective] [noun] wants to [verb] a [adjective] [noun] that can [verb] the [adjective] [noun].");
        templates.add("In the [adjective] [noun], the [noun] must [verb] or the [adjective] [noun] will [verb].");
        templates.add("[Adjective] and [adjective], the [noun] [verb] over the [adjective] [noun] and [verb] the [noun].");
        
    }

    //metodi getter
    public List<String> getTemplates() {
        return templates;
    }

    //stampa i templates di default
    public void printTemplates() {
        for (int i = 0; i < templates.size(); i++) {
         System.out.println(i + ": " + templates.get(i));
        }
    }

    public String getTemplateByIndex(int index) {
        if (index >= 0 && index < templates.size()) {
            return templates.get(index);
        }
        return null;
    }       

    //genera un template casuale, mettendo una frase principale obbligatoria e due opzionali (in base a un numero generato casulamente)
    public String generateRandomTemplate() {
        StringBuilder template = new StringBuilder();

        // Frase condizionale (opzionale)
        if (Math.random() < 0.3) {
            template.append("If the ")
                    .append(optionalAdjective())
                    .append("[noun] ")
                    .append("[verb] the ")
                     .append(optionalAdjective())
                     .append("[noun], ");
        }

        // Frase principale (obbligatoria)
        template.append("the ")
                .append(optionalAdjective())
                .append("[noun] ")
                .append("[verb] the ")
                .append(optionalAdjective())
                .append("[noun]");

        // Frase casuale (opzionale)
        if (Math.random() < 0.3) {
            template.append(" because the ")
                    .append(optionalAdjective())
                    .append("[noun] ")
                    .append("[verb] the ")
                    .append(optionalAdjective())
                    .append("[noun]");
        }

        template.append(".");

        return capitalize(template.toString());
    }

    //metodo che ritorna un aggettivo solo se il numero casuale è minore di 0.8
    private String optionalAdjective() {
        return Math.random() < 0.8 ? "[adjective] " : "";
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


}
