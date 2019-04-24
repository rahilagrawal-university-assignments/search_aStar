/**
 * @author Rahil Agrawal
 * z5165505@student.unsw.edu.au
 * Assignment 2 - Shipment Planner
 * State - Contains the following information about a state
 *  - Shipments yet to be done
 *  - gScore - the total cost of getting to this state from the start state
 *  - currentPort - which port are we at if we are at this state (corresponds to a particular shipment)
 *  - Previous - Previous state, the state that on making a certain shipment led to this state
 *  - fScore - The cost of getting to this port + edtimated cost of going from this state to the goal state
 *  - Neighbours - the possible states that can be reached from this state
 */

import java.util.ArrayList;
import java.util.ListIterator;

public class State implements Comparable<State>{
	
	private Port currentPort;
	private Integer gScore;
	private Integer fScore;
	private ArrayList<Shipment> shipments;
	private ArrayList<State> neighbours;
	private State previous;
	private Shipment shipment;
	
	/**
	 * Constructor for State
	 * @param currentPort - The port we are at if have reached this state
	 */
	public State(Port currentPort) {
		this.currentPort = currentPort;
		this.shipments = new ArrayList<>();
		this.neighbours = new ArrayList<>();
		this.gScore = null;
		this.fScore = null;
		this.previous = null;
	}
	
	/**
	 * Getter method for previous
	 * @return
	 */
	public State getPrevious() {
		return previous;
	}

	/**
	 * Setter method for previous
	 * @param previous
	 */
	public void setPrevious(State previous) {
		this.previous = previous;
	}

	/**
	 * Method to compare two states
	 * We want to compare states by fScore so we can find the most optimal next state to visit during our A* search
	 * fScore = f(n) = g(n) + h(n) 
	 * This method overrides the default compareTo method
	 */
	@Override
    public int compareTo(State o) {
        Integer oFScore = o.getFScore();
        if (this.fScore != null && oFScore != null) {
            return this.fScore - oFScore;
        }
        else if (this.fScore == null) {
            return 1;
        }
        else {
            return -1;
        }
    }

    /**
     * Getter Method for currentPort
     * @return
     */
	public Port getCurrentPort() {
		return currentPort;
	}

	/**
	 * Setter Method for CurrentPort
	 * @param currentPort
	 */
	public void setCurrentPort(Port currentPort) {
		this.currentPort = currentPort;
	}

	/**
	 * Getter method for gScore
	 * @return
	 */
	public Integer getGScore() {
		return gScore;
	}

	/**
	 * Setter method for gScore
	 * @param gScore
	 */
	public void setGScore(Integer gScore) {
		this.gScore = gScore;
	}

	/**
	 * Getter Method for fScore
	 * @return
	 */
	public Integer getFScore() {
		return fScore;
	}

	/**
	 * Setter method for fScore
	 * @param fScore
	 */
	public void setFScore(Integer fScore) {
		this.fScore = fScore;
	}
	
	/**
	 * getNeighbours - Get an iterator over the list of states that can be reached from the current state by making a shipment
	 * @return listIterator over states reachable from the current state by making one shipment
	 */
	public ListIterator<State> getNeighbours() {
        int length = shipments.size();
        for(int i = 0; i < length; i++) {
    		Port endPort = shipments.get(i).getTo();
        	State newState = new State(endPort);
        	newState.setShipment(shipments.get(i));
        	for(int j = 0; j < length; j++) {
        		if(i!=j) newState.addShipment(shipments.get(j));
        	}
        	neighbours.add(newState);
        }
        return neighbours.listIterator();
    }
	
	/**
	 * getStateEdge - finds the cost of going from this state to the given state
	 * @param to - the next state
	 * @return the cost of going from this state to the next state
	 */
	public Integer getStateEdge(State to) {
		Shipment whichShipment = to.getShipment();
		Port shipmentFrom = whichShipment.getFrom();
		Port shipmentTo = whichShipment.getTo();
		if(currentPort != shipmentFrom) {
			return currentPort.getRefuelCost() + currentPort.getEdge(shipmentFrom.getName()) + shipmentFrom.getRefuelCost() + shipmentFrom.getEdge(shipmentTo.getName());
		}
		else {
			return currentPort.getRefuelCost() + currentPort.getEdge(shipmentFrom.getName()) + shipmentFrom.getEdge(shipmentTo.getName());
		}
	}
	
	/**
	 * Add shipments to the state
	 * @param shipment
	 */
	public void addShipment(Shipment shipment) {
		this.shipments.add(shipment);
	}
	
	/**
	 * Getter method for the list of shipments in this state
	 * @return shipments
	 */
	public ArrayList<Shipment> getShipments(){
		return this.shipments;
	}
	
	/**
	 * Getter method for the shipment that was made just before arriving to this state
	 * @return shipment
	 */
	public Shipment getShipment() {
		return shipment;
	}
	
	/**
	 * Setter method for the shipment to be made to arrive to this state
	 * @param shipment
	 */
	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}
}
