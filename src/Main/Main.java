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
        int posPrisao = 9;

        // Valores de exemplo para aluguéis e construções
        ArrayList<Integer> aluguelPadrao = criarAlugueis(10, 50, 150, 450, 800, 1200);
        ArrayList<Integer> precoCasaPadrao = criarPrecoConstrucao(50, 250);

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
        terrenos.add(new Imposto());                                        // 39: IMPOSTO

        // Garante que temos 40 terrenos
        System.out.println("Total de terrenos criados: " + terrenos.size());

        Tabuleiro tabuleiro = new Tabuleiro(terrenos);
        return tabuleiro;
    }

    public static void main(String[] args) {
        Tabuleiro tabuleiro = criaTabuleiro();
        //Baralho baralho = new Baralho();
        // criar cartas??
        
        JanelaPrincipal view = new JanelaPrincipal();
        
        GameController controller = new GameController(tabuleiro, view);
        
        view.setController(controller);
        
        view.iniciar();
    }
    
    
}