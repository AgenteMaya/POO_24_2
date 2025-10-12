package Model;
import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

public class PeaoTest {
	
	Tabuleiro tabuleiro = new Tabuleiro();
	Banco banco = new Banco();
	
	public void setup() {
		Terreno terreno = new Empresa(50, 100);
		tabuleiro.addTerreno(terreno);
		ArrayList<Integer> lVAluguel = new ArrayList<>(Arrays.asList(10,20,30,40));
		ArrayList<Integer> lVCompra = new ArrayList<>(Arrays.asList(30,40,50,60));
		Terreno terreno2 = new Propriedade(lVAluguel, lVCompra, 50);
		tabuleiro.addTerreno(terreno2);
		
		tabuleiro.addPeao(new Peao(tabuleiro.getTamListPeoes()));
		tabuleiro.addPeao(new Peao(tabuleiro.getTamListPeoes()));
		
		tabuleiro.getPeao(0).setDinheiro(200);
		tabuleiro.getPeao(1).setDinheiro(500);
	}
	
	@Test (timeout=5000)
	public void testaPosicionaPeao() {
		setup();
		int peaoDaVez = 1;
		int deslocamento = 6; //mocking de um possível valor retornado pela lanca_dados
		
		Peao peao = new Peao(peaoDaVez);
		peao.setaPosicaoPeao(deslocamento, tabuleiro, banco);
		int pos = peao.pegaPosicaoPeao();
			
		assertEquals("A posição setada não é a mesma do lançamento dos dados",
				pos, deslocamento);
	}
	
	@Test (timeout=5000)
	public void testaIdaPrisaoSemCartaSaida() {
		int peaoDaVez = 1;
		int posPrisao = 10;
		
		Peao peao = new Peao(peaoDaVez);
		
		Carta cartaSaida = peao.vaiPraPrisao(posPrisao);
			
		assertEquals(null, cartaSaida);
		assertEquals(true, peao.estaNaPrisao());
		assertEquals(posPrisao, peao.pegaPosicaoPeao());
	}
	
	@Test (timeout=5000)
	public void testaIdaPrisaoComCartaSaida() {
		int peaoDaVez = 1;
		int posPrisao = 10;
		
		Peao peao = new Peao(peaoDaVez);
		int posInicial = peao.pegaPosicaoPeao();
		String descricao = "Saída livre da prisão.";
		peao.atribuiSaidaLivrePrisao(new Carta(descricao, true, true, 0, true));
		
		Carta cartaSaida = peao.vaiPraPrisao(posPrisao);
			
		assertEquals(descricao, cartaSaida.getDescricao());
		assertEquals(false, peao.estaNaPrisao());
		assertEquals(posInicial, peao.pegaPosicaoPeao());
	}
	
	@Test (timeout=5000)
	public void testaRemoveCartaSaida() {
		int peaoDaVez = 1;
		int posPrisao = 10;
		
		Peao peao = new Peao(peaoDaVez);
		int posInicial = peao.pegaPosicaoPeao();
		String descricao = "Saída livre da prisão.";
		peao.atribuiSaidaLivrePrisao(new Carta(descricao, true, true, 0, true));
		
		Carta cartaSaida = peao.vaiPraPrisao(posPrisao);
			
		assertEquals(descricao, cartaSaida.getDescricao());
		assertEquals(false, peao.estaNaPrisao());
		assertEquals(posInicial, peao.pegaPosicaoPeao());
		
		peao.removeCartaSaidaLivrePrisao();
		
		cartaSaida = peao.vaiPraPrisao(posPrisao);
		assertEquals(null, cartaSaida);
		assertEquals(true, peao.estaNaPrisao());
		assertEquals(posPrisao, peao.pegaPosicaoPeao());
	}
}
