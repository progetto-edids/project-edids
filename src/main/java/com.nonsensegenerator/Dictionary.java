import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Dizionario {

    private Set<String> parole;
    private final String filePath = "dizionario.txt";

    public Dizionario() {
        this.parole = new HashSet<>();
        caricaDaFile();
    }

    // Legge il file e carica le parole nel dizionario
    private void caricaDaFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File dizionario non trovato, verrà creato alla prima aggiunta.");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String parola = scanner.nextLine().trim().toLowerCase();
                if (!parola.isEmpty()) {
                    parole.add(parola);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del dizionario: " + e.getMessage());
        }
    }

    public boolean contiene(String parola) {
        return parole.contains(parola.toLowerCase());
    }

    public void aggiungiParola(String parola) {
        String parolaLower = parola.toLowerCase();
        if (parole.add(parolaLower)) {
            salvaParolaSuFile(parolaLower);
            System.out.println("Parola aggiunta al dizionario: " + parolaLower);
        }
    }

    // Aggiunge una parola al file dizionario
    private void salvaParolaSuFile(String parola) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(parola + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio della parola nel file: " + e.getMessage());
        }
    }

    public void stampaDizionario() {
        System.out.println("Dizionario attuale: " + parole);
    }
}
