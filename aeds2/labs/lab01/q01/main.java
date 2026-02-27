import java.util.Scanner;

public class main {
	public static void main(String[] args){
	
		Scanner sc = new Scanner(System.in);
		
		String s1 = sc.nextLine();
		int cont = 0;

		for (int i = 0; i < s1.length(); i++) {

			if (s1.charAt(i) >= 65 && s1.charAt(i) <= 90) {

				cont++;

			}

		}

		System.out.println(cont);

	}

}
