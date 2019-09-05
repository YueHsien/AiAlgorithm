import random
import itertools
import math
import copy
#以小數第二位為主
Ld=-30
Ud=30
best=0
bestv=[]
tabulist=[]
def select(): 
    x1=selectx()
    x2=selecty()
    temp=[]
    temp.append(x1)
    temp.append(x2)
    return temp
def selectx():#產生0-600數字，並轉呈二進位
    x=random.randint(0,60000)
    y="{0:b}".format(x)
    z=y.zfill(16) #擴張至13個0
    return z
def selecty():
    x=random.randint(0,60000)
    y="{0:b}".format(x)
    z=y.zfill(16) #擴張至13個0
    return z
def encode(x):
     x=int(x,2)      #先將二進位轉乘十進位
     x=-30+0.001*x   #進行編碼動作
     x=round(x,3)
     return x
def function(vector):
    sumterm=0
    dimension=2
    for i in vector:
        i=encode(i)
        sumterm+=i*i
    sumterm=-0.2*math.sqrt(sumterm/dimension)
    costerm=0
    for i in vector:
        i=encode(i)
        costerm+=math.cos(2*math.pi*i)
    costerm=costerm/dimension
    Z=-20*math.exp(sumterm)-math.exp(costerm)+20+math.exp(1)
    return Z 
def nontabuselect(s,tabulist,Ld,Ud):
    selectarray=neighborxy(s,tabulist)
    return selectarray  

def neighborxy(s,tabulist):
    while 1:
        temp1=copy.deepcopy(s)
        pointx=random.randint(0,15)
        pointy=random.randint(0,15)
        if(temp1[0][pointx]=="0"):
            temp1[0]=temp1[0][:pointx]+"1"+temp1[0][pointx+1:]
        elif temp1[0][pointx]=="1":
            temp1[0]=temp1[0][:pointx]+"0"+temp1[0][pointx+1:]
        if(temp1[1][pointy]=="0"):
            temp1[1]=temp1[1][:pointy]+"1"+temp1[1][pointy+1:]
        elif temp1[1][pointy]=="1":
            temp1[1]=temp1[1][:pointy]+"0"+temp1[1][pointy+1:]
        solutionx=encode(temp1[0])
        solutiony=encode(temp1[1])
        temp2=[solutionx,solutiony]
        if(temp2 not in tabulist and solutionx<30 and solutionx>-30 and solutiony<30 and solutiony>-30):
            break
    return temp1

def determination(s,v,tabulist,f1,best,bestv):
    f2=function(v)
    if(f2<f1):
        f1=f2
        temp=copy.deepcopy(v)
        tabulist=updatetabulist(tabulist,s)  #把現在這個解放進tabu
        s=temp
        if(f2<best):
            best=f2
            bestv=temp
    elif f2>f1:
        f1=f2
        temp=copy.deepcopy(v)
        tabulist=updatetabulist(tabulist,s)
        s=temp
    return s,f1,tabulist,best,bestv
def updatetabulist(tabulist,s):
    if(len(tabulist)==1000):
        tabulist.pop(0)
        x=encode(s[0])
        y=encode(s[1])
        solution=[x,y]
        tabulist.append(solution)
    else:
        x=encode(s[0])
        y=encode(s[1])
        solution=[x,y]
        tabulist.append(solution)
    return tabulist
#total=0;
#for j in range(30):
s=select()
f1=function(s)
best=f1
bestv=s
for i in range(1000):
    v=nontabuselect(s,tabulist,Ld,Ud)
    f2=function(v)
    s,f1,tabulist,best,bestv=determination(s,v,tabulist,f1,best,bestv)
print("最佳位置")
print(encode(bestv[0]))
print(encode(bestv[1]))
print("最佳值")
print(best)

#print(total/30)
    
    
    


      
            
