import java.util.Scanner;

public class Soma{
	public static int somarNumeros(String entrada, int resultado, int i){
		if(i == entrada.length()){ //Quando chega no final retorna o resultado
			return resultado;
		}
		else{ //Soma os caracteres(números) na variável resultado
			resultado += (entrada.charAt(i) - '0'); 
			return somarNumeros(entrada, resultado, i + 1);
		}
	}

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in); 
		int resultado = 0;
		int i = 0;

		while(sc.hasNextLine()){
			String entrada = sc.nextLine();
			System.out.println(somarNumeros(entrada, resultado, i));
		}
	}
}
