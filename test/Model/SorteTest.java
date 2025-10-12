package Model;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class SorteTest {

    public Baralho criaBaralhoSaidaPrisao() {
		ArrayList<Carta> listaCartasTeste = new ArrayList<Carta>();
		
		listaCartasTeste.add(new Carta("Saída livre da prisão.", true, true, 0, true));
    	
    	return new Baralho(listaCartasTeste);
	}
    
    public Baralho criaBaralhoIdaPrisao() {
		ArrayList<Carta> listaCartasTeste = new ArrayList<Carta>();
		
		listaCartasTeste.add(new Carta("Vá para a prisão.", false, true, 0, true));
    	
    	return new Baralho(listaCartasTeste);
	}
	
	@Test
	public void testaPegaCartaSaída()
	{
		Baralho baralhoTeste = criaBaralhoSaidaPrisao();
		int posPrisao = 10;
		Sorte sorte = new Sorte(posPrisao);
		Peao peao = new Peao(1);
		int posInicial = peao.pegaPosicaoPeao();
		
		sorte.pegarSorte(baralhoTeste, peao);
		
		assertEquals(false, peao.estaNaPrisao());
		assertEquals(true, peao.temCartaSaidaLivre());	
		assertEquals(posInicial, peao.pegaPosicaoPeao());	
	}
	
	@Test
	public void testaPegaCartaIdaPrisao()
	{
		Baralho baralhoTeste = criaBaralhoIdaPrisao();
		int posPrisao = 10;
		Sorte sorte = new Sorte(posPrisao);
		Peao peao = new Peao(1);
		
		sorte.pegarSorte(baralhoTeste, peao);
		
		assertEquals(true, peao.estaNaPrisao());
		assertEquals(false, peao.temCartaSaidaLivre());	
		assertEquals(posPrisao, peao.pegaPosicaoPeao());	
	}
	
	@Test
	public void testaPegaCartaIdaPrisaoTendoCartaSaida()
	{
		Baralho baralhoTeste = criaBaralhoSaidaPrisao();
		int posPrisao = 10;
		Sorte sorte = new Sorte(posPrisao);
		Peao peao = new Peao(1);
		int posInicial = peao.pegaPosicaoPeao();
		
		sorte.pegarSorte(baralhoTeste, peao);
		
		assertEquals(false, peao.estaNaPrisao());
		assertEquals(true, peao.temCartaSaidaLivre());	
		assertEquals(posInicial, peao.pegaPosicaoPeao());
		
		baralhoTeste = criaBaralhoIdaPrisao();
		sorte.pegarSorte(baralhoTeste, peao);
		
		assertEquals(false, peao.estaNaPrisao());
		assertEquals(false, peao.temCartaSaidaLivre());	
		assertEquals(posInicial, peao.pegaPosicaoPeao());	
	}

}
