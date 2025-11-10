package Model;

class Terreno {
	
	/**
	 * 0 -> Sorte 
	 * 1 -> Propriedade 
	 * 2 -> Empresa 
	 * 3 -> Prisão
	 * 4 -> Vá para a Prisão
	 * 5 -> Ponto de Partida
	 * 6 -> Parada Livre
	 * 7 -> Imposto
	 * 8 -> Lucro
	 */
	int tipoTerreno;
	
	int valorCompra = 0;
	
	int indDono = -1;
	
	public void setDono(int num)
	{
		indDono = num;
	}
	
	public int getDono()
	{
		return indDono;
	}
	
	public int getValorCompra()
	{
		return valorCompra;
	}
	
	public String getNomeTerreno()
	{
		return "";
	}

}
