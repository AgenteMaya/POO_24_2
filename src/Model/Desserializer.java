package Model;
import Model.Api;
import Model.Banco;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Desserializer {
    private final Api api = Api.getInstance();
    
    public Desserializer()
    {	
    }

    public void carregarJogo(File arquivo) throws IOException
    {
        FileReader arq = null;
        BufferedReader buffer = null;
            arq = new FileReader(arquivo);
            buffer = new BufferedReader(arq);
            String linha = buffer.readLine();
            int qtdPeoes = Integer.parseInt(linha);

            linha = buffer.readLine();
            double dinheiroBanco = Double.parseDouble(linha);
            Banco.getBanco().qtdDinheiro = dinheiroBanco;
            carregaBaralho(buffer);
            carregaPeoes(buffer, qtdPeoes);
            carregaJogadorAtual(buffer);
            carregaTerrenos(buffer);
     
            try{
                if(buffer != null)
                    buffer.close();
            }
            catch(IOException e)
            {
                System.out.println("Erro ao fechar o arquivo de carregamento");
            }
        
    }

    private void carregaPeoes(BufferedReader buffer, int qtdPeoes)
    {
        //id, dinheiro, posicao, cor, nome, naPrisao, possuiSaidaLivrePrisao
        try
        {
            for(int i = 0; i < qtdPeoes; i++)
            {
                String linha = buffer.readLine();
                String[] dados = linha.split(",");

                Peao p = new Peao(Integer.parseInt(dados[0]));
                p.setDinheiro(Double.parseDouble(dados[1]));
                p.setaPosicaoPeao(Integer.parseInt(dados[2]));
                p.setCor(dados[3]);
                p.setNome(dados[4]);
                p.naPrisao = Boolean.parseBoolean(dados[5]);
                if (Boolean.parseBoolean(dados[6]))
                {
                    Carta carta = new Carta(9, "Saída livre da prisão.", true, true, 0, true);
                    p.atribuiSaidaLivrePrisao(carta);
                }
                api.adicionaPeao(p);
            }
        }
        catch(IOException e)
        {
            System.out.println("Erro ao carregar peões");
        }		
    }

    private ArrayList<Carta> carregaListaCartas(BufferedReader buffer, Map<Integer, Carta> hashCartas)
    {
        ArrayList<Carta> lCartas = new ArrayList<>();
        try
        {
            String linha = buffer.readLine();

            if (linha == null || linha.isEmpty())
            {
                return lCartas; 
            }

            String[] ids = linha.split(",");
            for (String idStr : ids)
            {
                int id = Integer.parseInt(idStr);
                Carta carta = hashCartas.get(id);
                if (carta != null) 
                {
                    lCartas.add(carta);
                }
            }
        }
        catch(IOException e)
        {
            System.out.println("Erro ao carregar lista de cartas");
        }		
        return lCartas;
    }

    private void carregaBaralho(BufferedReader buffer)
    {
        Map<Integer, Carta> hashCartas = new HashMap<>();
        for (Carta c : api.getlCartasCompras()) {
            hashCartas.put(c.getId(), c);
        }

        ArrayList<Carta> lCartasDescarte = carregaListaCartas(buffer, hashCartas);
        api.setlCartasDescarte(lCartasDescarte);

        ArrayList<Carta> lCartasCompras = carregaListaCartas(buffer, hashCartas);
        api.setlCartasCompras(lCartasCompras);
    }

    private void carregaJogadorAtual(BufferedReader buffer)
    {
        try
        {
            String linha = buffer.readLine();
            int idJogadorAtual = Integer.parseInt(linha);
            api.setJogadorAtualTabuleiro(idJogadorAtual);
            api.setJogadorAtual();
        }
        catch(IOException e)
        {
            System.out.println("Erro ao carregar jogador atual");
        }		
    }

    private void carregaTerrenos(BufferedReader buffer)
    {
        try
        {
            String linha;
            while ((linha = buffer.readLine()) != null)
            {
                String[] dados = linha.split(",");
                int index = Integer.parseInt(dados[1]);

                Terreno t = api.getListaTerrenos().get(index);
                t.indDono = Integer.parseInt(dados[2]);
                if (dados[0].equals("0")) //propriedade
                {
                    Propriedade p = (Propriedade) t;
                    p.setMudaQtdCasa(Integer.parseInt(dados[3]));
                    p.setTemHotel(Boolean.parseBoolean(dados[4]));
                }
            }
        }
        catch(IOException e)
        {
            System.out.println("Erro ao carregar terrenos");
        }		
    }
    
}
