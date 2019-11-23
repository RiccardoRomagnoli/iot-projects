package southPanel;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SouthPanel extends JPanel{
	
	MyBarChart barChart;
	
	public SouthPanel() {
		barChart = new MyBarChart();
		//barChart.clearDataSet();
		this.add(new JLabel(new ImageIcon(barChart.getBarChartImage())));
	}
	
	public void addDataToChart(int angle, double distance) {
		barChart.addDataToDataSet(distance, Integer.toString(angle) + "°");	
	}
}
