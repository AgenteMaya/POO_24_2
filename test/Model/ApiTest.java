package Model;
import org.junit.*;
import static org.junit.Assert.*;

public class ApiTest {

	private Api api;
    @Before
    public void setUp() throws Exception {
    	
        Api.reset();
        api = Api.getInstance();
        api.Inicializa(); 
    }

    @Test
    public void testeEhSingleton() {
        Api outraReferencia = Api.getInstance();
        assertSame("Mesma instancia retornada!", api, outraReferencia);
    }

    @Test
    public void testeInicializacaoTabuleiro() {
        assertEquals("O tabuleiro tem 40 terrenos", 40, api.getTamTabuleiro());
    }

    @Test
    public void testeAdicionarERecuperarJogador() {
        api.adicionaJogador(1, "Teste", "Azul", 2000);

        assertEquals("Deve haver 1 peão", 1, api.getQtdPeoes());
        assertEquals("Nome incorreto", "Teste", api.getNomePeao(0));
        assertEquals("Cor incorreta", "Azul", api.getCorPeao(0));
        assertEquals("Dinheiro incorreto", 2000.0, api.getDinheiroPeao(0), 0.01);
    }

    @Test
    public void testeOrdemEIncioDeTurno() {
        api.adicionaJogador(1, "P1", "Vermelho", 1000);
        api.adicionaJogador(2, "P2", "Azul", 1000);

        api.iniciaTurno(); 
        api.setJogadorAtual();
        
        assertNotNull("O jogador atual não deveria ser nulo", api.getNomeJogAtual());
        assertNotEquals("O nome do jogador atual não deveria estar vazio", "", api.getNomeJogAtual());
    }

    @Test
    public void testeTiposDeTerreno() {
        assertTrue("Posição 0 deveria ser Ponto de Partida", api.ehPontoDePartida(0));
        
        assertTrue("Posição 1 deveria ser Propriedade", api.ehPropriedade(1));
        assertEquals("Nome do terreno 1 incorreto", "Leblon", api.getNomeTerreno(1));
        
        assertTrue("Posição 10 deveria ser Prisão", api.ehPrisao(10));
        
        assertTrue("Posição 5 deveria ser Empresa", api.ehEmpresa(5));
    }
 
    @Test
    public void testeCompraPropriedadeFluxo() {
        api.adicionaJogador(1, "Rico", "Amarelo", 5000);
        api.iniciaTurno();
        api.setJogadorAtual();
        
        boolean sucesso = api.realizaCompraDePropriedade(1);
        
        assertTrue("Deveria conseguir comprar o Leblon", sucesso);
        assertEquals("O dono do Leblon deve ser o ID do jogador", api.getIdJogadorAtual(), api.getIdDono(1));
    }

    @Test
    public void testeCartas() {

        int idCarta = api.getIdCarta();
        assertTrue("ID da carta inválido", idCarta >= 1 && idCarta <= 30);
    }
    
    @Test
    public void testDinheiroVencedor() {
        api.adicionaJogador(1, "Vencedor", "Branco", 9000);
        
        assertEquals("Dinheiro do vencedor incorreto", 9000.0, api.getDinheiroVencedor(), 0.01);
        assertEquals("Nome do vencedor incorreto", "Vencedor", api.getNomeVencedor());
    }
}
