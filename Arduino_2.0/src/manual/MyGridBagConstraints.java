package manual;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class MyGridBagConstraints extends GridBagConstraints{
	
	public MyGridBagConstraints() {
		this.gridx = 0;
		this.gridy = 0;
		this.gridwidth = 1;
		this.gridheight = 1;
		this.weightx = 1;
		this.weighty = 1;
		this.anchor = GridBagConstraints.EAST;
		this.fill = GridBagConstraints.NONE;
		this.insets = new Insets(0,0,0,0);
		this.ipadx = 2;
		this.ipady = 2;
	}
}
