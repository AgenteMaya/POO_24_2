package Model;
import java.util.ArrayList;

class Propriedade extends Terreno{
	
	//hotel - 0; 1 = 1 casa; 2 = 2 casas...
	ArrayList<Integer> lVAluguel = new ArrayList<>();
	ArrayList<Integer> lVCompra = new ArrayList<>();
	
	int qtdCasas = 0;
	boolean temHotel = false;
	
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
	
	
}
