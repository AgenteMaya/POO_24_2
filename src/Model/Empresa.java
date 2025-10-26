package Model;

public class Empresa extends Terreno{	
	
	int valorAluguel = 0;
	
	public Empresa(int valorA, int valorC)
	{
		this.tipoTerreno = 2;
		
		valorAluguel = valorA;
		valorCompra = valorC;
	}
	
	int getValorAluguel()
	{
		return valorAluguel;
	}
	
}
