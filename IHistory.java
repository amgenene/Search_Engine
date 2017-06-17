/**
 * Alazar Genene
 * This is the interface for my hashmap that is implements in my encapsulation class History. It shares the 
 * methods used within that class and makes the data structure more flexible for change. 
 */
import java.util.LinkedList;
import java.util.Set;

public interface IHistory {
	public LinkedList<Webpage> getQuery(String query);
	public LinkedList<Webpage> putQuery(String query, LinkedList<Webpage>webList);
	public Set<String> keySetQuery ();
	public boolean checkContainsKey(String query);
}
