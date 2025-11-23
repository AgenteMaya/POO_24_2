package Model;

import java.util.ArrayList;
import java.util.Collections;

class Tabuleiro 
{ 

	ArrayList<Terreno> lTerrenos=new ArrayList<>();
	ArrayList<Peao> lPeoes=new ArrayList<>();
	
	private int jogadorDaVezIndex;
	
	Tabuleiro (ArrayList<Terreno> listaTerrenos)
	{
		lTerrenos.addAll(listaTerrenos);
		this.jogadorDaVezIndex = 0;
	}
	
	ArrayList<Terreno> getListaTerrenos()
	{
		return lTerrenos;
	}
	
	Terreno getTerreno(int pos)
	{
		return lTerrenos.get(pos);
	}
	
	Peao getPeao(int id)
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
	
	ArrayList<Peao> getListaPeoes()
	{
		return lPeoes;
	}

	ArrayList<Terreno> getListaTerrenos()
	{
		return lTerrenos;
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
	
	void sortearOrdemJogadores()
	{
		Collections.shuffle(this.lPeoes); // sorteia a ordem dos jogadores
	}
	
	String getPosPeaoNome(int pos) 
    {
        return lPeoes.get(pos).getNome();
    }

    String getPosPeaoCor(int pos) 
    {
        return lPeoes.get(pos).getCor();
    }
    
    int getPosPeao(int index) 
    {
        return lPeoes.get(index).pegaPosicaoPeao();
    }
    
    Peao getPeaoPorPos(int pos) 
    {
        return lPeoes.get(pos);
    }
    
    int getIndicePeao(String cor) 
    {
    	for (int i = 0; i < getTamListPeoes(); i++)
    	{
    		if (lPeoes.get(i).getCor() == cor) return i;
    	}
        return -1;
    }
	
	void iniciarPrimeiroTurno()
	{
		this.jogadorDaVezIndex = 0;
		
		for (Peao p : lPeoes) {
			p.setaPosicaoPeao(0); 
		}
	}

	Peao getJogadorDaVez()
	{
		if (lPeoes.isEmpty()) {
			return null;
		}
		return lPeoes.get(jogadorDaVezIndex);
	}

	void setJogadorDaVezIndex(int index)
	{
		if (index >= 0 && index < lPeoes.size()) {
			this.jogadorDaVezIndex = index;
		}
	}

	void setJogadorDaVezIndexManual(int index)
	{
		System.out.println("Definindo jogador da vez para o índice: " + index);
		this.jogadorDaVezIndex = index;
		System.out.println("Jogador da vez agora é: " + this.jogadorDaVezIndex);
	}
	
	void proximoTurno()
	{
		if (!lPeoes.isEmpty()) {
			this.jogadorDaVezIndex = (this.jogadorDaVezIndex + 1) % lPeoes.size();
		}
	}

}
