
import sharek.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sharek.objects.Drivers;
import sharek.objects.Edges;
import sharek.objects.LonLat;
import sharek.objects.Nodes;
import sharek.objects.Rider;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;


// TODO: Auto-generated Javadoc
/**
 * Sharek Server for Sharek algorithm. 
 * 'getDrivers' url to get the drivers for a given Rider.
 * 
 */
@WebServlet(name="sharek",urlPatterns={"/getDrivers"})

public class sharek extends HttpServlet {
	       
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new sharek.
	 *
	 * @see HttpServlet#HttpServlet()
	 */
    public sharek() {
    	
        super();
       
        // TODO Auto-generated constructor stub
    }
    
    /**  Initialization of server.
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init() throws ServletException {
    	 try {
    		/*
    		 *  read the nodes and edges file for a given ride session
    		 *  For the demo, we only need Mecca data. 
    		 */
     		String directory = "E:/Work/Umm ElQura/workspace/Sharek/data/";
     		BufferedReader buffer1 = new BufferedReader(new FileReader(directory+"1807-Nodes.txt"));
 			Nodes.createNodes(buffer1);
 			
 			BufferedReader buffer2=new BufferedReader(new FileReader(directory+"1807-Edges.txt"));
 			Edges.createEdges(buffer2);
 			System.out.println("finished reading nodes and edges");
 			
 			
 			/*
 			 *  Drivers simulation.
 			 *  we have a static drivers (traffic roads) from osm extractor to simulate the drivers
 			 */
 			BufferedReader buffer3=new BufferedReader(new FileReader(directory+"1807.txt"));
 			Drivers.createDrivers(buffer3);
 			System.out.println("finished reading drivers");
 		
     	} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			System.out.println(e.toString());
 		}
		
    }

	/**
	 * Get Request for sharek session. User sends a request to get the matching skyline drivers based on Sharek algorithm. 
	 *
	 * @param request the params of Rider.
	 * @param response the output skyline drivers in Json. 
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 *  input params
		 *  @param id rider id.
		 *  @param startlonlat the source point of rider
		 *  @param endlonlat the destination point of rider
		 *  @param max_price the max price rider could pay
		 *  @param max_time the max time rider could wait
		 */
		long   id  = Integer.parseInt( request.getParameter("id") );
		String startlonlat = ( request.getParameter("startlonlat") );
		String endlonlat = ( request.getParameter("endlonlat") );
		double max_price = Float.parseFloat( request.getParameter("max_price") );
		double max_time = Float.parseFloat( request.getParameter("max_time") );
		
		
		/*
		 * parsing data and create rider object.
		 * 
		 */
		System.out.println("start parsing data");
		long riderId=id;
		double startlon=Double.valueOf(startlonlat.split(",")[0]);
		double startlat=Double.valueOf(startlonlat.split(",")[1]);
		double endlon=Double.valueOf(endlonlat.split(",")[0]);
		double endlat=Double.valueOf(endlonlat.split(",")[1]);
		LonLat start=new LonLat(startlon, startlat);
		LonLat end=new LonLat(endlon, endlat); // nearest to driver 2
		Rider rider=new Rider(riderId,start,end,max_time,max_price);
		
		
		
		
		/*
		 * Sharek algorithm to get the matching skyline drivers.
		 * @param rider the rider sent by user
		 * @return list of matchingTuple the set of candidate drivers. 
		 */
		ArrayList<matchingTuple>drivers=sharekAlgorithm.Sharek(rider);
		
		
		/*
		 * parse the matching table into Json object and return to response.
		 * Response returns a Json object contains:
		 * @return riderTrip shortest path for rider from source to destination. 
		 * @return array of candidate drivers each contains: driver id, pickup and return cost with shortest path.
		 * 
		 */
		JsonObject result=new JsonObject();
		JsonArray arrayDrivers=new JsonArray();
		long driver_id=0;;
		double pickup,Return,ridesharingCost;
		ArrayList<Long>riderPath=rider.getRiderPath();
		JsonArray riderPathJson=new JsonArray();
//		System.out.println("rider path size "+riderPath.size());
		for (int j = 0; j < riderPath.size(); j++) {
			LonLat lonlat=Nodes.getNode(riderPath.get(j)).getPoint();
			JsonPrimitive jsonLonLat= new JsonPrimitive(lonlat.lat+","+lonlat.lon);
			riderPathJson.add(jsonLonLat);
		}
		result.add("riderTrip", riderPathJson);
		
		for (int i = 0; i < drivers.size(); i++) {
			driver_id=drivers.get(i).getDriverId();
			pickup=drivers.get(i).getPickup();
			Return=drivers.get(i).getReturn();
			ridesharingCost=drivers.get(i).getRidesharingCost();
			JsonObject  driver=new JsonObject();
			driver.addProperty("id", driver_id);
			driver.addProperty("pickup", pickup);
			driver.addProperty("return",Return);
			driver.addProperty("ridesharingcost ",ridesharingCost);
			
			ArrayList<Long>pickupPath=drivers.get(i).getPickupPath();
			JsonArray pickupPathJson=new JsonArray();
//			System.out.println("Pickup path size "+pickupPath.size());
			for (int j = 0; j < pickupPath.size(); j++) {
				LonLat lonlat=Nodes.getNode(pickupPath.get(j)).getPoint();
				JsonPrimitive jsonLonLat= new JsonPrimitive(lonlat.lat+","+lonlat.lon);
				pickupPathJson.add(jsonLonLat);
			}
			driver.add("pickupPath", pickupPathJson);
			
			ArrayList<Long>returnPath=drivers.get(i).getReturnPath();
			JsonArray returnPathJson=new JsonArray();
//			System.out.println("rider path size "+returnPath.size());
			for (int j = 0; j < returnPath.size(); j++) {
				LonLat lonlat=Nodes.getNode(returnPath.get(j)).getPoint();
				JsonPrimitive jsonLonLat= new JsonPrimitive(lonlat.lat+","+lonlat.lon);
				returnPathJson.add(jsonLonLat);
			}
			driver.add("ReturnPath", returnPathJson);
			
			arrayDrivers.add(driver);
		}
		
		
		result.add("drivers", arrayDrivers);
		String json = new Gson().toJson(result);
		response.getWriter().write(json);
	
				
		
		
	}

	/**
	 * Do post.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 //get request parameters for userID and password
        
             
        
         
	}
	
	
}
