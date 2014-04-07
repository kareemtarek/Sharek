package sharek.objects;


import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Driver.
 */
public class Driver {

	/** The driver id. */
	long driverId;
	
	/** The start lon lat. */
	LonLat startLonLat;
	
	/** The end lon lat. */
	LonLat endLonLat;
	
	/** The start node. */
	long startNode;
	
	/** The end node. */
	long endNode;
	
	/** The velocity. */
	double velocity;
	
	/** The driver trip. */
	double driverTrip;
	
	/** The driver locations. */
	ArrayList<LonLat> driverLocations; // used only for simulations drivers
	
	ArrayList<Long> DriverTripshortestPath;
	/**
	 * Instantiates a new driver.
	 *
	 * @param id the id
	 * @param start the start
	 */
	public Driver(long id, LonLat start) {
		this.driverId=id;
		this.startLonLat=start;
		startNode=Nodes.getNearestNode(start);
		driverLocations=new ArrayList<LonLat>();
		velocity=60;// static on 60 now.
		
	}
	
	/**
	 * Instantiates a new driver.
	 *
	 * @param id the id
	 * @param start the source of driver
	 * @param end the destination of driver
	 */
	public Driver(long id, LonLat start, LonLat end) {
		this.driverId=id;
		this.startLonLat=start;
		startNode=Nodes.getNearestNode(start);
		endNode=Nodes.getNearestNode(end);
		driverLocations=new ArrayList<LonLat>();
		velocity=60;// static on 60 now.
		
	}
	
	public void setDriverTripshortestPath(ArrayList<Long> driverTripshortestPath) {
		DriverTripshortestPath = new ArrayList<Long>();
		for(long id:driverTripshortestPath){
			DriverTripshortestPath.add(id);
		}
	}
	public ArrayList<Long> getDriverTripshortestPath() {
		return DriverTripshortestPath;
	}
	/**
	 * Adds the new location.
	 *
	 * @param location the location
	 */
	public void addNewLocation(LonLat location){
		driverLocations.add(location);
		endLonLat=location;
		
	}
	
	/**
	 * Gets the drivers locations size.
	 *
	 * @return the drivers locations size
	 */
	public int getDriversLocationsSize(){
		return driverLocations.size();
	}
	
	/**
	 * Gets the location.
	 *
	 * @param index the index
	 * @return the location
	 */
	public LonLat getLocation(int index){
		return driverLocations.get(index);
	}
	
	/**
	 * Gets the start lon lat.
	 *
	 * @return the start lon lat
	 */
	public LonLat getStartLonLat() {
		return startLonLat;
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
	 * Gets the velocity.
	 *
	 * @return the velocity
	 */
	public double getVelocity() {
		return velocity;
	}
	
	/**
	 * Sets the velocity.
	 *
	 * @param velocity the new velocity
	 */
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	
	/**
	 * Gets the driver trip.
	 *
	 * @return the driver trip
	 */
	public double getDriverTrip() {
		return driverTrip;
	}
	
	/**
	 * Sets the driver trip.
	 *
	 * @param driverTrip the new driver trip
	 */
	public void setDriverTrip(double driverTrip) {
		this.driverTrip = driverTrip;
	}
	
	/**
	 * Gets the start node.
	 *
	 * @return the start node
	 */
	public long getStartNode() {
		return startNode;
	}
	
	/**
	 * Gets the end node.
	 *
	 * @return the end node
	 */
	public long getEndNode() {
		return endNode;
	}
	
	/**
	 * Gets the end lon lat.
	 *
	 * @return the end lon lat
	 */
	public LonLat getEndLonLat() {
		return endLonLat;
	}
	
}
