tsp=[
     [0,19,92,29,49,78,6],
     [19,0,21,85,45,16,26],
     [92,21,0,24,26,87,47],
     [29,85,24,0,76,17,8],
     [49,45,26,76,0,90,27],
     [78,16,87,17,90,0,55],
     [6,26,47,8,27,55,0]
     ]
import random
import itertools
import math
import copy
x0=[1,2,3,4,5,6,7]  #目前可行解
xb=copy.deepcopy(x0)  #xb目前最佳解
fx0=0   #目前該可行解之狀態值
fxb=291 #適應函數目前最佳值
t0=300 #初始溫度
def step2():
    global fx0
    a=random.choice(list(itertools.combinations(x0,2))) #隨機挑選2個節點
    a0=x0.index(a[0])
    a1=x0.index(a[1])
    temp=x0[a0]
    x0[a0]=x0[a1]
    x0[a1]=temp
    fx0=tsp[x0[6]-1][x0[0]-1]
    for i,j in enumerate(x0):
        if(i==6):
            break
        else:
            fx0+=tsp[x0[i]-1][x0[i+1]-1]
t1=0
e=0
for k in  range(0,2000): #從第一開始
    step2()
    t1=0.95*t0
    t0=t1
    if(fx0<fxb):
        xb=copy.deepcopy(x0)
        fxb=fx0
    elif(fx0>fxb):
        e=math.exp(-((fx0-fxb)/t1))
        pr=min(1,e)#波茲曼
        r=random.random() #產生0到1的範圍數
        if(r<pr):
            xb=copy.deepcopy(x0)
            fxb=fx0
print(xb)
print(fxb)
    



