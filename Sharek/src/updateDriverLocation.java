import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sharek.objects.Driver;
import sharek.objects.Drivers;
import sharek.objects.LonLat;
import sharek.objects.Nodes;
import sharek.shortest_path.A_star;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

@WebServlet(name="updateDriverLocation",urlPatterns={"/updateDriverLocation"})
public class updateDriverLocation extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		long   id  = Integer.parseInt( request.getParameter("id") );
		String driverLonLat= ( request.getParameter("driverLocation") );
		double longitude= Double.valueOf(driverLonLat.split(",")[0]);
		double latitude= Double.valueOf(driverLonLat.split(",")[1]);
		Driver d = Drivers.getDriver(id);
		
		if(d==null){// wrong input param
			// should return failed in response;
			return;
		}
		Drivers.updateDriverShortestPath(id,new LonLat(longitude,latitude));
		JsonObject  driverJson=new JsonObject();
		
		
		driverJson.addProperty("id", id);
		driverJson.addProperty("totalDistance",A_star.distance);
		
		JsonArray shortestPathJson=new JsonArray();
		ArrayList<Long>shortestPath=d.getDriverTripshortestPath();
		for (int j = 0; j < shortestPath.size(); j++) {
			LonLat lonlat=Nodes.getNode(shortestPath.get(j)).getPoint();
			JsonPrimitive jsonLonLat= new JsonPrimitive(lonlat.lat+","+lonlat.lon);
			shortestPathJson.add(jsonLonLat);
		}
		driverJson.add("shortestPath", shortestPathJson);
		String json = new Gson().toJson(driverJson);
		response.getWriter().write(json);
	}
}
