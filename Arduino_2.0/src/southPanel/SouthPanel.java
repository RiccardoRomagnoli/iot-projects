package southPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SouthPanel extends JPanel{
	
	public SouthPanel() {
		this.setLayout((LayoutManager) new FlowLayout(FlowLayout.LEFT));
		this.add(new MySlider());
	}
}
