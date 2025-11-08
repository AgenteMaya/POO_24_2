package Model;

import java.util.ArrayList;
import java.util.Arrays;


public class Api {
	
	private static Api instance;
	
	private Tabuleiro tabuleiro;
	private Baralho baralho;
	private Dado dado;
	private Peao jogadorAtual;
	private Carta cartaAtual;
	
	private Api() {}
	
	public static Api getInstance() 
	{
		if(instance==null) 
		{
			instance = new Api();
		}
		return instance;
	}
	
	public void Inicializa() 
	{
		criaTabuleiro();
		criaBaralho();
	}
	
	private Dado getDado() 
	{
		if(dado == null) 
		{
			dado = new Dado();
		}
		return dado;
	}
	
	public void adicionaJogador(int id, String nome, String cor, int dinheiro) 
	{
		Peao jogador = new Peao(id);
		
        tabuleiro.addPeao(jogador);
        jogador.setNome(nome);
        jogador.setCor(cor);
        jogador.setDinheiro(dinheiro);
	}
	
	public void sorteiaOrdem() 
	{
		tabuleiro.sortearOrdemJogadores();
	}
	
	public void iniciaTurno() 
	{
		tabuleiro.iniciarPrimeiroTurno();
	}
	
	/*
	 * Função é chamada quando a carta retirada pelo jogador
	 * é de saída da prisão
	 */
	public void jogadorGanhaSaiDaPrisao() 
	{
		jogadorAtual.atribuiSaidaLivrePrisao(cartaAtual);
	}
	
	public void jogadorVaiPraPrisao() 
	{
		Carta cartaSaida = jogadorAtual.vaiPraPrisao(9);
        if (cartaSaida != null) 
        {
            baralho.descartarCarta(cartaSaida);
            jogadorAtual.removeCartaSaidaLivrePrisao();
            //notificar a view que foi utilizada a carta de Saida Livre

        }
        baralho.descartarCarta(cartaAtual);
	}
	
	public void processaTransferencias() 
	{
		int valor = cartaAtual.getValorTransferencia();
        if (cartaAtual.ehTranferenciaBanco()) 
        {
            Banco.getBanco().realizaTransferenciaBanco(jogadorAtual.getId(), valor, tabuleiro);
        } 
        else 
        {
            Banco.getBanco().realizaTransferenciaPeoes(jogadorAtual.getId(), valor, tabuleiro);
        }
	}
	
	public void realizaTransferenciaBanco(int valor) 
	{
		Banco.getBanco().realizaTransferenciaBanco(jogadorAtual.getId(), valor, tabuleiro);
	}
	
	public void realizaCompraDePropriedade(int pos) 
	{
		Banco.getBanco().compraPropriedade(pos, jogadorAtual.getId(), tabuleiro);
	}
	
	public void realizaConstrucao(boolean ehCasa, int pos) 
	{
		Banco.getBanco().constroiCasa(jogadorAtual.getId(), pos, tabuleiro, ehCasa);
	}
	
	public void vaiProProximoTurno() 
	{
		tabuleiro.proximoTurno();
	}
	
	public void libertaJodadorDaPrisao(int desl) 
	{
		jogadorAtual.saiDaPrisao(desl); // Conseguiu!
	}
	
	public void setJogadorAtual() 
	{
		jogadorAtual = tabuleiro.getJogadorDaVez();
	
	}
	
	public void setPosicaoPeao(int posNova) 
	{
		 jogadorAtual.setaPosicaoPeao(posNova);
	}
	
	public int getDeslocSaidaPrisao() 
	{
		Dado dado = getDado();
		return dado.deslocamentoSaidaPrisao(); // Tenta dados iguais
	}
	
	public int getDeslocamentoTotalDados() 
	{
		Dado dado = getDado();
		return dado.totalDeslocamento();
	}
	
	public int getTamTabuleiro() 
	{
		return tabuleiro.getTamListTerreno();
	}
	
	public int getPosJogadorAtual() 
	{
		return jogadorAtual.pegaPosicaoPeao();
	}
	
	public int getIdDono(int posAtual) 
	{
		Terreno terreno = getTerrenoAtual(posAtual);
		return terreno.getDono();
	}
	
	public ArrayList<Peao> getListaPeoes()
	{
		return tabuleiro.getListaPeoes();
	}
	
	public int getIdJogadorAtual() 
	{
		return jogadorAtual.getId();
	}
	
	public String getNomeJogAtual() 
	{
		return jogadorAtual.getNome();
	}
	
	public String getCorJogAtual() 
	{
		return jogadorAtual.getCor();
	}
	
	public int getIdCarta() 
	{
		cartaAtual = baralho.pegarCarta();
		return cartaAtual.getId();
	}
	
	public boolean jogadorEstahNaPrisao() 
	{
		return jogadorAtual.estaNaPrisao();
	}
	
	public boolean ehCartaSaidaPrisao() 
	{
		return cartaAtual.ehSaidaPrisao();
	}
	
	public boolean ehCartaIdaPrisao() 
	{
		return cartaAtual.ehIdaPrisao();
	}
	
	public boolean ehEmpresa(int pos) 
	{
        Terreno terrenoAtual = getTerrenoAtual(pos);
        return terrenoAtual instanceof Empresa;
	}
	
	public boolean ehPropriedade(int pos) 
	{
        Terreno terrenoAtual = getTerrenoAtual(pos);
        return terrenoAtual instanceof Propriedade;
	}
	
	public boolean ehSorte(int pos) 
	{
		Terreno terrenoAtual = getTerrenoAtual(pos);
        return terrenoAtual instanceof Sorte;
	}
	
	public boolean ehIrPraPrisao(int pos) 
	{
		Terreno terrenoAtual = getTerrenoAtual(pos);
		return terrenoAtual instanceof IrPraPrisao;
	}
	
	public boolean ehImposto(int pos) 
	{
		Terreno terrenoAtual = getTerrenoAtual(pos);
		return terrenoAtual instanceof Imposto;
	}
	
	public boolean ehPrisao(int pos) 
	{
		Terreno terrenoAtual = getTerrenoAtual(pos);
		return terrenoAtual instanceof Prisao;
	}
	
	public boolean ehParadaLivre(int pos) 
	{
		Terreno terrenoAtual = getTerrenoAtual(pos);
		return terrenoAtual instanceof ParadaLivre;
	}
	
	public boolean ehPontoDePartida(int pos) 
	{
		Terreno terrenoAtual = getTerrenoAtual(pos);
		return terrenoAtual instanceof PontoDePartida;
	}
	
	public void pagarAluguel(int idTerreno) 
	{
		Banco banco = Banco.getBanco();
		banco.pagarAluguel(tabuleiro, jogadorAtual.getId(), idTerreno);
	}
	
	private Terreno getTerrenoAtual(int pos) 
	{
		return tabuleiro.getTerreno(pos);
	}
		
	private static ArrayList<Integer> criarAlugueis(int base, int c1, int c2, int c3, int c4, int h) 
	{
	    return new ArrayList<>(Arrays.asList(base, c1, c2, c3, c4, h));
	}

	//Função auxiliar para criar dados de compra de construção. --> MUDAR DEPOIS
	private static ArrayList<Integer> criarPrecoConstrucao(int precoCasa, int precoHotel) 
	{
	    return new ArrayList<>(Arrays.asList(precoCasa, precoHotel));
	}	
		
	private void criaTabuleiro() 
	{
		//talvez mover esse código todo para a tabuleiro??? 
		ArrayList<Terreno> terrenos = new ArrayList<>();
        int posPrisao = 9;

        // Valores de exemplo para aluguéis e construções
        ArrayList<Integer> aluguelPadrao = criarAlugueis(10, 50, 150, 450, 800, 1200);
        ArrayList<Integer> precoCasaPadrao = criarPrecoConstrucao(50, 250);

        // inserir o nome dos terrenos???
        // -- LADO 1 (EMBAIXO) --
        terrenos.add(new PontoDePartida());                                 // 0: PONTO DE PARTIDA
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 60));  // 1: LEBLON
        terrenos.add(new Sorte(posPrisao));                                 // 2: ? (SORTE/REVÉS)
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 60));  // 3: AV. PRESIDENTE VARGAS
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 70));  // 4: AV. NOSSA S. DE COPACABANA
        terrenos.add(new Empresa(100, 200));                                // 5: Estação (Trem)
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 100)); // 6: AV. BRIG. FARIA LIMA
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 100)); // 7: AV. REBOUÇAS
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 120)); // 8: AV. 9 DE JULHO
        terrenos.add(new Prisao());                                         // 9: PRISÃO (VISITA)

        // -- LADO 2 (ESQUERDA) --
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 140)); // 10: AV. EUROPA
        terrenos.add(new Sorte(posPrisao));                                 // 11: ? (SORTE/REVÉS)
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 140)); // 12: RUA AUGUSTA
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 160)); // 13: AV. PACAEMBÚ
        terrenos.add(new Empresa(100, 200));                                // 14: Companhia (Carro)
        terrenos.add(new Sorte(posPrisao));                                 // 15: ? (SORTE/REVÉS)
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 180)); // 16: INTERLAGOS
        terrenos.add(new Empresa(75, 150));                                 // 17: $$ (Utility)
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 200)); // 18: MORUMBI
        terrenos.add(new ParadaLivre());                                    // 19: PARADA LIVRE

        // -- LADO 3 (CIMA) --
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 220)); // 20: FLAMENGO
        terrenos.add(new Sorte(posPrisao));                                 // 21: ? (SORTE/REVÉS)
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 220)); // 22: BOTAFOGO
        terrenos.add(new Empresa(75, 150));                                 // 23: $$ (Utility)
        terrenos.add(new Empresa(100, 200));                                // 24: Companhia (Barco)
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 260)); // 25: AV. BRASIL
        terrenos.add(new Sorte(posPrisao));                                 // 26: ? (SORTE/REVÉS)
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 260)); // 27: AV. PAULISTA
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 280)); // 28: JARDIM EUROPA
        terrenos.add(new IrPraPrisao(posPrisao));                           // 29: VÁ PARA A PRISÃO

        // -- LADO 4 (DIREITA) --
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 300)); // 30: COPACABANA
        terrenos.add(new Empresa(100, 200));                                // 31: Companhia (Avião)
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 300)); // 32: AV. VIEIRA SOUTO
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 320)); // 33: AV. ATLÂNTICA
        terrenos.add(new Empresa(100, 200));                                // 34: Companhia (Helicóptero)
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 350)); // 35: IPANEMA
        terrenos.add(new Sorte(posPrisao));                                 // 36: ? (SORTE/REVÉS)
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 370)); // 37: JARDIM PAULISTA
        terrenos.add(new Propriedade(aluguelPadrao, precoCasaPadrao, 400)); // 38: BROOKLIN
        terrenos.add(new Imposto());
        
        tabuleiro = new Tabuleiro(terrenos);
	}
	
	private void criaBaralho()
    {
        ArrayList<Carta> todasCartas = new ArrayList<>();
                
        // chance1.png
        todasCartas.add(new Carta(1, "A prefeitura mandou abrir uma nova avenida...", true, false, 25, true));
        // chance2.png
        todasCartas.add(new Carta(2, "Houve um assalto à sua loja, mas você estava segurado.", true, false, 150, true));
        // chance3.png
        todasCartas.add(new Carta(3, "Um amigo tinha lhe pedido um empréstimo e se esqueceu de devolver.", true, false, 80, true));
        // chance4.png
        todasCartas.add(new Carta(4, "Você está com sorte. Suas ações na Bolsa de Valores estão em alta.", true, false, 200, true));
        // ... (adicionar outras cartas de Sorte)

        // --- CARTAS REVÉS ---
        // ... (adicionar outras cartas de Revés)
        // chance25.png
        todasCartas.add(new Carta(25, "Você acaba de receber a comunicação do Imposto de Renda. Pague 50", false, false, -50, true));
        // chance26.png
        todasCartas.add(new Carta(26, "Seu clube está ampliando as piscinas. Os sócios devem contribuir. Pague 25", false, false, -25, true));
        // chance27.png
        todasCartas.add(new Carta(27, "Renove a tempo a licença do seu automóvel. Pague 30", false, false, -30, true));
        // chance28.png
        todasCartas.add(new Carta(28, "Seus parentes do interior vieram passar umas 'férias' na sua casa. Pague 45", false, false, -45, true));
        // chance29.png
        todasCartas.add(new Carta(29, "Seus filhos já vão para a escola. Pague a primeira mensalidade. Pague 50", false, false, -50, true));
        // chance30.png
        todasCartas.add(new Carta(30, "A geada prejudicou a sua safra de café. Pague 50", false, false, -50, true));
        
        baralho = new Baralho(todasCartas);
    }
	

}
