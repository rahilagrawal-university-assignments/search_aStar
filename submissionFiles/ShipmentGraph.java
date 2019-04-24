/**
 * @author Rahil Agrawal
 * z5165505@student.unsw.edu.au
 * Assignment 2 - Shipment Planner
 * Shipment Graph - Consists of
 *  - Graph as a HashMap of ports where each port has its "edges"
 *  - The heuristic function as an object of the class that implements the heuristic interface using STRATEGY pattern
 *  - The shipments that need to be made
 *  - Functions to add elements to the graph and perform the A* search algorithm on a state space.
 * 
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.PriorityQueue;

public class ShipmentGraph {

    private HashMap<String, Port> ports;
    private Heuristic heuristic;
    private ArrayList<Shipment> shipments;

    /**
     * Constructor of Graph
     * Initiates a HashMap of ports and an ArrayList of shipments	
     */
    public ShipmentGraph() {
        this.ports = new HashMap<>();
        this.shipments = new ArrayList<>();
    }

    /**
     * addShipment - Adds a shipment from the source port to the destination port
     * Gets the port corresponding to the given port names and creates shipment using the ports
     * @param data - Name of the source port of the shipment
     * @param data2 - Name of the destination port of the shipment
     */
    public void addShipment(String data, String data2) {
    	this.shipments.add(new Shipment(ports.get(data), ports.get(data2)));
    }
    
    /**
     * addPort - Adds a port with the given port name and refuelling cost
     * @param data - Name of the port
     * @param refuelCost - Cost of refuelling the ship fully at the given port
     */
    public void addPort(String data, Integer refuelCost) {
        this.ports.put(data, new Port(data, refuelCost));
    }
    
    /**
     * addEdge - Adds an UNDIRECTED edge between the given ports
     * Edges are added by including both ports as each other's neighbours and
     * mapping the name to the edge weight (travel time)
     * @param data1 - One of the ends of the edge / Port 1
     * @param data2 - The other end of the edge / Port 2
     * @param weight - The distance between the Ports / Travel Time
     */
    public void addEdge(String data1, String data2, Integer weight) {
        Port port1 = this.ports.get(data1);
        Port port2 = this.ports.get(data2);
        port1.addEdge(data2, weight);
        port2.addEdge(data1, weight);
    }
    
    /**
     * getStateEdge - Calculates the cost of travelling from the source state to the destination state
     * @param from - Source State
     * @param to - Destination State
     * @return Integer - Returns the calculated value of travel time (weight of edge) between two states
     */
    public Integer getStateEdge(State from, State to) {
    	return from.getStateEdge(to);
    }

    /**
     * findPath - Function that allows the user to input a heuristic function
     * and then use that heuristic function as part of the A* search to go 
     * from the START state to the END state.
     * -------- STRATEGY PATTERN ----------
     */
    public void findPath() {
        State start = createStartState();
        State end = createEndState();
    	/* Always chooses the GoodHeuristic implementation however,
    	 * the user can provide their own implementation of the heuristic
    	 * interface.
    	 */
        this.heuristic = new GoodHeuristic();
    	// Use the supplied heuristic funtion and perfrom A* function for the end/goal state.
        this.aStar(start, end);
    }
    /**
     * aStar - Java implementation of the A* search algorithm on 
     * Wikipedia - 
     * 
     * @param start - Start State
     * @param end - Goal state
     */
    private void aStar(State start, State end) {
       
    	// Set of visited states
        HashSet<State> closedSet = new HashSet<>();
        // Set of the ports too be visited next in a certain order
        PriorityQueue<State> openSet = new PriorityQueue<>();
        // At the start, the Start state is the only state we know we have to visit
        // so add it to our PQ
        start.setFScore(0);
        openSet.add(start);
        start.setGScore(0);

        // While we still have states to visit
        while (!openSet.isEmpty()) {
        	
        	// Pop of the state with the lowest f(n)
            State current = openSet.poll();
            if (current == null) break;
            
            // We have reached the end.
            // So we output the number of ports we visited and then reconstruct the path to our start port
            if (current.getShipments().size() == 0) {
                System.out.println(closedSet.size() + " nodes expanded");
                this.reconstructPath(current);
                return;
            }

            // Add current state to the set of visited states.
            closedSet.add(current);
            // Discover the state's neighbours
            ListIterator<State> neighbours = current.getNeighbours();
            while (neighbours.hasNext()) {
                State neighbour = neighbours.next();
                // We have already visited the neighbouring state
                if (closedSet.contains(neighbour)) continue;
                // We've discovered a new state!
                if (!openSet.contains(neighbour)) openSet.add(neighbour);
                // Calculate the g(n) for the non-visited neighbour
                Integer tentativeGScore = current.getGScore() + this.getStateEdge(current, neighbour);
                // We already have a shorter route to this state
                if (neighbour.getGScore() != null && tentativeGScore >= neighbour.getGScore()) continue;

                // Record that neighbour came from current
                neighbour.setPrevious(current);
                // Store the state's g(n)
                neighbour.setGScore(tentativeGScore);
                // Store the state's f(n) = g(n) + h(n)
                neighbour.setFScore(tentativeGScore + this.heuristic.heuristicCost(neighbour));
                // neighbour.setFScore(tentativeGScore);
                // Re-add the neighbour to the priority queue to ensure it is ordered correctly
                openSet.remove(neighbour);
                openSet.add(neighbour);
            }
        }
    }
    /**
     * reconstructPath - Use the data stored for each state to generate the path 
     * by going from the end port to the start state (retracing the path that brought us to
     * the goal state)
     * @param current - Current state (end State)
     */
    private void reconstructPath(State current) {
    	// Output the total distance travelled in reaching the goal state
        System.out.println("cost = " + current.getGScore());
        ArrayList<String> places = new ArrayList<>();
       // While there are more states, keep going back until you reach the start state
        while (current != null) {
        	// Add in reverse order what ports were visisted
        	State previous = current.getPrevious();
        	String currentPort = current.getCurrentPort().getName();
        	Shipment whichShipment = current.getShipment();
       		places.add(currentPort);
       		places.add(whichShipment.getFrom().getName());
        	// If you are at the second last state, you have added all the ports
        	if(previous.getPrevious() == null) {
        		places.add(whichShipment.getFrom().getName());
    			places.add(previous.getCurrentPort().getName());
    			break;
        	}	
        	// If a port is the end of a shipment and the start of the next one, add it twice to generate
        	// both edges
        	if(!(previous.getCurrentPort().getName().equals(whichShipment.getFrom().getName()))) {
        		places.add(whichShipment.getFrom().getName());
        		places.add(previous.getCurrentPort().getName());
        	}
        	current = current.getPrevious();
        }
        // Build a string that prints the route taken in the correct order
        // Essentially reverts the reverse order - thereby printing the forward order
        // of trips.
        StringBuilder output = new StringBuilder();
        for (int i = places.size()-1; i >= 0; i-=2) {
        	if(!(places.get(i).equals(places.get(i-1))))
        	{
        		output.append("Ship " + places.get(i) + " to " + places.get(i-1)); 
        		if(i!=1) output.append("\n");
        	}
        }
        System.out.println(output);

    }
    
    /**
     * getShipments - Getter method for shipments array
     * @return shipments - ArrayList of Shipments
     */
    public ArrayList<Shipment> getShipments(){
    	return this.shipments;
    }
    
    /**
     * getPorts - Getter method for ports hashmap
     * @return ports - HashMap of Ports
     */
    public HashMap<String, Port> getPorts(){
    	return this.ports;
    }
    
    /**
     * createStartState - Creates the start state
     * Start state consists of all the shipments that the user wants to make
     * @return startState - The state containing all shipments that need to be made
     */
    public State createStartState() {
		State startState = new State(ports.get("Sydney"));
		for(Shipment shipment : shipments) {
			startState.addShipment(shipment);
		}
		return startState;
	}
	
    /**
     * createEndState - Create the goal state
     * End State consists of list of shipments
     * This means that all shipments have been made
     * @return endState - The state containing 0 shipments
     */
	public State createEndState() {
		State endState = new State(null);
		return endState;
	}
}
