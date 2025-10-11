package Model;

class Peao {
	int id;
	int dinheiro;
	int posicao;
	
	public Peao(int id_peao) {
		id = id_peao;
	} 
		
	public void setaPosicaoPeao(int pos) {
		posicao = pos;
		System.out.printf("Jogador %d está na posição %d do tabuleiro\n", id, posicao);
		return;
	}
	
	public int pegaPosicaoPeao(int id_peao) {
		return posicao;
	}
	
}
