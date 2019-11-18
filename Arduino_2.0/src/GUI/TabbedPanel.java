package GUI;
import javax.swing.JTabbedPane;

import automatic.AutomaticPanel;
import manual.ManualPanel;
import single.SinglePanel;

public class TabbedPanel extends JTabbedPane{
	
	public TabbedPanel() {
		this.addTab("Manual", new ManualPanel());
		this.addTab("Single", new SinglePanel());
		this.addTab("Auto", new AutomaticPanel());
	}

}
