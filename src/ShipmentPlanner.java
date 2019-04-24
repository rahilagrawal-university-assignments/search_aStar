/**
 * @author Rahil Agrawal
 * z5165505@student.unsw.edu.au
 * Assignment 2 - Shipment Planner
 * Main Class for a Shipment Planner
 * Takes input from a file
 * Processes input and executes methods of respective objects
 * Writes output to standard output
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ShipmentPlanner {
	
    private ShipmentGraph graph;
    
    public ShipmentPlanner() {
        this.graph = new ShipmentGraph();
    }


    /**
	 * Main Function for the project
	 * Accepts input from a file and sends line-wise input
	 * to another function
	 * @param args
	 */
    public static void main(String args[]){
		Scanner sc = null;
		// Read data from standard input / input file and create the graph
		ShipmentPlanner system = new ShipmentPlanner();
	    
		try
		{
	        sc = new Scanner(new File(args[0])); 
	        while(sc.hasNextLine()){
				system.execute(sc.nextLine(), system);
			}
	        
	    }
	    catch (FileNotFoundException e)
	    {
	        System.out.println(e.getMessage());
	    }
	    finally
	    {
	        if (sc != null) sc.close();
	    }
		/* Now that all the data has been read and we have created our graph,
		 * use the A* search algorithm for a state space search to go from
		 * the START state - The state where all shipments are yet to be made - to
		 * the END state - The state where all shipments have been made.
		 */
		system.graph.findPath();
	}
    /**
     * Function takes in a string input
     * Processes the input and calls appropriate functions
     * to add shipping details - Shipping Ports(Ports), Refuelling Times, Trip times(Edges) and Shipments to be made.
     * @param line - Consists a line of input from the input file
     */
    public void execute(String line, ShipmentPlanner system) {
    	String[] input;
    	//Split the line into an array of strings where each element is one word.
    	input = line.split(" ");
    	// Call function based on the input provided
    	if(input[0].equals("Refuelling")) {
        	system.addPort(input, system);
    	}
    	else if(input[0].equals("Time")) {
    		system.addEdge(input, system);
    	}
    	else if(input[0].equals("Shipment")) {
    		system.addShipment(input, system);
    	}
    	else {
    		return;
    	}
    }
    /**
     * addPort - Adds a port to the graph
     * @param input - Array of strings that has words of one line of input
     * @param system - Object containing the graph
     */
    public void addPort(String[] input, ShipmentPlanner system) {
    	system.graph.addPort(input[2], Integer.valueOf(input[1]));
    }
    /**
     * addEdge - Adds an edge between two ports to the graph
     * @param input - Array of strings that has words of one line of input
     * @param system - Object containing the graph
     */
    public void addEdge(String[] input, ShipmentPlanner system) {
    	system.graph.addEdge(input[2], input[3], Integer.valueOf(input[1]));
    }
    /**
     * addShipment - Adds a shipment to the graph
     * @param input - Array of strings that has words of one line of input
     * @param system - Object containing the graph
     */
	public void addShipment(String[] input, ShipmentPlanner system) {
		system.graph.addShipment(input[1], input[2]);
	}
	
}



    
