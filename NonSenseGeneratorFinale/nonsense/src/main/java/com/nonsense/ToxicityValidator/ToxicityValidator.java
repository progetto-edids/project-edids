
package com.nonsense.ToxicityValidator;

import com.google.cloud.language.v1.*;
import com.google.cloud.language.v1.ClassificationCategory;
import java.io.IOException;

public class ToxicityValidator {
     static {
  
  System.setProperty("GOOGLE_APPLICATION_CREDENTIALS",
 "C:/Users/saret/Desktop/nonsensegenerator/nonsense/credentials/project-edids.json"
  );
 }

 //metodo che ritorna il punteggio di tossicità di una frase
  public static double getToxicityScore(String text) throws IOException {
  try (LanguageServiceClient language = LanguageServiceClient.create()) {
  Document doc = Document.newBuilder()
  .setContent(text)
  .setType(Document.Type.PLAIN_TEXT)
  .build();
 
  ModerateTextRequest request = ModerateTextRequest.newBuilder()
  .setDocument(doc)
  .build();
 
 ModerateTextResponse response = language.moderateText(request);
 
  double maxConfidence = 0.0;
  for (ClassificationCategory category :
  response.getModerationCategoriesList()) {
  maxConfidence = Math.max(maxConfidence, category.getConfidence());
  }
 
  return maxConfidence; // restituisce il punteggio di tossicità massimo
  }
  }
  }
 
