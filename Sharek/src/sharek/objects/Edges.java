package sharek.objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;


// TODO: Auto-generated Javadoc
/**
 * The Class Edges.
 */
public class Edges {
	
	/** The edges. */
	private static HashMap<Long, Edge> edges=new HashMap<Long, Edge>();
	
	/**
	 * Instantiates a new edges.
	 */
	public Edges() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Creates the edges.
	 *
	 * @param buffer the buffer
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void createEdges(BufferedReader buffer) throws IOException{
		
		
		String line=buffer.readLine();
		while(line!=null&&!line.equals("")){
			
			String[] args=line.split(",");
			
			long id=Long.valueOf(args[0]);
			long nodeA = Long.valueOf(args[1]);
			long nodeB = Long.valueOf(args[2]);
			Edge edge=new Edge(id, nodeA, nodeB);
			edges.put(id, edge);
			Nodes.getNode(nodeA).addNeigbhor(nodeB);
			String onewayInfo=args[3];
			if(!onewayInfo.contains("{\"oneway\"=\"yes\"}")){
				Nodes.getNode(nodeB).addNeigbhor(nodeA);
			}
			
			
			line=buffer.readLine();
		}
		buffer.close();
		
	}

}
