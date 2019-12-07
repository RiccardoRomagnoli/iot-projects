package manual;

import serialCommunication.ManualSerialCommunication;

@SuppressWarnings("serial")
public class ObjectPresenceLabel extends MyLabel{
	public ObjectPresenceLabel(ManualSerialCommunication serial) {
		serial.setPresenceLabel(this);
		this.setText("-");
	}
}
