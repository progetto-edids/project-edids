public class UI {
  System.out.println("Ciao!\nInserisci una frase:");
  Scanner scanner = new Scanner(System.in);
  String input = scanner.nextLine();
 
  String[] parole = input.trim().split("\\s+"); // stringa di input tokenizzata
  

  public UI() {
    
  }
}
