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
	
	
	
	public String sendMsgSpeedChange() {
		String x = Integer.toString(speedSlider.getValue());
		System.out.println("SpeedChanged. New speed is: " + x);
		this.changeDistanceLabel("2 metri");
		this.changePresenceLabel("Yes");
		this.changePositionLabel("23°");
		return Integer.toString(speedSlider.getValue());
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
}
