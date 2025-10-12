package Model;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class PropriedadeTest {
	
	ArrayList<Integer> lVAluguel = new ArrayList<>(Arrays.asList(10,20,30,40));
	ArrayList<Integer> lVCompra = new ArrayList<>(Arrays.asList(30,40,50,60));
	int valorC = 4;
	Propriedade propriedade = new Propriedade(lVAluguel, lVCompra, valorC);
	@Test
	void testaConstrutorPropriedades() {
		assertTrue(propriedade.lVAluguel.equals(lVAluguel));
		assertTrue(propriedade.lVCompra.equals(lVCompra));
	}
	
	@Test
	void testaBooleanHotel()
	{
		assertFalse(propriedade.temHotel());
		propriedade.setTemHotel(true);
		assertTrue(propriedade.temHotel());		
	}
	
	@Test
	void testaQtdCasas()
	{
		assertEquals(propriedade.getQtdCasas(), 0);
		propriedade.setMudaQtdCasa(1);
		assertEquals(propriedade.getQtdCasas(), 1);
	}
	
	@Test
	void testaPegarValoresAluguelECompra()
	{
		assertEquals(propriedade.getVAluguel(2), lVAluguel.get(2));
		assertEquals(propriedade.getVCompra(2), lVCompra.get(2));
	}
}
