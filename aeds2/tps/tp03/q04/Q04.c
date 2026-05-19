#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

long comparacoes = 0;
long movimentacoes = 0;

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

int comparar_datas(Data a, Data b){
	if(a.ano != b.ano){
		return a.ano - b.ano;
	}
	if(a.mes != b.mes){
		return a.mes - b.mes;
	}
	
	return a.dia - b.dia;
}

void swap(Restaurante** restaurantes, int i, int j){
	Restaurante* tmp = restaurantes[i];
	restaurantes[i] = restaurantes[j];
	restaurantes[j] = tmp;
	
	movimentacoes += 3;
}

void construir(Restaurante** restaurantes, int tam){
	for(int i = tam; i > 1 && comparar_datas(restaurantes[i - 1]->data_abertura, restaurantes[(i / 2) - 1]->data_abertura) > 0; i /= 2){
		comparacoes++;
		swap(restaurantes, i - 1, (i / 2) - 1);	
	}
}

void reconstruir(Restaurante** restaurantes, int tam){
    	int i = 1;
	
   	 while(i <= tam / 2){

        	int filho = i * 2;

        	if(filho < tam && comparar_datas(restaurantes[filho]->data_abertura, restaurantes[filho - 1]->data_abertura) > 0){
            		filho++;
        	}
        	comparacoes++;

        	if(comparar_datas(restaurantes[filho - 1]->data_abertura, restaurantes[i - 1]->data_abertura) > 0){
            		swap(restaurantes, filho - 1, i - 1);
            		i = filho;
        	}
        	else{
            		i = tam;
        	}
    	}
}

void heapsort_parcial(Restaurante** restaurantes, int n, int k){
	for(int tam = 2; tam <= k; tam++){
        	construir(restaurantes, tam);
    	}

    	for(int i = k; i < n; i++){
		comparacoes++;
		if(comparar_datas(restaurantes[i]->data_abertura, restaurantes[0]->data_abertura) < 0){
			swap(restaurantes, i, 0);
			reconstruir(restaurantes, k);
		}
	}
	
	int tam = k;

	while(tam > 1){
		swap(restaurantes, 0, tam - 1);
		tam--;
		reconstruir(restaurantes, tam);
	}
}

void gerar_log(double tempo){
	FILE* log = fopen("899683_heapsort_parcial.txt", "w");

	fprintf(log, "899683\t%ld\t%ld\t%lf", comparacoes, movimentacoes, tempo);
	
	fclose(log);
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
	Colecao_Restaurantes restaurantesBuscados;
	restaurantesBuscados.tamanho = 0;
	restaurantesBuscados.restaurante = (Restaurante**) malloc(500 * sizeof(Restaurante*));

	int id;
	int k = 10;

	while(scanf("%d", &id) && id != -1){//Lê os ids até o "-1"
		bool encontrado = false;
		int cont = 0;
		while(cont < c->tamanho && !encontrado){//Percorre a colecao buscando o id informado
			if(c->restaurante[cont]->id == id){//Cria uma nova coleção com os restaurantes de id informado
				restaurantesBuscados.restaurante[restaurantesBuscados.tamanho] = c->restaurante[cont];
				restaurantesBuscados.tamanho++;
				encontrado = true;
			}
			cont++;
		}
	}
	
	clock_t inicio = clock();
	heapsort_parcial(restaurantesBuscados.restaurante, restaurantesBuscados.tamanho, k);
	clock_t fim = clock();

	double tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC;
	
	char buffer[500];
	for(int i = 0; i < restaurantesBuscados.tamanho; i++){//Printa a colecao criada anteriormente
		formatar_restaurante(restaurantesBuscados.restaurante[i], buffer);
		printf("%s\n", buffer);
	}

	gerar_log(tempo);

	limpar_colecao(c);
	free(c);
	free(restaurantesBuscados.restaurante);
	return 0;
}
