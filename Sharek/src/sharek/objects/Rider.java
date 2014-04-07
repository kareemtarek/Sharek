package sharek.objects;

import java.util.ArrayList;

import sharek.shortest_path.A_star;


// TODO: Auto-generated Javadoc
/**
 * The Class Rider.
 */
public class Rider {

	/** The rider id. */
	long riderId;
	
	/** The start lon lat. */
	LonLat startLonLat;
	
	/** The end lon lat. */
	LonLat endLonLat;
	
	/** The start node. */
	long startNode;
	
	/** The end node. */
	long endNode;
	
	/** The max_time. */
	double max_time;
	
	/** The max_price. */
	double max_price;
	
	/** The rider trip. */
	double riderTrip;
	
	/** The rider path. */
	ArrayList<Long> riderPath;
	
	/**
	 * Instantiates a new rider.
	 *
	 * @param id the id
	 * @param start the start
	 * @param end the end
	 * @param max_time the max_time
	 * @param max_price the max_price
	 */
	public Rider(long id, LonLat start, LonLat end, double max_time, double max_price){
		this.riderId=id;
		this.startLonLat=start;
		this.endLonLat=end;
		this.max_price=max_price;
		this.max_time=max_time;
		this.startNode=Nodes.getNearestNode(start);
		this.endNode=Nodes.getNearestNode(end);
		A_star.runAStarShortestPath(startNode, endNode,true);
		riderTrip=A_star.distance;
		riderPath=new ArrayList<>();
		for (long nodeId:A_star.shortestPath) {
			riderPath.add(nodeId);
		}
	}
	
	/**
	 * Gets the rider path.
	 *
	 * @return the rider path
	 */
	public ArrayList<Long> getRiderPath() {
		return riderPath;
	}
	
	/**
	 * Gets the rider trip.
	 *
	 * @return the rider trip
	 */
	public double getRiderTrip() {
		return riderTrip;
	}
	
	/**
	 * Sets the rider trip.
	 *
	 * @param riderTrip the new rider trip
	 */
	public void setRiderTrip(double riderTrip) {
		this.riderTrip = riderTrip;
	}
	
	/**
	 * Gets the end lon lat.
	 *
	 * @return the end lon lat
	 */
	public LonLat getEndLonLat() {
		return endLonLat;
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
	 * Gets the max_price.
	 *
	 * @return the max_price
	 */
	public double getMax_price() {
		return max_price;
	}
	
	/**
	 * Gets the max_time.
	 *
	 * @return the max_time
	 */
	public double getMax_time() {
		return max_time;
	}
	
	/**
	 * Gets the rider id.
	 *
	 * @return the rider id
	 */
	public long getRiderId() {
		return riderId;
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
	 * Gets the start node.
	 *
	 * @return the start node
	 */
	public long getStartNode() {
		return startNode;
	}
	
	/**
	 * Sets the end lon lat.
	 *
	 * @param endLonLat the new end lon lat
	 */
	public void setEndLonLat(LonLat endLonLat) {
		this.endLonLat = endLonLat;
	}
	
	/**
	 * Sets the end node.
	 *
	 * @param endNode the new end node
	 */
	public void setEndNode(long endNode) {
		this.endNode = endNode;
	}
	
	/**
	 * Sets the max_price.
	 *
	 * @param max_price the new max_price
	 */
	public void setMax_price(double max_price) {
		this.max_price = max_price;
	}
	
	/**
	 * Sets the max_time.
	 *
	 * @param max_time the new max_time
	 */
	public void setMax_time(double max_time) {
		this.max_time = max_time;
	}
	
	/**
	 * Sets the rider id.
	 *
	 * @param riderId the new rider id
	 */
	public void setRiderId(long riderId) {
		this.riderId = riderId;
	}
	
	/**
	 * Sets the start node.
	 *
	 * @param startNode the new start node
	 */
	public void setStartNode(long startNode) {
		this.startNode = startNode;
	}
	
	/**
	 * Sets the start lon lat.
	 *
	 * @param startLonLat the new start lon lat
	 */
	public void setStartLonLat(LonLat startLonLat) {
		this.startLonLat = startLonLat;
	}
	

}
