import java.util.*;

public class Concurso{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		Lista l = new Lista();	
	
		while(sc.hasNext()){
			String nome = sc.next();
			int peso = sc.nextInt();	
	
			l.inserir(nome, peso);
		}
		
		l.selecao();
		l.exibir();
		sc.close();
	}
}

class Lista{
	Atleta[] atletas = new Atleta[100];
	int index = 0;

	public void inserir(String nome, int peso){
		Atleta novo = new Atleta();
		novo.nome = nome;
		novo.peso = peso;
		atletas[index] = novo;
		index++;
	}

	public void selecao(){
		for(int i = 0; i < index; i++){
			int maior = i;

			for(int j = i + 1; j < index; j++){
				if(atletas[j].peso > atletas[maior].peso){
					maior = j;
				}
				else if(atletas[j].peso == atletas[maior].peso){//Condição de desempate
					if(atletas[j].nome.compareTo(atletas[maior].nome) < 0){
						maior = j;
					}
				}
				
			}

			Atleta temp = atletas[i];
			atletas[i] = atletas[maior];
			atletas[maior] = temp;			
		}
	}

	public void exibir(){
		for(int i = 0; i < index; i++){
			System.out.print(atletas[i].nome + " ");
			System.out.println(atletas[i].peso);
		}
	}
}

class Atleta{
	String nome = "";
	int peso = 0;
}

