package southPanel;

import java.awt.Image;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import GUI.Settings;

public class MyBarChart {
	
	private static DefaultCategoryDataset objDataset;
	
	public MyBarChart() {
		objDataset = new DefaultCategoryDataset();
	}
	
	public Image getBarChartImage() {
		JFreeChart objChart = ChartFactory.createBarChart(
			       "",     //Chart title
			    "Servo position",     //Domain axis label
			    "Distance(cm)",         //Range axis label
			    objDataset,         //Chart Data 
			    PlotOrientation.VERTICAL, // orientation
			    true,             // include legend?
			    true,             // include tooltips?
			    false             // include URLs?
			);
		
		return objChart.createBufferedImage(Settings.getMainFrameWidth(), 210);
	}
	
	public void addDataToDataSet(double value, String legend) {
		objDataset.addValue(value, legend, "");
	}
	
	public void clearDataSet(){
		objDataset.clear();
	}
}
