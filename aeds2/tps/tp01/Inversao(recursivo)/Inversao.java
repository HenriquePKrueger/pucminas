import java.util.Scanner;

public class Inversao{
	public static String inverter(String entrada, String saida, int cont){
		if(cont == entrada.length()){ //Se chegou no final da string retorna saida
			return saida;
		}
		else{
			saida += entrada.charAt(entrada.length() - (cont + 1)); //Cria a string saida adicionando os elementos da "entrada" de trás para frente
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
