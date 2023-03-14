package iterations;

import java.util.ArrayList;
import java.util.List;

import controller.ShowOutput;
import controller.SaveOutput;
import controller.UtilityController;
import grid.Utility;
import grid.AgentState;
import grid.Grid;

public class ValueIteration {
    public static Grid gridEnvironment;
	private static List<Utility[][]> utilityList;
	private static AgentState[][] grid;
	private static int iterations = 0;
	private static double convergeThreshold;
	private static boolean isValueIteration = true;

	public static void main(String[] args) {

		// Initialize grid environment
		gridEnvironment = new Grid();
		grid = gridEnvironment.getGrid();

		// Execute value iteration
		runValueIteration(grid);

		// Display experiment results
		displayResults();

		// Save utility estimates to csv file for plotting
		SaveOutput.writeToFile(utilityList, "value_iteration_utilities");
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
