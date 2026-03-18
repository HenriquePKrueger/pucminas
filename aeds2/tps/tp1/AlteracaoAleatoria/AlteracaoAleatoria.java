import java.util.Random;

public class AlteracaoAleatoria{

	static char letraSort;
	static char letraTrocar;

	public static void sortAleatorios(Random gerador){
		letraSort = ((char)('a' + (Math.abs(gerador.nextInt()) % 26)));
		letraTrocar = ((char)('a' + (Math.abs(gerador.nextInt()) % 26)));
	}

	public static String alterar(String str){
		String strSaida = "";

		for(int i = 0; i < str.length(); i++){
			if(str.charAt(i) == letraSort){
				strSaida += letraTrocar;
			}
			else{
				strSaida += str.charAt(i);
			}
		}
		return strSaida;
	}

	public static void main(String[] args){
		String str = MyIO.readLine();
		Random gerador = new Random();
		gerador.setSeed(4);
		while(!str.equals("FIM")){
			sortAleatorios(gerador);
			MyIO.println(alterar(str));
			str = MyIO.readLine();
		}
	}
}
