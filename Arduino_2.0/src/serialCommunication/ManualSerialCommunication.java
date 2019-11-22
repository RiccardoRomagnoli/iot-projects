package serialCommunication;

import manual.DistanceLabel;
import manual.ManualButtonManagment;
import manual.ObjectPresenceLabel;
import manual.RotationGradeLabel;

public class ManualSerialCommunication {
	
	private ManualButtonManagment buttons;
	private RotationGradeLabel rotationGradeLabel;
	private DistanceLabel distanceLabel;
	private ObjectPresenceLabel presenceLabel;
	
	public ManualSerialCommunication() {
		buttons = new ManualButtonManagment(this);
	}
	
	public void sendMsgManualDx() {
		System.out.println("Tasto Dx");
		buttons.disableButton();
		this.changeDegreeLabel("13°");
		this.changeDistanceLabel("2 metri");
		this.changeObjectPresenceLabel("No");
	}
	
	public void sendMsgManualSx() {
		System.out.println("Tasto Sx");
		buttons.disableButton();
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
	
}
