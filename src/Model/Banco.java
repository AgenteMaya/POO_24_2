package Model;

public class Banco { 
	
    private static Banco banco = null; // Banco é Singleton
    
    private Banco() {
        this.qtdDinheiro = 200000;
    }

    public static Banco getBanco() {
    	if (banco == null)
    		banco = new Banco();
        return banco;
    }
	
	int qtdDinheiro;
	
	public void compraPropriedade(int idTerreno, int idPeao, Tabuleiro tabuleiro)
	{		
		Terreno terreno = tabuleiro.getTerreno(idTerreno);
		Peao peao = tabuleiro.getPeao(idPeao);
		
		if (terreno.getDono() < 0 && peao.getDinheiro() > terreno.getValorCompra())
		{
			terreno.setDono(peao.getId());
			
			int valor = terreno.getValorCompra();
			peao.adicionaDinheiro(-valor);
			qtdDinheiro += valor;
		}
	}
	
	public boolean vendePropriedade(Peao peao, Tabuleiro tabuleiro)
	{
		int tamVetor = tabuleiro.getTamListTerreno();
		int id = peao.getId();
		for (int i = 0; i < tamVetor; i++)
		{
			Terreno terreno = tabuleiro.getTerreno(i);
			if (terreno.getDono() == id)
			{
				int valor = (int) (terreno.getValorCompra() * 0.90);
				peao.adicionaDinheiro(valor);
				qtdDinheiro -= valor;
				
				terreno.setDono(-1);				
				return true;
			}
		}
		return false;
	}
	
	public boolean pagarAluguel(Tabuleiro tabuleiro, int idPeao, int idTerreno)
	{
		int valorASerPago = 0;
		if (tabuleiro.getTerreno(idTerreno) instanceof Propriedade)
		{
			Propriedade terreno = (Propriedade) tabuleiro.getTerreno(idTerreno);
			if(terreno.getQtdCasas() > 0)
			{
				System.out.printf("Entrei no if do qtdCasas");
				if(terreno.temHotel())
				{
					valorASerPago += terreno.getVAluguel(0);
				}
				for(int i = 0; i < terreno.getQtdCasas(); i++)
				{
					valorASerPago += terreno.getVAluguel(i + 1);
				}
			}
		}
		else if (tabuleiro.getTerreno(idTerreno) instanceof Empresa)
		{
			Empresa terreno = (Empresa) tabuleiro.getTerreno(idTerreno);
			valorASerPago += terreno.getValorAluguel();
		}

		Peao peao = tabuleiro.getPeao(idPeao);
		System.out.printf("\nValor a ser pago = R$ %d,00\n\n", valorASerPago);
		
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
	
	public void constroiCasa(int idPeao, int idTerreno, Tabuleiro tabuleiro, boolean casaOuHotel)
	{
		Propriedade propriedade = (Propriedade) tabuleiro.getTerreno(idTerreno);
		Peao peao = tabuleiro.getPeao(idPeao);
		
		if(casaOuHotel) //se true, entao e casa
		{
			if (peao.getDinheiro() > propriedade.getVCompra(propriedade.getQtdCasas() + 1))
			{
				int valor = propriedade.getVCompra(propriedade.getQtdCasas() + 1);
				peao.adicionaDinheiro(-valor);
				qtdDinheiro += valor;
				
				propriedade.setMudaQtdCasa(1);
			}
		}
		
		else if (propriedade.getQtdCasas() >= 1
				&& 	!propriedade.temHotel()
				&& peao.getDinheiro() > propriedade.getVCompra(0))
		{
			int valor = propriedade.getVCompra(0);
			peao.adicionaDinheiro(-valor);
			qtdDinheiro += valor;
			
			propriedade.setTemHotel(true);;
		}
		else
		{
			System.out.printf("Nao foi possivel comprar uma nova casa ou hotel\n");
		}
	}
	
	public void realizaTransferenciaBanco(int idPeao, int valor, Tabuleiro tabuleiro)
	{
		Peao peao = tabuleiro.getPeao(idPeao);
		peao.adicionaDinheiro(valor);
		qtdDinheiro += valor;
	}
	
	public void realizaTransferenciaPeoes(int idPeao, int valor, Tabuleiro tabuleiro)
	{
		Peao peao = tabuleiro.getPeao(idPeao);
		int qtdPeoes = tabuleiro.getTamListPeoes();
		
		for (int i = 0; i < qtdPeoes; i++)
		{
			if (i != idPeao)
			{
				Peao peaoTemp = tabuleiro.getPeao(i);
				peaoTemp.adicionaDinheiro(valor);
			}
		}
		peao.adicionaDinheiro(valor * (qtdPeoes - 1));
	}
	
}
