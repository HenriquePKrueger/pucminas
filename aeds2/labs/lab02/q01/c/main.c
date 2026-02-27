#include <stdio.h>

int main() {
	char s1[100], s2[100], s3[100];
	int i = 0, j = 0, k = 0;

	//printf("Digite a primeira string:\n");
	//scanf("%s", s1);

	//printf("Digite a segunda string:\n");
	//scanf("%s", s2);
	
	printf("Digite a primeira e a segunda string:\n");
	while (scanf("%s %s", s1, s2) != EOF) {

		i = 0;
		j = 0;
		k = 0;

		while (s1[i] != '\0' && s2[j] != '\0'){
			s3[k] = s1[i];
			k++;
			i++;
			s3[k] = s2[j];
			k++;
			j++;
		}

		while (s1[i] != '\0'){
			s3[k] = s1[i];
			i++;
			k++;
		}
	
		while (s2[j] != '\0'){
			s3[k] = s2[j];
			j++;
			k++;
		}
	
	s3[k] = '\0';

	printf("Strings combinadas:\n%s\n", s3);

	}

}
