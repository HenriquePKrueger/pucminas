#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

//Struct Hora
typedef struct {
	int hora;
	int minuto;
}Hora;

Hora parse_hora(char* s){
	Hora h;

	sscanf(s, "%d:%d", &h.hora, &h.minuto);//Usar "." pois está apontando diretamente para a struct
	return h;
}

void formatar_hora(Hora* hora, char* buffer){
	sprintf(buffer, "%02d:%02d", hora->hora, hora->minuto);//Usar "->" pois nesse caso se trata de um ponteiro
}

//Struct Data
typedef struct{
	int ano;
	int mes;
	int dia;
}Data;

Data parse_data(char* s){
	Data d;
	
	sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
	return d;
}

void formatar_data(Data* data, char* buffer){
	sprintf(buffer, "%02d/%02d/%d", data->dia, data->mes, data->ano);
}

//Struct Restaurante
typedef struct{
    	int id;
    	char* nome;
    	char* cidade;
    	int capacidade;
    	double avaliacao;
    	int n_tipos_cozinha;
    	char** tipos_cozinha;
    	int faixa_preco;
    	Hora horario_abertura;
    	Hora horario_fechamento;
    	Data data_abertura;
    	bool aberto;
}Restaurante;

Restaurante parse_restaurante(char* s){
   	 Restaurante r;

   	//Variáveis temporárias para receber as string antes da conversão e para saber o tamanho das variáveis quie precisam do malloc
	char nomeTmp[100];
	char cidadeTmp[100];
	char tiposCozinhaTmp[100];
	char precoTmp[10];
    	char hrAbertura[10];
	char hrFechamento[10];
	char dtAbertura[20];
	char abertoTmp[20];

    	sscanf(s, "%d,%[^,],%[^,],%d,%lf,%[^,],%[^,],%[^-]-%[^,],%[^,],%s", &r.id, nomeTmp, cidadeTmp, &r.capacidade, &r.avaliacao, tiposCozinhaTmp, precoTmp, hrAbertura, hrFechamento, dtAbertura, abertoTmp);
	
	//Aloca memória para variáveis da struct usando o tamanho das variáveis temporárias como referência
	r.nome = (char*) malloc((strlen(nomeTmp) + 1) * sizeof(char));
	strcpy(r.nome, nomeTmp);
	r.cidade = (char*) malloc((strlen(cidadeTmp) + 1) * sizeof(char));
	strcpy(r.cidade, cidadeTmp);
	r.tipos_cozinha = (char**) malloc(15 * sizeof(char*));
	
	r.n_tipos_cozinha = 0;
	int inicio = 0;
	for(int i = 0; i <= strlen(tiposCozinhaTmp); i++){//Separa cada tipo de culinária em diferentes strings
		if(tiposCozinhaTmp[i] == ';' || tiposCozinhaTmp[i] == '\0'){
			int tamanho = i - inicio;
			r.tipos_cozinha[r.n_tipos_cozinha] = (char*) malloc((tamanho + 1)* sizeof(char));
			for(int j = 0; j < tamanho; j++){
				r.tipos_cozinha[r.n_tipos_cozinha][j] = tiposCozinhaTmp[inicio + j];
			}
			r.tipos_cozinha[r.n_tipos_cozinha][tamanho] = '\0';
			r.n_tipos_cozinha++;
			inicio = i + 1;
		}
	}

	//Atribuir variáveis restantes
	r.faixa_preco = strlen(precoTmp);
	r.horario_abertura = parse_hora(hrAbertura);
	r.horario_fechamento = parse_hora(hrFechamento);
	r.data_abertura = parse_data(dtAbertura);
	r.aberto = (strcmp(abertoTmp, "true") == 0);
	
	return r;
}

void formatar_restaurante(Restaurante* r, char* buffer) {
	char strPreco[20] = "";
	char hrAbertura[20] = "";
   	char hrFechamento[20] = "";
	char dtAbertura[20] = "";
	char strTiposCozinha[200] = "";

	for(int i = 0; i < r->faixa_preco; i++){//Monta uma string formada por "$" de acordo com o valor de r->faixa_preco
		strcat(strPreco, "$");
	}

	for(int i = 0; i < r->n_tipos_cozinha; i++){//Combina os tipos de cozinha em uma string só para ser exibido
		strcat(strTiposCozinha, r->tipos_cozinha[i]);
		if(i < r->n_tipos_cozinha - 1){
			strcat(strTiposCozinha, ",");
		}
	}	    

	formatar_hora(&r->horario_abertura, hrAbertura);
    	formatar_hora(&r->horario_fechamento, hrFechamento);
    	formatar_data(&r->data_abertura, dtAbertura);

    	sprintf(buffer, "[%d ## %s ## %s ## %d ## %.1lf ## [%s] ## %s ## %s-%s ## %s ## %s]", r->id, r->nome, r->cidade, r->capacidade, r->avaliacao, strTiposCozinha, strPreco, hrAbertura, hrFechamento, dtAbertura, r->aberto ? "true" : "false");
}

//Struct Colecao_Restaurante
typedef struct{
	int tamanho;
	Restaurante** restaurante;
}Colecao_Restaurantes;

void ler_csv_colecao(Colecao_Restaurantes* c, char* path){
	FILE* arquivo = fopen(path, "r");

	if(arquivo == NULL){
		printf("Erro ao abrir arquivo!");
		return;
	}

	char linha[500];
	int numLinhas = 0;

	fgets(linha, sizeof(linha), arquivo);//Descartar a primeira linha do cabeçalho
	while(fgets(linha, sizeof(linha), arquivo) != NULL){//Conta o número de linhas(restaurantes) do arquivo
		numLinhas++;
	}
	
	c->restaurante = (Restaurante**) malloc(numLinhas * sizeof(Restaurante*));
	c->tamanho = 0;
	
	rewind(arquivo);//Volta para o início do arquivo para nova leitura

	fgets(linha, sizeof(linha), arquivo);
	while(fgets(linha, sizeof(linha), arquivo) != NULL){//Envia as linhas para a função parse_restaurante
		linha[strcspn(linha, "\n")] = '\0';//Remover o '\n'
		Restaurante r = parse_restaurante(linha);
		c->restaurante[c->tamanho] = (Restaurante*) malloc(sizeof(Restaurante));
		*c->restaurante[c->tamanho] = r;
		
		c->tamanho++;
	}
	fclose(arquivo);
}

Colecao_Restaurantes* ler_csv(){
	Colecao_Restaurantes* c = (Colecao_Restaurantes*) malloc(sizeof(Colecao_Restaurantes));
	c->tamanho = 0;
	c->restaurante = NULL;
	ler_csv_colecao(c, "/tmp/restaurantes.csv");
	return c;
}

//Struct celula
typedef struct Celula{
	Restaurante* restaurante;
	struct Celula* prox;
}Celula;

Celula* nova_celula(Restaurante* restaurante){
	Celula* nova = (Celula*) malloc(sizeof(Celula));
	
	nova->restaurante = restaurante;
	nova->prox = NULL;
	
	return nova;
}

//Struct Lista Flexível
typedef struct{
	Celula* primeiro;
	Celula* ultimo;
	int tam;
}ListaFlexivel;

void iniciar_lista(ListaFlexivel* lista){
	lista->primeiro = nova_celula(NULL);
	lista->ultimo = lista->primeiro;
	lista->tam = 0;
}

void inserir_inicio(ListaFlexivel* lista, Restaurante* restaurante){
	Celula* tmp = nova_celula(restaurante);
	tmp->prox = lista->primeiro->prox;
	lista->primeiro->prox = tmp;

	if(lista->primeiro == lista->ultimo){
		lista->ultimo = tmp;
	}
	
	lista->tam++;
}

void inserir_fim(ListaFlexivel* lista, Restaurante* restaurante){
        lista->ultimo->prox = nova_celula(restaurante);
        lista->ultimo = lista->ultimo->prox;

        lista->tam++;
}

void inserir(ListaFlexivel* lista, Restaurante* restaurante, int pos){
	if(pos < 0 || pos > lista->tam){
		return;
	}
	if(pos == 0){
		inserir_inicio(lista, restaurante);
	}
	else if(pos == lista->tam){
		inserir_fim(lista, restaurante);
	}
	else{
		Celula* i = lista->primeiro;
	
		for(int j = 0; j < pos; j++, i = i->prox);
		
		Celula* tmp = nova_celula(restaurante);
		tmp->prox = i->prox;
		i->prox = tmp;
		lista->tam++;
	}
}

Restaurante* remover_inicio(ListaFlexivel* lista){
	if(lista->primeiro == lista->ultimo){
    	   	 return NULL;
    	}
	Celula* tmp = lista->primeiro->prox;
	lista->primeiro->prox = tmp->prox;

	if(tmp == lista->ultimo){
		lista->ultimo = lista->primeiro;
    	}
	Restaurante* resp = tmp->restaurante;
	free(tmp);
	lista->tam--;

	return resp;
}

Restaurante* remover_fim(ListaFlexivel* lista){
	if(lista->primeiro == lista->ultimo){
        	return NULL;
    	}
    	Celula* i;

    	for(i = lista->primeiro; i->prox != lista->ultimo; i = i->prox);
    	Restaurante* resp = lista->ultimo->restaurante;
    	free(lista->ultimo);
    	lista->ultimo = i;
    	i->prox = NULL;
    	lista->tam--;

    	return resp;
}

Restaurante* remover(ListaFlexivel* lista, int pos){
	if(pos < 0 || pos >= lista->tam){
        	return NULL;
    	}
    	if(pos == 0){
		return remover_inicio(lista);
    	}
	if(pos == lista->tam - 1){
        	return remover_fim(lista);
    	}
    	Celula* i = lista->primeiro;

    	for(int j = 0; j < pos; j++, i = i->prox);
	Celula* tmp = i->prox;
	i->prox = tmp->prox;
	Restaurante* resp = tmp->restaurante;
	free(tmp);
    	lista->tam--;

    	return resp;
}

void mostrar_lista(ListaFlexivel* lista){
	char buffer[500];

  	for(Celula* i = lista->primeiro->prox;i != NULL; i = i->prox){
        	formatar_restaurante(i->restaurante, buffer);
        	printf("%s\n", buffer);
    	}
}

void limpar_lista(ListaFlexivel* lista){
	Celula* i = lista->primeiro;
	
	while(i != NULL){
		Celula* tmp = i;
		i = i->prox;
		free(tmp);
	}
}


void limpar_colecao(Colecao_Restaurantes* c){//Função para liberar os malloc
	for(int i = 0; i < c->tamanho; i++){
		free(c->restaurante[i]->nome);
		free(c->restaurante[i]->cidade);
		for(int j = 0; j < c->restaurante[i]->n_tipos_cozinha; j++){
			free(c->restaurante[i]->tipos_cozinha[j]);
		}
		free(c->restaurante[i]->tipos_cozinha);
		free(c->restaurante[i]);
		
	}
	free(c->restaurante);
}

int main(){
	Colecao_Restaurantes* c = ler_csv();
	ListaFlexivel lista;
	iniciar_lista(&lista);

	int id;
	
	//Parte 1
	while(scanf("%d", &id) && id != -1){//Lê os ids até o "-1"
		for(int i = 0; i < c->tamanho; i++){
			if(c->restaurante[i]->id == id){
				inserir_fim(&lista, c->restaurante[i]);
			}
		}
	}

	//Parte 2
	int qntRegistros;
	
	scanf("%d", &qntRegistros);

	for(int i = 0; i < qntRegistros; i++){
		char comando[5];
		
		scanf("%s", comando);
		if(strcmp(comando, "II") == 0){
			scanf("%d", &id);
			for(int j = 0; j < c->tamanho; j++){
				if(c->restaurante[j]->id == id){
					inserir_inicio(&lista, c->restaurante[j]);
				}
			}
		}
		else if(strcmp(comando, "IF") == 0){
			scanf("%d", &id);
			for(int j = 0; j < c->tamanho; j++){
				if(c->restaurante[j]->id == id){
					inserir_fim(&lista, c->restaurante[j]);
				}
			}
		}
		else if(strcmp(comando, "I*") == 0){
			int pos;
				
			scanf("%d %d", &pos, &id);
			for(int j = 0; j < c->tamanho; j++){
				if(c->restaurante[j]->id == id){
					inserir(&lista, c->restaurante[j], pos);
				}
			}
		}
		else if(strcmp(comando, "RI") == 0){
			Restaurante* r = remover_inicio(&lista);
			printf("(R)%s\n", r->nome);
		}
		else if(strcmp(comando, "RF") == 0){
			Restaurante* r = remover_fim(&lista);
			printf("(R)%s\n", r->nome);
		}
		else if(strcmp(comando, "R*") == 0){
			int pos;
			
			scanf("%d", &pos);
			Restaurante* r = remover(&lista, pos);
			printf("(R)%s\n", r->nome);
		}
	}

	mostrar_lista(&lista);

	limpar_lista(&lista);
	limpar_colecao(c);
	free(c);

	return 0;
}

