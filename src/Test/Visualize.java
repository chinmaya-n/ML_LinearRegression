package Test;
// Imports
import weka.core.Instance;
import weka.core.Instances;
import weka.gui.visualize.Plot2D;
import weka.gui.visualize.PlotData2D;
import weka.gui.arffviewer.*;
import weka.core.converters.ConverterUtils.DataSource;
import weka.gui.explorer.VisualizePanel;
import java.io.BufferedReader;
import java.io.FileReader;

// Class
public class Visualize {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		// Read the file from the args list
		//DataSource source = new DataSource("data.arff");
		BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\cn262114\\Documents\\eclipse-workspace\\ML_A1\\src\\data.arff"));
		Instances instances = new Instances(reader);
		
		VisualizePanel graph = new VisualizePanel();
		graph.setInstances(instances);
		graph.main(args);
		
		// Plot the graph
//		PlotData2D dataPlot = new PlotData2D(instances);
//		Plot2D plot = new Plot2D();
//		plot.addPlot(dataPlot);
//		plot.paint
	}

}
