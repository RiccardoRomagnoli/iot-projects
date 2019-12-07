package automatic;

import manual.MyLabel;
import serialCommunication.AutomaticSerialCommunication;

@SuppressWarnings("serial")
public class AllarmLabel extends MyLabel{
	
	public AllarmLabel(AutomaticSerialCommunication serial) {
		serial.setAllarmLabel(this);
		this.setText("No");
	}
}
