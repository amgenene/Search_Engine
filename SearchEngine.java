
/* Starter file for homework 5 
 * 
 * The file is marked throughout with what you can, cannot, and should not need to edit.
 */
/** htnorthcott & mluu
 * Haylea Northcott and Max Luu
 * This class is the search engine which is similar to a google interface and holds all methods 
 * and variables that affect this class. 
/**
 */
import java.util.HashMap;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

// This class basically mocks a search engine like google, bing, yahoo, etc. 
public class SearchEngine implements ISearchEngine {
	// leave these first two variables alone
	public Scanner keyboard = new Scanner(System.in);
	public SimpleMarkdownReader m = new SimpleMarkdownReader();
	private History history = new History();
	private Sponsor sponsors = new Sponsor();

	// you are welcome to replace the pages variable with your own data
	// structures.
	// the specific name "pages" will not be referenced in any of our
	// tests/autograding
	private LinkedList<Webpage> pages = new LinkedList<>();

	// constructor
	// you are welcome to change what the constructor does, as long as you leave
	// the header intact
	SearchEngine(LinkedList<String> initPages) throws FileNotFoundException, UnsupportedFileExn {
		for (String pageLoc : initPages) {
			pages.add(addSite(pageLoc));
		}
	}

	// ****** THE SEARCH ENGINE METHODS YOU NEED TO PROVIDE ***********
	// Even if you encapuslate (full version), leave methods with these headers
	// in the SearchEngine class (our tests/autograding expect to find them
	// here)

	// given query string, produce webpages that meet query
	// you will need to edit this, but do not edit the header (autograding
	// expects this)
	/**
	 * RunQuery cleans all the data by turning to lowercase and stripping all
	 * filter words using methods provided. If the query is already seen it
	 * returns the query search from the hashmap previously. If the query has
	 * not been seen it will check a list of webpages and match the body and url
	 * for the query. If found, it will be added to a new Linked List called
	 * webList Overall this function produces the the webList which will have
	 * all the previous results of the webpages from the queries.
	 */
	public LinkedList<Webpage> runQuery(String query) {
		// cleaning data
		query = query.toLowerCase();
		query = stripFillers(query);
		updateLinkRate();
		if (alreadySawQuery(query)) {
			return history.getQuery(query);
		} else {
			LinkedList<Webpage> webList = new LinkedList<>();
			for (Webpage el : pages) {
				el.rank = 0.0;
				el.updateRank(getSponsoredRate(el.url));
				if (el.title.toLowerCase().contains(query) || el.body.toLowerCase().contains(query)) {
					webList.add(el);
				}
			}
			history.putQuery(query, webList);
			return sortRank(webList);
		}
	}

	/**
	 * Visit page method takes in a location and checks to see if the LinkedList
	 * of Webpage contain the Page and if not, add it to the list. If the body
	 * and title contain the query you want to add it to our hashmap. You return
	 * thePage at the end of this function which is a Webpage with a location.
	 */
	public Webpage visitPage(String location) throws UnsupportedFileExn, FileNotFoundException {
		Webpage thePage = addSite(location);
		// for (Webpage el : visitedPages) {
		if (!pages.contains(thePage)) {
			pages.add(thePage);
		}
		for (String query : history.keySetQuery()) {
			if (thePage.title.contains(query) || thePage.body.contains(query)) {
				history.getQuery(query).add(thePage);
			}
		}
		return thePage;
	}

	// produce the number of web pages known in the system
	// you are welcome to edit this method as long as you leave the header
	// intact (autograding expects this)
	public int knownPageCount() {
		return pages.size();
	}

	// determine whether given query has been seen in the search engine
	// you will need to edit this, but do not edit the header (autograding
	// expects this)
	// This method checks to see if the query is already stored in memory witin
	// the hashmap. This is a helper
	// method that was very helpful in determining if the results of the query
	// were cached or not.
	public boolean alreadySawQuery(String query) {
		return history.checkContainsKey(query);
	}

	// ****** ADDING SITES TO THE ENGINE ***************
	// parses given file into a webpage
	// you are welcome to edit this method as long as you leave the header
	// intact.
	// you should NOT need to edit the call to readPage (you may want to add
	// statements around it though)
	Webpage addSite(String locationName) throws UnsupportedFileExn, FileNotFoundException {
		Webpage newWP;
		if (locationName.endsWith(".md")) {
			return (m.readPage(locationName));
		} else {
			throw new UnsupportedFileExn(locationName);
		}
	}

	// ****** REMOVING FILLER WORDS FROM QUERIES *****************
	// Don't edit either the list of fillerWords or the stripFillers method
	private LinkedList<String> fillerWords = new LinkedList<String>(
			Arrays.asList("a", "an", "the", "of", "on", "in", "to", "by", "about", "how", "is", "what", "when"));

	// remove the filler words from a string
	// assume string has no punctuation
	private String stripFillers(String query) {
		String[] wordArray = query.toLowerCase().split(" ");
		String resultStr = "";
		for (int i = 0; i < wordArray.length; i++) {
			if (!(fillerWords.contains(wordArray[i])))
				resultStr = resultStr + wordArray[i];
		}
		return resultStr;
	}
/**
 * This method sorts myPages using this built in Java function, collection. java.util.Collections was 
 * imported to make this function work.
 * @param myPages- a list of webpages 
 * @return a list of sorted webpages by rank 
 */
	public LinkedList<Webpage> sortRank(LinkedList<Webpage> myPages) {
		Collections.sort(myPages);
		return myPages;
	}
/**
 * This method takes a sponsor name and a new rate (a double) and sets the sponsor to have the given rate.
 * The method either adds the sponsor to the data structure (if the sponsor is new) 
 * or updates the existing rate (if the sponsor already has a rate). updateSponsor is also responsible
 * for throwing exceptions if the rate is lower than 0 or greater than .1. 
 */
	@Override
	public void updateSponsor(String sponsor, double rate) throws LowerRateException, InvalidRateException {
		if (rate > .1 || rate < 0) {
			throw new InvalidRateException(rate);
		}
		sponsors.checkSponsor(sponsor, rate);
		// updates hashmap if passes through both exceptions
		sponsors.updateSponsor(sponsor, rate);
	}
/**
 * This function indicates how much sponsors are paying for this page. A sponsor pays for a page if the sponsors
 * name is a substring of the url. Set what the sponsor pays in test cases. 
 * @param String URL
 * @return a double indicating how much sponsors are paying for this page.
 */
	public double getSponsoredRate(String URL) {
		return sponsors.getSponsoredRate(URL);
	}
	/**
	 * This funtion iterates over all pages and distributes the link credits.
	 * Count how many references there are to other pages excluding self links 
	 * And call the computation to the rating for each page that this one links to = getLinkCount 
	 */
	public void updateLinkRate() {
		for (Webpage el : pages){
			if (el.getLinkCount() == 0){
				continue;
			}
			el.updateRank(1/(el.getLinkCount()));
		}
	}

}
