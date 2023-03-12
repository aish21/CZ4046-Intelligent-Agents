/*
 * The SaveOutput class provides methods for saving experiment results.
*/

package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import grid.Utility;

public class SaveOutput {

    /** 
     * Writes a list of Utility arrays to a CSV file with the given file name.
     * 
     * @param lstUtilitys the list of Utility arrays to write to the file
     * @param fileName the name of the file to write to
    */

    public static void writeToFile(List<Utility[][]> lstUtilitys, String fileName) {

		StringBuilder sb = new StringBuilder();
		String pattern = "00.000";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

		for (int col = 0; col < globals.GridConstants.TOTAL_NUM_COLS; col++) {
			for (int row = 0; row < globals.GridConstants.TOTAL_NUM_ROWS; row++) {

				Iterator<Utility[][]> iter = lstUtilitys.iterator();
				while(iter.hasNext()) {

					Utility[][] actionUtil = iter.next();
					sb.append(decimalFormat.format(
							actionUtil[col][row].getUtil()).substring(0, 6));

					if(iter.hasNext()) {
						sb.append(",");
					}
				}
				sb.append("\n");
			}
		}

		writeToFile(sb.toString().trim(), fileName + ".csv");

	}

    /**
     * Writes the given content to a file with the given file name.
     * 
     * @param content the content to write to the file
     * @param fileName the name of the file to write to
    */

	public static void writeToFile(String content, String fileName)
	{
		try
		{
			FileWriter fw = new FileWriter(new File(fileName), false);

			fw.write(content);
			fw.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
    
}
