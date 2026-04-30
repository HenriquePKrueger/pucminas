import java.util.*;

/*
* Comandos da atividade:
*'E X': Adiciona a célula de elemento 'X' à fila
*'D': Remove elemento da fila(como é fila, remove sempre o primeiro). Se estiver vazia exibe "-1"
*'M': Imprime os elementos de cada célula da fila
*'P X': Pesquisa se existe uma célula de elemento 'X' na fila. Exibe "S" se encontrar e "N" se não encontrar
*/

public class EstruturaFlexivel{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		Fila f = new Fila();//Cria uma nova fila
		
		while(sc.hasNext()){
			char c = sc.next().charAt(0);

			if(c == 'E'){
				int elementoInserir = sc.nextInt();
				f.inserir(elementoInserir);
			}
			else if(c == 'P'){
				int elementoPesquisar = sc.nextInt();
				System.out.println(f.pesquisar(elementoPesquisar) ? "S" : "N");
			}
			else if(c == 'D'){
				f.remover();
			}
			else if(c == 'M'){
				f.exibir();	
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
	
	public void setElemento(int x){
		this.elemento = x;
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

	public Fila(){//Cria a primeira célula - "cabeça" - que dará início à fila
		Celula c = new Celula();
		//Definimos a célula cabeça como primeiro e último elemento
		this.primeiro = c;
		this.ultimo = c;
	}

	public void inserir(int elementoInserir){
		Celula c = new Celula(elementoInserir);//Cria uma nova célula "c"
		this.ultimo.setProx(c);//A última célula da fila recebe o endereço de memória da nova célula criada
		this.ultimo = c;//A última célula que criamos vira a última da fila
	}
	
	public void remover(){
		if(this.primeiro == this.ultimo){//Exibe "-1" caso não existam elementos
			System.out.println("-1");
		}
		else{	
			Celula tmp = this.primeiro;
			this.primeiro = primeiro.getProx();
			System.out.println(primeiro.getElemento());
			tmp.setProx(null);
			tmp = null;
		}
	}

	public boolean pesquisar(int elementoPesquisar){
		Celula aux = this.primeiro.getProx();//Cria uma célula auxiliar

		for(Celula i = primeiro.getProx(); i != null; i = i.getProx()){//Passa por todas as células criadas até na celula em que o "prox" seja "null"
			if(aux.getElemento() == elementoPesquisar){//Se encontrar uma célula que tiver o 'elemento' igual ao elemento que estamos buscando retorna true
				return true;
			}
			aux = aux.getProx();//Agora a célula auxiliar é a próxima da fila
		}
		return false;
	}
	
	public void exibir(){
		if(this.primeiro == this.ultimo){
			System.out.println("V");
		}
		else{
			Celula aux = primeiro.getProx();
	
			for(Celula i = primeiro.getProx(); i != null; i = i.getProx()){
				if(i.getProx() != null){
					System.out.print(aux.getElemento() + " ");
				}
				else{
					System.out.println(aux.getElemento());
				}
				aux = aux.getProx();
			}
		}
	}
}
