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
        	Api api = Api.getInstance();
        	
        	int id = numJogadoresTotal - jogadoresRestantes;
        	api.adicionaJogador(id, nome, cor, jogadoresRestantes);
            
            coresDisponiveis.remove(cor);
            
            System.out.println("Jogador " + nome + " criado com a cor " + cor);
            
            view.mostrarErroCor(false); 
            view.mostrarTelaConfigJogadores(numJogadoresTotal, jogadoresRestantes, coresDisponiveis);
            
        } 
        else 
        {
            System.out.println("Cor inválida ou já escolhida!");
            view.mostrarErroCor(true);
        }
    }
    
    public void iniciarPartida() 
    {
        System.out.println("Começar partida");
        
        Api api = Api.getInstance();
        api.sorteiaOrdem();
        api.iniciaTurno();

        view.mostrarTabuleiro(); 
        
        //talvez não precise passar a lista de peões como parâmetro
        view.atualizarPaineisInfo(api.getListaPeoes());
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
            view.mostrarOpcaoCompra(terreno);
        } 
        else if (donoId != api.getIdJogadorAtual()) 
        {
            view.mostrarMensagem("Pagando aluguel...");
            int idTerreno = api.getPosJogadorAtual();
            
            api.pagarAluguel(idTerreno);;
            
            //esta função na view pode se inscrever em alguma função dentro da model que notifique a modi
            //ficação na lista de peoes?
            //Inclusive essa função de notify pode ser chamada aqui
            view.atualizarPaineisInfo(tabuleiro.getListaPeoes());
            
        } 
        else 
        {

            if (api.ehPropriedade(posAtual)) 
            {
            	//esta função na view pode se inscrever em alguma função dentro da model que notifique 
            	//o evento de construção de uma propriedade?
                view.mostrarOpcaoConstruir((Propriedade) terreno);
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
            
            view.atualizarPosicaoPeao(jogadorAtual); 
            
        } 
        else 
        {
            api.processaTransferencias();
            view.atualizarPaineisInfo(tabuleiro.getListaPeoes());
        }
    }


    private void processarVaParaPrisao() 
    {
        view.mostrarMensagem(Api.getInstance().getNomeJogAtual() + " vai direto para a prisão!");  
        Api.getInstance().jogadorVaiPraPrisao();       
        view.atualizarPosicaoPeao(jogadorAtual);
    }
    
    private void processarImposto() 
    {
    	int valorImposto = -200; 
        Api.getInstance().realizaTransferenciaBanco(valorImposto);
        view.mostrarMensagem(Api.getInstance().getNomeJogAtual() + " paga R$ 200 de imposto.");    
        view.atualizarPaineisInfo(tabuleiro.getListaPeoes());
    }
    
    
    public void usuarioDecidiuComprar() 
    {
        int pos = Api.getInstance().getPosJogadorAtual();
        
        Api api = Api.getInstance();
        api.realizaCompraDePropriedade(pos);
        
        view.atualizarDonoPropriedade(pos, api.getCorJogAtual());
        view.atualizarPaineisInfo(tabuleiro.getListaPeoes());
    }


    public void usuarioDecidiuNaoComprar() 
    {
        view.mostrarMensagem("Propriedade não foi comprada.");
    }
    
   
    public void usuarioDecidiuConstruir(boolean ehCasa) // true=casa, false=hotel
    { 
        int pos = Api.getInstance().getPosJogadorAtual();
        
        Api.getInstance().realizaConstrucao(ehCasa, pos);
        
        view.atualizarPaineisInfo(tabuleiro.getListaPeoes());
        view.atualizarConstrucoes(pos); 
    }

    // verificar!
    public void usuarioLancouDados(int deslocamento)
    {
    	Api api = Api.getInstance();
    	
    	api.setJogadorAtual();
    	
    	int posAntiga = api.getPosJogadorAtual();

        // tabuleiro.getTamListTerreno() deve ser 40
        int posNova = (posAntiga + deslocamento) % api.getTamTabuleiro(); 

        if (posNova < posAntiga) {
            System.out.println(api.getNomeJogAtual() + " passou pelo Ponto de Partida! Recebe R$ 200.");
            api.realizaTransferenciaBanco(200);
            view.atualizarPaineisInfo(tabuleiro.getListaPeoes()); 
        }

       api.setPosicaoPeao(posNova);
        
        view.atualizarPosicaoPeao(jogadorAtual);
        processarJogada();
        
        api.vaiProProximoTurno();
    }
    
 // Este é o método que o botão "Lançar Dados" REAL deve chamar
    public void lancarDadosReal() {
    	
    	Api api = Api.getInstance();
    	api.setJogadorAtual();
        
        if (api.jogadorEstahNaPrisao()) {
            view.mostrarMensagem("Tentando sair da prisão...");
            int deslocamentoPrisao = api.getDeslocSaidaPrisao(); // Tenta dados iguais
            if (deslocamentoPrisao != -1) {
                api.libertaJodadorDaPrisao(deslocamentoPrisao);
                // (chamar o resto da lógica)
                usuarioLancouDados(deslocamentoPrisao); 
            } else {
                view.mostrarMensagem("Não tirou dados iguais. Fica na prisão.");
                api.vaiProProximoTurno();
            }
            return; // Termina o turno
        }
        
        int deslocamento = api.getDeslocamentoTotalDados();

        if (deslocamento == -1) {
            view.mostrarMensagem("3 duplas! Vá para a prisão.");
            api.jogadorVaiPraPrisao(); // 9 = posPrisao
            view.atualizarPosicaoPeao(jogadorAtual);
            api.vaiProProximoTurno();
            return; 
        }
        
        usuarioLancouDados(deslocamento);
    }
}
