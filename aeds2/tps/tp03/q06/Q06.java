import java.util.*;
import java.io.*;

public class Q06{
	public static void main(String args[]) throws Exception{
		Scanner sc = new Scanner(System.in);
		ColecaoRestaurantes c = ColecaoRestaurantes.lerCsv();

		//for(int i = 0; i < c.getTamanho(); i++){
		//	System.out.println(c.getRestaurante()[i].formatar());
		//}
			
		PilhaSequencial pilha = new PilhaSequencial(c.getTamanho());				

		//Parte 1
		int id = sc.nextInt();
		while(id != -1){
			for(int i = 0; i < c.getTamanho(); i++){
				if(c.getRestaurante()[i].getId() == id){
					pilha.inserir(c.getRestaurante()[i]);
				}
			}	
		
			id = sc.nextInt();
		}
		
		//Parte 2
		int qntRegistros = sc.nextInt();
		for(int i = 0; i < qntRegistros; i++){
			String comando = sc.next();
			
			if(comando.equals("I")){
				id = sc.nextInt();
			
				for(int j = 0; j < c.getTamanho(); j++){
					if(c.getRestaurante()[j].getId() == id){
						pilha.inserir(c.getRestaurante()[j]);
					}
				}
			}
			if(comando.equals("R")){
				System.out.println("(R)" + pilha.remover().getNome());
			}
		}
		
		for(int i = 0; i < pilha.getTopo(); i++){
			System.out.println(pilha.getRestaurantes()[i].formatar());
		}	
	}	
}

//Lista Sequencial
class PilhaSequencial{
	private Restaurante[] restaurantes;
	private int topo;

	public PilhaSequencial(int tamanho){
		this.restaurantes = new Restaurante[tamanho];//Cria um array de Restaurantes com o tamanho da coleção
		this.topo = 0;
	}

	public void inserir(Restaurante restaurante) throws Exception{//Insere um novo elemento na posição informada
		if(topo >= restaurantes.length){
			throw new Exception("Erro!");
		}
		
		this.restaurantes[topo] = restaurante;
		topo++;
	}

	public Restaurante remover() throws Exception{
		if(topo == 0){
			throw new Exception("Erro!");
		}
		
		return restaurantes[--topo];
	}

	//Gets e sets
	public Restaurante[] getRestaurantes(){
		return this.restaurantes;
	}

	public int getTopo(){
		return this.topo;
	}
	
}

class ColecaoRestaurantes{
	private int tamanho;
	private Restaurante[] restaurantes;

	public ColecaoRestaurantes(){
		this.tamanho = 0;
		this.restaurantes = null;
	}
	
	public ColecaoRestaurantes(int tamanho, Restaurante[] restaurantes){
		
	}

	public static ColecaoRestaurantes lerCsv(){//Manda o caminho do arquivo para o lerCsv e retorna a coleção para a main
		ColecaoRestaurantes colecao = new ColecaoRestaurantes();
		colecao.lerCsv("/tmp/restaurantes.csv");

		return colecao;
	}

	public void lerCsv(String path){
		try{
			File arquivo = new File(path);
			Scanner lerLinhas = new Scanner(arquivo);

			this.restaurantes = new Restaurante[10];//Tamanho inicial arbitrário
			
			lerLinhas.nextLine();
			while(lerLinhas.hasNextLine()){
				String linha = lerLinhas.nextLine();
				
				if(this.tamanho == this.restaurantes.length){//verifica se o array criado anteriormente está cheio
					restaurantes = redimensionar();
				}
				
				this.restaurantes[this.tamanho] = Restaurante.parseRestaurante(linha);
				this.tamanho++;
			}
			lerLinhas.close();
		}
		catch(Exception e){
			System.out.println("Erro ao ler o arquivo!");
			e.printStackTrace();
		}
	}
	
	//Gets e sets
	public int getTamanho(){
		return this.tamanho;
	}
	
	public Restaurante[] getRestaurante(){
		return this.restaurantes;
	}
	
	//Métodos auxiliares privados
	private Restaurante[] redimensionar(){//Redimensionamento dinâmico
		Restaurante[] novo = new Restaurante[this.restaurantes.length * 2];//Cria um novo array com o dobro do tamanho do anterior

		for(int i = 0; i < this.restaurantes.length; i++){
			novo[i] = this.restaurantes[i];//Preenche o novo array com os dados do array anterior
		}
	
		return novo;	
	}
}

class Restaurante{
	private int id;
	private String nome;
	private String cidade;
	private int capacidade;
	private double avaliacao;
	private String[] tiposCozinha;
	private int faixaPreco;
	private Hora horarioAbertura;
	private Hora horarioFechamento;
	private Data dataAbertura;
	private boolean aberto;
	
	/*
	*Construtor vazio removido para garantir nome obrigatório
	*public Restaurante(){
	*	this.nome = "";
	*}
	*/

	public Restaurante(String nome){//Já que o 'nome' não pode ser vazio, o construtor dispara uma excessão se recebê-lo vazio 
		if(nome == null || nome.isEmpty()){
			throw new IllegalArgumentException("Nome obrigatorio!");
		}
		this.nome = nome;
	}
	
	public static Restaurante parseRestaurante(String s){
		String buffer = "";
		int index = 1;
		
		for(int i = 0; i < s.length(); i++){//Conta o numero de dados a serem inseridos a partir do número total de vírgulas
			char c = s.charAt(i);
			
			if(c == ','){
				index++;
			}
		}
		
		String[] dados = new String[index];
		index = 0;

		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			
			if(c != ','){
				buffer += c;
			}
			else{
				dados[index] = buffer;
				index++;
				buffer = "";
			}
		}	
		dados[index] = buffer;

		Restaurante r = new Restaurante(dados[1]);//Cria um novo objeto passando o 'nome' para o contrutor
	
		r.id = Integer.parseInt(dados[0]);
		r.cidade = dados[2];
		r.capacidade = Integer.parseInt(dados[3]);
		r.avaliacao = Double.parseDouble(dados[4]);
		r.tiposCozinha = r.separarTiposCozinha(dados[5]);
		r.faixaPreco = dados[6].length();

		//Chama o método "separarHoras" 2 vezes sem necessidade:
		//r.horarioAbertura = Hora.parseHora(r.separarHoras(dados[7])[0]);
		//r.horarioFechamento = Hora.parseHora(r.separarHoras(dados[7])[1]);

		//Melhor:
		String[] h = r.separarHoras(dados[7]);
		r.horarioAbertura = Hora.parseHora(h[0]);
		r.horarioFechamento = Hora.parseHora(h[1]);

		r.dataAbertura = Data.parseData(dados[8]);
		r.aberto = Boolean.parseBoolean(dados[9]);

		return r;			
	}

	public String formatar(){
		String s = String.format("[%d ## %s ## %s ## %d ## %.2f ## [%s] ## %s ## %s-%s ## %s ## %b]", this.id, this.nome, this.cidade, this.capacidade, this.avaliacao,
		this.formatarTiposCozinha(this.tiposCozinha), this.formatarFaixaPreco(), this.horarioAbertura.formatar(), this.horarioFechamento.formatar(), this.dataAbertura.formatar(), this.aberto);
		return s;
	}

	//Gets e sets
	public int getId(){
		return this.id;
	}
	
	public String getNome(){
		return this.nome;
	}

	//Métodos auxiliares privados
	private String[] separarTiposCozinha(String s){
		int qntTipos = 1;
		String buffer = "";
		char c;

		for(int i = 0; i < s.length(); i++){//Verifica a quantos tipos de cozinha o restaurante tem
			c = s.charAt(i);
			
			if(c == ';'){
				qntTipos++;	
			}
		}
		
		String[] tipos = new String[qntTipos];
		int index = 0;

		for(int i = 0; i < s.length(); i++){//Separa cada tipo em um índice de um array
			c = s.charAt(i);
			
			if(c != ';'){
				buffer += c;
			}	
			else{
				tipos[index] = buffer;
				index++;
				buffer = ""; 	
			}
		}
		tipos[index] = buffer;
		
		return tipos;
	}
	
	private String formatarTiposCozinha(String[] s){
		String result = "";		

		for(int i = 0; i < s.length; i++){
			if(i != (s.length - 1)){
				result += s[i];
				result += ',';
			}
			else{
				result += s[i];
			}
		}
		return result;
	}
	
	private String formatarFaixaPreco(){
		String result = "";
		
		for(int i = 0; i < this.faixaPreco; i++){
			result += '$';
		}

		return result;
	}

	private String[] separarHoras(String s){
		String[] result = new String[2];
	 	String buffer = "";
		int index = 0;	

		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			
			if(c != '-'){
				buffer += c;		
			}
			else{
				result[index] = buffer;
				index++;
				buffer = "";	
			}
		}
		result[index] = buffer;
		return result;	
	}

}

class Hora{
	private int hora;
	private int minuto;
		
	public static Hora parseHora(String s){
		Hora h = new Hora();
		
		String bufferHora = "";
		String bufferMinuto = "";

		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			
			if(c != ':' && i < 2){
				bufferHora += c;
			}
			else if(c != ':'){
				bufferMinuto += c; 
			}
		}
		
		h.hora = Integer.parseInt(bufferHora);
		h.minuto = Integer.parseInt(bufferMinuto);
		
		return h;
	}
	
	public String formatar(){
		String result = String.format("%02d:%02d", this.hora, this.minuto);
		return result;
	}
}

class Data{
	private int ano;
	private int mes;
	private int dia;

	public static Data parseData(String s){
		Data d = new Data();

		String bufferAno = "";
		String bufferMes = "";
		String bufferDia = "";
		
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			
			if(c != '-' && i < 4){
				bufferAno += c;	
			}
			else if(c != '-' && i < 7){
				bufferMes += c;
			}
			else if(c != '-'){
				bufferDia += c;
			}
		}

			d.ano = Integer.parseInt(bufferAno);
			d.mes = Integer.parseInt(bufferMes);
			d.dia = Integer.parseInt(bufferDia);

		return d;
	}

	public String formatar(){
		String result = String.format("%02d/%02d/%d", this.dia, this.mes, this.ano);
		return result;	
	}
}

