import java.util.Random;

public class AlteracaoAleatoria{

	public static String alterar(String str){
		Random gerador = new Random();
		gerador.setSeed(4);
		char letraSort = ((char)('a' + (Math.abs(gerador.nextInt()) % 26)));
		char letraTrocar = ((char)('a' + (Math.abs(gerador.nextInt()) % 26)));
		
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
		 
		 while(!str.equals("FIM")){
			System.out.println(alterar(str));
			str = MyIO.readLine();
		 }
	}
}
