package Observer;

import java.util.ArrayList;

public interface ObservadoIF 
{
	void add(ObservadorIF o);
	
	void remove(ObservadorIF o);
	
	// método auxiliar
	int getQtdPeoes();
	
	// métodos para os peões
	double getDinheiroPeao(String nome);
	
	String getCorPeao(String nome);
	
	String getNomePeao(String nome);
	String getNomePeao(int index);
	
	int getPosicaoPeao(String nome);
    
	boolean isPeaoPreso(String nome);
	
	boolean temCartaSaidaLivre(String nome);
	
	ArrayList<String> getPropriedadesPeao(String nome);
	
	// métodos para as propriedades
	int getQtdCasas(String nome);
	
	boolean getTemHotel(String nome);
	
	String getDonoPropriedade(String nome);
	
	double getValorAluguel(String nome);
	
	// métodos para as companhias
	String getDonoCompanhia(String nome);
	
	int getTaxaCompanhia(String nome);
}
