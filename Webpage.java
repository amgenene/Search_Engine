import java.util.LinkedList;
/**
 * @author Alazar Genene
 * 
 */
class Webpage implements Comparable<Webpage> {
	public String url;
	String title;
	String body;
	LinkedList<String> referencedURLs;
	public Double rank = 0.0;

	// The constructor converts title and body to lowercase, to ease
	// other computations later
	Webpage(String locator, String title, String body, LinkedList<String> referencedURLs) {
		this.url = locator;
		this.title = title.toLowerCase();
		this.body = body.toLowerCase();
		this.referencedURLs = referencedURLs;
	}

	// This method checks to see if the body contains the query and if it does,
	// return true.
	public boolean containsQuery(String query) {
		// if the webpage has the query return true
		if (body.contains(query)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This compare to method helps us implement our interface for this class, helping us
	 * compare webpages ranks to one another and is very helpful for test cases when looking at the ranks. 
	 */
	@Override
	public int compareTo(Webpage myPage) {
		if (this.rank == myPage.rank) {
			return 0;
		}
		else if (this.rank > myPage.rank) {
			return -1;
		}
		return 1;
	}
/**
 * This method adds the sponsoredRate for that page to the page’s current rank.
 * @param rate
 */
	public void updateRank(double rate) {
		rank += rate;
	}
/**
 * This counts the links within the referencedURLS rating 
 * for each page that this one links to (again, excluding self-links).
 * @return count 
 */
	public int getLinkCount() {
		int count = 0;
		for (String link : referencedURLs) {
			if (url.equals(link)) {
				continue;
			}
			count++;
		}
		return count;
	}
	
}