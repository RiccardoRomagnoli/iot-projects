package GUI;

import manual.ManualButtonManagment;
import serialCommunication.AutomaticSerialCommunication;
import serialCommunication.ManualSerialCommunication;
import serialCommunication.SingleSerialCommunication;

public class GUI {
	
	public static void main(String[] args) {
		
		ManualSerialCommunication serial = new ManualSerialCommunication();
		ManualButtonManagment buttons = serial.getButtonManager();
		SingleSerialCommunication singleSerial = new SingleSerialCommunication();
		AutomaticSerialCommunication automaticSerial = new AutomaticSerialCommunication();
		
		@SuppressWarnings("unused")
		MainFrame GUI = new MainFrame(serial, buttons, singleSerial, automaticSerial);
		
		
	}
}
