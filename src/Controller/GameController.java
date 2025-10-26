package Controller;

import java.util.ArrayList;

import Model.*;
import View.JanelaPrincipal;

public class GameController {
    
    private Banco banco;
    private Tabuleiro tabuleiro;
    //cartas???
    private JanelaPrincipal view;
    
    private ArrayList<String> coresDisponiveis;
    private int numJogadoresTotal;

    public GameController(Banco banco, Tabuleiro tabuleiro, JanelaPrincipal view) {
        this.banco = banco;
        this.tabuleiro = tabuleiro;
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
                // (Aqui você poderia mandar a View mostrar um erro)
            }
        } catch (NumberFormatException ex) {
            System.out.println("Entrada inválida.");
            // (Aqui também)
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
            // Manda a View mostrar um erro
            view.mostrarErroCor(true);
        }
    }
    
    public void iniciarPartida() {
        System.out.println("Começar partida");
        // (Aqui entra a lógica final de setup, como sortear a ordem, etc.)
        // ...
        
        // Manda a View mostrar o tabuleiro final
        view.mostrarTabuleiro();
    }
}