import java.util.*;

public class Main {

	public static int contMaiusculas(String str, int cont){
		
		if(cont >= str.length()){

			return 0;

		}

		if(str.charAt(cont) >= 'A' && str.charAt(cont) <= 'Z'){
			
			return 1 + contMaiusculas(str, cont + 1);
				
		}
			
		return contMaiusculas(str, cont + 1);

	}

	public static void main(String[] args){
	
		Scanner sc = new Scanner(System.in);
	
		String str;

		str = sc.nextLine();

		while(!str.equals("FIM")){

		System.out.println(contMaiusculas(str, 0));

		str = sc.nextLine();

		}
		sc.close();

	} 
}
