package single;
import manual.MyLabel;
import serialCommunication.SingleSerialCommunication;

@SuppressWarnings("serial")
public class RadarPositionLabel extends MyLabel{
	
	public RadarPositionLabel(SingleSerialCommunication serial) {
		serial.setPositionLabel(this);
		this.setText("-");
	}
}
