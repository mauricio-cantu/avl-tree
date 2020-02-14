package trabgb;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ArvoreAVL {
	protected No raiz;

	public void inserir(int index, String tipo) {
		No n = new No(index);
		inserirAVL(this.raiz, n, tipo);
	}

	public void inserirAVL(No aComparar, No aInserir, String tipo) {

		if (aComparar == null) {
			this.raiz = aInserir;

		} else {

			Pessoa compara = PessoaGerenciador.getPessoa(aComparar.getChave());
			Pessoa insere = PessoaGerenciador.getPessoa(aInserir.getChave());

			// faz a comparação adequada dependendo do tipo de informação a ser comparada
			// (nome, cpf ou data)
			// se o no a inserir é menor, insere a esquerda
			if (insere.compareTo(compara, tipo) < 0) {

				if (aComparar.getEsquerda() == null) {
					aComparar.setEsquerda(aInserir);
					aInserir.setPai(aComparar);
					verificarBalanceamento(aComparar);

				} else {
					inserirAVL(aComparar.getEsquerda(), aInserir, tipo);
				}
				// se é maior, insere a direita
			} else if (insere.compareTo(compara, tipo) > 0) {

				if (aComparar.getDireita() == null) {
					aComparar.setDireita(aInserir);
					aInserir.setPai(aComparar);
					verificarBalanceamento(aComparar);

				} else {
					inserirAVL(aComparar.getDireita(), aInserir, tipo);
				}

			} else {
				// O nó já existe
			}

		}
	}

	public void verificarBalanceamento(No atual) {
		setBalanceamento(atual);
		int balanceamento = atual.getBalanceamento();

		if (balanceamento == -2) {

			if (altura(atual.getEsquerda().getEsquerda()) >= altura(atual.getEsquerda().getDireita())) {
				atual = rotacaoDireita(atual);

			} else {
				atual = duplaRotacaoEsquerdaDireita(atual);
			}

		} else if (balanceamento == 2) {

			if (altura(atual.getDireita().getDireita()) >= altura(atual.getDireita().getEsquerda())) {
				atual = rotacaoEsquerda(atual);

			} else {
				atual = duplaRotacaoDireitaEsquerda(atual);
			}
		}

		if (atual.getPai() != null) {
			verificarBalanceamento(atual.getPai());
		} else {
			this.raiz = atual;
		}
	}

	public No rotacaoEsquerda(No inicial) {

		No direita = inicial.getDireita();
		direita.setPai(inicial.getPai());

		inicial.setDireita(direita.getEsquerda());

		if (inicial.getDireita() != null) {
			inicial.getDireita().setPai(inicial);
		}

		direita.setEsquerda(inicial);
		inicial.setPai(direita);

		if (direita.getPai() != null) {

			if (direita.getPai().getDireita() == inicial) {
				direita.getPai().setDireita(direita);

			} else if (direita.getPai().getEsquerda() == inicial) {
				direita.getPai().setEsquerda(direita);
			}
		}

		setBalanceamento(inicial);
		setBalanceamento(direita);

		return direita;
	}

	public No rotacaoDireita(No inicial) {

		No esquerda = inicial.getEsquerda();
		esquerda.setPai(inicial.getPai());

		inicial.setEsquerda(esquerda.getDireita());

		if (inicial.getEsquerda() != null) {
			inicial.getEsquerda().setPai(inicial);
		}

		esquerda.setDireita(inicial);
		inicial.setPai(esquerda);

		if (esquerda.getPai() != null) {

			if (esquerda.getPai().getDireita() == inicial) {
				esquerda.getPai().setDireita(esquerda);

			} else if (esquerda.getPai().getEsquerda() == inicial) {
				esquerda.getPai().setEsquerda(esquerda);
			}
		}

		setBalanceamento(inicial);
		setBalanceamento(esquerda);

		return esquerda;
	}

	public No duplaRotacaoEsquerdaDireita(No inicial) {
		inicial.setEsquerda(rotacaoEsquerda(inicial.getEsquerda()));
		return rotacaoDireita(inicial);
	}

	public No duplaRotacaoDireitaEsquerda(No inicial) {
		inicial.setDireita(rotacaoDireita(inicial.getDireita()));
		return rotacaoEsquerda(inicial);
	}

	private int altura(No atual) {
		if (atual == null) {
			return -1;
		}

		if (atual.getEsquerda() == null && atual.getDireita() == null) {
			return 0;

		} else if (atual.getEsquerda() == null) {
			return 1 + altura(atual.getDireita());

		} else if (atual.getDireita() == null) {
			return 1 + altura(atual.getEsquerda());

		} else {
			return 1 + Math.max(altura(atual.getEsquerda()), altura(atual.getDireita()));
		}
	}

	private void setBalanceamento(No no) {
		no.setBalanceamento(altura(no.getDireita()) - altura(no.getEsquerda()));
	}

	public int buscaPorCpf(long cpfBusca) {
		// arvore vazia
		if (raiz == null)
			return -1;

		// começa a procurar pela raiz
		No atual = raiz;
		Pessoa pAtual = PessoaGerenciador.getPessoa(atual.getChave());
		Pessoa pAux = new Pessoa();
		pAux.setCpf(cpfBusca);

		// enquanto nao encontrar o cpf
		while (pAtual.getCpf() != cpfBusca) {
			if (pAux.compareTo(pAtual, "cpf") < 0) {
				// se cpf é menor que o no atual caminha para esquerda
				atual = atual.getEsquerda();
			} else {
				// se for maior caminha para direita
				atual = atual.getDireita();
			}

			// nao encontrou
			if (atual == null)
				return -1;

			// aponta para a pessoa referente ao no atual
			pAtual = PessoaGerenciador.getPessoa(atual.getChave());
		}
		// se sair do laço eh porque encontrou, retorna o indice da pessoa
		// correspondente
		return atual.getChave();
	}

	public ArrayList<Integer> buscaPorNome(String nome) {
		// arvore vazia
		if (raiz == null)
			return null;

		// array de retorno
		ArrayList<Integer> ret = new ArrayList<>();

		// objeto auxiliar para fazer a comparação do nome
		Pessoa pAux = new Pessoa();
		pAux.setNome(nome);

		// começa pela raiz
		No atual = raiz;

		return buscaPorNome(nome, atual, ret, pAux);

	}

	private ArrayList<Integer> buscaPorNome(String nome, No no, ArrayList<Integer> ret, Pessoa pAux) {
		if (no != null) {
			// pessoa do no atual (começa pela raiz)
			Pessoa pAtual = PessoaGerenciador.getPessoa(no.getChave());

			// continua busca na subarvore esquerda
			buscaPorNome(nome, no.getEsquerda(), ret, pAux);

			// se a pessoa do no atual tem nome iniciado com a palavra do usuario ou tem o
			// mesmo nome, adiciona o indice dessa pessoa no array
			if (pAtual.getNome().startsWith(pAux.getNome())) {
				ret.add(no.getChave());
			}

			if (no.getDireita() != null) {
				if (PessoaGerenciador.getPessoa(no.getDireita().getChave()).getNome().startsWith(pAux.getNome())) {
					// continua busca na subarvore direita
					buscaPorNome(nome, no.getDireita(), ret, pAux);
				}
			}

		}

		return ret;

	}

	public ArrayList<Integer> buscaPorData(String dataIni, String dataFin) throws ParseException {
		// arvore vazia
		if (raiz == null)
			return null;

		// array de retorno
		ArrayList<Integer> ret = new ArrayList<>();

		// começa pela raiz
		No atual = raiz;

		// converte pra objeto Date
		DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dataInicial = sourceFormat.parse(dataIni);
		Date dataFinal = sourceFormat.parse(dataFin);

		return buscaPorData(dataInicial, dataFinal, atual, ret);

	}

	private ArrayList<Integer> buscaPorData(Date dataInicial, Date dataFinal, No no, ArrayList<Integer> ret)
			throws ParseException {

		if (no != null) {

			// pessoa do no atual (começa pela raiz)
			Pessoa pAtual = PessoaGerenciador.getPessoa(no.getChave());

			DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date dataAtual = sourceFormat.parse(pAtual.getData());
			
			// continua busca na subarvore esquerda
			buscaPorData(dataInicial, dataFinal, no.getEsquerda(), ret);
			
			if (dataAtual.compareTo(dataFinal) > 0) {
				return ret;
			}
			
			// se a pessoa do no atual nasceu entre as datas informadas, adiciona o indice
			// dessa pessoa no array
			if (dataAtual.compareTo(dataInicial) >= 0) {
				ret.add(no.getChave());
			}	
						
			// continua busca na subarvore direita
			buscaPorData(dataInicial, dataFinal, no.getDireita(), ret);
		}

		return ret;

	}

}
