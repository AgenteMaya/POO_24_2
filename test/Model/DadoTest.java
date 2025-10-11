package Model;
import org.junit.*;
import static org.junit.Assert.*;

public class DadoTest {
	
	@Test (timeout = 5000)
	public void testaLancamentoDados() {
		Dado d = new Dado();
		int resultado_dados = d.lanca_dados();
		boolean expressao = resultado_dados >= 2 && resultado_dados <= 12;
		assertTrue("Resultado Inválido! A soma dos dados não estava entre 2 e 12!", expressao);
		
		
	}
	
}