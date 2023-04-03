package iterations;

import java.util.ArrayList;
import java.util.List;

import controller.ShowOutput;
import controller.SaveOutput;
import controller.UtilityController;
import grid.Utility;
import grid.AgentState;
import grid.Grid;
import grid.AgentAction;

public class ComplexMaze {
    public static Grid gridEnvironment;
	private static List<Utility[][]> utilityList;
	private static AgentState[][] grid;
	private static int iterations = 0;
	private static double convergeThreshold;
	private static boolean isValueIteration = true;

    // Change grid size in GridConstants.java

	public static void main(String[] args) {

		// Initialize grid environment
		gridEnvironment = new Grid();
		grid = gridEnvironment.getGrid();

		// Execute value iteration
		runValueIteration(grid);

		// Display experiment results
		displayResults();

		// Save utility estimates to csv file for plotting
		SaveOutput.writeToFile(utilityList, "complex_maze__value_utilities");

        // Execute policy iteration
		runPolicyIteration(grid);

		// Display experiment results
		displayResults();

		// Save utility estimates to csv file for plotting
		SaveOutput.writeToFile(utilityList, "complex_maze_policy_utilities");
	}

    public static void runPolicyIteration(final AgentState[][] grid) {

		Utility[][] currUtilArr = new Utility[globals.GridConstants.TOTAL_NUM_COLS][globals.GridConstants.TOTAL_NUM_ROWS];
		Utility[][] newUtilArr = new Utility[globals.GridConstants.TOTAL_NUM_COLS][globals.GridConstants.TOTAL_NUM_ROWS];

		// Initialize default utilities and policies for each state
		for (int col = 0; col < globals.GridConstants.TOTAL_NUM_COLS; col++) {
			for (int row = 0; row < globals.GridConstants.TOTAL_NUM_ROWS; row++) {
				newUtilArr[col][row] = new Utility();
				if (!grid[col][row].isWall()) {
					AgentAction randomAction = AgentAction.getRandomAction();
					newUtilArr[col][row].setAction(randomAction);
				}
			}
		}

		// List to store utilities of every state at each iteration
		utilityList = new ArrayList<>();

		// Used to check if the current policy value is already optimal
		boolean unchanged = true;

		do {

			UtilityController.updateUtils(newUtilArr, currUtilArr);

			// Append to list of Utility a copy of the existing actions & utilities
			Utility[][] currUtilArrCopy =
			new Utility[globals.GridConstants.TOTAL_NUM_COLS][globals.GridConstants.TOTAL_NUM_ROWS];
			UtilityController.updateUtils(currUtilArr, currUtilArrCopy);
			utilityList.add(currUtilArrCopy);
			

			// Policy estimation based on the current actions and utilities
			newUtilArr = UtilityController.calcNextUtil(currUtilArr, grid);

			unchanged = true;

			// For each state - Policy improvement
			for (int row = 0; row < globals.GridConstants.TOTAL_NUM_ROWS; row++) {
				for (int col = 0; col < globals.GridConstants.TOTAL_NUM_COLS; col++) {

					// Calculate the utility for each state, not necessary to calculate for walls
					if (!grid[col][row].isWall()) {
						// Best calculated action based on maximizing utility
						Utility bestActionUtil =
						UtilityController.calcBestUtil(col, row, newUtilArr, grid);

						// Action and the corresponding utility based on current policy
						AgentAction policyAction = newUtilArr[col][row].getAction();
						Utility policyActionUtil = UtilityController.calcActionUtil(policyAction, col, row, newUtilArr, grid);

						if((bestActionUtil.getUtil() > policyActionUtil.getUtil())) {
							newUtilArr[col][row].setAction(bestActionUtil.getAction());
							unchanged = false;
						}
					}
				}
			}
			iterations++;

		} while (!unchanged);
	}


	public static void runValueIteration(final AgentState[][] grid) {

		Utility[][] currUtilArr = new Utility[globals.GridConstants.TOTAL_NUM_COLS][globals.GridConstants.TOTAL_NUM_ROWS];
		Utility[][] newUtilArr = new Utility[globals.GridConstants.TOTAL_NUM_COLS][globals.GridConstants.TOTAL_NUM_ROWS];

		// Initialize default utilities for each state
		for (int col = 0; col < globals.GridConstants.TOTAL_NUM_COLS; col++) {
			for (int row = 0; row < globals.GridConstants.TOTAL_NUM_ROWS; row++) {
				newUtilArr[col][row] = new Utility();
			}
		}

		utilityList = new ArrayList<>();

		// Initialize delta to minimum double value first
		double delta = Double.MIN_VALUE;

		convergeThreshold = globals.IterationConstants.MAXIMUM_ALLOWABLE_DISCOUNTED_ERROR * 
        ((1.000 - globals.IterationConstants.DISCOUNT_FACTOR) / globals.IterationConstants.DISCOUNT_FACTOR);

		// Initialize number of iterations
		do {

			UtilityController.updateUtils(newUtilArr, currUtilArr);

 			delta = Double.MIN_VALUE;

			// Append to list of Utility a copy of the existing actions & utilities
 			Utility[][] currUtilArrCopy =
 			new Utility[globals.GridConstants.TOTAL_NUM_COLS][globals.GridConstants.TOTAL_NUM_ROWS];
            UtilityController.updateUtils(currUtilArr, currUtilArrCopy);
			utilityList.add(currUtilArrCopy);

			// For each state
			for(int row = 0 ; row < globals.GridConstants.TOTAL_NUM_ROWS ; row++) {
				for(int col = 0 ; col < globals.GridConstants.TOTAL_NUM_COLS ; col++) {

					// Calculate the utility for each state, not necessary to calculate for walls
					if (!grid[col][row].isWall()) {
						newUtilArr[col][row] =
						UtilityController.calcBestUtil(col, row, currUtilArr, grid);

						double updatedUtil = newUtilArr[col][row].getUtil();
						double currentUtil = currUtilArr[col][row].getUtil();
						double updatedDelta = Math.abs(updatedUtil - currentUtil);

						// Update delta, if the updated delta value is larger than the current one
						delta = Math.max(delta, updatedDelta);
					}
				}
			}
			iterations++;

		//the iteration will cease when the delta is less than the convergence threshold
		} while ((delta) >= convergeThreshold);
	}

	private static void displayResults() {
		// Final item in the list is the optimal policy derived by value iteration
		int lastIteration = utilityList.size() - 1;
		final Utility[][] optimalPolicy =
		utilityList.get(lastIteration);

		// Displays the Grid Environment
		ShowOutput.dispGrid(grid);

		// Displays the experiment setup
		ShowOutput.displayExperimentSetup(isValueIteration, convergeThreshold);

		// Display total number of iterations required for convergence
		ShowOutput.displayIterationsCount(iterations);

		// Display the final utilities of all the (non-wall) states
		ShowOutput.displayUtilities(grid, optimalPolicy);

		// Display the optimal policy
		ShowOutput.displayPolicy(optimalPolicy);

		// Display the final utilities of all states
		ShowOutput.displayUtilitiesGrid(optimalPolicy);
	}
}
