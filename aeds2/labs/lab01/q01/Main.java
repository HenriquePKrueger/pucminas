import java.util.Scanner;

public class Main {

	public static int contMaiusculas(String s2){
	
		int cont = 0;

		for (int i = 0; i < s2.length(); i++) {
		
			if (s2.charAt(i) >= 'A' && s2.charAt(i) <= 'Z') {

				cont++;

			}

		}

		return cont;

	}

	public static void main(String[] args){
	
		Scanner sc = new Scanner(System.in);
		
		String s1 = sc.nextLine();
 
		while (!s1.equals("FIM")) {

			System.out.println(contMaiusculas(s1));

			s1 = sc.nextLine();

		}

	}

}
