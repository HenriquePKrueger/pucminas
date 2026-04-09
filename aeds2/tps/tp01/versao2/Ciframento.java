import java.util.Scanner;

public class Ciframento{
	public static void cifrar(String str){
		String saida = "";
		for(int i = 0; i < str.length(); i++){
			saida += (char)(str.charAt(i) + 3);
		}
		System.out.println(saida);
	}

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()){	
		String str = sc.nextLine();		
		cifrar(str);
		}
	}
}
