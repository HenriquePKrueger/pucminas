import java.util.*;

//Considerando que essa é a questão de número 1, acabei optando por deixar alguns comentários no código. Esses me ajudam a entender as melhores práticas de programação

public class Q01{
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		
		while(sc.hasNextLine()){
			String entrada = sc.nextLine();
			Restaurante r = Restaurante.parseRestaurante(entrada);
			System.out.println(r.formatar());
		}
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
	*Considerando que a chave de busca é o 'nome' esse não pode ser vazio
	*public Restaurante(){
	*	this.nome = "";
	*}
	*/

	public Restaurante(String nome){//Já que o 'nome' não pode ser vazio, o construtor dispara uma excessão se receber esse atributo vazio 
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

