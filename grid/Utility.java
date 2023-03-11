/*
 * Utility class representing the utility value and action associated with a state.
 */

package grid;

public class Utility implements Comparable<Utility> {

	private AgentAction agent_action = null;
	private double utility = 0.000;

    // Creates a new Utility object with default values.
	public Utility() {
		agent_action = null;
		utility = 0.000;
	}

    /*
     * Creates a new Utility object with specified action and utility values.
     * @param action The action associated with the state
     * @param util The utility value of the state
     */

	public Utility(AgentAction action, double util) {
		this.agent_action = action;
		this.utility = util;
	}

    /*
     * Returns the action associated with the state.
     * @return The action associated with the state
     */

	public AgentAction getAction() {
		return agent_action;
	}

    /*
     * Returns the string representation of the action associated with the state.
     * @return The string representation of the action associated with the state
     */

	public String getActionStr() {
		// Return any 1 of the 4 actions, but at wall there will be no action
		return agent_action != null ? agent_action.toString() : " Wall";
	}

    /*
     * Sets the action associated with the state.
     * @param action The action to be set
     */

	public void setAction(AgentAction action) {
		this.agent_action = action;
	}

    /*
     * Returns the utility value of the state.
     * @return The utility value of the state
     */

	public double getUtil() {
		return utility;
	}

    /*
     * Sets the utility value of the state.
     * @param util The utility value to be set
     */

	public void setUtil(double util) {
		this.utility = util;
	}

    /*
     * Compares this Utility object with another Utility object based on their utility values in descending order.
     * @param other The other Utility object to be compared with
     * @return A negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
     */

	@Override
	public int compareTo(Utility other) {
		// Utility values are returned in descending order
		return ((Double) other.getUtil()).compareTo(getUtil());
	}

}