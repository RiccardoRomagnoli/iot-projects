package automaticEastPanel;

import java.awt.FlowLayout;
import java.awt.LayoutManager;
import javax.swing.JPanel;

import serialCommunication.AutomaticSerialCommunication;

@SuppressWarnings("serial")
public class AutomaticEastPanel extends JPanel{
	
	public AutomaticEastPanel(AutomaticSerialCommunication serial) {
		this.setLayout((LayoutManager) new FlowLayout(FlowLayout.LEFT));
		this.add(new AutomaticSlider(serial));
	}
}
