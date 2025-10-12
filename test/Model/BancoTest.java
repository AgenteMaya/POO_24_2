package Model;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;



public class BancoTest {
	
	Banco banco = new Banco();
	Tabuleiro tabuleiro = new Tabuleiro();
	
	@Before
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

	
	  @Test public void testaCompraPropriedade() {
	  assertEquals(tabuleiro.getTerreno(0).getDono(), -1);
	  assertEquals(tabuleiro.getPeao(0).getDinheiro(), 200);
	  
	  banco.compraPropriedade(0, 0, tabuleiro);
	  
	  assertEquals(tabuleiro.getTerreno(0).getDono(), 0);
	  assertEquals(tabuleiro.getPeao(0).getDinheiro(), 100);
	  
	  
	  assertEquals(tabuleiro.getTerreno(1).getDono(), -1);
	  assertEquals(tabuleiro.getPeao(1).getDinheiro(), 500);
	  
	  banco.compraPropriedade(1, 1, tabuleiro);
	  
	  assertEquals(tabuleiro.getTerreno(1).getDono(), 1);
	  assertEquals(tabuleiro.getPeao(1).getDinheiro(), 450);
	  
	  }
	  
	  @Test public void testaVendePropriedade() { banco.compraPropriedade(0, 0,
	  tabuleiro); assertEquals(tabuleiro.getTerreno(0).getDono(), 0);
	  assertEquals(tabuleiro.getPeao(0).getDinheiro(), 100);
	  
	  boolean retorno = banco.vendePropriedade(tabuleiro.getPeao(0), tabuleiro);
	  
	  assertEquals(tabuleiro.getTerreno(0).getDono(), -1);
	  assertEquals(tabuleiro.getPeao(0).getDinheiro(), (int)(100 + 100 * 0.90));
	  assertTrue(retorno);
	  
	  retorno = banco.vendePropriedade(tabuleiro.getPeao(0), tabuleiro);
	  assertFalse(retorno); }
	  
	  @Test public void testarConstrucaoCasa() { banco.compraPropriedade(1, 1,
	  tabuleiro);
	  
	  //teste construir hotel sem ter casa
	  assertEquals(tabuleiro.getPeao(1).getDinheiro(), 450); banco.constroiCasa(1,
	  1, tabuleiro, false);
	  
	  assertEquals(tabuleiro.getPeao(1).getDinheiro(), 450);
	  
	  
	  //teste construir casa
	  banco.constroiCasa(1, 1, tabuleiro, true);
	  
	  assertEquals(tabuleiro.getPeao(1).getDinheiro(), 410);
	  
	  Propriedade prop = (Propriedade) (tabuleiro.getTerreno(1));
	  assertFalse(prop.temHotel()); assertEquals(prop.qtdCasas, 1);
	  
	  //teste construir hotel tendo casa
	  assertEquals(tabuleiro.getPeao(1).getDinheiro(), 410); banco.constroiCasa(1,
	  1, tabuleiro, false);
	  
	  assertEquals(tabuleiro.getPeao(1).getDinheiro(), 380);
	  assertTrue(prop.temHotel());
	  
	  //teste construir outra casa 
	  banco.constroiCasa(1, 1, tabuleiro, true);
	  assertEquals(tabuleiro.getPeao(1).getDinheiro(), 330);
	  assertEquals(prop.qtdCasas, 2); }
	 
	
	
	  @Test public void testaPagarAluguel() { System.out.printf("%d\n",
	  tabuleiro.getPeao(0).getDinheiro()); 
	  banco.compraPropriedade(0, 0, tabuleiro);
	  banco.compraPropriedade(1, 0, tabuleiro);
	  assertEquals(tabuleiro.getPeao(0).getDinheiro(), 50);
	  
	  
	  
	  //Teste pagar empresa 
	  banco.pagarAluguel(tabuleiro, 1, 0);
	  assertEquals(tabuleiro.getPeao(1).getDinheiro(), 450);
	  assertEquals(tabuleiro.getPeao(0).getDinheiro(), 100);
	  
	  //Teste pagar propriedade sem casa (não cobra)
	  assertEquals(tabuleiro.getPeao(1).getDinheiro(), 450);
	  assertEquals(tabuleiro.getPeao(0).getDinheiro(), 100);
	  
	  //teste pagar propriedade com uma casa
	  
	  banco.constroiCasa(0, 1, tabuleiro, true);
	  assertEquals(tabuleiro.getPeao(0).getDinheiro(), 60);
	  
	  banco.pagarAluguel(tabuleiro, 1, 1);
	  
	  assertEquals(tabuleiro.getPeao(0).getDinheiro(), 80);
	  assertEquals(tabuleiro.getPeao(1).getDinheiro(), 430);
	  
	  //teste pagar propriedade com duas casas
	  
	  banco.constroiCasa(0, 1, tabuleiro, true);
	  assertEquals(tabuleiro.getPeao(0).getDinheiro(), 30);
	  
	  banco.pagarAluguel(tabuleiro, 1, 1);
	  
	  assertEquals(tabuleiro.getPeao(0).getDinheiro(), 80);
	  assertEquals(tabuleiro.getPeao(1).getDinheiro(), 380);
	  
	  //teste pagar propriedade com duas casas e hotel
	  
	  banco.constroiCasa(0, 1, tabuleiro, false);
	  assertEquals(tabuleiro.getPeao(0).getDinheiro(), 50);
	  
	  banco.pagarAluguel(tabuleiro, 1, 1);
	  
	  assertEquals(tabuleiro.getPeao(0).getDinheiro(), 110);
	  assertEquals(tabuleiro.getPeao(1).getDinheiro(), 320); }
	 
	 
	 
	 @Test
	 public void testaPagarAluguelSemDinheiro()
	 {
	 	  System.out.printf("%d\n",	  tabuleiro.getPeao(0).getDinheiro()); 
		  banco.compraPropriedade(0, 1, tabuleiro); 
		  banco.compraPropriedade(1, 0, tabuleiro); 
		  banco.constroiCasa(0, 1, tabuleiro, true);
		  
		  assertEquals(tabuleiro.getPeao(0).getDinheiro(), 110);		  
		  assertEquals(tabuleiro.getPeao(1).getDinheiro(), 400);
		  
		  tabuleiro.getPeao(1).setDinheiro(0);
		  
		  assertEquals(tabuleiro.getPeao(1).getDinheiro(), 0);
		
		  //paggar o aluguel não tendo dinheiro suficiente, mas tendo propriedade para vender
		  boolean b = banco.pagarAluguel(tabuleiro, 1, 1);
		  
		  assertEquals(tabuleiro.getPeao(0).getDinheiro(), 130);		  
		  assertEquals(tabuleiro.getPeao(1).getDinheiro(), 70);
		  assertTrue(b);
		  
		  tabuleiro.getPeao(1).setDinheiro(0);
		  
		  //pagar aluguel nao tendo propriedade e sem ter dinheiro
		  b = banco.pagarAluguel(tabuleiro, 1, 1);
		  
		  assertEquals(tabuleiro.getPeao(0).getDinheiro(), 130);		  
		  assertEquals(tabuleiro.getPeao(1).getDinheiro(), 0);
		  assertFalse(b);
		  
		  
	 }
	
	

}

