package Model;

class Empresa extends Terreno{	
	
	int valorTaxa = 0;
	String nomeEmpresa;
	
	public Empresa(String nome, int valorT, int valorC)
	{
		this.tipoTerreno = 2;
		
		nomeEmpresa = nome;
		valorTaxa = valorT;
		valorCompra = valorC;
	}
	
	@Override
	public String getNomeTerreno()
	{
		return nomeEmpresa;
	}
	
	int getValorTaxa()
	{
		return valorTaxa;
	}
	
}
