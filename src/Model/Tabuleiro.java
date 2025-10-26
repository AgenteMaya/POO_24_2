package Model;
import java.util.ArrayList;

public class Tabuleiro 
{ 

	ArrayList<Terreno> lTerrenos=new ArrayList<>();
	ArrayList<Peao> lPeoes=new ArrayList<>();
	
	public Tabuleiro (ArrayList<Terreno> listaTerrenos)
	{
		lTerrenos.addAll(listaTerrenos);
	}
	
	public Terreno getTerreno(int pos)
	{
		return lTerrenos.get(pos);
	}
	
	public Peao getPeao(int id)
	{
		return lPeoes.get(id);
	}
	
	public ArrayList<Peao> getListaPeoes()
	{
		return lPeoes;
	}
	
	public int getTamListTerreno()
	{
		return lTerrenos.size();
	}
	
	public int getTamListPeoes()
	{
		return lPeoes.size();
	}
	
	public void addPeao(Peao peao)
	{
		lPeoes.add(peao);
	}
	
	public void removePeao(Peao peao)
	{
		lPeoes.remove(peao);
	}
	
	public void addTerreno(Terreno terreno)
	{
		lTerrenos.add(terreno);
	}

}
