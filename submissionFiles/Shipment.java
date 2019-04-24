/**
 * @author Rahil Agrawal
 * z5165505@student.unsw.edu.au
 * Assignment 2 - Shipment Planner
 * Shipment - Contains the following information about a shipment
 *  - The port that the shipment is to be started from
 *  - The port that the shipment is to be made to
 */
public class Shipment {
	
	private Port from;
	private Port to;
	
	/**
	 * Constructor for a shipment object
	 * @param from
	 * @param to
	 */
	public Shipment(Port from, Port to) {
		this.from = from;
		this.to= to;
	}
	/**
	 * Getter method for the starting port
	 * @return from
	 */
	public Port getFrom() {
		return from;
	}
	
	/**
	 * Getter method for the destination port
	 * @return to
	 */
	public Port getTo() {
		return to;
	}
	
}
