#include <stdio.h>

int verificar(char original[100], char anagrama[100], int j, int k){
	if(j != k){
		return 0;
	}
	else{
		int verificador;
		for(int i = 0; i <= j; i++){
			verificador = 0;
			for(int l = 0; l <= k; l++){
				if(original[i] == anagrama[l]){
					verificador = 1;
				}
			}
			if(verificador == 0){
				return 0;
			}
			else{
				return 1;
			}
		}
	}
}

int separarStr(char entrada[100]){
	char original[100];
	char anagrama[100];
	int cheio = 0;
	int i = 0, j = 0, k = 0;

	while(entrada[i] != '\0' && entrada[i] != '\n'){
		if(entrada[i] != ' ' && cheio == 0){
			if(entrada[i] >= 'A' && entrada[i] <= 'Z'){
				original[j] = (entrada[i] + 32);
			}
			else{
				original[j] = entrada[i];
			}
			j++;
		}
		else if(entrada[i] == ' '){
			cheio++;
		}
		else if(entrada[i] != ' ' && entrada[i] != '-'){
			if(entrada[i] >= 'A' && entrada[i] <= 'Z'){
				anagrama[k] = (entrada[i] + 32);
			}
			else{
				anagrama[k] = entrada[i];
			}
			k++;
		}
		i++;
	}
	return verificar(original, anagrama, j, k);
}

int main(){
	char entrada[100];
	fgets(entrada, 100, stdin);

	while((entrada[0] != 'F') || (entrada[1] != 'I') || (entrada[2] != 'M')){
	printf("%s\n", (separarStr(entrada) == 1 ? "SIM" : "NÃO"));
	fgets(entrada, 100, stdin);
	}
}
