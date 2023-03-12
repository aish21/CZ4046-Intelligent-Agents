
/*
 * A utility manager class that provides static methods for calculating utilities 
 * and estimating next utilities based on the Bellman equation.
 */

package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import grid.Utility;
import grid.AgentState;
import grid.AgentAction;

public class UtilityController {

    /**
     * Calculates the utility all possible actions and returns action with maximum utility.
     * 
     * @param col         The column index of the current state
     * @param row         The row index of the current state
     * @param curr_utils  The current utility array
     * @param grid        The grid of states
     * @return            The Utility object with the highest utility
     */

	public static Utility calcBestUtil(final int col, final int row, final Utility[][] curr_utils, final AgentState[][] grid) {

        // Create a list to store the utilities for each action
		List<Utility> utils = new ArrayList<>();

        // Add the utilities for each possible action to the list
		utils.add(new Utility(AgentAction.UP, moveUpUtil(col, row, curr_utils, grid)));
		utils.add(new Utility(AgentAction.DOWN, moveDownUtil(col, row, curr_utils, grid)));
		utils.add(new Utility(AgentAction.LEFT, moveLeftUtil(col, row, curr_utils, grid)));
		utils.add(new Utility(AgentAction.RIGHT, moveRightUtil(col, row, curr_utils, grid)));

        // Sort the list of utilities based on the utility value
		Collections.sort(utils);

        // Return the Utility object with the maximum utility
		Utility best_util = utils.get(0);
		return best_util;

	}

    /**
     * Calculates utility an action.
     * 
     * @param action        The action to calculate the utility for
     * @param col           The column index of the current state
     * @param row           The row index of the current state
     * @param action_utils  The current utility array for the action
     * @param grid          The grid of states
     * @return              The Utility object for the given action
     */

	public static Utility calcActionUtil(final AgentAction action, final int col, final int row, final Utility[][] action_utils, final AgentState[][] grid) {

		Utility action_util = null;

		switch (action) {
			case UP:
			action_util = new Utility(AgentAction.UP, UtilityController.moveUpUtil(col, row, action_utils, grid));
			break;
			
            case DOWN:
			action_util = new Utility(AgentAction.DOWN, UtilityController.moveDownUtil(col, row, action_utils, grid));
			break;
			
            case LEFT:
			action_util = new Utility(AgentAction.LEFT, UtilityController.moveLeftUtil(col, row, action_utils, grid));
			break;
			
            case RIGHT:
			action_util = new Utility(AgentAction.RIGHT, UtilityController.moveRightUtil(col, row, action_utils, grid));
			break;
		}

		return action_util;

	}

	/**
     * Use Bellman Equation to calculate the next utility value.
     * 
     * @param utils 	The current utility list
     * @param grid    	The grid of states
     * @return        	The next utility list
     */

	public static Utility[][] calcNextUtil(final Utility[][] utils, final AgentState[][] grid) {

		Utility[][] curr_utils = new Utility[GridConstants.TOTAL_NUM_COLS][GridConstants.TOTAL_NUM_ROWS];
		
		// Initializes the current utility array with new utility objects
		for (int col = 0; col < GridConstants.TOTAL_NUM_COLS; col++) {
			for (int row = 0; row < GridConstants.TOTAL_NUM_ROWS; row++) {
				curr_utils[col][row] = new Utility();
			}
		}

		Utility[][] new_utils = new Utility[GridConstants.TOTAL_NUM_COLS][GridConstants.TOTAL_NUM_ROWS];

		// Copies the utility and action values from the previous utility array to the new utility array
		for (int col = 0; col < GridConstants.TOTAL_NUM_COLS; col++) {
			for (int row = 0; row < GridConstants.TOTAL_NUM_ROWS; row++) {
				new_utils[col][row] = new Utility(utils[col][row].getAction(), utils[col][row].getUtil());
			}
		}

		int k = 0;
		do {
			UtilityController.updateUtils(new_utils, curr_utils);

			// Updates the utility for each state based on the action stated in the policy
			for (int row = 0; row < GridConstants.TOTAL_NUM_ROWS; row++) {
				for (int col = 0; col < GridConstants.TOTAL_NUM_COLS; col++) {
					if (!grid[col][row].isWall()) {
						AgentAction action = curr_utils[col][row].getAction();
						new_utils[col][row] = UtilityController.calcActionUtil(action, col, row, curr_utils, grid);
					}
				}
			}
			k++;
		} while(k < IterationConstants.K);
		return new_utils;

	}

	/**
	 * Calculates the utility for current state -> up.
	 * 
	 * @param col 			The column of the current state
	 * @param row 			The row of the current state
	 * @param curr_utils 	The current utility array
	 * @param grid 			The state grid
	 * @return 				The utility of attempting to move up
	*/

	public static double moveUpUtil(final int col, final int row, final Utility[][] curr_utils, final AgentState[][] grid) {

		double up_util = 0.000;

		// Calculates the utility for moving up
		up_util += IterationConstants.INTENDED_PROBABILITY * goUp(col, row, curr_utils, grid);

		// Calculates the utility for moving left instead of up
		up_util += IterationConstants.LEFT_PROBABILITY * turnLeft(col, row, curr_utils, grid);

		// Calculates the utility for moving right instead of up
		up_util += IterationConstants.RIGHT_PROBABILITY * turnRight(col, row, curr_utils, grid);

		// Calculates the final utility for moving up
		up_util = grid[col][row].getReward() + IterationConstants.DISCOUNT_FACTOR * up_util;

		return up_util;

	}

	/**
	 * Calculates the utility for current state -> down
	 * 
	 * @param col 			The column index of the state
	 * @param row 			The row index of the state
	 * @param curr_utils 	The current utility array
	 * @param grid 			The grid of states
	 * @return 				The utility value for attempting to move down
	 */

	public static double moveDownUtil(final int col, final int row, final Utility[][] curr_utils, final AgentState[][] grid) {

		double down_util = 0.000;

		// Calculates the utility for moving down
		down_util += IterationConstants.INTENDED_PROBABILITY * goDown(col, row, curr_utils, grid);

		// Calculates the utility for moving left instead of down
		down_util += IterationConstants.LEFT_PROBABILITY * turnLeft(col, row, curr_utils, grid);

		// Calculates the utility for moving right instead of down
		down_util += IterationConstants.RIGHT_PROBABILITY * turnRight(col, row, curr_utils, grid);

		// Calculates the final utility for moving down
		down_util = grid[col][row].getReward() + IterationConstants.DISCOUNT_FACTOR * down_util;

		return down_util;

	}

	/**
	 * Calculates the utility for current state -> left
	 * 
	 * @param col 			The column index of the state
	 * @param row 			The row index of the state
	 * @param curr_utils 	The current utility array
	 * @param grid 			The grid of states
	 * @return 				The utility value for attempting to move left
	 */

	public static double moveLeftUtil(final int col, final int row, final Utility[][] curr_utils, final AgentState[][] grid) {

		double left_util = 0.000;

		// Calculates the utility for moving left
		left_util += IterationConstants.INTENDED_PROBABILITY * turnLeft(col, row, curr_utils, grid);

		// Calculates the utility for moving up instead of left
		left_util += IterationConstants.RIGHT_PROBABILITY * goUp(col, row, curr_utils, grid);

		// Calculates the utility for moving down instead of left
		left_util += IterationConstants.LEFT_PROBABILITY * goDown(col, row, curr_utils, grid);

		// Calculates the final utility for moving left
		left_util = grid[col][row].getReward() + IterationConstants.DISCOUNT_FACTOR * left_util;

		return left_util;

	}

	/**
	 * Calculates the utility for current state -> right
	 * 
	 * @param col 			The column index of the state
	 * @param row 			The row index of the state
	 * @param curr_utils 	The current utility array
	 * @param grid 			The grid of states
	 * @return 				The utility value for attempting to move right
	 */

	public static double moveRightUtil(final int col, final int row, final Utility[][] curr_utils, final AgentState[][] grid) {

		double right_util = 0.000;

		// Calculates the utility for moving right
		right_util += IterationConstants.INTENDED_PROBABILITY * turnRight(col, row, curr_utils, grid);

		// Calculates the utility for moving down instead of right
		right_util += IterationConstants.RIGHT_PROBABILITY * goDown(col, row, curr_utils, grid);

		// Calculates the utility for moving up instead of right
		right_util += IterationConstants.LEFT_PROBABILITY * goUp(col, row, curr_utils, grid);

		// Calculates the final utility for moving right
		right_util = grid[col][row].getReward() + IterationConstants.DISCOUNT_FACTOR * right_util;

		return right_util;

	}

	/**
	 * This method attempts to move the agent up by checking if the cell above is not a wall, and if so, returns the utility of that cell; otherwise, it returns the current cell's utility.
	 * 
	 * @param col 			The column of the current cell
	 * @param row 			The row of the current cell
	 * @param curr_utils 	The 2D array of current utilities for each state in the grid
	 * @param grid 			The 2D array of states representing the grid
	 * @return 				The utility of the cell above if the agent can move there, or the current cell's utility if the agent cannot move there
	*/
	
	public static double goUp(final int col, final int row, final Utility[][] curr_utils, final AgentState[][] grid) {

		if (row - 1 >= 0 && !grid[col][row - 1].isWall()) {
			return curr_utils[col][row - 1].getUtil();
		}
		return curr_utils[col][row].getUtil();

	}

	/**
	 * This method attempts to move the agent down by checking if the cell below is not a wall, and if so, returns the utility of that cell; otherwise, it returns the current cell's utility.
	 * 
	 * @param col 			The column of the current cell
	 * @param row 			The row of the current cell
	 * @param curr_utils 	The 2D array of current utilities for each state in the grid
	 * @param grid 			The 2D array of states representing the grid
	 * @return 				The utility of the cell below if the agent can move there, or the current cell's utility if the agent cannot move there
	*/
	
	public static double goDown(final int col, final int row, final Utility[][] curr_utils, final AgentState[][] grid) {
		
		if (row + 1 < GridConstants.TOTAL_NUM_ROWS && !grid[col][row + 1].isWall()) {
			return curr_utils[col][row + 1].getUtil();
		}
		return curr_utils[col][row].getUtil();
	
	}

	/**
	 * This method attempts to move the agent left by checking if the cell to the left is not a wall, and if so, returns the utility of that cell; otherwise, it returns the current cell's utility.
	 * 
	 * @param col 			The column of the current cell
	 * @param row 			The row of the current cell
	 * @param curr_utils 	The 2D array of current utilities for each state in the grid
	 * @param grid 			The 2D array of states representing the grid
	 * @return 				The utility of the cell to the left if the agent can move there, or the current cell's utility if the agent cannot move there
	*/
	
	public static double turnLeft(final int col, final int row, final Utility[][] curr_utils, final AgentState[][] grid) {
		
		if (col - 1 >= 0 && !grid[col - 1][row].isWall()) {
			return curr_utils[col - 1][row].getUtil();
		}
		return curr_utils[col][row].getUtil();
	
	}

	/**
	 * This method calculates the utility for attempting to move right.
	 * 
	 * @param col 			The column index of the current state
	 * @param row 			The row index of the current state
	 * @param curr_utils 	The current utility array
	 * @param grid 			The state grid
	 * @return 				The calculated utility
	*/

	public static double turnRight(final int col, final int row, final Utility[][] curr_utils, final AgentState[][] grid) {
		
		if (col + 1 < GridConstants.TOTAL_NUM_COLS && !grid[col + 1][row].isWall()) {
			return curr_utils[col + 1][row].getUtil();
		}
		return curr_utils[col][row].getUtil();
	
	}

	/**
	 * Copies the contents of the source array to the destination array.
	 * 
	 * @param src 		The source utility array
	 * @param dst 	The destination utility array
	*/

	public static void updateUtils(Utility[][] src, Utility[][] dst) {
		
		for (int i = 0; i < src.length; i++) {
			System.arraycopy(src[i], 0, dst[i], 0, src[i].length);
		}
		
	}

}