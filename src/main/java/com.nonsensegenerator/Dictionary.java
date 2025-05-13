import java.util.HashSet;
import java.util.Set;

public class Dizionario {
    private Set<String> parole;

    public Dizionario() {
        this.parole = new HashSet<>();
        // Dizionario iniziale
        parole.add("cane");
        parole.add("correre");
        parole.add("verde");
        parole.add("ciao");
    }

    public boolean contiene(String parola) {
        return parole.contains(parola.toLowerCase());
    }

    public void aggiungiParola(String parola) {
        parole.add(parola.toLowerCase());
        System.out.println("Parola aggiunta al dizionario: " + parola);
    }

    public void stampaDizionario() {
        System.out.println("Dizionario attuale: " + parole);
    }
}
