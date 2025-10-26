package View;

import javax.swing.*;
import java.awt.*;

public class TabuleiroPanel extends JPanel{
	Image img;
	
	public TabuleiroPanel(Image i)
	{
		img = i;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
	}
}
