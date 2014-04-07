package sharek.objects;


// TODO: Auto-generated Javadoc
/**
 * The Class Edge.
 */
public class Edge {
	
	/** The earth radius. */
	static double EARTH_RADIUS = 6378.137;// the radius of the earth
	
	/** The edge id. */
	private long edgeId;
	
	/** The node a_ id. */
	private long nodeA_Id;
	
	/** The node b_ id. */
	private long nodeB_Id;
	
	/** The distance. */
	private double distance;
	
	/**
	 * Instantiates a new edge.
	 *
	 * @param edgeId the edge id
	 * @param nodeA_id the node a_id
	 * @param nodeB_Id the node b_ id
	 */
	public Edge(long edgeId, long nodeA_id, long nodeB_Id) {
		this.edgeId=edgeId;
		this.nodeA_Id=nodeA_id;
		this.nodeB_Id=nodeB_Id;
		this.distance= Nodes.getDistancebetween(nodeA_id, nodeB_Id);
		
	}

	
	
	/**
	 * Gets the distance.
	 *
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}
	
	/**
	 * Gets the edge id.
	 *
	 * @return the edge id
	 */
	public long getEdgeId() {
		return edgeId;
	}
	
	/**
	 * Gets the node a_ id.
	 *
	 * @return the node a_ id
	 */
	public long getNodeA_Id() {
		return nodeA_Id;
	}
	
	/**
	 * Gets the node b_ id.
	 *
	 * @return the node b_ id
	 */
	public long getNodeB_Id() {
		return nodeB_Id;
	}
	
	/**
	 * Sets the edge id.
	 *
	 * @param edgeId the new edge id
	 */
	public void setEdgeId(long edgeId) {
		this.edgeId = edgeId;
	}
	
	/**
	 * Sets the node a_ id.
	 *
	 * @param nodeA_Id the new node a_ id
	 */
	public void setNodeA_Id(long nodeA_Id) {
		this.nodeA_Id = nodeA_Id;
	}
	
	/**
	 * Sets the node b_ id.
	 *
	 * @param nodeB_Id the new node b_ id
	 */
	public void setNodeB_Id(long nodeB_Id) {
		this.nodeB_Id = nodeB_Id;
	}
	
}
