package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import Controller.GameController;

@SuppressWarnings("serial")
public class JanelaPrincipal extends JFrame {

	public final int LARG_DEFAULT = 1280;
	public final int ALT_DEFAULT = 800;

	private GameController controller;

	JPanel painelMenu;
	JPanel painelTabuleiro;
	private JTextField campoNumJogadores;
	private JTextField campoNome;
	private JTextField campoCor;
	private Texto textoAviso;

	public JanelaPrincipal() {
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

		campoNumJogadores = new JTextField();
		campoNumJogadores.setBounds(10, 50, 100, 30);

		Botao btnConfirmar = new Botao("Confirmar");
		btnConfirmar.setBounds(120, 50, 100, 30);

		btnConfirmar.adicionaListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.confirmarNumeroJogadores(campoNumJogadores.getText());
			}
		});

		painelJogadores.add(texto_num_jogadores);
		painelJogadores.add(campoNumJogadores);
		painelJogadores.add(btnConfirmar);

		getContentPane().add(painelJogadores);

		revalidate();
		repaint();
	}

	public void mostrarTelaConfigJogadores(int num_jogadores) {
		JPanel painelConfiguracao = new JPanel();
		painelConfiguracao.setLayout(null);
		getContentPane().removeAll();
		setSize(500, 500);

		if (num_jogadores == 0) {
			Botao btnJogar = new Botao("Jogar!");
			btnJogar.setBounds(150, 180, 100, 30);

			btnJogar.adicionaListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					controller.iniciarPartida();
				}
			});
			painelConfiguracao.add(btnJogar);
		} else {
			Texto textoCampoNome = new Texto();
			textoCampoNome.setTexto("Nome jogador (até 8 caracteres):");
			textoCampoNome.setBounds(10, 10, 190, 30);

			campoNome = new JTextField();
			campoNome.setBounds(10, 50, 100, 30);

			Texto textoCor = new Texto();
			textoCor.setTexto("Cor do peão:");
			textoCor.setBounds(10, 90, 100, 30);

			campoCor = new JTextField();
			campoCor.setBounds(10, 130, 100, 30);

			textoAviso = new Texto();
			textoAviso.setTexto("Cor inválida!!!");
			textoAviso.setBounds(10, 150, 100, 30);
			textoAviso.setVisible(false);

			Botao btnProximo = new Botao("Próximo");
			btnProximo.setBounds(120, 180, 100, 30);

			btnProximo.adicionaListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					controller.configurarProximoJogador(num_jogadores - 1, campoNome.getText(), campoCor.getText());
				}
			});

			painelConfiguracao.add(textoCampoNome);
			painelConfiguracao.add(campoNome);
			painelConfiguracao.add(textoCor);
			painelConfiguracao.add(campoCor);
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

	public void mostrarTabuleiro() {
		getContentPane().removeAll();
		setSize(LARG_DEFAULT, ALT_DEFAULT);

		HashMap<String, Image> imagensPeoes;
        imagensPeoes = new HashMap<>();
        imagensPeoes = carregaImagemPeoes(imagensPeoes);
        
		Image imagemTabuleiro = carregaImagem("/tabuleiro.png");
		painelTabuleiro = new TabuleiroPanel(imagemTabuleiro, imagensPeoes);
		painelTabuleiro.setBackground(Color.WHITE);
		painelTabuleiro.setBounds(0, 0, 400, 400);
		getContentPane().add(painelTabuleiro);

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

	private HashMap<String, Image> carregaImagemPeoes(HashMap<String, Image> imagens) 
	{
		imagens.put("Vermelho", carregaImagem("/peao0.png"));
		imagens.put("Azul", carregaImagem("/peao1.png"));
		imagens.put("Laranja", carregaImagem("/peao2.png"));
		imagens.put("Amarelo", carregaImagem("/peao3.png"));
		imagens.put("Magenta", carregaImagem("/peao4.png"));
		imagens.put("Cinza", carregaImagem("/peao5.png"));
	
		return imagens;
	}
}