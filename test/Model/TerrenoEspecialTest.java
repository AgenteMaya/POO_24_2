//package Model;
//
//import org.junit.*;
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//
//public class TerrenoEspecialTest {
//	
//	@Test
//	public void testaEntradaComSaidaLivrePrisao()
//	{
//		Baralho baralhoTeste = new Baralho(new ArrayList<Carta>());
//		int posPrisao = 10;
//		IrPraPrisao terreno = new IrPraPrisao(posPrisao);
//		Peao peao = new Peao(1);
//		int posInicial = peao.pegaPosicaoPeao();
//		
//		Carta saida = new Carta(9, "Saída livre da prisão.", true, true, 0, true);
//		peao.atribuiSaidaLivrePrisao(saida);
//		
//		assertEquals(true, peao.temCartaSaidaLivre());
//		assertEquals(0, baralhoTeste.tamanhoListaDescarte());
//		
//		terreno.realizaAcao(baralhoTeste, peao);
//		
//		assertEquals(false, peao.temCartaSaidaLivre());
//		assertEquals(1, baralhoTeste.tamanhoListaDescarte());
//		assertEquals(false, peao.estaNaPrisao());
//		assertEquals(posInicial, peao.pegaPosicaoPeao());	
//	}
//	
//	@Test
//	public void testaEntradaSemSaidaLivrePrisao()
//	{
//		Baralho baralhoTeste = new Baralho(new ArrayList<Carta>());
//		int posPrisao = 10;
//		IrPraPrisao terreno = new IrPraPrisao(posPrisao);
//		Peao peao = new Peao(1);
//		
//		assertEquals(false, peao.temCartaSaidaLivre());
//		terreno.realizaAcao(baralhoTeste, peao);
//		
//		assertEquals(true, peao.estaNaPrisao());
//		assertEquals(posPrisao, peao.pegaPosicaoPeao());	
//	}
//}
