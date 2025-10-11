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
		int pos = peao.pegaPosicaoPeao(peaoDaVez);
			
		assertEquals("A posição setada não é a mesma do lançamento dos dados",
				pos, deslocamento);
	}
}
