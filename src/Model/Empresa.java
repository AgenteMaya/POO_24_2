package Model;

public class Empresa extends Terreno{	
	
	int valorAluguel = 0;
	String nomeEmpresa;
	
	public Empresa(String nome, int valorA, int valorC)
	{
		this.tipoTerreno = 2;
		
		nomeEmpresa = nome;
		valorAluguel = valorA;
		valorCompra = valorC;
	}
	
	@Override
	public String getNomeTerreno()
	{
		return nomeEmpresa;
	}
	
	int getValorAluguel()
	{
		return valorAluguel;
	}
	
}
