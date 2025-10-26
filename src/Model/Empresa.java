package Model;

class Empresa extends Terreno{	
	
	int valorAluguel = 0;
	
	Empresa(int valorA, int valorC)
	{
		this.tipoTerreno = 2;
		
		valorAluguel = valorA;
		valorCompra = valorC;
	}
	
	int getValorAluguel()
	{
		return valorAluguel;
	}
	
	void realizaAcao(Baralho baralho, Banco banco, Peao peao)
	{
		if (getDono() == -1) 
		{
			// perguntar pela interface se usuário deseja comprar
			// se sim:
			// banco.compraPropriedade()
        } 
		else 
		{
            // banco.pagarAluguel(tabuleiro, peao.getId(), getId());

        } 
	}
	
}
