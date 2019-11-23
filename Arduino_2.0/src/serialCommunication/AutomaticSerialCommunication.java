package serialCommunication;

import automatic.AllarmLabel;
import automatic.AutomaticDistanceLabel;
import automatic.AutomaticRadarPositionLabel;
import automaticEastPanel.AutomaticSlider;

public class AutomaticSerialCommunication {
	private AutomaticSlider slider;
	private AutomaticRadarPositionLabel positionLabel;
	private AutomaticDistanceLabel distanceLabel;
	private AllarmLabel allarmLabel;
	SerialCommunication serial;
	
	public AutomaticSerialCommunication(SerialCommunication serial) {
		this.serial = serial;
	}

	public void sendMsgSpeedChange() throws Exception {
		serial.sendMsg("s:" + Integer.toString(slider.getValue()));
	}	
	
	public void setSlider(AutomaticSlider slider) {
		this.slider = slider;
	}
	
	public void changeAllarmLabel(String allarm) {
		allarmLabel.setText(allarm);
		allarmLabel.repaint();
	}
	
	public void changePositionLabel(String position) {
		positionLabel.setText(position);
		positionLabel.repaint();
	}
	
	public void changeDistanceLabel(String distance) {
		distanceLabel.setText(distance);
		distanceLabel.repaint();
	}

	public void setPositionLabel(AutomaticRadarPositionLabel positionLabel) {
		this.positionLabel = positionLabel;
	}

	public void setDistanceLabel(AutomaticDistanceLabel distanceLabel) {
		this.distanceLabel = distanceLabel;
	}

	public void setAllarmLabel(AllarmLabel allarmLabel) {
		this.allarmLabel = allarmLabel;
	}
}
