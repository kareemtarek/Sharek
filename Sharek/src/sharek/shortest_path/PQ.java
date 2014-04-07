package sharek.shortest_path;



import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;



// TODO: Auto-generated Javadoc
/**
 * This class implements a priority queue
 */
public class PQ extends AbstractCollection
{
    
    /**
     * The Class DefaultComparator.
     */
    private static class DefaultComparator implements Comparator
    {
        
        /* (non-Javadoc)
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare (Object o1, Object o2)
        {
            return ((PQElement) o1).compareTo(o2);
        }
    }
    
    /** The my comp. */
    private Comparator myComp = new DefaultComparator();
    
    /** The my size. */
    private int        mySize;
    
    /** The my list. */
    private ArrayList<PQElement>  myList;
    
    /** The my ids. */
    private HashSet<Long>  myIds;

    
    /**
     * This is a trivial iterator class that returns
     * elements in the PriorityQueue ArrayList field
     * one-at-a-time.
     */
    private class PQItr implements Iterator
    {
        
        /* (non-Javadoc)
         * @see java.util.Iterator#next()
         */
        public Object next()
        {
            return myList.get(myCursor);
        }
        
        /* (non-Javadoc)
         * @see java.util.Iterator#hasNext()
         */
        public boolean hasNext()
        {
            return myCursor <= mySize;
        }

        /* (non-Javadoc)
         * @see java.util.Iterator#remove()
         */
        public void remove()
        {
            throw new UnsupportedOperationException("remove not implemented");
        }
        
        /** The my cursor. */
        private int myCursor = 1;
    }

    
    
    /**
     * constructs an empty priority queue, when new elements
     * are added their natural order will be used to determine
     * which is minimal. This means elements that are added must
     * implement the <code>Comparable</code> interface and must
     * be <em>mutually comparable</em>, i.e.,
     * <code>e1.compareTo(e2)</code> must not throw a
     * <code>CastCastException</code> for any two elements <code>e1</code>
     * and <code>e2</code> in the priority queue.
     */
    
    public PQ()
    {
        myList = new ArrayList(32);
        myList.add(null);             // first slot has index 1
        myIds=new HashSet<Long>();
        mySize = 0;
    }

    /**
     * constructs an empty priority queue, when new elements
     * are added the <code>Comparator comp</code> determines which is
     * smaller.
     *
     * @param comp is the <code>Comparator</code> used in determining order
     */

    public PQ(Comparator comp)
    {
        this();
        myComp = comp;
    }

    /**
     * all elements in coll are added to the priority queue. The
     * complexity is O(n) where <code>coll.size() == n</code>
     *
     * @param coll is a collection of mutually comparable elements
     */

    public PQ(Collection coll)
    {
        this();
        myList.addAll(coll);
        myIds.addAll(coll);
        mySize = coll.size();

        for(int k=coll.size()/2; k >= 1; k--)
        {
            heapify(k);
        }
    }

    /**
     * A new element <code>o</code> is added to the priority queue
     * in O(log n) time where n is the size of the priority queue.
     * <P>
     * The return value should be ignored, a boolean value must be
     * returned because of the requirements of the
     * <code>Collection</code> interface.
     *
     * @param o is the (Comparable) object added to the priority queue
     * @return true
     */
    
    public boolean add(Object o)
    {
        myList.add((PQElement) o);        // stored, but not correct location
        myIds.add(((PQElement)o).nodeId);
        mySize++;             // added element, update count
        int k = mySize;       // location of new element

        while (k > 1 && myComp.compare(myList.get(k/2), o) > 0)
        {
            myList.set(k, myList.get(k/2));
            k /= 2;
        }
        myList.set(k,(PQElement) o);
        
        return true;
    }

    /**
     * Size.
     *
     * @return the number of elements in the priority queue
     */
    public int size()
    {
        return mySize;
    }

    /**
     * Checks if is empty.
     *
     * @return true if and only if the priority queue is empty
     */
    public boolean isEmpty()
    {
        return mySize == 0;
    }

    /**
     * The smallest/minimal element is removed and returned
     * in O(log n) time where n is the size of the priority queue.
     *
     * @return the smallest element (and removes it)
     */
    
    public Object remove()
    {
    
        if (! isEmpty())
        {
            Object hold = myList.get(1);
            
            myList.set(1, myList.get(mySize));  // move last to top
            PQElement a=myList.remove(mySize);              // pop last off
            myIds.remove(a.nodeId);
            mySize--;
            if (mySize > 1)
            {
                heapify(1);
            }
            return hold;
        }
        return null;
    }

    /**
     * Executes in O(1) time, returns smallest element.
     *
     * @return the minimal element in the priority queue
     */
    
    public Object peek()
    {
        return myList.get(1);
    }

    /**
     * The order of the elements returned by the iterator is not specified.
     *
     * @return an iterator of all elements in priority queue
     */
    
    public Iterator iterator()
    {
        return new PQItr();
    }

    
    
    /**
     * Contains.
     *
     * @param id the id
     * @return true, if successful
     */
    public boolean contains(Long id){
    	
    	return myIds.contains(id);
//    	boolean r=myList.contains(new A(id,0));
//    	
//    	return r;
    }
    
    /**
     * works in O(log(size()-vroot)) time.
     *
     * @param vroot is the index at which re-heaping occurs
     * @precondition: subheaps of index vroot are heaps
     * @postcondition: heap rooted at index vroot is a heap
     */
    
    private void heapify(int vroot)
    {
        Object last = myList.get(vroot);
        int child, k = vroot;
        while (2*k <= mySize)
        {
            child = 2*k;
            if (child < mySize &&
                        myComp.compare(myList.get(child),
                                       myList.get(child+1)) > 0)
            {
                child++;
            }
            if (myComp.compare(last, myList.get(child)) <= 0)
            {
                break;
            }
            else
            {
                myList.set(k, myList.get(child));
                k = child;
            }
        }
        myList.set(k, (PQElement) last);
    }
   

    /**
     * simple test harnass that inserts all arguments into a
     * priority queue and then removes them one at a time and prints
     * them one per line as they are removed
     * thus effectively sorting in O(n log n) time.
     *
     * @param args the arguments
     */
    
    public static void main(String args[])
    {
        PQ pq = new PQ(Arrays.asList(args));

        while (pq.size() > 0)
        {
            System.out.println(pq.peek());
            pq.remove();
        }
    }
}
