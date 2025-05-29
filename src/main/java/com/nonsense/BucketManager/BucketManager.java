package com.nonsense.BucketManager;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BucketManager {

    private final List<String> bucket;

    public BucketManager() {
        this.bucket = new ArrayList<>();
    }

    // Aggiunge una frase al bucket
    public void add(String phrase) {
        if (phrase != null && !phrase.trim().isEmpty()) {
            bucket.add(phrase);
        }
    }

    // Svuota il bucket
    public void clear() {
        bucket.clear();
    }

    // Ritorna tutte le frasi
    public List<String> getAll() {
        return new ArrayList<>(bucket);
    }

    // Stampa tutte le frasi nel bucket
    public void printBucket() {
        if (bucket.isEmpty()) {
            System.out.println("The bucket is empty.\n");
        } else {
            System.out.println("Buket:");
            for (String phrase : bucket) {
                System.out.println("- " + phrase);
            }
        }
    }

    // Salva tutte le frasi in un file .txt
    public void saveToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String phrase : bucket) {
                writer.write(phrase);
                writer.newLine();
            }
            System.out.println("Bucket saved in: " + filePath + "\n");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

