package Model;

public class TerrenoEspecial extends Terreno{
	int posPrisao;
	
	public TerrenoEspecial(int posPrisao)
	{
		this.tipoTerreno = 4; // até o presente momento, esta classe serve para o campo de "Vá para a Prisão"
		this.posPrisao = posPrisao;
	}
	
	void irPraPrisao(Baralho baralho, Peao peao)
	{
		Carta cartaSaidaPrisao = peao.vaiPraPrisao(posPrisao);
		if (cartaSaidaPrisao == null)
		{
			System.out.printf("O jogador atual entrou no campo de ida à prisão e não tinha a carta de saída.");
		}
		else
		{
			System.out.printf("O jogador atual entrou no campo de ida à prisão e tinha a carta de saída.");
			baralho.descartarCarta(cartaSaidaPrisao);
			peao.removeCartaSaidaLivrePrisao();
		}
	}
}
