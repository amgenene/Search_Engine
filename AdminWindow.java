import java.util.Scanner;
import java.io.*;
/**
 * 
 * @author Alazar Genene
 *This class provides a keyboard interface for interacting with one of the new methods for this week. We added
 *our exceptions to this class
 */
class AdminWindow {
  SearchEngine s;
  Scanner keyboard = new Scanner(System.in);
  
  AdminWindow(SearchEngine s){
    this.s = s;
  }
  
  public void adminScreen() {
    System.out.println("-------------------------------------");
    System.out.println("Enter a sponsor name (or done to exit)");
    String choice = keyboard.next();
    // eat up the rest of the line
    keyboard.nextLine();
    
    if (choice.equals("done"))
      System.out.println("Existing admin operations");
    else {
      System.out.println("Enter sponsor's price per page hit (as a double)");
      String rateString = keyboard.next();
      Double rate = Double.parseDouble(rateString); 
      try {
		s.updateSponsor(choice, rate);
	} catch (LowerRateException e) {
		e.printStackTrace();
	} catch (InvalidRateException e) {
		e.printStackTrace();
	}
      adminScreen();
    }
  }
}