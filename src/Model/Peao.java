package Model;

class Peao {
	int id;
	double dinheiro;
	int posicao;
	String cor;
	String nome;
	
	boolean naPrisao = false;
	boolean possuiSaidaLivrePrisao = false;
	Carta cartaSaidaPrisao = null;
	
	Peao(int id_peao) 
	{
		this.id = id_peao;
	} 
	
	void setCor(String cor) 
	{
		this.cor = cor;
	}
	
	void setNome(String nome) 
	{
		this.nome = nome;
	}

	String getCor()
	{
		return this.cor;
	}
	
	String getNome()
	{
		return this.nome;
	}
	
    void setaPosicaoPeao(int pos) 
    {
		if (naPrisao)
		{
			System.out.printf("O jogador %d está na prisão e, portanto, não pode se deslocar", id);
			return;
		}
		posicao = pos;
		System.out.printf("Jogador %d está na posição %d do tabuleiro\n", id, posicao);
	}
	
	int pegaPosicaoPeao() {
		return posicao;
	}		
	
	void setDinheiro(double d)
	{
		dinheiro = d;
	}

	double getDinheiro()
	{
		return dinheiro;
	}
	
	void adicionaDinheiro(double d)
	{
		dinheiro += d;
	}
	
	int getId()
	{
		return id;
	}
	
	// função que verifica se o peão vai para a prisão e, se for, realiza seu deslocamento
	Carta vaiPraPrisao(int posPrisao)
	{
		posicao = posPrisao;
		if (cartaSaidaPrisao != null) 
		{
			return cartaSaidaPrisao;
		}
		
		naPrisao = true;
		return cartaSaidaPrisao;
	}
	
	void saiDaPrisao(int deslocamento)
	{
		if (!naPrisao) return;
		
		naPrisao = false;
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
