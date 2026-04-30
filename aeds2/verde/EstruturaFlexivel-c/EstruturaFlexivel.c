#include <stdio.h>
#include <stdlib.h>

/*
*Comandos da atividade:
*"E X": adiciona o número 'X' na fila
*"D": Remove uma célula da fila e retorna o número removido
*"M": Imprime os elementos das células da fila
*"P Y": Pesquisa se o número 'Y' está na fila. Se estiver exibe "S" se não "N"
*/

struct Celula{
	int elemento;
	struct Celula* prox; //Ponteiro para próxima célula
};

//Funções da struct "Celula"
struct Celula* novaCelula(int elementoInserir){//Cria uma nova célula
	struct Celula* c = (struct Celula*) malloc(sizeof(struct Celula));
	
	c->elemento = elementoInserir;
	c->prox = NULL;

	return c;
}

struct Fila{
	struct Celula* primeiro;
	struct Celula* ultimo;
};

//Funções para struct "Fila"
void IniciarFila(struct Fila* f){//'f' recebe o endereço de memória da fila criada anteriormente
	struct Celula* cabeca = novaCelula(0);
	f->primeiro = cabeca;
	f->ultimo = cabeca;
}

void Inserir(struct Fila* f, int elementoInserir){
	struct Celula* c = novaCelula(elementoInserir);
	f->ultimo->prox = c;
	f->ultimo = c;
}

void Exibir(struct Fila* f){//Recebe o endereço de memória de uma variável "Fila" - asterisco indica que é um endereço de memória 
	for(struct Celula* i = f->primeiro->prox; i != NULL; i = i->prox){
		printf("%d\n", i->elemento);
	}	
}

int main(){
	char comando;
	struct Fila* f = (struct Fila*) malloc(sizeof(struct Fila));//Cria uma fila
	IniciarFila(f);//chama a função "IniciarFila" passando o endereço de memória da fila criada anteriormente

	while(scanf(" %c", &comando) != EOF){
		if(comando == 'E'){
			int elementoInserir;
			scanf("%d", &elementoInserir);
			Inserir(f, elementoInserir);
		}
		else if(comando == 'M'){
			Exibir(f);
		}
	}
	
	return 0;
}
