package automatic;

import manual.MyLabel;
import serialCommunication.AutomaticSerialCommunication;

public class AutomaticDistanceLabel extends MyLabel{
	
	public AutomaticDistanceLabel(AutomaticSerialCommunication serial) {
		serial.setDistanceLabel(this);
		this.setText("-");
	}
}
