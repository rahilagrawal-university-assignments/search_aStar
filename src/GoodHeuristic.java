/**
 * @author Rahil Agrawal
 * z5165505@student.unsw.edu.au
 * Assignment 2 - Shipment Planner
 * GoodHeaursitc - Smart Heuristic function 
 * that implements the Heuristic Interface
 */

public class GoodHeuristic implements Heuristic {

    /**
     * This function runs in time O(n) where
     * n is the number of shipments yet to be done
     * whihc is stored as an arrayList in the State.
     * Clearly, it goes through every shipment in the
     * list of shipments and adds certain costs depending on the distances and starting node
     * All the lookups for Port Names and edges are O(1) constant time as they are implemented
     * using HashMaps.  
     */
	@Override
    public Integer heuristicCost(State state) {
        Integer returnCost = 0;
        int flag = 0;
        if (state.getShipments().size() == 0) return 0;
        int min = state.getCurrentPort().getEdge(state.getShipments().get(0).getFrom().getName());
        for(int i = 0;i<state.getShipments().size();i++) {
        	Shipment shipment = state.getShipments().get(i);
        	if(!(shipment.getFrom() == state.getCurrentPort())) {
        		if(state.getCurrentPort().getEdge(shipment.getFrom().getName()) < min)
        			min = state.getCurrentPort().getEdge(shipment.getFrom().getName()); 
        	}
        	else {
        		flag = 1;
        	}
        	returnCost += shipment.getFrom().getEdge(shipment.getTo().getName()) + shipment.getTo().getRefuelCost();
        }
    	return (flag==1) ? returnCost : returnCost + min;
    }
}
