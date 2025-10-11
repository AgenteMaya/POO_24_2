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
}
