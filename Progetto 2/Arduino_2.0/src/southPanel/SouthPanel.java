package southPanel;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SouthPanel extends JPanel{
	
	MyBarChart barChart;
	JLabel image;
	
	public SouthPanel(MyBarChart barChart) {
		image = new JLabel(new ImageIcon(barChart.getBarChartImage()));
		this.barChart = barChart;
		//barChart.clearDataSet();
		barChart.setPanel(this);
		this.add(image);
	}
	
	public void addDataToChart(int angle, double distance) {
		barChart.addDataToDataSet(distance, Integer.toString(angle) + "°");	
	}
	
	public void repaintChart() {
		this.remove(image);
		image = new JLabel(new ImageIcon(barChart.getBarChartImage()));;
		this.add(image);
	}
}
