package Model;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class SorteTest {

    public Baralho criaBaralhoSaidaPrisao() {
		ArrayList<Carta> listaCartasTeste = new ArrayList<Carta>();
		
		listaCartasTeste.add(new Carta(9, "Saída livre da prisão.", true, true, 0, true));
    	
    	return new Baralho(listaCartasTeste);
	}
    
    public Baralho criaBaralhoIdaPrisao() {
		ArrayList<Carta> listaCartasTeste = new ArrayList<Carta>();
		
		listaCartasTeste.add(new Carta(23, "Vá para a prisão sem receber nada. (talvez eu lhe faça uma visita...)", false, true, 0, true));
    	
    	return new Baralho(listaCartasTeste);
	}
	
	@Test
	public void testaPegaCartaSaída()
	{
		Peao peao = new Peao(1);
		int posInicial = peao.pegaPosicaoPeao();
		
		Carta saida = new Carta(9, "Saída livre da prisão.", true, true, 0, true);
		
		peao.atribuiSaidaLivrePrisao(saida);
		
		assertEquals(false, peao.estaNaPrisao());
		assertEquals(true, peao.temCartaSaidaLivre());	
		assertEquals(posInicial, peao.pegaPosicaoPeao());	
	}
	
	@Test
	public void testaPegaCartaIdaPrisao()
	{
		int posPrisao = 10;
		Peao peao = new Peao(1);
		
		Carta cartaSaidaPrisao = peao.vaiPraPrisao(posPrisao);
		
		assertEquals(null, cartaSaidaPrisao);
		assertEquals(true, peao.estaNaPrisao());
		assertEquals(false, peao.temCartaSaidaLivre());	
		assertEquals(posPrisao, peao.pegaPosicaoPeao());	
	}
	
	@Test
	public void testaPegaCartaIdaPrisaoTendoCartaSaida()
	{
		Peao peao = new Peao(1);
		int posInicial = peao.pegaPosicaoPeao();
		
		Carta saida = new Carta(9, "Saída livre da prisão.", true, true, 0, true);
		peao.atribuiSaidaLivrePrisao(saida);      
		
		assertEquals(false, peao.estaNaPrisao());
		assertEquals(true, peao.temCartaSaidaLivre());	
		assertEquals(posInicial, peao.pegaPosicaoPeao());
		
		peao.removeCartaSaidaLivrePrisao();
		
		assertEquals(false, peao.estaNaPrisao());
		assertEquals(false, peao.temCartaSaidaLivre());	
		assertEquals(posInicial, peao.pegaPosicaoPeao());	
	}

}
