package Model;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class TerrenoEspecialTest {
	
	@Test
	public void testaEntradaComSaidaLivrePrisao()
	{
		Baralho baralhoTeste = new Baralho(new ArrayList<Carta>());
		int posPrisao = 10;
		TerrenoEspecial terreno = new TerrenoEspecial(posPrisao);
		Peao peao = new Peao(1);
		int posInicial = peao.pegaPosicaoPeao();
		
		peao.atribuiSaidaLivrePrisao(new Carta("Saída livre da prisão.", true, true, 0, true));
		
		assertEquals(true, peao.temCartaSaidaLivre());
		assertEquals(0, baralhoTeste.tamanhoListaDescarte());
		
		terreno.irPraPrisao(baralhoTeste, peao);
		
		assertEquals(false, peao.temCartaSaidaLivre());
		assertEquals(1, baralhoTeste.tamanhoListaDescarte());
		assertEquals(false, peao.estaNaPrisao());
		assertEquals(posInicial, peao.pegaPosicaoPeao());	
	}
	
	@Test
	public void testaEntradaSemSaidaLivrePrisao()
	{
		Baralho baralhoTeste = new Baralho(new ArrayList<Carta>());
		int posPrisao = 10;
		TerrenoEspecial terreno = new TerrenoEspecial(posPrisao);
		Peao peao = new Peao(1);
		
		assertEquals(false, peao.temCartaSaidaLivre());
		terreno.irPraPrisao(baralhoTeste, peao);
		
		assertEquals(true, peao.estaNaPrisao());
		assertEquals(posPrisao, peao.pegaPosicaoPeao());	
	}
}
