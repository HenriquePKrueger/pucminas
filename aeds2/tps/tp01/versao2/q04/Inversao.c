#include <stdio.h>

void  inverter(char *str){
	int cont = 0;
	char strInvertida[100];
	while(str[cont] != '\0' && str[cont] != '\n'){ //Conta o numero de alementos da string
		cont++;
	}
	for(int i = 0; i <= cont; i++){
		strInvertida[i] = str[cont - i];
	}
	strInvertida[cont + 1] = '\0';
	
	str = strInvertida;
}

int main(){
	char str[100];
	fgets(str, 100, stdin);

	while((str != '\0') && (str != '\n')){
		printf("%s\n", str);
		fgets(str, 100, stdin);
	}
}
