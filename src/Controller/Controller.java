package Controller;
import javax.swing.*;
//import java.awt.*;

@SuppressWarnings("serial")
class Controller extends JFrame{
	
	public final int LARG_DEFAULT = 1280;
	public final int ALT_DEFAULT = 800;
	
	Controller()
	{
		setSize(LARG_DEFAULT, ALT_DEFAULT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) 
	{
		Controller c = new Controller();
		
		c.setTitle("Banco Imobiliário");
		c.setVisible(true);
	}
	
}
