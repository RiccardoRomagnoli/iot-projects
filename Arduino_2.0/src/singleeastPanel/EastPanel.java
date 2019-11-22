package singleeastPanel;

import java.awt.FlowLayout;
import java.awt.LayoutManager;
import javax.swing.JPanel;

import serialCommunication.ManualSerialCommunication;
import serialCommunication.SingleSerialCommunication;

@SuppressWarnings("serial")
public class EastPanel extends JPanel{
	
	public EastPanel(SingleSerialCommunication serial) {
		this.setLayout((LayoutManager) new FlowLayout(FlowLayout.LEFT));
		this.add(new MySlider(serial));
	}
}
