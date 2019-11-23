package serialCommunication;

import single.RadarPositionLabel;
import single.SingleDistanceLabel;
import single.SingleObjectPresenceLabel;
import singleeastPanel.MySlider;

public class SingleSerialCommunication {
	
	private MySlider speedSlider;
	private SingleObjectPresenceLabel objectPresenceLabel;
	private SingleDistanceLabel distanceLabel;
	private RadarPositionLabel positionLabel;
	SerialCommunication serial;
	
	public SingleSerialCommunication (SerialCommunication serial) {
		this.serial = serial;
	}
	
	public void sendMsgSpeedChange() throws Exception {
		serial.sendMsg("s:" + Integer.toString(speedSlider.getValue()));
	}
	
	public void changePresenceLabel(String presence) {
		objectPresenceLabel.setText(presence);
		objectPresenceLabel.repaint();
	}
	
	public void changeDistanceLabel(String distance) {
		distanceLabel.setText(distance);
		distanceLabel.repaint();
	}
	
	public void changePositionLabel(String position) {
		positionLabel.setText(position);
		positionLabel.repaint();
	}

	public void setSpeedSlider(MySlider speedSlider) {
		this.speedSlider = speedSlider;
	}

	public void setPresenceLabel(SingleObjectPresenceLabel singleObjectPresenceLabel) {
		this.objectPresenceLabel = singleObjectPresenceLabel;
	}

	public void setDistanceLabel(SingleDistanceLabel distanceLabel) {
		this.distanceLabel = distanceLabel;
	}

	public void setPositionLabel(RadarPositionLabel positionLabel) {
		this.positionLabel = positionLabel;
	}
	
	public void singleSendMsg(String msg) throws Exception {
		serial.sendMsg(msg);
	}
	
	public void changeMode(String mode) {
		serial.changeMode(mode);
	}
	
	public void receiveMsg(double distance, int angle) {
		
	}
}
