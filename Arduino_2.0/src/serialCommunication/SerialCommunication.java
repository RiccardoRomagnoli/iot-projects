package serialCommunication;

import GUI.MainFrame;
import manual.ManualButtonManagment;

public class SerialCommunication {
	
	ManualSerialCommunication serial;
	ManualButtonManagment buttons;
	SingleSerialCommunication singleSerial;
	AutomaticSerialCommunication automaticSerial;
	
	public SerialCommunication() {
		serial = new ManualSerialCommunication();
		buttons = serial.getButtonManager();
		singleSerial = new SingleSerialCommunication();
		automaticSerial = new AutomaticSerialCommunication();
	}
	
	public void start() {
		new MainFrame(serial, buttons, singleSerial, automaticSerial);
	}
	
	public void inputOutput() {
		
	}
}
