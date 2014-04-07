package sharek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import sharek.objects.Driver;
import sharek.objects.Drivers;
import sharek.objects.Nodes;
import sharek.objects.Rider;
import sharek.shortest_path.A_star;


/**
 * The entry point of sharekAlgorithm.  
 */
public class sharekAlgorithm {

	/**
	 * sharek method to get the candidate skyline drivers for a given rider.
	 *
	 * @param rider the user that request a ride 
	 * @return the array list of matchingTuple for each candidate driver
	 */
	public static ArrayList<matchingTuple> Sharek(Rider rider) {
		
		// initialize the matching table.
		matchingTable.init();
		
		System.out.println("Start Euclidean Distance Pruning  .. ");
		double t1=System.currentTimeMillis();
		
		/*
		 * call Euclidean distance pruning methods (phase one) 
		 */
		eucldieanDistancePruning(rider);
		
		System.out.println("Finished after "+(System.currentTimeMillis()-t1));
		
		System.out.println("start Semi Euclidean Distance Pruning .. ");
		t1=System.currentTimeMillis();
		
		/*
		 * call Semi Euclidean distance pruning methods (phase two) 
		 */
		ArrayList<matchingTuple>SkylineDrivers=semiEuclideanDistancePruning(rider);
		
		System.out.println("Finished after "+(System.currentTimeMillis()-t1));
		
		
		/*
		 * some logs for testing
		 */
		System.out.println("Rider Trip Cost "+rider.getRiderTrip());
		for (matchingTuple tuple:SkylineDrivers) {
			System.out.print("Driver "+tuple.getDriverId());
			System.out.print("\tPickup "+Math.round(tuple.getPickup()));
			System.out.print("\tReturn "+Math.round(tuple.getReturn()));
			System.out.print("\tDriver trip "+Math.round(tuple.getDriverTripCost()));
			System.out.println("\tRidesharing cost "+Math.round(tuple.getRidesharingCost()));
		}
		System.out.println("Algorithm finished");
		
		return SkylineDrivers;
	}

	/**
	 * Semi Euclidean distance pruning.
	 *
	 * @param rider the user that request a ride
	 * @return the array list of candidate drivers.
	 */
	private static ArrayList<matchingTuple> semiEuclideanDistancePruning(Rider rider){
		 
		ArrayList<matchingTuple> skylineDrivers=new ArrayList<matchingTuple>();
		
		/*
		 * sort the matching table based on (euclidean return - driver trip)
		 */
		matchingTable.sortMatchingTable();
		
		double MAX= rider.getMax_price();

		// for every matchingTuple in matchingTable.
		while(!matchingTable.isEmpty()){
			
			/*
			 * get the nearest neighbor of the rider (based on euclideanPickup)
			 */
			matchingTuple tuple=matchingTable.nearetDriverEuclideanPickup(); // nearest NN average case (O(1))
			
			/*
			 * calculate the shortest Path of pickup.
			 */
			boolean success=A_star.runAStarShortestPath(Drivers.getDriver(tuple.driverId).getStartNode(), rider.getStartNode(), true);
			if(!success){
				matchingTable.removeTuple();
				continue;
			}
			double pickup=A_star.distance; // shortest pathh O(LogH*(N))
			tuple.setPickupPath(A_star.shortestPath);
			
			double max_Time_Cost=((double)rider.getMax_time()*(Drivers.getDriver(tuple.driverId).getVelocity()/1000));// velocity in KM/Sec;
			
			if(pickup > max_Time_Cost){
				 break; // terminate the program.
			}
			
			if ( pickup + 2*rider.getRiderTrip() + tuple.euclideanReturn - tuple.driverTripCost 
					> MAX){ // remove d and the drivers bellow from matching table. 
				matchingTable.removeTupleAndAllBelow();
			}
			
			else{
				success=A_star.runAStarShortestPath(rider.getEndNode(), Drivers.getDriver(tuple.driverId).getEndNode(), true);
				if(!success){
					matchingTable.removeTuple();
					continue;
				}
				
				/*
				 * calculate the shortest Path of return.
				 */
				double Return = A_star.distance; 
				tuple.setReturnPath(A_star.shortestPath);
				
				if( pickup + 2*rider.getRiderTrip() + Return - tuple.driverTripCost 
					> MAX){  // failed for only this matching tuple
					matchingTable.removeTuple();
				}
				else{ // success skyline driver. 
					MAX =  pickup + 2*rider.getRiderTrip() + Return - tuple.driverTripCost ;
					tuple.setPickup(pickup);
					tuple.setReturn(Return);
					tuple.setRidesharingCost(MAX);
					skylineDrivers.add(tuple);
					matchingTable.removeTuple();
				}
				
				
				
			}
			
		}
		
		return skylineDrivers;
	}
	
	/**
	 * Eucldiean distance pruning.
	 *
	 * @param rider the user that request a ride
	 */
	private static void eucldieanDistancePruning(Rider rider) {
		
		Collection<Driver> drivers=Drivers.getDrivers();
		Iterator<Driver> itr=drivers.iterator();
		
		// iterate on each driver
		while(itr.hasNext()){
			Driver driver=itr.next();
			
			/*
			 * calculate the max time cost the rider could wait
			 * Distance  =  Time(given) * velocity of Driver
			 * Velocity of Driver is always 60Km/Sec for the demo purpose.
			 */
			double max_Time_Cost=((double)rider.getMax_time()*driver.getVelocity()/1000);// velocity in KM/Sec;
			
			/*
			 * calculate distance between driver start points and rider start point.
			 */
			double driver_time_reach_rider= Nodes.getDistancebetween(rider.getStartLonLat(), driver.getStartLonLat());
			
			/*
			 *  Range Query for driver. Driver must be inside a circle query of radius = max_time_cost to arrive on time. 
			 *  Drive time to reach <= max waiting time of rider. 
			 *  Temporal Pruning.
			 */
			if(driver_time_reach_rider<max_Time_Cost){
				
				/*
				 * calcuate euclidean distance to pickup the driver and euclidean distance to return to driver destination.
				 */
				double euclideanPickup,euclideanReturn;
				euclideanPickup=Nodes.getDistancebetween(driver.getStartLonLat(), rider.getStartLonLat());
				euclideanReturn=Nodes.getDistancebetween(rider.getEndLonLat(), driver.getEndLonLat());
				
				/*
				 * Cost Pruning.
				 * According to Sharek Algorithm (phase one), cost pruning inequality is applied to
				 * check which driver is candidate based on max price rider could pay.
				 */
				if(rider.getMax_price()+driver.getDriverTrip()-2*rider.getRiderTrip()
						> euclideanPickup+euclideanReturn){ 
					
					/*
					 * Create a matchingTuple with driver info.
					 * add the candidate driver (matchingTuple) to the matching table. 
					 */
					matchingTuple tuple=new matchingTuple(driver.getDriverId(), euclideanPickup, euclideanReturn, driver.getDriverTrip(),rider.getRiderTrip());
					matchingTable.addMatchingTuple(tuple);
				}
				
			}
		}
	}
	

}
