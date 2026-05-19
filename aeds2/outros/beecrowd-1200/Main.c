#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Estrutura do Nó da Árvore
typedef struct No {
    char elemento;
    struct No* esq;
    struct No* dir;
} No;

// Estrutura da Árvore (Guarda o ponteiro para a raiz)
typedef struct {
    No* raiz;
} Arvore;

// Controle global para formatação dos espaços na saída dos percursos
int primeiro_elemento;

// Função Recursiva para Inserir um elemento na BST
No* inserirNo(No* atual, char elemento) {
    if (atual == NULL) {
        No* novo = (No*) malloc(sizeof(No));
        novo->elemento = elemento;
        novo->esq = NULL;
        novo->dir = NULL;
        return novo;
    }

    if (elemento < atual->elemento) {
        atual->esq = inserirNo(atual->esq, elemento);
    } else if (elemento > atual->elemento) {
        atual->dir = inserirNo(atual->dir, elemento);
    }

    return atual;
}

// Função Recursiva para Pesquisar um elemento na BST
int pesquisarNo(No* atual, char elemento) {
    if (atual == NULL) {
        return 0; // Não existe
    }
    if (atual->elemento == elemento) {
        return 1; // Existe
    }

    if (elemento < atual->elemento) {
        return pesquisarNo(atual->esq, elemento);
    } else {
        return pesquisarNo(atual->dir, elemento);
    }
}

// Percurso Infixo (Inorder): Esquerda -> Raiz -> Direita
void infixa(No* atual) {
    if (atual != NULL) {
        infixa(atual->esq);
        
        if (primeiro_elemento) {
            printf("%c", atual->elemento);
            primeiro_elemento = 0;
        } else {
            printf(" %c", atual->elemento);
        }
        
        infixa(atual->dir);
    }
}

// Percurso Prefixo (Preorder): Raiz -> Esquerda -> Direita
void prefixa(No* atual) {
    if (atual != NULL) {
        if (primeiro_elemento) {
            printf("%c", atual->elemento);
            primeiro_elemento = 0;
        } else {
            printf(" %c", atual->elemento);
        }
        
        prefixa(atual->esq);
        prefixa(atual->dir);
    }
}

// Percurso Posfixo (Postorder): Esquerda -> Direita -> Raiz
void posfixa(No* atual) {
    if (atual != NULL) {
        posfixa(atual->esq);
        posfixa(atual->dir);
        
        if (primeiro_elemento) {
            printf("%c", atual->elemento);
            primeiro_elemento = 0;
        } else {
            printf(" %c", atual->elemento);
        }
    }
}

// Função para desalocar a memória da árvore ao final do programa
void liberarArvore(No* atual) {
    if (atual != NULL) {
        liberarArvore(atual->esq);
        liberarArvore(atual->dir);
        free(atual);
    }
}

int main() {
    char comando[20];
    char elemento;

    // Inicializa a árvore
    Arvore* a = (Arvore*) malloc(sizeof(Arvore));
    a->raiz = NULL;

    // Loop de leitura até o Fim do Arquivo (EOF)
    while (scanf("%s", comando) != EOF) {
        
        if (strcmp(comando, "I") == 0) {
            scanf(" %c", &elemento);
            a->raiz = inserirNo(a->raiz, elemento);
        } 
        else if (strcmp(comando, "P") == 0) {
            scanf(" %c", &elemento);
            if (pesquisarNo(a->raiz, elemento)) {
                printf("%c existe\n", elemento);
            } else {
                printf("%c nao existe\n", elemento);
            }
        } 
        else if (strcmp(comando, "INFIXA") == 0) {
            primeiro_elemento = 1; // Reseta o sinalizador de espaço
            infixa(a->raiz);
            printf("\n");
        } 
        else if (strcmp(comando, "PREFIXA") == 0) {
            primeiro_elemento = 1;
            prefixa(a->raiz);
            printf("\n");
        } 
        else if (strcmp(comando, "POSFIXA") == 0) {
            primeiro_elemento = 1;
            posfixa(a->raiz);
            printf("\n");
        }
    }

    // Libera a memória alocada dinamicamente
    liberarArvore(a->raiz);
    free(a);

    return 0;
}
