package Main;

import Model.*;
import Controller.GameController;

import View.JanelaPrincipal;


public class Main 
{
	
	public static void main(String[] args) 
	{
         
        JanelaPrincipal view = new JanelaPrincipal();
        
        Api api = Api.getInstance();
        api.Inicializa();
        
        GameController controller = new GameController(view);
        
        view.setController(controller);
        view.iniciar();
    }
   
}