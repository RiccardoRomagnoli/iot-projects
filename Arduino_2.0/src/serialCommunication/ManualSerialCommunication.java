package serialCommunication;

import GUI.Settings;
import manual.DistanceLabel;
import manual.ManualButtonManagment;
import manual.ObjectPresenceLabel;
import manual.RotationGradeLabel;

public class ManualSerialCommunication {
	
	private ManualButtonManagment buttons;
	public ManualButtonManagment getButtons() {
		return buttons;
	}

	private RotationGradeLabel rotationGradeLabel;
	private DistanceLabel distanceLabel;
	private ObjectPresenceLabel presenceLabel;
	int angle = Settings.getInitialManualAngle();
	SerialCommunication serial;
	
	public ManualSerialCommunication(SerialCommunication serial) {
		buttons = new ManualButtonManagment(this);
		this.serial = serial;
	}
	
	public int returnRotation() {
		int x;
		x = Integer.parseInt(rotationGradeLabel.getText().replace("°", ""));
		return  x;
	}
	
	public void sendMsgManualDx() throws Exception {
		angle += Settings.getManualAngleDelta();
		if(angle > 180) {
			angle -= Settings.getManualAngleDelta();
		}
		else {
		rotationGradeLabel.setText(Integer.toString(angle) + "°");
		serial.sendMsg("a:" + Integer.toString(angle));
		buttons.disableButton(true);
		Thread.sleep(500);
		buttons.disableButton(false);
		}
	}
	
	public void sendMsgManualSx() throws Exception {
		angle -= Settings.getManualAngleDelta();
		if(angle < 0) {
			angle += Settings.getManualAngleDelta();
		}
		else {
		rotationGradeLabel.setText(Integer.toString(angle) + "°");
		serial.sendMsg("a:" + Integer.toString(angle));
		buttons.disableButton(true);
		Thread.sleep(200);
		buttons.disableButton(false);
		}
	}
	
	public void changeDegreeLabel(String degree) {
		rotationGradeLabel.setText(degree);
		rotationGradeLabel.repaint();
	}
	
	public void changeDistanceLabel(String distance) {
		distanceLabel.setText(distance);
		distanceLabel.repaint();
	}
	
	public void changeObjectPresenceLabel(String presence) {
		presenceLabel.setText(presence);
		presenceLabel.repaint();
	}
	
	public ManualButtonManagment getButtonManager() {
		return buttons;
	}

	public void setRotationGradeLabel(RotationGradeLabel rotationGradeLabel) {
		this.rotationGradeLabel = rotationGradeLabel;
	}

	public void setDistanceLabel(DistanceLabel distanceLabel) {
		this.distanceLabel = distanceLabel;
	}

	public void setPresenceLabel(ObjectPresenceLabel presenceLabel) {
		this.presenceLabel = presenceLabel;
	}	
	
	public void receiveMsg(double distance, int angle) {
		distanceLabel.setText(Double.toString(distance));
		distanceLabel.repaint();
		if(distance != 0) {
			presenceLabel.setText("Yes");
		}
		else {
			presenceLabel.setText("No");
		}
		presenceLabel.repaint();
	}
}
