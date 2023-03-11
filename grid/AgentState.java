/*
 * This class represents a state in the grid environment
 * It has a reward and a boolean flag indicating whether it's a wall or not
 */

package grid;

public class AgentState {

    // The reward for being in this state, initialized to 0
    private double current_reward = 0.000;

     // Indicates whether this state is a wall or not, initialized to false
	private boolean is_wall = false;

    // Constructor that takes the initial reward for the state as a parameter
	public AgentState(double reward) {
		this.current_reward = reward;
	}

    // Get method for the reward of the state
	public double getReward() {
		return current_reward;
	}

    // Set method for the reward of the state
	public void setReward(double reward) {
		this.current_reward = reward;
	}

    // Get method for the is_wall flag of the state
	public boolean isWall() {
		return is_wall;
	}

    // Set method for the is_wall flag of the state
	public void setAsWall(boolean is_Wall) {
		this.is_wall = is_Wall;
	}

}
