package LinReg;
import java.io.IOException;
import weka.core.matrix.Matrix;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

/**
 * Calculates M vs ERMS values with out regularization  
 * @author - cn262114
 */
public class LinearRegressionWithoutRegularization {

	/**
	 * main method
	 * @param args - args[0] = Training data file ; args[1] = Test Data file
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		// Get the Training data set
		String trainingDataFile = args[0];
		String testDataFile = args[1];
		
		// Create Strings for M & Erms
		String mPoints = "";
		String ermsTestPoints = "";
		String ermsTrainPoints = "";

		// Find W for each M where M belongs to [0-9] & Erms Values
		for(int m=0; m<=9; m++) {
			// Find W
			FindW findW = new FindW(m, trainingDataFile, 0);
			Matrix W = findW.compute();

			// Find Erms 
			FindErms findErms = new FindErms();
			double eRMStest = findErms.calculate(testDataFile, W);
			double eRMStrain = findErms.calculate(trainingDataFile, W);

			// Write to point strings
			mPoints += Integer.toString(m) + ", ";
			ermsTestPoints += Double.toString(eRMStest) + ", ";
			ermsTrainPoints += Double.toString(eRMStrain) + ", ";
		}
		
		// Remove extra ", " in point strings
		mPoints = mPoints.substring(0, mPoints.length()-2);
		ermsTestPoints = ermsTestPoints.substring(0, ermsTestPoints.length()-2);
		ermsTrainPoints = ermsTrainPoints.substring(0, ermsTrainPoints.length()-2);

		// Generate Graph for R
		createGraph(mPoints, ermsTestPoints, ermsTrainPoints, "M_vs_ERMS_NoReg");
	}

	/**
	 * Creates Graph file for R (saves with .R extension) 
	 * @param xValues
	 * @param y1Values
	 * @param y2Values
	 * @throws IOException 
	 */
	private static void createGraph(String xValues, String y1Values, String y2Values, String fileName) throws IOException {
		// Create Directory 'output' & Create a file with same name as the argument.
		new File("./output/").mkdir();
		File file = new File(".\\output\\" + fileName+ ".r");
		BufferedWriter buffer = new BufferedWriter(new FileWriter(file));

		// Write comments for the file
		buffer.write("## File Created from LinearRegressionWithoutRegularization.java - M vs Erms values ##\n\n");

		// Write X, Y Values to file
		buffer.write("x <- c(" + xValues + ")\n");
		buffer.write("y1 <- c(" + y1Values + ")\n");
		buffer.write("y2 <- c(" + y2Values + ")\n\n");

		// Plot the graph
		buffer.write("plot(x, y1, type='o', pch=2, col=\"blue\", xlab=\"M\", ylab=\"ERMS\", ylim=c(0, 0.2), xlim=c(0,10)) \n");
		buffer.write("lines(x, y2, type='o', pch=20, col=\"red\", lty=2) \n");
		
		// Legend to the graph
		buffer.write("legend(\"topleft\", c(\"Test\",\"Train\"), cex=0.8, col=c(\"blue\",\"red\"), lty=1:2, lwd=2, bty=\"n\")");

		buffer.close();
		System.out.println("Created graph: (in output directory)" + fileName + ".r"); 
	}

}
