package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controller.GameController;
import Controller.Ranking;

@SuppressWarnings("serial")
public class JanelaPrincipal extends JFrame
{
	public int LARG_DEFAULT = 1280;
	public int ALT_DEFAULT = 730;

	private GameController controller;

	JPanel painelMenu;
	TabuleiroPanel painelTabuleiro;
	JPanel painelDados;
	//JPanel painelInfo;
	InformacoesPanel painelInfo; // aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
	
	private String[] LISTA_PROPRIEDADES = {
		    "Leblon", "Av. Presidente Vargas", "Av. Nossa S. de Copacabana", "Av. Brig. Faria Lima",
		    "Av. Rebouças", "Av. 9 de Julho", "Av. Europa", "Rua Augusta", "Av. Pacaembú",
		    "Interlagos", "Morumbi", "Flamengo", "Botafogo", "Av. Brasil", "Av. Paulista",
		    "Jardim Europa", "Copacabana", "Av. Vieira Souto", "Av. Atlântica", "Ipanema",
		    "Jardim Paulista", "Brooklin"
	};

	private String[] LISTA_COMPANHIAS = {
	    "Companhia Ferroviária", "Companhia de Viação", "Companhia de Táxi",
	    "Companhia de Navegação", "Companhia de Aviação", "Companhia de Táxi Aéreo"
	};

	private JComboBox<String> cbCategoria;
	private JComboBox<String> cbItemSelecionado;

	private JTextField campoNome;

	private int faceAtualD1 = 0, faceAtualD2 = 0;
	private Color corPainelAtual = new Color(120, 120, 120);
	private String nomeJogadorDaVez = "—";

	private Rectangle btnRollRect = null;
	private JComboBox<Integer> cbD1, cbD2;
	
	private HashMap<Integer, Image> imagensCartas;
	private HashMap<String, Image> imagensPeoes;
	private HashMap<Integer, Image> imagensDados;
	
	private boolean aguardandoProximoTurno = false;

	private JButton btnSalvar;
	
	private java.util.List<String> historicoLancamentos = new java.util.ArrayList<>();

	public JanelaPrincipal() 
	{
		mostrarMenuInicial();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void setController(GameController controller) 
	{
		this.controller = controller;
	}

	public void iniciar() 
	{
		setTitle("Banco Imobiliário");
		setVisible(true);
	}

	public void mostrarMenuInicial() 
	{
		setSize(240, 170);
		this.setLocationRelativeTo(null);
		getContentPane().removeAll();

		painelMenu = new JPanel();
		painelMenu.setLayout(null);

		Botao botaoIniciar = new Botao("Iniciar");
		botaoIniciar.setBounds(10, 10, 200, 50);

		botaoIniciar.adicionaListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				controller.solicitarInicioJogo();
			}
		});

		Botao botaoRetomar = new Botao("Retomar");
		botaoRetomar.setBounds(10, 70, 200, 50);

		botaoRetomar.adicionaListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
            	fileChooser.setDialogTitle("Seleção de Arquivo de Carregamento");

         		FileNameExtensionFilter filtroSave =  new FileNameExtensionFilter("Arquivos de carregamento (*.txt)", "txt");
         		fileChooser.setFileFilter(filtroSave);

				int resultado = fileChooser.showOpenDialog(painelMenu);

				if (resultado == JFileChooser.APPROVE_OPTION) 
				{	
					File arquivoSelecionado = fileChooser.getSelectedFile();
					System.out.println("Arquivo selecionado: " + arquivoSelecionado.getAbsolutePath());
					controller.solicitarRetomadaJogo(arquivoSelecionado);
        		}
			}
		});

		painelMenu.add(botaoIniciar);
		painelMenu.add(botaoRetomar);

		getContentPane().add(painelMenu);
		revalidate();
		repaint();
	}

	public void mostrarTelaNumJogadores() 
	{
		getContentPane().removeAll();
		setSize(500, 500);
		this.setLocationRelativeTo(null);
		
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

		btnConfirmar.adicionaListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
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
	
	public void mostrarTelaFimDeJogo(ArrayList<Ranking> ranking) 
	{
	    getContentPane().removeAll();
	    setSize(600, 500); 
	    setLocationRelativeTo(null);

	    Ranking vencedor = ranking.get(0);

	    Color corBase;
	    switch (vencedor.cor.toLowerCase()) 
	    {
	        case "vermelho" -> corBase = new Color(220, 60, 60);
	        case "azul" -> corBase = new Color(70, 120, 220);
	        case "laranja" -> corBase = new Color(240, 140, 60);
	        case "amarelo" -> corBase = new Color(230, 200, 70);
	        case "magenta" -> corBase = new Color(200, 70, 200);
	        case "cinza" -> corBase = new Color(150, 150, 160);
	        default -> corBase = new Color(120, 120, 120);
	    }

	    JPanel painelFim = new JPanel() 
	    {
	        @Override
	        protected void paintComponent(Graphics g) 
	        {
	            super.paintComponent(g);
	            Graphics2D g2 = (Graphics2D) g.create();
	            
	            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

	            int w = getWidth();
	            int h = getHeight();

	            g2.setColor(corBase);
	            g2.fillRect(0, 0, w, h);

	            Color corTexto = contraste(corBase);
	            g2.setColor(corTexto);

	            g2.setFont(new Font("Arial", Font.BOLD, 32));
	            drawCentralizado(g2, "FIM DE JOGO!", w, 50);

	            g2.setFont(new Font("Arial", Font.BOLD, 20));
	            drawCentralizado(g2, "Vencedor: " + vencedor.nome, w, 90);

	            g2.setFont(new Font("Arial", Font.PLAIN, 16));
	            
	            int startY = 140;
	            int lineHeight = 30;
	            
	            g2.drawLine(100, startY - 20, w - 100, startY - 20);

	            for (int i = 0; i < ranking.size(); i++) 
	            {
	            	Ranking jog = ranking.get(i);
	                
	                String posicao = (i + 1) + "º";
	                String linhaTexto = String.format("%s  |  %s  |  R$ %.2f", posicao, jog.nome, jog.saldo);
	                
	                if (i == 0) 
	                {
	                    g2.setFont(new Font("Arial", Font.BOLD, 18));
	                } 
	                else 
	                {
	                    g2.setFont(new Font("Arial", Font.PLAIN, 16));
	                }
	                
	                drawCentralizado(g2, linhaTexto, w, startY + (i * lineHeight));
	            }

	            g2.dispose();
	        }

	        private void drawCentralizado(Graphics2D g2, String text, int larguraPainel, int y) 
	        {
	            FontMetrics fm = g2.getFontMetrics();
	            int x = (larguraPainel - fm.stringWidth(text)) / 2;
	            g2.drawString(text, x, y);
	        }
	    };

	    painelFim.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.insets = new Insets(10, 10, 10, 10);
	    
	    gbc.weighty = 1.0; 
	    painelFim.add(Box.createGlue(), gbc); 

	    gbc.weighty = 0.0; 
	    gbc.gridy++;
	    
	    JButton btnNovoJogo = new JButton("Novo Jogo");
	    btnNovoJogo.setPreferredSize(new Dimension(200, 40));
	    btnNovoJogo.setFont(new Font("Arial", Font.BOLD, 14));
	    btnNovoJogo.addActionListener(e -> controller.solicitarInicioJogo());
	    painelFim.add(btnNovoJogo, gbc);

	    gbc.gridy++;
	    JButton btnMenu = new JButton("Voltar ao Menu");
	    btnMenu.setPreferredSize(new Dimension(200, 40));
	    btnMenu.setFont(new Font("Arial", Font.PLAIN, 14));
	    btnMenu.addActionListener(e -> mostrarMenuInicial());
	    painelFim.add(btnMenu, gbc);

	    gbc.gridy++;
	    JButton btnSair = new JButton("Sair do Jogo");
	    btnSair.setPreferredSize(new Dimension(200, 40));
	    btnSair.setFont(new Font("Arial", Font.PLAIN, 14));
	    btnSair.addActionListener(e -> System.exit(0));
	    painelFim.add(btnSair, gbc);

	    getContentPane().add(painelFim);
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

			String[] listaCores = cores.toArray(new String[cores.size()]);
			JComboBox<String> comboBox = new JComboBox<>(listaCores);
			comboBox.setBounds(10, 130, 100, 30);

			Botao btnProximo = new Botao("Próximo");
			btnProximo.setBounds(120, 180, 100, 30);

			btnProximo.adicionaListener(new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
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
	
	public void limparDados() 
	{
	    faceAtualD1 = 0;
	    faceAtualD2 = 0;
	    if (painelDados != null) painelDados.repaint();
	}

	private JPanel criarPainelLateralDados() 
	{
		painelDados = new JPanel() 
		{
			@Override
			protected void paintComponent(Graphics g) 
			{
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

				// área para as imagens
				int boxX = 16, boxY = 72, boxW = W - 32, boxH = 140;
				g2.setColor(new Color(255, 255, 255, 220));
				g2.fillRoundRect(boxX, boxY, boxW, boxH, 12, 12);

				int pad = 16;
				int slotW = (boxW - pad * 3) / 2;
				int slotH = boxH - pad * 2;
				int x1 = boxX + pad;
				int x2 = x1 + slotW + pad;
				int y = boxY + pad;

				if (faceAtualD1 >= 1 && faceAtualD1 <= 6) 
				{
					Image img1 = imagensDados.get(faceAtualD1);
					desenharCentralizado(g2, img1, x1, y, slotW, slotH);
				}
				if (faceAtualD2 >= 1 && faceAtualD2 <= 6) 
				{
					Image img2 = imagensDados.get(faceAtualD2);
					desenharCentralizado(g2, img2, x2, y, slotW, slotH);
				}

		        int btnW = boxW, btnH = 44;
		        int btnX = boxX, btnY = boxY + boxH + 16;
		        g2.setColor(new Color(255, 255, 255, 230));
		        g2.fillRoundRect(btnX, btnY, btnW, btnH, 10, 10);
		        g2.setColor(new Color(0, 0, 0, 160));
		        g2.drawRoundRect(btnX, btnY, btnW, btnH, 10, 10);

		        String lbl = aguardandoProximoTurno ? "Próximo" : "Lançar";

		        g2.setFont(getFont().deriveFont(Font.BOLD, 14f));
		        int strW = g2.getFontMetrics().stringWidth(lbl);
		        int strH = g2.getFontMetrics().getAscent();
		        g2.setColor(Color.BLACK);
		        g2.drawString(lbl, btnX + (btnW - strW) / 2, btnY + (btnH + strH) / 2 - 4);

		        btnRollRect = new Rectangle(btnX, btnY, btnW, btnH);
				
				int histX = 16;
				int histY = (boxY + boxH + 16) + (44 + 12); 
				int histW = W - 32;
				int histH = Math.max(80, H - histY - 16);

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

				for (int i = 0; i < historicoLancamentos.size(); i++) 
				{
				    String ln = historicoLancamentos.get(i);
				    if (yText + lineHeight > histY + histH - 8) break; 
				    g2.drawString("• " + ln, histX + 10, yText);
				    yText += lineHeight;
				}

				g2.dispose();
			}
		};
		painelDados.setOpaque(true);

		painelDados.addMouseListener(new java.awt.event.MouseAdapter() 
		{
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent e) 
		    {
		        if (btnRollRect != null && btnRollRect.contains(e.getPoint())) 
		        {
		            if (aguardandoProximoTurno) 
		            {
		            	limparDados();
		                controller.iniciarProximoTurno();
		            } 
		            else 
		            {
		                controller.lancarDadosReal();
		            }
		        }
		    }
		});

		JPanel painelDebug = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
		painelDebug.setOpaque(true);
		painelDebug.setBackground(new Color(245, 245, 245));
		painelDebug.setPreferredSize(new Dimension(260, 64)); 

		Integer[] nums = { 1, 2, 3, 4, 5, 6 };
		cbD1 = new JComboBox<>(nums);
		cbD2 = new JComboBox<>(nums);

		JButton btnDebug = new JButton("Debug");
		btnDebug.addActionListener(ev -> 
		{
			int d1 = (Integer) cbD1.getSelectedItem();
			int d2 = (Integer) cbD2.getSelectedItem();
			controller.lancarDadosDebug(d1, d2);
		});

		painelDebug.add(new JLabel("D1:"));
		painelDebug.add(cbD1);
		painelDebug.add(new JLabel("D2:"));
		painelDebug.add(cbD2);
		painelDebug.add(btnDebug);

		JPanel container = new JPanel(new BorderLayout());
		container.setPreferredSize(new Dimension(260, ALT_DEFAULT)); 
		container.add(painelDados, BorderLayout.CENTER);
		container.add(painelDebug, BorderLayout.SOUTH);

		return container;
	}
	
	private JPanel criarPainelBotoesControle() 
	{
	    JPanel painelBotoes = new JPanel();
	    painelBotoes.setLayout(new GridLayout(4, 1, 5, 5));
	    
	    painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    painelBotoes.setBackground(new Color(240, 240, 240)); 

	    btnSalvar = new JButton("Salvar Jogo");
	    btnSalvar.setFont(new Font("Arial", Font.PLAIN, 12));
	    btnSalvar.addActionListener(e -> 
	    {
	        JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Seleção de Arquivo de Salvamento");

			FileNameExtensionFilter filtroSave =  new FileNameExtensionFilter("Arquivos de carregamento (*.txt)", "txt");
			fileChooser.setFileFilter(filtroSave);

			int resultado = fileChooser.showSaveDialog(painelMenu);

			if (resultado == JFileChooser.APPROVE_OPTION) 
			{	
				File arquivoSelecionado = fileChooser.getSelectedFile();
				System.out.println("Arquivo selecionado: " + arquivoSelecionado.getAbsolutePath());
				if (!arquivoSelecionado.getAbsolutePath().endsWith(".txt")) 
				{
				    arquivoSelecionado = new File(arquivoSelecionado.getAbsolutePath() + ".txt");
				}
				int ret = controller.solicitarSalvamento(arquivoSelecionado);
				System.out.println("Retorno do salvamento: " + ret);
				if(ret == 0)
				{
	        		JOptionPane.showMessageDialog(this, "Jogo Salvo!");
				}
				else
				{
					JOptionPane.showMessageDialog(this, "Erro ao salvar o jogo.");
				}
			}
	    });

	    JButton btnEncerrar = new JButton("Encerrar Jogo");
	    btnEncerrar.setFont(new Font("Arial", Font.PLAIN, 12));
	    btnEncerrar.addActionListener(e -> 
	    {
	        int resp = JOptionPane.showConfirmDialog(this, "Deseja realmente encerrar a partida atual?", "Encerrar", JOptionPane.YES_NO_OPTION);
	        if (resp == JOptionPane.YES_OPTION) 
	        {
	        	controller.terminoSolicitado(); // aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
	        }
	    });

	    JButton btnMenu = new JButton("Voltar ao Menu");
	    btnMenu.setFont(new Font("Arial", Font.PLAIN, 12));
	    btnMenu.addActionListener(e -> mostrarMenuInicial());

	    JButton btnFechar = new JButton("Fechar Jogo");
	    btnFechar.setFont(new Font("Arial", Font.BOLD, 12));
	    btnFechar.setForeground(new Color(150, 0, 0)); 
	    btnFechar.addActionListener(e -> System.exit(0));

	    painelBotoes.add(btnSalvar);
	    painelBotoes.add(btnEncerrar);
	    painelBotoes.add(btnMenu);
	    painelBotoes.add(btnFechar);

	    return painelBotoes;
	}
	
	
//	private JPanel criarPainelLateralInformacoes() // aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
//	{
//	    painelInfo = new JPanel() 
//	    {
//	        @Override
//	        protected void paintComponent(Graphics g) 
//	        {
//	            super.paintComponent(g);
//	            Graphics2D g2 = (Graphics2D) g.create();
//
//	            g2.setFont(getFont().deriveFont(Font.BOLD, 16f));
//	            g2.setColor(contraste(Color.LIGHT_GRAY));
//	            String titulo = "Painel de Informações";
//	            g2.drawString(titulo, 16, 28);
//	            
//	            // Aqui você pode desenhar mais infos do jogo futuramente (saldos, propriedades, etc)
//
//	            g2.dispose();
//	        }
//	    };
//	    painelInfo.setOpaque(true);
//	    painelInfo.setBackground(Color.LIGHT_GRAY); 
//
//	    JPanel container = new JPanel(new BorderLayout());
//	    container.setPreferredSize(new Dimension(260, ALT_DEFAULT));
//	    
//	    container.add(painelInfo, BorderLayout.CENTER);
//	    
//	    container.add(criarPainelBotoesControle(), BorderLayout.SOUTH);
//
//	    return container;
//	}
	
	private JPanel criarPainelLateralInformacoes() // aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
	{
	    JPanel containerEsquerdo = new JPanel(new BorderLayout());
	    containerEsquerdo.setPreferredSize(new Dimension(260, ALT_DEFAULT));
	    containerEsquerdo.setBackground(Color.LIGHT_GRAY);

	    // comboboxes
	    JPanel painelCombos = new JPanel(new GridLayout(4, 1, 5, 2));
	    painelCombos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    painelCombos.setOpaque(false);

	    JLabel lblCat = new JLabel("Selecione a Categoria:");
	    cbCategoria = new JComboBox<>(new String[]{"Peão", "Propriedade", "Companhia"});
	    JLabel lblItem = new JLabel("Selecione o Item:");
	    cbItemSelecionado = new JComboBox<>();

	    // ao mudar a categoria, atualiza a lista da segunda combo
	    cbCategoria.addActionListener(e -> atualizarComboItens());
	    
	    // ao mudar o item, avisa o painel para redesenhar as informações
	    cbItemSelecionado.addActionListener(e -> 
	    {
	        String cat = (String) cbCategoria.getSelectedItem();
	        String item = (String) cbItemSelecionado.getSelectedItem();
	        
	        if (painelInfo != null) 
	        {
	            painelInfo.setFiltros(cat, item);
	        }
	    });

	    painelCombos.add(lblCat);
	    painelCombos.add(cbCategoria);
	    painelCombos.add(lblItem);
	    painelCombos.add(cbItemSelecionado);
	    
	    atualizarComboItens(); 
	    containerEsquerdo.add(painelCombos, BorderLayout.NORTH);

	    painelInfo = new InformacoesPanel(); 
	    painelInfo.setController(controller);
	    containerEsquerdo.add(painelInfo, BorderLayout.CENTER);

	    // botões
	    containerEsquerdo.add(criarPainelBotoesControle(), BorderLayout.SOUTH);

	    return containerEsquerdo;
	}
	
	public InformacoesPanel getPainelInformacoes() 
	{
	    return this.painelInfo;
	}
	
	public void atualizarComboItens()  // aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
	{
	    String categoria = (String) cbCategoria.getSelectedItem();
	    cbItemSelecionado.removeAllItems();

	    if ("Propriedade".equals(categoria)) 
	    {
	        for (String s : LISTA_PROPRIEDADES) cbItemSelecionado.addItem(s);
	    } 
	    else if ("Companhia".equals(categoria)) 
	    {
	        for (String s : LISTA_COMPANHIAS) cbItemSelecionado.addItem(s);
	    } 
	    else if ("Peão".equals(categoria)) // como fazer?????????????????????????????????????????????????????????????????????????????
	    {
	    	try 
	    	{
		    	for (int i = 0; i < controller.getQtdPeoes(); i++) 
	            {
	                cbItemSelecionado.addItem(controller.getNomePeao(i));
	            }
	    	} 
	    	catch (Exception e) 
	    	{
                cbItemSelecionado.addItem("Carregando...");
            }
	    }
	    
	    String item = (String) cbItemSelecionado.getSelectedItem();
        
        if (painelInfo != null) 
        {
            painelInfo.setFiltros(categoria, item);
        }
	}
	
	private static void desenharCentralizado(Graphics2D g2, Image img, int x, int y, int w, int h) 
	{
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

	private static Color contraste(Color bg) 
	{
		double l = 0.299 * bg.getRed() + 0.587 * bg.getGreen() + 0.114 * bg.getBlue();
		return (l > 160) ? Color.BLACK : Color.WHITE;
	}
	
	public void setAguardandoProximoTurno(boolean aguardando) 
	{
	    this.aguardandoProximoTurno = aguardando;
	    if (painelDados != null) painelDados.repaint();
	}

	public void indicarJogadorDaVez(String nomePeao, String corPeao) 
	{
		nomeJogadorDaVez = nomePeao;

		// mapeamento de cor 
		switch (corPeao) 
		{
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
	
	public void resetHistoricoLancamentos() 
	{
	    historicoLancamentos.clear();
	    if (painelDados != null) painelDados.repaint();
	}

	public void registraLancamento(int d1, int d2, String nota) 
	{
	    String s = d1 + " + " + d2 + " = " + (d1 + d2);
	    if (nota != null && !nota.isBlank()) s += "  — " + nota;
	    historicoLancamentos.add(s);
	    
	    if (historicoLancamentos.size() > 10) 
	    {
	        historicoLancamentos = historicoLancamentos.subList(
	            historicoLancamentos.size() - 10, historicoLancamentos.size()
	        );
	    }
	    if (painelDados != null) painelDados.repaint();
	}

	public void mostrarDados(int d1, int d2) 
	{
		faceAtualD1 = (d1 >= 1 && d1 <= 6) ? d1 : 0;
		faceAtualD2 = (d2 >= 1 && d2 <= 6) ? d2 : 0;
		if (painelDados != null)
			painelDados.repaint();
	}

	public void mostrarTabuleiro(LinkedHashMap<String, Integer> listaPosicoesPeoes) 
	{
		getContentPane().removeAll();
		setSize(LARG_DEFAULT, ALT_DEFAULT);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		carregarImagemPeoes();
		carregarImagemCartas();
		carregarImagemDados();

		Image imagemTabuleiro = carregaImagem("/tabuleiro.png");
		
		painelTabuleiro = new TabuleiroPanel(imagemTabuleiro, imagensPeoes, listaPosicoesPeoes);
		painelTabuleiro.setController(controller);
		painelTabuleiro.setBackground(Color.WHITE);

		getContentPane().add(painelTabuleiro, BorderLayout.CENTER);

		JPanel painelLateralDireito = criarPainelLateralDados();
		getContentPane().add(painelLateralDireito, BorderLayout.EAST);
		
		JPanel painelLateralEsquerdo = criarPainelLateralInformacoes();
		getContentPane().add(painelLateralEsquerdo, BorderLayout.WEST);

		revalidate();
		repaint();
	}

	private Image carregaImagem(String nomeArquivo) 
	{
		Image image = null;
		URL imageUrl = getClass().getResource(nomeArquivo);

		if (imageUrl == null) 
		{
			System.out.println("Erro: Não foi possível encontrar o recurso: " + nomeArquivo);
			System.exit(1);
		}

		try 
		{
			image = ImageIO.read(imageUrl);
		} 
		catch (IOException e) 
		{
			System.out.println("Erro ao carregar a imagem: " + e.getMessage());
			System.exit(1);
		}

		return image;
	}

	private void carregarImagemPeoes() 
	{
		imagensPeoes = new HashMap<>();

		imagensPeoes.put("vermelho", carregaImagem("/pinos/pin0.png"));
		imagensPeoes.put("azul", carregaImagem("/pinos/pin1.png"));
		imagensPeoes.put("laranja", carregaImagem("/pinos/pin2.png"));
		imagensPeoes.put("amarelo", carregaImagem("/pinos/pin3.png"));
		imagensPeoes.put("magenta", carregaImagem("/pinos/pin4.png"));
		imagensPeoes.put("cinza", carregaImagem("/pinos/pin5.png"));

	}

	private void carregarImagemDados() 
	{
		imagensDados = new HashMap<>();

		for (int f = 1; f <= 6; f++) {
			imagensDados.put(f, carregaImagem("/dados/die_face_" + f + ".png"));
		}
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
	}

	public void mostrarMensagem(String msg) 
	{
		if (painelTabuleiro != null) {
			painelTabuleiro.mostrarMensagem(msg);
		}
	}

	public void mostrarCarta(int idCarta) 
	{
		if (painelTabuleiro != null) 
		{
			Image imgCarta = imagensCartas.get(idCarta);
			if (imgCarta != null) 
			{
				painelTabuleiro.mostrarCarta(imgCarta);
			} 
			else 
			{
				System.err.println("Imagem da carta não encontrada no cache: " + idCarta);
				painelTabuleiro.mostrarMensagem("Erro: Imagem da carta " + idCarta + " nao encontrada.");
			}
		}
	}

	public void mostrarOpcaoCompra(String nome, int valor) 
	{
		if (painelTabuleiro != null) 
		{
			painelTabuleiro.mostrarOpcaoCompra(nome, valor);
		}
	}

	public void mostrarOpcaoConstruir(String nome) 
	{
		if (painelTabuleiro != null) 
		{
			painelTabuleiro.mostrarOpcaoConstruir(nome);
		}
	}
	
	public void atualizarPaineisInfo(LinkedHashMap<String, Integer> peoes) 
	{
		if (painelTabuleiro != null) 
		{
			painelTabuleiro.setListaPeoes(peoes);
			painelTabuleiro.repaint();
		}
	}


	public void atualizarPosicaoPeao() 
	{
		if (painelTabuleiro != null) 
		{
			painelTabuleiro.repaint();
		}
	}

	public void atualizarConstrucoes(int pos) 
	{
		if (painelTabuleiro != null) 
		{
			painelTabuleiro.repaint();
		}
	}

	public void setHabilitaSalvar(boolean b)
	{
		btnSalvar.setEnabled(b);
	}
}
