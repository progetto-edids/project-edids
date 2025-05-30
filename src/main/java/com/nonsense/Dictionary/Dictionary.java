/*Classe Dictionary: gestiesce i dizionari di sostantivi, verbi e aggettivi*/
package com.nonsense.Dictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Dictionary {

    private final List<String> nounList;
    private final List<String> verbList;
    private final List<String> adjectiveList;
    private final Random random = new Random();

    // Costruttore: inizializza gli array con parole predefinite
    public Dictionary() {
        // Esempi di parole predefinite per ciascun tipo
        this.nounList = new ArrayList<>(Arrays.asList(  
       "apple", "banana", "car", "dog", "elephant",
            "house", "tree", "book", "cat", "ball",
            "school", "friend", "bird", "computer", "water",
            "chair", "table", "phone", "pen", "pencil",
            "window", "door", "shoe", "hat", "bed",
            "bottle", "box", "bag", "clock", "mouse",
            "river", "mountain", "sun", "moon", "star",
            "family", "baby", "mother", "father", "brother",
            "sister", "child", "street", "city", "village",
            "job", "food", "game", "toy", "train"));
        this.verbList = new ArrayList<>(Arrays.asList(  
       "run", "jump", "eat", "sleep", "talk",
            "walk", "write", "read", "play", "listen",
            "look", "cook", "drink", "swim", "open",
            "close", "sing", "dance", "laugh", "cry",
            "drive", "fly", "draw", "paint", "wash",
            "clean", "help", "build", "break", "buy",
            "sell", "cut", "throw", "catch", "learn",
            "teach", "stand", "sit", "climb", "watch",
            "touch", "smile", "shout", "think", "wait",
            "send", "receive", "win", "lose", "grow"));
        this.adjectiveList = new ArrayList<>(Arrays.asList(
       "quick", "slow", "happy", "sad", "beautiful",
            "tall", "short", "big", "small", "kind",
            "angry", "funny", "loud", "quiet", "bright",
            "dark", "warm", "cold", "hot", "cool",
            "young", "old", "new", "clean", "dirty",
            "fast", "friendly", "mean", "strong", "weak",
            "rich", "poor", "nice", "rude", "lazy",
            "hardworking", "soft", "hard", "sweet", "bitter",
            "brave", "scared", "clever", "silly", "honest",
            "beautiful", "ugly", "polite", "calm", "noisy"));
    }

    // === RANDOM GETTERS ===
    public String getRandomNoun() {
        return getRandomFromList(nounList, "noun");
    }

    public String getRandomVerb() {
        return getRandomFromList(verbList, "verb");
    }

    public String getRandomAdjective() {
        return getRandomFromList(adjectiveList, "adjective");
    }

    private String getRandomFromList(List<String> list, String type) {
        if (list.isEmpty()) {
            System.out.println("Il dizionario di tipo " + type + " è vuoto. Restituito 'placeholder'.");
            return "placeholder";
        }
        return list.get(random.nextInt(list.size()));
    }

    // === ADD METHODS ===
    public boolean addNoun(String noun) {
        return addWordToList(noun, nounList, "noun");
    }

    public boolean addVerb(String verb) {
        return addWordToList(verb, verbList, "verb");
    }

    public boolean addAdjective(String adjective) {
        return addWordToList(adjective, adjectiveList, "adjective");
    }

    private boolean addWordToList(String word, List<String> list, String type) {
        word = word.trim();

        if (list.contains(word)) {
            System.out.println("Word '" + word + "' is already in the dictionary " + type + ".");
            return false;
        }

        list.add(word);
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        System.out.println("Word '" + word + "' added to the dictionary " + type + ".");
        return true;
    }

    //solo per i test
    public void clearNouns() {
        nounList.clear();
    }
    public void clearVerbs() {
        verbList.clear();
    }   
    public void clearAdjectives() {
        adjectiveList.clear();
    }

    
}
