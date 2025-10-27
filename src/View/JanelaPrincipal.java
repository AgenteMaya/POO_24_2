package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import Controller.GameController;
import Model.Peao;
import Model.Propriedade; 
import Model.Terreno; 

@SuppressWarnings("serial")
public class JanelaPrincipal extends JFrame 
{
	public int LARG_DEFAULT = 1280;
	public int ALT_DEFAULT = 750;

	private GameController controller;

	JPanel painelMenu;
	TabuleiroPanel painelTabuleiro;
	private JTextField campoNumJogadores;
	private JTextField campoNome;
	private JTextField campoCor;
	private Texto textoAviso;
	
	private HashMap<Integer, Image> imagensCartas;
	private HashMap<String, Image> imagensPeoes;

	public JanelaPrincipal() 
	{
		mostrarMenuInicial();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void setController(GameController controller) {
		this.controller = controller;
	}

	public void iniciar() {
		setTitle("Banco Imobiliário");
		setVisible(true);
	}

	public void mostrarMenuInicial() {
		setSize(240, 170);

		getContentPane().removeAll();

		painelMenu = new JPanel();
		painelMenu.setLayout(null);

		Botao botaoIniciar = new Botao("Iniciar");
		botaoIniciar.setBounds(10, 10, 200, 50);

		botaoIniciar.adicionaListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.solicitarInicioJogo();
			}
		});

		Botao botaoRetomar = new Botao("Retomar");
		botaoRetomar.setBounds(10, 70, 200, 50);

		botaoRetomar.adicionaListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.solicitarRetomadaJogo();
			}
		});

		painelMenu.add(botaoIniciar);
		painelMenu.add(botaoRetomar);

		getContentPane().add(painelMenu);
		revalidate();
		repaint();
	}

	public void mostrarTelaNumJogadores() {
		getContentPane().removeAll();
		setSize(500, 500);

		JPanel painelJogadores = new JPanel();
		painelJogadores.setLayout(null);

		Texto texto_num_jogadores = new Texto();
		texto_num_jogadores.setTexto("Quantidade de jodores (entre 3-6)?");
		texto_num_jogadores.setBounds(10, 10, 250, 30);
		
	    Integer[] qtdJogadores = { 3, 4, 5, 6 };
	    JComboBox<Integer> comboBox = new JComboBox<>(qtdJogadores);
	    comboBox.setBounds(10, 50, 100, 30);

		Botao btnConfirmar = new Botao("Confirmar");
		btnConfirmar.setBounds(120, 50, 100, 30);

		btnConfirmar.adicionaListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int num = (Integer) comboBox.getSelectedItem();
				controller.confirmarNumeroJogadores(num);
			}
		});

		painelJogadores.add(texto_num_jogadores);
		painelJogadores.add(comboBox);
		painelJogadores.add(btnConfirmar);

		getContentPane().add(painelJogadores);

		revalidate();
		repaint();
	}

	public void mostrarTelaConfigJogadores(int total_jogadores, int num_jogadores, ArrayList<String> cores) 
	{
		JPanel painelConfiguracao = new JPanel();
		painelConfiguracao.setLayout(null);
		getContentPane().removeAll();
		setSize(500, 500);

		if (num_jogadores == 0) 
		{
			controller.iniciarPartida();
		} 
		else 
		{
			Texto textoCampoNome = new Texto();
			textoCampoNome.setTexto("Nome jogador nº" + (total_jogadores - num_jogadores + 1) + " (até 8 caracteres):");
			textoCampoNome.setBounds(10, 10, 220, 30);

			campoNome = new JTextField();
			campoNome.setBounds(10, 50, 100, 30);

			Texto textoCor = new Texto();
			textoCor.setTexto("Cor do peão:");
			textoCor.setBounds(10, 90, 100, 30);

			textoAviso = new Texto();
			textoAviso.setTexto("Cor inválida!!!");
			textoAviso.setBounds(10, 150, 100, 30);
			textoAviso.setVisible(false);
			
			String[] listaCores = cores.toArray(new String[cores.size()]);
			System.out.println(listaCores);
		    JComboBox<String> comboBox = new JComboBox<>(listaCores);
		    comboBox.setBounds(10, 130, 100, 30);

			Botao btnProximo = new Botao("Próximo");
			btnProximo.setBounds(120, 180, 100, 30);

			btnProximo.adicionaListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String cor = (String) comboBox.getSelectedItem();
					controller.configurarProximoJogador(num_jogadores - 1, campoNome.getText(), cor.toLowerCase());
				}
			});

			painelConfiguracao.add(textoCampoNome);
			painelConfiguracao.add(campoNome);
			painelConfiguracao.add(textoCor);
			painelConfiguracao.add(comboBox);
			painelConfiguracao.add(btnProximo);
			painelConfiguracao.add(textoAviso);
		}

		getContentPane().add(painelConfiguracao);
		revalidate();
		repaint();
	}

	public void mostrarErroCor(boolean mostrar) {
		if (textoAviso != null) {
			textoAviso.setVisible(mostrar);
		}
	}
	
	// Arrumar!
	public void mostrarDados()
	{
	    JDialog dialogoDados = new JDialog(this, "Lançar Dados (Debug)", true); // 'true' = modal
	    dialogoDados.setSize(350, 150);
	    dialogoDados.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); 
	    dialogoDados.setLocationRelativeTo(this); 

	    JPanel painelDados = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

	    Integer[] numeros = { 1, 2, 3, 4, 5, 6 };

	    JLabel label1 = new JLabel("Dado 1:");
	    JComboBox<Integer> comboBox1 = new JComboBox<>(numeros);
	    
	    JLabel label2 = new JLabel("Dado 2:");
	    JComboBox<Integer> comboBox2 = new JComboBox<>(numeros);

	    Botao btnConfirmar = new Botao("Lançar");

	    btnConfirmar.adicionaListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            int dado1 = (Integer) comboBox1.getSelectedItem();
	            int dado2 = (Integer) comboBox2.getSelectedItem();

	            controller.usuarioLancouDados(dado1 + dado2);
	            
	            dialogoDados.dispose();
	        }
	    });

	    painelDados.add(label1);
	    painelDados.add(comboBox1);
	    painelDados.add(label2);
	    painelDados.add(comboBox2);
	    painelDados.add(btnConfirmar); 

	    dialogoDados.getContentPane().add(painelDados);
	    dialogoDados.setVisible(true);
	}

	public void mostrarTabuleiro() 
	{
		getContentPane().removeAll();
		setSize(LARG_DEFAULT, ALT_DEFAULT); 
	    setLocationRelativeTo(null); 
		getContentPane().setLayout(new BorderLayout());

		carregarImagemPeoes();
		carregarImagemCartas();
        
		Image imagemTabuleiro = carregaImagem("/tabuleiro.png");
		
		painelTabuleiro = new TabuleiroPanel(imagemTabuleiro, imagensPeoes);
		painelTabuleiro.setController(controller);
		
		painelTabuleiro.setBackground(Color.WHITE);
		getContentPane().add(painelTabuleiro, BorderLayout.CENTER);

		Botao btnLancaDados = new Botao("Próximo");
		btnLancaDados.setBounds(10, 10, 150, 30);
			
		btnLancaDados.adicionaListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarDados();
				}
		});
				
		// TODO: Você pode adicionar seus painéis de info aqui, se permitido
        // JPanel infoPanel = new JPanel();
        // infoPanel.setPreferredSize(new Dimension(LARG_DEFAULT - ALT_DEFAULT, ALT_DEFAULT)); // ex: 480x800
        // infoPanel.setBackground(Color.LIGHT_GRAY);
        // infoPanel.add(new JLabel("Informações dos Jogadores"));
        // getContentPane().add(infoPanel, BorderLayout.EAST);

		painelTabuleiro.add(btnLancaDados);
		revalidate();
		repaint();
	}

	private Image carregaImagem(String nomeArquivo) {
		Image image = null;
		URL imageUrl = getClass().getResource(nomeArquivo);

		if (imageUrl == null) {
			System.out.println("Erro: Não foi possível encontrar o recurso: " + nomeArquivo);
			System.exit(1);
		}

		try {
			image = ImageIO.read(imageUrl);
		} catch (IOException e) {
			System.out.println("Erro ao carregar a imagem: " + e.getMessage());
			System.exit(1);
		}

		return image;
	}

	private void carregarImagemPeoes() 
	{
		imagensPeoes = new HashMap<>();
        
		imagensPeoes.put("Vermelho", carregaImagem("/pinos/pin0.png"));
		imagensPeoes.put("Azul", carregaImagem("/pinos/pin1.png"));
		imagensPeoes.put("Laranja", carregaImagem("/pinos/pin2.png"));
		imagensPeoes.put("Amarelo", carregaImagem("/pinos/pin3.png"));
		imagensPeoes.put("Magenta", carregaImagem("/pinos/pin4.png"));
		imagensPeoes.put("Cinza", carregaImagem("/pinos/pin5.png"));

	}
	
	private void carregarImagemCartas() 
	{
		imagensCartas = new HashMap<>();
		
		// arquivos das imagens
		imagensCartas.put(1, carregaImagem("/sorteReves/chance1.png"));
		imagensCartas.put(2, carregaImagem("/sorteReves/chance2.png"));
		imagensCartas.put(3, carregaImagem("/sorteReves/chance3.png"));
		imagensCartas.put(4, carregaImagem("/sorteReves/chance4.png"));
		imagensCartas.put(5, carregaImagem("/sorteReves/chance5.png"));
		imagensCartas.put(6, carregaImagem("/sorteReves/chance6.png"));
		imagensCartas.put(7, carregaImagem("/sorteReves/chance7.png"));
		imagensCartas.put(8, carregaImagem("/sorteReves/chance8.png"));
		imagensCartas.put(9, carregaImagem("/sorteReves/chance9.png"));
		imagensCartas.put(10, carregaImagem("/sorteReves/chance10.png"));
		imagensCartas.put(11, carregaImagem("/sorteReves/chance11.png"));
		imagensCartas.put(12, carregaImagem("/sorteReves/chance12.png"));
		imagensCartas.put(13, carregaImagem("/sorteReves/chance13.png"));
		imagensCartas.put(14, carregaImagem("/sorteReves/chance14.png"));
		imagensCartas.put(15, carregaImagem("/sorteReves/chance15.png"));
		imagensCartas.put(16, carregaImagem("/sorteReves/chance16.png"));
		imagensCartas.put(17, carregaImagem("/sorteReves/chance17.png"));
		imagensCartas.put(18, carregaImagem("/sorteReves/chance18.png"));
		imagensCartas.put(19, carregaImagem("/sorteReves/chance19.png"));
		imagensCartas.put(20, carregaImagem("/sorteReves/chance20.png"));
		imagensCartas.put(21, carregaImagem("/sorteReves/chance21.png"));
		imagensCartas.put(22, carregaImagem("/sorteReves/chance22.png"));
		imagensCartas.put(23, carregaImagem("/sorteReves/chance23.png"));
		imagensCartas.put(24, carregaImagem("/sorteReves/chance24.png"));
		imagensCartas.put(25, carregaImagem("/sorteReves/chance25.png"));
		imagensCartas.put(26, carregaImagem("/sorteReves/chance26.png"));
		imagensCartas.put(27, carregaImagem("/sorteReves/chance27.png"));
		imagensCartas.put(28, carregaImagem("/sorteReves/chance28.png"));
		imagensCartas.put(29, carregaImagem("/sorteReves/chance29.png"));
		imagensCartas.put(30, carregaImagem("/sorteReves/chance30.png"));
		imagensCartas.put(5, carregaImagem("/sorteReves/chance5.png"));
    }
	

	public void mostrarMensagem(String msg) {
		if (painelTabuleiro != null) {
			painelTabuleiro.mostrarMensagem(msg);
		}
	}


	public void mostrarCarta(int idCarta) {
		if (painelTabuleiro != null) {
			Image imgCarta = imagensCartas.get(idCarta);
			if (imgCarta != null) {
				painelTabuleiro.mostrarCarta(imgCarta);
			} else {
				System.err.println("Imagem da carta não encontrada no cache: " + idCarta);
				painelTabuleiro.mostrarMensagem("Erro: Imagem da carta " + idCarta + " nao encontrada.");
			}
		}
	}


	public void mostrarOpcaoCompra(Terreno terreno) {
		if (painelTabuleiro != null) {
			painelTabuleiro.mostrarOpcaoCompra(terreno);
		}
	}


	public void mostrarOpcaoConstruir(Propriedade propriedade) {
		if (painelTabuleiro != null) {
			painelTabuleiro.mostrarOpcaoConstruir(propriedade);
		}
	}
	
	
	public void atualizarPaineisInfo(ArrayList<Peao> peoes) 
	{
		if (painelTabuleiro != null) {
			painelTabuleiro.setListaPeoes(peoes); 
			painelTabuleiro.repaint(); 
		}
	}

	public void atualizarPosicaoPeao(Peao p) {
		if (painelTabuleiro != null) {
			painelTabuleiro.repaint();
		}
	}

	public void atualizarDonoPropriedade(int pos, String cor) {
		if (painelTabuleiro != null) {
			// (O TabuleiroPanel pode ter lógica para armazenar isso)
			painelTabuleiro.repaint();
		}
	}

	public void atualizarConstrucoes(int pos) {
		if (painelTabuleiro != null) {
			painelTabuleiro.repaint();
		}
	}
}
