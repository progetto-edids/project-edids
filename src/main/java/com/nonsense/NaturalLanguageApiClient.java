/*Classe NaturalLanguageApiClient: inizializza e la gestisce il LanguageServiceClient.
Per questa classe è stato utilizzato il GRASP pattern Singleton.
Garantisce che esista una sola istanza di LanguageServiceClient in tutta l'applicazione */

package com.nonsense;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotEnvException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.LanguageServiceSettings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;


//Classe di utilità per gestire l'inizializzazione e l'accesso al LanguageServiceClient.
 
public class NaturalLanguageApiClient {

    private static LanguageServiceClient instance;
    
    public static synchronized LanguageServiceClient getServiceClient() {
        if (instance == null) {
            try {
                Dotenv dotenv = Dotenv.configure()
                                      .directory(".")
                                      .load();

                String credentialsPath = dotenv.get("GOOGLE_CREDENTIALS_PATH");

                if (credentialsPath == null || credentialsPath.isEmpty()) {
                    System.err.println("Errore: Percorso delle credenziali non trovato nel file .env.");
                    System.err.println("Assicurati che il file .env esista e contenga la variabile GOOGLE_CREDENTIALS_PATH.");
                    return null; 
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
                // Cattura errori di I/O
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
        return instance; 
    }

    //Chiude l'istanza del LanguageServiceClient se è stata creata.   
    public static synchronized void closeServiceClient() {
        if (instance != null) {
            try {
                instance.close();
                instance = null; 
                System.out.println("LanguageServiceClient chiuso con successo.");
            } catch (Exception e) {
                System.err.println("Errore durante la chiusura del LanguageServiceClient: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
