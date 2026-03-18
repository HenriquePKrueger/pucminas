import java.util.Scanner;

public class Main{

	public static String criptografar(String str){

		String strSaida = "";

		for(int i = 0; i < str.length(); i++){
			char letra;
			if(str.charAt(i) >= 'A' && str.charAt(i) <= 'Z'){
				letra = (char)(str.charAt(i) + 3);
				strSaida += letra;
			}
			else if(str.charAt(i) >= 'a' && str.charAt(i) <= 'z'){
				letra = (char)(str.charAt(i) + 3);
				strSaida += letra;
			}
		}
		return strSaida;
	}

	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);

		while(sc.hasNextLine()){
		String str = sc.nextLine();
		String saida = criptografar(str);
		System.out.println(saida);
		}

		sc.close();

	}
}
