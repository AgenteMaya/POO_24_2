package Model;

class Terreno {
	
	/**
	 * 0 -> Sorte 
	 * 1 -> Propriedade 
	 * 2 -> Empresa 
	 * 3 -> Prisão
	 * 4 -> Vá para a Prisão
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

}
