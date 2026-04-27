import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Q05{
	
	static int comparacoes = 0;
	static int movimentacoes = 0;

	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		ColecaoRestaurantes c = ColecaoRestaurantes.lerCsv();
		Restaurante[] encontrados = new Restaurante[500];//Variável para contar a quantidade de restaurantes que o usuário vai buscar
		int cont = 0;

		String entrada = sc.nextLine();

		while(!entrada.equals("-1")){//Lê os números da entrada até o -1
			int id = Integer.parseInt(entrada);
			c.setIdRestaurante(id);

			Restaurante restauranteEncontrado = c.getRestaurantes();

			if(restauranteEncontrado != null){
				encontrados[cont] = restauranteEncontrado;//Monta uma string que será exibida no final
				cont++;
			}
			entrada = sc.nextLine();
		}
		
		ColecaoRestaurantes ordenar = new ColecaoRestaurantes(encontrados, cont);

		long inicio = System.currentTimeMillis();//Inicia o contador
		
		boolean[] resultados = new boolean[500];
		int contBuscas = 0;		

		entrada = sc.nextLine();
		while(!entrada.equals("FIM")){//Lê os nomes atem o "FIM"
			resultados[contBuscas] = ordenar.pesquisaSequencial(entrada);
			contBuscas++;
			entrada = sc.nextLine();
		}

		long fim = System.currentTimeMillis();//finaliza o contador
		double tempoTotal = (fim - inicio) / 1000.0;//Converter para segundos
		
		for(int i = 0; i < contBuscas; i++){
			System.out.println(resultados[i] ? "SIM" : "NAO");
		}
	
		PrintWriter log = new PrintWriter(new FileWriter("899683_sequencial.txt"));
		log.printf("899683\t%d\t%d\t%f", Q05.comparacoes, Q05.movimentacoes, tempoTotal);
		log.close();

		sc.close();
	}
}

//Gerenciador
class ColecaoRestaurantes{
	private int tamanho;
	private Restaurante[] restaurantes;
	private int id;//Variável que armazena o id desejado pelo usuário	
	public static ColecaoRestaurantes lerCsv() throws Exception{//Cria uma nova coleção com base no arquivo indicado
		ColecaoRestaurantes c = new ColecaoRestaurantes();
		c.lerCsv("/tmp/restaurantes.csv");
		return c;
	}

	public ColecaoRestaurantes(){
		this.restaurantes= null;
		this.tamanho = 0;
	}
	
	public ColecaoRestaurantes(Restaurante[] r, int size){
		this.restaurantes = r;
		this.tamanho = size;
	}

	public boolean pesquisaSequencial(String nomeBuscado){//Algorítmo de pesquisa sequencial
		boolean encontrado = false;
		int i = 0;
		
		while(i < this.tamanho && !encontrado){
			Q05.comparacoes++;
			if(this.restaurantes[i].getNome().equals(nomeBuscado)){
				encontrado = true;
			}
			i++;
		}
		return encontrado;
	}

	public Restaurante[] getListaRestaurantes(){
		return this.restaurantes;
	}	

	public void lerCsv(String path) throws Exception{//Adicionei o "throws Exception" para remover os try catchs que estavam poluindo o código
		BufferedReader br = new BufferedReader(new FileReader(path));
		br.readLine();//Descartar a primeira linha do CSV

		while(br.readLine() != null){//Verifica o número de restaurantes no arquivo
			this.tamanho++;
		}
			
		br.close();

		br = new BufferedReader(new FileReader(path)); //Lê o arquivo novamente, desta vez preenchendo o array
		br.readLine();

		restaurantes = new Restaurante[tamanho];//Cria o array com o número certo de restaurantes que serão inseridos

		for(int i = 0; i < this.tamanho; i++){//Monta o array de restaurantes
			restaurantes[i] = Restaurante.parseRestaurante(br.readLine());
				
		}

		br.close();		
	}	
	
	public Restaurante getRestaurantes(){
		for(int i = 0; i < tamanho; i++){
			if(restaurantes[i].getId() == this.id){
				return restaurantes[i];
			}
		}
		return null;
	}
	
	public void setIdRestaurante(int id){
		this.id = id;
	}

	public int getTamanho(){
		return tamanho;
	}
}

//Classe Principal
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
	
	public Restaurante(DadosRestaurante dados){//O construtor recebe os dados vindo do DTO e atribui aos dados do novo objeto criado
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
	
	public String getNome(){
		return this.nome;
	}

	public String getCidade(){
		return this.cidade;
	}
	
	public int getId(){
		return this.id;
	}
		
	public static Restaurante parseRestaurante(String strRestaurante){
		DadosRestaurante d = new DadosRestaurante();//Cria um novo conjunto de dados para o objeto restaurante atual
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
		
		//Define o valor das váriáveis na classe "DadosRestaurante"
		d.id = Integer.parseInt(arrDados[0]);
		d.nome = arrDados[1];
		d.cidade = arrDados[2];
		d.capacidade = Integer.parseInt(arrDados[3]);
		d.avaliacao = Double.parseDouble(arrDados[4]);
		d.parseTiposCozinha(arrDados[5]);
		d.faixaPreco = arrDados[6].length();
		
		d.horarioAbertura = Hora.parseHora(hrSeparada[0]);
		d.horarioFechamento = Hora.parseHora(hrSeparada[1]);

		d.dataAbertura = Data.parseData(arrDados[8]);

		d.aberto = Boolean.parseBoolean(arrDados[9]);
		
		return new Restaurante(d);//Cria o novo objeto enviando a classe auxiliar como parâmetro para o construtor
	}
	
	public String formatar(){
		String strTiposCozinha = "";
		String strFaixaPreco = "";
		
		for(int i = 0; i < this.tiposCozinha.length; i++){//Pega os elementos do array "tiposCozinha" e monta uma única string
			strTiposCozinha += this.tiposCozinha[i];
			if(i != (tiposCozinha.length - 1)){
				strTiposCozinha += ",";
			}
		}
		for(int i = 0; i < this.faixaPreco; i++){//Monta a String referente à "faixaPreco"
			strFaixaPreco += "$";
		}
		
		return String.format("[%d ## %s ## %s ## %d ## %.1f ## [%s] ## %s ## %s-%s ## %s ## %b]", this.id, this.nome, this.cidade, this.capacidade, this.avaliacao, strTiposCozinha, strFaixaPreco, this.horarioAbertura.formatar(), this.horarioFechamento.formatar(), this.dataAbertura.formatar(), this.aberto);
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

	//Envia a data formatada
	public String formatar(){
		return String.format("%02d/%02d/%d", this.dia, this.mes, this.ano);
	}
}

//Classe auxiliar(manter o código mais limpo)
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
		this.tiposCozinha[indice] = temp;
	}
}

