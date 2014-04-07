package sharek.objects;

import java.util.ArrayList;


// TODO: Auto-generated Javadoc
/**
 * The Class Node.
 */
public class Node {
	
	/** The node id. */
	private long nodeId;
	
	/** The point. */
	private LonLat point;
	
	/** The neigbhors. */
	private ArrayList<Long> neigbhors;
	
	/**
	 * Instantiates a new node.
	 *
	 * @param id the id
	 * @param point the point
	 */
	public Node(long id, LonLat point) {
		this.nodeId=id;
		this.point=new LonLat(point.lon, point.lat);
		neigbhors=new ArrayList<Long>();
	}
	
	/**
	 * Gets the neigbhors.
	 *
	 * @return the neigbhors
	 */
	public ArrayList<Long> getNeigbhors() {
		return neigbhors;
	}
	
	/**
	 * Gets the node id.
	 *
	 * @return the node id
	 */
	public long getNodeId() {
		return nodeId;
	}
	
	/**
	 * Gets the point.
	 *
	 * @return the point
	 */
	public LonLat getPoint() {
		return point;
	}

	/**
	 * Sets the node id.
	 *
	 * @param nodeId the new node id
	 */
	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}
	
	/**
	 * Sets the point.
	 *
	 * @param point the new point
	 */
	public void setPoint(LonLat point) {
		this.point = point;
	}

	/**
	 * Adds the neigbhor.
	 *
	 * @param nodeB the node b
	 */
	public void addNeigbhor(long nodeB) {
		// TODO Auto-generated method stub
		neigbhors.add(nodeB);
	}
}
