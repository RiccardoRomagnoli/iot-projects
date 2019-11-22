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

	public String sendMsgSpeedChange() {
		System.out.print(slider.getValue());
		this.changeAllarmLabel("YES");
		this.changeDistanceLabel("2 metri");
		this.changePositionLabel("4�");
		return Integer.toString(slider.getValue());
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
