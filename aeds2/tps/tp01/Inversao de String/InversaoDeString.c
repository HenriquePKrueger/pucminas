#include <stdio.h>
char invert(char str[]){
	int tamanho = 0;
	int temp;
	while(str[tamanho] != '\0' && str[tamanho] != '\n'){ //Verifica tamanho da string
		tamanho++;
	}	
	for(int i = 0; i < tamanho/2; i++){
		temp = str[i];
		str[i] = str[tamanho - 1 - i]; //Pega o último caractere e joga no início da string
		str[tamanho - 1 - i] = temp; //Pega o primeiro e joga no final
		if(str[i] == '\n'){
			str[i] = '\0';
		}
	}
}

int main(){
	char str[100];
	char strInvert[100];

	fgets(str, 100, stdin);
	while(!(str[0] == 'F' && str[1]== 'I' && str[2] == 'M')){
		invert(str);
		printf("%s", str);
		fgets(str, 100, stdin);
	}
	return 0;
}
