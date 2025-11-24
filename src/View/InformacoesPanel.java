package View;

import javax.swing.*;

import Controller.GameController;
import Observer.ObservadorIF;
import Observer.ObservadoIF;

import java.awt.*;
import java.util.ArrayList;

import Model.Api;

@SuppressWarnings("serial")
public class InformacoesPanel extends JPanel implements ObservadorIF 
{
    private GameController controller;
    
    private String categoriaAtual = "Peão"; 
    private String itemAtual = "—";
    
    private double dinheiroPeao;
    private String corPeao = "";
    private int posicaoPeao;
    private ArrayList<String> listaPropriedadesPeao;
    private boolean peaoEstaNaPrisao;
    private boolean temCartaSaida;
    
    private String donoPropriedade;
    private double aluguelPropriedade;
    private int qtdCasasPropriedade;
    private boolean temHotelPropriedade;
    
    private String donoCompanhia;
    private int taxaCompanhia;

    public InformacoesPanel() 
    {
        setBackground(Color.LIGHT_GRAY);
        setOpaque(true);
    }

    public void setController(GameController controller) 
    {
        this.controller = controller;

        if(controller != null) {
            controller.registra(this);
        }
    }

    private void atualizarDadosLocais(ObservadoIF o) 
    {
        if (o == null) return;

        if ("Peão".equals(categoriaAtual)) 
        {
            dinheiroPeao = o.getDinheiroPeao(itemAtual);
            corPeao = o.getCorPeao(itemAtual);
            posicaoPeao = o.getPosicaoPeao(itemAtual);
            listaPropriedadesPeao = o.getPropriedadesPeao(itemAtual);
            peaoEstaNaPrisao = o.isPeaoPreso(itemAtual);
            temCartaSaida = o.temCartaSaidaLivre(itemAtual);
        }
        else if ("Propriedade".equals(categoriaAtual)) 
        {
            donoPropriedade = o.getDonoPropriedade(itemAtual);
            aluguelPropriedade = o.getValorAluguel(itemAtual);
            qtdCasasPropriedade = o.getQtdCasas(itemAtual);
            temHotelPropriedade = o.getTemHotel(itemAtual);
        }
        else if ("Companhia".equals(categoriaAtual)) 
        {
            donoCompanhia = o.getDonoCompanhia(itemAtual);
            taxaCompanhia = o.getTaxaCompanhia(itemAtual);
        }
    }

    @Override
    public void notify(ObservadoIF o) 
    {
        atualizarDadosLocais(o);
        repaint();
    }
    
    public void setFiltros(String categoria, String item) 
    {
        this.categoriaAtual = categoria;
        this.itemAtual = item;
        
        atualizarDadosLocais(Api.getInstance());
        
        this.repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        int w = getWidth();
        int h = getHeight();

        g2.setColor(new Color(230, 230, 230));
        g2.fillRect(0, 0, w, h);
        g2.setColor(Color.GRAY);
        g2.drawRect(0, 0, w - 1, h - 1);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        
        String textoItem = (itemAtual == null) ? "—" : itemAtual;
        g2.drawString("Detalhes de:", 10, 25);
        g2.setFont(new Font("Arial", Font.BOLD, 13)); 
        g2.drawString(textoItem, 10, 45);

        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        int y = 75;
        int lineHeight = 20;

        try 
        {
            if ("Peão".equals(categoriaAtual)) 
            {
                g2.drawString(String.format("Saldo: R$ %.2f", dinheiroPeao), 10, y);
                y += lineHeight;
                
                g2.drawString("Cor: " + (corPeao == null ? "" : corPeao), 10, y);
                y += lineHeight;
                
                g2.drawString("Posição: " + posicaoPeao, 10, y);
                y += lineHeight;
                
                if (peaoEstaNaPrisao) 
                {
                    g2.setColor(Color.RED);
                    g2.drawString("STATUS: PRESO!", 10, y);
                    g2.setColor(Color.BLACK); // Volta para preto
                    y += lineHeight;
                }
                
                String textoCarta = temCartaSaida ? "Sim" : "Não";
                
                if (temCartaSaida) g2.setColor(new Color(0, 100, 0));
                else g2.setColor(Color.BLACK);
                
                g2.drawString("Carta Saída Livre: " + textoCarta, 10, y);
                y += lineHeight;

                g2.setColor(Color.BLACK);
                g2.drawString("Propriedades:", 10, y);
                y += lineHeight;
                
                if (listaPropriedadesPeao != null) 
                {
                    g2.setFont(new Font("Arial", Font.ITALIC, 11));
                    for (String prop : listaPropriedadesPeao) 
                    {
                        if (y > h - 10) break; 
                        g2.drawString("- " + prop, 15, y);
                        y += lineHeight;
                    }
                }
            } 
            else if ("Propriedade".equals(categoriaAtual)) 
            {
                g2.drawString("Dono: " + donoPropriedade, 10, y);
                y += lineHeight;
                
                g2.drawString(String.format("Aluguel: R$ %.2f", aluguelPropriedade), 10, y);
                y += lineHeight;
                
                g2.drawString("Casas: " + qtdCasasPropriedade, 10, y);
                y += lineHeight;
                
                String infoHotel = temHotelPropriedade ? "Sim" : "Não";
                g2.drawString("Hotel: " + infoHotel, 10, y);
            }
            else if ("Companhia".equals(categoriaAtual)) 
            {
                g2.drawString("Dono: " + donoCompanhia, 10, y);
                y += lineHeight;
                
                g2.drawString("Taxa Multiplicadora: " + taxaCompanhia + "x", 10, y); 
            }
        } 
        catch (Exception e) 
        {
            g2.setColor(Color.RED);
            g2.drawString("Selecione um item...", 10, y);
        }

        g2.dispose();
    }
}
