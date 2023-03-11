/*
 * This class holds constants related to the grid, such as -
 * its dimensions, separators used for grid cells and row-columns, and the placement of special cells (i.e., green, brown, and walls)
 * It is marked as final to prevent subclassing
 */

package controller;

public final class GridConstants {

    // Define the dimensions of the Grid
    public static final int TOTAL_NUM_COLS = 6;
    public static final int TOTAL_NUM_ROWS = 6;

    // Define separators for grid cells and row-columns
    public static final String CELL_SEPARATOR = ";";
    public static final String ROW_COLUMN_SEPARATOR = ",";

    // Define the start position of the Agent
    public static final int AGENT_INIT_COL = 2;
	public static final int AGENT_INIT_ROW = 3;

    // Define the environment data - placement of special cells
    public static final String GREEN_CELLS = "0,0; 2,0; 5,0; 3,1; 4,2; 5,3";
	public static final String BROWN_CELLS = "1,1; 5,1; 2,2; 3,3; 4,4";
	public static final String WALL_CELLS = "1,0; 4,1; 1,4; 2,4; 3,4";

}