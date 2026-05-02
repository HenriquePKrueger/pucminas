import java.util.*;

public class Q01{
	public static void main(String args[]){
		Data d = Data.parseData("2026-12-27");
		System.out.println(d.formatar()); 
	}
}

class Data{
	private int ano;
	private int mes;
	private int dia;

	public static Data parseData(String s){
		Data d = new Data();

		String tempAno = "";
		String tempMes = "";
		String tempDia = "";
		
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			
			if(c != '-' && i < 4){
				tempAno += c;	
			}
			else if(c != '-' && i < 7){
				tempMes += c;
			}
			else if(c != '-'){
				tempDia += c;
			}
		}

			d.ano = Integer.parseInt(tempAno);
			d.mes = Integer.parseInt(tempMes);
			d.dia = Integer.parseInt(tempDia);

		return d;
	}

	public String formatar(){
		String s = String.format("%02d/%02d/%d", this.dia, this.mes, this.ano);
		return s;	
	}
}

