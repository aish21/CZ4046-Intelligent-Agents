/*
 * An enum that represents all possible actions an agent can take.
 */

package grid;
import java.util.Random;

public enum AgentAction {
    
    UP("^"), // ↑ 
    DOWN("v"), // ↓
    RIGHT(">"), // →
	LEFT("<"); // ←

	// String representation of the action taken
	private String action_string_representaion;

    /**
     * Constructor for AgentAction enum.
     * @param string_rep The string representation of the action.
     */
    
	AgentAction(String string_rep) {
		this.action_string_representaion = string_rep;
	}

    /**
     * Returns the string representation of the action.
     * @return The string representation of the action.
     */

	@Override
	public String toString() {
		return action_string_representaion;
	}

    // The list of all possible actions
	private static final AgentAction[] ACTIONS = values();

    // The number of possible actions
	private static final int SIZE = ACTIONS.length;

    // A random number generator
	private static final Random RANDOM = new Random();

    /**
     * Returns a random AgentAction.
     * @return A random AgentAction.
     */

	public static AgentAction getRandomAction() {
		return ACTIONS[RANDOM.nextInt(SIZE)];
	}

}
