package Model;

import java.io.File;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.Map;

import java.io.*;

public class Api {
	
	private static Api instance;
	
	private Tabuleiro tabuleiro;
	private Baralho baralho;
	private Dado dado;
	private Peao jogadorAtual;
	private Carta cartaAtual;
	private Serializer serializer;
	private Desserializer desserializer;
	
	private Api() {}
	
	public static void reset() {
	    instance = null;
	}
	
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
	
	public int getQtdPeoes()
	{
		return tabuleiro.getTamListPeoes();
	}
	
	public LinkedHashMap<String, Integer> carregarPosicoesPeoes() {
        LinkedHashMap<String, Integer> listaPosicoesPeoes = new LinkedHashMap<String, Integer>();

        for(int i = 0; i < tabuleiro.getTamListPeoes(); i++) 
        {
            listaPosicoesPeoes.put(tabuleiro.getPosPeaoCor(i), tabuleiro.getPosPeao(i));
        }

        return listaPosicoesPeoes;
    }
	
	public void iniciaTurno() 
	{
		tabuleiro.iniciarPrimeiroTurno();
	}
	
	// função é chamada quando a carta retirada pelo jogador é de saída da prisão
	public void jogadorGanhaSaiDaPrisao() 
	{
		jogadorAtual.atribuiSaidaLivrePrisao(cartaAtual);
	}
	
	//olhar com mais detalhe esses dois métodos mais tarde!!
	public int jogadorVaiPraPrisao() 
	{
		Carta cartaSaida = jogadorAtual.vaiPraPrisao(10);
        if (cartaSaida != null) 
        {
            baralho.descartarCarta(cartaSaida);
            jogadorAtual.removeCartaSaidaLivrePrisao();
            //notificar a view que foi utilizada a carta de Saida Livre
            return  1;
        }
        return 0;
	}
	
	public void mandaJogadorPraPrisao() 
	{
		jogadorAtual.vaiPraPrisao(10);
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
	
	public boolean realizaCompraDePropriedade(int pos) 
	{
		return Banco.getBanco().compraPropriedade(pos, jogadorAtual.getId(), tabuleiro);
	}
	
	public boolean realizaConstrucao(boolean ehCasa, int pos) 
	{
		return Banco.getBanco().constroiCasa(jogadorAtual.getId(), pos, tabuleiro, ehCasa);
	}
	
	public void vaiProProximoTurno() 
	{
		tabuleiro.proximoTurno();
	}
	
	public void libertaJodadorDaPrisao(int desl) 
	{
		jogadorAtual.saiDaPrisao(desl); // conseguiu!
	}
	
	public void setJogadorAtual() 
	{
		jogadorAtual = tabuleiro.getJogadorDaVez();
		System.out.println("Jogador atual definido para: " + jogadorAtual.getId());
	}

	public void setJogadorAtualTabuleiro(int idJogadorAtual) 
	{
		tabuleiro.setJogadorDaVezIndex(idJogadorAtual);
		System.out.println("Índice do jogador atual no tabuleiro definido para: " + tabuleiro.getJogadorDaVez().getId());
	}

	public void setJogadorAtualTabuleiroManual(int idJogadorAtual) 
	{
		tabuleiro.setJogadorDaVezIndexManual(idJogadorAtual);
		System.out.println("Índice do jogador atual no tabuleiro definido para: " + tabuleiro.getJogadorDaVez().getId());
	}

	public Peao getJogadorAtual() 
	{
		return jogadorAtual;
	}
	
	public void setPosicaoPeao(int posNova) 
	{
		 jogadorAtual.setaPosicaoPeao(posNova);
	}
	
	public int[] getResultadoDados() 
	{
		Dado dado = getDado();
		return dado.lanca_dados();
	}
	
	public int getDeslocSaidaPrisao() 
	{
		Dado dado = getDado();
		return dado.deslocamentoSaidaPrisao(); // tenta dados iguais
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
		System.out.println(jogadorAtual.getCor());
		System.out.println(jogadorAtual.pegaPosicaoPeao());
		return jogadorAtual.pegaPosicaoPeao();
	}
	
	public int getIdDono(int posAtual) 
	{
		Terreno terreno = getTerrenoAtual(posAtual);
		return terreno.getDono();
	}
	
	public void setDono(int posAtual) 
	{
	    Terreno terreno = getTerrenoAtual(posAtual);
	    terreno.setDono(jogadorAtual.getId()); 
	}
	
	public ArrayList<Peao> getListaPeoes()
	{
		return tabuleiro.getListaPeoes();
	}
	
	public void adicionaPeao(Peao peao)
	{
		tabuleiro.addPeao(peao);
	}

	public ArrayList<Terreno> getListaTerrenos()
	{
		return tabuleiro.getListaTerrenos();
	}

	public ArrayList<Carta> getlCartasCompras()
	{
		return baralho.lCartasCompra;
	}

	public ArrayList<Carta> getlCartasDescarte()
	{
		return baralho.lCartasDescarte;
	}

	public void setlCartasCompras(ArrayList<Carta> lCartasCompras)
	{
		baralho.setlCompra(lCartasCompras);
	}

	public void setlCartasDescarte(ArrayList<Carta> lCartasDescarte)
	{
		baralho.setlDescarte(lCartasDescarte);
	}

	public int salvarJogo(File arquivo)
	{
		if(serializer == null)
		{
			serializer = new Serializer();
		}
		try{
			serializer.salvarJogo(arquivo);
			return 0;
		}       
        catch(Exception e)
        {
            System.out.println("Erro ao abrir ou escrever arquivo de salvamento");
			return 1;
        }
	}
	
	public int carregarJogo(File arquivo)
	{
		if(desserializer == null)
		{
			desserializer = new Desserializer();
		}
		try{
			desserializer.carregarJogo(arquivo);
			return 0;
		}       
        catch(Exception e)
        {
            System.out.println("Erro ao ler arquivo de carregamento");
			return 1;
        }
	}

	public double getDinheiroPeao(int index)
	{
		for (int i = 0; i < tabuleiro.getTamListPeoes(); i++)
    	{
    		if (i == index)
    		{
    			Peao peaoTemp = tabuleiro.getPeaoPorPos(i);
    			return peaoTemp.getDinheiro();
    		}
    	}
		return -1;
	}
	
	public String getNomePeao(int index) 
	{
		for (int i = 0; i < tabuleiro.getTamListPeoes(); i++)
    	{
    		if (i == index)
    		{
    			Peao peaoTemp = tabuleiro.getPeaoPorPos(i);
    			return peaoTemp.getNome();
    		}
    	}
		return "";
	}
	
	public String getCorPeao(int index) 
	{
		for (int i = 0; i < tabuleiro.getTamListPeoes(); i++)
    	{
    		if (i == index)
    		{
    			Peao peaoTemp = tabuleiro.getPeaoPorPos(i);
    			return peaoTemp.getCor();
    		}
    	}
		return "";
	}
	
	// estes métodos de pegar informações do vencedor servem para a situação quando todos os outros jogadores falem
	// neste caso, como só sobrou um jogador, deve-se pegar o primeiro peao da lista de peoes
	public String getNomeVencedor() 
	{
	    return tabuleiro.getPeaoPorPos(0).getNome();
	}

	public String getCorVencedor() 
	{
	    return tabuleiro.getPeaoPorPos(0).getCor();
	}

	public double getDinheiroVencedor() 
	{
	    return tabuleiro.getPeaoPorPos(0).getDinheiro();
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
	
	public double getDinheiroJogadorAtual()
	{
		return jogadorAtual.getDinheiro();
	}
	
	public void removeJogadorAtual()
	{
	    tabuleiro.removePeao(this.jogadorAtual);
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
	
	public boolean ehLucro(int pos) 
	{
		Terreno terrenoAtual = getTerrenoAtual(pos);
		return terrenoAtual instanceof Lucros;
	}
	
	public boolean pagarAluguelPropriedade(int idTerreno) 
	{
		Banco banco = Banco.getBanco();
		return banco.pagarAluguelPropriedade(tabuleiro, jogadorAtual.getId(), idTerreno);
	}
	
	public boolean pagarAluguelEmpresa(int idTerreno, int deslocamento) 
	{
		Banco banco = Banco.getBanco();
		return banco.pagarAluguelEmpresa(tabuleiro, jogadorAtual.getId(), idTerreno, deslocamento);
	}
	
	private Terreno getTerrenoAtual(int pos) 
	{
		return tabuleiro.getTerreno(pos);
	}
	
	public String getNomeTerreno(int posTerreno) 
    {
        Terreno terreno = getTerrenoAtual(posTerreno);
        return terreno.getNomeTerreno();
    }

    public int getValorTerreno(int posTerreno) 
    {
        Terreno terreno = getTerrenoAtual(posTerreno);
        return terreno.getValorCompra();
    }

    public String getNomePropriedade(int posProp) 
    {
        Propriedade propriedade = (Propriedade) getTerrenoAtual(posProp);
        return propriedade.getNomeTerreno();
    }	
		
	private void criaTabuleiro() 
	{
		ArrayList<Terreno> terrenos = new ArrayList<>();
        int posPrisao = 10;

        // parte de baixo
        terrenos.add(new PontoDePartida());                                
        terrenos.add(new Propriedade("Leblon", 100)); 
        terrenos.add(new Sorte(posPrisao));                                
        terrenos.add(new Propriedade("Av. Presidente Vargas", 60)); 
        terrenos.add(new Propriedade("Av. Nossa S. de Copacabana", 60)); 
        terrenos.add(new Empresa("Companhia Ferroviária", 50, 200));                        
        terrenos.add(new Propriedade("Av. Brig. Faria Lima", 240));
        terrenos.add(new Empresa("Companhia de Viação", 50, 200));   
        terrenos.add(new Propriedade("Avenida Rebouças", 220));
        terrenos.add(new Propriedade("Av. 9 de Julho", 220)); 
        terrenos.add(new Prisao());                                         

        // esquerda
        terrenos.add(new Propriedade("Av. Europa", 200));
        terrenos.add(new Sorte(posPrisao));                          
        terrenos.add(new Propriedade("Rua Augusta", 180)); 
        terrenos.add(new Propriedade("Av. Pacaembú", 180));
        terrenos.add(new Empresa("Companhia de Táxi", 40, 150));                               
        terrenos.add(new Sorte(posPrisao));                                
        terrenos.add(new Propriedade("Interlagos", 350)); 
        terrenos.add(new Lucros());                            
        terrenos.add(new Propriedade("Morumbi", 400)); 
        terrenos.add(new ParadaLivre());

        // cima
        terrenos.add(new Propriedade("Flamengo", 120)); 
        terrenos.add(new Sorte(posPrisao));                                 
        terrenos.add(new Propriedade("Botafogo", 100)); 
        terrenos.add(new Imposto());                               
        terrenos.add(new Empresa("Companhia de Navegação", 40, 150));                             
        terrenos.add(new Propriedade("Av. Brasil", 160)); 
        terrenos.add(new Sorte(posPrisao));                          
        terrenos.add(new Propriedade("Av. Paulista", 140)); 
        terrenos.add(new Propriedade("Jardim Europa", 140));
        terrenos.add(new IrPraPrisao(posPrisao));

        // direita
        terrenos.add(new Propriedade("Copacabana", 260)); 
        terrenos.add(new Empresa("Companhia de Aviação", 50, 200));                            
        terrenos.add(new Propriedade("Av. Vieira Souto", 320)); 
        terrenos.add(new Propriedade("Av. Atlântica", 300));
        terrenos.add(new Empresa("Companhia de Táxi Aéreo", 50, 200));                               
        terrenos.add(new Propriedade("Ipanema", 300)); 
        terrenos.add(new Sorte(posPrisao));                               
        terrenos.add(new Propriedade("Jardim Paulista", 280));
        terrenos.add(new Propriedade("Brooklin", 260)); 

        // garante que temos 40 terrenos
        System.out.println("Total de terrenos criados: " + terrenos.size());
        
        tabuleiro = new Tabuleiro(terrenos);
	}
	
	private void criaBaralho()
    {
		ArrayList<Carta> todasCartas = new ArrayList<>();
        
		// cartas sorte
        todasCartas.add(new Carta(1, "A prefeitura mandou abrir uma nova avenida, para o que desapropiou vários prédios. Em consequência seu terreno valorizou.", true, false, 25, true));
        todasCartas.add(new Carta(2, "Houve um assalto à sua loja, mas você estava segurado.", true, false, 150, true));
        todasCartas.add(new Carta(3, "Um amigo tinha lhe pedido um empréstimo e se esqueceu de devolver.", true, false, 80, true));
        todasCartas.add(new Carta(4, "Você está com sorte. Suas ações na Bolsa de Valores estão em alta.", true, false, 200, true));
        todasCartas.add(new Carta(5, "Você trocou seu carro usado com um amigo e ainda saiu lucrando.", true, false, 50, true));
        todasCartas.add(new Carta(6, "Você acaba de receber uma parcela do seu 13º salário.", true, false, 50, true));
        todasCartas.add(new Carta(7, "Você tirou o primeiro lugar no Torneio de Tênis do seu clube. Parabéns!", true, false, 100, true));
        todasCartas.add(new Carta(8, "O seu cachorro policial tirou o 1º prêmio na exposição do Kennel Club.", true, false, 100, true));
        todasCartas.add(new Carta(9, "Saída livre da prisão.", true, true, 0, true));
        todasCartas.add(new Carta(10, "Você encontrou dinheiro no chão.", true, false, 50, true));
        todasCartas.add(new Carta(11, "Você apostou com os parceiros deste jogo e ganhou.", true, false, 50, false));
        todasCartas.add(new Carta(12, "Você saiu de férias e se hospedou na casa de um amigo. Você economizou o hotel.", true, false, 45, true));
        todasCartas.add(new Carta(13, "Inesperadamente você recebeu uma herança que já estava esquecida.", true, false, 100, true));
        todasCartas.add(new Carta(14, "Você foi promovido a diretor da sua empresa..", true, false, 100, true));
        todasCartas.add(new Carta(15, "Você jogou na Loteria Esportiva com um grupo de amigos. Ganharam!.", true, false, 20, true));
        
        // cartas revés
        todasCartas.add(new Carta(16, "Um amigo ppediu-lhe um empréstimo. Você não pode recusar.", false, false, -15, true));
        todasCartas.add(new Carta(17, "Você vai casar e está comprando um apartamento novo.", false, false, -25, true));
        todasCartas.add(new Carta(18, "O médico lhe recomendou repouso num bom hotel de montanha.", false, false, -45, true));
        todasCartas.add(new Carta(19, "Você achou interessante assistir à estréia da temporada de ballet. Compre os ingressos.", false, false, -30, true));
        todasCartas.add(new Carta(20, "Parabéns! Você convidou seus amigos para festejar o aniversário.", false, false, -100, true));
        todasCartas.add(new Carta(21, "Você é papai outra vez! Despesas de maternidade.", false, false, -100, true));
        todasCartas.add(new Carta(22, "Papai os livros do ano passado não servem mais, preciso de livros novos.", false, false, -40, true));
        todasCartas.add(new Carta(23, "Vá para a prisão sem receber nada. (talvez eu lhe faça uma visita...)", false, true, 0, true));
        todasCartas.add(new Carta(24, "Você estacionou seu carro em lugar proibido e entrou na contra mão.", false, false, -30, true));
        todasCartas.add(new Carta(25, "Você acaba de receber a comunicação do Imposto de Renda.", false, false, -50, true));
        todasCartas.add(new Carta(26, "Seu clube está ampliando as piscinas. Os sócios devem contribuir.", false, false, -25, true));
        todasCartas.add(new Carta(27, "Renove a tempo a licença do seu automóvel.", false, false, -30, true));
        todasCartas.add(new Carta(28, "Seus parentes do interior vieram passar umas 'férias' na sua casa.", false, false, -45, true));
        todasCartas.add(new Carta(29, "Seus filhos já vão para a escola. Pague a primeira mensalidade.", false, false, -50, true));
        todasCartas.add(new Carta(30, "A geada prejudicou a sua safra de café.", false, false, -50, true));
        
        baralho = new Baralho(todasCartas);
    }
	

}
