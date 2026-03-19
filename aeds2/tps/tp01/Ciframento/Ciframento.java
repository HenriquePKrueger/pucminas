public class Ciframento{

	public static String criptografar(String str){

		String strSaida = "";

		for(int i = 0; i < str.length(); i++){
			strSaida += (char)(str.charAt(i) + 3);
		}
		return strSaida;
	}

	public static void main(String[] args){
		
		//Scanner sc = new Scanner(System.in);
		//String str = sc.nextLine();

		String str = MyIO.readLine();

		while(!str.equals("FIM")){
		String saida = criptografar(str);
		//System.out.println(saida);
		//str = sc.nextLine();
		MyIO.println(saida);
		str = MyIO.readLine();
		}

		//sc.close();

	}
}
