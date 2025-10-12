package Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmpresaTest {
	int valorCompra = 10;
	int valorAluguel = 20;

	@Test
	void testaContrucaoEmpresa() {
		Empresa empresa = new Empresa(valorAluguel, valorCompra);
		assertEquals(empresa.getValorAluguel(), valorAluguel);
		assertEquals(empresa.getValorCompra(), valorCompra);
	}
	
	

}
