package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import Controller.GameController;
import Model.Peao;
import Model.Propriedade;
import Model.Terreno;

@SuppressWarnings("serial")
public class TabuleiroPanel extends JPanel {
    Image imgTabuleiro; 
    Image imgCartaAtual = null; 

    private Point[] mapaPosicoes;
    private HashMap<String, Image> imagensPeoes;
    
    private GameController controller;
    private ArrayList<Peao> listaPeoes;
    
    private enum ViewState {
        NORMAL, 
        EXIBINDO_CARTA,
        AGUARDANDO_DECISAO_COMPRA,
        AGUARDANDO_DECISAO_CONSTRUCAO
    }
    private ViewState currentState = ViewState.NORMAL;
    
    private Terreno terrenoOfertado;
    private Propriedade propriedadeOfertada;
    private String mensagemTemporaria = null;
    
    private Rectangle btnDialogoFechar;
    private Rectangle btnDialogoComprar;
    private Rectangle btnDialogoNaoComprar;
    private Rectangle btnDialogoConstruirCasa;
    private Rectangle btnDialogoConstruirHotel;
    private Rectangle btnDialogoCancelar;

    public TabuleiroPanel(Image i, HashMap<String, Image> imagens) {
        this.imgTabuleiro = i;
        this.imagensPeoes = imagens;
        this.listaPeoes = new ArrayList<>();
        
        inicializaMapaPosicoes();
        
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (controller != null) {
                    handleMouseClick(e.getPoint());
                }
            }
        });
    }

    private void inicializaMapaPosicoes() 
	{
        mapaPosicoes = new Point[40]; 
        
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
    
    public void setController(GameController controller) {
        this.controller = controller;
    }
    
    public void setListaPeoes(ArrayList<Peao> peoes) {
        this.listaPeoes = peoes;
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
    
    public void mostrarOpcaoCompra(Terreno terreno) {
        this.terrenoOfertado = terreno;
        this.currentState = ViewState.AGUARDANDO_DECISAO_COMPRA;
        repaint();
    }
    
    public void mostrarOpcaoConstruir(Propriedade prop) {
        this.propriedadeOfertada = prop;
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

    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        // para centralizar o tabuleiro
        int w = getWidth();
        int h = getHeight();
        int boardSize = Math.min(w, h); 
        int boardX = (w - boardSize) / 2; 
        int boardY = (h - boardSize) / 2;

        g2d.drawImage(imgTabuleiro, boardX, boardY, boardSize, boardSize, null);
        
        // Lógica para desenhar os peões (você fará isso no futuro)
        // g.drawImage(imgPeao1, x, y, null);
//        ArrayList<Peao> listaJogadores = new ArrayList<>();
//		
//		Peao jogador1 = new Peao(1); 
//        jogador1.setNome("Alice");
//        jogador1.setCor("Vermelho");
//        jogador1.setDinheiro(4000); 
//   
//
//        Peao jogador2 = new Peao(2);
//        jogador2.setNome("Roberto");
//        jogador2.setCor("Azul");
//        jogador2.setDinheiro(4000);
//
//   
//        Peao jogador3 = new Peao(3);
//        jogador3.setNome("Carla");
//        jogador3.setCor("Verde");
//        jogador3.setDinheiro(4000);
//
//        
//        listaJogadores.add(jogador1);
//        listaJogadores.add(jogador2);
//        listaJogadores.add(jogador3);
//        
//        
//        for (Peao p : listaJogadores) {
//            int casaAtual = 5; 
//            String corPeao = p.getCor();     
//            
//            Point posBase = mapaPosicoes[casaAtual];
//            
//            Image imgPeao = imagensPeoes.get(corPeao);
//
//            if (posBase != null && imgPeao != null) {
//                int offset = listaJogadores.indexOf(p) * 6; 
//                g.drawImage(imgPeao, posBase.x + offset, posBase.y + offset, this);
//            }
//        }

        if (this.listaPeoes != null) {
            for (int i = 0; i < listaPeoes.size(); i++) {
                Peao p = listaPeoes.get(i);
                
                int casaAtual = p.pegaPosicaoPeao(); 
                String corPeao = p.getCor();     
                
                Point posBase = mapaPosicoes[casaAtual]; 
                Image imgPeao = imagensPeoes.get(corPeao);

                if (posBase != null && imgPeao != null) {
                    
                    double gridUnitSize = boardSize / 11.0; 
                    
                    int x = (int)(boardX + posBase.x * gridUnitSize);
                    int y = (int)(boardY + posBase.y * gridUnitSize);
                    
                    int offset = i * 8; 
                    
                    g2d.drawImage(imgPeao, x + offset, y + offset, 32, 32, null);
                }
            }
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
	    
	    g.dispose();
    }
    
    private void desenharDialogoFundo(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    private void desenharMensagemTemporaria(Graphics2D g2d) {
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
        String txt2 = "Nome: " + "NOME_DO_TERRENO"; // terrenoOfertado.getNome();
        String txt3 = "Preço: R$ " + terrenoOfertado.getValorCompra();
        
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
        String txt1 = "Construir em " + "NOME_PROPRIEDADE"; // prop.getNome()
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
