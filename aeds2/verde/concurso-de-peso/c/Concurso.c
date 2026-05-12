#include <stdio.h>
#include <string.h>
#include <stdlib.h>

typedef struct{
	char nome[50];
	int peso;
}Atleta;

typedef struct{//Não pode malloc dentro da struct
	Atleta* atletas;
	int index;
}Lista;

Lista* iniciarLista(){
	Lista* l = (Lista*) malloc(sizeof(Lista));
	l->atletas = (Atleta*) malloc(100 * sizeof(Atleta));
	l->index = 0;

	return l;
}

void inserir(char* nome, int peso, Lista* l){
	strcpy(l->atletas[l->index].nome, nome);//Copia a nome para a posição do array
	l->atletas[l->index].peso = peso;
	l->index++;
}

void selecao(Lista* l){
	for(int i = 0; i < l->index; i++){
		int maior = i;

		for(int j = i + 1; j < l->index; j++){
			if(l->atletas[j].peso > l->atletas[maior].peso){
				maior = j;
			}
			else if(l->atletas[j].peso == l->atletas[maior].peso){//verifica se são iguais
				if(strcmp(l->atletas[j].nome, l->atletas[maior].nome) > 0){
					maior = j;
				}
			}
		}
		
		Atleta temp = l->atletas[maior];
		l->atletas[maior] = l->atletas[i];
		l->atletas[i] = temp;

	}
}

void exibir(Lista* l){
	for(int i = 0; i < l->index; i++){
		printf("%s %d\n", l->atletas[i].nome, l->atletas[i].peso);
	}
}

int main(){
	char nome[50];
	int peso;
	Lista* l = iniciarLista();	

	while(scanf("%s %d", nome, &peso) != EOF){
		inserir(nome, peso, l);
	}

	selecao(l);
	exibir(l);

	//printf("%s ", nome);
	//printf("%d\n", peso);
}
