package manual;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ManualPanel extends JPanel{
	
	public ManualPanel() {
		
		
		this.setLayout(new GridBagLayout());
		MyGridBagConstraints c = new MyGridBagConstraints();
		this.add(new MyStaticLabel("Move:"), c);
		
		//prima riga (MOVE)
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		this.add(new ButtonSx(), c);
		
		c.gridx = 2;
		this.add(new RotationGradeLabel());
		
		c.gridx = 3;
		this.add(new ButtonDx(), c);
		
		//seconda riga (PRESENZA OGGETTO)
		c.gridy = 1;
		c.gridx = 0;
		c.anchor = GridBagConstraints.EAST;
		this.add(new MyStaticLabel("Object:"), c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		this.add(new ObjectPresenceLabel(), c);		
		
		//terza riga (DISTANZA)
		c.gridy=2;
		c.gridx = 0;
		c.anchor = GridBagConstraints.EAST;
		this.add(new MyStaticLabel("Distance:"), c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		this.add(new DistanceLabel(), c);
	}
}
