import java.util.Scanner;


public class Principal{
	public static void main(String[] args){
		//testes
		Data d1 = Data.parseData("2026-11-12"); //Chama o método parseData antes de criar o objeto
		String dataFormat = d1.formatar();
		System.out.println(dataFormat);
		
		Hora h1 = Hora.parseHora("11:00");
		System.out.println(h1.formatar());
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
