package sharek.shortest_path;




// TODO: Auto-generated Javadoc
/**
 * The Class PQElement.
 */
public class PQElement{
	
	/**
	 * Instantiates a new PQ element.
	 *
	 * @param id the node id
	 * @param value the heuristic cost of give node
	 */
	public PQElement(long id,double value) {
		this.nodeId=id;
		this.heuristicCost=value;
	}
    	
	    /** The id. */
	    long nodeId;
    	
	    /** The value. */
	    double heuristicCost;
		
		/**
		 * Compare to.
		 *
		 * @param o2 the o2
		 * @return the int
		 */
		public int compareTo(Object o2) {
			// TODO Auto-generated method stub
			if(heuristicCost<((PQElement)o2).heuristicCost)return -1;
			if(heuristicCost>((PQElement)o2).heuristicCost)return 1;
			return 0;
		}
		  
  		/* (non-Javadoc)
  		 * @see java.lang.Object#equals(java.lang.Object)
  		 */
  		@Override
		    public boolean equals(Object o) {
		    	// TODO Auto-generated method stub
		    	boolean res=false;
		    	if(o instanceof PQElement){
		    		PQElement obj=(PQElement)o;
		    		res=obj.nodeId==this.nodeId;
		    	}
		    	
		    	return res;
		    }
		  
  		/* (non-Javadoc)
  		 * @see java.lang.Object#hashCode()
  		 */
  		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			  int hash = 7;
		        hash = (int) (17 * hash + (this.nodeId));
		        return hash;
		}
    }