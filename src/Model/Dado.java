package Model;
import java.util.Random;

public class Dado {
	
	Random random = new Random();
	
	//retorna o valor do lançamento dos dois dados
	public int[] lanca_dados() 
	{
		int dado_um = random.nextInt(1,6);
		int dado_dois = random.nextInt(1,6);
		return new int[] {dado_um, dado_dois};
	}
	
	// esta função retorna -1 quando forem obtidas 3 duplas seguidas, significando que o jogador irá para a prisão
	public int totalDeslocamento()
	{
		int dados[] = lanca_dados();
		
		if (dados[0] != dados[1]) return dados[0] + dados[1];
		System.out.printf("Os dados deram uma dupla: %d e %d.\n", dados[0], dados[1]);
		
		dados = lanca_dados();
		
		if (dados[0] != dados[1]) return dados[0] + dados[1];
		System.out.printf("Os dados deram outra dupla: %d e %d.\n", dados[0], dados[1]);
		
		dados = lanca_dados();
		
		if (dados[0] != dados[1]) return dados[0] + dados[1];
		
		System.out.printf("Os dados deram a terceira dupla: %d e %d.\n", dados[0], dados[1]);
		System.out.println("O jogador da vez irá para a prisão.\n");
		
		return -1;
	}
	
	// esta função retorna -1 quando não for uma dupla, significando que o jogador continuará na prisão
	public int deslocamentoSaidaPrisao()
	{
		int dados[] = lanca_dados();
		
		if (dados[0] != dados[1]) 
		{
			System.out.printf("Os dados não deram uma dupla: %d e %d.\n", dados[0], dados[1]);
			System.out.println("O jogador da vez continuará na prisão.\n");
			return -1;
		}
		System.out.printf("Os dados deram uma dupla: %d e %d.\n", dados[0], dados[1]);
		System.out.println("O jogador da vez sairá da prisão.\n");
		
		return dados[0] + dados[1];
	}
}
