package sharek.shortest_path;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import sharek.objects.Drivers;
import sharek.objects.Edges;
import sharek.objects.Nodes;




// TODO: Auto-generated Javadoc
/**
 * The Class A_star responsible for shortest path based on A* algorithm.
 */
public class A_star {

	
	/** The distance. */
	public static double distance;
	
	/** The shortest path. */
	public static ArrayList<Long> shortestPath=new ArrayList<Long>(); // long edge ids 
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		
		try {
			
				BufferedReader buffer1 = new BufferedReader(new FileReader("1807-Nodes.txt"));
				Nodes.createNodes(buffer1);
				BufferedReader buffer2=new BufferedReader(new FileReader("1807-Edges.txt"));
				Edges.createEdges(buffer2);
				BufferedReader buffer3=new BufferedReader(new FileReader("1807.txt"));
				Drivers.createDrivers(buffer3);
				
			
			
			long start=1122637064;
			long goal=1850556552;
			long timeStart=System.currentTimeMillis();
			System.out.println("Start Shortest Path .. ");
			runAStarShortestPath(start,goal,true);
			System.out.println("Total Time:"+(System.currentTimeMillis()-timeStart));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * run A* shortest path algorithm
	 *
	 * @param start the start
	 * @param goal the goal
	 * @param needToCalculatePath the need to calculate path
	 * @return true, if successful path is found
	 */
	public static boolean runAStarShortestPath(long start, long goal,boolean needToCalculatePath) {
		
		/*
		 * init the shortest path
		 */
		distance=0;
		shortestPath.clear();
		ArrayList<Long> closedset=new ArrayList<Long>();
		PQ openset=new PQ();
		HashMap<Long, Long> camefrom=new HashMap<Long, Long>();
		HashMap<Long, Double> g_score= new HashMap<Long, Double>();
		HashMap<Long, Double> f_score=new HashMap<Long, Double>();
		for (Long id:Nodes.getNodesId()) {
			g_score.put(id, Double.MAX_VALUE);
		}
		g_score.put(start, 0.0);
		f_score.put(start, 0.0+heuristic_cost_estimate(start,goal));

		
		openset.add(new PQElement(start,f_score.get(start)));
		long current=0;
		while(!openset.isEmpty()){
			/*
			 * get the smallest f(heuristic) value from priority queue PQ. 
			 */
			PQElement element=(PQElement) openset.peek();
			current=element.nodeId;
			
			if(current==goal){ // goal reached
				distance=f_score.get(goal);
				if(needToCalculatePath)reconstructPath(camefrom,goal,start);
				return true;
			}
			
			openset.remove();
			closedset.add(current);
			
			/*
			 * get list of neighbors to node current 
			 */
			ArrayList<Long> neighbors=Nodes.getNeighborsOfNode(current);
			for(long neighbor:neighbors){
				if(closedset.contains(neighbor))continue;
				
				/*
				 * new g score is the current score + distance between current and neighbor
				 */
				double new_g_score=g_score.get(current)+distance_between(current, neighbor);
				
				boolean neighborInOpenSet=openset.contains(neighbor);
				
				if(!neighborInOpenSet|| new_g_score < g_score.get(neighbor)){
					
					if(needToCalculatePath)camefrom.put(neighbor,current);
					
					g_score.put(neighbor, new_g_score);
					
					/*
					 * calculate the f score of new neighbor
					 * f score = new g score + heuristic cost between neighbor and goal 
					 */
					double new_f_score=new_g_score+heuristic_cost_estimate(neighbor, goal);
					f_score.put(neighbor, new_f_score);
					
					if(!neighborInOpenSet){
						
						openset.add(new PQElement(neighbor,new_f_score));
						
					}
					
				}
				
				
			}
			
		}
				
		return false;
		
		
		
	}

	/**
	 * Reconstruct path for the shortest path.
	 *
	 * @param camefrom the camefrom
	 * @param currentNode the current node
	 * @param start the start
	 */
	private static void reconstructPath(HashMap<Long, Long> camefrom, long currentNode,long start) {
		// TODO Auto-generated method stub
//		if(camefrom.containsKey(currentNode)){
//			reconstructPath(camefrom, camefrom.get(currentNode));
//			shortestPath.add(currentNode);
//		}
//		else return ;
		
		while(currentNode!=start){
			
			if(camefrom.containsKey(currentNode))shortestPath.add(currentNode);
			currentNode=camefrom.get(currentNode);
			
			
		}
		shortestPath.add(start);
		
		
		
	}

	/**
	 * Distance_between two connected nodes
	 *
	 * @param current the neighbor
	 * @param neighbor the goal
	 * @return the double distance between two connected nodes
	 */
	private static Double distance_between(long current, long neighbor) {
		// TODO Auto-generated method stub
		return Nodes.getDistancebetween(current, neighbor);
		
	}

	/**
	 * Heuristic_cost_estimate between the neighbor node and goal
	 * The Heuristic function is the euclidean distance between neighbor node and goal node.
	 *
	 * @param neighbor the neighbor node
	 * @param goal the goal node
	 * @return the double euclidean distance between neighbor and goal
	 */
	private static double heuristic_cost_estimate(long neighbor, long goal) {
		return distance_between(neighbor, goal);

	}

}
