package manual;

import GUI.Settings;
import serialCommunication.ManualSerialCommunication;

@SuppressWarnings("serial")
public class RotationGradeLabel extends MyLabel{
	
	public RotationGradeLabel(ManualSerialCommunication serial) {
		serial.setRotationGradeLabel(this);
		this.setText(Integer.toString(Settings.getInitialManualAngle()) + "°");
	}
}
