package trabgb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

// classe estatica para ler e manipular o array de Pessoas 
public class PessoaGerenciador {

	private static Pessoa pessoas[];
	private static int cont = 0;
	private static int index = 0;

	private static ArvoreAVL avlNome = new ArvoreAVL();
	private static ArvoreAVL avlCpf = new ArvoreAVL();
	private static ArvoreAVL avlData = new ArvoreAVL();

	public static void carregar(File file) throws IOException {

		String linha;

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			while ((linha = br.readLine()) != null) {
				cont++;
			}
		}

		pessoas = new Pessoa[cont];

		Pessoa p;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			while ((linha = br.readLine()) != null) {
				String[] cols = linha.split(";");
				p = new Pessoa(Long.parseLong(cols[0]), Integer.parseInt(cols[1]), cols[2], cols[3], cols[4]);
				pessoas[index] = p;
				avlNome.inserir(index, "nome");
				avlCpf.inserir(index, "cpf");
				avlData.inserir(index, "data");
				index++;
			}

		}
	}

	public static Pessoa buscaPorCpf(long cpf) {
		int retorno = avlCpf.buscaPorCpf(cpf);
		if (retorno == -1)
			return null;
		else
			return pessoas[retorno];
	}

	public static ArrayList<Pessoa> buscaPorNome(String nome) {

		ArrayList<Integer> arrayP = avlNome.buscaPorNome(nome);
		ArrayList<Pessoa> retorno = new ArrayList<>();

		for (Integer i : arrayP) {
			Pessoa aux = PessoaGerenciador.getPessoa(i);
			retorno.add(aux);
		}

		return retorno;

	}

	public static ArrayList<Pessoa> buscaPorData(String dataInicial, String dataFinal) {
		ArrayList<Integer> arrayP;
		ArrayList<Pessoa> retorno = new ArrayList<>();
		try {
			arrayP = avlData.buscaPorData(dataInicial, dataFinal);
			for (Integer i : arrayP) {
				Pessoa aux = PessoaGerenciador.getPessoa(i);
				retorno.add(aux);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;

	}

	// retorna objeto Pessoa na posicao passada por parametro
	public static Pessoa getPessoa(int index) {
		return pessoas[index];
	}

	public static int getIndex() {
		return index;
	}

}
