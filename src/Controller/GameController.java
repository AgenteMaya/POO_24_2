package Controller;

import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import View.JanelaPrincipal;
import Model.Api;

public class GameController 
{
    private JanelaPrincipal view;
    
    private ArrayList<String> coresDisponiveis;
    private int numJogadoresTotal;
    
    public GameController(JanelaPrincipal view) 
    {
        this.view = view;
        
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
        Api.getInstance().Inicializa();
    }
    
    public void solicitarRetomadaJogo() 
    {
        System.out.println("AÇÃO: Retornando a jogo salvo...");
        
        // lógica de carregar um save!! --> inserir os peoes nas posicoes e tals
        
        view.mostrarTabuleiro(Api.getInstance().carregarPosicoesPeoes());
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
        	Api api = Api.getInstance();
        	
        	int id = numJogadoresTotal - jogadoresRestantes;
        	api.adicionaJogador(id, nome, cor, 4000);
            
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
        
        Api api = Api.getInstance();
        api.sorteiaOrdem();
        api.iniciaTurno();

        view.mostrarTabuleiro(api.carregarPosicoesPeoes()); 
        
        //talvez não precise passar a lista de peões como parâmetro
        view.atualizarPaineisInfo(api.carregarPosicoesPeoes());

        api.setJogadorAtual();
        view.indicarJogadorDaVez(api.getNomeJogAtual(), api.getCorJogAtual());

    }
    
    // chamado após o jogador lançar os dados e se mover --> analisa onde o peão caiu e decide o que fazer
    public void processarJogada() 
    {
    	Api api = Api.getInstance();
        int posAtual = api.getPosJogadorAtual(); 
        
        if (api.ehPropriedade(posAtual) || api.ehEmpresa(posAtual)) 
        {
            processarTerrenoCompra(posAtual);
        }
        else if (api.ehSorte(posAtual)) 
        {
            processarSorte();
        }
        else if (api.ehIrPraPrisao(posAtual)) 
        { 
            processarVaParaPrisao();
        }
        else if (api.ehImposto(posAtual)) 
        {
            processarImposto();
        }
        else if (api.ehLucro(posAtual)) 
        {
        	processarLucros();
        }
        else if (api.ehPrisao(posAtual)) 
        {
            view.mostrarMensagem("Apenas visitando a prisão.");
        }
        else if (api.ehParadaLivre(posAtual)) 
        {
            view.mostrarMensagem("Parada Livre. Nada acontece.");
        }
        else if (api.ehPontoDePartida(posAtual)) 
        {
            view.mostrarMensagem("Parou no Ponto de Partida.");
        }
    }

    private void processarTerrenoCompra(int posAtual) 
    {
    	Api api = Api.getInstance();
        int donoId = api.getIdDono(posAtual);
        
        if (donoId == -1) 
        {
        	//modificar o cabeçalho na view
            view.mostrarOpcaoCompra(api.getNomeTerreno(posAtual), api.getValorTerreno(posAtual));
        } 
        else if (donoId != api.getIdJogadorAtual()) 
        {
            view.mostrarMensagem("Pagando aluguel...");
            int idTerreno = api.getPosJogadorAtual(); 
            
            boolean continuaJogo = api.pagarAluguel(idTerreno);;
            if (!continuaJogo) view.mostrarMensagem("O jogador atual faliu e foi retirado do jogo!");
            
            //esta função na view pode se inscrever em alguma função dentro da model que notifique a modi
            //ficação na lista de peoes?
            //Inclusive essa função de notify pode ser chamada aqui
            view.atualizarPaineisInfo(api.carregarPosicoesPeoes());
        } 
        else 
        {

            if (api.ehPropriedade(posAtual)) 
            {
            	//esta função na view pode se inscrever em alguma função dentro da model que notifique 
            	//o evento de construção de uma propriedade?
                view.mostrarOpcaoConstruir(api.getNomePropriedade(posAtual));
            } 
            else 
            {
                view.mostrarMensagem("Você parou em sua própria Empresa.");
            }
        }
    }


    private void processarSorte() 
    {
    	Api api = Api.getInstance();
        
        view.mostrarCarta(api.getIdCarta()); 
        
        if (api.ehCartaSaidaPrisao()) 
        {
        	api.jogadorGanhaSaiDaPrisao();
            view.mostrarMensagem(api.getNomeJogAtual() + " guardou uma carta de Saída Livre da Prisão!");
            
        } 
        else if (api.ehCartaIdaPrisao()) 
        {
            view.mostrarMensagem("Sorte/Revés: Vá para a prisão!");

            api.jogadorVaiPraPrisao();
            
            view.atualizarPosicaoPeao(); 
            view.atualizarPaineisInfo(api.carregarPosicoesPeoes());
        } 
        else 
        {
            api.processaTransferencias();
            view.atualizarPaineisInfo(api.carregarPosicoesPeoes());
        }
    }


    private void processarVaParaPrisao() 
    {
        view.mostrarMensagem(Api.getInstance().getNomeJogAtual() + " vai direto para a prisão!");  
        Api.getInstance().jogadorVaiPraPrisao();       
        view.atualizarPosicaoPeao();
        view.atualizarPaineisInfo(Api.getInstance().carregarPosicoesPeoes());
    }
    
    private void processarImposto() 
    {
    	int valorImposto = -200; 
        Api.getInstance().realizaTransferenciaBanco(valorImposto);
        view.mostrarMensagem(Api.getInstance().getNomeJogAtual() + " paga R$ 200 de imposto.");    
        view.atualizarPaineisInfo(Api.getInstance().carregarPosicoesPeoes());
    }
    
    private void processarLucros() 
    {
        int valorLucros = 200; 
        view.mostrarMensagem(Api.getInstance().getNomeJogAtual() + " ganha R$ 200 de lucros.");
        
        Api.getInstance().realizaTransferenciaBanco(valorLucros);
        view.atualizarPaineisInfo(Api.getInstance().carregarPosicoesPeoes());
    }
    
    public void usuarioDecidiuComprar() 
    {
        int pos = Api.getInstance().getPosJogadorAtual();
        
        Api api = Api.getInstance();
        api.realizaCompraDePropriedade(pos);
        
        api.setDono(pos);
        view.atualizarPaineisInfo(Api.getInstance().carregarPosicoesPeoes());
    }


    public void usuarioDecidiuNaoComprar() 
    {
        view.mostrarMensagem("Propriedade não foi comprada.");
    }
    
   
    public void usuarioDecidiuConstruir(boolean ehCasa) // true=casa, false=hotel
    { 
        int pos = Api.getInstance().getPosJogadorAtual();
        
        Api.getInstance().realizaConstrucao(ehCasa, pos);
        
        view.atualizarConstrucoes(pos); 
        view.atualizarPaineisInfo(Api.getInstance().carregarPosicoesPeoes());
    }

    
    public void deslocamentoPeao(int deslocamento)
    {
    	Api api = Api.getInstance();
    	
    	api.setJogadorAtual();
    	
    	int posAntiga = api.getPosJogadorAtual();

        // tabuleiro.getTamListTerreno() deve ser 40
        int posNova = (posAntiga + deslocamento) % api.getTamTabuleiro(); 

        if (posNova < posAntiga) {
            System.out.println(api.getNomeJogAtual() + " passou pelo Ponto de Partida! Recebe R$ 200.");
            api.realizaTransferenciaBanco(200);
            view.atualizarPaineisInfo(api.carregarPosicoesPeoes());
        }

       api.setPosicaoPeao(posNova);
       view.atualizarPaineisInfo(api.carregarPosicoesPeoes());
        
       view.atualizarPosicaoPeao();
       view.atualizarPaineisInfo(api.carregarPosicoesPeoes());
    }
    
    public void iniciarProximoTurno() {
        Api api = Api.getInstance();

        api.setJogadorAtual();
        view.indicarJogadorDaVez(api.getNomeJogAtual(), api.getCorJogAtual());

        view.setAguardandoProximoTurno(false);
    }
    
    public void terminarTurno()
    {
        processarJogada();
        Api.getInstance().vaiProProximoTurno();

        view.setAguardandoProximoTurno(true);
    }
    
    public void lancarDadosDebug(int dado1, int dado2) {
    	Api api = Api.getInstance();
    	
        api.setJogadorAtual();
        view.indicarJogadorDaVez(api.getNomeJogAtual(), api.getCorJogAtual());

        // Jogador preso?
        if (api.jogadorEstahNaPrisao()) {
            view.mostrarDados(dado1, dado2); // <- exibe as imagens (Java2D)

            if (dado1 == dado2) {
                int deslocamento = dado1 + dado2;                
                api.libertaJodadorDaPrisao(deslocamento);
                deslocamentoPeao(deslocamento);
                
                view.mostrarMensagem("Tirou dupla. Saiu da prisão.");
                
                terminarTurno();
            } else {
                view.mostrarMensagem("Não tirou dupla. Fica na prisão.");
                api.vaiProProximoTurno();
                api.setJogadorAtual();
                view.indicarJogadorDaVez(api.getNomeJogAtual(), api.getCorJogAtual());
            }
            return;
        }

        view.mostrarDados(dado1, dado2); 
        deslocamentoPeao(dado1 + dado2); // primeira dupla

        if (dado1 == dado2) {        	
        	deslocamentoPeao(dado1 + dado2); // segunda dupla
        	
        	deslocamentoPeao(dado1 + dado2); // terceira dupla
        	
        	int foiPraPrisao = api.jogadorVaiPraPrisao(); 
            if (foiPraPrisao == 1) view.mostrarMensagem("Tirou a terceira dupla. Iria para a Prisão, mas possui a Carta de Saída Livre!");
            
            view.mostrarMensagem("Tirou a terceira dupla. Vai para a Prisão!");
            view.atualizarPosicaoPeao();
            view.atualizarPaineisInfo(api.carregarPosicoesPeoes());
            
            api.vaiProProximoTurno();
            api.setJogadorAtual();
            view.indicarJogadorDaVez(api.getNomeJogAtual(), api.getCorJogAtual());
        	return;            
        } else {
        	terminarTurno();
            return;
        }
    }
    
    public void lancarDadosReal() {
        Api api = Api.getInstance();
        api.setJogadorAtual();
        
        view.indicarJogadorDaVez(api.getNomeJogAtual(), api.getCorJogAtual());

        // Jogador preso?
        if (api.jogadorEstahNaPrisao()) 
        {
            int[] dados = api.getResultadoDados();
            view.mostrarDados(dados[0], dados[1]); // <- exibe as imagens (Java2D)
            view.registraLancamento(dados[0], dados[1], (dados[0]==dados[1]) ? "dupla — sai da prisão" : "falhou em sair");

            if (dados[0] == dados[1]) {
                int deslocamento = dados[0] + dados[1];
                deslocamentoPeao(deslocamento);
                api.libertaJodadorDaPrisao(deslocamento);
                         
                view.mostrarMensagem("Tirou dupla. Saiu da prisão.");
                
                terminarTurno();
            } else {
                view.mostrarMensagem("Não tirou dupla. Fica na prisão.");
                api.vaiProProximoTurno();
                api.setJogadorAtual();
                view.indicarJogadorDaVez(api.getNomeJogAtual(), api.getCorJogAtual());
            }
            return;
        }

        // Turno normal (uma rolagem conforme seu fluxo atual)

        int[] dados = api.getResultadoDados();
        view.mostrarDados(dados[0], dados[1]); // <- exibe as imagens
        view.registraLancamento(dados[0], dados[1], (dados[0]==dados[1]) ? "dupla #1" : "");

        deslocamentoPeao(dados[0] + dados[1]);

        if (dados[0] == dados[1]) {

            // 2ª
			dados = api.getResultadoDados();
            view.mostrarDados(dados[0], dados[1]);
            view.registraLancamento(dados[0], dados[1], (dados[0]==dados[1]) ? "dupla #2" : "");
            
            deslocamentoPeao(dados[0] + dados[1]);

            if (dados[0] == dados[1]) {
                // 3ª
                dados = api.getResultadoDados();
                view.mostrarDados(dados[0], dados[1]);
                view.registraLancamento(dados[0], dados[1], (dados[0]==dados[1]) ? "dupla #3 → prisão" : "");
                deslocamentoPeao(dados[0] + dados[1]);

                if (dados[0] == dados[1]) {
                    int foiPraPrisao = api.jogadorVaiPraPrisao(); // 10 = posPrisao 
                    if (foiPraPrisao == 1) view.mostrarMensagem("Tirou a terceira dupla. Iria para a Prisão, mas possui a Carta de Saída Livre!");
                    
                    view.mostrarMensagem("Tirou a terceira dupla. Vai para a Prisão!");
                    view.atualizarPosicaoPeao();
                    view.atualizarPaineisInfo(api.carregarPosicoesPeoes());
                    
                    api.vaiProProximoTurno();
                    api.setJogadorAtual();
                    view.indicarJogadorDaVez(api.getNomeJogAtual(), api.getCorJogAtual());
                    return;
                } else {
                    terminarTurno();
                    return;
                }
            } else {
                terminarTurno();
                return;
            }
        } else {
            terminarTurno();
            return;
        }
    }
}
