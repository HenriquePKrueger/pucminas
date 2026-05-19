import java.util.*;

public class Principal{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		Lista lista = new Lista();

		String palavra = "";
		int tam = 0;
		boolean first = false;		
	
		int n = sc.nextInt();
		sc.nextLine();
		for(int i = 0; i < n; i++){
			String s = sc.nextLine();

			palavra = "";
			tam = 0;
			first = false;

			for(int j = 0; j < s.length(); j++){
				char c = s.charAt(j);

				if(c != ' ' && c != '\0' && c!= '\n'){
					palavra += c;
					tam++;
				}
				else{
					if(first == false){
						lista.primeira(palavra, tam);
						first = true;
					}
					else{
						lista.inserir(palavra, tam);
					}
					palavra = "";
					tam = 0;
				}
			}

			lista.inserir(palavra, tam);
			lista.selecao();
			lista.exibir();
		}
	}
}

class Palavra{
	String palavra;
	int tamanho;
	Palavra prox;
}

class Lista{
	Palavra primeiro;
	Palavra ultimo;

	public void primeira(String inserirPalavra, int tam){
		Palavra p = new Palavra();

		p.palavra = inserirPalavra;
		p.tamanho = tam;
		this.primeiro = p;
		this.ultimo = p;
	}

	public void inserir(String inserirPalavra, int tam){
		Palavra p = new Palavra();

		p.palavra = inserirPalavra;
		p.tamanho = tam;
		this.ultimo.prox = p;
		this.ultimo = p;
	}

	public void selecao(){
		for(Palavra i = this.primeiro; i != this.ultimo; i = i.prox){
			Palavra maior = i;

			for(Palavra j = i.prox; j != null; j = j.prox){
				if(j.tamanho > maior.tamanho){
					maior = j;
				}
			}
			swap(maior, i);
		}
	}
	
	public void swap(Palavra maior, Palavra i){

		String tmpPalavra = i.palavra;
		int tmpTamanho = i.tamanho;
		
		i.palavra = maior.palavra;
		i.tamanho = maior.tamanho;
	
		maior.palavra = tmpPalavra;
		maior.tamanho = tmpTamanho;
	}

	public void exibir(){
		for(Palavra i = this.primeiro; i != null; i = i.prox){
			if(i.prox != null){
				System.out.print(i.palavra + " ");
			}
			else{
				System.out.println(i.palavra);
			}
		}
	}
	
}

