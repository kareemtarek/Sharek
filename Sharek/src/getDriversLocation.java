import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sharek.objects.Driver;
import sharek.objects.Drivers;
import sharek.objects.Edges;
import sharek.objects.LonLat;
import sharek.objects.Nodes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;


/**
 * Sharek Server for Sharek algorithm. 
 * 'getDrivers' url to get the drivers for a given Rider.
 * 
 */
@WebServlet(name="getDriversLocation",urlPatterns={"/getDriversLocation"})
public class getDriversLocation extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  
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
	 * doGet for drivers in system. 
	 *
	 * 
	 * @param response the output drivers in system  
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JsonObject result=new JsonObject();
		JsonArray arrayDrivers=new JsonArray();
		
		Iterator<Driver> driverItr=Drivers.getDrivers().iterator();
		for (;driverItr.hasNext();) {
			Driver d=driverItr.next();
			if(d==null)continue;
			JsonObject  driver=new JsonObject();
			
			double driver_id=d.getDriverId();
			driver.addProperty("id", driver_id);
			
			ArrayList<Long>shortestPath=d.getDriverTripshortestPath();
			JsonArray shortestPathJson=new JsonArray();
			
			for (int j = 0; j < shortestPath.size(); j++) {
				LonLat lonlat=Nodes.getNode(shortestPath.get(j)).getPoint();
				JsonPrimitive jsonLonLat= new JsonPrimitive(lonlat.lat+","+lonlat.lon);
				shortestPathJson.add(jsonLonLat);
			}
			driver.addProperty("startposition", d.getStartLonLat().lat+","+ d.getStartLonLat().lon);
			driver.add("shortestPath", shortestPathJson);
			
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
