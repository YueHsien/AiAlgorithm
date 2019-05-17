tsp=[
     [0,91.8,105.2,89.9,189.9,76.2,278.3,54.4],
     [91.8,0,187.2,38.9,271.3,162.9,363.3,88.4],
     [105.2,187.2,0,194.1,182.3,31.4,176.1,153.8],
     [89.9,38.9,194.1,0,249.4,166.1,368.3,63.6],
     [189.9,271.3,182.3,249.4,0,168.0,243.0,185.9],
     [76.2,162.9,31.4,166.1,168.0,0,202.2,122.8],
     [278.3,363.3,176.1,368.3,243.0,202.2,0,320.0],
     [54.4,88.4,153.8,63.6,185.9,122.8,320.0,0]
     ]
import random 
import itertools
import numpy as np
nums = itertools.permutations([1,2,3,4,5,6,7,8])
nums2=list(nums)
n=[] #族群大小n
distance=0
first=[]   #第一個最好的染色體
second=[]   #次要最好的染色體
def length(k):
    global distance
    distance=tsp[k[7]-1][k[0]-1]
    for i in range(0,8):
        if(i==7):
            break
        else:
            distance+=tsp[k[i]-1][k[i+1]-1]
    distance="%.1f" % distance
    distance=float(distance)
def select5n(k):
    i=0
    for x in nums2: #挑五個
        if(i==200):
            break;
        else:
            n.append([])
            randselect=random.randint(0,40000)
            n[i].append(nums2[randselect])
            i+=1
def calfitness(k):
    fitmax=0
    for i in k:
        if(i[1]>fitmax):
            fitmax=i[1]
    for i in k:
        i[2]=fitmax-i[1]
        i[2]="%.1f" % i[2]
        i[2]=float(i[2])
def findmax(k):
    fmax=0
    for i in k:
        if(i[2]>fmax):
            fmax=i[2]
    return fmax
def findsecmax(k,firstmax):
    fmax=firstmax
    secmax=0
    for i in k:
        if(i[2]>secmax and i[2]!=fmax):
            secmax=i[2]
    return secmax
def select2(n,first,second,firstmax,secondmax):
    for i,j in enumerate(n):
        if(j[2]==firstmax):
            first=j[0]
        elif(j[2]==secondmax):
            second=j[0]
    return first,second
def crossover(k,first,second):
    point=random.randint(0,6)
    child1=[ first[i] for i in range(0,point+1)] #124853769
    child2=[ second[i] for i in range(0,point+1)] #17365824
    for i in second: #子代1
        if(i not in child1):
            child1.append(i)
    for i in first: #子代2
        if(i not in child2):
            child2.append(i)
    return child1,child2

def mutation(child1,child2):
    rate=0.1 #突變機率
    hit=random.random()
    if(hit<rate): #進行突變
       select=random.randint(0,1)
       if(select==1): #子代1突變
           slice = random.sample(child1,2) #隨機選取2點
           index1=child1.index(slice[0])  #找出第一個點索引
           index2=child1.index(slice[1]) #找出第二個點索引
           temp=child1[index1]
           child1[index1]=child1[index2]
           child1[index2]=temp
       else:                    #子代2突變
           slice = random.sample(child2,2) #隨機選取2點
           index1=child2.index(slice[0])  #找出第一個點索引
           index2=child2.index(slice[1]) #找出第二個點索引
           temp=child2[index1]
           child2[index1]=child2[index2]
           child2[index2]=temp

def replace(k,child1,child2):
    fmin1=k[0][2]
    fmin2=k[0][2]
    index1=0 #第一個適合度差的索引
    index2=0 #第二個適合度差的索引
    for i in k:
        if(i[2]<fmin1):
            fmin1=i[2]
    for i in k:
        if(i[2]<fmin2 and i[2]!=fmin1):
            fmin2=i[2]
    for i in k:
        if(i[2]==fmin1):
            i[0]=child1
        if(i[2]==fmin2):
            i[0]=child2
    


def routewheel(k):
    select1=[] #輪盤法第一個
    select2=[] #輪盤法第二個
    fitsum=0
    fitemp=[]#放加總機率
    fitemp2=[]#放每個機率
    fitevery=0
    for i in k:
        fitsum+=i[2]
        fitsum="%.1f" % fitsum
        fitsum=float(fitsum)
    for i in k:
        s=i[2]/fitsum
        #s="%.1f" % s
        #s=float(s)
        fitemp2.append(s)
    fitemp2.remove(0.0)
    for i in fitemp2:
        fitevery+=i
        fitevery="%.5f" % fitevery
        fitevery=float(fitevery)
        fitemp.append(fitevery)
    index=[]
    for x in range(0,2): 
        diceball=random.random()
        for i,j in enumerate(fitemp):
            if(diceball<fitemp[0]):
                ss=fitemp2[i]*fitsum
                ss="%.1f" % ss
                ss=float(ss)
                index.append(ss)
                break;
            elif(i==0):
                continue
            elif(diceball>fitemp[i-1] and diceball<fitemp[i]):
                ss=fitemp2[i]*fitsum
                ss="%.1f" % ss
                ss=float(ss)
                index.append(ss)
                break;
    if(not index[0] or not index[1]):
        print("debug")
    for i,j in enumerate(k):
        if(index[0]==k[i][2]):
            select1=k[i][0]
        if(index[1]==k[i][2]):
            select2=k[i][0]
    #利用dice>i[i-1] and dice<i[i]來進行判斷，並找出index對應到fitemp2，最後在轉換原數值，
    #最後找出該染色體的index
    #問題出在位數問題
    return select1,select2

select5n(n) ##挑5個解作為族群大小
for k in  n:
    k.append([])
    k.append([])

#進交配池的最好經過輪盤
for generation in range(0,400):
    for k in n:
        distance=0
        length(k[0])
        k[1]=distance
    calfitness(n)
    #找適合度最高的兩個交配
    #可改用輪盤法選擇
    #firstmax=findmax(n) #先找最大
    #secondmax=findsecmax(n,firstmax) #再找次大  
    #first,second=select2(n,first,second,firstmax,secondmax)
    check=0
    for i in n:
        check+=i[2]
    if(check==0):
        break;
    first,second=routewheel(n) #利用輪盤找兩個交配
    child1,child2=crossover(n,first,second) #子代
    mutation(child1,child2)
    replace(n,child1,child2) #將2子代取代適合度最差的兩個染色體

print("最佳解=")
print(n[0])







