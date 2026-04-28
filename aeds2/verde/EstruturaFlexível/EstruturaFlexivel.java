import java.util.*;

public class EstruturaFlexivel{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		Fila f = new Fila();
		
		while(sc.hasNext()){
			char c = sc.next().charAt(0);
			//'E': Adiciona elemento à fila - ler numero do elemento
			//'D': Remove elemento da fila, se estiver vazia printa "-1"
			//'M': Imprime elementos da fila
			//'P': Pesquisa se o número está na fila - ler numero do elemento - print "S" ou "N
			
			if(c == 'E'){
				int elementoInserir = sc.nextInt();
				f.inserir(elementoInserir);
			}
			else if(c == 'P'){
				int elementoPesquisar = sc.nextInt();
				System.out.println(f.pesquisar(elementoPesquisar) ? "SIM" : "NAO");
			}
			else if(c == 'D'){
			
			}
			else if(c == 'M'){
	
			}
		}
	}
}

class Celula{
	private int elemento;
	private Celula prox;
	
	public Celula(){
		this(0);
	}
	
	public Celula(int elementoInserir){
		this.elemento = elementoInserir;
		this.prox = null;
	}

	public int getElemento(){
		return this.elemento;
	}

	public void setProx(Celula prox){
		this.prox = prox;
	}
	
	public Celula getProx(){
		return this.prox;
	}
}

class Fila{
	private Celula primeiro;
	private Celula ultimo;
	private int qntCelulas = 0;

	public Fila(){//cria a fila e a celula cabeça
		Celula c = new Celula();
		this.primeiro = c;
		this.ultimo = c;
	}

	public void inserir(int elementoInserir){
		Celula c = new Celula(elementoInserir);
		this.ultimo.setProx(c);
		this.ultimo = c;
		this.qntCelulas++;
	}

	public boolean pesquisar(int elementoPesquisar){
		Celula aux = this.primeiro.getProx();

		for(int i = 0; i < this.qntCelulas; i++){//pode colocar indo até null
			if(aux.getElemento() == elementoPesquisar){
				return true;
			}
			aux = aux.getProx();
		}
		return false;
	}

}
