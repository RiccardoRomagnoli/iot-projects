package automatic;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import southPanel.SouthPanel;

public class AutomaticPanel extends JPanel{
	
	public AutomaticPanel() {
		this.setLayout(new BorderLayout());
		this.add(new AutomaticGridLayoutPanel(), BorderLayout.CENTER);
		this.add(new SouthPanel(), BorderLayout.EAST);
	}
}
