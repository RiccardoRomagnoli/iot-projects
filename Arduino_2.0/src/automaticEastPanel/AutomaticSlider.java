package automaticEastPanel;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import GUI.Settings;
import serialCommunication.AutomaticSerialCommunication;

@SuppressWarnings("serial")
public class AutomaticSlider extends JSlider{
	
	public AutomaticSlider(AutomaticSerialCommunication serial) {
		serial.setSlider(this);
		
		this.setOrientation(JSlider.VERTICAL);
		int value = (Settings.getRefreshTimeMax() - Settings.getRefreshTimeMin())/2;
		this.setValue(value);
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
		              serial.sendMsgSpeedChange();
		          }
		      }
		    });
	}
	
	
}