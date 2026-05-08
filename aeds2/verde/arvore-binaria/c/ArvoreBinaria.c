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
// Pesquisar
// =========================
int pesquisarRec(struct No* i, int elementoPesquisar){
	int resp = 0;

	if(i == NULL){
		resp = 0;
	}
	else if(i->elemento == elementoPesquisar){
		printf("%d ", i->elemento);
		resp = 1;
	}
	else if(i->elemento > elementoPesquisar){
		printf("%d ", i->elemento);
		resp = pesquisarRec(i->esq, elementoPesquisar);
	}
	else{
		printf("%d ", i->elemento);
		resp = pesquisarRec(i->dir, elementoPesquisar);
	}

	
	return resp;
}

void pesquisar(struct ArvoreBinaria* arvore, int elementoPesquisar){
	printf("%s\n", pesquisarRec(arvore->raiz, elementoPesquisar) ? "S" : "N");
}

// =========================
// Caminhar Pré-ordem
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
		printf("V");
	}
}

// =========================
// Caminhar Pós-ordem
// =========================
void caminharPosRec(struct No* i){
	if(i != NULL){
		caminharPosRec(i->esq);
		caminharPosRec(i->dir);
		printf("%d ", i->elemento);
	}
}

void caminharPosOrdem(struct ArvoreBinaria* arvore){
	if(arvore->raiz != NULL){
		caminharPosRec(arvore->raiz);
	}
	else{
		printf("V");
	}
}

// =========================
// Caminhar Em-ordem
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
		printf("V");
	}
}

// =========================
// Main
// =========================
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
		else if(strcmp(comando, "POS") == 0){
			caminharPosOrdem(&arvore);
			printf("\n");
		}
		else if(strcmp(comando, "P") == 0){
			int elementoPesquisar;
			scanf("%d", &elementoPesquisar);
			pesquisar(&arvore, elementoPesquisar);
		}
	}
}
