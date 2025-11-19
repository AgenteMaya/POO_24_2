package Model;
import java.util.ArrayList;

class Propriedade extends Terreno
{
	int qtdCasas = 0;
	boolean temHotel = false;
	String nomeTerreno;
	
	public Propriedade(String nome, int valorC)
	{
		this.tipoTerreno = 1;
		
		nomeTerreno = nome;
		valorCompra = valorC;
	}
	
	boolean temHotel()
	{
		return temHotel;
	}
	
	@Override
	public String getNomeTerreno()
	{
		return nomeTerreno;
	}
	
	int getQtdCasas()
	{
		return qtdCasas;
	}
	
	void setMudaQtdCasa(int x)
	{
		qtdCasas += x;
	}
	
	void setTemHotel(boolean b)
	{
		temHotel = b;
	}
	
	double getAluguel()
	{
		return valorCompra * 0.1 + getValorCasa() + getValorHotel();
	}
	
	double getValorCasa()
	{
		return (valorCompra * 0.15) * qtdCasas;
	}
	
	double getValorHotel()
	{
		double Vh = 0;
		if (temHotel)
		{
			Vh = valorCompra * 0.3;
		}
		return Vh;
	}
	
}
