package GUI;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
			SingleSerialCommunication singleSerial, AutomaticSerialCommunication automaticSerial, 
			MainFrame frame) {

		this.addTab("Manual", new ManualPanel(buttons, serial));
		this.addTab("Single", new SinglePanel(singleSerial));
		this.addTab("Auto", new AutomaticPanel(automaticSerial));

		
		this.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	            try {
					singleSerial.singleSendMsg("m:" + frame.getSelectedTab());
					/*if(frame.getSelectedTab() == Settings.getManualMode()) {
						singleSerial.singleSendMsg("a:" + Integer.toString(Settings.getInitialManualAngle()));
					}*/
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	        }
	    });	
	}
}
