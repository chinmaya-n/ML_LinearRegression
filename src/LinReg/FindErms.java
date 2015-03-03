package LinReg;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import weka.core.matrix.Matrix;

/**
 * Finds ERMS value for a given test data & W
 * @author - cn262114
 */
public class FindErms {

	/**
	 * Calculates the ERMS value for the given test data set using W without lambda
	 * @param dataFile
	 * @param W
	 * @return ERMS value
	 * @throws IOException
	 */
	public double calculate(String dataFile, Matrix W) throws IOException {

		// Get the data values - Read the file
		BufferedReader buffer = new BufferedReader(new FileReader(dataFile));

		// Create separate X, Y point sets
		String xPoints = "", tPoints = "";

		// Tokenize the (x, y) points - useful to find number of points
		for(String str; (str = buffer.readLine())!= null; ) {
			StringTokenizer st = new StringTokenizer(str, " ");
			xPoints += st.nextToken() + ", ";
			tPoints += st.nextToken() + ", ";
		}

		// Populate the x, t values into arrays
		StringTokenizer stX = new StringTokenizer(xPoints, ", ");
		StringTokenizer stT = new StringTokenizer(tPoints, ", ");
		double[] xArray = new double[stX.countTokens()];
		double[] tArray = new double[stT.countTokens()];
		if(xArray.length == tArray.length) {
			int i=0;
			while(stX.hasMoreTokens()) {
				xArray[i] = Double.parseDouble(stX.nextToken());
				tArray[i] = Double.parseDouble(stT.nextToken());
				i++;
			}
			buffer.close();
		}
		else {
			System.out.println("X, Y points did not match in count!! Provide Values correctly.");
			buffer.close();
			System.exit(-1);
		}

		// matrix for X values - M x N matrix. i.e M ( x0, x1, x2, .... , xM) - N X points
		Matrix testX = new Matrix(W.getRowDimension(), xArray.length);

		// Fill the matrix testX - M x N matrix
		for(int col=0; col<testX.getColumnDimension(); col++) {
			for(int row=0; row<testX.getRowDimension(); row++) {
				testX.set(row, col, Math.pow(xArray[col], row));  
			}
		}
		
		// Get System Labels = predicted labels
		Matrix sysLabel = multiply(W.transpose(), testX);

		// Compute Erms value of the prediction
		// Find Ew
		double Ew = 0;
		for(int n=0; n<tArray.length; n++) {
			Ew += Math.pow(sysLabel.get(0, n)-tArray[n], 2);
		}
		Ew = Ew/tArray.length; // 1/N over Ew
		
		// Erms = sqrt(Ew/N) -- 2 and 1/2 gets cancelled
		double erms = Math.sqrt(Ew/tArray.length);
		
		// return system label matrix
		return erms;
	}
	
	/**
	 * Calculates teh ERMS value for the given test data set using W & Lambda
	 * @param dataFile
	 * @param W
	 * @param lnlambda
	 * @return ERMS value
	 * @throws IOException
	 */
	public double calculate(String dataFile, Matrix W, double lnlambda) throws IOException {

		// Get the data values - Read the file
		BufferedReader buffer = new BufferedReader(new FileReader(dataFile));

		// Create separate X, Y point sets
		String xPoints = "", tPoints = "";

		// Tokenize the (x, y) points - useful to find number of points
		for(String str; (str = buffer.readLine())!= null; ) {
			StringTokenizer st = new StringTokenizer(str, " ");
			xPoints += st.nextToken() + ", ";
			tPoints += st.nextToken() + ", ";
		}

		// Populate the x, t values into arrays
		StringTokenizer stX = new StringTokenizer(xPoints, ", ");
		StringTokenizer stT = new StringTokenizer(tPoints, ", ");
		double[] xArray = new double[stX.countTokens()];
		double[] tArray = new double[stT.countTokens()];
		if(xArray.length == tArray.length) {
			int i=0;
			while(stX.hasMoreTokens()) {
				xArray[i] = Double.parseDouble(stX.nextToken());
				tArray[i] = Double.parseDouble(stT.nextToken());
				i++;
			}
			buffer.close();
		}
		else {
			System.out.println("X, Y points did not match in count!! Provide Values correctly.");
			buffer.close();
			System.exit(-1);
		}

		// matrix for X values - M x N matrix. i.e M ( x0, x1, x2, .... , xM) - N X points
		Matrix testX = new Matrix(W.getRowDimension(), xArray.length);

		// Fill the matrix testX - M x N matrix
		for(int col=0; col<testX.getColumnDimension(); col++) {
			for(int row=0; row<testX.getRowDimension(); row++) {
				testX.set(row, col, Math.pow(xArray[col], row));  
			}
		}
		
		// Get System Labels = predicted labels
		Matrix sysLabel = multiply(W.transpose(), testX);

		// Compute Erms value of the prediction
		// Find Ew
		double Ew = 0;
		for(int n=0; n<tArray.length; n++) {
			Ew += Math.pow(sysLabel.get(0, n)-tArray[n], 2);
		}
		Ew = Ew/tArray.length;	// 1/N over Ew
		
		// Find ||W||^2 = wT.w
		Matrix wTw = multiply(W.transpose(), W);
		double wTwvalue = wTw.get(0, 0);
		
		// calculate lambda from given lnlambda
		double lambda = Math.exp(lnlambda);
		
		// Erms = sqrt((Ew + (lambda * ||w||^2))/N) -- 2 and 1/2 gets cancelled
		double erms = Math.sqrt((Ew + (lambda * wTwvalue))/tArray.length);
		
		// return system label matrix
		return erms;
	}
	
	/**
	 * Multiply matrix A & B
	 * @param A
	 * @param B
	 * @return A*B matrix
	 */
	private Matrix multiply(Matrix A, Matrix B) {
		// Three loops for each multiplication. C[i][j] += A[i][k] * B[k][j]

		// Check dimensionality
		if(A.getColumnDimension() != B.getRowDimension()) {
			System.out.println("Error in dimention of the Matrix for multiplication!!");
			System.exit(-1);
		}

		// result Matrix
		Matrix C = new Matrix(A.getRowDimension(), B.getColumnDimension());

		for(int i=0; i<A.getRowDimension(); i++) {
			for(int j=0; j<B.getColumnDimension(); j++) {
				double cij = 0;
				for(int k=0; k<A.getColumnDimension(); k++) {
					// Find cij
					cij += A.get(i, k) * B.get(k, j);
				}
				
				// set cij
				C.set(i, j, cij);
			}
		}

		// return result
		return C;
	}
}
