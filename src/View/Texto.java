package Model;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Texto extends JPanel{
	
	String m_texto;
	Texto()
	{
		
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.BLUE); 
		g2d.setFont(new Font("Arial", Font.BOLD, 12)); 
		
		FontMetrics metrics = g2d.getFontMetrics();
		int textoLargura = metrics.stringWidth(this.m_texto);
		
		
		int textoX = (getWidth() - textoLargura) / 2;
		int textoY = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
		
		g2d.drawString(this.m_texto, textoX, textoY);
	}
	
	public void setTexto(String texto) 
	{
		this.m_texto = texto;
	}

}
