package Model;
import org.junit.*;
import static org.junit.Assert.*;

public class PeaoTest {
	
	@Test (timeout=5000)
	public void testaPosicionaPeao() {
		int peaoDaVez = 1;
		int deslocamento = 6; //mocking de um possível valor retornado pela lanca_dados
		
		Peao peao = new Peao(peaoDaVez);
		peao.setaPosicaoPeao(deslocamento);
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
