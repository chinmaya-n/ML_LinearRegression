I have done this project using Eclipse IDE.
Added "Weka.jar" into known Libraries for the project

5.(a) (b)
Use DataDisplay.java to plot the graphs for given dataset. 
args = list of locations of data sets (.dat files) to plot.

5.(c)
Use LinearRegressionWithoutRegularization.java to plot the graphs for M vs ERMS
args -> args[0] = Training data file ; args[1] = Test Data file
Plots graph for M = 0 to 9

	1)  args[0] = train.dat
		args[1] = test.dat
		"Result: Plots graph for M vs ERMS" -> M_vs_ERMS_NoReg.r
		
	2) 	args[0] = train_valid.dat
		args[1] = test.dat
		train_valid.dat is the aggregate of training and validation data sets into a single file
		"Result: Plots graph for M vs ERMS" -> M_vs_ERMS_NoReg.r

Use LinearRegressionWithRegularization.java to plot the graphs for ln lambda vs ERMS.
For M = 9. 

	3)  Find Best lambda by testing on validation data & training on training data
		args -> args[0] = train.dat
				args[1] = valid.dat
		"Result: Plots graph for ln Lambda vs ERMS" -> Lambda_vs_ERMS_WithReg.r
		
		Report ERMS Values for the following with the best lambda obtained from the previous graph.
		i.e. here (for given data) ln lambda = -50;
		Use EvaluateModel.java to get the ERMS values
		args ->	args[0] = Training .dat file
				args[1] = Tesing .dat file
				args[2] = ln Lambda ( = -50)
		
		a) Train on the training data, test on the test data
				args[0] = train.dat
				args[1] = test.dat
				args[2] = -50
			"Result: Output pasted to file: ERMS values for 5-3.txt"
			
		b) Train on the training data + validation data, test on the test data
				args[0] = train_valid.dat
				args[1] = test.dat
				args[2] = -50		
			"Result: Output pasted to file: ERMS values for 5-3.txt"
		