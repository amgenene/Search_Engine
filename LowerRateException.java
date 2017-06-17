/**
 * 
 * @author Alazar Genene
 * This class which updateSponsor throws if the sponsor already exists and the new rate is lower than the
 * old rate. It prints out a message via admin screen that said the sponsor already exists 
 * in the hashmap and the new rate is lower than the old. 
 *
 */
public class LowerRateException extends Exception {
	String sponsor;
	double invalidRate;
	
	LowerRateException(String sponsor, double invalidRate){
		super("This sponsor " + sponsor + "already exists and the new rate is lower than the old: " + invalidRate);
		this.sponsor = sponsor;
		this.invalidRate = invalidRate;
	}
}
