package manual;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

import GUI.Settings;
import serialCommunication.ManualSerialCommunication;

@SuppressWarnings("serial")
public class ManualPanel extends JPanel{
	
	ManualButtonManagment buttons;
	
	public ManualPanel(ManualButtonManagment buttons, ManualSerialCommunication serial) {
		this.setName(Settings.getManualMode());
		this.buttons = buttons;
		
		this.setLayout(new GridBagLayout());
		MyGridBagConstraints c = new MyGridBagConstraints();
		this.add(new MyStaticLabel("Move:"), c);
		
		//prima riga (MOVE)
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		this.add(buttons.getButtonSx(), c);
		
		c.gridx = 2;
		this.add(new RotationGradeLabel(serial));
		
		
		c.gridx = 3;
		this.add(buttons.getButtonDx(), c);
		
		//seconda riga (PRESENZA OGGETTO)
		c.gridy = 1;
		c.gridx = 0;
		c.anchor = GridBagConstraints.EAST;
		this.add(new MyStaticLabel("Object:"), c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		this.add(new ObjectPresenceLabel(serial), c);		
		
		//terza riga (DISTANZA)
		c.gridy=2;
		c.gridx = 0;
		c.anchor = GridBagConstraints.EAST;
		this.add(new MyStaticLabel("Distance:"), c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		this.add(new DistanceLabel(serial), c);
	}
}
