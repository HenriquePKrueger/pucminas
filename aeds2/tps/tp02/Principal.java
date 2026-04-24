import java.util.Scanner;


public class Principal{
	public static void main(String[] args){
		//testes
		Restaurante r1 = Restaurante.parseRestaurante("1,Classic Palace Works,Zurich,168,3.9,churrasco;internacional,$$,11:00-20:00,2018-03-31,false");		

		/*
		* Data d1 = Data.parseData("2026-11-12"); //Chama o método parseData antes de criar o objeto
		* String dataFormat = d1.formatar();
		* System.out.println(dataFormat);

		* Hora h1 = Hora.parseHora("11:00");
		* System.out.println(h1.formatar());
		*/
	}
}

//Classe Composta/Principal
class Restaurante{
	private int id;
	private String nome;
	private	String cidade;
	private	int capacidade;
	private	double avaliacao;
	private String[] tiposCozinha;
	private int faixaPreco;
	private Hora horarioAbertura;
	private Hora horarioFechamento;
	private	Data dataAbertura;
	private	boolean aberto;
	
	public Restaurante(DadosRestaurante dados){
		this.id = dados.id;
		this.nome = dados.nome;
		this.cidade = dados.cidade;
		this.capacidade = dados.capacidade;
		this.avaliacao = dados.avaliacao;
		this.tiposCozinha = dados.tiposCozinha;
		this.faixaPreco = dados.faixaPreco;
		this.horarioAbertura = dados.horarioAbertura;
		this.horarioFechamento = dados.horarioFechamento;
		this.dataAbertura = dados.dataAbertura;
		this.aberto = dados.aberto;
	}

	public static Restaurante parseRestaurante(String strRestaurante){
		DadosRestaurante d = new DadosRestaurante();//Instancia o conjunto de dados do objeto restaurante atual
		String[] arrDados = new String[10];//Cria um array de string para que os dados possam ser inseridos de forma mais limpa
		String temp = "";
		int indice = 0;

		for(int i = 0; i < strRestaurante.length(); i++){
			char c = strRestaurante.charAt(i);

			if(c == ','){
				arrDados[indice] = temp;
				temp = "";
				indice++;
			}
			else{
				temp += c;
			}
		}
		arrDados[indice] = temp;

		temp = "";
		indice = 0;
		String[] hrSeparada = new String[2];

		for(int i = 0; i < arrDados[7].length(); i++){//Separar a String das horas
			char c = arrDados[7].charAt(i);
			if(c == '-'){
				hrSeparada[indice] = temp;
				temp = "";
				indice++;	
			}
			else{
				temp += c;
			}
		}
		hrSeparada[indice] = temp;
		

		for(int i = 0; i < 10; i++){//Debugar
			System.out.println(arrDados[i]);
		}
		
		Hora hr1 = Hora.parseHora(hrSeparada[0]);
		Hora hr2 = Hora.parseHora(hrSeparada[1]);
		Data dtAbertura = Data.parseData(arrDados[8]);
		
		d.id = Integer.parseInt(arrDados[0]);
		d.nome = arrDados[1];
		d.cidade = arrDados[2];
		d.capacidade = Integer.parseInt(arrDados[3]);
		d.avaliacao = Double.parseDouble(arrDados[4]);
		d.parseTiposCozinha(arrDados[5]);
		d.faixaPreco = arrDados[6].length();

		d.horarioAbertura = hr1;
		d.horarioFechamento = hr2;

		d.dataAbertura = dtAbertura;
		d.aberto = Boolean.parseBoolean(arrDados[9]);
		
		System.out.println(hr1.formatar());
		System.out.println(hr2.formatar());
		System.out.println(dtAbertura.formatar());
		
		return new Restaurante(d);
	}
}

//Classes Componentes
class Hora{
	private int hora;
	private int minuto;

	public Hora(int hora, int minuto){
		this.hora = hora;
		this.minuto = minuto;
	}
	
	public static Hora parseHora(String strHora){
		String hora = "";
		String minuto = "";

		for(int i = 0; i < strHora.length(); i++){
			char c = strHora.charAt(i);
			if(c != ':' && hora.length() != 2){
				hora += c;
			}
			else if(c != ':' && minuto.length() != 2){
				minuto += c;
			}
		}
		return new Hora(Integer.parseInt(hora), Integer.parseInt(minuto));
	}

	public String formatar(){
		return String.format("%02d:%02d", this.hora, this.minuto);
	}
}

class Data{
	private int ano;
	private int mes;
	private int dia;

	//Construtor
	public Data(int ano, int mes, int dia){
		this.ano = ano;
		this.mes = mes;
		this.dia = dia;	
	}

	//Retorno é do tipo "Data" para que o retorno chame o construtor e crie o objeto
	public static Data parseData(String strData){
		String ano = "";
		String mes = "";
		String dia = "";

		for(int i = 0; i < strData.length(); i++){
			char c = strData.charAt(i);
			if(c != '-' && ano.length() != 4){
				ano += c;
			}
			else if(c != '-' && mes.length() != 2){
				mes += c;
			}
			else if(c != '-' && dia.length() != 2){
				dia += c;
			}
		}
		
		return new Data(Integer.parseInt(ano), Integer.parseInt(mes), Integer.parseInt(dia));//Converte todas as strings em inteiros
	}

	//Envia a data formatada para a main
	public String formatar(){
		return String.format("%02d/%02d/%d", this.dia, this.mes, this.ano);
	}
}

//Classe DTO(Data Transfer Object)
class DadosRestaurante{
	public int id;
	public String nome;
	public String cidade;
	public int capacidade;
	public double avaliacao;
	public String[] tiposCozinha;
	public int faixaPreco;
	public Hora horarioAbertura;
	public Hora horarioFechamento;
	public Data dataAbertura;
	public boolean aberto;

	public void parseTiposCozinha(String str){
		int qnt = 0;

		for(int i = 0; i < str.length(); i++){//Verifica qual o tamanho do array "tiposCozinha"
			char c = str.charAt(i);
			if(c == ';'){
				qnt++;
			}
		}
		
		tiposCozinha = new String[qnt + 1];//Cria o array com o tamanho encontrado
		
		//Preencher o array "tiposCozinha"
		String temp = "";
		int indice = 0;

		for(int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			if(c == ';'){
				this.tiposCozinha[indice] = temp;
				temp = "";
				indice++;
			}
			else{
				temp += c;
			}
		}
	}
}
