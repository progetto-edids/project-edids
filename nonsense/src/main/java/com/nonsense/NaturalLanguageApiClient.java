package com.nonsense;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotEnvException; // Importa DotEnvException
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.LanguageServiceSettings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Classe di utilità per gestire l'inizializzazione e l'accesso al LanguageServiceClient.
 * Implementa un pattern Singleton per assicurare che ci sia una sola istanza del client.
 */
public class NaturalLanguageApiClient {

    private static LanguageServiceClient instance;
    // Non è necessario un campo statico per Dotenv, può essere locale al metodo getServiceClient

    public static synchronized LanguageServiceClient getServiceClient() {
        // Applica il pattern Singleton: crea l'istanza solo se non esiste già
        if (instance == null) {
            try {
                // Configura dotenv per cercare il file .env nella directory corrente (cioè la radice del progetto)
                //String absoluteDotEnvDirectory = "C:\\project-edids-mainSARA\\project-edids-main\\NonSenseGenerator\\nonsense";
                Dotenv dotenv = Dotenv.configure()
                                      .directory(".") // Indica la directory corrente
                                      .load();

                String credentialsPath = dotenv.get("GOOGLE_CREDENTIALS_PATH");

                if (credentialsPath == null || credentialsPath.isEmpty()) {
                    System.err.println("Errore: Percorso delle credenziali non trovato nel file .env.");
                    System.err.println("Assicurati che il file .env esista e contenga la variabile GOOGLE_CREDENTIALS_PATH.");
                    return null; // Restituisce null se il percorso non è configurato
                }

                // Carica le credenziali dal percorso specificato e crea il LanguageServiceClient
                GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsPath))
                        .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-language"));

                LanguageServiceSettings settings = LanguageServiceSettings.newBuilder()
                        .setCredentialsProvider(() -> credentials)
                        .build();
                instance = LanguageServiceClient.create(settings);
                System.out.println("Client Natural Language API inizializzato con successo da NaturalLanguageApiClient.");

            } 
             catch (IOException e) {
                // Cattura errori di I/O (es. file credenziali non trovato o illeggibile)
                System.err.println("Errore durante il caricamento o l'uso del file delle credenziali: " + e.getMessage());
                e.printStackTrace();
                instance = null;
                return null;
            } catch (Exception e) {
                // Cattura qualsiasi altra eccezione generica durante l'inizializzazione del client
                System.err.println("Si è verificato un errore generico durante l'inizializzazione del client: " + e.getMessage());
                e.printStackTrace();
                instance = null;
                return null;
            }
        }
        return instance; // Restituisce l'istanza esistente o appena creata (o null in caso di errore)
    }


    /**
     * Chiude l'istanza del LanguageServiceClient se è stata creata.
     * Dovrebbe essere chiamata quando l'applicazione non ha più bisogno del client.
     */
    public static synchronized void closeServiceClient() {
        if (instance != null) {
            try {
                instance.close();
                instance = null; // Resetta l'istanza a null dopo la chiusura
                System.out.println("LanguageServiceClient chiuso con successo.");
            } catch (Exception e) {
                System.err.println("Errore durante la chiusura del LanguageServiceClient: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
