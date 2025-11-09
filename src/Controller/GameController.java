package Controller;

import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import Model.*;
import View.JanelaPrincipal;

public class GameController 
{
    private Tabuleiro tabuleiro;
    private Baralho baralho;
    private JanelaPrincipal view;
    
    private ArrayList<String> coresDisponiveis;
    private int numJogadoresTotal;
    
    private Peao jogadorAtual;
    
    private Dado dado;

    public GameController(Tabuleiro tabuleiro, Baralho baralho, JanelaPrincipal view) 
    {
        this.tabuleiro = tabuleiro;
        this.baralho = baralho;
        this.view = view;
        this.dado = new Dado();
        
        this.coresDisponiveis = new ArrayList<>();
        inicializaCores();
    }
    
    
    private void inicializaCores() 
    {
        coresDisponiveis.add("vermelho");
        coresDisponiveis.add("azul");
        coresDisponiveis.add("laranja");
        coresDisponiveis.add("amarelo");
        coresDisponiveis.add("magenta");
        coresDisponiveis.add("cinza");
    }
    
    
    public void solicitarInicioJogo() 
    {
        System.out.println("AÇÃO: Iniciando o jogo...");
        view.mostrarTelaNumJogadores();
    }
    
    public void solicitarRetomadaJogo() 
    {
        System.out.println("AÇÃO: Retornando a jogo salvo...");
        // lógica de carregar um save!!
        view.mostrarTabuleiro();
    }
    
    public void confirmarNumeroJogadores(int num_jogadores) 
    {
        try 
        {
            System.out.println("Jogadores: " + num_jogadores);
            
            if (num_jogadores >= 3 && num_jogadores <= 6) 
            { 
                this.numJogadoresTotal = num_jogadores;
                view.mostrarTelaConfigJogadores(num_jogadores, num_jogadores, coresDisponiveis);
            } 
            else 
            {
                System.out.println("Número de jogadores inválido.");
                view.mostrarMensagem("Número de jogadores inválido.");
            }
        } catch (NumberFormatException ex) 
        {
            System.out.println("Entrada inválida.");
            view.mostrarMensagem("Entrada inválida.");
        }
    }
    
    public void configurarProximoJogador(int jogadoresRestantes, String nome, String cor) 
    {
        System.out.println("Configurando jogador... Restam: " + jogadoresRestantes);
        
        if (coresDisponiveis.contains(cor)) 
        {
            Peao jogador = new Peao(numJogadoresTotal - jogadoresRestantes);
            tabuleiro.addPeao(jogador);
            jogador.setNome(nome);
            jogador.setCor(cor);
            jogador.setDinheiro(4000);
            
            coresDisponiveis.remove(cor);
            
            System.out.println("Jogador " + nome + " criado com a cor " + cor);
            
            view.mostrarTelaConfigJogadores(numJogadoresTotal, jogadoresRestantes, coresDisponiveis);
            
        } 
        else 
        {
            System.out.println("Ímpossível chegar aqui");
        }
    }
    
    public void iniciarPartida() 
    {
        System.out.println("Começar partida");
        
        tabuleiro.sortearOrdemJogadores();
        tabuleiro.iniciarPrimeiroTurno();

        view.mostrarTabuleiro(); 
        
        view.atualizarPaineisInfo(tabuleiro.getListaPeoes());
        
        this.jogadorAtual = tabuleiro.getJogadorDaVez();
        view.indicarJogadorDaVez(this.jogadorAtual);
    }
    
    public void setJogadorAtual(Peao peao) 
    {
        this.jogadorAtual = peao;
    }
    

    // chamado após o jogador lançar os dados e se mover --> analisa onde o peão caiu e decide o que fazer
    public void processarJogada() 
    {
        int posAtual = jogadorAtual.pegaPosicaoPeao(); 
        Terreno terrenoAtual = tabuleiro.getTerreno(posAtual);
        
        if (terrenoAtual instanceof Propriedade || terrenoAtual instanceof Empresa) 
        {
            processarTerrenoCompra(terrenoAtual);
        }
        else if (terrenoAtual instanceof Sorte) 
        {
            processarSorte((Sorte) terrenoAtual);
        }
        else if (terrenoAtual instanceof IrPraPrisao) 
        { 
            processarVaParaPrisao((IrPraPrisao) terrenoAtual);
        }
        else if (terrenoAtual instanceof Imposto) 
        {
            processarImposto();
        }
        else if (terrenoAtual instanceof Lucros) 
        {
        	processarLucros();
        }
        else if (terrenoAtual instanceof Prisao) 
        {
            view.mostrarMensagem("Apenas visitando a prisão.");
        }
        else if (terrenoAtual instanceof ParadaLivre) 
        {
            view.mostrarMensagem("Parada Livre. Nada acontece.");
        }
        else if (terrenoAtual instanceof PontoDePartida) 
        {
            view.mostrarMensagem("Parou no Ponto de Partida.");
        }
    }

    private void processarTerrenoCompra(Terreno terreno) 
    {
        int donoId = terreno.getDono();
        
        if (donoId == -1) 
        {
            view.mostrarOpcaoCompra(terreno);
        } 
        else if (donoId != jogadorAtual.getId()) 
        {
            view.mostrarMensagem("Pagando aluguel...");
            int idTerreno = jogadorAtual.pegaPosicaoPeao();
            
            Banco.getBanco().pagarAluguel(tabuleiro, jogadorAtual.getId(), idTerreno);
            
            view.atualizarPaineisInfo(tabuleiro.getListaPeoes());
            
        } 
        else 
        {

            if (terreno instanceof Propriedade) 
            {
                view.mostrarOpcaoConstruir((Propriedade) terreno);
            } 
            else 
            {
                view.mostrarMensagem("Você parou em sua própria Empresa.");
            }
        }
    }


    private void processarSorte(Sorte terrenoSorte) 
    {
        Carta carta = baralho.pegarCarta();
        
        view.mostrarCarta(carta.getId()); 
        
        if (carta.ehSaidaPrisao()) 
        {
            jogadorAtual.atribuiSaidaLivrePrisao(carta);
            view.mostrarMensagem(jogadorAtual.getNome() + " guardou uma carta de Saída Livre da Prisão!");
            
        } 
        else if (carta.ehIdaPrisao()) 
        {
            view.mostrarMensagem("Sorte/Revés: Vá para a prisão!");

            Carta cartaSaida = jogadorAtual.vaiPraPrisao(9);
            if (cartaSaida != null) 
            {
                baralho.descartarCarta(cartaSaida);
            }
            baralho.descartarCarta(carta);
            view.atualizarPosicaoPeao(jogadorAtual); 
            
        } 
        else 
        {
            int valor = carta.getValorTransferencia();
            if (carta.ehTranferenciaBanco()) 
            {
                Banco.getBanco().realizaTransferenciaBanco(jogadorAtual.getId(), valor, tabuleiro);
            } 
            else 
            {
                Banco.getBanco().realizaTransferenciaPeoes(jogadorAtual.getId(), valor, tabuleiro);
            }
            view.atualizarPaineisInfo(tabuleiro.getListaPeoes());
        }
    }


    private void processarVaParaPrisao(IrPraPrisao terrenoPrisao) 
    {
        view.mostrarMensagem(jogadorAtual.getNome() + " vai direto para a prisão!");
        
        Carta cartaSaida = jogadorAtual.vaiPraPrisao(9);
        if (cartaSaida != null) 
        {
            baralho.descartarCarta(cartaSaida);
            jogadorAtual.removeCartaSaidaLivrePrisao();
            view.mostrarMensagem("...mas usou a carta de Saída Livre!");
        }
        
        view.atualizarPosicaoPeao(jogadorAtual);
    }
    
    private void processarImposto() 
    {
        int valorImposto = -200; 
        view.mostrarMensagem(jogadorAtual.getNome() + " paga R$ 200 de imposto.");
        
        Banco.getBanco().realizaTransferenciaBanco(jogadorAtual.getId(), valorImposto, tabuleiro);
        view.atualizarPaineisInfo(tabuleiro.getListaPeoes());
    }
    
    private void processarLucros() 
    {
        int valorImposto = 200; 
        view.mostrarMensagem(jogadorAtual.getNome() + " ganha R$ 200 de lucros.");
        
        Banco.getBanco().realizaTransferenciaBanco(jogadorAtual.getId(), valorImposto, tabuleiro);
        view.atualizarPaineisInfo(tabuleiro.getListaPeoes());
    }
    
    public void usuarioDecidiuComprar() 
    {
        int pos = jogadorAtual.pegaPosicaoPeao();
        
        Banco.getBanco().compraPropriedade(pos, jogadorAtual.getId(), tabuleiro);
        
        view.atualizarDonoPropriedade(pos, jogadorAtual.getCor());
        view.atualizarPaineisInfo(tabuleiro.getListaPeoes());
    }


    public void usuarioDecidiuNaoComprar() 
    {
        view.mostrarMensagem("Propriedade não foi comprada.");
    }
    
   
    public void usuarioDecidiuConstruir(boolean ehCasa) // true=casa, false=hotel
    { 
        int pos = jogadorAtual.pegaPosicaoPeao();
        
        Banco.getBanco().constroiCasa(jogadorAtual.getId(), pos, tabuleiro, ehCasa);
        
        view.atualizarPaineisInfo(tabuleiro.getListaPeoes());
        view.atualizarConstrucoes(pos); 
    }

    
    public void deslocamentoPeao(int deslocamento)
    {
    	this.jogadorAtual = tabuleiro.getJogadorDaVez();

        int posAntiga = jogadorAtual.pegaPosicaoPeao();
        // tabuleiro.getTamListTerreno() deve ser 40
        int posNova = (posAntiga + deslocamento) % tabuleiro.getTamListTerreno(); 

        if (posNova < posAntiga) {
            System.out.println(jogadorAtual.getNome() + " passou pelo Ponto de Partida! Recebe R$ 200.");
            Banco.getBanco().realizaTransferenciaBanco(jogadorAtual.getId(), 200, tabuleiro);
            view.atualizarPaineisInfo(tabuleiro.getListaPeoes()); 
        }

        jogadorAtual.setaPosicaoPeao(posNova);
        
        view.atualizarPosicaoPeao(jogadorAtual);
    }
    
    public void terminarTurno()
    {       
        processarJogada();
        
        tabuleiro.proximoTurno();
    }
    
    public void lancarDadosDebug(int dado1, int dado2) {
        this.jogadorAtual = tabuleiro.getJogadorDaVez();
        view.indicarJogadorDaVez(this.jogadorAtual);

        // Jogador preso?
        if (jogadorAtual.estaNaPrisao()) {
            view.mostrarDados(dado1, dado2); // <- exibe as imagens (Java2D)

            if (dado1 == dado2) {
                int deslocamento = dado1 + dado2;
                deslocamentoPeao(deslocamento);
                
                jogadorAtual.saiDaPrisao(deslocamento);
                
                view.mostrarMensagem("Tirou dupla. Saiu da prisão.");
                
                terminarTurno();
            } else {
                view.mostrarMensagem("Não tirou dupla. Fica na prisão.");
                tabuleiro.proximoTurno();
                this.jogadorAtual = tabuleiro.getJogadorDaVez();
                view.indicarJogadorDaVez(this.jogadorAtual);
            }
            return;
        }

        view.mostrarDados(dado1, dado2); 
        deslocamentoPeao(dado1 + dado2);

        if (dado1 == dado2) {
        	view.mostrarMensagem("Tirou uma dupla.");
        	
        	deslocamentoPeao(dado1 + dado2);
        	view.mostrarMensagem("Tirou segunda dupla.");
        	
        	deslocamentoPeao(dado1 + dado2);
        	view.mostrarMensagem("Tirou terceira dupla. Vai para a Prisão!");
        	
            jogadorAtual.vaiPraPrisao(10); // 10 = posPrisao
            view.atualizarPosicaoPeao(jogadorAtual);
            tabuleiro.proximoTurno();
        	return;            
        } else {
        	terminarTurno();
            return;
        }
    }
    
    public void lancarDadosReal() {
        this.jogadorAtual = tabuleiro.getJogadorDaVez();
        view.indicarJogadorDaVez(this.jogadorAtual);

        // Jogador preso?
        if (jogadorAtual.estaNaPrisao()) {
            int[] dados = dado.lanca_dados();
            view.mostrarDados(dados[0], dados[1]); // <- exibe as imagens (Java2D)

            if (dados[0] == dados[1]) {
                int deslocamento = dados[0] + dados[1];
                deslocamentoPeao(deslocamento);
                
                jogadorAtual.saiDaPrisao(deslocamento);
                
                view.mostrarMensagem("Tirou dupla. Saiu da prisão.");
                
                terminarTurno();
            } else {
                view.mostrarMensagem("Não tirou dupla. Fica na prisão.");
                tabuleiro.proximoTurno();
                this.jogadorAtual = tabuleiro.getJogadorDaVez();
                view.indicarJogadorDaVez(this.jogadorAtual);
            }
            return;
        }

        // Turno normal (uma rolagem conforme seu fluxo atual)
        int[] dados = dado.lanca_dados();
        view.mostrarDados(dados[0], dados[1]); // <- exibe as imagens
        deslocamentoPeao(dados[0] + dados[1]);

        if (dados[0] == dados[1]) {
        	view.mostrarMensagem("Tirou a primeira dupla.");
        	
        	dados = dado.lanca_dados();
            view.mostrarDados(dados[0], dados[1]);
            deslocamentoPeao(dados[0] + dados[1]);
            
            if (dados[0] == dados[1])
            {
            	view.mostrarMensagem("Tirou a segunda dupla.");
            	
            	dados = dado.lanca_dados();
                view.mostrarDados(dados[0], dados[1]);
                deslocamentoPeao(dados[0] + dados[1]);
                
                if (dados[0] == dados[1])
                {
                	view.mostrarMensagem("Tirou a terceira dupla. Vai para a Prisão!");

                    jogadorAtual.vaiPraPrisao(10); // 10 = posPrisao
                    view.atualizarPosicaoPeao(jogadorAtual);
                    tabuleiro.proximoTurno();
                    return;
                }
                else
                {
                	terminarTurno();
                	return;
                }
            }
            else
            {
            	terminarTurno();
            	return;
            }
            
        } else {
        	terminarTurno();
            return;
        }
    }
}
