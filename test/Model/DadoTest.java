package Model;
import org.junit.*;
import static org.junit.Assert.*;

public class DadoTest {
	
	Dado d = new Dado();
	int [] resultado_dados = d.lanca_dados();
	
	@Test (timeout = 5000)
	public void testaSomaDados() {
		int soma = resultado_dados[0] + resultado_dados[1];
		boolean expressao = soma >= 2 && soma <= 12;
		assertTrue("Resultado Inválido! A soma dos dados não estava entre 2 e 12!", expressao);
		
	}
	
	@Test (timeout = 5000)
	public void testaValorDados() {
		boolean expressaoDado1 = resultado_dados[0] >=1 && resultado_dados[0] <=6;
		boolean expressaoDado2 = resultado_dados[1] >=1 && resultado_dados[1] <=6;
		assertTrue("Valor do Dado1 inválido!", expressaoDado1);
		assertTrue("Valor do Dado2 inválido!", expressaoDado2);
	}
	
	@Test (timeout = 5000)
	public void testaIrParaPrisao() {
		int deslocamento = d.totalDeslocamento();
		while(deslocamento != -1) 
		{ 
			deslocamento = d.totalDeslocamento();
		}
		
		assertEquals(-1, deslocamento);
	}
	
	@Test (timeout = 5000)
	public void testaSairDaPrisao() {
		int deslocamento = d.deslocamentoSaidaPrisao();
		while(deslocamento == -1) 
		{ 
			deslocamento = d.deslocamentoSaidaPrisao();
		}
		
		assertNotEquals(-1, deslocamento);
	}
}