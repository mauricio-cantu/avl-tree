package trabgb;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PessoaTest {

	// componentes a serem usados
	static JFrame frame;
	static JPanel buttonsPanel;
	static JTextArea result;
	static JScrollPane resultScroll;
	static JButton buttonCpf;
	static JButton buttonNome;
	static JButton buttonData;
	static JButton buttonReset;

	public static void main(String[] args) {

		// carrega arquivo com os dados das pessoas
		try {
			PessoaGerenciador.carregar(new File("C:\\Users\\mauricio.jochims\\Documents\\teste.csv"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// exibe janela principal do programa
		initFrame();
	}

	public static void initFrame() {

		// cria janela principal
		frame = new JFrame("Consulta Pessoas");
		// tamanho
		frame.setSize(600, 400);
		// centraliza a posição da janela
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// cria painel dos botoes de pesquisa
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		// cria os botoes
		buttonCpf = new JButton("CPF");
		buttonNome = new JButton("Nome");
		buttonData = new JButton("Data de Nascimento");
		buttonReset = new JButton("Limpar");

		// adiciona botoes no painel
		buttonsPanel.add(buttonCpf);
		buttonsPanel.add(buttonNome);
		buttonsPanel.add(buttonData);
		buttonsPanel.add(buttonReset);

		// adiciona o painel de botoes na janela principal
		frame.add(buttonsPanel, BorderLayout.NORTH);

		// cria textarea pra exibir os resultados da pesquisa
		result = new JTextArea();
		// define que nao sera editavel (apenas leitura)
		result.setEditable(true);
		// define fonte dos resultados
		result.setFont(new Font("Arial", Font.BOLD, 12));
		// define espacamento entre o texto de resultado e a janela
		result.setMargin(new Insets(10, 10, 10, 10));
		// cria barra de scroll para a area de resultados
		resultScroll = new JScrollPane(result);
		// define que as barras de scroll horizontal e verical serão exibidas apenas
		// quando for necessario (quando ultrapassar o tamanho da janela)
		resultScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		resultScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// adiciona a area de resultado na janela principal
		frame.add(resultScroll);

		// mostra a janela principal
		frame.setVisible(true);

		// define acao dos botoes
		addActionListeners();

	}

	// funcao para definir o que cada botao deve fazer ao ser clicado
	public static void addActionListeners() {

		// evento de clique no botao de busca por cpf
		buttonCpf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// solicita cpf ao usuario
					long cpf = Long.parseLong(JOptionPane.showInputDialog(frame, "Digite um CPF:"));
					// guarda o objeto pessoa correspondente ao cpf (caso encontrado)
					Pessoa aux = PessoaGerenciador.buscaPorCpf(cpf);
					if (aux != null) {
						// exibe a pessoa na area de resultado
						result.setText(aux.toString());
					} else {
						JOptionPane.showMessageDialog(frame, "CPF não encontrado!");
					}

				} catch (Exception ex) {
				}
			}
		});

		// evento de clique no botao de busca por nome
		buttonNome.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// array list para guardar as pessoas encontradas na arvore
				ArrayList<Pessoa> p = new ArrayList<>();
				// solicita nome ao usuario
				String nome = JOptionPane.showInputDialog(frame, "Digite a incial ou o nome:");
				// guarda as pessoas encontradas
				p = PessoaGerenciador.buscaPorNome(nome);
				// apaga o conteudo da area de resultado
				result.setText("");
				// exibe alerta de nenhuma pessoa encontrada caso array esteja vazio
				if (p.size() == 0) {
					JOptionPane.showMessageDialog(frame, "Nenhuma pessoa encontrada!");
				} else {
					// printa cada pessoa na area de resultado
					for (Pessoa pessoa : p) {
						result.append("\n" + pessoa.toString() + "\n");
					}
				}

			}
		});

		// evento de clique no botao de busca por data
		buttonData.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// array list para guardar as pessoas encontradas na arvore
				ArrayList<Pessoa> p = new ArrayList<>();
				// solicita data inicial e final ao usuario
				String dataIni = JOptionPane.showInputDialog(frame, "Digite a data inicial:");
				String dataFim = JOptionPane.showInputDialog(frame, "Digite a data final:");
				// guarda as pessoas encontradas
				p = PessoaGerenciador.buscaPorData(dataIni, dataFim);
				// apaga o conteudo da area de resultado
				result.setText("");
				// exibe alerta de nenhuma pessoa encontrada caso array esteja vazio
				if (p.size() == 0) {
					JOptionPane.showMessageDialog(frame, "Nenhuma pessoa encontrada!");
				} else {
					// printa cada pessoa na area de resultado
					for (Pessoa pessoa : p) {
						result.append("\n" + pessoa.toString() + "\n");
					}
				}

			}
		});

		// evento de clique no botao limpar (apaga tudo da area de resultado)
		buttonReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result.setText("");
			}
		});
	}

}
