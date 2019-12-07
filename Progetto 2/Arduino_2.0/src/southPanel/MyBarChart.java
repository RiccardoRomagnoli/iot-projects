package southPanel;

import java.awt.Image;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import GUI.Settings;

public class MyBarChart {
	
	private static DefaultCategoryDataset objDataset;
	
	private SouthPanel panel;
	
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
	
	public void addDataToDataSet(double distance, String legend) {
		objDataset.addValue(distance, legend, "");
	}
	
	public void clearDataSet(){
		objDataset.clear();
	}
	
	public void refresh() {
		
	}

	public void setPanel(SouthPanel panel) {
		this.panel = panel;
	}
	
	public SouthPanel getPanel() {
		return panel;
	}
}
