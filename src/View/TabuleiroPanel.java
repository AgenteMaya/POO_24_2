package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import Controller.GameController;

@SuppressWarnings("serial")
public class TabuleiroPanel extends JPanel 
{
    Image imgTabuleiro; 
    Image imgCartaAtual = null; 

    private HashMap<String, Image> imagensPeoes;
    
    private LinkedHashMap<String, Integer> posicoesPeoes; // relaciona a cor (String) com a posição de um respectivo peão
    
    private GameController controller;
    
    private enum ViewState 
    {
        NORMAL, 
        EXIBINDO_CARTA,
        AGUARDANDO_DECISAO_COMPRA,
        AGUARDANDO_DECISAO_CONSTRUCAO
    }
    private ViewState currentState = ViewState.NORMAL;
    
    private String terrenoOfertadoNome;
    private int terrenoOfertadoValor;
    
    private String propriedadeOfertadaNome;
    
    private String mensagemTemporaria = null;
    
    private Rectangle btnDialogoFechar;
    private Rectangle btnDialogoComprar;
    private Rectangle btnDialogoNaoComprar;
    private Rectangle btnDialogoConstruirCasa;
    private Rectangle btnDialogoConstruirHotel;
    private Rectangle btnDialogoCancelar;

    public TabuleiroPanel (Image i, HashMap<String, Image> imagens, LinkedHashMap<String, Integer> listaPeoes) 
    {
        this.imgTabuleiro = i;
		this.imagensPeoes = imagens;
		this.posicoesPeoes = listaPeoes;
                
        this.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                if (controller != null) 
                {
                    handleMouseClick(e.getPoint());
                }
            }
        });
    }
    
    public void setController(GameController controller) {
        this.controller = controller;
    }
    
    public void setListaPeoes(LinkedHashMap<String, Integer> peoes) {
        this.posicoesPeoes = peoes;
    }

    public void mostrarMensagem(String msg) {
        this.mensagemTemporaria = msg;
        this.currentState = ViewState.NORMAL;
        repaint();
    }
    
    public void mostrarCarta(Image imgCarta) {
        this.imgCartaAtual = imgCarta;
        this.currentState = ViewState.EXIBINDO_CARTA;
        repaint();
    }
    
    public void mostrarOpcaoCompra(String nome, int valor) {
        this.terrenoOfertadoNome = nome;
        this.terrenoOfertadoValor = valor;
        this.currentState = ViewState.AGUARDANDO_DECISAO_COMPRA;
        repaint();
    }
    
    public void mostrarOpcaoConstruir(String nome) {
        this.propriedadeOfertadaNome = nome;
        this.currentState = ViewState.AGUARDANDO_DECISAO_CONSTRUCAO;
        repaint();
    }

    private void handleMouseClick(Point p) {
        switch (currentState) {
            case EXIBINDO_CARTA:
                if (btnDialogoFechar != null && btnDialogoFechar.contains(p)) {
                    currentState = ViewState.NORMAL;
                    imgCartaAtual = null; 
                    repaint();
                }
                break;
                
            case AGUARDANDO_DECISAO_COMPRA:
                if (btnDialogoComprar != null && btnDialogoComprar.contains(p)) {
                    currentState = ViewState.NORMAL;
                    controller.usuarioDecidiuComprar(); 
                } else if (btnDialogoNaoComprar != null && btnDialogoNaoComprar.contains(p)) {
                    currentState = ViewState.NORMAL;
                    controller.usuarioDecidiuNaoComprar(); 
                }
                break;
                
            case AGUARDANDO_DECISAO_CONSTRUCAO:
                if (btnDialogoConstruirCasa != null && btnDialogoConstruirCasa.contains(p)) {
                    currentState = ViewState.NORMAL;
                    controller.usuarioDecidiuConstruir(true); 
                } else if (btnDialogoConstruirHotel != null && btnDialogoConstruirHotel.contains(p)) {
                    currentState = ViewState.NORMAL;
                    controller.usuarioDecidiuConstruir(false); 
                } else if (btnDialogoCancelar != null && btnDialogoCancelar.contains(p)) {
                    currentState = ViewState.NORMAL;
                    repaint();
                }
                break;
                
            case NORMAL:
                if (mensagemTemporaria != null) {
                    mensagemTemporaria = null;
                    repaint();
                }
                break;
        }
    }
    
    private Point calculaPosicaoPeao(int casa, int numPeao)
    {
    	int posX = 0;
    	int posY = 0;
    	int aux = 0;
    	int aux2 = 0;
    	int aux3 = 0;
    	int ajusteX = 128; 
    	
    	if (casa % 10 == 0)
    	{
    		if (casa == 0 || casa == 10) posY = 595;
    		else posY = 10;
    		
    		if (casa == 0 || casa == 30) posX = 875 - ajusteX;
    		else posX = 290 - ajusteX;
    		
    		if (numPeao < 3)
            {
    		  posX += 35 * numPeao;
            }
            else
            {
              posX += 35 * (numPeao - 3);
              posY += 45;
            } 
    	}
    	else if ((casa > 10 && casa < 20) || (casa > 30 && casa < 40)) 
    	{
    		if (casa > 10 && casa < 20) 
    		{
    			posX = 290 - ajusteX;
    			aux = 11;
    			aux2 = -53;
    			posY = 551;
    			aux3 = -1;
    			
    		}
    		else if (casa > 30 && casa < 40) 
    		{
    			posX = 875 - ajusteX;
    			aux = 31;
    			aux2 = 53;
    			posY = 110;
    			aux3 = 1;
    		}
    			
    		if (numPeao < 3)
            {
    		  posX += 35 * numPeao;
    		  posY += + aux2 * (casa - aux) + aux3 * (casa - aux) * 2 - 28;
            }
            else
            {
              posX += 35 * (numPeao - 3);
              posY += aux2 * (casa - aux) + aux3 * (casa - aux) * 2;
            } 
    	}
    	else if ((casa > 0 && casa < 10) || (casa > 20 && casa < 30))
    	{
    		if (casa > 0 && casa < 10)
    		{
    			posY = 595;
    			posX = 822 - ajusteX;
    			aux = 1;
    			aux2 = -55;
    		}
    		else
    		{
    			posY = 10;
    			posX = 385 - ajusteX;
    			aux = 21;
    			aux2 = 55;
    		}
    		
    		if (numPeao < 2)
            {
    		  posX += 30 * numPeao + aux2 * (casa - aux);
            }
            else if (numPeao < 4)
            {
              posX += 30 * (numPeao - 2) + aux2 * (casa - aux);
              posY += 23;
            } 
            else
            {
              posX += 30 * (numPeao - 4) + aux2 * (casa - aux);
              posY += 46;
            }
    		
    	}
    		
    	return new Point(posX, posY);
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int w = getWidth();
        int h = getHeight();

        int boardDrawSize = Math.min(w, h);
        int boardX = (w - boardDrawSize) / 2;
        int boardY = (h - boardDrawSize) / 2;

        g2d.drawImage(imgTabuleiro, boardX, boardY, boardDrawSize, boardDrawSize, this);

        int idxJog = 0; 
        for (Map.Entry<String, Integer> entry : posicoesPeoes.entrySet()) {
        	String corPeao = entry.getKey();
            Integer posicao = entry.getValue();
            
            int casa = posicao;

            Image pin = imagensPeoes.get(corPeao);
            if (pin == null) continue;
       
            Point C = calculaPosicaoPeao(casa, idxJog);
            g.drawImage(pin, C.x, C.y, this);
            
            idxJog++;
        }
        
        switch (currentState) {
	        case EXIBINDO_CARTA:
	            desenharDialogoFundo(g2d);
	            desenharDialogoCarta(g2d, imgCartaAtual);
	            break;
	        case AGUARDANDO_DECISAO_COMPRA:
	            desenharDialogoFundo(g2d);
	            desenharDialogoCompra(g2d);
	            break;
	        case AGUARDANDO_DECISAO_CONSTRUCAO:
	            desenharDialogoFundo(g2d);
	            desenharDialogoConstruir(g2d);
	            break;
	        case NORMAL:
	            desenharMensagemTemporaria(g2d);
	            break;
	    }
	    
        g2d.dispose();
    }
    
    private void desenharDialogoFundo(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    private void desenharMensagemTemporaria(Graphics2D g2d) { // alterar para a mensagem desaparecer automaticamente
        if (mensagemTemporaria != null) {
            g2d.setColor(new Color(0, 0, 0, 180));
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            int strWidth = g2d.getFontMetrics().stringWidth(mensagemTemporaria);
            int strHeight = g2d.getFontMetrics().getHeight();
            int x = (getWidth() - strWidth) / 2;
            int y = getHeight() - 100;
            
            g2d.fillRect(x - 10, y - strHeight, strWidth + 20, strHeight + 10);
            g2d.setColor(Color.WHITE);
            g2d.drawString(mensagemTemporaria, x, y);
        }
    }

    private void desenharDialogoCarta(Graphics2D g2d, Image imgCarta) {
        if (imgCarta == null) return;
        
        int x = (getWidth() - imgCarta.getWidth(null)) / 2;
        int y = (getHeight() - imgCarta.getHeight(null)) / 2;
        
        g2d.drawImage(imgCarta, x, y, null);
        
        String texto = "Fechar";
        int btnX = x;
        int btnY = y + imgCarta.getHeight(null) + 10;
        int btnW = imgCarta.getWidth(null);
        int btnH = 40;
        
        btnDialogoFechar = desenharBotaoVirtual(g2d, texto, btnX, btnY, btnW, btnH);
    }
    
    private void desenharDialogoCompra(Graphics2D g2d) {
        int dialogW = 400;
        int dialogH = 200;
        int dialogX = (getWidth() - dialogW) / 2;
        int dialogY = (getHeight() - dialogH) / 2;

        g2d.setColor(Color.WHITE);
        g2d.fillRect(dialogX, dialogY, dialogW, dialogH);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(dialogX, dialogY, dialogW, dialogH);
        
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        String txt1 = "Deseja comprar este terreno?";
        String txt2 = "Nome: " + terrenoOfertadoNome;
        String txt3 = "Preço: R$ " + terrenoOfertadoValor;
        
        g2d.drawString(txt1, dialogX + 20, dialogY + 40);
        g2d.drawString(txt2, dialogX + 20, dialogY + 70);
        g2d.drawString(txt3, dialogX + 20, dialogY + 100);
        
        int btnW = (dialogW / 2) - 30;
        int btnH = 40;
        int btnY = dialogY + dialogH - btnH - 20;
        
        btnDialogoComprar = desenharBotaoVirtual(g2d, "Comprar", dialogX + 20, btnY, btnW, btnH);
        btnDialogoNaoComprar = desenharBotaoVirtual(g2d, "Não Comprar", dialogX + 20 + btnW + 20, btnY, btnW, btnH);
    }
    
    private void desenharDialogoConstruir(Graphics2D g2d) {
        int dialogW = 400;
        int dialogH = 200;
        int dialogX = (getWidth() - dialogW) / 2;
        int dialogY = (getHeight() - dialogH) / 2;

        g2d.setColor(Color.WHITE);
        g2d.fillRect(dialogX, dialogY, dialogW, dialogH);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(dialogX, dialogY, dialogW, dialogH);
        
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        String txt1 = "Construir em " + propriedadeOfertadaNome;
        g2d.drawString(txt1, dialogX + 20, dialogY + 40);
        
        int btnW = (dialogW / 3) - 20;
        int btnH = 40;
        int btnY = dialogY + dialogH - btnH - 20;
        
        btnDialogoConstruirCasa = desenharBotaoVirtual(g2d, "Casa", dialogX + 10, btnY, btnW, btnH);
        btnDialogoConstruirHotel = desenharBotaoVirtual(g2d, "Hotel", dialogX + 20 + btnW, btnY, btnW, btnH);
        btnDialogoCancelar = desenharBotaoVirtual(g2d, "Cancelar", dialogX + 30 + (2*btnW), btnY, btnW, btnH);
    }
    
    private Rectangle desenharBotaoVirtual(Graphics2D g2d, String texto, int x, int y, int w, int h) {
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(x, y, w, h);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, w, h);
        
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        int strWidth = g2d.getFontMetrics().stringWidth(texto);
        g2d.drawString(texto, x + (w - strWidth) / 2, y + (h / 2) + 5);
        
        return new Rectangle(x, y, w, h);
    }
}
