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

public class PolicyIteration {
    
    public static Grid gridEnvironment;
	private static List<Utility[][]> utilityList;
	private static AgentState[][] grid;
	private static int iterations = 0;
	//private static double convergeThreshold;
	private static boolean isValueIteration = true;

	public static void main(String[] args) {

		// Initialize grid environment
		gridEnvironment = new Grid();
		grid = gridEnvironment.getGrid();

		// Execute policy iteration
		runPolicyIteration(grid);

		// Display experiment results
		displayResults();

		// Save utility estimates to csv file for plotting
		SaveOutput.writeToFile(utilityList, "policy_iteration_utilities");
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


	private static void displayResults() {
		// Final item in the list is the optimal policy derived by policy iteration
		int latestUtilities = utilityList.size() - 1;
		final Utility[][] optimalPolicy =
		utilityList.get(latestUtilities);

		// Displays the Grid Environment
		ShowOutput.dispGrid(grid);

		// Displays the experiment setup
		ShowOutput.displayExperimentSetup(isValueIteration, 0);

		// Display total number of iterations required for convergence
		ShowOutput.displayIterationsCount(iterations);

		// Display the utilities of all the (non-wall) states
		ShowOutput.displayUtilities(grid, optimalPolicy);

		// Display the optimal policy
		ShowOutput.displayPolicy(optimalPolicy);

		// Display the utilities of all states
		ShowOutput.displayUtilitiesGrid(optimalPolicy);
	}

}
