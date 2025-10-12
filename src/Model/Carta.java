package Model;


class Carta {
	String descricao;
	boolean isSorte; // para verificar se a carta é de sorte ou não
	boolean envolvePrisao; // para verificar se a carta envolve prisão ou não
	
	int valor;
	boolean tranferenciaBanco; // para verificar se a transferência envolve o banco (true) ou peoes (false)
	
	/**
     * Construtor para cartas.
     * @param descricao é a instrução na carta.
     * @param sorte indica se a carta é de sorte ou de revés.
     * @param prisao indica se a carta envolve a prisão ou não.
     * @param valor indica o valor da carta, se for de prisão o valor será 0.
     * @param tipoTransferencia indica se a tranferência irá ocorrer entre peão e banco ou entre peões.
     */
	public Carta(String descricao, boolean sorte, boolean prisao, int valor, boolean tipoTransferencia) {
        this.descricao = descricao;
        this.isSorte = sorte;
        this.envolvePrisao = prisao;
        this.valor = valor;
        this.tranferenciaBanco = tipoTransferencia;
    }
	
	// função para auxiliar na transferência do valor --> enviar banco?
	
	// Função para verificar se a carta é de Saída da Prisão.
	boolean ehSaidaPrisao()
	{
		return isSorte && envolvePrisao;
	}
	
	// Função para verificar se a carta é de Ida para a Prisão.
	boolean ehIdaPrisao()
	{
		return !isSorte && envolvePrisao;
	}
	
	// Função para pegar a descrição da carta.
	public String getDescricao() 
	{
        return descricao;
    }
}
