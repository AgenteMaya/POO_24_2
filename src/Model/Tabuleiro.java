package Model;
import java.util.ArrayList;

class Tabuleiro {
	ArrayList<Terreno> lTerrenos=new ArrayList<>();
	ArrayList<Peao> lPeoes=new ArrayList<>();
	
	Terreno getTerreno(int pos)
	{
		return lTerrenos.get(pos);
	}
	
	Peao getPeao(int id)
	{
		return lPeoes.get(id);
	}
	
	int getTamListTerreno()
	{
		return lTerrenos.size();
	}
	
	int getTamListPeoes()
	{
		return lPeoes.size();
	}
	
	void addPeao(Peao peao)
	{
		lPeoes.add(peao);
	}
	
	void removePeao(Peao peao)
	{
		lPeoes.remove(peao);
	}
	
	void addTerreno(Terreno terreno)
	{
		lTerrenos.add(terreno);
	}
	
	
	
	
	

}
