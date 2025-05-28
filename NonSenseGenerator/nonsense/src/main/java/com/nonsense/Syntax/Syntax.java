package com.nonsense.Syntax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.cloud.language.v1.AnalyzeSyntaxResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.PartOfSpeech;
import com.google.cloud.language.v1.Token;
import com.nonsense.Dictionary.Dictionary;

public class Syntax {
    private final LanguageServiceClient languageService;
    protected  List<String> nouns;
    protected  List<String> verbs;
    protected  List<String> adjectives;
    private final  Dictionary dictionary;

    // Costruttore
    public Syntax(Dictionary dictionary) throws IOException {
        this.languageService = LanguageServiceClient.create();
        this.nouns = new ArrayList<>();
        this.verbs = new ArrayList<>();
        this.adjectives = new ArrayList<>();
        this.dictionary = dictionary;
    }

    //analizza la frase id input, svuotando le liste di sostantivi, verbi e aggettivi
    public AnalyzeSyntaxResponse responseAnalycies(String inputSentence) throws Exception {
        nouns.clear();
        verbs.clear();
        adjectives.clear();

        Document doc = Document.newBuilder()
                .setContent(inputSentence)
                .setType(Document.Type.PLAIN_TEXT)
                .build();

        AnalyzeSyntaxResponse response = languageService.analyzeSyntax(doc);

        saveNouns(response);
        saveVerbs(response);
        saveAdjectives(response);

        updateDictionary();

        return response;
    }

    //metodi per salvare i vocaboli della frase di input nelle liste corrispondenti e aggiornare il dizionario
    private void saveNouns(AnalyzeSyntaxResponse response) {
        for (Token token : response.getTokensList()) {
            if (token.getPartOfSpeech().getTag() == PartOfSpeech.Tag.NOUN) {
                nouns.add(token.getText().getContent());
            }
        }
    }

    private void saveVerbs(AnalyzeSyntaxResponse response) {
        for (Token token : response.getTokensList()) {
            if (token.getPartOfSpeech().getTag() == PartOfSpeech.Tag.VERB) {
                verbs.add(token.getText().getContent());
            }
        }
    }

    private void saveAdjectives(AnalyzeSyntaxResponse response) {
        for (Token token : response.getTokensList()) {
            if (token.getPartOfSpeech().getTag() == PartOfSpeech.Tag.ADJ) {
                adjectives.add(token.getText().getContent());
            }
        }
    }

    //aggiorna i dizionari aggiungendo dei termini
    private void updateDictionary() {
        for (String noun : nouns) {
            dictionary.addNoun(noun);
        }
        for (String verb : verbs) {
            dictionary.addVerb(verb);
        }
        for (String adjective : adjectives) {
            dictionary.addAdjective(adjective);
        }
    }

    //valida la sintassi della frase, cioè controlla che ci sia almeno un verbo e un sostantivo
    public boolean validateSyntax(String inputSentence) {
        try {
            responseAnalycies(inputSentence);
            boolean hasNoun = !nouns.isEmpty();
            boolean hasVerb = !verbs.isEmpty();

            return hasNoun && hasVerb;

        } catch (Exception e) {
            return false;
        }
    }

    public void close() {
        languageService.close();
    }

    //metodi getters
    public List<String> getNouns() {
        return nouns;
    }

    public List<String> getVerbs() {
        return verbs;
    }

    public List<String> getAdjectives() {
        return adjectives;
    }

    //aggiorna le liste di termini che verrnano usati per riempire il template
    public void updateListForTemplate(int requiredCount, String type) {
    
    List<String> source = switch (type.toLowerCase()) {
        case "noun" -> nouns;
        case "verb" -> verbs;
        case "adjective" -> adjectives;
        default -> throw new IllegalArgumentException("Unknown type: " + type);
    };

    // Crea una copia modificabile della lista originale
    List<String> updatedList = new ArrayList<>(source);

    // Completa con parole dal dizionario se necessario
    while (updatedList.size() < requiredCount) {
        String fallback = switch (type.toLowerCase()) {
            case "noun" -> dictionary.getRandomNoun();
            case "verb" -> dictionary.getRandomVerb();
            case "adjective" -> dictionary.getRandomAdjective();
            default -> "placeholder";
        };
        if (fallback != null && !fallback.isBlank()) {
            updatedList.add(fallback);
        }
    }
    //se ci sono troppe parole toglie dalle liste delle parole finchè non sono del nuemro giusto
    while (updatedList.size() > requiredCount) {
        int randomIndex = (int) (Math.random() * updatedList.size());
        updatedList.remove(randomIndex);
    }


    // Sovrascrive la lista originale con la versione aggiornata
    switch (type.toLowerCase()) {
        case "noun" -> nouns = updatedList;
        case "verb" -> verbs = updatedList;
        case "adjective" -> adjectives = updatedList;
    }

  }

  // Usa Google Cloud NLP per ottenere l'albero sintattico.

    public Map<String, Object> getSyntaxTree(String sentence) throws IOException {
        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            Document doc = Document.newBuilder()
                .setContent(sentence)
                .setType(Document.Type.PLAIN_TEXT)
                .build();

            AnalyzeSyntaxResponse response = language.analyzeSyntax(doc);
            List<Token> tokens = response.getTokensList();

            // Costruisci una struttura ad albero basata sulle dipendenze
            Map<String, Object> tree = new LinkedHashMap<>();
            List<Map<String, Object>> treeNodes = new ArrayList<>();

            for (Token token : tokens) {
                Map<String, Object> node = new LinkedHashMap<>();
                String word = token.getText().getContent();
                String posTag = token.getPartOfSpeech().getTag().name();
                String dependency = token.getDependencyEdge().getLabel().name();

                node.put("word", word);
                node.put("tag", posTag);
                node.put("dependency", dependency);
                treeNodes.add(node);
            }

            tree.put("sentence", sentence);
            tree.put("tokens", treeNodes);
            return tree;
        }
    }

}
 
