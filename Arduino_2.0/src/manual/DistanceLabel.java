package manual;

import serialCommunication.ManualSerialCommunication;

@SuppressWarnings("serial")
public class DistanceLabel extends MyLabel{
	
	public DistanceLabel(ManualSerialCommunication serial) {
		serial.setDistanceLabel(this);
		this.setText("-");
	}
}
