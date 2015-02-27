package LinReg;
import weka.core.matrix.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;
import java.util.StringTokenizer;

/**
 * Finds W i.e weights / coefficients
 * @author  Chinmaya R Naguri - cn262114
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
	 * Calculates the W matrix & returns it 
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
		Matrix A = findA(xArray, M);

		// Find the Matrix T
		Matrix T = findT(tArray, xArray, M);

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
	 * @param xArray
	 * @param m
	 * @return Matrix A
	 */
	private Matrix findA(double[] xArray, int m) {

		// M+1 = no of coefficients for polynomial i.e W
		// M = order of polynomial 
		// Create matrix A with dimensions dim x dim
		int dim = m+1;
		Matrix A = new Matrix(dim, dim);

		// Compute only for upper triangle of the Matrix A.
		for(int i=0; i<dim; i++) {
			for(int j=i; j<dim; j++) {
				double aij = computeAij(xArray, i, j);
				A.set(i, j, aij); // ij = 0, 0 => 1,1 position 
				A.set(j, i, aij);
			}
		}

		// return Matrix A
		return A;
	}

	/**
	 * Calculate Values for matrix A
	 * @param xArray
	 * @param i
	 * @param j
	 * @return Aij value for Matrix A
	 */
	private double computeAij(double[] xArray, int i, int j) {

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
	 * @param tArray
	 * @param xArray
	 * @param m
	 * @return Matrix T
	 */
	private Matrix findT(double[] tArray, double[] xArray, int m) {

		// dimention
		int dim = m+1;
		// Matrix T
		Matrix T = new Matrix(dim, 1);

		// Populate the matrix
		for(int i=0; i<dim; i++) {
			T.set(i, 0, computeTi(tArray, xArray, i));
		}

		// return matrix
		return T;
	}

	/**
	 * Calculate Values for matrix T
	 * @param tArray
	 * @param xArray
	 * @param i
	 * @return Ti value for Matrix T
	 */
	private double computeTi(double[] tArray, double[] xArray, int i) {
		// result
		double result=0;

		// Find Ti
		for(int index=0; index < tArray.length; index++) {
			result += tArray[index] * Math.pow(xArray[index], i);
		}

		// return result
		return result;
	}

}
