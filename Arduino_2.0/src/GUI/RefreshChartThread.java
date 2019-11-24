package GUI;

import serialCommunication.SerialCommunication;

public class RefreshChartThread extends Thread{
	
	public RefreshChartThread() {
		
	}
	
	public void start(SerialCommunication serial) throws Exception {
		while(true) {
		Thread.sleep(3000);
		serial.repaintChart();
		System.out.println("RIDISEGNA");
		}
	}
}
