package Main;

import Model.*;
import Controller.GameController;

import View.JanelaPrincipal;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	
	//Função auxiliar para criar dados de aluguel de exemplo. --> MUDAR DEPOIS
    private static ArrayList<Integer> criarAlugueis(int base, int c1, int c2, int c3, int c4, int h) {
        return new ArrayList<>(Arrays.asList(base, c1, c2, c3, c4, h));
    }

    //Função auxiliar para criar dados de compra de construção. --> MUDAR DEPOIS
    private static ArrayList<Integer> criarPrecoConstrucao(int precoCasa, int precoHotel) {
        return new ArrayList<>(Arrays.asList(precoCasa, precoHotel));
    }	

	static Tabuleiro criaTabuleiro()
    {
        ArrayList<Terreno> terrenos = new ArrayList<>();
        int posPrisao = 10;

        // Valores de exemplo para aluguéis e construções
        ArrayList<Integer> aluguelPadrao = criarAlugueis(10, 50, 150, 450, 800, 1200);
        ArrayList<Integer> precoCasaPadrao = criarPrecoConstrucao(50, 250);

        // -- LADO 1 (EMBAIXO) --
        terrenos.add(new PontoDePartida());                                
        terrenos.add(new Propriedade("Leblon", aluguelPadrao, precoCasaPadrao, 60)); 
        terrenos.add(new Sorte(posPrisao));                                
        terrenos.add(new Propriedade("Av. Presidente Vargas", aluguelPadrao, precoCasaPadrao, 60)); 
        terrenos.add(new Propriedade("Av. Nossa S. de Copacabana", aluguelPadrao, precoCasaPadrao, 70)); 
        terrenos.add(new Empresa("Companhia Ferroviária", 100, 200));                        
        terrenos.add(new Propriedade("Av. Brig. Faria Lima", aluguelPadrao, precoCasaPadrao, 100));
        terrenos.add(new Empresa("Companhia de Viação", 100, 200));   
        terrenos.add(new Propriedade("Avenida Rebouças", aluguelPadrao, precoCasaPadrao, 100));
        terrenos.add(new Propriedade("Av. 9 de Julho", aluguelPadrao, precoCasaPadrao, 120)); 
        terrenos.add(new Prisao());                                         

        // -- LADO 2 (ESQUERDA) --
        terrenos.add(new Propriedade("Av. Europa", aluguelPadrao, precoCasaPadrao, 140));
        terrenos.add(new Sorte(posPrisao));                          
        terrenos.add(new Propriedade("Rua Augusta", aluguelPadrao, precoCasaPadrao, 140)); 
        terrenos.add(new Propriedade("Av. Pacaembú", aluguelPadrao, precoCasaPadrao, 160));
        terrenos.add(new Empresa("Companhia de Táxi", 100, 200));                               
        terrenos.add(new Sorte(posPrisao));                                
        terrenos.add(new Propriedade("Interlagos", aluguelPadrao, precoCasaPadrao, 180)); 
        terrenos.add(new Lucros());                            
        terrenos.add(new Propriedade("Morumbi", aluguelPadrao, precoCasaPadrao, 200)); 
        terrenos.add(new ParadaLivre());

        // -- LADO 3 (CIMA) --
        terrenos.add(new Propriedade("Flamengo", aluguelPadrao, precoCasaPadrao, 220)); 
        terrenos.add(new Sorte(posPrisao));                                 
        terrenos.add(new Propriedade("Botafogo", aluguelPadrao, precoCasaPadrao, 220)); 
        terrenos.add(new Imposto());                               
        terrenos.add(new Empresa("Companhia de Navegação", 100, 200));                             
        terrenos.add(new Propriedade("Av. Brasil", aluguelPadrao, precoCasaPadrao, 260)); 
        terrenos.add(new Sorte(posPrisao));                          
        terrenos.add(new Propriedade("Av. Paulista", aluguelPadrao, precoCasaPadrao, 260)); 
        terrenos.add(new Propriedade("Jardim Europa", aluguelPadrao, precoCasaPadrao, 280));
        terrenos.add(new IrPraPrisao(posPrisao));

        // -- LADO 4 (DIREITA) --
        terrenos.add(new Propriedade("Copacabana", aluguelPadrao, precoCasaPadrao, 300)); 
        terrenos.add(new Empresa("Companhia de Aviação", 100, 200));                            
        terrenos.add(new Propriedade("Av. Vieira Souto", aluguelPadrao, precoCasaPadrao, 300)); 
        terrenos.add(new Propriedade("Av. Atlântica", aluguelPadrao, precoCasaPadrao, 320));
        terrenos.add(new Empresa("Companhia de Táxi Aéreo", 100, 200));                               
        terrenos.add(new Propriedade("Ipanema", aluguelPadrao, precoCasaPadrao, 350)); 
        terrenos.add(new Sorte(posPrisao));                               
        terrenos.add(new Propriedade("Jardim Paulista", aluguelPadrao, precoCasaPadrao, 370));
        terrenos.add(new Propriedade("Brooklin", aluguelPadrao, precoCasaPadrao, 400)); 
        terrenos.add(new ParadaLivre()); 

        // Garante que temos 40 terrenos
        System.out.println("Total de terrenos criados: " + terrenos.size());

        Tabuleiro tabuleiro = new Tabuleiro(terrenos);
        return tabuleiro;
    }
	
	static Baralho criaBaralho()
    {
        ArrayList<Carta> todasCartas = new ArrayList<>();
                
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
        
        // --- CARTAS REVÉS ---
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
        
        return new Baralho(todasCartas);
    }

    public static void main(String[] args) {
        Tabuleiro tabuleiro = criaTabuleiro();
        Baralho baralho = criaBaralho();
        
        JanelaPrincipal view = new JanelaPrincipal();
        
        GameController controller = new GameController(tabuleiro, baralho, view);
        
        view.setController(controller);
        
        view.iniciar();
    }
    
    
}