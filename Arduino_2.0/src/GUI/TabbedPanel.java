package GUI;
import javax.swing.JTabbedPane;

import automatic.AutomaticPanel;
import manual.ManualButtonManagment;
import manual.ManualPanel;
import serialCommunication.AutomaticSerialCommunication;
import serialCommunication.ManualSerialCommunication;
import serialCommunication.SingleSerialCommunication;
import single.SinglePanel;

@SuppressWarnings("serial")
public class TabbedPanel extends JTabbedPane{
	
	public TabbedPanel(ManualSerialCommunication serial, ManualButtonManagment buttons, 
			SingleSerialCommunication singleSerial, AutomaticSerialCommunication automaticSerial) {
		this.addTab("Manual", new ManualPanel(buttons, serial));
		this.addTab("Single", new SinglePanel(singleSerial));
		this.addTab("Auto", new AutomaticPanel(automaticSerial));
	}

}
