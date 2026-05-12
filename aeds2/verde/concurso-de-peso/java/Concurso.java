import java.util.*;

/*
 * Seu programa deverá ler os dados dos atletas com nome (sem espaços) e o peso máximo levantado (inteiro positivo), e apresentar a listagem dos atletas a partir dos maiores pesos levantados.
 * Em caso de empate no peso, ordenar em ordem alfabética pelo nome. Seu programa deve ler os atletas até o fim do arquivo. Imprimir os atletas um por linha no formato: Nome Peso.
 * 
 * Regras e Restrições:
 *
 * Criar uma estrutura de dados para representar um atleta.
 * O número de atletas será no máximo 100.
 * O nome de cada atleta não terá espaços e terá no máximo 50 caracteres.
 * O peso máximo será um número inteiro entre 1 e 500.
*/


public class Concurso{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		ListaSequencial l = new ListaSequencial();
		Selecao s = new Selecao();

		while(sc.hasNextLine()){
			String nome = sc.next();
			int peso = sc.nextInt();
			
			l.inserir(nome, peso);
			s.ordenar();
		}
	}
}

class ListaSequencial{
	Atleta[] atletas = new Atleta[100];
	int pos = 0;

	public void inserir(String nome, int peso){
		atletas[pos] = new Atleta(nome, peso);
		pos++;
	}
}

class Selecao{
	public int tamanho = 100;

	public void ordenar(){
		for(int i = 0; i < this.tamanho; i++){
			int menor = i;
	
			for(int j = i + 1; j < this.tamanho; j++){
				if(ListaSequencial.atletas[j] < ListaSequecial.atletas[menor]){
					menor = ListaSequencial.atletas[j];
				}
			}
		}
	}

	public void swap(Atleta j, int menor){
		Atleta temp = ListaSequencial.atletas[i];
		ListaSequencial.atletas[i] = ListaSequencial.atletas[menor];
		ListaSequencial.atletas[menor] = temp;
	}


}

class Atleta{
	String nome;
	int peso;

	public Atleta(String nome, int peso){
		this.nome = nome;
		this.peso = peso;
	}

}
