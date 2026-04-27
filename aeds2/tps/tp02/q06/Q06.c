#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>//Biblioteca para calcular o tempo de execução

//Variáveis globais
int comparacoes = 0;
int movimentacoes = 0;

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

//Swap
void swap(Restaurante** pos1, Restaurante** pos2){
	Restaurante* temp = *pos1;
	*pos1 = *pos2;
	*pos2 = temp;
	
	movimentacoes += 3;//3 movimentações por swap
}

//Algorítmo de selecao
void selecao(Colecao_Restaurantes* c){
	for(int i = 0; i < (c->tamanho - 1); i++){
		int menor = i;
		for(int j = (i + 1); j < c->tamanho; j++){
			comparacoes++;
			if(strcmp(c->restaurante[j]->nome, c->restaurante[menor]->nome) < 0){
				menor = j;
			}
		}
		swap(&c->restaurante[i], &c->restaurante[menor]);
	}
}

//Algorítmo de pesquisa binária
bool pesquisa_binaria(Colecao_Restaurantes* c, char* nomeBuscado) {
	int esq = 0;
	int dir = c->tamanho - 1;
	bool encontrado = false;

	while (esq <= dir && !encontrado) {
		int meio = (esq + dir) / 2;
		int comp = strcmp(nomeBuscado, c->restaurante[meio]->nome);
        
        	comparacoes++;
        	if (comp == 0) {
            		encontrado = true;
        	} 
		else if (comp > 0) {
            		esq = meio + 1;
        	} 
		else{
            		dir = meio - 1;
        	}
    	}
	return encontrado;
}

int main(){
	Colecao_Restaurantes* c = ler_csv();
	Colecao_Restaurantes restaurantesBuscados;//Cria uma coleção dos restaurantes que foram buscados pelo usuário
	restaurantesBuscados.tamanho = 0;
	restaurantesBuscados.restaurante = (Restaurante**) malloc(500 * sizeof(Restaurante*));

	int id;

	while(scanf("%d", &id) == 1 && id != -1){//Roda o arquivo procurando pelo id digitado pelo usuario
		bool encontrado = false;
		int cont = 0;
		while(cont < c->tamanho && !encontrado){
			if(c->restaurante[cont]->id == id){
				restaurantesBuscados.restaurante[restaurantesBuscados.tamanho++] = c->restaurante[cont];
				encontrado = true;
			}
			cont++;
		}
	}

	selecao(&restaurantesBuscados);
	
	char nomePesquisa[100];
	getchar();
	
	bool resultados[500];
	int contBuscas = 0;	

	clock_t inicio = clock();//Inicia o contador

	bool continuar = true;

	while(continuar && fgets(nomePesquisa, sizeof(nomePesquisa), stdin)){
		nomePesquisa[strcspn(nomePesquisa, "\r\n")] = 0;

		if(strcmp(nomePesquisa, "FIM") == 0){
			continuar = false;
		}
		else{
			resultados[contBuscas] = pesquisa_binaria(&restaurantesBuscados, nomePesquisa);
			contBuscas++;
		}
	
	}
	
	clock_t fim = clock();//Finaliza o contador
		
	double tempoTotal = ((double)(fim - inicio)) / CLOCKS_PER_SEC;//Calcula o tempo de execução do pesquisa binária

	for(int i = 0; i < contBuscas; i++){//Printa na tela os resultados
		printf("%s\n", resultados[i] ? "SIM" : "NAO");
	}
	
	//Gerar o arquivo de log
	FILE* log = fopen("899683_selecao.txt", "w");
	if(log != NULL){
		fprintf(log, "899683\t%d\t%d\t%lf", comparacoes, movimentacoes, tempoTotal);
		fclose(log);
	}

	free(restaurantesBuscados.restaurante);
	limpar_colecao(c);
	free(c);	
	return 0;
}
