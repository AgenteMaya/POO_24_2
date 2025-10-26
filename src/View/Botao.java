package View;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

//a View pode importar classes do Controller, se necessário

@SuppressWarnings("serial")
public class Botao extends JPanel implements MouseListener
{

	String nome;
	private ActionListener acaoCallback;
	
	public Botao(String nome)
	{
		this.nome = nome;
		
		this.acaoCallback = null;
		addMouseListener(this);
		
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		Rectangle2D rt = new Rectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1);
		g2d.draw(rt);
		
		g2d.setColor(Color.BLUE); 
		g2d.setFont(new Font("Arial", Font.BOLD, 12)); 
		
		FontMetrics metrics = g2d.getFontMetrics();
		int textoLargura = metrics.stringWidth(this.nome);
		
		
		int textoX = (getWidth() - textoLargura) / 2;
		int textoY = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
		
		g2d.drawString(this.nome, textoX, textoY);
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		
		System.out.printf("Botão %s foi clicado!", nome);
		acionaAcao();
	
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