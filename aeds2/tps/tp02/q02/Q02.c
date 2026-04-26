#include <stdio.h>
#include <string.h>

typedef struct {//Definindo a struct
	int Hora;
	int minuto;
}Hora;

void parse_hora(char* s){
	Hora h;

	sscanf(s, "%d:%d", &h.hora, &h.minuto);//Usar "." pois está apontando diretamente para a struct
	return h;
}

void formatar_hora(Hora* hora, char* buffer){
	sprintf(buffer, "%02d:%02d", h->hora, h->minuto);//Usar "->" pois nesse caso se trata de um ponteiro
}



int main(){

}
