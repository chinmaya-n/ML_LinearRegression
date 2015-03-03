package LinReg;
import weka.core.matrix.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;
import java.util.StringTokenizer;

/**
 * Finds W vector i.e weights / coefficients
 * @author - cn262114
 */
public class FindW {

	// Data Arrays
	private double[] xArray;
	private double[] tArray;
	private int M;
	private String dataFile;

	/**
	 * Constructor for FindW 
	 * @param M
	 * @param trainingDataFile
	 */
	public FindW(int M, String trainingDataFile) {
		this.M = M;
		this.dataFile = trainingDataFile;
	}
	
	/**
	 * Calculates the W matrix without regularization parameter & returns it 
	 * @param args
	 * @throws IOException 
	 */
	public Matrix compute() throws IOException {

		// Get the data values - Read the file
		BufferedReader buffer = new BufferedReader(new FileReader(dataFile));
		
		// Create separate X, Y point sets
		String xPoints = "", tPoints = "";

		// Tokenize the (x, y) points - useful to find no of elements in each
		for(String str; (str = buffer.readLine())!= null; ) {
			StringTokenizer st = new StringTokenizer(str, " ");
			xPoints += st.nextToken() + ", ";
			tPoints += st.nextToken() + ", ";
		}

		// Populate the x, t values into arrays
		StringTokenizer stX = new StringTokenizer(xPoints, ", ");
		StringTokenizer stT = new StringTokenizer(tPoints, ", ");
		xArray = new double[stX.countTokens()];
		tArray = new double[stT.countTokens()];
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

		// Find the Matrix A
		Matrix A = findA();

		// Find the Matrix T
		Matrix T = findT();

		// Find W from A inverse & T
		Matrix W = multiply(A.inverse(), T);

		/*		
			System.out.println("Printing Matrix W");
			for(int row=0; row<W.getRowDimension(); row++) {
				for(int col=0; col<W.getColumnDimension(); col++){
					System.out.print(W.get(row, col) + " ");
				}
				System.out.println();
			}
		 */

		// Return W matrix
		return W;
		
	}

	/**
	 * Calculates the W matrix with regularization parameter & returns it 
	 * @param args
	 * @throws IOException 
	 */
	public Matrix compute(int lnLambda) throws IOException {

		// Get the data values - Read the file
		BufferedReader buffer = new BufferedReader(new FileReader(dataFile));
		
		// Create separate X, Y point sets
		String xPoints = "", tPoints = "";

		// Tokenize the (x, y) points - useful to find no of elements in each
		for(String str; (str = buffer.readLine())!= null; ) {
			StringTokenizer st = new StringTokenizer(str, " ");
			xPoints += st.nextToken() + ", ";
			tPoints += st.nextToken() + ", ";
		}

		// Populate the x, t values into arrays
		StringTokenizer stX = new StringTokenizer(xPoints, ", ");
		StringTokenizer stT = new StringTokenizer(tPoints, ", ");
		xArray = new double[stX.countTokens()];
		tArray = new double[stT.countTokens()];
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

		// Find phi matrix
		Matrix phi = findPhi();
		
		// Find T matrix
		Matrix T = new Matrix(tArray, tArray.length);
		
		// Find lambda * N * I
		Matrix lambdaNI = (Matrix.identity(M+1, M+1)).times(xArray.length * Math.exp(lnLambda));

		// intermediate C = (lambdaNI + (phi transpose * phi)) inverse
		Matrix C = (multiply(phi.transpose(), phi).plus(lambdaNI)).inverse();

		// Find W = C * phi transpose * T
		Matrix W = multiply(C, multiply(phi.transpose(), T));

		return W;
		
	}
	
	/**
	 * Multiply matrix A & B
	 * @param A
	 * @param B
	 * @return A*B matrix
	 */
	private Matrix multiply(Matrix A, Matrix B) {
		// Three loops for each C[i][j] += A[i][k] * B[k][j]

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

	/**
	 * Find Matrix A
	 * @return Matrix A
	 */
	private Matrix findA() {

		// M+1 = no of coefficients for polynomial i.e W
		// M = order of polynomial 
		// Create matrix A with dimensions dim x dim
		Matrix A = new Matrix(M+1, M+1);

		// Compute only for upper triangle of the Matrix A.
		for(int i=0; i<M+1; i++) {
			for(int j=i; j<M+1; j++) {
				double aij = computeAij(i, j);
				A.set(i, j, aij); // ij = 0, 0 => 1,1 position 
				A.set(j, i, aij);
			}
		}

		// return Matrix A
		return A;
	}

	/**
	 * Calculate Values for matrix A
	 * @param i
	 * @param j
	 * @return Aij value for Matrix A
	 */
	private double computeAij(int i, int j) {

		// result
		double result = 0;
		// power i+j
		int exponent = i+j;

		// Sum of the 'power of exponent' of each array value
		for(int index=0; index < xArray.length; index++) {
			result += Math.pow(xArray[index], exponent);
		}

		// return the result
		return result;
	}

	/**
	 * Find Matrix T
	 * @return Matrix T
	 */
	private Matrix findT() {

		// Matrix T
		Matrix T = new Matrix(M+1, 1);

		// Populate the matrix
		for(int i=0; i<M+1; i++) {
			T.set(i, 0, computeTi(i));
		}

		// return matrix
		return T;
	}

	/**
	 * Calculate Values for matrix T
	 * @param i
	 * @return Ti value for Matrix T
	 */
	private double computeTi(int i) {
		// result
		double result=0;

		// Find Ti
		for(int index=0; index < tArray.length; index++) {
			result += tArray[index] * Math.pow(xArray[index], i);
		}

		// return result
		return result;
	}
	
	/**
	 * Find Matrix Phi ( used for regularization )
	 * @return matrix Phi
	 */
	private Matrix findPhi() {
		// Create new Matrix Phi
		Matrix phi = new Matrix(xArray.length, M+1);
		
		// Fill the matrix - Dimensions: N x (M+1)
		// M+1 feature vectors: w0 to wM
		for(int i=0; i<xArray.length; i++) {
			for(int j=0; j<M+1; j++) {
				phi.set(i, j, Math.pow(xArray[i], j));
			}
		}
		
		// return matrix phi
		return phi;
	}

}
