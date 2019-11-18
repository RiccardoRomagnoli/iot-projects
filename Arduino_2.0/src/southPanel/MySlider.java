package southPanel;
import javax.swing.JSlider;

import GUI.Settings;

public class MySlider extends JSlider{
	
	public MySlider() {
		this.setOrientation(JSlider.VERTICAL);
		int value = (Settings.getRefreshTimeMax() - Settings.getRefreshTimeMin())/2;
		this.setValue(value);
		this.setMinimum(Settings.getRefreshTimeMin());
		this.setMaximum(Settings.getRefreshTimeMax());
		this.setMajorTickSpacing(1);
		this.setPaintTicks(true);
		this.setPaintLabels(true);
	}
}
