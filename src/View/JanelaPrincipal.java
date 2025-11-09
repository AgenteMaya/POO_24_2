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
public class JanelaPrincipal extends JFrame {
	public int LARG_DEFAULT = 1280;
	public int ALT_DEFAULT = 730;

	private GameController controller;

	JPanel painelMenu;
	TabuleiroPanel painelTabuleiro;
	JPanel painelDados;

	private JTextField campoNome;

	private int faceAtualD1 = 0, faceAtualD2 = 0;
	private Color corPainelAtual = new Color(120, 120, 120);
	private String nomeJogadorDaVez = "—";

	private Rectangle btnRollRect = null;
	private JComboBox<Integer> cbD1, cbD2;

	private HashMap<Integer, Image> imagensCartas;
	private HashMap<String, Image> imagensPeoes;
	private HashMap<Integer, Image> imagensDados;
	
	private java.util.List<String> historicoLancamentos = new java.util.ArrayList<>();

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

	public void mostrarTelaConfigJogadores(int total_jogadores, int num_jogadores, ArrayList<String> cores) {
		JPanel painelConfiguracao = new JPanel();
		painelConfiguracao.setLayout(null);
		getContentPane().removeAll();
		setSize(500, 500);

		if (num_jogadores == 0) {
			controller.iniciarPartida();
		} else {
			Texto textoCampoNome = new Texto();
			textoCampoNome.setTexto("Nome jogador nº" + (total_jogadores - num_jogadores + 1) + " (até 8 caracteres):");
			textoCampoNome.setBounds(10, 10, 220, 30);

			campoNome = new JTextField();
			campoNome.setBounds(10, 50, 100, 30);

			Texto textoCor = new Texto();
			textoCor.setTexto("Cor do peão:");
			textoCor.setBounds(10, 90, 100, 30);

			String[] listaCores = cores.toArray(new String[cores.size()]);
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
		}

		getContentPane().add(painelConfiguracao);
		revalidate();
		repaint();
	}

	private JPanel criarPainelLateralDados() {
		// --- painel pintado em Java2D (CENTER) ---
		painelDados = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();

				int W = getWidth(), H = getHeight();

				// fundo com a cor do jogador da vez
				g2.setColor(corPainelAtual);
				g2.fillRect(0, 0, W, H);

				// título
				g2.setFont(getFont().deriveFont(Font.BOLD, 16f));
				g2.setColor(contraste(corPainelAtual));
				String titulo = "Lançamento de Dados";
				g2.drawString(titulo, 16, 28);

				// rótulo jogador da vez
				g2.setFont(getFont().deriveFont(Font.BOLD, 13f));
				g2.drawString("Vez de: " + nomeJogadorDaVez, 16, 52);

				// área “cartucho” para as imagens
				int boxX = 16, boxY = 72, boxW = W - 32, boxH = 140;
				g2.setColor(new Color(255, 255, 255, 220));
				g2.fillRoundRect(boxX, boxY, boxW, boxH, 12, 12);

				int pad = 16;
				int slotW = (boxW - pad * 3) / 2;
				int slotH = boxH - pad * 2;
				int x1 = boxX + pad;
				int x2 = x1 + slotW + pad;
				int y = boxY + pad;

				if (faceAtualD1 >= 1 && faceAtualD1 <= 6) {
					Image img1 = imagensDados.get(faceAtualD1);
					drawCenteredScaled(g2, img1, x1, y, slotW, slotH);
				}
				if (faceAtualD2 >= 1 && faceAtualD2 <= 6) {
					Image img2 = imagensDados.get(faceAtualD2);
					drawCenteredScaled(g2, img2, x2, y, slotW, slotH);
				}

				// “botão” desenhado
				int btnW = boxW, btnH = 44;
				int btnX = boxX, btnY = boxY + boxH + 16;
				g2.setColor(new Color(255, 255, 255, 230));
				g2.fillRoundRect(btnX, btnY, btnW, btnH, 10, 10);
				g2.setColor(new Color(0, 0, 0, 160));
				g2.drawRoundRect(btnX, btnY, btnW, btnH, 10, 10);

				String lbl = "Lançar";
				g2.setFont(getFont().deriveFont(Font.BOLD, 14f));
				int strW = g2.getFontMetrics().stringWidth(lbl);
				int strH = g2.getFontMetrics().getAscent();
				g2.setColor(Color.BLACK);
				g2.drawString(lbl, btnX + (btnW - strW) / 2, btnY + (btnH + strH) / 2 - 4);

				btnRollRect = new Rectangle(btnX, btnY, btnW, btnH);
				
				int histX = 16;
				int histY = (boxY + boxH + 16) + (44 + 12); // abaixo do botão
				int histW = W - 32;
				int histH = Math.max(80, H - histY - 16);   // usa o que sobrar de altura

				// fundo do histórico
				g2.setColor(new Color(255,255,255,220));
				g2.fillRoundRect(histX, histY, histW, histH, 10, 10);
				g2.setColor(new Color(0,0,0,160));
				g2.drawRoundRect(histX, histY, histW, histH, 10, 10);

				// título do histórico
				g2.setFont(getFont().deriveFont(Font.BOLD, 13f));
				g2.setColor(Color.BLACK);
				int yText = histY + 18;
				g2.drawString("Histórico deste turno:", histX + 10, yText);
				yText += 6;

				// linhas do histórico
				g2.setFont(getFont().deriveFont(Font.PLAIN, 12f));
				int lineHeight = g2.getFontMetrics().getHeight();
				yText += lineHeight;

				for (int i = 0; i < historicoLancamentos.size(); i++) {
				    String ln = historicoLancamentos.get(i);
				    if (yText + lineHeight > histY + histH - 8) break; // não estoura a caixa
				    g2.drawString("• " + ln, histX + 10, yText);
				    yText += lineHeight;
				}

				g2.dispose();
			}
		};
		// importante: não fixe o preferredSize do CENTER
		painelDados.setOpaque(true);

		painelDados.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (btnRollRect != null && btnRollRect.contains(e.getPoint())) {
					controller.lancarDadosReal();
				}
			}
		});

		// --- rodapé de DEBUG (SOUTH) com altura fixa ---
		JPanel painelDebug = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
		painelDebug.setOpaque(true);
		painelDebug.setBackground(new Color(245, 245, 245));
		painelDebug.setPreferredSize(new Dimension(260, 64)); // altura fixa p/ garantir visibilidade

		Integer[] nums = { 1, 2, 3, 4, 5, 6 };
		cbD1 = new JComboBox<>(nums);
		cbD2 = new JComboBox<>(nums);

		JButton btnDebug = new JButton("Debug");
		btnDebug.addActionListener(ev -> {
			int d1 = (Integer) cbD1.getSelectedItem();
			int d2 = (Integer) cbD2.getSelectedItem();
			controller.lancarDadosDebug(d1, d2);
		});

		painelDebug.add(new JLabel("D1:"));
		painelDebug.add(cbD1);
		painelDebug.add(new JLabel("D2:"));
		painelDebug.add(cbD2);
		painelDebug.add(btnDebug);

		// --- container EAST (define só aqui o tamanho total) ---
		JPanel container = new JPanel(new BorderLayout());
		container.setPreferredSize(new Dimension(260, ALT_DEFAULT)); // largura fixa do lado direito
		container.add(painelDados, BorderLayout.CENTER);
		container.add(painelDebug, BorderLayout.SOUTH);

		return container;
	}

	private static void drawCenteredScaled(Graphics2D g2, Image img, int x, int y, int w, int h) {
		if (img == null)
			return;
		int iw = img.getWidth(null), ih = img.getHeight(null);
		if (iw <= 0 || ih <= 0)
			return;
		double sx = w / (double) iw;
		double sy = h / (double) ih;
		double s = Math.min(sx, sy);
		int dw = (int) Math.round(iw * s);
		int dh = (int) Math.round(ih * s);
		int dx = x + (w - dw) / 2;
		int dy = y + (h - dh) / 2;
		g2.drawImage(img, dx, dy, dw, dh, null);
	}

	private static Color contraste(Color bg) {
		double l = 0.299 * bg.getRed() + 0.587 * bg.getGreen() + 0.114 * bg.getBlue();
		return (l > 160) ? Color.BLACK : Color.WHITE;
	}

	public void indicarJogadorDaVez(Model.Peao p) {
		if (p == null)
			return;
		nomeJogadorDaVez = p.getNome();

		// mapeamento de cor -> Color
		switch (p.getCor()) {
		case "vermelho" -> corPainelAtual = new Color(220, 60, 60);
		case "azul" -> corPainelAtual = new Color(70, 120, 220);
		case "laranja" -> corPainelAtual = new Color(240, 140, 60);
		case "amarelo" -> corPainelAtual = new Color(230, 200, 70);
		case "magenta" -> corPainelAtual = new Color(200, 70, 200);
		case "cinza" -> corPainelAtual = new Color(150, 150, 160);
		default -> corPainelAtual = new Color(120, 120, 120);
		}
		if (painelDados != null)
			painelDados.repaint();
		
		resetHistoricoLancamentos();
	}
	
	public void resetHistoricoLancamentos() {
	    historicoLancamentos.clear();
	    if (painelDados != null) painelDados.repaint();
	}

	public void registraLancamento(int d1, int d2, String nota) {
	    String s = d1 + " + " + d2 + " = " + (d1 + d2);
	    if (nota != null && !nota.isBlank()) s += "  — " + nota;
	    historicoLancamentos.add(s);
	    // limita a, por ex., 10 linhas
	    if (historicoLancamentos.size() > 10) {
	        historicoLancamentos = historicoLancamentos.subList(
	            historicoLancamentos.size() - 10, historicoLancamentos.size()
	        );
	    }
	    if (painelDados != null) painelDados.repaint();
	}

	public void mostrarDados(int d1, int d2) {
		faceAtualD1 = (d1 >= 1 && d1 <= 6) ? d1 : 0;
		faceAtualD2 = (d2 >= 1 && d2 <= 6) ? d2 : 0;
		if (painelDados != null)
			painelDados.repaint();
	}

	public void mostrarTabuleiro() {
		getContentPane().removeAll();
		setSize(LARG_DEFAULT, ALT_DEFAULT);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		carregarImagemPeoes();
		carregarImagemCartas();
		carregarImagemDados();

		Image imagemTabuleiro = carregaImagem("/tabuleiro.png");

		painelTabuleiro = new TabuleiroPanel(imagemTabuleiro, imagensPeoes);
		painelTabuleiro.setController(controller);
		painelTabuleiro.setBackground(Color.WHITE);

		getContentPane().add(painelTabuleiro, BorderLayout.CENTER);

		JPanel painelLateral = criarPainelLateralDados();
		getContentPane().add(painelLateral, BorderLayout.EAST);

		// TODO: Você pode adicionar seus painéis de info aqui, se permitido
		// JPanel infoPanel = new JPanel();
		// infoPanel.setPreferredSize(new Dimension(LARG_DEFAULT - ALT_DEFAULT,
		// ALT_DEFAULT)); // ex: 480x800
		// infoPanel.setBackground(Color.LIGHT_GRAY);
		// infoPanel.add(new JLabel("Informações dos Jogadores"));
		// getContentPane().add(infoPanel, BorderLayout.EAST);

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

	private void carregarImagemPeoes() {
		imagensPeoes = new HashMap<>();

		imagensPeoes.put("vermelho", carregaImagem("/pinos/pin0.png"));
		imagensPeoes.put("azul", carregaImagem("/pinos/pin1.png"));
		imagensPeoes.put("laranja", carregaImagem("/pinos/pin2.png"));
		imagensPeoes.put("amarelo", carregaImagem("/pinos/pin3.png"));
		imagensPeoes.put("magenta", carregaImagem("/pinos/pin4.png"));
		imagensPeoes.put("cinza", carregaImagem("/pinos/pin5.png"));

	}

	private void carregarImagemDados() {
		imagensDados = new HashMap<>();

		for (int f = 1; f <= 6; f++) {
			imagensDados.put(f, carregaImagem("/dados/die_face_" + f + ".png"));
		}
	}

	private void carregarImagemCartas() {
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

	public void atualizarPaineisInfo(ArrayList<Peao> peoes) {
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
