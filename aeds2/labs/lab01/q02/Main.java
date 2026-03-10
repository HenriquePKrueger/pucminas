import java.util.*;

public class Main {

	public static int contMaiusculas(String str, int cont){
		
		if(cont >= str.length()){

			return 0;

		}

		if(str.charAt(cont) >= 'A' && str.charAt(cont) <= 'Z'){
			
			return 1 + contMaiusculas(str, cont + 1);
				
		}
		else{
			
			return contMaiusculas(str, cont + 1);

		}

	}

	public static void main(String[] args){
	
		Scanner sc = new Scanner(System.in);
	
		String s1;

		s1 = sc.nextLine();

		System.out.println(contMaiusculas(s1, 0));

		sc.close();

	} 
}
