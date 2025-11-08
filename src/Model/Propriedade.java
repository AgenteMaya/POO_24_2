package Model;
import java.util.ArrayList;

class Propriedade extends Terreno{
	
	//hotel - 0; 1 = 1 casa; 2 = 2 casas...
	ArrayList<Integer> lVAluguel;
	ArrayList<Integer> lVCompra;
	
	int qtdCasas = 0;
	boolean temHotel = false;
	
	public Propriedade(ArrayList<Integer> lAlug, ArrayList<Integer> lComp, int valorC)
	{
		this.tipoTerreno = 1;
		
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
	
}
