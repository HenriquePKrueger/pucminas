#include <stdio.h>
#include <string.h>
#include <stdlib.h>

struct No {
	int elemento;
	struct No *esq;
	struct No *dir;
};

struct ArvoreBinaria{
	struct No* raiz;
};

// =========================
// Inserir
// =========================
struct No* inserirRec(struct No* i, int elementoInserir){
	if(i == NULL){
		struct No* novo = (struct No*) malloc(sizeof(struct No));
		novo->elemento = elementoInserir;
		novo->esq = NULL;
		novo->dir = NULL;

		return novo;
	}
	else if(i->elemento > elementoInserir){
		i->esq = inserirRec(i->esq, elementoInserir);
	}
	else{
		i->dir = inserirRec(i->dir, elementoInserir);
	}

	return i;
}

void inserir(struct ArvoreBinaria* arvore, int elementoInserir){
	arvore->raiz = inserirRec(arvore->raiz, elementoInserir);
}

// =========================
// Exibir Pre-ordem
// =========================
void caminharPreRec(struct No* i){
	if(i != NULL){
		printf("%d ", i->elemento);
		caminharPreRec(i->esq);
		caminharPreRec(i->dir);
	}
}

void caminharPreOrdem(struct ArvoreBinaria* arvore){
	if(arvore->raiz != NULL){
		caminharPreRec(arvore->raiz);
	}
	else{
		printf("V\n");
	}
}

// =========================
// Exibir Em-ordem
// =========================
void caminharCentralRec(struct No* i){
	if(i != NULL){
		caminharCentralRec(i->esq);
		printf("%d ", i->elemento);
		caminharCentralRec(i->dir);
	}
}

void caminharCentral(struct ArvoreBinaria* arvore){
	if(arvore->raiz != NULL){
		caminharCentralRec(arvore->raiz);
	}
	else{
		printf("V\n");
	}
} 

int main(){
	struct ArvoreBinaria arvore;
	char comando[10];
	
	while(scanf("%s", comando) != EOF){
	
		if(strcmp(comando, "I") == 0){
			int elementoInserir;
			scanf("%d", &elementoInserir);
			inserir(&arvore, elementoInserir);
		}
		else if(strcmp(comando, "EM") == 0){
			caminharCentral(&arvore);
			printf("\n");
		}
		else if(strcmp(comando, "PRE") == 0){
			caminharPreOrdem(&arvore);
			printf("\n");
		}
	}
}
