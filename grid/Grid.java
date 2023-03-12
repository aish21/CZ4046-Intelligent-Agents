/*
 * Represents a Grid Environment consisting of a 2D array of states
 */

package grid;

import controller.GridConstants;
import controller.IterationConstants;

public class Grid {
    private AgentState[][] gridEnv = null;

    // Constructor to initialize the grid environment by building the grid
	public Grid() {
		gridEnv = new AgentState[GridConstants.TOTAL_NUM_COLS][GridConstants.TOTAL_NUM_ROWS];
		build_grid();
	}

	/*
     * Returns a 2D array of states
     * @return 2D array of State objects representing the grid
     */
	public AgentState[][] getGrid() {
		return gridEnv;
	}

	/*
     * Initializes the grid by setting rewards for each state based on 
     * their color, and marking walls as unreachable
     */
	public void build_grid() {
		// All cells start with -0.040 as reward
		for(int row = 0 ; row < GridConstants.TOTAL_NUM_ROWS ; row++) {
			for(int col = 0 ; col < GridConstants.TOTAL_NUM_COLS ; col++) {
				gridEnv[col][row] = new AgentState(IterationConstants.REWARD_WHITE);
			}
		}

		// Set +1.000 as the reward for green cells
		String[] green_cells = GridConstants.GREEN_CELLS.split(GridConstants.CELL_SEPARATOR);
		for(String green_cell : green_cells) {
			green_cell = green_cell.trim();
			String [] gridInfo = green_cell.split(GridConstants.ROW_COLUMN_SEPARATOR);
			int gridCol = Integer.parseInt(gridInfo[0]);
			int gridRow = Integer.parseInt(gridInfo[1]);
			gridEnv[gridCol][gridRow].setReward(IterationConstants.REWARD_GREEN);
		}

		// Set -1.000 as the reward for brown cells
		String[] brown_cells = GridConstants.BROWN_CELLS.split(GridConstants.CELL_SEPARATOR);
		for (String brown_cell : brown_cells) {

			brown_cell = brown_cell.trim();
			String[] gridInfo = brown_cell.split(GridConstants.ROW_COLUMN_SEPARATOR);
			int gridCol = Integer.parseInt(gridInfo[0]);
			int gridRow = Integer.parseInt(gridInfo[1]);
			gridEnv[gridCol][gridRow].setReward(IterationConstants.REWARD_BROWN);
		}

		// Set 0 for all the walls and mark them as unreachable - agent does not move
		String[] wall_cells = GridConstants.WALL_CELLS.split(GridConstants.CELL_SEPARATOR);
		for (String wall_cell : wall_cells) {

			wall_cell = wall_cell.trim();
			String[] gridInfo = wall_cell.split(GridConstants.ROW_COLUMN_SEPARATOR);
			int gridCol = Integer.parseInt(gridInfo[0]);
			int gridRow = Integer.parseInt(gridInfo[1]);
			gridEnv[gridCol][gridRow].setReward(IterationConstants.WALL_COLLISION);
			gridEnv[gridCol][gridRow].setAsWall(true);
		}
	}

}
