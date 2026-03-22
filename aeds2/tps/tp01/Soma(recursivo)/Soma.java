import java.util.Scanner;

public class Soma{
	public static int somarNumeros(String entrada, int resultado, int i){
		if(i == entrada.length()){
			return resultado;
		}
		else{
			resultado += (entrada.charAt(i) - '0'); 
			return somarNumeros(entrada, resultado, i + 1);
		}
	}

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in); 
		int resultado = 0;
		int i = 0;

		String entrada = sc.nextLine();
		while(!entrada.equals("FIM")){
			System.out.println(somarNumeros(entrada, resultado, i));
			entrada = sc.nextLine();
		}

		sc.close();
	}
}
