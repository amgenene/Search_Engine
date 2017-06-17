import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
/**
 * 
 * @author Alazar Genene
 * This class was made for the FULL part of the assignment. It encapsulates the data structure to hide
 * details of the query-cache data structure from the SearchEngine class 
 */
public class History implements IHistory {
	private HashMap<String, LinkedList<Webpage>> history;
	History(){
		
	this.history = new HashMap<>();
	};
	
	// make methods for get, put, keySet, containsKey 
	//return history.get(query);
	// This method looks through the hashmap to get the query, and returns the value of the specified key or null
	public LinkedList<Webpage> getQuery(String query){
		return history.get(query);
	}
	//history.put(query, webList);
	// This method associates the specified value with the specified key in the hashmap 
	public LinkedList<Webpage> putQuery(String query, LinkedList<Webpage>webList){
		return history.put(query, webList);
	}
	//history.keySet() 
	// This method returns a Set view of the keys contained in this map 
	public Set<String> keySetQuery (){
		return history.keySet();
	}
	
	// history.containsKey(query);
	// This method returns true if this map contains the mapping for the specified key 
	public boolean checkContainsKey(String query){
		return history.containsKey(query);
	}
}
