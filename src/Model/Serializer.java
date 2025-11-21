package Model;
import Model.Api;
import Model.Banco;
import java.io.*;
import java.util.ArrayList;

public class Serializer {
	private final Api api = Api.getInstance();
	
	public Serializer()
	{	
	}

	public void salvarJogo(String caminho)
	{
		FileWriter arq = null;
		BufferedWriter buffer = null;
		//ordem: qtdPeoes, dinheiroBanco, lista de descarte, lista de compra, info dos peoes, index do peao da vez, info dos terrenos
		try{
			arq = new FileWriter(caminho, false);
			buffer = new BufferedWriter(arq);
			buffer.write(api.getQtdPeoes() + "");
			buffer.newLine();
			buffer.write(Banco.getBanco().qtdDinheiro + "");
			buffer.newLine();
			salvaBaralho(buffer, api.getlCartasDescarte());
			salvaBaralho(buffer, api.getlCartasCompras());			
			salvaPeoes(buffer);
			buffer.write(api.getJogadorAtual().getId() + ""); //salva o id do jogador atual
			buffer.newLine();
			salvaTerrenos(buffer);
		}
		catch(IOException e)
		{
			System.out.println("Erro ao criar arquivo de salvamento");
		}
		finally
		{
			try{
				if(buffer != null)
					buffer.close();
			}
			catch(IOException e)
			{
				System.out.println("Erro ao fechar o arquivo de salvamento");
			}
		}
	}
	
	private void salvaPeoes(BufferedWriter buffer)
	{
		//id, dinheiro, posicao, cor, nome, naPrisao, possuiSaidaLivrePrisao
		try
		{
			for(Peao p : api.getListaPeoes())
			{
				buffer.write(p.getId() + "," + p.getDinheiro() + "," + p.pegaPosicaoPeao() + "," + p.getCor() + "," + p.getNome() + "," + p.estaNaPrisao() + "," + p.temCartaSaidaLivre());
				buffer.newLine();
			}
		}
		catch(IOException e)
		{
			System.out.println("Erro ao salvar peões");
		}		
	}

	private void salvaBaralho(BufferedWriter buffer, ArrayList<Carta> lCartas)
	{
		try
		{
			//id
			int i = 0;
			for(Carta c : lCartas)
			{
				buffer.write(c.getId() + "");
				if (i < lCartas.size() - 1)
					buffer.write(",");
				i++;
			}
			buffer.newLine();
		}
		catch(IOException e)
		{
			System.out.println("Erro ao salvar baralho");
		}
	}

	private void salvaTerrenos(BufferedWriter buffer)
	{
		try
		{
			//valorCompra, indDono
			int index = 0;
			for(Terreno t : api.getListaTerrenos())
			{
				if(t.getDono() != -1)
				{
					//0 se for propriedade, 1 se for empresa -> saber na hora de desserializar
					if(t instanceof Propriedade)
					{
						//tipoTerreno, pos, indDono, qtdCasas, temHotel
						Propriedade p = (Propriedade) t;
						buffer.write("0," + index + "," + t.getDono() + "," + p.getQtdCasas() + "," + p.temHotel());
					}
					else if(t instanceof Empresa)
					{
						buffer.write("1," + index + "," + t.getDono());
					}
					buffer.newLine();
				}
				index++;
			}
		}
		catch(IOException e)
		{
			System.out.println("Erro ao salvar terrenos");
		}
	}
}
