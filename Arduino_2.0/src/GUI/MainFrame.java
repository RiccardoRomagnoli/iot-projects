package GUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import manual.ManualButtonManagment;
import serialCommunication.AutomaticSerialCommunication;
import serialCommunication.ManualSerialCommunication;
import serialCommunication.SerialCommunication;
import serialCommunication.SingleSerialCommunication;
import southPanel.SouthPanel;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	
	TabbedPanel tabbedPanel;
	SerialCommunication serial;
	
	public MainFrame(ManualSerialCommunication manualSerial, ManualButtonManagment buttons, 
			SingleSerialCommunication singleSerial, AutomaticSerialCommunication automaticSerial,
			SerialCommunication serial) {
		tabbedPanel = new TabbedPanel(manualSerial, buttons, singleSerial, automaticSerial, this);
		this.setTitle("Arduino 2.0");
		this.setSize(Settings.getMainFrameWidth(), Settings.getMainFrameHeight());
		this.setResizable(false);
		this.add(tabbedPanel, BorderLayout.CENTER);
		this.add(new SouthPanel(), BorderLayout.SOUTH);
		windowPosition(this);		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.serial = serial;
	}
	
	static void windowPosition(JFrame main) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        
        int w = main.getSize().width;
        int h = main.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;

        
        main.setLocation(x, y);
	}
	
	public String getSelectedTab() {
		serial.changeMode(tabbedPanel.getSelectedComponent().getName());
		return tabbedPanel.getSelectedComponent().getName();
	}
	
	public TabbedPanel getTabbedPane() {
		return tabbedPanel;
	}
}
