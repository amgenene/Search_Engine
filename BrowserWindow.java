import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * 
 * Alazar Genene
 * This is the new class Browser Window and was apart of the FULL assignment. This class is used to seperate
 * the User Interface from the Search engine and Data Structures.
 */
public class BrowserWindow {
	SearchEngine E; 
	
	BrowserWindow (SearchEngine E){
		this.E= E;
	}
	// ***** THE SCREEN/USER INTERFACE *******************
	// You should NOT need to edit this method
	void screen() {
		System.out.println("-------------------------------------");
		System.out.println("Enter a number to select an option");
		System.out.println("1. Search for pages");
		System.out.println("2. Visit a page");
		System.out.println("3. Exit the system (and lose all index data)");
		String choice = this.E.keyboard.next();
		// eat up the rest of the line
		this.E.keyboard.nextLine();

		try {
			if (choice.equals("1")) {
				System.out.println("Enter your query:");
				String query = this.E.keyboard.nextLine();
				System.out.println("Search results: ");
				System.out.println(this.E.runQuery(query));
				screen();
			} else if (choice.equals("2")) {
				System.out.println("Which page (filename) do you want to visit?:");
				String filename = this.E.keyboard.next();
				System.out.println(this.E.visitPage(filename));
				screen();
			} else if (choice.equals("3"))
				System.out.println("Thanks for searching with us");
			else
				System.out.println("ERROR *** Unrecognized option " + choice + ". Try again");
		} catch (UnsupportedFileExn e) {
			System.out.println("ERROR *** Filetype not supported: " + e.filename);
			screen();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR *** No page found at address " + e);
			screen();
		}
	}
}
