import java.util.*;

/*
*Implementar uma Lista Encadeada Simples, utilizando uma célula cabeça. A estrutura deve conter métodos para os seguintes comandos:
*"I X" - Inserir um elemento no início.
*"F X" - Inserir um elemento no final.
*"R" - Remover um elemento do início.
*"E" - Exibir todos os elementos da lista.
*/

public class FilaFlexivel{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		Fila f = new Fila();

		while(sc.hasNext()){
			char comando = sc.next().charAt(0);

			if(comando == 'I'){
				int elementoInserirInicio = sc.nextInt();
				f.inserirInicio(elementoInserirInicio);
			}
			else if(comando == 'F'){
				int elementoInserirFinal = sc.nextInt();
				f.inserirFinal(elementoInserirFinal);
			}
			else if(comando == 'E'){
				System.out.println(f.Exibir());
			}
		}
	}
}

class Celula{
	private int elemento;
	private Celula prox;
	
	public Celula(){
		this.elemento = 0;
		this.prox = null;
	}
	
	public Celula(int elementoInserir){
		this.elemento = elementoInserir;
		this.prox = null;
	}
	
	public Celula getProx(){
		return this.prox;
	}
	
	public void setProx(Celula prox){
		this.prox = prox;
	}
	
	public int getElemento(){
		return this.elemento;
	}
}

class Fila{
	private Celula primeiro;
	private Celula ultimo;

	public Fila(){//O próprio construtor já instancia a célula cabeça
		Celula c = new Celula();
		this.primeiro = c;
		this.ultimo = c;
	}
	
	public void inserirInicio(int elementoInserirInicio){
		Celula c = new Celula(elementoInserirInicio);
		c.setProx(this.primeiro.getProx());
		this.primeiro.setProx(c);

		if(c.getProx() != null){
			this.ultimo = c.getProx();
		}

		System.out.println(primeiro.getProx().getElemento());
	}

	public void inserirFinal(int elementoInserirFinal){
		Celula c = new Celula(elementoInserirFinal);
		this.ultimo.setProx(c);
		this.ultimo = c;
	}
	
	public String Exibir(){
		String result = "";

		for(Celula i = this.primeiro.getProx(); i != null; i = i.getProx()){
			if(i.getProx() != null){
				result += String.format("%d ", i.getElemento());
			}
			else{
				result += String.format("%d", i.getElemento());
			}
			
		}
		return result;
	}

		
}
