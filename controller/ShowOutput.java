/*
 * This code defines a public ShowOutput class with four methods to display the Grid, 
 * the policy, the utilities of all the (non-wall) states, and the utilities of all the states, in a grid format. 
 * Each method uses StringBuilder to format the output and prints it to the console. 
 */

package controller;

import java.text.DecimalFormat;

import grid.Utility;
import grid.AgentState;

public class ShowOutput {

    /**
	 * Display the Grid
	 * @param grid 2D array of State objects representing the environment grid
	 */

	public static void dispGrid(AgentState[][] grid) {

        // Create a StringBuilder object with a title for the frame
		StringBuilder sb = ShowOutput.frameTitle("GRID WORLD");
		sb.append("|");

        // Add separators for the columns
		for(int col = 0 ; col < globals.GridConstants.TOTAL_NUM_COLS ; col++) {
			sb.append("--------|");
		}
		sb.append("\n");

		for (int row = 0; row < globals.GridConstants.TOTAL_NUM_ROWS; row++) {

			sb.append("|");

            // Add separators for the rows
			for(int col = 0 ; col < globals.GridConstants.TOTAL_NUM_COLS ; col++) {
				sb.append("--------|".replace('-', ' '));
			}
			sb.append("\n");

			sb.append("|");
			for(int col = 0 ; col < globals.GridConstants.TOTAL_NUM_COLS ; col++) {

				AgentState state = grid[col][row];
				String temp;

                // If the current state is the agent's starting point, label it as "Start"
				if (col == globals.GridConstants.AGENT_INIT_COL && row == globals.GridConstants.AGENT_INIT_ROW) {
					temp = " Start";
				} 
                
                // If the current state is a wall, label it as "Wall"
                else if(state.isWall()) {
					temp = "Wall";
				}

                // If the current state has a reward different than the default white reward, display the reward
				else if(state.getReward() != globals.IterationConstants.REWARD_WHITE) {
					temp = Double.toString(state.getReward());
					if (temp.charAt(0) != '-') {
						temp = " " + temp;
					}
				}

                // Otherwise, leave a blank space
				else {
					temp = String.format("%4s", "");
				}
				int n = (8 - temp.length())/2;
				String str = String.format("%1$"+n+"s", "");
				sb.append(str + temp + str + "|");
			}

			sb.append("\n|");

            // Add separators for the rows
			for(int col = 0 ; col < globals.GridConstants.TOTAL_NUM_COLS ; col++) {
				sb.append("--------|".replace('-', ' '));
			}
			sb.append("\n");

			sb.append("|");

            // Add separators for the columns
			for(int col = 0 ; col < globals.GridConstants.TOTAL_NUM_COLS ; col++) {
				sb.append("--------|");
			}
			sb.append("\n");
		}

		System.out.println(sb.toString());

	}

	/**
     * Displays the optimal policy, which is the action to be taken at each state, given a 2D array of utility values.
     * 
     * @param utilArr the 2D array of Utility objects representing the utility values of each state
    */

	public static void displayPolicy(final Utility[][] utilArr) {
		
        // create a StringBuilder object to build the output string
        StringBuilder sb = frameTitle("Plot of Optimal Policy");
		sb.append("|");
		for(int col = 0 ; col < globals.GridConstants.TOTAL_NUM_COLS ; col++) {
			sb.append("--------|");
		}
		sb.append("\n");
		for (int row = 0; row < globals.GridConstants.TOTAL_NUM_ROWS; row++) {

			sb.append("|");
			for(int col = 0 ; col < globals.GridConstants.TOTAL_NUM_COLS ; col++) {
				sb.append("--------|".replace('-', ' '));
			}
			sb.append("\n");

            // add the action symbol for each state in the current row
			sb.append("|");
			for (int col = 0; col < globals.GridConstants.TOTAL_NUM_COLS; col++) {
				String util = utilArr[col][row].getActionStr();
				int n = (9 - util.length())/2;
				String str = String.format("%1$"+n+"s", "");
				String str1 = String.format("%1$"+(n-1)+"s", "");
				sb.append(str + util + str1 + "|");
			}

			sb.append("\n|");
			for(int col = 0 ; col < globals.GridConstants.TOTAL_NUM_COLS ; col++) {
				sb.append("--------|".replace('-', ' '));
			}
			sb.append("\n");

			sb.append("|");
			for(int col = 0 ; col < globals.GridConstants.TOTAL_NUM_COLS ; col++) {
				sb.append("--------|");
			}
			sb.append("\n");
		}

		System.out.println(sb.toString());

	}

	/**
     * Display the utilities of all the (non-wall) states in the grid.
     * 
     * @param grid 2D array of State objects representing the grid world
     * @param utilArr 2D array of Utility objects representing the utility values of the states in the grid
    */

	public static void displayUtilities(final AgentState[][] grid, final Utility[][] utilArr) {
		StringBuilder sb = frameTitle("Utility Values of States");
		for (int col = 0; col < globals.GridConstants.TOTAL_NUM_COLS; col++) {
			for (int row = 0; row < globals.GridConstants.TOTAL_NUM_ROWS; row++) {

				if (!grid[col][row].isWall()) {
					String util = String.format("%.8g", utilArr[col][row].getUtil());
					sb.append("(" + col + ", " + row + "): " + util + "\n");
				}
			}
		}
		System.out.println(sb.toString());
	}

	/**
     * Displays the utilities of all the states in a grid format.
     * 
     * @param utilArr 2D array of Utility objects containing the utility values of each state
    */

	public static void displayUtilitiesGrid(final Utility[][] utilArr) {

		StringBuilder sb = frameTitle("Utilities of All States (Map)");

		sb.append("|");
		for(int col = 0 ; col < globals.GridConstants.TOTAL_NUM_COLS ; col++) {
			sb.append("--------|");
		}
		sb.append("\n");

		String pattern = "00.000";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);

		for (int row = 0; row < globals.GridConstants.TOTAL_NUM_ROWS; row++) {

			sb.append("|");
			for(int col = 0 ; col < globals.GridConstants.TOTAL_NUM_COLS ; col++) {
				sb.append("--------|".replace('-', ' '));
			}
			sb.append("\n");

			sb.append("|");
			for (int col = 0; col < globals.GridConstants.TOTAL_NUM_COLS; col++) {

				sb.append(String.format(" %s |",
				decimalFormat.format(utilArr[col][row].getUtil()).substring(0, 6)));
			}

			sb.append("\n|");
			for(int col = 0 ; col < globals.GridConstants.TOTAL_NUM_COLS ; col++) {
				sb.append("--------|".replace('-', ' '));
			}
			sb.append("\n");

			sb.append("|");
			for(int col = 0 ; col < globals.GridConstants.TOTAL_NUM_COLS ; col++) {
				sb.append("--------|");
			}
			sb.append("\n");
		}

		System.out.println(sb.toString());

	}

	/**
     * Display the number of iterations
     * 
     * @param num The number of iterations to display
    */

	public static void displayIterationsCount(int num) {
		StringBuilder sb = frameTitle("Total Iteration Count");
		sb.append("Iterations: " + num + "\n");
		System.out.println(sb.toString());
	}

	/**
     * Display the experiment setup for value iteration or policy iteration
     * 
     * @param isValueIteration True if the experiment is for value iteration, false otherwise
     * @param convergeThreshold The convergence threshold to use in value iteration
    */
	
    public static void displayExperimentSetup(boolean isValueIteration, double convergeThreshold) {
		StringBuilder sb = frameTitle(" - SETUP - ");
		if (isValueIteration) {
			sb.append("DISCOUNT FACTOR\t\t" + ":\t" + globals.IterationConstants.DISCOUNT_FACTOR + "\n");
			sb.append("UPPER BOUND OF UTILITY\t" + ":\t" + String.format("%.5g", globals.IterationConstants.UTILITY_UPPER_BOUND) + "\n");
			sb.append("MAXIMUM REWARD\t" + ":\t" + globals.IterationConstants.MAXIMUM_REWARD + "\n");
			sb.append("CONSTANT 'c'\t\t" + ":\t" + globals.IterationConstants.MAXIMUM_ALLOWED_ERROR + "\n");
			sb.append("EPSILON (c * Rmax)\t" + ":\t" + globals.IterationConstants.MAXIMUM_ALLOWABLE_DISCOUNTED_ERROR + "\n");
			sb.append("CONVERGENCE THRESHOLD\t:\t" + String.format("%.5f", convergeThreshold) + "\n\n");
		} else {
			sb.append("DISCOUNT\t:\t" + globals.IterationConstants.DISCOUNT_FACTOR + "\n");
			//(i.e. # of times simplified Bellman update is repeated to produce the next utility estimate)
			sb.append("K\t\t:\t" + globals.IterationConstants.K + "\n\n");
		}
		System.out.print(sb.toString());
	}

    /**
     * Create a frame for a title string
     * 
     * @param str The title string to create the frame for
     * @return A StringBuilder object containing the title string framed with asterisks
    */
    
	public static StringBuilder frameTitle(String str) {
		StringBuilder sb = new StringBuilder();
		int padding = 4;
		sb.append("\n");
		for(int i = 0; i < str.length()+padding; i++) {
			sb.append("*");
		}
		sb.append("\n");
		sb.append("* " + str + " *");
		sb.append("\n");
		for(int i = 0; i < str.length()+padding; i++) {
			sb.append("*");
		}
		sb.append("\n");
		sb.append("\n");
		return sb;
	}

}
