import java.util.*;

public class Principal{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		while(n != 0){	
			Pilha p = new Pilha();
			for(int i = 1; i <= n; i++){
				if(i == 1){
					p.criarCelulaTopo(i);
				}
				else{
					p.criarCelula(i);
				}
			}
			p.jogoDeCartas(n);
			p.exibir();		
			n = sc.nextInt();
		}
	}
}

class Celula{
	public int elemento;
	public Celula prox;
}

class Pilha{
	public Celula topo;
	public Celula base;

	public Pilha(){
		this.topo = null;
		this.base = null;
	}

	public void criarCelulaTopo(int n){
		Celula c = new Celula();
			c.elemento = n;
			this.topo = c;
			this.base = c;
			c.prox = null;
	}

	public void criarCelula(int n){
		Celula c = new Celula();
		c.elemento = n;
		this.base.prox = c;
		this.base = c;
	}
	
	public void jogoDeCartas(int n){
		int[] descartadas = new int[n - 1];

		for(int i = 0; i < n - 1; i++){
			descartadas[i] = this.topo.elemento;
		
			Celula tmp = this.topo;
			this.base.prox = tmp.prox;
			this.base = this.base.prox;
			this.topo = tmp.prox.prox;
			
			if(i == 0){
				System.out.print("Discarded cards: " + descartadas[i] + ", ");
			}
			else if(i == (n - 1) - 1){
				System.out.println(descartadas[i]);
			}
			else{
				System.out.print(descartadas[i] + ", ");
			}
		}
	}

	public void exibir(){
		System.out.println("Remaining card: " + this.topo.elemento);
	}
}
