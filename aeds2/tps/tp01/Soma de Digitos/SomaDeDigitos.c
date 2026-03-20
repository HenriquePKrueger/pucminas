#include <stdio.h>

int soma(char nums[], int i){
	if(nums[i] == '\0'){
		return 0;
	}
	else{
		return (nums[i] - '0') + soma(nums, i + 1);
	}
}	

int main(){

	char nums[100];
	int i = 0;
	scanf("%s", nums);
	
	while(!(nums[0] == 'F' && nums[1] == 'I' && nums[2] == 'M')){
		printf("%d\n",soma(nums, i));
		scanf("%s", nums);
	}

}
