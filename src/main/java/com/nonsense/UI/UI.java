/* Classe UI è stato utlizzato il GRASP pattern Controller. La classe orchestra un'interazione utente complessa 
delegando le responsabilità a componenti più specifici.*/

package com.nonsense.UI;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.nonsense.BucketManager.BucketManager;

import com.nonsense.NonSenseGenerator.NonSenseGenerator;
import com.nonsense.Syntax.Syntax;
import com.nonsense.TemplateManager.TemplateManager;
import com.nonsense.ToxicityValidator.ToxicityValidator;

public class UI {

    private final NonSenseGenerator generator;
    private final Scanner scanner;
    private final TemplateManager templateManager;
    private final BucketManager bucketManager;
    private final Syntax syntaxAnalyzer;
    private final ToxicityValidator toxicityValidator; 
    private final double TOXICITY_THRESHOLD = 0.5;

    // Costruttore
    public UI(NonSenseGenerator generator, Syntax syntaxAnalyzer, ToxicityValidator toxicityValidator) {
        this.generator = generator;
        this.syntaxAnalyzer = syntaxAnalyzer; 
        this.templateManager = new TemplateManager(); 
        this.scanner = new Scanner(System.in);
        this.bucketManager = new BucketManager(); 
        this.toxicityValidator = toxicityValidator; 
    }
    @SuppressWarnings("ConvertToTryWithResources")
    public void start() {
        System.out.println("=== Welcome to the NonSense Sentence Generator ===");

        boolean continueGenerating = true;

        // finchè l'utente non dirà di non voler continuare, gli verrà chiesto di inserire una nuova frase
        while (continueGenerating) {
            System.out.print("Write a sentence: ");
            String userInput = scanner.nextLine();

            try {
                // Verifica se la frase è valida (almeno un sostantivo e un verbo)
                if (!syntaxAnalyzer.validateSyntax(userInput)) {
                    System.out.println("The sentence must contain at least one noun and one verb. Please try again.");
                    continue;
                }

                List<String> userNouns = syntaxAnalyzer.getNouns();
                List<String> userVerbs = syntaxAnalyzer.getVerbs();
                List<String> userAdjectives = syntaxAnalyzer.getAdjectives();

                // Mostra parole estratte
                System.out.println("Extracted words:");
                System.out.println(" Nouns: " + userNouns);
                System.out.println(" Verbs: " + userVerbs);
                System.out.println(" Adjectives: " + userAdjectives);

                // chiede se vuole l'albero sintattico
                System.out.println("\n------Do you want to see the syntax tree of you sentence?(yes/no)------");
                String input = scanner.nextLine().trim().toLowerCase();
                if (input.equals("yes") || input.equals("y")) {
                    Map<String, Object> syntaxTree = syntaxAnalyzer.getSyntaxTree(userInput); 
                    System.out.println("Syntax Tree:\n");
                    System.out.println(syntaxTree);
                } else {
                    System.out.println("\nNo syntax tree will be shown.\n");
                }

                // Mostra template disponibili e chiede se si vuole usare un template casuale
                String selectedTemplate;
                List<String> templates = templateManager.getTemplates();
                System.out.println("\nAvailable templates:");
                for (int i = 0; i < templates.size(); i++) {
                    System.out.printf(" [%d] %s\n", i + 1, templates.get(i));
                }

                System.out.println("\n------Do you want to use a random template?\nIf no you will choose a template from the list. (yes/no)------");
                String input1 = scanner.nextLine().trim().toLowerCase();

                // se l'utente dice di voler usare un template casuale, ne viene generato uno
                if (input1.equals("yes") || input1.equals("y")) {
                    String randomTemplate = templateManager.generateRandomTemplate();
                    System.out.println("\nRandom Template: " + randomTemplate + "\n");
                    selectedTemplate = randomTemplate;
                } else {
                    // l'utente sceglie il template
                    int choice = -1;
                    while (choice < 1 || choice > templates.size()) {
                        System.out.print("\nChoose a template by number: ");
                        try {
                            choice = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("\nPlease enter a valid number.");
                        }
                    }

                    selectedTemplate = templates.get(choice - 1);
                    System.out.println("\nSelected Template: " + selectedTemplate);
                }

                // viene settato il template scelto nel generator
                generator.setSelectedTemplate(selectedTemplate, syntaxAnalyzer);

                // viene chiesto se si vuole generare la frase nonsense
                System.out.println("\n------Template choosen, do you want to generate the nonsense sentence?(yes/no)------");
                String input2 = scanner.nextLine().trim().toLowerCase();

                if (input2.equals("yes") || input2.equals("y")) {
                    System.out.println("\n\nNonsense sentence will be generated.\n\n");

                    // riempe il template scelto con le parole delle liste
                    String nonsense = generator.fillTemplate(selectedTemplate, syntaxAnalyzer);

                    // Validazione della tossicità, se è tossica non la mostra altrimenti la mostra e chiede se la si vuole salvare nel bucket
                    double toxicity = toxicityValidator.getToxicityScore(nonsense); 
                    
                    System.out.printf("\nToxicity Score: %.2f\n", toxicity);

                    if (toxicity >= TOXICITY_THRESHOLD) {
                        System.out.println("Warning: This sentence might be considered toxic.");
                    } else {
                        System.out.println("\nGenerated Sentence: " + nonsense + "\n");
                        System.out.println("This sentence appears to be safe.\n");

                        // utilizzo del bucket, chiede se si vuole salvare la frase generata nel bucket
                        System.out.println("------Do you want to add the nonsense sentence to the bucket?(yes/no)------");
                        String input3 = scanner.nextLine().trim().toLowerCase();
                        if (input3.equals("yes") || input3.equals("y")) {
                            bucketManager.add(nonsense);
                            System.out.println("\nNonsense sentence added to the bucket.\n");
                        } else {
                            System.out.println("\nNonsense sentence not added to the bucket.\n");
                        }
                    }
                } else {
                    System.out.println("\nNo nonsense sentence will be generated.\n");
                }

                // chiede se si vuole visualizzare il bucket di frasi salvate fino a quel momento
                System.out.println("------Do you want to see the bucket?(yes/no)------");
                String input4 = scanner.nextLine().trim().toLowerCase();
                if (input4.equals("yes") || input4.equals("y")) {
                    bucketManager.printBucket();
                } else {
                    System.out.println("No bucket will be shown.");
                }

                // chiede se si vuole salvare il bucket in un file
                System.out.println("\nDo you want to save the bucket in a file?(yes/no)");
                String input5 = scanner.nextLine().trim().toLowerCase();
                if (input5.equals("yes") || input5.equals("y")) {
                    bucketManager.saveToFile("bucket.txt");
                } else {
                    System.out.println("\nNo bucket will be saved.");
                }

                // chiede se si vuole svuotare il bucket
                System.out.println("\n------Do you want to clear the bucket?(yes/no)------");
                String input6 = scanner.nextLine().trim().toLowerCase();
                if (input6.equals("yes") || input6.equals("y")) {
                    bucketManager.clear();
                    System.out.println("\nBucket cleared.\n");
                } else {
                    System.out.println("\nThe bucket wil remain the same.");
                }

            } catch (Exception e) {
                System.out.println("Error during sentence processing: " + e.getMessage());
                e.printStackTrace(); 
            }

            // chiede se si vuole generare un altra frase. In caso negativo il programma termina.
            System.out.print("\nGenerate another? (yes/no): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (!input.equals("yes") && !input.equals("y")) {
                continueGenerating = false;
                System.out.println("Goodbye!");
            }
        }

        scanner.close();
    }
}
