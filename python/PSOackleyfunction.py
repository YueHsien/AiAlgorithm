import random
import itertools
import math
import copy
Ld=-30
Ud=30
c1=2
c2=2
vmin=-4
vmax=4
weight=0.9
globalmax=0
globalposition=[]
def select(number,Ld,Ud,vmax,vmin):
    i=0
    temp=[]
    for i in range(number): #挑n個
        temp.append([]);
        temp[i].append(position(Ld,Ud))     #0  位置
        temp[i].append(setspeed(vmax,vmin)) #1  速度
        temp[i].append(function(temp[i][0]))#2  值
        localoptimu=temp[i][2];
        temp[i].append(localoptimu) #局部最佳值初始設定為0  #3
        array=copy.deepcopy(temp[i][0])
        temp[i].append(array)#局部最佳設定為初始位置  #4
    return temp
def position(Ld,Ud):
    x=random.uniform(Ld,Ud)
    y=random.uniform(Ld,Ud)
    temp=[x,y]
    return temp
def setspeed(vmax,vmin):
    x=random.uniform(vmin,vmax)
    y=random.uniform(vmin,vmax)
    temp=[x,y]
    return temp
def function(vector):
    sumterm=0
    dimension=2
    for i in vector:
        sumterm+=i*i
    sumterm=-0.2*math.sqrt(sumterm/dimension)
    costerm=0
    for i in vector:
        costerm+=math.cos(2*math.pi*i)
    costerm=costerm/dimension
    Z=-20*math.exp(sumterm)-math.exp(costerm)+20+math.exp(1)
    return Z 
def globalevaluate(particle):
     minvalue=particle[0][3]
     minposition=particle[0][4]
     for i in particle:
         if(i[3]<minvalue):
             minvalue=i[3]
             minposition=i[4]
     #minvalue=round(minvalue,2)
     #minposition[0]=round(minposition[0],2)
     #minposition[1]=round(minposition[1],2)
     return minvalue,minposition
def renewparticle(particle,c1,c2,globalposition,weight,vmax,vmin,Ld,Ud):
    newposition=[]
    newvalue=0
    for i in particle:
        r1=random.random()
        r2=random.random()
        i[1][0]=(weight*i[1][0])+(c1*r1*(i[4][0]-i[0][0]))+(c2*r2*(globalposition[0]-i[0][0]))
        #print(i[1][0])
        i[1][1]=(weight*i[1][1])+(c1*r1*(i[4][1]-i[0][1]))+(c2*r2*(globalposition[1]-i[0][1]))
        #print(i[1][1])
        if(i[1][0]>0 and i[1][0]>vmax):
            i[1][0]=vmax
        elif(i[1][0]<0 and i[1][0]<vmin):
            i[1][0]=vmin
        if(i[1][1]>0 and i[1][1]>vmax):
            i[1][1]=vmax
        elif(i[1][1]<0 and i[1][1]<vmin):
            i[1][1]=vmin
        i[0][0]=i[0][0]+i[1][0]
        i[0][1]=i[0][1]+i[1][1]
        if(i[0][0]>0 and i[0][0]>Ud):
            i[0][0]=Ud
        elif(i[0][0]<0 and i[0][0]<Ld):
            i[0][0]=Ld
        if(i[0][1]>0 and i[0][1]>Ud):
            i[0][1]=Ud
        elif(i[0][1]<0 and i[0][1]<Ld):
            i[0][1]=Ld
    return particle
def evallocal(particle):
    for i in particle:
        i[2]=function(i[0])
        if(i[2]<i[3]):
            tempvalue=i[2]
            i[3]=tempvalue
            temparray=copy.deepcopy(i[0])
            i[4]=temparray