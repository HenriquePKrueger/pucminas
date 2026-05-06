import java.util.*;

/*
*Faça a implementação de uma árvore binária de pesquisa (ABP) em Java para armazenar números inteiros. Crie um programa que instancia uma nova árvore e processa as operações solicitadas na entrada de dados, obedecendo o seguinte formato:
*'I X': insere o número X;
*'P Y': pesquisa se o número Y está na árvore, imprimindo S caso verdadeiro e N caso contrário. O algoritmo de pesquisa deve imprimir cada nó visitado;
*'PRE': imprime os elementos da árvore por caminhamento pré-ordem ou V se a árvore estiver vazia;
*'POS': imprime os elementos da árvore por caminhamento pós-ordem ou V se a árvore estiver vazia;
*'EM': imprime os elementos da árvore por caminhamento em-ordem ou V se a árvore estiver vazia;
*/

public class ArvoreBinaria{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		Arvore a = new Arvore();
		String comando;

		while(sc.hasNext()){
			comando = sc.next();

			if(comando.equals("I")){
				int elementoInserir = sc.nextInt();
				a.inserir(elementoInserir);
			}
			else if(comando.equals("P")){
				int elementoPesquisar = sc.nextInt();
				System.out.print(a.pesquisar(elementoPesquisar) ? "S" : "N");
				System.out.println();
			}
			else if(comando.equals("PRE")){
				a.caminharPre();
			}
			else if(comando.equals("POS")){
				a.caminharPos();
			}
			else if(comando.equals("EM")){
				a.caminharCentral();
			}
		}
	}
}

class No{
	public int elemento;
	public No dir;
	public No esq;

	public No(int elemento){
		this.elemento = elemento;
		this.dir = null;
		this.esq = null;
	}
}

class Arvore{
	public No raiz;

	public Arvore(){
		this.raiz = null;
	}

	// =========================
	// Inserir elemento
	// =========================	
	public void inserir(int elementoInserir){
		this.raiz = inserir(elementoInserir, this.raiz);
	}
	
	private No inserir(int elementoInserir, No i){//Private por que é chamado somente pelo método "inserir"
		if(i == null){
			i = new No(elementoInserir);
		}
		else if(elementoInserir < i.elemento){
			i.esq = inserir(elementoInserir, i.esq);
		}
		else{
			i.dir = inserir(elementoInserir, i.dir);
		}
		return i;
	}

	// =========================
	// Pesquisar elemento
	// =========================
	public boolean pesquisar(int elementoPesquisar){
		if(this.raiz == null){
			return false;
		}
		else{
			return pesquisar(elementoPesquisar, this.raiz);
		}
	}

	private boolean pesquisar(int elementoPesquisar, No i){
		boolean resp = false;
		
		if(i == null){
			resp = false;
		}
		else if(i.elemento == elementoPesquisar){
			System.out.print(i.elemento + " ");
			resp = true;
		}
		else if(i.elemento > elementoPesquisar){
			System.out.print(i.elemento + " ");
			resp = pesquisar(elementoPesquisar, i.esq);	
		}
		else{
			System.out.print(i.elemento + " ");
			resp = pesquisar(elementoPesquisar, i.dir);
		}
		return resp;
	}

	// =========================
	// Caminhar Pré-ordem
	// =========================
	public void caminharPre(){
		if(this.raiz == null){
			System.out.println("V");
		}
		else{
			caminharPre(this.raiz);
			System.out.println();
		}
	}
	
	private void caminharPre(No i){
		if(i != null){
			System.out.println(i.elemento + " ");
			caminharPre(i.esq);
			caminharPre(i.dir);
		}
	}
	
	// =========================
	// Caminhar Pós-ordem
	// =========================
	public void caminharPos(){
		if(this.raiz == null){
			System.out.println("V");
		}
		else{
			caminharPos(this.raiz);
			System.out.println();
		}
	}

	private void caminharPos(No i){
		if(i != null){
			caminharPos(i.esq);
			caminharPos(i.dir);
			System.out.println(i.elemento + " ");
		}
	}
	
	// =========================
	// Caminhar Em-ordem
	// =========================	
	public void caminharCentral(){
		if(this.raiz == null){
			System.out.println("V");
		}
		else{
			caminharCentral(this.raiz);
			System.out.println();
		}
	}

	private void caminharCentral(No i){
		if(i != null){
			caminharCentral(i.esq);
			System.out.print(i.elemento + " ");
			caminharCentral(i.dir);
		}
	}
}

