#include <iostream>
#include <stdlib.h> /* 亂數相關函數 */
using namespace std;
/* run this program using the console pauser or add your own getch, system("pause") or input loop */

int main(int argc, char** argv) {
	int run=0;
    int iteration=0;
    int len=0;
    cin >> run >> iteration>>len;
	int timerun=0;
	int number=run;
	int number2=iteration;
	int number3=len;
	while(timerun<number){
		int time=0;
		char bit[number3];
		for(int i=0;i<number3;i++){
			int temp=rand()%2;
			char ss[1];
			sprintf(ss,"%d",temp);
			bit[i]=ss[0];
		}
		char v[number3];
		int f1=0;
		int one=0;
		for(int i=0;i<number3;i++){
			if(bit[i]=='1'){
				one++;
			}
		}
		f1=one;
		while(time<number2){
		int point=rand()%number3;
		for(int i=0;i<number3;i++){
			v[i]=bit[i];
		}
		if(v[point]=='0'){
			v[point]='1';
		}
		else if(v[point]=='1'){
			v[point]='0';
		}
		int f2=0;
		one=0;
		for(int i=0;i<number3;i++){
			if(v[i]=='1'){
				one++;
			}
		}
		f2=one;
		if(f2>f1){
			for(int i=0;i<number3;i++){
				bit[i]=v[i];
			}
			f1=f2;
		}
		time++;
	} 
		cout << "最好解";
		cout << f1;
		cout << "\n";
	timerun++;
}
	return 0;
}
