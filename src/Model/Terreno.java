package Model;

public class Terreno {
	
	/**
	 * 0 -> Sorte 
	 * 1 -> Propriedade 
	 * 2 -> Empresa 
	 * 3 -> Prisão
	 * 4 -> Vá para a Prisão
	 * 5 -> Ponto de Partida
	 * 6 -> Parada Livre
	 * 7 -> imposto
	 */
	int tipoTerreno;
	
	int valorCompra = 0;
	
	int indDono = -1;
	
	void setDono(int num)
	{
		indDono = num;
	}
	
	int getDono()
	{
		return indDono;
	}
	
	int getValorCompra()
	{
		return valorCompra;
	}
	
	void realizaAcao(Baralho baralho, Banco banco, Peao peao) 
	{
		// --> todas as implementações deste método vão para o controller
		// alterar os testes!
		System.out.println("Realiza ação da casa correspondente.");
	}

}
