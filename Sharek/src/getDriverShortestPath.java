import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import sharek.objects.Driver;
import sharek.objects.Drivers;
import sharek.objects.LonLat;
import sharek.objects.Nodes;
import sharek.shortest_path.A_star;

/**
 * get Driver ShortestPath 
 * 
 * 
 */
@WebServlet(name="getDriverShortestPath",urlPatterns={"/getDriverShortestPath"})
public class getDriverShortestPath extends HttpServlet {

	
	
	private static final long serialVersionUID = 1L;
	
	public getDriverShortestPath() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		/*
		 *  input params
		 *  @param id rider id.
		 *  @param startlonlat the source point of rider
		 *  @param endlonlat the destination point of rider
		 */
		long   id  = Integer.parseInt( request.getParameter("id") );
		String startlonlat = ( request.getParameter("startlonlat") );
		String endlonlat = ( request.getParameter("endlonlat") );
		Driver driver=Drivers.getDriver(id);
		
		if(driver==null){ // new driver
			Double startlongitude=Double.valueOf(startlonlat.split(",")[0]);
			Double startlatitude=Double.valueOf(startlonlat.split(",")[1]);
			Double endlongitude=Double.valueOf(endlonlat.split(",")[0]);
			Double endlatitude=Double.valueOf(endlonlat.split(",")[1]);
			driver=new Driver(id,new  LonLat(startlongitude, startlatitude),new LonLat(endlongitude, endlatitude));
			
			
			A_star.runAStarShortestPath(driver.getStartNode(), driver.getEndNode(), true);
			double distance = A_star.distance;
			ArrayList<Long>shortestPath=A_star.shortestPath;
			
			Drivers.addDriver(driver);
			
			
			JsonObject  driverJson=new JsonObject();
			
			
			driverJson.addProperty("id", driver.getDriverId());
			driverJson.addProperty("totalDistance",distance);
			
			JsonArray shortestPathJson=new JsonArray();
			
			for (int j = 0; j < shortestPath.size(); j++) {
				LonLat lonlat=Nodes.getNode(shortestPath.get(j)).getPoint();
				JsonPrimitive jsonLonLat= new JsonPrimitive(lonlat.lat+","+lonlat.lon);
				shortestPathJson.add(jsonLonLat);
			}
			driverJson.add("shortestPath", shortestPathJson);
			String json = new Gson().toJson(driverJson);
			resp.getWriter().write(json);
		}
		else{ // update previous shortest path
			
		}
		
	}
}
