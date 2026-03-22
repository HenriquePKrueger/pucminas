import java.util.Scanner;

public class Inversao{
	public static String inverter(String entrada, String saida, int cont){
		if(cont == entrada.length()){
			return saida;
		}
		else{
			saida += entrada.charAt(entrada.length() - (cont + 1));
			return inverter(entrada, saida, cont + 1);
		}
	}

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int cont = 0;
		String entrada = sc.nextLine();

		while(!entrada.equals("FIM")){
		String saida = "";
		System.out.println(inverter(entrada, saida, cont));
		entrada = sc.nextLine();
		}
	}
}
