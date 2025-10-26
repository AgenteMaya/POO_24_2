package View;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class botao extends JPanel implements MouseListener
{
	double leftX;
	double topY;
	double larg;
	double alt;
	String nome;
	
	private ActionListener acaoCallback;
	
	public botao(double x, double y, double w, double h, String nome)
	{
		this.leftX = x;
		this.topY = y;
		this.larg = w;
		this.alt = h;
		this.nome = nome;
		
		this.acaoCallback = null;
		addMouseListener(this);
		
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		Rectangle2D rt=new Rectangle2D.Double(leftX,topY,larg,alt);
		g2d.draw(rt);
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		int mouseX = e.getX();
		int mouseY = e.getY();
		
		
		if((mouseX >= leftX && mouseX <= leftX + larg) 
				&& (mouseY >= topY && mouseY >= topY + alt)) 
		{
			System.out.printf("Botão %s foi clicado!", nome);
			acionaAcao();
		}
	}
	
	
	public void adicionaListener(ActionListener callback)
	{
		this.acaoCallback = callback;
	}
	
	public void removeListener() 
	{
		this.acaoCallback = null;
	}
	
	protected void acionaAcao() 
	{
		if (this.acaoCallback != null) {
					
			ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, this.nome);
			this.acaoCallback.actionPerformed(e);
		}
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}

