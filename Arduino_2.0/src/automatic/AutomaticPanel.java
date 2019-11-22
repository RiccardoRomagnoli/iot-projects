package automatic;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import automaticEastPanel.AutomaticEastPanel;
import serialCommunication.AutomaticSerialCommunication;


@SuppressWarnings("serial")
public class AutomaticPanel extends JPanel{
	
	public AutomaticPanel(AutomaticSerialCommunication serial) {
		this.setLayout(new BorderLayout());
		this.add(new AutomaticGridLayoutPanel(serial), BorderLayout.CENTER);
		this.add(new AutomaticEastPanel(serial), BorderLayout.EAST);
	}
}
