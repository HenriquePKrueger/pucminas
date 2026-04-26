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
typedef struct {
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
} Restaurante;

Restaurante parse_restaurante(char* s) {
   	 Restaurante r;

   	//Variáveis temporárias para ler as strings antes de converter
	char nomeTmp[100];
	char cidadeTmp[100];
	char tiposTmp[100];
	char precoTmp[10];
    	char hrAbertura[10];
	char hrFechamento[10];
	char dtAbertura[20];
	char abertoTmp[20];

    	sscanf(s, "%d,%[^,],%[^,],%d,%lf,%[^,],%[^,],%[^-]-%[^,],%[^,],%s", &r.id, nomeTmp, cidadeTmp, &r.capacidade, &r.avaliacao, tiposTmp, precoTmp, hrAbertura, hrFechamento, dtAbertura, abertoTmp);
	
	//Alocação de memória para variáveis da struct usando o tamanho das variáveis temporárias
	r.nome = (char*) malloc((strlen(nomeTmp) + 1) * sizeof(char));
	strcpy(r.nome, nomeTmp);
	r.cidade = (char*) malloc((strlen(cidadeTmp) + 1) * sizeof(char));
	strcpy(r.cidade, cidadeTmp);
	r.tipos_cozinha = (char**) malloc(15 * sizeof(char*));
	
	//Separar os tipos de cozinha em strings "independentes"
	r.n_tipos_cozinha = 0;
	int inicio = 0;
	for(int i = 0; i < strlen(tiposTmp); i++){
		if(tiposTmp[i] == ';' || tiposTmp[i] == '\0'){
			int tamanho = i - inicio;
			r.tipos_cozinha[r.n_tipos_cozinha] = (char*) malloc((tamanho + 1)* sizeof(char));
			for(int j = 0; j < tamanho; j++){
				r.tipos_cozinha[r.n_tipos_cozinha][j] = tiposTmp[inicio + j];
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
	char hrAbertura[20] = "";
   	char hrFechamento[20] = "";
	char dtAbertura[20] = "";
	char strTiposCozinha[200] = "";

	for(int i = 0; i < r->n_tipos_cozinha; i++){
		strcat(strTiposCozinha, r->tipos_cozinha[i]);
		if(i < r->n_tipos_cozinha - 1){
			strcat(strTiposCozinha, ", ");
		}
	}	    

	formatar_hora(&r->horario_abertura, hrAbertura);
    	formatar_hora(&r->horario_fechamento, hrFechamento);
    	formatar_data(&r->data_abertura, dtAbertura);

    	sprintf(buffer, "[%d ## %s ## %s ## %d ## %.1lf ## [%s] ## %d ## %s-%s ## %s ## %s]", r->id, r->nome, r->cidade, r->capacidade, r->avaliacao, strTiposCozinha, r->faixa_preco, hrAbertura, hrFechamento, dtAbertura, r->aberto ? "true" : "false");
}

int main(){
	//teste
	char entrada[] = "1,Classic Palace Works,Zurich,168,3.9,churrasco;internacional,$$,11:00-20:00,2018-03-31,false";
	Restaurante r1 = parse_restaurante(entrada);
	char saida[600];
	formatar_restaurante(&r1, saida);
	printf("%s\n", saida);
	return 0;
}	
