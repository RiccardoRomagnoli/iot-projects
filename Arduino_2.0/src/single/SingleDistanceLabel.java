package single;

import manual.MyLabel;
import serialCommunication.SingleSerialCommunication;

public class SingleDistanceLabel extends MyLabel{
	
	public SingleDistanceLabel(SingleSerialCommunication serial) {
		serial.setDistanceLabel(this);
		this.setText("-");
	}
}
