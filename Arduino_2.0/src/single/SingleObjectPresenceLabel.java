package single;

import manual.MyLabel;
import serialCommunication.SingleSerialCommunication;
public class SingleObjectPresenceLabel extends MyLabel{
	public SingleObjectPresenceLabel(SingleSerialCommunication serial) {
		serial.setPresenceLabel(this);
		this.setText("-");
	}
}
