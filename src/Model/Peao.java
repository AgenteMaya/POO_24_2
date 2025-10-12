package Model;

class Peao {
	int id;
	int dinheiro;
	int posicao;
	
	boolean naPrisao = false;
	boolean possuiSaidaLivrePrisao = false;
	Carta cartaSaidaPrisao = null;
	
	public Peao(int id_peao) {
		id = id_peao;
	} 
		
	public void setaPosicaoPeao(int pos) {
		if (naPrisao)
		{
			System.out.printf("O jogador %d está na prisão e, portanto, não pode se deslocar", id);
			return;
		}
		posicao = pos;
		System.out.printf("Jogador %d está na posição %d do tabuleiro\n", id, posicao);
	}
	
	public int pegaPosicaoPeao() {
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
	
	// função que verifica se o peão vai para a prisão e, se for, realiza seu deslocamento
	Carta vaiPraPrisao(int posPrisao)
	{
		if (cartaSaidaPrisao != null) 
		{
			return cartaSaidaPrisao;
		}
		
		naPrisao = true;
		posicao = posPrisao;
		return cartaSaidaPrisao;
	}
	
	void saiDaPrisao(int deslocamento)
	{
		if (!naPrisao) return;
		
		naPrisao = false;
		posicao = deslocamento;
	}
	
	void atribuiSaidaLivrePrisao(Carta carta)
	{
		cartaSaidaPrisao = carta;
	}
	
	void removeCartaSaidaLivrePrisao()
	{
		cartaSaidaPrisao = null;
	}
	
	boolean estaNaPrisao()
	{
		return naPrisao;
	}
	
	boolean temCartaSaidaLivre()
	{
		return cartaSaidaPrisao != null;
	}
	
}
