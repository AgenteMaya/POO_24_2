package View;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class CartaPanel  extends JPanel{
	Image img;
	
	public CartaPanel(Image i)
	{
		img = i;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
	}
}
