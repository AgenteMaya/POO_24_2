package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import Model.Peao;
@SuppressWarnings("serial")
public class TabuleiroPanel extends JPanel {
    Image imgTabuleiro; 
    Image imgCartaAtual = null; 

    private Point[] mapaPosicoes;
    private HashMap<String, Image> imagensPeoes;

    public TabuleiroPanel(Image i, HashMap<String, Image> imagens) {
        this.imgTabuleiro = i;
        this.imagensPeoes = imagens;
        
        inicializaMapaPosicoes();
    }

    private void inicializaMapaPosicoes() 
	{
        int x = 0;
        int y = 0;

        for (int i = 0; i < 40; i++) {
            
            // Lado Inferior (Índices 0 a 10)
            // Do "Ponto de Partida" (10,0) até a "Prisão" (0,0)
            if (i >= 0 && i <= 10) {
                x = 10 - i;
                y = 0;
            } 
            // Lado Esquerdo (Índices 11 a 20)
            // Da "Prisão" (0,0) [exclusivo] até "Parada Livre" (0,10)
            else if (i > 10 && i <= 20) {
                x = 0;
                y = i - 10;
            } 
            // Lado Superior (Índices 21 a 30)
            // De "Parada Livre" (0,10) [exclusivo] até "Vá para a Prisão" (10,10)
            else if (i > 20 && i <= 30) {
                x = i - 20;
                y = 10;
            } 
            // Lado Direito (Índices 31 a 39)
            // De "Vá para a Prisão" (10,10) [exclusivo] de volta ao "Ponto de Partida"
            else if (i > 30 && i < 40) { // ou i <= 39
                x = 10;
                y = 40 - i; // (ex: i=31 -> y=9; i=39 -> y=1)
            }

            mapaPosicoes[i] = new Point(x, y);
        }
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
        ArrayList<Peao> listaJogadores = new ArrayList<>();
		
		Peao jogador1 = new Peao(1); 
        jogador1.setNome("Alice");
        jogador1.setCor("Vermelho");
        jogador1.setDinheiro(4000); 
   

        Peao jogador2 = new Peao(2);
        jogador2.setNome("Roberto");
        jogador2.setCor("Azul");
        jogador2.setDinheiro(4000);

   
        Peao jogador3 = new Peao(3);
        jogador3.setNome("Carla");
        jogador3.setCor("Verde");
        jogador3.setDinheiro(4000);

        
        listaJogadores.add(jogador1);
        listaJogadores.add(jogador2);
        listaJogadores.add(jogador3);
        
        
        for (Peao p : listaJogadores) {
            int casaAtual = 5; 
            String corPeao = p.getCor();     
            
            Point posBase = mapaPosicoes[casaAtual];
            
            Image imgPeao = imagensPeoes.get(corPeao);

            if (posBase != null && imgPeao != null) {
                int offset = listaJogadores.indexOf(p) * 6; 
                g.drawImage(imgPeao, posBase.x + offset, posBase.y + offset, this);
            }
        }

        
        if (imgCartaAtual != null) 
        {
        	g.drawImage(imgCartaAtual, 750, 500, null);
        }
    }
}