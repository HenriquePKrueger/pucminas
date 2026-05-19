import java.util.*;
import java.io.*;

/*
* Reimplementação do UML com foco em melhoria das estruturas, visando melhorias no código e aplicação de boas práticas de programação.
* Alguns métodos e funções comentados foram mantidos para fins de estudo e comparação entre
 diferentes abordagens.
*/

public class Q02{
	
	static int comparacoes = 0;
	static int movimentacoes = 0;

	public static void main(String args[]) throws Exception{
		Scanner sc = new Scanner(System.in);
		//ColecaoRestaurantes c = ColecaoRestaurantes.lerCsv();
		//int k = 10;
		
		int[] idsBusca = new int[10];
		int qntIds = 0;

		int id;
		while((id = sc.nextInt()) != -1){
			if(qntIds == idsBusca.length){
				idsBusca = redimensionar(idsBusca);
			}
			idsBusca[qntIds] = id;
			qntIds++;
		}
		sc.close();	
		
		ColecaoRestaurantes c = ColecaoRestaurantes.lerCsv();
		Restaurante[] rest = c.separarPorIds(idsBusca, qntIds);		

		long inicio = System.currentTimeMillis();//Inicia o contador
                Ordenacao ordenado = new Ordenacao();
		Restaurante[] resultado = ordenado.quicksort(rest);
                long fim = System.currentTimeMillis();//finaliza o contador

		double tempoTotal = (fim - inicio) / 1000.0;//Converter para segundos

		for(int i = 0; i < qntIds; i++){
			System.out.println(resultado[i].formatar());
		}

		PrintWriter log = new PrintWriter(new FileWriter("899683_quicksort.txt"));
		log.printf("899683\t%d\t%d\t%f", Q02.comparacoes, Q02.movimentacoes, tempoTotal);
		log.close();
	}
		
	//Método auxiliar privado
	private static int[] redimensionar(int[] idsBusca){//Redimensionamento dinâmico
		int[] novo = new int[idsBusca.length * 2];//Cria um novo array com o dobro do tamanho do anterior
 
            	for(int i = 0; i < idsBusca.length; i++){
                         novo[i] = idsBusca[i];//Preenche o novo array com os dados do array anterior
                 }
 
                 return novo;
         }
	
}

class Ordenacao{//Ordenar pela chave "nome"
	private int k = 10;

	private int comparar(Restaurante a, Restaurante b){

        	if(a.getAvaliacao() < b.getAvaliacao()){
            		return -1;
       		 }

        	if(a.getAvaliacao() > b.getAvaliacao()){
            		return 1;
        	}
        	return a.getNome().compareTo(b.getNome());
	}
	
	public Restaurante[] quicksort(Restaurante[] restaurantes){
			quicksortParcial(restaurantes, 0, restaurantes.length - 1);
			return restaurantes;
	}

	public void quicksortParcial(Restaurante[] restaurantes, int esq, int dir){
		int i = esq;
		int j = dir;
		Restaurante pivo = restaurantes[(esq + dir) / 2];
		
		while(i <= j){
			while(comparar(restaurantes[i], pivo) < 0){
				i++;
				Q02.comparacoes++;
			}
			while(comparar(restaurantes[j], pivo) > 0){
				j--;
				Q02.comparacoes++;
			}
			if(i <= j){
				swap(restaurantes, i, j);
				Q02.movimentacoes += 3;
				i++;
				j--;
			}
		}
		if(esq < j && esq < k){
			quicksortParcial(restaurantes, esq, j);
		}
		if(i < k && i < dir){
			quicksortParcial(restaurantes, i, dir);
		}
	}

	private void swap(Restaurante[] restaurantes, int menor, int i){
		Restaurante temp = restaurantes[menor];
		restaurantes[menor] = restaurantes[i];
		restaurantes[i] = temp;
	}
}

class ColecaoRestaurantes{
	private int tamanho;
	private Restaurante[] restaurantes;
	private Restaurante[] buscaPorIds;

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
	
	public Restaurante[] separarPorIds(int[] idsBusca, int qntIds){
		Restaurante[] results = new Restaurante[qntIds];
		int index = 0;			

		for(int i = 0; i < this.tamanho; i++){
			int atual = this.restaurantes[i].getId();
		
			for(int j = 0; j < qntIds; j++){
				if(idsBusca[j] == atual){
					results[index] = this.restaurantes[i];	
					index++;	
				}
			}
		}
		
		return results;
	}

	//Gets e Sets
	public int getTamanho(){
		return this.tamanho;
	}
	
	public Restaurante[] getRestaurante(){
		return this.restaurantes;
	}
	
	//Métodos auxiliares privados
        private Restaurante[] redimensionar(){//Redimensionamento dinâmico
		Restaurante[] novo = new Restaurante[restaurantes.length * 2];//Cria um novo array com o dobro do tamanho do anterior
 
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
		String s = String.format("[%d ## %s ## %s ## %d ## %.1f ## [%s] ## %s ## %s-%s ## %s ## %b]", this.id, this.nome, this.cidade, this.capacidade, this.avaliacao,
		this.formatarTiposCozinha(this.tiposCozinha), this.formatarFaixaPreco(), this.horarioAbertura.formatar(), this.horarioFechamento.formatar(), this.dataAbertura.formatar(), this.aberto);
		return s;
	}

	//Gets e sets
	public String getNome(){
		return this.nome;
	}
	
	public int getId(){
		return this.id;	
	}

	public double getAvaliacao(){
		return this.avaliacao;
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

