import java.util.Scanner;

public class Validacao{
	public static boolean verificar(String senha, int[] condicoes){
		if(senha.length() >= 8){
			for(int i = 0; i < senha.length(); i++){
				char c = senha.charAt(i);
				if(c >= 'A' && c <= 'Z'){
					condicoes[0] = 1;
				}
				else if(c >= 'a' && c <= 'z'){
					condicoes[1] = 1;
				}
				else if(c >= '0' && c <= '9'){
					condicoes[2] = 1;
				}
				else{
					condicoes[3] = 1;
				}
			}
			for(int j = 0; j < 4; j++){
				if(condicoes[j] == 0){
					return false;
				}
			}
			return true;
		}
		else{
			return false;
		}
	}

	public static void main(String[] args){
		 int i = 0;
		 //String senha = MyIO.readLine();
		 Scanner sc = new Scanner(System.in);
		 String senha = sc.nextLine();
				
		 while(!senha.equals("FIM")){
			int[] condicoes = {0, 0, 0, 0};
		 	MyIO.println(verificar(senha, condicoes) == true ? "SIM" : "NAO");
		 	senha = sc.nextLine();
		 }
	}
}
