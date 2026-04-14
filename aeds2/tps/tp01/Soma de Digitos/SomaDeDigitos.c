#include <stdio.h>

int somaDigitos(char nums[]) {
    int resultado = 0;
    
    for (int i = 0; nums[i] != '\0' && nums[i] != '\n'; i++) {
            // Soma o numero na variável
            resultado += (nums[i] - '0');
    }
    return resultado;
}

int main() {
	char nums[100];
	
	while (fgets(nums, 100, stdin) != NULL) {
		printf("%d\n", somaDigitos(nums));
	}
}
