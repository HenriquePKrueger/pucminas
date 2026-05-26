import java.util.*;


public class Main{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		BST bst = new BST();

		int elemento = sc.nextInt();
		while(elemento != 0){
			bst.inserir(elemento);

			elemento = sc.nextInt();
		}

		bst.exibir();
	}
}

class AVL{
	public No raiz;

	public AVL(){
		this.raiz = null;
	}


	public void inserir(int elemento){
		raiz = inserirRec(this.raiz, elemento);
	}

	public No inserirRec(No atual, int elemento){
		if(atual == null){
			return new No(elemento);
		}

		if(elemento < atual.elemento){
			atual.esq = inserirRec(atual.esq, elemento);
		}
		else if(elemento > atual.elemento){
			atual.dir = inserirRec(atual.dir, elemento);
		}
		
		atual.setNivel();
		return atual;
	}

	public void exibir(){
		exibirRec(this.raiz);
		System.out.println();
	}

	public void exibirRec(No atual){
		if(atual != null){
			exibirRec(atual.esq);
			System.out.println("teste");
			System.out.println(atual.elemento + " -> " + atual.nivel);
			exibirRec(atual.dir);
		}
	}
}

class No{
	int elemento;
	No esq;
	No dir;
	int nivel;

	public No(int elemento){
		this.elemento = elemento;
		this.esq = null;
		this.dir = null;
		this.nivel = 1;
	}

	public int getNivel(No i){
		return i == null ? 0 : i.nivel;
	}

	public void setNivel(){
		this.nivel = 1 + Math.max(getNivel(this.esq), getNivel(this.dir));
	}
}

/*
class BST{
	public No raiz;
	
	public BST(){
		this.raiz = null;
	}

	public void inserir(int elemento){
		raiz = inserirRec(this.raiz, elemento);
	}

	public No inserirRec(No atual, int elemento){
		if(atual == null){
			return new No(elemento);
		}
		if(elemento < atual.elemento){
			atual.esq = inserirRec(atual.esq, elemento);
		}
		else if(elemento > atual.elemento){
			atual.dir = inserirRec(atual.dir, elemento);
		}

		return atual;
	}

	public void exibir(){
		exibirRec(this.raiz);
		System.out.println();
	}

	public void exibirRec(No atual){
		if(atual != null){
			exibirRec(atual.esq);
			System.out.println(atual.elemento + " ");
			exibirRec(atual.dir);
		}
	}
}

class No{
	int elemento;
	No esq;
	No dir;

	public No(int elemento){
		this.elemento = elemento;
		this.esq = null;
		this.dir = null;
	}
}
*/
