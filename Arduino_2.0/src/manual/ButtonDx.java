package manual;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import serialCommunication.ManualSerialCommunication;

@SuppressWarnings("serial")
public class ButtonDx extends JButton{
	public ButtonDx(ManualSerialCommunication serial) {
		this.setText("=>");
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					serial.sendMsgManualDx();
				} catch (Exception e1) {

					e1.printStackTrace();
				}	
			}
		});
	}	
	
	public void disableButton() {
		this.setEnabled(false);
	}
}
