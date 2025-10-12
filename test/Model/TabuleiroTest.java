package Model;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.*;
import static org.junit.Assert.*;

public class TabuleiroTest {
	
	Tabuleiro tabuleiro = new Tabuleiro();

	@Test
	public void testaAdicionarTerrenos() {
		assertEquals(tabuleiro.getTamListTerreno(), 0);
		Terreno terreno = new Empresa(50, 100);
		tabuleiro.addTerreno(terreno);
		
		ArrayList<Integer> lVAluguel = new ArrayList<>(Arrays.asList(10,20,30,40));
		ArrayList<Integer> lVCompra = new ArrayList<>(Arrays.asList(30,40,50,60));
		Terreno terreno2 = new Propriedade(lVAluguel, lVCompra, 200);
		tabuleiro.addTerreno(terreno2);
		assertEquals(tabuleiro.getTamListTerreno(), 2);
		
		assertEquals(tabuleiro.getTerreno(0).getValorCompra(),100 );
	}
	
	@Test
	public void testaAdicionarPeoes()
	{
		assertEquals(tabuleiro.getTamListPeoes(), 0);
		tabuleiro.addPeao(new Peao(tabuleiro.getTamListPeoes()));
		tabuleiro.addPeao(new Peao(tabuleiro.getTamListPeoes()));
		
		assertEquals(tabuleiro.getTamListPeoes(), 2);
		
		assertEquals(tabuleiro.getPeao(0).getId(), 0);
	}
	
	@Test
	public void testaRemoverPeoes()
	{
		assertEquals(tabuleiro.getTamListPeoes(), 0);
		tabuleiro.addPeao(new Peao(tabuleiro.getTamListPeoes()));
		tabuleiro.addPeao(new Peao(tabuleiro.getTamListPeoes()));
		
		assertEquals(tabuleiro.getTamListPeoes(), 2);
		
		assertEquals(tabuleiro.getPeao(0).getId(), 0);
		
		tabuleiro.removePeao(tabuleiro.getPeao(0));
		
		assertEquals(tabuleiro.getTamListPeoes(), 1);
		
		assertEquals(tabuleiro.getPeao(0).getId(), 1);
	}
	
}
