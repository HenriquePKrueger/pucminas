import java.util.*;

public class Q01{
	public static void main(String args[]){
		Data d = Data.parseData("2026-12-27");
		System.out.println(d.formatar());
		Hora h = Hora.parseHora("11:00");
		System.out.println(h.formatar());
		Restaurante r = Restaurante.parseRestaurante("1,Classic Palace Works,Zurich,168,3.9,churrasco;internacional,$$,11:00-20:00,2018-03-31,false");
		System.out.println(r.formatar());
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
	
	public Restaurante(){//Fazer construtor
	}
	
	public static Restaurante parseRestaurante(String s){
		Restaurante r = new Restaurante();

		String sTmp = "";
		int contVirgula = 1;
		
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			
			if(c == ','){
				contVirgula++;
			}
		}
		
		String[] arrDados = new String[contVirgula];
		contVirgula = 0;

		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			
			if(c != ','){
				sTmp += c;
			}
			else{
				arrDados[contVirgula] = sTmp;
				System.out.println(arrDados[contVirgula]);
				contVirgula++;
				sTmp = "";
			}
		
		}
		
		arrDados[contVirgula] = sTmp;
		System.out.println(arrDados[contVirgula]);

		//teste
		String[] teste = new String[2];
		teste[0] = "teste1;";
		teste[1] = " teste2";		

		r.id = Integer.parseInt(arrDados[0]);
		r.nome = arrDados[1];
		r.cidade = arrDados[2];
		r.capacidade = Integer.parseInt(arrDados[3]);
		r.avaliacao = Double.parseDouble(arrDados[4]);
		r.tiposCozinha = teste;//Separar arrDados[5]
		r.faixaPreco = arrDados[6].length();
		r.horarioAbertura = Hora.parseHora(r.separarHoras(arrDados[7])[0]);//Separar arrDados[7]
		r.horarioFechamento = Hora.parseHora(r.separarHoras(arrDados[7])[1]);
		r.dataAbertura = Data.parseData(arrDados[8]);
		r.aberto = Boolean.parseBoolean(arrDados[9]);

		return r;			
	}

	public String formatar(){
		String s = String.format("[%d ## %s ## %s ## %d ## %.2f ## %s ## %d ## %s-%s ## %s ## %b]", this.id, this.nome, this.cidade, this.capacidade, this.avaliacao,
		this.tiposCozinha, this.faixaPreco, this.horarioAbertura, this.horarioFechamento, this.dataAbertura, this.aberto);
		return s;
	}

	public String[] separarHoras(String s){
		String[] str = new String[2];
		String tmp = "";
		int indice = 1;	

		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			
			if(c != '-'){
				tmp += c;		
			}
			else{
				str[indice] = tmp;
				indice++;
				tmp = "";	
			}
			str[indice] = tmp;
		}
		return str;	
	}

}

class Hora{
	private int hora;
	private int minuto;
		
	public static Hora parseHora(String s){
		Hora h = new Hora();
		
		String tmpHora = "";
		String tmpMinuto = "";

		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			
			if(c != ':' && i < 2){
				tmpHora += c;
			}
			else if(c != ':'){
				tmpMinuto += c; 
			}
		}
		
		h.hora = Integer.parseInt(tmpHora);
		h.minuto = Integer.parseInt(tmpMinuto);
		
		return h;
	}
	
	public String formatar(){
		String s = String.format("%02d:%02d", this.hora, this.minuto);
		return s;
	}
}

class Data{
	private int ano;
	private int mes;
	private int dia;

	public static Data parseData(String s){
		Data d = new Data();

		String tmpAno = "";
		String tmpMes = "";
		String tmpDia = "";
		
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			
			if(c != '-' && i < 4){
				tmpAno += c;	
			}
			else if(c != '-' && i < 7){
				tmpMes += c;
			}
			else if(c != '-'){
				tmpDia += c;
			}
		}

			d.ano = Integer.parseInt(tmpAno);
			d.mes = Integer.parseInt(tmpMes);
			d.dia = Integer.parseInt(tmpDia);

		return d;
	}

	public String formatar(){
		String s = String.format("%02d/%02d/%d", this.dia, this.mes, this.ano);
		return s;	
	}
}

