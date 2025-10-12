package Model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BancoTest {
	
	Tabuleiro tabuleiro = new Tabuleiro();
	


	
	@BeforeEach
    void setup() {
		Terreno terreno = new Empresa(50, 100);
		tabuleiro.addTerreno(terreno);
		ArrayList<Integer> lVAluguel = new ArrayList<>(Arrays.asList(10,20,30,40));
		ArrayList<Integer> lVCompra = new ArrayList<>(Arrays.asList(30,40,50,60));
		Terreno terreno2 = new Propriedade(lVAluguel, lVCompra, 200);
		tabuleiro.addTerreno(terreno2);
		
		tabuleiro.addPeao(new Peao(tabuleiro.getTamListPeoes()));
		tabuleiro.addPeao(new Peao(tabuleiro.getTamListPeoes()));
		
		tabuleiro.getPeao(0).setDinheiro(200);
		tabuleiro.getPeao(1).setDinheiro(200);
	}

	@Test
	void testaCompraPropriedade() {		
		assertEquals(tabuleiro.getTerreno(0).getDono(), -1);
		
		
		
		
	}

}

