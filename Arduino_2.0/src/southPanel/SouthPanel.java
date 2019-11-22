package southPanel;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SouthPanel extends JPanel{
	
	public SouthPanel() {
		MyBarChart barChart = new MyBarChart();
		barChart.addDataToDataSet(2.03, "15°");
		barChart.addDataToDataSet(3.08, "20°");
		barChart.addDataToDataSet(4.5, "30°");
		barChart.addDataToDataSet(7.2, "18°");
		barChart.addDataToDataSet(9.8, "106°");
		//barChart.clearDataSet();
		this.add(new JLabel(new ImageIcon(barChart.getBarChartImage())));
	}
}
