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
			comando = sc.nextLine();//pesquisar diferença entre funcoes next

			if(comando.equals("I")){
				int elementoInserir = sc.nextInt();
				System.out.println(comando);
				System.out.println(elementoInserir);
				a.inserirElemento(elementoInserir);
			}
			else if(comando.equals("EM")){
				a.exibirEmOrdem();
			}
		}
	}
}

class No{
	private int elemento;
	private No direita;
	private No esquerda;

	public No(int elemento){
		this.elemento = elemento;
		this.direita = null;
		this.esquerda = null;
	}

	public int getElemento(){
		return this.elemento;
	}

	public No getDireita(){
		return this.direita;
	}

	public void setDireita(No direita){
		this.direita = direita;
	}

	public No getEsquerda(){
		return this.esquerda;
	}

	public void setEsquerda(No esquerda){
		this.esquerda = esquerda;
	}


}

class Arvore{
	private No raiz;

	public Arvore(){//Cria a estrutura inicial e inicializa a raiz como "null"
		this.raiz = null;
	}
	
	public void inserirElemento(int elementoInserir){
		this.raiz = inserirRecursivo(elementoInserir, this.raiz);
	}
	
	private No inserirRecursivo(int elementoInserir, No i){//Private por que é chamado somente pelo método "inserirElemento"
		if(i == null){
			i = new No(elementoInserir);
		}
		else if(elementoInserir > i.getElemento()){
			i.setDireita(inserirRecursivo(elementoInserir, i.getDireita()));
		}
		else{
			i.setEsquerda(inserirRecursivo(elementoInserir, i.getEsquerda()));
		}

		return i;
	}

	public void exibirEmOrdem(){
		emOrdemRecursivo(this.raiz);
	}

	private void emOrdemRecursivo(No i){
		if(i != null){
			emOrdemRecursivo(i.getEsquerda());
			System.out.println(i.getElemento());
			emOrdemRecursivo(i.getDireita());
		}
		else{
			System.out.println("V");
		}
	}
}

