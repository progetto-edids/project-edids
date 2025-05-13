import com.google.cloud.language.v1.*;
import com.google.cloud.language.v1.Document.Type;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Syntax {

    private List<String> nomi = new ArrayList<>();
    private List<String> verbi = new ArrayList<>();
    private List<String> aggettivi = new ArrayList<>();

    private Dizionario dizionario;

    public Syntax(Dizionario dizionario) {
        this.dizionario = dizionario;
    }

    public boolean analizzaFrase(String frase) {
        Document doc = Document.newBuilder()
                .setContent(frase)
                .setType(Type.PLAIN_TEXT)
                .setLanguage("it")
                .build();

        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            AnalyzeSyntaxResponse response = language.analyzeSyntax(doc);

            boolean sintatticamenteCorretta = !response.getTokensList().isEmpty();
            if (!sintatticamenteCorretta) {
                System.err.println("Frase non valida sintatticamente.");
                return false;
            }

            for (Token token : response.getTokensList()) {
                String parola = token.getText().getContent();
                PartOfSpeech.Tag tag = token.getPartOfSpeech().getTag();

                switch (tag) {
                    case NOUN:
                        nomi.add(parola);
                        break;
                    case VERB:
                        verbi.add(parola);
                        break;
                    case ADJ:
                        aggettivi.add(parola);
                        break;
                    default:
                        break;
                }

                // Aggiunta al dizionario se non presente
                if (!dizionario.contiene(parola)) {
                    dizionario.aggiungiParola(parola);
                }
            }

            return true;

        } catch (IOException e) {
            System.err.println("Errore durante l'analisi sintattica: " + e.getMessage());
            return false;
        }
    }

    public void stampaParoleTrovate() {
        System.out.println("Nomi: " + nomi);
        System.out.println("Verbi: " + verbi);
        System.out.println("Aggettivi: " + aggettivi);
    }
}
