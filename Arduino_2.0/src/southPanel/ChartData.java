package southPanel;

public class ChartData {
	private int angle;
	private double distance;
	
	public ChartData(int angle, double distance) {
		this.angle = angle;
		this.distance = distance;
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
}
