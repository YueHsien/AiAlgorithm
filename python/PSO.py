import random
import itertools
import math
import copy
Ld=-100
Ud=100
c1=2
c2=2
vmin=-4
vmax=4
weight=0.9
def select(number,Ld,Ud,vmax,vmin):
    i=0
    temp=[]
    for i in range(number): #挑n個
        temp.append([]);
        temp[i].append(position(Ld,Ud))
        temp[i].append(setspeed(vmax,vmin))
        temp[i].append(function(temp[i][1]))
    return temp
def position(Ld,Ud):
    x=random.uniform(-100,100)
    y=random.uniform(-100,100)
    temp=[x,y]
    return temp
def setspeed(vmax,vmin):
    x=random.uniform(-100,100)
    y=random.uniform(-100,100)
    temp=[x,y]
    return temp
def function(vector):
    x1=vector[0]
    x2=vector[1]
    Z=math.pow(x1,2)+(2*math.pow(x2,2))-(0.3*(math.cos(3*math.pi*x1)))-(0.4*math.cos(4*math.pi*x2))+0.7
    return Z 
def evaluate(particle):
    
minl=0
mint=[]
deb=0
start=1 #可根據起始點決定

for i in range(50):
    particle=select(10,Ld,Ud,vmax,vmin)
    particlefitness=evaluate(particle)
    print(particle)
print("最低路徑")
print(mint)
print()
print("最短長度=")
print(length(mint))