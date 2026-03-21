#include <stdio.h>
int substring(char entrada[100]){
	char saida[100];
	int verificar;
	int i = 1, j = 0, l, k;

	saida[0] = entrada[0];

	while(entrada[i] != '\0' && entrada[i] != '\n'){
		k = l;
		while(k >= j){
			if(entrada[i] == saida[k]){
				verificar = 0;
				k = 0;
			}
			else{
				verificar = 1;
			}
			k--;
		}
		if(verificar == 1){
			j++;
			saida[l] = entrada[i];
			l++;
		}
		else{
			l = i;
		}
		i++;
	}
	return j;
}

int main(){
	char entrada[100];
	fgets(entrada, 100, stdin);
	while(entrada[0] != 'F' && entrada[1] != 'I' && entrada[2] != 'M'){
		printf("%d\n", substring(entrada));
		fgets(entrada, 100, stdin);
	}
}
