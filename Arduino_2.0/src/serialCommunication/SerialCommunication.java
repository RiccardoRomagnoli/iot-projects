package serialCommunication;

import GUI.MainFrame;
import GUI.Settings;
import GUI.TabbedPanel;
import jssc.SerialPortList;
import manual.ManualButtonManagment;

public class SerialCommunication {
	
	ManualSerialCommunication manualSerial;
	ManualButtonManagment buttons;
	SingleSerialCommunication singleSerial;
	AutomaticSerialCommunication automaticSerial;
	CommChannel channel;
	ReadMessagesFromSerial reader;
	String currentMode;
	boolean firstMessage = true;
	MainFrame frame;
	
	public SerialCommunication() throws Exception {
		currentMode = Settings.getManualMode();
		manualSerial = new ManualSerialCommunication(this);
		buttons = manualSerial.getButtonManager();
		singleSerial = new SingleSerialCommunication(this);
		automaticSerial = new AutomaticSerialCommunication(this);
		String[] portNames = SerialPortList.getPortNames();
		channel = new SerialCommChannel(portNames[portNames.length - 1],9600);
		reader = new ReadMessagesFromSerial();
	}
	
	public void start() throws Exception {
		frame = new MainFrame(manualSerial, buttons, singleSerial, automaticSerial, this);
		manualSerial.getButtons().disableButton(true);
		System.out.println("Waiting Arduino for rebooting...");		
		Thread.sleep(4000);
		System.out.println("Ready.");
		singleSerial.singleSendMsg("a:" + Integer.toString(Settings.getInitialManualAngle()));
		manualSerial.getButtons().disableButton(false);
	}
	
	public void inputOutput() throws Exception {
		Thread.sleep(100);
		reader.start(channel, this);
	}
	
	public void sendMsg(String msg) throws Exception {
		channel.sendMsg(msg);
	}
	
	public void messageArrived(String message) {
		System.out.println(message);
		String[] stringhe = message.split(":");
		if(firstMessage) {
			firstMessage = false;
		}
		else if(message.equals("ricevuto")) {
			//
		} 
		else if(stringhe[0].equals("m")) {
			changeMode(stringhe[stringhe.length - 1]);
		}
		else {
			int angle = Integer.parseInt(stringhe[0]);
			double distance = Double.parseDouble(stringhe[1]);
			if(currentMode.equals(Settings.getManualMode())) {
				manualSerial.receiveMsg(distance, angle);
			}
			if(currentMode.equals(Settings.getSingleMode())) {
				singleSerial.receiveMsg(distance, angle);
			}
			if(currentMode.equals(Settings.getAutoMode())) {
				automaticSerial.receiveMsg(distance, angle);
			}
		}	
	}
	
	public void changeMode(String mode) {
		int tab = 0;
		if(mode.contentEquals(Settings.getSingleMode())) {
			tab = 1;
		}
		if(mode.contentEquals(Settings.getAutoMode())) {
			tab = 2;
		}
		this.currentMode = mode;
		TabbedPanel panel = frame.getTabbedPane();
		panel.setSelectedIndex(tab);
	}
}
