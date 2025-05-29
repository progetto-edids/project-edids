***ISTRUZIONI:***

Come preparare l'ambiente, compilare e avviare l’applicazione.

1. Clonare il progetto con: 

		git clone https://github.com/sara-githu/project-edids
Oppure utilizzando la funzione clone dell'IDE utilzzato.

3. Configurare Google Cloud:

Accedere alla console di Google Cloud.

Creare un Service Account con il ruolo di Cloud Natural Language API User.

Scaricare il file .json di credenziali.

Rinominarlo come 

		google-credentials.json 
Copiarlo nella cartella credentials. 

Il path finale dovrebbe essere

		credentials/google-credentials.json 
Che verrà ignorato da git.
Il progetto è già configurato per usare questo path.


3. Per chi vuole poter modificare il progetto:

Prerequisito avere Maven installato sul computer con le variabili d'ambiente corrette.

Compilare il progetto: 
Aprire il terminale nella cartella principale del progetto (dove si trova pom.xml) e digitare: 

	mvn clean install

Eseguire il progetto : 
Aprire il terminale nella cartella principale del progetto (dove si trova pom.xml) e digitare: 

	mvn exec:java


Eseguire i test
Aprire il terminale nella cartella principale del progetto (dove si trova pom.xml) e digitare: 

	mvn test



4. Per utenti finali: 

 Apri la cartella del progetto con un IDE 

 Fare Run del Main.java che si trova nel seguente path 
 		
   	C:src\main\java\com\nonsense\Main.java





