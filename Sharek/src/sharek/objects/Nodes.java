package sharek.objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


// TODO: Auto-generated Javadoc
/**
 * The Class Nodes.
 */
public class Nodes {

	/** The nodes. */
	private static HashMap<Long, Node> nodes=new HashMap<Long, Node>();;
	
	/**
	 * Instantiates a new nodes.
	 */
	public Nodes() {
		
	}
	
	/**
	 * Creates the nodes.
	 *
	 * @param buffer the buffer
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void createNodes(BufferedReader buffer) throws IOException{
		
		
		String line=buffer.readLine();
		while(line!=null&&!line.equals("")){
			
			String[] args=line.split(",");
			
			long id=Long.valueOf(args[0]);
			double lat = Double.valueOf(args[1]);
			double lon = Double.valueOf(args[2]);
			Node node=new Node(id, new LonLat(lon, lat));
			nodes.put(id, node);
			
			line=buffer.readLine();
		}
		buffer.close();
	}
	
	/**
	 * Gets the neighbors of node.
	 *
	 * @param Nodeid the nodeid
	 * @return the neighbors of node
	 */
	public static ArrayList<Long> getNeighborsOfNode(long Nodeid){
		return Nodes.nodes.get(Nodeid).getNeigbhors();
	}
	
	/**
	 * Gets the nearest node.
	 *
	 * @param lonLat the lon lat
	 * @return the nearest node
	 */
	public static long getNearestNode(LonLat lonLat) {
		// TODO Auto-generated method stub
		Collection<Node> list=Nodes.nodes.values();
		Iterator<Node> itr=list.iterator();
		double min=Integer.MAX_VALUE;
		long minId=-1;
		while(itr.hasNext()){
			Node n=itr.next();
			double d=getDistancebetween(lonLat,n.getPoint());
			if(d<min){
				min=d;
				minId=n.getNodeId();
			}
		}
		return minId;
	}
	
	/**
	 * Gets the distancebetween.
	 *
	 * @param point1 the point1
	 * @param point2 the point2
	 * @return the distancebetween
	 */
	public static double getDistancebetween(LonLat point1, LonLat point2){
		
		double radLat1 = Math.toRadians(point1.lat);
		double radLat2 = Math.toRadians(point2.lat);
		double a = radLat1 - radLat2;
		double b = Math.toRadians(point1.lon) - Math.toRadians(point2.lon);
		double dist = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		dist = dist * Edge.EARTH_RADIUS;
		return dist;
	}
	
	/**
	 * Gets the distancebetween.
	 *
	 * @param start the start
	 * @param goal the goal
	 * @return the distancebetween
	 */
	public static double getDistancebetween(long start, long goal){
		LonLat point1=nodes.get(start).getPoint();
		LonLat point2=nodes.get(goal).getPoint();
		return getDistancebetween(point1, point2);
	}
	
	/**
	 * Gets the node.
	 *
	 * @param nodeId the node id
	 * @return the node
	 */
	public static Node getNode(long nodeId) {
		// TODO Auto-generated method stub
		return nodes.get(nodeId);
	}
	
	/**
	 * Gets the nodes id.
	 *
	 * @return the nodes id
	 */
	public static Set<Long> getNodesId() {
		// TODO Auto-generated method stub
		return nodes.keySet();
	}
}
