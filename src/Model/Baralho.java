package Model;
import java.util.ArrayList;
import java.util.Collections;

class Baralho {
	ArrayList<Carta> lCartasCompra=new ArrayList<>();
	ArrayList<Carta> lCartasDescarte=new ArrayList<>();
	
	// Construtor que cria um baralho.
    public Baralho(){
        inicializarCartas();
        embaralharCartas();
    }
    
    // Função para inicializar as cartas específicas do jogo
    void inicializarCartas() {
    	// para a primeira iteração, só iremos trabalhar com as cartas envolvendo a prisão
    	lCartasCompra.add(new Carta("Vá para a prisão.", false, true, 0, true));
    	lCartasCompra.add(new Carta("Saída livre da prisão.", true, true, 0, true));
    }
    
    // Função para embaralhar as cartas de compra.
    void embaralharCartas()
    {
    	Collections.shuffle(this.lCartasCompra);
    }
	
    /**
     * Pega a carta do topo do monte de compra.
     * Se o monte de compra estiver vazio, reabastece com o monte de descarte,
     * embaralha, e então pega a carta.
     * @return a carta do topo.
     */
    Carta pegarCarta() {
        if (lCartasCompra.isEmpty()) {
            System.out.println("O monte de compras acabou. Reabastecendo...");

            lCartasCompra.addAll(lCartasDescarte);
            lCartasCompra.clear();
            embaralharCartas();
            System.out.println("Monte de descarte foi embaralhado e agora é o novo monte de compras!");
        }
        
        // Remove e retorna a carta do topo (a última da lista, para eficiência)
        return lCartasCompra.remove(lCartasCompra.size() - 1);
    }
	
	 /**
     * Adiciona uma carta à lista de descarte.
     * Cartas de "Saída Livre da Prisão" geralmente são guardadas pelo jogador.
     * @param carta é a carta a ser descartada.
     */
    void descartarCarta(Carta carta) {
        if (carta != null) {
        	lCartasDescarte.add(carta);
        }
    }
	
	// Função para verifica o tamanho da lista de cartas de compra.
    int tamanhoListaCompra() {
        return lCartasCompra.size();
    }

    // Função para verifica o tamanho da lista de cartas de descarte.
    int tamanhoListaDescarte() {
        return lCartasDescarte.size();
    }
	
}
