package Model;

import org.junit.*;
import static org.junit.Assert.*;

public class PropriedadeTest {
	
	int lVCompra = 30;
	int vAluguel = 3;
	Propriedade propriedade = new Propriedade("", lVCompra);
	@Test
	public void testaConstrutorPropriedades() {
		assertTrue(propriedade.valorCompra == lVCompra);
	}
	
	@Test
	public void testaBooleanHotel()
	{
		assertFalse(propriedade.temHotel());
		propriedade.setTemHotel(true);
		assertTrue(propriedade.temHotel());		
	}
	
	@Test
	public void testaQtdCasas()
	{
		assertEquals(propriedade.getQtdCasas(), 0);
		propriedade.setMudaQtdCasa(1);
		assertEquals(propriedade.getQtdCasas(), 1);
	}
	

	@Test
	public void testaPegarValoresAluguelECompra()
	{
		propriedade.setMudaQtdCasa(0);
		assertEquals(vAluguel, propriedade.getAluguel(), 0.01);
		assertEquals(propriedade.valorCompra, 30);
	}
	

}
