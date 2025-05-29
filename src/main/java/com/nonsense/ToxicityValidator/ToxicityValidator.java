package com.nonsense.ToxicityValidator;

import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.ModerateTextRequest;
import com.google.cloud.language.v1.ModerateTextResponse;
import com.google.cloud.language.v1.ClassificationCategory;
import com.google.cloud.language.v1.LanguageServiceClient;


public class ToxicityValidator {

    //  campo per l'istanza di LanguageServiceClient
    private final LanguageServiceClient languageService;

    // Costruttore
    public ToxicityValidator(LanguageServiceClient languageServiceClient) {
        this.languageService = languageServiceClient;
    }

    
    public double getToxicityScore(String text)  {
        
        Document doc = Document.newBuilder()
                .setContent(text)
                .setType(Document.Type.PLAIN_TEXT)
                .build();

        ModerateTextRequest request = ModerateTextRequest.newBuilder()
                .setDocument(doc)
                .build();

        // Effettua la chiamata all'API usando l'istanza iniettata
        ModerateTextResponse response = languageService.moderateText(request);

        double maxConfidence = 0.0;
        for (ClassificationCategory category :
                response.getModerationCategoriesList()) {
            maxConfidence = Math.max(maxConfidence, category.getConfidence());
        }

        return maxConfidence; // restituisce il punteggio di tossicità massimo
        // La chiusura del client LanguageServiceClient è gestita da NaturalLanguageApiClient nel Main.
    }
}
