#include <stdio.h>

int x1(char *entrada, int cont){
	char c = entrada[cont];
	if(c == '\0' || c == '\n'){
		cont = 0;
		return 1;
	}
	else{
		if(c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u'){
			cont = 0;
			return 0;
		}
		else{
			return x1(entrada, cont + 1);
		}
	}
}

int x2(char *entrada, int cont){
	char c = entrada[cont];
	if(c == '\0' || c == '\n'){
		cont = 0;
		return 1;
	}
	else{
		if(!(c >= 'a' && c <= 'z') || (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')){
			cont = 0;
			return 0;
		}
		else{
			return x2(entrada, cont + 1);
		}
	}
}

int x3(char *entrada, int cont){
	char c = entrada[cont];
	if(c == '\0' || c == '\n'){
		cont = 0;
		return 1;
	}
	else{
		if(!(c >= '0' && c <= '9')){
			cont = 0;
			return 0;
		}	
		else{
			return x3(entrada, cont + 1);
		}
	}
}

int x4(char *entrada, int cont){
	char c = entrada[cont];
	if(c == '\0' || c == '\n'){
		cont = 0;
		return 1;
	}
	else{
		if(!(c >= '0' && c <= '9') &&  c != '.'){
			cont = 0;
			return 0;
		}
		else{
			return x4(entrada, cont + 1);
		}
	}
}

int main(){
	char entrada[1000];
	int cont = 0;
	fgets(entrada, 1000, stdin);
	
	while(entrada[0] != 'F' || entrada[1] != 'I' || entrada[2] != 'M'){
		int i = 0;
		while(entrada[i] != '\0' && entrada[i] != '\n'){
			if(entrada[i] >= 'A' && entrada[i] <= 'Z'){
				entrada[i] = (entrada[i] + 32);
			}
			i++;
		}
		printf("%s", (x1(entrada, cont) == 1 ? "SIM " : "NAO "));
		printf("%s", (x2(entrada, cont) == 1 ? "SIM " : "NAO "));
		printf("%s", (x3(entrada, cont) == 1 ? "SIM " : "NAO "));
		printf("%s\n", (x4(entrada, cont) == 1 ? "SIM" : "NAO"));
		fgets(entrada, 1000, stdin);
	}

	return 0;
}
