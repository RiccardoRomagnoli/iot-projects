package single;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import GUI.Settings;
import serialCommunication.SingleSerialCommunication;
import singleeastPanel.EastPanel;

@SuppressWarnings("serial")
public class SinglePanel extends JPanel{
	
	public SinglePanel(SingleSerialCommunication serial) {
		this.setLayout(new BorderLayout());
		this.add(new SingleNorthPanel(serial), BorderLayout.CENTER);
		this.add(new EastPanel(serial), BorderLayout.EAST);
		this.setName(Settings.getSingleMode());
	}
}
