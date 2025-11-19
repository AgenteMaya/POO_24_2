package Model;

class Banco { 
	
    private static Banco banco = null; // Banco é Singleton
    
    private Banco() {
        this.qtdDinheiro = 200000;
    }

    static Banco getBanco() {
    	if (banco == null)
    		banco = new Banco();
        return banco;
    }
	
	double qtdDinheiro;
	
	boolean compraPropriedade(int idTerreno, int idPeao, Tabuleiro tabuleiro)
	{		
		Terreno terreno = tabuleiro.getTerreno(idTerreno);
		Peao peao = tabuleiro.getPeao(idPeao);
		
		if (terreno.getDono() < 0 && peao.getDinheiro() > terreno.getValorCompra())
		{
			terreno.setDono(peao.getId());
			
			double valor = terreno.getValorCompra();
			peao.adicionaDinheiro(-valor);
			qtdDinheiro += valor;
			return true;
		}
		return false;
	}
	
	boolean vendePropriedade(Peao peao, Tabuleiro tabuleiro)
	{
		int tamVetor = tabuleiro.getTamListTerreno();
		int id = peao.getId();
		for (int i = 0; i < tamVetor; i++)
		{
			Terreno terreno = tabuleiro.getTerreno(i);
			if (terreno.getDono() == id)
			{
				double valor = terreno.getValorCompra() * 0.90;
				peao.adicionaDinheiro(valor);
				qtdDinheiro -= valor;
				
				terreno.setDono(-1);				
				return true;
			}
		}
		return false;
	}
	
	boolean pagarAluguelPropriedade(Tabuleiro tabuleiro, int idPeao, int idTerreno)
	{
		Propriedade propriedadeCaida = (Propriedade)tabuleiro.getTerreno(idTerreno);
		double valorASerPago = propriedadeCaida.getAluguel();

		Peao peao = tabuleiro.getPeao(idPeao);
		System.out.printf("\nValor a ser pago = R$ %f\n\n", valorASerPago);
		
		while (valorASerPago > peao.getDinheiro())
		{	
			if(!vendePropriedade(peao, tabuleiro))
			{
				System.out.printf("\nO jogador %d faliu e, portanto, sairá do jogo. Saldo final = R$ %d,00.\n\n", idPeao, peao.getDinheiro());
				tabuleiro.removePeao(peao); // remove o peão, pois ele foi à falência
				return false;
			}
		}
		
		peao.adicionaDinheiro(-valorASerPago);
		
		Peao dono = tabuleiro.getPeao(tabuleiro.getTerreno(idTerreno).getDono());
		dono.adicionaDinheiro(valorASerPago);
		return true;
	}
	
	boolean pagarAluguelEmpresa(Tabuleiro tabuleiro, int idPeao, int idTerreno, int deslocamento)
	{
		Empresa empresaCaida = (Empresa)tabuleiro.getTerreno(idTerreno);
		double valorASerPago = empresaCaida.getValorTaxa() * deslocamento;

		Peao peao = tabuleiro.getPeao(idPeao);
		System.out.printf("\nValor a ser pago = R$ %f\n\n", valorASerPago);
		
		while (valorASerPago > peao.getDinheiro())
		{	
			if(!vendePropriedade(peao, tabuleiro))
			{
				System.out.printf("\nO jogador %d faliu e, portanto, sairá do jogo. Saldo final = R$ %d,00.\n\n", idPeao, peao.getDinheiro());
				tabuleiro.removePeao(peao); // remove o peão, pois ele foi à falência
				return false;
			}
		}
		
		peao.adicionaDinheiro(-valorASerPago);
		
		Peao dono = tabuleiro.getPeao(tabuleiro.getTerreno(idTerreno).getDono());
		dono.adicionaDinheiro(valorASerPago);
		return true;
	}
	
	boolean constroiCasa(int idPeao, int idTerreno, Tabuleiro tabuleiro, boolean casaOuHotel)
	{
		Propriedade propriedade = (Propriedade) tabuleiro.getTerreno(idTerreno);
		Peao peao = tabuleiro.getPeao(idPeao);
		
		if(casaOuHotel && propriedade.getQtdCasas() < 5) //se true, entao e casa
		{
			if (peao.getDinheiro() > propriedade.getValorCasa())
			{
				double valor = propriedade.getValorCasa();
				peao.adicionaDinheiro(-valor);
				qtdDinheiro += valor;
				
				propriedade.setMudaQtdCasa(1);
				return true;
			}
		}
		else if (propriedade.getQtdCasas() >= 1
				&& 	!propriedade.temHotel()
				&& peao.getDinheiro() > propriedade.getValorHotel())
		{
			double valor = propriedade.getValorHotel();
			peao.adicionaDinheiro(-valor);
			qtdDinheiro += valor;
			
			propriedade.setTemHotel(true);
			return true;
		}

		System.out.printf("Não foi possivel comprar uma nova casa ou hotel\n");
		return false;
	}
	
	void realizaTransferenciaBanco(int idPeao, int valor, Tabuleiro tabuleiro)
	{
		Peao peao = tabuleiro.getPeao(idPeao);
		peao.adicionaDinheiro(valor);
		qtdDinheiro += valor;
	}
	
	void realizaTransferenciaPeoes(int idPeao, int valor, Tabuleiro tabuleiro)
	{
		Peao peao = tabuleiro.getPeao(idPeao);
		int qtdPeoes = tabuleiro.getTamListPeoes();
		
		for (int i = 0; i < qtdPeoes; i++)
		{
			if (i != idPeao)
			{
				Peao peaoTemp = tabuleiro.getPeaoPorPos(i);
				peaoTemp.adicionaDinheiro(valor);
			}
		}
		peao.adicionaDinheiro(valor * (qtdPeoes - 1));
	}
	
}
