package Model;

class Terreno {
	
	/**
	 * 0 -> Sorte 
	 * 1 -> Propriedade 
	 * 2 -> Empresa 
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
	
	int valorCompra()
	{
		return valorCompra;
	}

}
