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
	struct Celula* prox;//'prox' armazena um ponteiro para próxima célula
};

//Funções da struct "Celula"
struct Celula* novaCelula(int elementoInserir){//Cria uma nova célula
	struct Celula* c = (struct Celula*) malloc(sizeof(struct Celula));//Reserva um espaço na memória e passa o endereço desse espaço para a variável 'c'
	
	c->elemento = elementoInserir;
	c->prox = NULL;

	return c;//Retorna apenas o endereço de memória contido em 'c', depois a variável deixa de existir
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
	struct Celula* c = novaCelula(elementoInserir);//'c' armazena o endereço de memória retornado pela função "novaCelula"
	//'ultimo' e 'prox' são variáveis que recebem endereços de memória
	f->ultimo->prox = c;
	f->ultimo = c;
}

int Remover(struct Fila* f){
	if(f->ultimo == f->primeiro){
		return -1;
	}
	else{
		struct Celula* temp;//Cria um ponteiro temporário
		temp = f->primeiro;//'temp' recebe o endereço de memória da primeira célula da fila
		f->primeiro = f->primeiro->prox;//A célula da segunda posição agora é a primeira da fila
		int elementoRemovido = f->primeiro->elemento;//Considera que o elemento removido é o que agora ocupa a primeira posição

		free(temp);
	
		return elementoRemovido;
	}
}

void Exibir(struct Fila* f){//O asterisco indica que 'f' deve receber um endereço de memória de uma variável do tipo "Fila" 
	if(f->primeiro == f->ultimo){
		printf("V\n");
	}
	else{
		for(struct Celula* i = f->primeiro->prox; i != NULL; i = i->prox){//Percorre a fila começando do segundo elemento por que consideramos que o primeiro já foi excluído
			if(i->prox != NULL){
			printf("%d ", i->elemento);
			}
			else{
				printf("%d\n", i->elemento);
			}
		}
	}	
}

char Pesquisar(struct Fila* f, int elementoPesquisar){
	if(f->ultimo == f->primeiro){
		return 'N';		
	}
	else{
		for(struct Celula* i = f->primeiro->prox; i != NULL; i = i->prox){
			if(i->elemento == elementoPesquisar){
				return 'S';
			}
		}
		return 'N';
	}
}

//Função liberar mémoria
void liberarMemoria(struct Fila* f){
	while(f->primeiro->prox != NULL){
		Remover(f);//Remove todas as células restantes na fila
	}

	free(f->primeiro);//Remove a célula cabeça
	
	free(f);//Remove a fila
}


int main(){
	char comando;
	struct Fila* f = (struct Fila*) malloc(sizeof(struct Fila));//Cria uma Fila e armazena na variável 'f' o endereço de memória dela
	IniciarFila(f);//chama a função "IniciarFila" passando o conteúdo de 'f'(endereço de memória)

	while(scanf(" %c", &comando) != EOF){
		if(comando == 'E'){
			int elementoInserir;
			scanf("%d", &elementoInserir);
			Inserir(f, elementoInserir);
		}
		else if(comando == 'D'){
			printf("%d\n", Remover(f));
		}
		else if(comando == 'M'){
			Exibir(f);
		}
		else if(comando == 'P'){
			int elementoPesquisar;
			scanf("%d", &elementoPesquisar);
			printf("%c\n", Pesquisar(f, elementoPesquisar));
		}
	}
	liberarMemoria(f);
	return 0;
}
