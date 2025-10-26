package Model;

class Peao {
	int id;
	int dinheiro;
	int posicao;
	String cor;
	String nome;
	
	boolean naPrisao = false;
	boolean possuiSaidaLivrePrisao = false;
	Carta cartaSaidaPrisao = null;
	
    /**
     * Construtor do Peão do jogador que recebe o id do mesmo
     * @param id_peao indice que identifica qual é o peão do jogador
     */
	public Peao(int id_peao) {
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
	
	public void setaPosicaoPeao(int pos, Tabuleiro tabuleiro, Banco banco) {
		if (naPrisao)
		{
			System.out.printf("O jogador %d está na prisão e, portanto, não pode se deslocar", id);
			return;
		}
		posicao = pos;
		
		//no instante so estao sendo verificados as posicoes dos de aluguel para pagamento automatico
		if (posicao < tabuleiro.getTamListTerreno() && posicao != tabuleiro.getTerreno(pos).getDono())
		{
			banco.pagarAluguel(tabuleiro, id, pos);
		}
		
		System.out.printf("Jogador %d está na posição %d do tabuleiro\n", id, posicao);
	}
	
    /**
     * Função que pega a posição da intância de um peão
     * @return
     */
	public int pegaPosicaoPeao() {
		return posicao;
	}		
	
    /**
     * Função que seta o montante de dinheiro do jogador
     * @param d
     */
	void setDinheiro(int d)
	{
		dinheiro = d;
	}

    /**
     * Função que retorna o montante da instância de um peão
     * @return
     */
	int getDinheiro()
	{
		return dinheiro;
	}
	
    /**
     * Função que adiciona um valor ao montante existente do peao
     * @param d
     */
	void adicionaDinheiro(int d)
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
