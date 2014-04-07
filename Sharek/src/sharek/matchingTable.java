/*
 * 
 */
package sharek;
import java.util.ArrayList;
import java.util.Collections;



// TODO: Auto-generated Javadoc
/**
 * This table is specified for SHAREK query processing which matches one rider against multiple drivers.
 */
public class matchingTable {
	
	 /**
	  *  Array list of matchingTuple 
	  *  
	  */
 	private static ArrayList<matchingTuple> table=new ArrayList<matchingTuple>();
	 
 	/**
 	 * Add a new matchingTuple (Driver) to the matchingTable.
 	 *
 	 * @param tuple the driver info
 	 */
 	public static void addMatchingTuple(matchingTuple tuple){
		 table.add(tuple);
	 }
	 
	 /**
 	 * Sort the matching table based on (euclidean return - driver trip cost)
 	 */
 	public static void sortMatchingTable(){
		 Collections.sort(table);
	 }
	 
 	/** The index nearest driver. */
 	public static int indexNearestDriver;
	 
 	/**
 	 * return the nearest neighbor (NN1) of the rider
 	 *
 	 * @return the matching tuple of the driver
 	 */
 	public static matchingTuple nearetDriverEuclideanPickup(){
		double minDist=Double.MAX_VALUE;
		 for (int i = 0; i < table.size(); i++) {
			matchingTuple tuple=table.get(i);
			if(minDist>tuple.euclideanPickup){
				indexNearestDriver=i;
				minDist=tuple.euclideanPickup;
			}
		}
		 return table.get(indexNearestDriver);
	 }
	
	 
	/**
	 * Checks if the matching table is empty.
	 *
	 * @return true, if is empty
	 */
	public static boolean isEmpty() {
		// TODO Auto-generated method stub
		return table.isEmpty();
	}

	/**
	 * Removes the tuple and all tuples below in matching table
	 */
	public static void removeTupleAndAllBelow() {
		// TODO Auto-generated method stub
		
		
		for (int i =indexNearestDriver; i < table.size(); i++) {
			table.remove(i);
		}
		
	}

	/**
	 * Removes the nearest neighbor tuple.
	 */
	public static void removeTuple() {
		// TODO Auto-generated method stub
		table.remove(indexNearestDriver);
		
	}

	/**
	 * Initialize the matching table
	 */
	public static void init() {
		// TODO Auto-generated method stub
		table.clear();
		indexNearestDriver=0;
		
	}

	

}
