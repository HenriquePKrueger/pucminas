import java.util.Random;

public class AlteracaoAleatoria{

	public static String alterar(String str, Random gerador){
		String strSaida = "";
		char letraSort;
		char letraTrocar;
		letraSort = ((char)('a' + (Math.abs(gerador.nextInt()) % 26)));
		letraTrocar = ((char)('a' + (Math.abs(gerador.nextInt()) % 26)));

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
			MyIO.println(alterar(str, gerador));
			str = MyIO.readLine();
		}
	}
}
