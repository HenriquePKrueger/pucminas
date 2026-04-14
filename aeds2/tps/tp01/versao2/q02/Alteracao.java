import java.util.Scanner;
import java.util.Random;

public class Alteracao{
	public static void alterar(String str, Random gerador){
		char letra1 = (char)('a' + Math.abs(gerador.nextInt()) % 26);
		char letra2 = (char)('a' + Math.abs(gerador.nextInt()) % 26);
		String saida = "";
		for(int i = 0; i < str.length(); i++){
			if(str.charAt(i) == letra1){ //Verifica se o char na posição i é a primeira letra sorteada, se for troca com a segunda letra
				saida += letra2;
			}
			else{
				saida += str.charAt(i);
			}
		}
		System.out.println(saida);
	}
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in, "ISO-8859-1");
		Random gerador = new Random();
		gerador.setSeed(4);

		String str = sc.nextLine();
		while(!str.equals("FIM")){	
			alterar(str, gerador);
			str = sc.nextLine();
		}
	}
}
