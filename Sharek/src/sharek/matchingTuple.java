package sharek;
import java.util.ArrayList;



// TODO: Auto-generated Javadoc
/**
 * The Tuple of matching table. Each contains the information for each driver session. 
 */
public class matchingTuple implements Comparable<matchingTuple> {

	/** The driver id. */
	long driverId;
	
	/** The actual distance taken for driver to reach the rider */
	double Pickup;
	
	/** The euclidean distance for driver to reach driver. */
	double euclideanPickup;
	
	/** The actual distance taken for driver to return for destination. */
	double Return;
	
	/** The euclidean distance taken for driver to return for destination. */
	double euclideanReturn;
	
	/** The driver trip cost. */
	double driverTripCost;
	
	/** The Rider trip cost. */
	double RiderTripCost;
	
	/** The ridesharing cost. */
	double ridesharingCost;
	
	/** The pickup path. the shortest path from driver start node to rider start node */
	ArrayList<Long>pickupPath;
	
	/** The return path. the shortest path from rider destination to driver destination*/
	ArrayList<Long>returnPath;
	
	
	/**
	 * Instantiates a new matching tuple.
	 *
	 * @param id the id
	 * @param euclideanPickup the euclidean pickup
	 * @param euclideanReturn the euclidean return
	 * @param driverTrip the driver trip
	 * @param riderTrip the rider trip
	 */
	public matchingTuple(long id, double euclideanPickup, double euclideanReturn, double driverTrip,double riderTrip) {
		this.driverId=id;
		this.euclideanPickup=euclideanPickup;
		this.euclideanReturn=euclideanReturn;
		this.driverTripCost=driverTrip;
		this.RiderTripCost=riderTrip;
	}
	
	/**
	 * Sets the pickup path.
	 *
	 * @param pickupPath the new pickup path
	 */
	public void setPickupPath(ArrayList<Long> pickupPath) {
		this.pickupPath = new ArrayList<>();
		for(long id:pickupPath){
			this.pickupPath.add(id);
		}
	}
	
	/**
	 * Sets the return path.
	 *
	 * @param returnPath the new return path
	 */
	public void setReturnPath(ArrayList<Long> returnPath) {
		this.returnPath = new ArrayList<>();
		for(long id:returnPath){
			this.returnPath.add(id);
		}
	}
	
	/**
	 * Gets the pickup path.
	 *
	 * @return the pickup path
	 */
	public ArrayList<Long> getPickupPath() {
		return pickupPath;
	}
	
	/**
	 * Gets the return path.
	 *
	 * @return the return path
	 */
	public ArrayList<Long> getReturnPath() {
		return returnPath;
	}
	
	/**
	 * Gets the ridesharing cost.
	 *
	 * @return the ridesharing cost
	 */
	public double getRidesharingCost() {
		return ridesharingCost;
	}
	
	/**
	 * Sets the rider trip cost.
	 *
	 * @param riderTripCost the new rider trip cost
	 */
	public void setRiderTripCost(double riderTripCost) {
		RiderTripCost = riderTripCost;
	}
	
	/**
	 * Sets the ridesharing cost.
	 *
	 * @param ridesharingCost the new ridesharing cost
	 */
	public void setRidesharingCost(double ridesharingCost) {
		this.ridesharingCost = ridesharingCost;
	}
	
	/**
	 * Gets the driver id.
	 *
	 * @return the driver id
	 */
	public long getDriverId() {
		return driverId;
	}
	
	/**
	 * Gets the driver trip cost.
	 *
	 * @return the driver trip cost
	 */
	public double getDriverTripCost() {
		return driverTripCost;
	}
	
	/**
	 * Gets the euclidean pickup.
	 *
	 * @return the euclidean pickup
	 */
	public double getEuclideanPickup() {
		return euclideanPickup;
	}
	
	/**
	 * Gets the euclidean return.
	 *
	 * @return the euclidean return
	 */
	public double getEuclideanReturn() {
		return euclideanReturn;
	}
	
	/**
	 * Gets the pickup.
	 *
	 * @return the pickup
	 */
	public double getPickup() {
		return Pickup;
	}
	
	/**
	 * Gets the return.
	 *
	 * @return the return
	 */
	public double getReturn() {
		return Return;
	}
	
	/**
	 * Sets the driver id.
	 *
	 * @param driverId the new driver id
	 */
	public void setDriverId(long driverId) {
		this.driverId = driverId;
	}
	
	/**
	 * Sets the driver trip cost.
	 *
	 * @param driverTripCost the new driver trip cost
	 */
	public void setDriverTripCost(double driverTripCost) {
		this.driverTripCost = driverTripCost;
	}
	
	/**
	 * Sets the euclidean pickup.
	 *
	 * @param euclideanPickup the new euclidean pickup
	 */
	public void setEuclideanPickup(double euclideanPickup) {
		this.euclideanPickup = euclideanPickup;
	}
	
	/**
	 * Sets the euclidean return.
	 *
	 * @param euclideanReturn the new euclidean return
	 */
	public void setEuclideanReturn(double euclideanReturn) {
		this.euclideanReturn = euclideanReturn;
	}
	
	/**
	 * Sets the pickup.
	 *
	 * @param pickup the new pickup
	 */
	public void setPickup(double pickup) {
		Pickup = pickup;
	}
	
	/**
	 * Sets the return.
	 *
	 * @param return1 the new return
	 */
	public void setReturn(double return1) {
		Return = return1;
	}
	
	/**
	 * Compare between matching tuples based on (euclidean return - driver trip cost) 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(matchingTuple arg0) {
		
		double res1=this.euclideanReturn-driverTripCost;
		double res2=arg0.euclideanReturn-arg0.driverTripCost;
		if(res1<res2)return -1;
		if(res1>res2)return 1;
		return 0;
	}
	
	
}
