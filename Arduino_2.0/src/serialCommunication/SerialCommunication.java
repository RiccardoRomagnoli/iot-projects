package serialCommunication;

import GUI.MainFrame;
import GUI.Settings;
import jssc.SerialPortList;
import manual.ManualButtonManagment;

public class SerialCommunication {
	
	ManualSerialCommunication serial;
	ManualButtonManagment buttons;
	SingleSerialCommunication singleSerial;
	AutomaticSerialCommunication automaticSerial;
	CommChannel channel;
	ReadMessagesFromSerial reader;
	String mode;
	
	public SerialCommunication() throws Exception {
		mode = Settings.getManualMode();
		serial = new ManualSerialCommunication(this);
		buttons = serial.getButtonManager();
		singleSerial = new SingleSerialCommunication(this);
		automaticSerial = new AutomaticSerialCommunication(this);
		String[] portNames = SerialPortList.getPortNames();
		channel = new SerialCommChannel(portNames[portNames.length - 1],9600);
	}
	
	public void start() throws Exception {
		new MainFrame(serial, buttons, singleSerial, automaticSerial);
		serial.getButtons().disableButton(true);
		System.out.println("Waiting Arduino for rebooting...");		
		Thread.sleep(4000);
		System.out.println("Ready.");
		singleSerial.singleSendMsg("a:" + Integer.toString(Settings.getInitialManualAngle()));
		serial.getButtons().disableButton(false);
	}
	
	public void inputOutput() throws Exception {
		reader = new ReadMessagesFromSerial();
		reader.run(channel, this);
	}
	
	public void sendMsg(String msg) throws Exception {
		channel.sendMsg(msg);
	}
	
	public void messageArrived(String message) {
		System.out.println(message);
	}
	
	public void changeMode(String mode) {
		this.mode = mode;
	}
}
