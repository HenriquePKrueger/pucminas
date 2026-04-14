import java.util.Scanner;

public class Is{
	public static String conversor(String str){ //Converte maiúsculas em minúsculas
		String strMinuscula = "";
		for(int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			if(c >= 'A' && c <= 'Z'){
				strMinuscula += (char)(c + 32);
			}
			else{
				strMinuscula += c;
			}
		}
		return strMinuscula;
	}

	public static boolean x1(String str){ //Só vogais
		for(int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			if(c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u'){
				return false;
			}
		}
		return true;
	}
	
	public static boolean x2(String str){ //Só consoantes
		for(int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			if(!(c >= 'a' && c <= 'z') || (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')){
				return false;
			}
		}
		return true;
	}

	public static boolean x3(String str){ //Número inteiro
		for(int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			if(!(c >= '0' && c <= '9')){
				return false;
			}
		}
		return true;
	}
	
	public static boolean x4(String str){ //Número real
		int cont = 0; //Verifica se existe apenas um ponto ou uma vírgula 
		for(int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			if(c == '.' || c == ','){
				cont++;
			}
			if((!(c >= '0' && c <= '9') && (c != '.' && c != ',')) || cont > 1){
				return false;
			}
		}
		return true;
	}


	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		String strMinuscula;

		while(!str.equals("FIM")){
			strMinuscula = conversor(str);
			System.out.print(x1(strMinuscula) ? "SIM " : "NAO "); //Operador ternário para deixar o código mais limpo 
			System.out.print(x2(strMinuscula) ? "SIM " : "NAO ");
			System.out.print(x3(strMinuscula) ? "SIM " : "NAO ");
			System.out.println(x4(strMinuscula) ? "SIM" : "NAO");
			str = sc.nextLine();
		}		
	}
}
