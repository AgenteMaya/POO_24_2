package Model;

import java.util.ArrayList;
import java.util.Collections;

public class Tabuleiro 
{ 

	ArrayList<Terreno> lTerrenos=new ArrayList<>();
	ArrayList<Peao> lPeoes=new ArrayList<>();
	
	private int jogadorDaVezIndex;
	
	public Tabuleiro (ArrayList<Terreno> listaTerrenos)
	{
		lTerrenos.addAll(listaTerrenos);
		this.jogadorDaVezIndex = 0;
	}
	
	public Terreno getTerreno(int pos)
	{
		return lTerrenos.get(pos);
	}
	
	public Peao getPeao(int id)
	{
		for (Peao p : lPeoes) 
		{
			if (p.getId() == id) 
			{ 
				return p;
			}
		}
		return null; 
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
	
	public void sortearOrdemJogadores()
	{
		Collections.shuffle(this.lPeoes); // sorteia a ordem dos jogadores
	}
	
	public String getPosPeaoNome(int pos) 
    {
        return lPeoes.get(pos).getNome();
    }

    public String getPosPeaoCor(int pos) 
    {
        return lPeoes.get(pos).getCor();
    }
    
    public int getPosPeao(int index) 
    {
        return lPeoes.get(index).pegaPosicaoPeao();
    }
    
    public int getIndicePeao(String cor) 
    {
    	for (int i = 0; i < getTamListPeoes(); i++)
    	{
    		if (lPeoes.get(i).getCor() == cor) return i;
    	}
        return -1;
    }
	
	public void iniciarPrimeiroTurno()
	{
		this.jogadorDaVezIndex = 0;
		
		for (Peao p : lPeoes) {
			p.setaPosicaoPeao(0); 
		}
	}

	public Peao getJogadorDaVez()
	{
		if (lPeoes.isEmpty()) {
			return null;
		}
		return lPeoes.get(jogadorDaVezIndex);
	}
	
	public void proximoTurno()
	{
		if (!lPeoes.isEmpty()) {
			this.jogadorDaVezIndex = (this.jogadorDaVezIndex + 1) % lPeoes.size();
		}
	}

}
