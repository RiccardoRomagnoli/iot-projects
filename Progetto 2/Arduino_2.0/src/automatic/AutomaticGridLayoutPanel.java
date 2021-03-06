package automatic;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import manual.DistanceLabel;
import manual.MyGridBagConstraints;
import manual.MyStaticLabel;
import serialCommunication.AutomaticSerialCommunication;
import single.RadarPositionLabel;

@SuppressWarnings("serial")
public class AutomaticGridLayoutPanel extends JPanel{
	
	public AutomaticGridLayoutPanel(AutomaticSerialCommunication serial) {
		this.setLayout(new GridBagLayout());
		MyGridBagConstraints c = new MyGridBagConstraints();
		//Prima riga (ANGOLO RADAR)
		c.anchor = GridBagConstraints.CENTER;
		this.add(new MyStaticLabel("Radar position:"));
		
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 1;
		this.add(new AutomaticRadarPositionLabel(serial), c);
		
		//Seconda riga(DISTANZA)
		c.gridy = 1;
		c.gridx = 0;
		c.anchor = GridBagConstraints.CENTER;
		this.add(new MyStaticLabel("Distance:"), c);
		
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 1;
		this.add(new AutomaticDistanceLabel(serial), c);
		
		//Terza riga ()
		c.gridy=2;
		c.gridx = 0;
		c.anchor = GridBagConstraints.CENTER;
		this.add(new MyStaticLabel("Allarm:"), c);
		
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 1;
		this.add(new AllarmLabel(serial), c);
	}
}
