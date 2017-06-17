/**
 * 
 * @author Alazar Genene
 * This class which updateSponsor throws if the entered rate is lower than 0 or greater than .1 
 * (rates of 0 are valid). We send the message "This is an invalid rate" if an exception is throw and catched. 
 */
public class InvalidRateException extends Exception {
	double invaildRate;
	
InvalidRateException (double invalidRate){
	super("This is an invalid rate: " + invalidRate);
	this.invaildRate = invaildRate;
}
}
