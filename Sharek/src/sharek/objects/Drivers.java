package sharek.objects;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import sharek.shortest_path.A_star;



// TODO: Auto-generated Javadoc
/**
 * The Class Drivers.
 */
public class Drivers {

	/** The drivers. */
	private static HashMap<Long, Driver> drivers=new HashMap<Long, Driver>();
	
	/**
	 * Creates the drivers.
	 *
	 * @param buffer the buffer
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void createDrivers(BufferedReader buffer) throws IOException {
		// TODO Auto-generated method stub
		buffer.readLine();buffer.readLine();
		
		String line=buffer.readLine();
		while(line!=null&&!line.equals("")){
			
			String[] args=line.split(" ");
			long id=Long.valueOf(args[0]);
			double lat=Double.valueOf(args[3]);
			double lon=Double.valueOf(args[4]);
			
			if(drivers.containsKey(id)){ // updat driver
				Driver driver=drivers.get(id);
				driver.addNewLocation(new LonLat(lon, lat));
				drivers.put(id, driver);
			}
			else{
				
				Driver driver=new Driver(id,new LonLat(lon, lat));
				drivers.put(id, driver);
			}
			
			
			line=buffer.readLine();
		}
		Collection<Driver> drivers=Drivers.drivers.values();
		Iterator<Driver> itr=drivers.iterator();
		while(itr.hasNext()){
			Driver d=itr.next();
			if(d.driverLocations.size()==0)continue;
			d.endNode=Nodes.getNearestNode(d.endLonLat);
			
			A_star.runAStarShortestPath(d.startNode, d.endNode,true);
			d.driverTrip=A_star.distance;
			d.setDriverTripshortestPath(A_star.shortestPath);
		}
		buffer.close();
		
	}
	
	/**
	 * Gets the driver.
	 *
	 * @param driverId the driver id
	 * @return the driver
	 */
	public static Driver getDriver(long driverId) {
		// TODO Auto-generated method stub
		return drivers.get(driverId);
	}
	
	/**
	 * Gets the drivers.
	 *
	 * @return the drivers
	 */
	public static Collection<Driver> getDrivers() {
		// TODO Auto-generated method stub
		return drivers.values();
	}

	public static int size() {
		// TODO Auto-generated method stub
		return drivers.size();
	}

	public static void addDriver(Driver driver) {
		// TODO Auto-generated method stub
		drivers.put(driver.getDriverId(), driver);
		
	}

	public static void updateDriverShortestPath(long id, LonLat driverLoc) {
		// TODO Auto-generated method stub
		Driver driver=drivers.get(id);
		A_star.distance=driver.driverTrip;
		ArrayList<Long>shortestPath=driver.getDriverTripshortestPath();
		while(shortestPath.size()>2){
			Long n1=shortestPath.get(shortestPath.size()-1);
			Long n2=shortestPath.get(shortestPath.size()-2);
			Long n3=shortestPath.get(shortestPath.size()-3);
			double d1=Nodes.getDistancebetween(driverLoc, Nodes.getNode(n1).getPoint());
			double d2=Nodes.getDistancebetween(driverLoc, Nodes.getNode(n2).getPoint());
			double d3=Nodes.getDistancebetween(driverLoc, Nodes.getNode(n3).getPoint());
			double d12=Nodes.getDistancebetween(Nodes.getNode(n1).getPoint(), Nodes.getNode(n2).getPoint());
			double d23=Nodes.getDistancebetween(Nodes.getNode(n2).getPoint(), Nodes.getNode(n3).getPoint());
			double e12=d1+d2-d12;
			double e23=d2+d3-d23;
			if(e12<e23){
				break;
			}
			else{
				shortestPath.remove(shortestPath.size()-1);
				A_star.distance-=d12;
			}
			driver.setDriverTripshortestPath(shortestPath);
			
		}
		
	}

}
