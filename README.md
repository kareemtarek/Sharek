SHAREK Introduction
======

Sharek is a scalable dynamic ridesharing system. sharek aiming to address a more general dynamic ridesharing matching problem: no restriction is imposed on the drivers beforehand and the rider requests the ridesharing service with only two requirements of maximum waiting time for being picked up and maximum price for sharing the road.

Sharek adopts a step-by-step strategy to ﬁlter out drivers and achieve efﬁciency and scalability in a way that:

(a) minimizes the shortestpath search operations as many as possible.

(b) return skyline values, in terms of waiting time and ridesharing cost as the ﬁnal results, without introducing overhead to the system.

More info about sharek [here](https://docs.google.com/file/d/0B7d_M5aFWs3HVGNvLUZsSUI0NjQ/).

Sharek core is implemented using A* algorithm for searching the shortest path as a key performance to reduce the complexity time to heuristically O ( N Log (|E|) ) where N is the number of drivers in system and |E| is number of edges connecting nodes.



SHAREK Components
======
* A* Algorithm to search for the shortest path between two nodes. 
* MatchingTable: This table is specified for SHAREK query processing which matches one rider against multiple drivers.
* MatchingTuple: The Tuple of matching table. Each contains the information for each driver session.
* Objects:
    1. Drivers.
    2. Rider.
    3. Nodes.
    4. Edges.


SHAREK Server side
======
* Sharek Httpservlet to run Sharek Algorithm to get drivers for a ride session.
* getDriverShortestPath Httpservlet to run A* shortest path algorithm for driver.
* getDriversLocation Httpservlet to simulate the drivers locations in the demo.
* updateDriverLocation Httpservlet to update the server side driver's location.

Please Assume That:
* Assume unit of max price equals to number of Km covered e.g. (Price= 50$ means 1$ for every 1 Km)
* Assume unit of max waiting time is seconds. (Default velocity of cars 60 Km/Sec)
* User sends a request to server and the response is a JSON result for:
  1. Rider shortest path between start and goal.
  2. Array of drivers which contains:
  3. Driver id
  4. Pickup distance with array list of nodes for shortest path between driver start location and rider start location.
  5. Return distance with array list of nodes for shortest path between rider end location and driver end location.
  6. Ridesharing cost.
