package com.nonsense.NonSenseGenerator;


import java.util.List;
import java.util.regex.Pattern;

import com.nonsense.Syntax.Syntax;


public class NonSenseGenerator {


    public NonSenseGenerator() {
        
    }

    
    protected String getSafeWord(String word) {
        return (word != null && !word.isBlank()) ? word : "placeholder";
    }
    
    

    //riempe il template scelto con le parole delle liste del syntax
    public String fillTemplate(String template, Syntax syntaxAnalyzer) throws Exception {
    String result = template;
    
    //prende le liste aggiornate

    List<String> userNouns = syntaxAnalyzer.getNouns();
    List<String> userAdjectives = syntaxAnalyzer.getAdjectives();
    List<String> userVerbs = syntaxAnalyzer.getVerbs();

    // Sostituisce i [noun] con parole dalla lista userNouns
    while (result.matches("(?i).*\\[noun\\].*") && !userNouns.isEmpty()) {
        result = result.replaceFirst("(?i)\\[noun\\]", getSafeWord(userNouns.remove(0)));
    }
    
    // Sostituisce i [plural_noun] con parole dalla lista userNouns + "s"
    while (result.matches("(?i).*\\[plural_noun\\].*") && !userNouns.isEmpty()) {
        result = result.replaceFirst("(?i)\\[plural_noun\\]", getSafeWord(userNouns.remove(0)) + "s");
    }
    
    // Sostituisce i [verb] con parole dalla lista userVerbs
    while (result.matches("(?i).*\\[verb\\].*") && !userVerbs.isEmpty()) {
        result = result.replaceFirst("(?i)\\[verb\\]", getSafeWord(userVerbs.remove(0)));
    }
    
    // Sostituisce i [adjective] con parole dalla lista userAdjectives
    while (result.matches("(?i).*\\[adjective\\].*") && !userAdjectives.isEmpty()) {
        result = result.replaceFirst("(?i)\\[adjective\\]", getSafeWord(userAdjectives.remove(0)));
    }
    
        return result;
    }

    protected int countPlaceholders(String template, String regex) {
        return (int) Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
            .matcher(template)
            .results()
            .count();
    }

   

    //vengono contanti gli spazi nel template e il syntax aggiorna le liste 
    public void setSelectedTemplate(String template, Syntax syntaxAnalyzer){
        int nounCount = countPlaceholders(template, "\\[noun\\]");
        int pluralNounCount = countPlaceholders(template, "\\[plural_noun\\]");
        int verbCount = countPlaceholders(template, "\\[verb\\]");
        int adjectiveCount = countPlaceholders(template, "\\[adjective\\]");

        syntaxAnalyzer.updateListForTemplate(nounCount + pluralNounCount, "noun");
        syntaxAnalyzer.updateListForTemplate(verbCount, "verb");
        syntaxAnalyzer.updateListForTemplate(adjectiveCount, "adjective");
    }

    
}