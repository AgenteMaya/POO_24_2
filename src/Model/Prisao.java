package Model;

public class Prisao extends Terreno{
	
	public Prisao()
	{
		this.tipoTerreno = 3;
	}
	
	void tentaSair(Peao peao)
	{
		Dado dado = new Dado();
		int deslocamento = dado.deslocamentoSaidaPrisao();
		
		if (deslocamento != -1) 
		{
			peao.saiDaPrisao(deslocamento);
		}
	}
}
