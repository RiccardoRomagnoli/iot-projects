package serialCommunication;

import java.util.ArrayList;

import GUI.MainFrame;
import GUI.RefreshChartThread;
import GUI.Settings;
import GUI.TabbedPanel;
import jssc.SerialPortList;
import manual.ManualButtonManagment;
import southPanel.ChartData;
import southPanel.MyBarChart;

public class SerialCommunication {
	
	ManualSerialCommunication manualSerial;
	ManualButtonManagment buttons;
	SingleSerialCommunication singleSerial;
	AutomaticSerialCommunication automaticSerial;
	CommChannel channel;
	ReadMessagesFromSerial reader;
	String currentMode;
	MainFrame frame;
	ArrayList<ChartData> chart;
	MyBarChart barChart;
	RefreshChartThread refreshChart;
	int counter;
	
	public SerialCommunication() throws Exception {
		currentMode = Settings.getManualMode();
		manualSerial = new ManualSerialCommunication(this);
		buttons = manualSerial.getButtonManager();
		singleSerial = new SingleSerialCommunication(this);
		automaticSerial = new AutomaticSerialCommunication(this);
		String[] portNames = SerialPortList.getPortNames();
		channel = new SerialCommChannel(portNames[portNames.length - 1],9600);
		reader = new ReadMessagesFromSerial();
		chart = new ArrayList<ChartData>();
		barChart = new MyBarChart();
		refreshChart = new RefreshChartThread();
		counter = 0;
	}
	
	public void start() throws Exception {
		frame = new MainFrame(manualSerial, buttons, singleSerial, automaticSerial, this, barChart);
		manualSerial.getButtons().disableButton(true);
		System.out.println("Waiting Arduino for rebooting...");		
		Thread.sleep(4000);
		System.out.println("Ready.");
		singleSerial.singleSendMsg("a:" + Integer.toString(Settings.getInitialManualAngle()));
		manualSerial.getButtons().disableButton(false);
	}
	
	public void inputOutput() throws Exception {
		Thread.sleep(100);
		//refreshChart.start(this);
		reader.start(channel, this);
	}
	
	public void sendMsg(String msg) throws Exception {
		channel.sendMsg(msg);
	}
	
	public void messageArrived(String message) throws Exception {
		System.out.println(message);
		/*String[] stringhe = message.split(":");
		if(firstMessage) {
			firstMessage = false;
		}
		else if(message.equals("ricevuto")) {
			//
		} 
		else if(message.equals(Settings.getManualMode())) {
			changeMode(Settings.getManualMode());
		}
		else if(message.equals(Settings.getSingleMode())) {
			changeMode(Settings.getManualMode());
		}
		else if(message.equals(Settings.getAutoMode())) {
			changeMode(Settings.getAutoMode());
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
		}	*/
		if(message.equals("arduino:m:" + Settings.getManualMode())) {
			changeMode(Settings.getManualMode());
		}
		else if(message.equals("arduino:m:" + Settings.getSingleMode())) {
			changeMode(Settings.getManualMode());
		}
		else if(message.equals("arduino:m:" + Settings.getAutoMode())) {
			changeMode(Settings.getAutoMode());
		}
		else {
			String[] stringhe = message.split(":");
			if(stringhe.length == 2 && !(stringhe[0].contains(" ")) && !(stringhe[1].contains(" "))) {
				int angle = Integer.parseInt(stringhe[0]);
				double distance = Double.parseDouble(stringhe[1]);
				if(currentMode.equals(Settings.getManualMode())) {
					manualSerial.receiveMsg(distance, angle);
					counter++;
					if(counter == 1) {
						repaintChart();
						counter = 0;
					}
				}
				if(currentMode.equals(Settings.getSingleMode())) {
					singleSerial.receiveMsg(distance, angle);
					counter++;
					if(counter == 22) {
						repaintChart();
						counter = 0;
					}
				}
				if(currentMode.equals(Settings.getAutoMode())) {
					automaticSerial.receiveMsg(distance, angle);
					counter++;
					if(counter == 22) {
						repaintChart();
						counter = 0;
					}
				}
				addData(distance, angle);
			}
		}
		
		
	}
	
	public void changeMode(String mode) throws Exception {
		int tab = 0;
		counter = 0;
		if(mode.contentEquals(Settings.getSingleMode())) {
			tab = 1;
		}
		if(mode.contentEquals(Settings.getAutoMode())) {
			tab = 2;
		}
		if(tab == 0) {
			this.sendMsg("a:" + manualSerial.returnRotation());
		}
		this.currentMode = mode;
		TabbedPanel panel = frame.getTabbedPane();
		panel.setSelectedIndex(tab);
		clearData();
	}	
	
	private void addData(double distance, int angle) {
		ChartData buff = new ChartData(angle, distance);
		if(chart.size() == 0) {
			chart.add(buff);
		}
		else {
			for(int y = 0; y < chart.size(); y++) {
				if(chart.get(y).getAngle() == angle) {
					chart.set(y, buff);
				}
				else if(chart.get(y).getAngle() > angle) {
					chart.set(y, buff);
					break;
				}
			}
			if(!(chart.contains(buff))) {
				chart.add(buff);
			}
			refreshChart(distance, angle);
		}
	}
	
	private void clearData() {
		chart = new ArrayList<ChartData>();
		barChart.clearDataSet();
		repaintChart();
	}
	
	public void refreshChart(double distance, int angle) {
		for(int y = 0; y <= chart.size(); y++) {
			barChart.addDataToDataSet(distance, Integer.toString(angle) + "°");
		}
	}
	
	public void repaintChart() {
		barChart.getPanel().repaintChart();
	}
}
