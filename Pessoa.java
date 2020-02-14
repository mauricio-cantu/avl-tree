package trabgb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pessoa {
	private Long cpf;
	private int rg;
	private String nome;
	private String data;
	private String cidade;

	public Pessoa(long cpf, int rg, String nome, String data, String cidade) {
		this.cpf = cpf;
		this.rg = rg;
		this.nome = nome;
		this.data = data;
		this.cidade = cidade;
	}

	public Pessoa() {
		// TODO Auto-generated constructor stub
	}

	public String getNome() {
		return nome;
	}

	public String getData() {
		return data;
	}

	public long getCpf() {
		return cpf;
	}

	@Override
	public String toString() {
		return "Nome: " + nome + ", CPF: " + cpf + ", RG: " + rg + ", Nascimento: " + data + ", Cidade: " + cidade
				+ "\n";
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int compareTo(Pessoa op, String tipo) {
		switch (tipo) {
		case "nome":
			return this.nome.compareTo(op.getNome());
		case "cpf":
			return this.cpf.compareTo(op.getCpf());
		case "data":
			DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
			try {
				Date date = sourceFormat.parse(this.data);
				Date otherDate = sourceFormat.parse(op.getData());
				return date.compareTo(otherDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		return 0;

	}

}
