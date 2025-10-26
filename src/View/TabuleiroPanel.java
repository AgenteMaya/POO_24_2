package View;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class TabuleiroPanel extends JPanel {
    Image imgTabuleiro; 
    Image imgCartaAtual = null; 
    
    public TabuleiroPanel(Image i) {
        this.imgTabuleiro = i;
    }
    
    public void setCartaParaExibir(Image carta) {
        this.imgCartaAtual = carta;
        repaint(); 
    }

    public void esconderCarta() {
        this.imgCartaAtual = null;
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawImage(imgTabuleiro, 0, 0, null);
        
        // Lógica para desenhar os peões (você fará isso no futuro)
        // g.drawImage(imgPeao1, x, y, null);
        
        if (imgCartaAtual != null) 
        {
        	g.drawImage(imgCartaAtual, 750, 500, null);
        }
    }
}