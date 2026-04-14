#include <stdio.h>
 
int substring(char *str) {
	int tam = 0;
        int i = 0;
        int maior = 0;
        int novaStr = 0;
        int j;
 
        while (str[i] != '\0' && str[i] != '\n') {
                for (j = novaStr; j < i; j++) {
                        if (str[j] == str[i]) { //Se j for igual a i começamos a contar uma nova substring
                                novaStr = j + 1;
                                j = i;
                        }
                }
                tam = i - novaStr + 1; //Armazenamos o tamanho da substring
                if (tam > maior) //Trocamos se achar uma substring maior que a anterior
                maior = tam;
                i++;
        }
        return maior;
}
 
int main() {
        char str[100];
        fgets(str, 100, stdin);
 
       	while (!(str[0] == 'F' && str[1] == 'I' && str[2] == 'M')) {
                printf("%d\n", substring(str));
                fgets(str, 100, stdin);
        }
}

