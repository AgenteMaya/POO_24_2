package Controller;

import java.util.ArrayList;

import Model.*;
import View.JanelaPrincipal;

public class GameController {
    
    private Tabuleiro tabuleiro;
    private Baralho baralho;
    //cartas???
    private JanelaPrincipal view;
    
    private ArrayList<String> coresDisponiveis;
    private int numJogadoresTotal;
    
    private Peao jogadorAtual;

    public GameController(Tabuleiro tabuleiro, Baralho baralho, JanelaPrincipal view) {
        this.tabuleiro = tabuleiro;
        this.baralho = baralho;
        this.view = view;
        
        this.coresDisponiveis = new ArrayList<>();
        inicializaCores();
    }
    
    
    private void inicializaCores() {
        coresDisponiveis.add("Vermelho");
        coresDisponiveis.add("Azul");
        coresDisponiveis.add("Laranja");
        coresDisponiveis.add("Amarelo");
        coresDisponiveis.add("Magenta");
        coresDisponiveis.add("Cinza");
    }
    
    
    public void solicitarInicioJogo() {
        System.out.println("AÇÃO: Iniciando o jogo...");
        view.mostrarTelaNumJogadores();
    }
    
    public void solicitarRetomadaJogo() {
        System.out.println("AÇÃO: Retornando a jogo salvo...");
        // lógica de carregar um save
        view.mostrarTabuleiro();
    }
    
    public void confirmarNumeroJogadores(String num) {
        try {
            int num_jogadores = Integer.parseInt(num);
            System.out.println("Jogadores: " + num_jogadores);
            
            if (num_jogadores >= 3 && num_jogadores <= 6) { 
                this.numJogadoresTotal = num_jogadores;
                view.mostrarTelaConfigJogadores(num_jogadores);
            } else {
                System.out.println("Número de jogadores inválido.");
                // view mandar erro?
            }
        } catch (NumberFormatException ex) {
            System.out.println("Entrada inválida.");
         // view mandar erro?
        }
    }
    
    public void configurarProximoJogador(int jogadoresRestantes, String nome, String cor) {
        System.out.println("Configurando jogador... Restam: " + jogadoresRestantes);
        
        if (coresDisponiveis.contains(cor)) {
            // Lógica de Model
            // Peao jogador = new Peao(numJogadoresTotal - jogadoresRestantes, nome, cor);
            // tabuleiro.adicionarPeao(jogador);
            // banco.darDinheiroInicial(jogador);
            coresDisponiveis.remove(cor);
            
            System.out.println("Jogador " + nome + " criado com a cor " + cor);
            
            view.mostrarErroCor(false); 
            view.mostrarTelaConfigJogadores(jogadoresRestantes);
            
        } else {
            System.out.println("Cor inválida ou já escolhida!");
         // view mandar erro?
            view.mostrarErroCor(true);
        }
    }
    
    public void iniciarPartida() {
        System.out.println("Começar partida");
        // (Aqui entra a lógica final de setup, como sortear a ordem, etc.)
        // ...
        
        view.mostrarTabuleiro();
    }
    
    public void setJogadorAtual(Peao peao) {
        this.jogadorAtual = peao;
    }
    

    // chamado após o jogador lançar os dados e se mover --> analisa onde o peão caiu e decide o que fazer
    public void processarJogada() {
        int posAtual = jogadorAtual.pegaPosicaoPeao(); 
        Terreno terrenoAtual = tabuleiro.getTerreno(posAtual);
        
        if (terrenoAtual instanceof Propriedade || terrenoAtual instanceof Empresa) {
            processarTerrenoCompra(terrenoAtual);
        }
        else if (terrenoAtual instanceof Sorte) {
            processarSorte((Sorte) terrenoAtual);
        }
        else if (terrenoAtual instanceof IrPraPrisao) { 
            processarVaParaPrisao((IrPraPrisao) terrenoAtual);
        }
        else if (terrenoAtual instanceof Imposto) {
            processarImposto();
        }
        else if (terrenoAtual instanceof Prisao) {
            view.mostrarMensagem("Apenas visitando a prisão.");
        }
        else if (terrenoAtual instanceof ParadaLivre) {
            view.mostrarMensagem("Parada Livre. Nada acontece.");
        }
        else if (terrenoAtual instanceof PontoDePartida) {
            view.mostrarMensagem("Parou no Ponto de Partida.");
        }
    }

    private void processarTerrenoCompra(Terreno terreno) {
        int donoId = terreno.getDono();
        
        if (donoId == -1) {
            view.mostrarOpcaoCompra(terreno);
        } else if (donoId != jogadorAtual.getId()) {
            view.mostrarMensagem("Pagando aluguel...");
            int idTerreno = jogadorAtual.pegaPosicaoPeao();
            
            Banco.getBanco().pagarAluguel(tabuleiro, jogadorAtual.getId(), idTerreno);
            
            // Atualiza a UI (View)
            view.atualizarPaineisInfo(tabuleiro.getListaPeoes());
            
        } else {

            if (terreno instanceof Propriedade) {
                view.mostrarOpcaoConstruir((Propriedade) terreno);
            } else {
                view.mostrarMensagem("Você parou em sua própria Empresa.");
            }
        }
    }


    private void processarSorte(Sorte terrenoSorte) {
        Carta carta = baralho.pegarCarta();
        
        view.mostrarCarta(carta.getId()); 
        
        if (carta.ehSaidaPrisao()) {
            jogadorAtual.atribuiSaidaLivrePrisao(carta);
            view.mostrarMensagem(jogadorAtual.getNome() + " guardou uma carta de Saída Livre da Prisão!");
            
        } else if (carta.ehIdaPrisao()) {
            view.mostrarMensagem("Sorte/Revés: Vá para a prisão!");

            Carta cartaSaida = jogadorAtual.vaiPraPrisao(9);
            if (cartaSaida != null) {
                baralho.descartarCarta(cartaSaida);
            }
            baralho.descartarCarta(carta);
            view.atualizarPosicaoPeao(jogadorAtual); 
            
        } else {
            int valor = carta.getValorTransferencia();
            if (carta.ehTranferenciaBanco()) {
                Banco.getBanco().realizaTransferenciaBanco(jogadorAtual.getId(), valor, tabuleiro);
            } else {
                Banco.getBanco().realizaTransferenciaPeoes(jogadorAtual.getId(), valor, tabuleiro);
            }
            view.atualizarPaineisInfo(tabuleiro.getListaPeoes());
        }
    }


    private void processarVaParaPrisao(IrPraPrisao terrenoPrisao) {
        view.mostrarMensagem(jogadorAtual.getNome() + " vai direto para a prisão!");
        
        Carta cartaSaida = jogadorAtual.vaiPraPrisao(9);
        if (cartaSaida != null) {
            baralho.descartarCarta(cartaSaida);
            jogadorAtual.removeCartaSaidaLivrePrisao();
            view.mostrarMensagem("...mas usou a carta de Saída Livre!");
        }
        
        view.atualizarPosicaoPeao(jogadorAtual);
    }
    
    private void processarImposto() {
        int valorImposto = -200; 
        view.mostrarMensagem(jogadorAtual.getNome() + " paga R$ 200 de imposto.");
        
        Banco.getBanco().realizaTransferenciaBanco(jogadorAtual.getId(), valorImposto, tabuleiro);
        view.atualizarPaineisInfo(tabuleiro.getListaPeoes());
    }
    
    
    public void usuarioDecidiuComprar() {
        int pos = jogadorAtual.pegaPosicaoPeao();
        
        Banco.getBanco().compraPropriedade(pos, jogadorAtual.getId(), tabuleiro);
        
        view.atualizarDonoPropriedade(pos, jogadorAtual.getCor());
        view.atualizarPaineisInfo(tabuleiro.getListaPeoes());
    }


    public void usuarioDecidiuNaoComprar() {
        view.mostrarMensagem("Propriedade não foi comprada.");
    }
    
   
    public void usuarioDecidiuConstruir(boolean ehCasa) { // true=casa, false=hotel
        int pos = jogadorAtual.pegaPosicaoPeao();
        
        Banco.getBanco().constroiCasa(jogadorAtual.getId(), pos, tabuleiro, ehCasa);
        
        view.atualizarPaineisInfo(tabuleiro.getListaPeoes());
        view.atualizarConstrucoes(pos); 
    }
}
