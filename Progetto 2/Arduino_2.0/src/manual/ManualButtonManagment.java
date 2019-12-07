package manual;

import serialCommunication.ManualSerialCommunication;

public class ManualButtonManagment {
	
	private static ButtonDx buttonOnDx;
	private static ButtonSx buttonOnSx;
	
	
	public ManualButtonManagment(ManualSerialCommunication serial) {
		buttonOnDx = new ButtonDx(serial);
		buttonOnSx = new ButtonSx(serial);
	}
	
	public void disableButton(boolean disable) {
		if(disable) {
			buttonOnDx.disableButton();
			buttonOnSx.disableButton();
		}
		if(!disable) {
			buttonOnDx.setEnabled(true);
			buttonOnSx.setEnabled(true);
		}
	}
	
	public ButtonDx getButtonDx() {
		return buttonOnDx;
	}
	
	public ButtonSx getButtonSx() {
		return buttonOnSx;
	}
}
