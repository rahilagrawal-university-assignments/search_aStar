/**
 * @author Rahil Agrawal
 * z5165505@student.unsw.edu.au
 * Assignment 2 - Shipment Planner
 * Heuristic - Interface that needs to be implemented by certain classes
 * to use the STRATEGY pattern
 */
public interface Heuristic {
    public Integer heuristicCost(State state);
}
