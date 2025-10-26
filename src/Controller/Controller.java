package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

// Controller só terá acesso a algumas classes da View e do Model
import Model.*;
import View.*;

@SuppressWarnings("serial")
class Controller extends JFrame{
	
	public final int LARG_DEFAULT = 1280;
	public final int ALT_DEFAULT = 800;
	
	JPanel painelMenu;
	
	Controller()
	{
		janelaInicial();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) // main permanecerá aqui ou inserimos ela em outro "pacote" só para main?
	{
		Controller c = new Controller();
		
		c.setTitle("Banco Imobiliário");
		c.setVisible(true);
	}
	
	
	void janelaInicial() 
	{
		setSize(210, 210);
		painelMenu = new JPanel();
		painelMenu.setLayout(null);
		
		Botao botaoIniciar = new Botao("Iniciar");
		botaoIniciar.setBounds(10, 10, 200, 50);
		
		
		botaoIniciar.adicionaListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        System.out.println("AÇÃO: Iniciando o jogo...");
		        janelaNumJogadores();
		    }
		});
		
		Botao botaoRetomar = new Botao("Retomar");
		botaoRetomar.setBounds(10, 70, 200, 50);
		
		botaoRetomar.adicionaListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        System.out.println("AÇÃO: Retornando a jogo salvo...");
		        //janelaTabuleiro();
		    }
		});
		
		
		painelMenu.add(botaoIniciar);
		painelMenu.add(botaoRetomar);
		
		getContentPane().add(painelMenu);
	}
	
	void janelaNumJogadores() 
	{
		getContentPane().removeAll();
		setSize(500, 500);
		
		JPanel painelJogadores = new JPanel();
        painelJogadores.setLayout(null); 
        
		//escanear o número de jogadores
		Texto texto_num_jogadores = new Texto();
		texto_num_jogadores.setTexto("Quantidade de jodores (entre 3-6)?");
		texto_num_jogadores.setBounds(10, 10, 250, 30);
		
		JTextField campoNumJogadores = new JTextField();
        campoNumJogadores.setBounds(10, 50, 100, 30);
        
        Botao btnConfirmar = new Botao("Confirmar");
        btnConfirmar.setBounds(120, 50, 100, 30);
        
        btnConfirmar.adicionaListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String num = campoNumJogadores.getText();
                int num_jogadores = Integer.parseInt(num);
                
                System.out.println("Jogadores: " + num_jogadores);
                if(num_jogadores>=3 && num_jogadores >=6) 
                {
                	System.out.printf("Números de jogadores %d", num_jogadores);
                }
            }
        });

        painelJogadores.add(texto_num_jogadores);
        painelJogadores.add(campoNumJogadores);
        painelJogadores.add(btnConfirmar);

        getContentPane().add(painelJogadores);
        
        revalidate();
        repaint();
    }
	
}