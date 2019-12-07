package singleeastPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import GUI.Settings;
import serialCommunication.SingleSerialCommunication;

@SuppressWarnings("serial")
public class MySlider extends JSlider{
	
	public MySlider(SingleSerialCommunication serial) {
		serial.setSpeedSlider(this);
		
		this.setOrientation(JSlider.VERTICAL);
		this.setValue(2);
		this.setMinimum(Settings.getRefreshTimeMin());
		this.setMaximum(Settings.getRefreshTimeMax());
		this.setMajorTickSpacing(Settings.getSpeedTickSpacing());
		this.setSnapToTicks(true);
		this.setPaintTicks(true);
		this.setPaintLabels(true);
		this.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent e) {
		    	  JSlider source = (JSlider)e.getSource();
		          if (!source.getValueIsAdjusting()) {
		              try {
						serial.sendMsgSpeedChange();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
		          }
		      }
		    });
	}
	
	
}
