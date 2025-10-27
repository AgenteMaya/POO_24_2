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
public class TabuleiroPanel extends JPanel 
{
    Image imgTabuleiro; 
    Image imgCartaAtual = null; 

    private Point[] mapaPosicoes;
    private HashMap<String, Image> imagensPeoes;
    
    private GameController controller;
    private ArrayList<Peao> listaPeoes;
    
    private enum ViewState 
    {
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

    public TabuleiroPanel (Image i, HashMap<String, Image> imagens) 
    {
        this.imgTabuleiro = i;
        this.imagensPeoes = imagens;
        this.listaPeoes = new ArrayList<>();
                
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

    private Point[] inicializaMapaPosicoes(int boardX, int boardY, int boardSize, int cropPx) 
    {
        Point[] pos = new Point[40];

        double inner = boardSize - 2.0 * cropPx;
        double cell  = inner / 11.0; 

        for (int i = 0; i < 40; i++) {
            int gx, gy;

            if (i >= 0 && i <= 10) {           
                gx = 10 - i; gy = 10;
            } else if (i <= 20) {               
                gx = 0; gy = 20 - i;
            } else if (i <= 30) {               
                gx = i - 20; gy = 0;
            } else {                            
                gx = 10; gy = i - 30;
            }

            int cx = (int) Math.round(boardX + cropPx + (gx + 0.5) * cell);
            int cy = (int) Math.round(boardY + cropPx + (gy + 0.5) * cell);

            System.out.println("pos x - " + (cx) + "Pos y - " +  (cy));
            pos[i] = new Point(cx, cy); 
        }

        return pos;
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
        
        int boardDrawSize = Math.min(w, h);   
        int boardX = (w - boardDrawSize) / 2;
        int boardY = (h - boardDrawSize) / 2;
        g2d.drawImage(imgTabuleiro, boardX, boardY, boardDrawSize, boardDrawSize, this);

        int cropPx = 0;

        Point[] centros = inicializaMapaPosicoes(boardX, boardY, boardDrawSize, cropPx);

        int casaAtual = 0;
        Point C = centros[casaAtual];

        int idx = 0;
        Point[] offsets = { new Point(-12,-12), new Point(+12,-12), new Point(-12,+12), new Point(+12,+12) };
        for (Peao p : this.listaPeoes) 
        {
            Image pin = imagensPeoes.get(p.getCor());
            if (pin == null) continue;

            int pinW = pin.getWidth(this);
            int pinH = pin.getHeight(this);
            Point off = offsets[Math.min(idx, offsets.length - 1)];

            int drawX = C.x - pinW/2 + off.x;
            int drawY = C.y - pinH/2 + off.y;

            System.out.println("drawX - " + (drawX) + " drawY - " +  (drawY));
            g.drawImage(pin, drawX, drawY, this);
            idx++;
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
    
 // desenha todos os peões no centro da célula (11 células por lado)
    private void desenharPeoes(Graphics2D g2d, java.util.List<Peao> jogadores, int boardX, int boardY, int boardSize) {

        if (jogadores == null || jogadores.isEmpty()) return;

        // cada lado do tabuleiro é dividido em 11 células (10 casas + 1 canto)
        final double cell = boardSize / 11.0;

        // tamanho do ícone do peão relativo à célula
        final int pinSize = (int)Math.round(cell * 0.70); // ~70% da célula
        final int half    = pinSize / 2;

        for (int i = 0; i < jogadores.size(); i++) {
            Peao p = jogadores.get(i);

            int casa = p.pegaPosicaoPeao(); // 0..39
            if (casa < 0 || casa >= 40) continue;

            Point posBase = mapaPosicoes[casa]; // x,y em [0..10]
            if (posBase == null) continue;

            // seu mapa cresce "para cima"; a tela cresce "para baixo"
            // usamos o centro da célula: +0.5
            double cx = boardX + (posBase.x + 0.5) * cell;
            double cy = boardY + ((10 - posBase.y) + 0.5) * cell;

            // separa peões na mesma casa: offset em "anel"
            // (você pode trocar por deslocamentos fixos 0,8,16 se preferir)
            int ring = (i % 4);
            int offX = (ring == 1 ? 7 : ring == 3 ? -7 : 0);
            int offY = (ring == 2 ? 7 : ring == 0 ? -7 : 0);

            String corKey = normalizaCor(p.getCor());
            Image imgPeao = imagensPeoes.get(corKey);
            if (imgPeao == null) {
                System.err.println("Imagem não encontrada para a cor: " + p.getCor());
                continue;
            }

            int px = (int)Math.round(cx) - half + offX;
            int py = (int)Math.round(cy) - half + offY;

            g2d.drawImage(imgPeao, px, py, pinSize, pinSize, null);
        }
    }
    
 // normaliza para as chaves existentes do seu HashMap
    private String normalizaCor(String c) {
        if (c == null) return "";
        c = c.trim().toLowerCase();
        return switch (c) {
            case "vermelho" -> "Vermelho";
            case "azul"     -> "Azul";
            case "laranja"  -> "Laranja";
            case "amarelo"  -> "Amarelo";
            case "magenta"  -> "Magenta";
            case "cinza"    -> "Cinza";
            default -> c;
        };
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
