package com.nonsense;

import com.nonsense.NonSenseGenerator.NonSenseGenerator;
import com.nonsense.UI.UI;

public class Main {

    public static void main(String[] args) {
        
        UI ui = new UI(new NonSenseGenerator());
        ui.start();
      
        System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", "C:/Users/franc/OneDrive/Desktop/project-edids.txt.json");
    }
}