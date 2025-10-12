package Model;

class Empresa extends Terreno{	
	
	int valorAluguel = 0;
	
	Empresa(int valorA, int valorC)
	{
		valorAluguel = valorA;
		valorCompra = valorC;
	}
	
	int getValorAluguel()
	{
		return valorAluguel;
	}
	
}
