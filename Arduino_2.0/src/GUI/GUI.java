package GUI;

import serialCommunication.SerialCommunication;

public class GUI {
	
	public static void main(String[] args) throws Exception {
		SerialCommunication arduino = new SerialCommunication();
		arduino.start();
		arduino.inputOutput();
	}
}
