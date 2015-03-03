package LinReg;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

/**
 * Writes the data into .R file to create a graph
 * @author - cn262114
 */
public class DataDisplay {

	/**
	 * Reads the .dat file & creates a .r file
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		// Read the content for each file
		for(int i=0; i<=args.length-1; i++) {
			
			// Read the file
			BufferedReader buffer = new BufferedReader(new FileReader(args[i]));
			
			// Create separate X, Y point sets
			String xPoints = "", yPoints = "";
			
			// Tokenize the (x, y) points 
			for(String str; (str = buffer.readLine())!= null; ) {
				StringTokenizer st = new StringTokenizer(str, " ");
				xPoints += st.nextToken() + ", ";
				yPoints += st.nextToken() + ", ";
			}
			
			// Remove the last ", " from the strings ( R gives error if not removed )
			xPoints = xPoints.substring(0, xPoints.length()-2);
			yPoints = yPoints.substring(0, yPoints.length()-2);
			
			// Send the X, Y point sets to create a graph
			createGraph(xPoints, yPoints, Integer.toString(i));

			// close the buffer
			buffer.close();
		}
	}
	
	/**
	 * Creates Graph file for R (saves with .R extension) 
	 * @param xValues
	 * @param yValues
	 * @throws IOException 
	 */
	private static void createGraph(String xValues, String yValues, String fileName) throws IOException {
		// Create Directory 'output' & Create a file with same name as the argument.
		new File("./output/").mkdir();
		File file = new File(".\\output\\" + fileName+ ".r");
		BufferedWriter buffer = new BufferedWriter(new FileWriter(file));

		// Write comments for the file
		buffer.write("## File Created from DataDisplay.java - Converted data files to 2D graphs ##\n\n");
		
		// Write X, Y Values to file
		buffer.write("x <- c(" + xValues + ")\n");
		buffer.write("y <- c(" + yValues + ")\n\n");
		
		// Plot the graph
		buffer.write("plot(x, y, pch=20, col=\"blue\", xlab=\"X Axis\", ylab=\"Y Axis\", ylim=c(-1, 1), xlim=c(0, 1)) \n");
		
		buffer.close();
		System.out.println("Created graph: " + fileName + ".r"); 
	}
}
