package Model;

public class Sorte extends Terreno{
	int posPrisao;
	
	public Sorte(int posPrisao)
	{
		this.tipoTerreno = 0;
		this.posPrisao = posPrisao;
	}
	
	void pegarSorte(Baralho baralho, Peao peao)
	{
		Carta cartaRetirada = baralho.pegarCarta();
		
		// se for a carta de saída livre, o peão deverá guardá-la e não deve descartá-la
		if (cartaRetirada.ehSaidaPrisao())
		{
			peao.atribuiSaidaLivrePrisao(cartaRetirada);
		}
		else if (cartaRetirada.ehIdaPrisao())
		{
			Carta cartaSaidaPrisao = peao.vaiPraPrisao(posPrisao);
			if (cartaSaidaPrisao == null)
			{
				System.out.println("O jogador atual pegou a carta de ida à prisão e não tinha a de saída.");
			}
			else
			{
				System.out.println("O jogador atual pegou a carta de ida à prisão e tinha a de saída.");
				baralho.descartarCarta(cartaSaidaPrisao);
				peao.removeCartaSaidaLivrePrisao();
			}
			baralho.descartarCarta(cartaRetirada);
		}
	}
}
