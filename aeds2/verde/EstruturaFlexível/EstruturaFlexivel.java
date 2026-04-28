import java.util.*;

public class EstruturaFlexivel{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);

		char c = sc.next().charAt(0);
		//'E': Adiciona elemento à fila - ler numero do elemento
		//'D': Remove elemento da fila, se estiver vazia printa "-1"
		//'M': Imprime elementos da fila
		//'P': Pesquisa se o número está na fila - ler numero do elemento - print "S" ou "N
		
		Fila f = new Fila();
			
		if(c == 'E'){
			int codigo = sc.nextInt();
			f.inserir(codigo);
		}
		else if(c == 'P'){
			int codigo = sc.nextInt();
			f.pesquisar(codigo);
			System.out.println(f.pesquisar(codigo) ? "SIM" : "NAO");
		}
		else if(c == 'D'){
			
		}
		else if(c == 'M'){
	
		}
	}
}

class Celula{
	private int elemento;
	private Celula prox;
	
	public Celula(){
		this(0);
	}
	
	public Celula(int x){
		this.elemento = 0;
		this.prox = null;
	}

	public int getElemento(){
		return this.elemento;
	}

	public Celula getProxCelula(){
		return this.prox;
	}
}

class Fila{
	private Celula primeiro;
	private Celula ultimo;
	private int totalCelulas = 0;

	public Fila(){//cria a fila e a celula cabeça
		Celula c = new Celula();
		this.primeiro = c;
		this.ultimo = c;
	}

	public void inserir(int x){
		Celula c = new Celula(x);
		this.ultimo.prox = c;
		this.ultimo = c;
		this.totalCelulas++;
	}

	public int pesquisar(int x){
		Celula aux = this.primeiro.getProxCelula;
		for(int i = 0; i < this.totalcelulas; i++){//pode colocar indo até null
			if(aux.elemento == x){
				return 1;
			}
		}
	}

}
