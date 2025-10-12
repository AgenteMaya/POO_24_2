package Model;

class Peao {
	int id;
	int dinheiro;
	int posicao;
	
	public Peao(int id_peao) {
		id = id_peao;
	} 
		
	public void setaPosicaoPeao(int pos, Banco banco, Tabuleiro tabuleiro) {
		posicao = pos;
		System.out.printf("Jogador %d está na posição %d do tabuleiro\n", id, posicao);
		
		if (id != tabuleiro.getTerreno(pos).getDono())
		{
			banco.pagarAluguel(tabuleiro, id, pos);
		}
		return;
	}
	
	public int pegaPosicaoPeao(int id_peao) {
		return posicao;
	}		
	
	void setDinheiro(int n)
	{
		dinheiro = n;
	}

	int getDinheiro()
	{
		return dinheiro;
	}
	
	void adicionaDinheiro(int n)
	{
		dinheiro += n;
	}
	
	int getId()
	{
		return id;
	}
	
}
