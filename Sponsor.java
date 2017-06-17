/**
 * Alazar Genene
 * this class is used to encapsulate the data structure, which we choose is a HashMap for sponsors rate information. 
 */
import java.util.HashMap;

public class Sponsor {
	private HashMap <String, Double > sponsors; 
	Sponsor(){
		
		this.sponsors = new HashMap<>();
		};
/**
 * This method either adds the sponsor to the data structure 
 * (if the sponsor is new) or updates the existing rate (if the sponsor already has a rate).
 * @param newSponsor
 * @param rate
 */
public void updateSponsor (String newSponsor, double rate){
	String sponsorLower = newSponsor.toLowerCase();
	if (sponsors.containsKey(sponsorLower)){
		sponsors.put(sponsorLower, rate);
	}
	else {
		sponsors.put(sponsorLower, rate);	
	}
}
/**
 * This method checks to see if sponsor already exists and the new rate is lower than the old rate.
 * If these are true than an exception is thrown that will print out a message that is defined in the
 * exception class. 
 * @param newSponsor
 * @param rate
 * @throws LowerRateException
 */
public void checkSponsor (String newSponsor, double rate) throws LowerRateException{
	String sponsorLower = newSponsor.toLowerCase();
	if (sponsors.containsKey(sponsorLower)) {
		double oldRating = sponsors.get(sponsorLower);
		if (oldRating> rate){
		throw new LowerRateException(newSponsor, rate);
		}
	}
}
/**
 * This method goes through the key set of the hashmap and for each key does a contains method 
 * to see if the URL contains the sponsor and if so, a sponsor pays for that page. 
 * @param URL
 * @return a double indicating how much sponsors are paying for this page.
 */
public double getSponsoredRate (String URL){
	// go through key set, for each key and do a contains method
	for (String sponsor : sponsors.keySet()){
		if (URL.contains(sponsor)){
		return sponsors.get(sponsor);
		}
	}
	return 0;
}
}
