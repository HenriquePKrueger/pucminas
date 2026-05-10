import java.util.*;
import java.io.*;

public class Q11{
	public static void main(String args[]) throws Exception{
		Scanner sc = new Scanner(System.in);
		ColecaoRestaurantes c = ColecaoRestaurantes.lerCsv();

		//for(int i = 0; i < c.getTamanho(); i++){
		//	System.out.println(c.getRestaurante()[i].formatar());
		//}
			
		ListaSequencial lista = new ListaSequencial(c.getTamanho());//Cria uma lista e manda para o contrutor o tamanho total da coleção				

		//Parte 1
		int id = sc.nextInt();
		while(id != -1){
			for(int i = 0; i < c.getTamanho(); i++){
				if(c.getRestaurante()[i].getId() == id){
					lista.inserirFim(c.getRestaurante()[i]);//No final da lista será inserido o elemento indicado
				}
			}	
		
			id = sc.nextInt();
		}
		
		//Parte 2
		int qntRegistros = sc.nextInt();
		for(int i = 0; i < qntRegistros; i++){
			String comando = sc.next();
			
			if(comando.equals("II")){
				id = sc.nextInt();
				for(int j = 0; j < c.getTamanho(); j++){
					if(c.getRestaurante()[j].getId() == id){
						lista.inserirInicio(c.getRestaurante()[j]);
					}
				}

			}
			if(comando.equals("I*")){
				int posicao = sc.nextInt();
				id = sc.nextInt();
				
				for(int j = 0; j < c.getTamanho(); j++){
					if(c.getRestaurante()[j].getId() == id){
						lista.inserir(c.getRestaurante()[j], posicao);
					}
				}
			}
			if(comando.equals("IF")){
				id = sc.nextInt();
				
				for(int j = 0; j < c.getTamanho(); j++){
					if(c.getRestaurante()[j].getId() == id){
						lista.inserirFim(c.getRestaurante()[j]);
					}
				}
			}
			if(comando.equals("RI")){
				System.out.println("(R)" + lista.removerInicio().getNome());
			}
			if(comando.equals("R*")){
				int posicao = sc.nextInt();
				System.out.println("(R)" + lista.remover(posicao).getNome());
			}
			if(comando.equals("RF")){
				System.out.println("(R)" + lista.removerFim().getNome());
			}
		}
		
		for(int i = 0; i < lista.getN(); i++){
			System.out.println(lista.getRestaurantes()[i].formatar());
		}	
	}	
}

//Lista Sequencial
class ListaSequencial{
	private Restaurante[] restaurantes;
	private int n;//número de índices ocupados(registros cadastrados) no array

	public ListaSequencial(int tamanho){
		this.restaurantes = new Restaurante[tamanho];//Cria um array de Restaurantes com o tamanho da coleção
		this.n = 0;
	}

	/*
	* Entradas:
	* Parte 1) Número inteiro que que representa o ID de um restaurante. Este deve ser inserido no final da fila(para de ler no "-1")
	* Parte 2.0) Número de registros(qntRegistros) que serão inseridos ou removidos
	* parte 2.1) Nas linhas seguintes serão dados os comando:
	* "II X" = Inserir no início o elemento de ID 'X'
	* "I* Y X" = Inserir na posição 'Y' o elemento de ID 'X'
	* "IF X" = Inserir no fim o elemento de ID 'X'
	* "RI" = Remover do início
	* "R* Y X" = Remover da posição 'Y' o elemento de ID 'X' 
	* "RF" = Remover do fim 
	*
   	* Saídas:
	* Deve ter uma linha para cada registro removido. Essa saída seguirá a seguinte formatação: "(R)nome do elemento removido"
	* No final mostrar os restaurantes presentes na lista de forma crescente
	*/

	public void inserirInicio(Restaurante restaurante) throws Exception{
		if(n >= this.restaurantes.length){
			throw new Exception("Erro!");
		}
		
		for(int i = n; i > 0; i--){
			this.restaurantes[i] = this.restaurantes[i - 1]; 
		}
		
		this.restaurantes[0] = restaurante;
		n++;
	}

	public void inserirFim(Restaurante restaurante) throws Exception{
		if(n >= this.restaurantes.length){
			throw new Exception("Erro!");
		}

		this.restaurantes[n] = restaurante;
		n++; 
	}

	public void inserir(Restaurante restaurante, int posicao) throws Exception{//Insere um novo elemento na posição informada
		for(int i = this.n; i > posicao; i--){
			this.restaurantes[i] = this.restaurantes[i - 1];
		}
		
		this.restaurantes[posicao] = restaurante;
		n++;
		
	}

	public Restaurante removerInicio(){
		Restaurante resp = restaurantes[0];			
		
		n--;
		for(int i = 0; i < this.n; i++){
			this.restaurantes[i] = this.restaurantes[i + 1];
		}
		
		return resp;
	}

	public Restaurante removerFim(){
		return restaurantes[--n]; 
	}
	
	public Restaurante remover(int posicao){
		Restaurante resp = this.restaurantes[posicao];
		n--;	
	
		for(int i = posicao; i < n; i++){
			this.restaurantes[i] = this.restaurantes[i + 1];
		}
		
		return resp;
	}

	//Gets e sets
	public Restaurante[] getRestaurantes(){
		return this.restaurantes;
	}

	public int getN(){
		return this.n;
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

