package Controller;

import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.io.*;

import View.JanelaPrincipal;
import Model.Api;

import Observer.ObservadoIF;
import Observer.ObservadorIF;

public class GameController 
{
    private JanelaPrincipal view;
    
    private ArrayList<String> coresDisponiveis;
    private int numJogadoresTotal;
    private int deslocamentoAtual;
    
    public GameController(JanelaPrincipal view) 
    {
        this.view = view;
        
        this.coresDisponiveis = new ArrayList<>();
        inicializaCores();
    }
    
    public ObservadoIF registra(ObservadorIF o)
    {
    	Api.getInstance().add(o);
    	return Api.getInstance();
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
        this.coresDisponiveis = new ArrayList<>();
        inicializaCores();
        
        System.out.println("AÇÃO: Iniciando o jogo...");
        view.mostrarTelaNumJogadores();
        Api.getInstance().Inicializa();
    }
    
    public void solicitarRetomadaJogo(File arquivo) 
    {
        System.out.println("AÇÃO: Retornando a jogo salvo...");
        
        Api.getInstance().Inicializa();
        
        int retorno = Api.getInstance().carregarJogo(arquivo);      
    
        if (retorno == 0)
        {
            view.mostrarTabuleiro(Api.getInstance().carregarPosicoesPeoes());

            view.indicarJogadorDaVez(Api.getInstance().getNomeJogAtual(), Api.getInstance().getCorJogAtual());
            view.setAguardandoProximoTurno(false);
            view.setHabilitaSalvar(true);
            view.atualizarComboItens();
        }
        else
        {            
            JOptionPane.showMessageDialog(
                null, 
                "Não foi possível carregar o jogo.\nVerifique o arquivo selecionado.",
                "Erro ao carregar",
                JOptionPane.ERROR_MESSAGE
            );
        }

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
        } 
        catch (NumberFormatException ex) 
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
        	System.out.println("Id do jogador: " + id);
        	api.adicionaJogador(id, nome, cor, 4000); // aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
            
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
        api.add(view.getPainelInformacoes());
        
        //talvez não precise passar a lista de peões como parâmetro
        view.atualizarPaineisInfo(api.carregarPosicoesPeoes());

        api.setJogadorAtual();
        view.indicarJogadorDaVez(api.getNomeJogAtual(), api.getCorJogAtual());
        view.limparDados();
        view.setAguardandoProximoTurno(false);
        view.atualizarComboItens();
        view.setHabilitaSalvar(true);
    }
    
    // chamado após o jogador lançar os dados e se mover --> analisa onde o peão caiu e decide o que fazer
    public boolean processarJogada() 
    {
        //view.setHabilitaSalvar(false);
        System.out.println("id do peao da vez da rodada: " + Api.getInstance().getIdJogadorAtual());
        Api api = Api.getInstance();
        int posAtual = api.getPosJogadorAtual();

        if (api.ehPropriedade(posAtual)) 
        {
            return processarPropriedade(posAtual); 
        } 
        else if (api.ehEmpresa(posAtual)) 
        {
            return processarEmpresa(posAtual); 
        }
        else if (api.ehSorte(posAtual)) 
        {
            return processarSorte();
            //System.out.println("SORTE: Saldo jogador " + api.getNomeJogAtual() + " = " + api.getDinheiroJogadorAtual());
        }
        else if (api.ehIrPraPrisao(posAtual)) 
        {
            processarVaParaPrisao();
        }
        else if (api.ehImposto(posAtual)) 
        {
            processarImposto();
            System.out.println("IMPOSTO: Saldo jogador " + api.getNomeJogAtual() + " = " + api.getDinheiroJogadorAtual());
        }
        else if (api.ehLucro(posAtual)) 
        {
            processarLucros();
            System.out.println("LUCROS: Saldo jogador " + api.getNomeJogAtual() + " = " + api.getDinheiroJogadorAtual());
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
            processarPontoDePartida();
            System.out.println("PONTO DE PARTIDA: Saldo jogador " + api.getNomeJogAtual() + " = " + api.getDinheiroJogadorAtual());
        }
        
        return false; 
    }

    private boolean processarPropriedade(int posAtual) 
    {
        Api api = Api.getInstance();
        int donoId = api.getIdDono(posAtual);

        if (donoId == -1) 
        {
        	// pausa o turno e espera pela decisão do jogador
            view.mostrarOpcaoCompra(api.getNomeTerreno(posAtual), api.getValorTerreno(posAtual));
            return true; 
        } 
        else if (donoId != api.getIdJogadorAtual()) 
        {
            view.mostrarMensagem("Pagando aluguel...");
            int idTerreno = api.getPosJogadorAtual();
            boolean continuaJogo = api.pagarAluguelPropriedade(idTerreno);
            
            if (!continuaJogo) view.mostrarMensagem(api.getNomeJogAtual() + " faliu e foi retirado do jogo!");
            
            view.atualizarPaineisInfo(api.carregarPosicoesPeoes());
        } 
        else 
        {
            view.mostrarOpcaoConstruir(api.getNomePropriedade(posAtual));
            return true; 
        }
        return false;
    }
    
    private boolean processarEmpresa(int posAtual) 
    {
        Api api = Api.getInstance();
        int donoId = api.getIdDono(posAtual);

        if (donoId == -1) 
        {
            view.mostrarOpcaoCompra(api.getNomeTerreno(posAtual), api.getValorTerreno(posAtual));
            return true; 
        } 
        else if (donoId != api.getIdJogadorAtual()) 
        {
            view.mostrarMensagem("Pagando taxa...");
            int idTerreno = api.getPosJogadorAtual();
            boolean continuaJogo = api.pagarAluguelEmpresa(idTerreno, deslocamentoAtual);
            if (!continuaJogo) view.mostrarMensagem("O jogador atual faliu e foi retirado do jogo!");
            view.atualizarPaineisInfo(api.carregarPosicoesPeoes());
        } 
        else 
        {
            view.mostrarMensagem("Você parou em sua própria Empresa.");
        }
        return false;
    }
    
    private boolean processarSorte() 
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
            if(api.jogadorVaiPraPrisao() == 1) view.mostrarMensagem("Iria para a prisão, mas usou a carta de Saída Livre para sair da prisão!");
            api.descartaCartaAtual();
            
            view.atualizarPosicaoPeao(); 
            view.atualizarPaineisInfo(api.carregarPosicoesPeoes());
        } 
        else 
        {
            api.processaTransferencias();
            api.descartaCartaAtual();
            view.atualizarPaineisInfo(api.carregarPosicoesPeoes());
        }
        
        System.out.println("SORTE: Saldo jogador " + api.getNomeJogAtual() + " = " + api.getDinheiroJogadorAtual());
        
        return true; 
    }

    public void usuarioConfirmouCarta() 
    {
        finalizarTurno();
    }


    private void processarVaParaPrisao() 
    {
        view.mostrarMensagem(Api.getInstance().getNomeJogAtual() + " vai direto para a prisão!");  
        if(Api.getInstance().jogadorVaiPraPrisao() == 1) view.mostrarMensagem("Iria para a prisão, mas usou a carta de Saída Livre para sair da prisão!");     
        
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
    
    private void processarPontoDePartida()
    {
    	int valorRecebido = 200;
    	view.mostrarMensagem(Api.getInstance().getNomeJogAtual() + " ganha R$ 200 por ter passado pelo Ponto de Partida.");
        
        Api.getInstance().realizaTransferenciaBanco(valorRecebido);
        view.atualizarPaineisInfo(Api.getInstance().carregarPosicoesPeoes());
    }
    
    public void usuarioDecidiuComprar() 
    {
        int pos = Api.getInstance().getPosJogadorAtual();
        Api api = Api.getInstance();

        if (api.realizaCompraDePropriedade(pos)) 
        {
            api.setDono(pos);
            view.atualizarPaineisInfo(Api.getInstance().carregarPosicoesPeoes());
            view.mostrarMensagem("Compra realizada com sucesso!"); 
        } 
        else 
        {
            view.mostrarMensagem("Propriedade não foi comprada por falta de dinheiro.");
        }
        
        finalizarTurno();
    }


    public void usuarioDecidiuNaoComprar() 
    {
        view.mostrarMensagem("Propriedade não foi comprada.");
        
        finalizarTurno();
    }
    
   
    public void usuarioDecidiuConstruir(boolean ehCasa) 
    { 
        int pos = Api.getInstance().getPosJogadorAtual();
        
        if (Api.getInstance().realizaConstrucao(ehCasa, pos)) 
        {
            view.atualizarConstrucoes(pos); 
            view.atualizarPaineisInfo(Api.getInstance().carregarPosicoesPeoes());  
        } 
        else 
        {
            view.mostrarMensagem("Não foi possível realizar a construção.");
        }
        
        finalizarTurno();
    }

    
    public void deslocamentoPeao(int deslocamento)
    {
    	Api api = Api.getInstance();
    	
    	api.setJogadorAtual();
    	
    	int posAntiga = api.getPosJogadorAtual();

        // tabuleiro.getTamListTerreno() deve ser 40
        int posNova = (posAntiga + deslocamento) % api.getTamTabuleiro(); 

        if (posNova < posAntiga) 
        {
            System.out.println(api.getNomeJogAtual() + " passou pelo Ponto de Partida! Recebe R$ 200.");
            api.realizaTransferenciaBanco(200);
            view.atualizarPaineisInfo(api.carregarPosicoesPeoes());
        }

       api.setPosicaoPeao(posNova);
       view.atualizarPaineisInfo(api.carregarPosicoesPeoes());
        
       view.atualizarPosicaoPeao();
       view.atualizarPaineisInfo(api.carregarPosicoesPeoes());
    }
    
    
    public void terminoSolicitado() 
    {
        Api api = Api.getInstance();
        
        ArrayList<Ranking> ranking = new ArrayList<>(); // ranking não deveria ser do model??
        
        for (int i = 0; i < api.getQtdPeoes(); i++) 
        {
            String nome = api.getNomePeao(i); 
            String cor = api.getCorPeao(i);
            double dinheiro = api.getDinheiroPeao(i);
            
            ranking.add(new Ranking(nome, cor, dinheiro));
        }
        
        ranking.sort((p1, p2) -> Double.compare(p2.saldo, p1.saldo));
        
        System.out.println("Jogo encerrado! Vencedor: " + ranking.get(0).nome);
        
        view.mostrarTelaFimDeJogo(ranking);
    }
    
    public void finalizarTurno() 
    {
        Api api = Api.getInstance();

        System.out.println("Saldo final do turno - Jogador " + api.getNomeJogAtual() + " = " + api.getDinheiroJogadorAtual());

        boolean jogadorFoiRemovido = false;

        if (api.getDinheiroJogadorAtual() <= 0) 
        {
            view.mostrarMensagem(api.getNomeJogAtual() + " faliu e foi retirado do jogo!");
            api.removeJogadorAtual();
            view.atualizarPaineisInfo(api.carregarPosicoesPeoes()); 
            view.atualizarComboItens();
            
            jogadorFoiRemovido = true;
        }

        if (!jogadorFoiRemovido) {
            api.vaiProProximoTurno();
        }

        deslocamentoAtual = 0;

        view.setAguardandoProximoTurno(true);
    }
    
    public void iniciarProximoTurno() 
    {
        Api api = Api.getInstance();

    	if (api.getQtdPeoes() == 1)
    	{
    		// o vencedor estará na posição 0 da lista neste caso de ser o último que sobrou
    		System.out.println(api.getNomeVencedor() + " venceu o jogo!");
    		
    		ArrayList<Ranking> ranking = new ArrayList<>();
    		String nome = api.getNomeVencedor(); 
    		String cor = api.getCorVencedor();
    		double dinheiro = api.getDinheiroVencedor();
    		ranking.add(new Ranking(nome, cor, dinheiro));
            
    		view.mostrarTelaFimDeJogo(ranking);
    		return;
    	}
    	
    	api.setJogadorAtual();
        System.out.println("Saldo início do turno - Jogador " + api.getNomeJogAtual() + " = " + api.getDinheiroJogadorAtual());
        
        view.indicarJogadorDaVez(api.getNomeJogAtual(), api.getCorJogAtual());

        view.setAguardandoProximoTurno(false);
        view.setHabilitaSalvar(true);

    }
    
    
    public void terminarTurno() 
    {
        boolean aguardandoAcaoUsuario = processarJogada();

        if (!aguardandoAcaoUsuario) 
        {
            finalizarTurno();
        }
    }
    
    public void lancarDadosDebug(int dado1, int dado2) 
    {
    	Api api = Api.getInstance();
    	
        api.setJogadorAtual();
        view.indicarJogadorDaVez(api.getNomeJogAtual(), api.getCorJogAtual());

        // jogador preso?
        if (api.jogadorEstahNaPrisao()) 
        {
            view.mostrarDados(dado1, dado2);

            if (dado1 == dado2) {
                int deslocamento = dado1 + dado2;                
                api.libertaJodadorDaPrisao(deslocamento);
                deslocamentoPeao(deslocamento);
                
                deslocamentoAtual = deslocamento;
                
                view.mostrarMensagem("Tirou dupla. Saiu da prisão.");
                
                terminarTurno();
            } 
            else 
            {
                view.mostrarMensagem("Não tirou dupla. Fica na prisão.");
                
                deslocamentoAtual = 0;
                
//                api.vaiProProximoTurno();
//                api.setJogadorAtual();
//                view.indicarJogadorDaVez(api.getNomeJogAtual(), api.getCorJogAtual());
                
                api.vaiProProximoTurno();
                deslocamentoAtual = 0;

                view.setAguardandoProximoTurno(true);
            }
            return;
        }

        view.mostrarDados(dado1, dado2); 
        deslocamentoPeao(dado1 + dado2); // primeira dupla
        deslocamentoAtual = dado1 + dado2;

        if (dado1 == dado2) 
        {        	
        	deslocamentoPeao(dado1 + dado2); // segunda dupla
        	deslocamentoAtual += dado1 + dado2;
        	
        	deslocamentoPeao(dado1 + dado2); // terceira dupla
        	deslocamentoAtual += dado1 + dado2;
        	
        	int foiPraPrisao = api.jogadorVaiPraPrisao(); 
            if (foiPraPrisao == 1) view.mostrarMensagem("Tirou a terceira dupla. Iria para a Prisão, mas possui a Carta de Saída Livre!");
            else view.mostrarMensagem("Tirou a terceira dupla. Vai para a Prisão!");             
            
            view.atualizarPosicaoPeao();
            view.atualizarPaineisInfo(api.carregarPosicoesPeoes());
            
            api.vaiProProximoTurno();
            //api.setJogadorAtual();
            //view.indicarJogadorDaVez(api.getNomeJogAtual(), api.getCorJogAtual());
            view.setAguardandoProximoTurno(true);
        	return;            
        } 
        else 
        {
        	terminarTurno();
            return;
        }
    }
    
    public void lancarDadosReal() 
    {
        view.setHabilitaSalvar(false);

        Api api = Api.getInstance();
        api.setJogadorAtual();
        
        view.indicarJogadorDaVez(api.getNomeJogAtual(), api.getCorJogAtual());

        // jogador preso?
        if (api.jogadorEstahNaPrisao()) 
        {
            int[] dados = api.getResultadoDados();
            view.mostrarDados(dados[0], dados[1]); 
            view.registraLancamento(dados[0], dados[1], (dados[0]==dados[1]) ? "dupla — sai da prisão" : "falhou em sair");

            if (dados[0] == dados[1]) 
            {
                int deslocamento = dados[0] + dados[1];
                api.libertaJodadorDaPrisao(deslocamento);
                deslocamentoPeao(deslocamento);
                
                deslocamentoAtual = deslocamento;
                         
                view.mostrarMensagem("Tirou dupla. Saiu da prisão.");
                
                terminarTurno();
            } 
            else 
            {
                view.mostrarMensagem("Não tirou dupla. Fica na prisão.");
                
                deslocamentoAtual = 0;
                
                api.vaiProProximoTurno();
                //api.setJogadorAtual();
                //view.indicarJogadorDaVez(api.getNomeJogAtual(), api.getCorJogAtual());
                view.setAguardandoProximoTurno(true);
            }
            return;
        }

        // turno normal

        int[] dados = api.getResultadoDados();
        view.mostrarDados(dados[0], dados[1]);
        view.registraLancamento(dados[0], dados[1], (dados[0]==dados[1]) ? "dupla #1" : "");

        deslocamentoPeao(dados[0] + dados[1]);
        
        deslocamentoAtual = dados[0] + dados[1];

        if (dados[0] == dados[1]) // primeira dupla
        {
			dados = api.getResultadoDados();
            view.mostrarDados(dados[0], dados[1]);
            view.registraLancamento(dados[0], dados[1], (dados[0]==dados[1]) ? "dupla #2" : "");
            
            deslocamentoPeao(dados[0] + dados[1]);
            
            deslocamentoAtual += dados[0] + dados[1];

            if (dados[0] == dados[1]) // segunda dupla
            {
                dados = api.getResultadoDados();
                view.mostrarDados(dados[0], dados[1]);
                view.registraLancamento(dados[0], dados[1], (dados[0]==dados[1]) ? "dupla #3 → prisão" : "");
                deslocamentoPeao(dados[0] + dados[1]);
                
                deslocamentoAtual += dados[0] + dados[1];

                if (dados[0] == dados[1]) // terceira dupla
                {
                    int foiPraPrisao = api.jogadorVaiPraPrisao(); // 10 = posPrisao 
                    if (foiPraPrisao == 1) view.mostrarMensagem("Tirou a terceira dupla. Iria para a Prisão, mas possui a Carta de Saída Livre!");
                    else view.mostrarMensagem("Tirou a terceira dupla. Vai para a Prisão!");                   

                    view.atualizarPosicaoPeao();
                    view.atualizarPaineisInfo(api.carregarPosicoesPeoes());
            
                    api.vaiProProximoTurno();
                    //api.setJogadorAtual();
                    //view.indicarJogadorDaVez(api.getNomeJogAtual(), api.getCorJogAtual());
                    view.setAguardandoProximoTurno(true);
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
        } 
        else 
        {
            terminarTurno();
            return;
        }
    }

    
    public int getQtdPeoes()
    {
    	return Api.getInstance().getQtdPeoes();
    }
    
    public String getNomePeao(int index)
    {
    	return Api.getInstance().getNomePeao(index);
    }

    public int solicitarSalvamento(File arquivo)
    {
        return  Api.getInstance().salvarJogo(arquivo);
    }
}
