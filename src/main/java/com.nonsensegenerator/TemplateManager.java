public class TemplateManager {
    private Map<String, String> templates;
    //private Syntax syntaxAnalyzer;

    public TemplateManager(Syntax syntaxAnalyzer) {
        //this.syntaxAnalyzer = syntaxAnalyzer;
        this.templates = new HashMap<>();
        initializeTemplates();
    }

    // Inizializza i template con segnaposto
    private void initializeTemplates() {
        templates.put("DECLARATION", "The [noun] [verb] the [adjective] [noun]");
        templates.put("QUESTION", "What are/is [noun] [verb]");
        templates.put("COMPLEX", "[noun] [verb] [adjective] [noun] but/and/so [noun] [verb] [adjective]");
    }

    
      //Estrae i tipi di termini (placeholder) dal template e conta quanti sono per tipo.
     
    public Map<String, integer> extractTerms(String templateType) {

        String template = templates.get(templateType.toUpperCase());
        if (template == null) {
            return null;
        }

        int nounSlotCount = 0;
        int adjSlotCount = 0;
        int verbSlotCount = 0;

        Map<String, Integer> terms = new HashMap<>();
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(template);

        // Conta le occorrenze dei segnaposto per tipo
        while (matcher.find()) {
            String termType = matcher.group(1);

            if (termType.equals("noun")){
                nounSlotCount++;
            }
            if (termType.equals("verb")){
                verbSlotCount++;
            }
            if (termType.equals("adjective")){
                adjSlotCount++;
            }
        }

        terms.put("noun", nounSlotCount);
        terms.put("verb", verbSlotCount);
        terms.put("adjective", adjSlotCount);

        return terms;
    }

    // per accedere ai template dall’esterno
    public String getTemplate(String templateType) {
        return templates.get(templateType.toUpperCase());
    }

    


}
