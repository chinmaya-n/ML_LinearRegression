package LinReg;
import java.io.IOException;
import weka.core.matrix.Matrix;

public class EvaluateModel {

	/**
	 * @param args - args[0] = Training .dat file ; args[1] = Tesing .dat file ; args[2] = ln Lambda
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// Get the arguments
		String trainDataFile = args[0];
		String testDataFile = args[1];
		int lnLambda = Integer.parseInt(args[2]);
		
		// Get W for estimation
		FindW w = new FindW(9, trainDataFile, lnLambda);
		Matrix mW = w.compute();
		
		// Find ERMS for testing data
		FindErms erms = new FindErms();
		System.out.println("Given Traning File: " + trainDataFile);
		System.out.println("Given Test File: " + testDataFile);
		System.out.println("ln Lambda: " + lnLambda);
		System.out.println("ERMS Value for the Testing Predictions:" + erms.calculate(testDataFile, mW, lnLambda));
	}

}
