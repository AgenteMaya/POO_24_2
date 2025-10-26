package Main;

import Model.Banco;
import Model.Tabuleiro;
import View.JanelaPrincipal;
import Controller.GameController;

public class Main {

    public static void main(String[] args) {
        Banco banco = new Banco(); 
        Tabuleiro tabuleiro = new Tabuleiro();
        // criar cartas
        
        JanelaPrincipal view = new JanelaPrincipal();
        
        GameController controller = new GameController(banco, tabuleiro, view);
        
        view.setController(controller);
        
        view.iniciar();
    }
}