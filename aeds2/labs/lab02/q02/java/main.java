import java.util.Scanner;

public class main {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);

		String s1;
		
		System.out.println("Digite a string:");
		s1 = sc.nextLine();

		String s2 = new String(s1);

		for (int i = (s1.length() - 1); i >= 0; i--) {
			
			s2 += s1.charAt(i);

		}

		System.out.println("String espelhada:\n"+s2);

		sc.close();

	} 
}
