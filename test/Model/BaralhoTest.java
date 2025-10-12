package Model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class BaralhoTest{
	
	private Baralho baralhoTeste;
	
	@Before
    public void setup() {
		// por enquanto estamos trabalhando somente com as de prisão
		ArrayList<Carta> listaCartasTeste = new ArrayList<Carta>();
	
		listaCartasTeste.add(new Carta("Vá para a prisão.", false, true, 0, true));
		listaCartasTeste.add(new Carta("Saída livre da prisão.", true, true, 0, true));
    	
    	baralhoTeste = new Baralho(listaCartasTeste);
	}
	
	@Test (timeout=5000)
	public void testaPegaCarta() {	
		int tamCartasCompraInicio = baralhoTeste.tamanhoListaCompra();
		assertEquals("A quantidade inicial de cartas de compra está inicializando de modo errado.",
				2, tamCartasCompraInicio);

		baralhoTeste.pegarCarta();
		
		int tamCartasCompraFinal = baralhoTeste.tamanhoListaCompra();
		assertEquals("A quantidade cartas de compra não está sendo alterada corretamente.",
				1, tamCartasCompraFinal);
	}
	
	@Test (timeout=5000)
	public void testaDescartaCarta() {
		int tamCartasDescarteInicio = baralhoTeste.tamanhoListaDescarte();
		assertEquals("A quantidade inicial de cartas de descarte está inicializando de modo errado.",
				0, tamCartasDescarteInicio);
		
		Carta cartaTeste = baralhoTeste.pegarCarta();
		baralhoTeste.descartarCarta(cartaTeste);
		
		int tamCartasDescarteFinal = baralhoTeste.tamanhoListaCompra();
		assertEquals("A quantidade cartas de descarte não está sendo alterada corretamente.",
				1, tamCartasDescarteFinal);
	}
	
	@Test (timeout=5000)
	public void testaAcabaCartasCompra() {
		int tamCartas = baralhoTeste.tamanhoListaCompra();
		assertEquals("A quantidade inicial de cartas de compra está inicializando de modo errado.",
				2, tamCartas);

		Carta cartaTeste = baralhoTeste.pegarCarta();
		baralhoTeste.descartarCarta(cartaTeste);
		
		tamCartas = baralhoTeste.tamanhoListaCompra();
		assertEquals("A quantidade cartas de compra não está sendo alterada corretamente.",
				1, tamCartas);
		
		cartaTeste = baralhoTeste.pegarCarta();
		baralhoTeste.descartarCarta(cartaTeste);
		
		tamCartas = baralhoTeste.tamanhoListaCompra();
		assertEquals("A quantidade cartas de compra não está sendo alterada corretamente.",
				0, tamCartas);
		
		baralhoTeste.pegarCarta();
		
		tamCartas = baralhoTeste.tamanhoListaCompra();
		assertEquals("A quantidade de cartas de compra não está reinicializando de maneira correta.",
				1, tamCartas);
	}
}
