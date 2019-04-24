/**
 * @author Rahil Agrawal
 * z5165505@student.unsw.edu.au
 * Assignment 2 - Shipment Planner
 * Port - Contains the following information about a Port
 *  - Name of the port that the Port corresponds to
 *  - The edges of this port (stored as a HashMap that maps the 
 *  port name to the travel time (edge weight) between the ports
 *  - 
 */
import java.util.HashMap;

public class Port{
	private String name;
    private HashMap<String, Integer> edges;
    private Integer refuelCost;

    /**
     * Constructor for Port
     * @param name - Name of the port
     * @param refuelCost - Cost of refuelling the ship 
     */
    public Port(String name, Integer refuelCost) {
        this.name = name;
        this.edges = new HashMap<>();
        this.edges.put(name, 0);
        this.refuelCost = refuelCost;
    }
    /**
     * Getter method for refuelCost
     * @return - The cost of refuelling the ship when the ship is at the current port
     */
    public Integer getRefuelCost() {
		return refuelCost;
	}
    
	/**
	 * addEdge - Add an edge between the current port and the given port
	 * @param name - Name of the port that forms the other end of thedge
	 * @param weight - Time taken to travel between the current port and the given port
	 */
	public void addEdge(String name, Integer weight) {
        this.edges.put(name, weight);
    }
    
    /**
     * Getter Method for name - The name of the port
     * @return The name of the port
     */
	public String getName() {
        return name;
    }
    
   /**
    * Returns an edge (The edge weight) between the current port and a given port
    * @param name - The name of the port that is the second end of the edge
    * @return the weight of the edge between the given two ports
    */
	public Integer getEdge(String name) {
    	return edges.get(name);
    }

    /**
     * Getter Method for edges
     * @return HashMap of ports that are mapped to edge weights
     * These are the edges between the port and its neighbours
     */
	public HashMap<String, Integer> getEdges(){
    	return this.edges;
    }
   
}
