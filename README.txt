* Run LinearRegressionWithoutRegularization.java for without regularization.
* Run LinearRegressionWithRegularization.java for with regularization.

* "Arguments" : While running the each program, send 
  argument 1 - training data file [if you want to send train+valid data 
									combine both data into a testfile & make it
									as a single training data file] 
  argument 2 - test data file 
  Eg:>> java LinearRegressionWithRegularization train_valid.dat test.dat  
  		// Here train_valid.dat = training + validation data in a single file
  		// 		test.dat		= test data file

* Each program creates a .R file in the "./output/" directory 
  which is the graph for the computation
  
* DataDisplay.java is the program to read the arguments and display them in
  a graph. Outputs to "./output/" directory.
  
  arguments - files to convert from .dat to graphs
  