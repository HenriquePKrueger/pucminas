#include <stdio.h>

char* codificar(char entrada[], char saida[], int cont){
	if(entrada[cont] == '\0' || entrada[cont] == '\n'){
		saida[cont] = '\0';
		return saida; //Retorna a string cifrada quando chegar no final
	}
	else{
		saida[cont] = (entrada[cont] + 3); //Pega o caracter na posiçao cont + 3 e coloca na string saida
		return codificar(entrada, saida, cont + 1);
	}
}

int main(){
	char entrada[1000];
	char saida[1000];
	int cont = 0;

	fgets(entrada, 1000, stdin);
	while(entrada[0] != 'F' || entrada[1] != 'I' || entrada[2] != 'M'){
		printf("%s\n", codificar(entrada, saida, cont));
		fgets(entrada, 1000, stdin);
	}
	return 0;
}
