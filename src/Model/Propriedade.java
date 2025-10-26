package Model;
import java.util.ArrayList;

class Propriedade extends Terreno{
	
	//hotel - 0; 1 = 1 casa; 2 = 2 casas...
	ArrayList<Integer> lVAluguel;
	ArrayList<Integer> lVCompra;
	
	int qtdCasas = 0;
	boolean temHotel = false;
	
	Propriedade(ArrayList<Integer> lAlug, ArrayList<Integer> lComp, int valorC)
	{
		this.tipoTerreno = 0;
		
		lVAluguel = new ArrayList<>(lAlug);
		lVCompra = new ArrayList<>(lComp);
		valorCompra = valorC;
	}
	
	boolean temHotel()
	{
		return temHotel;
	}
	
	int getVAluguel(int num)
	{
		return lVAluguel.get(num);
	}
	
	int getVCompra(int num)
	{
		return lVCompra.get(num);
	}
	
	int getQtdCasas()
	{
		return qtdCasas;
	}
	
	void setMudaQtdCasa(int x)
	{
		qtdCasas += x;
	}
	
	void setTemHotel(boolean b)
	{
		temHotel = b;
	}
	
	void realizaAcao(Baralho baralho, Banco banco, Peao peao)
	{
		if (getDono() == -1) 
		{
			// perguntar pela interface se usuário deseja comprar
			// se sim:
			// banco.compraPropriedade()
        } 
		else if (getDono() != peao.getId()) 
		{
            // banco.pagarAluguel(tabuleiro, peao.getId(), getId());

        } 
		else 
		{
            // perguntar se o usuário deseja contruir algo
			// se sim:
				// perguntar o que ele deseja construir
			
        }
	}
}
