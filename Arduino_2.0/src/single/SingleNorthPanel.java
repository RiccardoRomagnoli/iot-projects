package single;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

import manual.DistanceLabel;
import manual.MyGridBagConstraints;
import manual.MyStaticLabel;
import manual.ObjectPresenceLabel;
import serialCommunication.SingleSerialCommunication;

@SuppressWarnings("serial")
public class SingleNorthPanel extends JPanel{
	public SingleNorthPanel(SingleSerialCommunication serial) {
		this.setLayout(new GridBagLayout());
		MyGridBagConstraints c = new MyGridBagConstraints();
		//Prima riga (E' PRESENTE UN OGGETTO?)
		c.anchor = GridBagConstraints.CENTER;
		this.add(new MyStaticLabel("Object:"));
		
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 1;
		this.add(new SingleObjectPresenceLabel(serial), c);	
		
		//Seconda riga(DISTANZA)
		c.gridy = 1;
		c.gridx = 0;
		c.anchor = GridBagConstraints.CENTER;
		this.add(new MyStaticLabel("Distance:"), c);
		
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 1;
	    this.add(new SingleDistanceLabel(serial), c);
		
		//Terza riga (Posizione radar)
		c.gridy=2;
		c.gridx = 0;
		c.anchor = GridBagConstraints.CENTER;
		this.add(new MyStaticLabel("Radar position:"), c);
		
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 1;
		this.add(new RadarPositionLabel(serial), c);
	}
}
