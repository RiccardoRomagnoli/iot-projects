package automatic;

import manual.MyLabel;
import serialCommunication.AutomaticSerialCommunication;

public class AutomaticRadarPositionLabel extends MyLabel{
	
	public AutomaticRadarPositionLabel(AutomaticSerialCommunication serial) {
		serial.setPositionLabel(this);
		this.setText("-");
	}
}
