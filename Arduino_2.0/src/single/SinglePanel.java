package single;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import southPanel.SouthPanel;

public class SinglePanel extends JPanel{
	
	public SinglePanel() {
		this.setLayout(new BorderLayout());
		this.add(new SingleNorthPanel(), BorderLayout.CENTER);
		this.add(new SouthPanel(), BorderLayout.EAST);
	}
}
