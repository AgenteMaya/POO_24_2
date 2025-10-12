package Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TerrenoTest {
	Terreno terreno = new Terreno();

	@Test
	void testaSetaDono() {
		assertSame(terreno.getDono(), -1);
		terreno.setDono(2);
		assertSame(terreno.getDono(), 2);
	}
	
	@Test
	void testaPegarValorCompra()
	{
		terreno.valorCompra = 4;
		assertSame(terreno.getValorCompra(), 4);
	}

}
