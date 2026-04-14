import java.util.Scanner;

public class Ciframento{
	public static void cifrar(String str){
		String saida = "";
		for(int i = 0; i < str.length(); i++){
			saida += (char)(str.charAt(i) + 3); //Pega o caracter na posição i, soma 3 e monta na nova string
		}
		System.out.println(saida);
	}

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		while(!str.equals("FIM")){
			cifrar(str);
			str = sc.nextLine();
		}	
	}
}
