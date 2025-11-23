package Model;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class BancoTest {
	
	ArrayList<Terreno> lTerrenos = new ArrayList<>();
	Tabuleiro tabuleiro = new Tabuleiro(lTerrenos);
	
	@Before
	public void setup() {
		Terreno terreno = new Empresa("", 50, 100);
		tabuleiro.addTerreno(terreno);
		int lVCompra = 50;
		Terreno terreno2 = new Propriedade("", lVCompra);
		tabuleiro.addTerreno(terreno2);
		
		tabuleiro.addPeao(new Peao(tabuleiro.getTamListPeoes()));
		tabuleiro.addPeao(new Peao(tabuleiro.getTamListPeoes()));
		
		tabuleiro.getPeao(0).setDinheiro(200);
		tabuleiro.getPeao(1).setDinheiro(500);
	}

	
	  @Test public void testaCompraPropriedade() {
	  assertEquals(tabuleiro.getTerreno(0).getDono(), -1);
	  assertEquals(200, tabuleiro.getPeao(0).getDinheiro(), 0.001);
	  
	  Banco.getBanco().compraPropriedade(0, 0, tabuleiro);
	  
	  assertEquals(tabuleiro.getTerreno(0).getDono(), 0);
	  assertEquals(100, tabuleiro.getPeao(0).getDinheiro(), 0.001);
	  
	  
	  assertEquals(tabuleiro.getTerreno(1).getDono(), -1);
	  assertEquals(500, tabuleiro.getPeao(1).getDinheiro(), 0.001);
	  
	  Banco.getBanco().compraPropriedade(1, 1, tabuleiro);
	  
	  assertEquals(tabuleiro.getTerreno(1).getDono(), 1);
	  assertEquals(450, tabuleiro.getPeao(1).getDinheiro(), 0.001);
	  
	  }
	  
	  @Test public void testaVendePropriedade() { 
	  Banco.getBanco().compraPropriedade(0, 0,
	  tabuleiro); assertEquals(tabuleiro.getTerreno(0).getDono(), 0);
	  assertEquals(100, tabuleiro.getPeao(0).getDinheiro(), 0.001);
	  
	  boolean retorno = Banco.getBanco().vendePropriedade(tabuleiro.getPeao(0), tabuleiro);
	  
	  assertEquals(tabuleiro.getTerreno(0).getDono(), -1);
	  assertEquals((int)(100 + 100 * 0.90), tabuleiro.getPeao(0).getDinheiro(), 0.001);
	  assertTrue(retorno);
	  
	  retorno = Banco.getBanco().vendePropriedade(tabuleiro.getPeao(0), tabuleiro);
	  assertFalse(retorno); }
	  
	  @Test public void testarConstrucaoCasa() { Banco.getBanco().compraPropriedade(1, 1,
	  tabuleiro);
	  
	  //teste construir hotel sem ter casa
	  assertEquals(450, tabuleiro.getPeao(1).getDinheiro(), 0.001); 
	  Banco.getBanco().constroiCasa(1,1, tabuleiro, false);
	  
	  assertEquals(450, tabuleiro.getPeao(1).getDinheiro(), 0.001);
	  
	  
	  //teste construir casa
	  
	  Banco.getBanco().constroiCasa(1, 1, tabuleiro, true);
	  
	  assertEquals(442.5, tabuleiro.getPeao(1).getDinheiro(), 0.001);
	  
	  Propriedade prop = (Propriedade) (tabuleiro.getTerreno(1));
	  assertFalse(prop.temHotel()); assertEquals(prop.qtdCasas, 1);
	  
	  //teste construir hotel tendo casa
	  assertEquals(442.5, tabuleiro.getPeao(1).getDinheiro(), 0.01); 
	  Banco.getBanco().constroiCasa(1, 1, tabuleiro, false);
	  
	  assertEquals(427.5, tabuleiro.getPeao(1).getDinheiro(), 0.01);
	  assertTrue(prop.temHotel());
	  
	  //teste construir outra casa 
	  Banco.getBanco().constroiCasa(1, 1, tabuleiro, true);
	  assertEquals(412.5, tabuleiro.getPeao(1).getDinheiro(), 0.01);
	  assertEquals(prop.qtdCasas, 2); }
	 
	
	
	  @Test public void testaPagarAluguel() { 
	  Banco.getBanco().compraPropriedade(0, 0, tabuleiro);
	  Banco.getBanco().compraPropriedade(1, 0, tabuleiro);
	  assertEquals(50, tabuleiro.getPeao(0).getDinheiro(), 0.01);
	  
	  
	  
	  //Teste pagar empresa 
	  Banco.getBanco().pagarAluguelEmpresa(tabuleiro, 1, 0, 1);
	  assertEquals(450, tabuleiro.getPeao(1).getDinheiro(), 0.01);
	  assertEquals(100, tabuleiro.getPeao(0).getDinheiro(), 0.01);
	  
	  //Teste pagar propriedade sem casa (não cobra)
	  assertEquals(450, tabuleiro.getPeao(1).getDinheiro(), 0.01);
	  assertEquals(100, tabuleiro.getPeao(0).getDinheiro(), 0.01);
	  
	  //teste pagar propriedade com uma casa
	  
	  Banco.getBanco().constroiCasa(0, 1, tabuleiro, true);
	  assertEquals(92.5, tabuleiro.getPeao(0).getDinheiro(), 0.01);
	  
	  Banco.getBanco().pagarAluguelPropriedade(tabuleiro, 1, 1);
	  
	  assertEquals(105, tabuleiro.getPeao(0).getDinheiro(), 0.01);
	  assertEquals(437.5, tabuleiro.getPeao(1).getDinheiro(), 0.01);
	  
	  //teste pagar propriedade com duas casas
	  Banco.getBanco().constroiCasa(0, 1, tabuleiro, true);
	  assertEquals(90, tabuleiro.getPeao(0).getDinheiro(), 0.01);
	  
	  Banco.getBanco().pagarAluguelPropriedade(tabuleiro, 1, 1);
	  
	  assertEquals(110, tabuleiro.getPeao(0).getDinheiro(), 0.01);
	  assertEquals(417.5, tabuleiro.getPeao(1).getDinheiro(), 0.01);
	  
	  //teste pagar propriedade com duas casas e hotel
	  Banco.getBanco().constroiCasa(0, 1, tabuleiro, false);
	  assertEquals(95, tabuleiro.getPeao(0).getDinheiro(), 0.01);
	  
	  Banco.getBanco().pagarAluguelPropriedade(tabuleiro, 1, 1);
	  
	  assertEquals(130, tabuleiro.getPeao(0).getDinheiro(), 0.01);
	  assertEquals(382.5, tabuleiro.getPeao(1).getDinheiro(), 0.01); }
	 
	 
	 @Test
	 public void testaPagarAluguelSemDinheiro()
	 {
		  Banco.getBanco().compraPropriedade(0, 1, tabuleiro); 
		  Banco.getBanco().compraPropriedade(1, 0, tabuleiro); 
		  Banco.getBanco().constroiCasa(0, 1, tabuleiro, true);
		  
		  assertEquals(142.5, tabuleiro.getPeao(0).getDinheiro(), 0.01);		  
		  assertEquals(400, tabuleiro.getPeao(1).getDinheiro(), 0.01);
		  
		  tabuleiro.getPeao(1).setDinheiro(0);
		  
		  assertEquals(0, tabuleiro.getPeao(1).getDinheiro(), 0.01);
		
		  //paggar o aluguel não tendo dinheiro suficiente, mas tendo propriedade para vender
		  boolean b = Banco.getBanco().pagarAluguelPropriedade(tabuleiro, 1, 1);
		  
		  assertEquals(155, tabuleiro.getPeao(0).getDinheiro(), 0.01);		  
		  assertEquals(77.5, tabuleiro.getPeao(1).getDinheiro(), 0.01);
		  assertTrue(b);
		  
		  tabuleiro.getPeao(1).setDinheiro(0);
		  
		  int qtdPeoesInicial = tabuleiro.getTamListPeoes();
		  
		  //pagar aluguel nao tendo propriedade e sem ter dinheiro
		  b = Banco.getBanco().pagarAluguelPropriedade(tabuleiro, 1, 1);
		  
		  assertEquals(155, tabuleiro.getPeao(0).getDinheiro(), 0.01);		  
		  assertFalse(b);
		  
		  // verificar se o peão foi removido
		  assertEquals(qtdPeoesInicial - 1, tabuleiro.getTamListPeoes());
	 }
	
	

}

