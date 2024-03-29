#include <iostream>
#include <vector>  //for std::vector
#include <string>
#include <fstream>
#include <cstdlib>
#include <math.h>
#include <cstdlib> /* 亂數相關函數 */
#include <ctime>   /* 時間相關函數 */
using namespace std;
/* run this program using the console pauser or add your own getch, system("pause") or input loop */
int findcapacity()
{
    fstream file;
    file.open("knapsackdatasets/p02_c.txt");
    char buffer[200];
    if(!file)
        cout << "無法開啟\n";
    else
    {
        file.read(buffer,sizeof(buffer));
        file.close();
    }
    string str=buffer;
    int capacity = std::atoi (str.c_str());
    return capacity;
}
int* select(int n,int *weights,int capacity)
{
    int *ss=new int[n];
    while(true)
    {
        int weight=0;
        for(int i=0; i<n; i++)
        {
            int temp=rand()%2;
            ss[i]=temp;
            if(ss[i]==1)
            {
                weight+=weights[i];
            }
        }
        if(weight<=capacity)
        {
            break;
        }
    }
    return ss;
}
int Evaluation(int n,int *s,int *weights,int *profits)
{
    int f=0;
    for(int i=0; i<n; i++)
    {
        if(s[i]==1)
        {
            f+=profits[i];
        }
    }
    return f;
}
int* neighborselect(int n,int *s)
{
    int point=(rand()%n);

    int *temp=new int[n];
    for(int i=0;i<n;i++){
    temp[i]=s[i];
    }
    if(temp[point]==1)
    {
        temp[point]=0;
    }
    if(temp[point]==0)
    {
        temp[point]=1;
    }
    return temp;
}
int main(int argc, char** argv)
{
    //int point=rand()%2;
    srand( time(NULL) );
    int run=0;
    int iteration=0;
    cin >> run >> iteration;
    int capacity=26;
    int timerun=0;
    while(timerun<run)
    {
        float temparature=1000;
        int weights[]= {12,7,11,8,9};
        int profits[]= {24,13,23,15,16};
        int optselect[]= {0,1,1,1,0};
        int point;
        int length=sizeof(weights)/sizeof(*weights);
        int *s=select(length,weights,capacity);
        int f1=Evaluation(length,s,weights,profits);
        int time=0;
        int best=0;
        while(time<iteration)
        {
            int *v=s;
            int point=(rand()%length);
            if(v[point]==1){
            v[point]=0;
            }else if(v[point]==0){
            v[point]=1;
            }
            int f2=Evaluation(length,v,weights,profits);
            int weightv=0;
            for(int i=0; i<length; i++)
            {
                if(v[i]==1)
                {
                    weightv+=weights[i];
                }
            }
            if(weightv<=capacity)
            {
                if(f2>f1)
                {
                    s=v;
                    f1=f2;
                    if(f2>best)
                    {
                        best=f2;
                    }
                }
                else if(f2<f1)
                {
                    float pa=exp((f2-f1)/temparature);
                    float r = (float) rand() / (RAND_MAX + 1.0);
                    if(r<pa)
                    {
                        s=v;
                        f1=f2;
                    }
                }
                temparature = (float) (0.95 * temparature);
            }
            time++;
        }
        cout << "iteration ";
        cout<< iteration;
        cout << "\n";
        cout << "best=";
        cout<< best;
        cout << "\n";
        iteration+=20;
        cout << "\n";
        timerun++;
    }
    return 0;
}
