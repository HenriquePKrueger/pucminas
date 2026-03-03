import java.util.*;

public class  somaDoisInteiros{

	public static void somar(int n1, int n2){
	
		System.out.println(n1 + n2);
	
	}

	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);

		int num1, num2;

		num1 = sc.nextInt();
		num2 = sc.nextInt();

		somar(num1, num2);

	}

}
