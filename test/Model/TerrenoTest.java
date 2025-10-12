package Model;

import org.junit.*;
import static org.junit.Assert.*;

public class TerrenoTest {
	Terreno terreno = new Terreno();

	@Test
	public void testaSetaDono() {
		assertSame(terreno.getDono(), -1);
		terreno.setDono(2);
		assertSame(terreno.getDono(), 2);
	}
	
	@Test
	public void testaPegarValorCompra()
	{
		terreno.valorCompra = 4;
		assertSame(terreno.getValorCompra(), 4);
	}

}
