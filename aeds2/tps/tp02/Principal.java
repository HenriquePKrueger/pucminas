import java.util.Scanner;

public class Principal{
	public static void main(String[] args){
		Data d1 = Data.parseData("2026-01-02"); //Chama o método parseData antes de criar o objeto
		String dataFormat = d1.formatar();
		System.out.println(dataFormat);
	}
}

class 

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
	public static Data parseData(String str){
		String ano = "";
		String mes = "";
		String dia = "";

		for(int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
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
		return String.format("0%d/0%d/%d", this.dia, this.mes, this.ano);
	}
}
