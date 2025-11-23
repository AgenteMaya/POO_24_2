package Model;

import org.junit.*;
import static org.junit.Assert.*;

public class EmpresaTest {
	int valorCompra = 10;
	int valorAluguel = 20;

	@Test
	public void testaContrucaoEmpresa() {
		Empresa empresa = new Empresa("", valorAluguel, valorCompra);
		assertEquals(empresa.getValorTaxa(), valorAluguel);
		assertEquals(empresa.getValorCompra(), valorCompra);
	}
	
	

}
