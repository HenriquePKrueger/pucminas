import java.util.*;

public class Main {

	public static int contMaiusculas(String str, int i, int maiusculas){

		if ( i >= str.length() - 1){
			
			if (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') {

				maiusculas++;

			}

			return maiusculas;

		}
		else if (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z'){

			maiusculas++;
				
		}

		i++;

		return contMaiusculas(str, i, maiusculas);

	}

	public static void main(String[] args){
	
		Scanner sc = new Scanner(System.in);
	
		String s1;

		int indice = 0, result, maiusculas = 0;

		s1 = sc.nextLine();

		result = contMaiusculas(s1, indice, maiusculas);

		System.out.println(result);

		sc.close();

	} 
}
