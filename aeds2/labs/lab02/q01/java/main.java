import java.util.Scanner;

public class main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		String s1, s2, s3 = "";
		int i = 0;
		
		System.out.println("Digite a primeira string:");
		s1 = sc.nextLine();

		System.out.println("Digite a segunda string:");
		s2 = sc.nextLine();

		while (i < s1.length() && i < s2.length()) {

			s3 += s1.charAt(i);
			s3 += s2.charAt(i);
			i++;

		}

		while (i < s1.length()) {
			
			s3 += s1.charAt(i);
			i++;

		}

		while (i < s2.length()) {

			s3 += s2.charAt(i);
			i++;

		}

		System.out.println("Strings combinadas:\n" + s3);

	}
}
