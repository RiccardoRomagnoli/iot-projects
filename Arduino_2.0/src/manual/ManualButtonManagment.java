package manual;

import serialCommunication.ManualSerialCommunication;

public class ManualButtonManagment {
	
	private static ButtonDx buttonOnDx;
	private static ButtonSx buttonOnSx;
	
	
	public ManualButtonManagment(ManualSerialCommunication serial) {
		buttonOnDx = new ButtonDx(serial);
		buttonOnSx = new ButtonSx(serial);
	}
	
	public void disableButton() {
		buttonOnDx.disableButton();
		buttonOnSx.disableButton();
	}
	
	public ButtonDx getButtonDx() {
		return buttonOnDx;
	}
	
	public ButtonSx getButtonSx() {
		return buttonOnSx;
	}
}
