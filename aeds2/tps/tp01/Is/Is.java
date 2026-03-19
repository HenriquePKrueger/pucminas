import java.util.Scanner;

public class Is{
	public static boolean x1(String str){
		boolean vogais = true;
		int i = 0;
		while(vogais && (i < str.length())){
			if(str.charAt(i) != 'a' && str.charAt(i) != 'e' && str.charAt(i) != 'i' && str.charAt(i) != 'o' && str.charAt(i) != 'u'){
				vogais = false;
			}
			i++;
		}
		return vogais;
	}

	public static boolean x2(String str){
		boolean consoantes = true;
		int i = 0;
		while(consoantes && (i < str.length())){
			if(!(str.charAt(i) >= 'a' && str.charAt(i) <= 'z') || (str.charAt(i) == 'a' || str.charAt(i) == 'e' || str.charAt(i) == 'i' || str.charAt(i) == 'o' || str.charAt(i) == 'u')){
				consoantes = false;
			}
			i++;	
		}
		return consoantes;
	}

	public static boolean x3(String str){
		boolean inteiro = true;
		int i = 0;
		while(inteiro && (i < str.length())){
			if(!(str.charAt(i) >= '0' && str.charAt(i) <= '9')){
				inteiro = false;
			}
			i++;
		}
		return inteiro;
	}

	public static boolean x4(String str){
		boolean real = true;
		int i = 0;
		while(real && (i < str.length())){
			if(!(str.charAt(i) >= '0' && str.charAt(i) <= '9') && (str.charAt(i) != '.')){
				real = false;
			}
			i++;
		}
		return real;
	}

	public static void main(String[] args){
		String str = MyIO.readLine();
	
		while(!str.equals("FIM")){
			MyIO.print((x1(str) == true) ? "SIM " : "NAO ");
			MyIO.print((x2(str) == true) ? "SIM " : "NAO ");
			MyIO.print((x3(str) == true) ? "SIM " : "NAO ");
			MyIO.println((x4(str) == true) ? "SIM" : "NAO");
			str = MyIO.readLine();
		}

/*		while(!str.equals("FIM")){
			if((x1(str) == true)){
				MyIO.print("SIM ");
			}
			else{
				MyIO.print("NAO ");
			}

			if((x2(str) == true)){
				MyIO.print("SIM ");
			}
			else{
				MyIO.print("NAO ");
			}

			if((x3(str) == true)){
				MyIO.print("SIM ");
			}
			else{
				MyIO.print("NAO ");
			}

			if((x4(str) == true)){
				MyIO.print("SIM ");
			}
			else{
				MyIO.print("NAO ");
			}
	
*/			str = MyIO.readLine();
	}
}

